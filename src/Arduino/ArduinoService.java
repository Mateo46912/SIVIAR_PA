
package Arduino;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import java.io.PrintWriter;
import java.util.List;
import Modelo.traspasoDAO;
import Modelo.DatosUsuario;
import Modelo.DatosLogs; 

public class ArduinoService {

    private SerialPort puertoSerial;
    private traspasoDAO dao;
    private int intentosFallidos = 0;
    private int idUsuarioTemporal = 0; 

    public ArduinoService(traspasoDAO dao) {
        this.dao = dao;
    }


    public String[] obtenerPuertos() {
        SerialPort[] ports = SerialPort.getCommPorts();
        String[] nombres = new String[ports.length];
        for (int i = 0; i < ports.length; i++) {
            nombres[i] = ports[i].getSystemPortName();
        }
        return nombres;
    }

    public boolean conectar(String nombrePuerto) {
        puertoSerial = SerialPort.getCommPort(nombrePuerto);
        puertoSerial.setBaudRate(9600);

        if (puertoSerial.openPort()) {
            puertoSerial.addDataListener(new SerialPortDataListener() {
                @Override
                public int getListeningEvents() {
                    return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
                }

                @Override
                public void serialEvent(SerialPortEvent event) {
                    if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) return;
                    
                    byte[] buffer = new byte[puertoSerial.bytesAvailable()];
                    puertoSerial.readBytes(buffer, buffer.length);
                    String mensaje = new String(buffer).trim();
                    
                    if (mensaje.length() > 2) {
                        procesarMensaje(mensaje);
                    }
                }
            });
            return true;
        }
        return false;
    }

    private void procesarMensaje(String mensaje) {
        if (mensaje.startsWith("ID:")) {
            try {
                int idRecibido = Integer.parseInt(mensaje.substring(3));
                verificarID(idRecibido);
            } catch (NumberFormatException e) {
                System.out.println("Error al leer ID");
            }
        }
        else if (mensaje.startsWith("PASS:")) {
            String passRecibida = mensaje.substring(5);
            verificarContrasena(passRecibida);
        }
    }

    private void verificarID(int id) {
        List<DatosUsuario> usuarios = dao.listarUsuarios();
        boolean encontrado = false;

        for (DatosUsuario u : usuarios) {
            if (u.getId() == id) {
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            idUsuarioTemporal = id;
            intentosFallidos = 0;
            enviarOrden("ID_OK");
        } else {
            enviarOrden("ID_ERROR");
            registrarEnBaseDeDatos(id, "DESCONOCIDO", "FALLIDO - ID NO EXISTE", 1);
        }
    }

    private void verificarContrasena(String passIngresada) {
        String passReal = "";
        String nombreUsuario = "DESCONOCIDO";
        
        List<DatosUsuario> usuarios = dao.listarUsuarios();
        for (DatosUsuario u : usuarios) {
            if (u.getId() == idUsuarioTemporal) {
                passReal = u.getContrasena();
                nombreUsuario = u.getNombreU();
                break;
            }
        }

        if (passReal.equals(passIngresada)) {
            enviarOrden("ACCESO_CONCEDIDO");
            registrarEnBaseDeDatos(idUsuarioTemporal, nombreUsuario, "EXITOSO", intentosFallidos);
            
            intentosFallidos = 0;
            idUsuarioTemporal = 0;
        } else {
            intentosFallidos++;
            
            if (intentosFallidos >= 3) {
                enviarOrden("BLOQUEO");
                registrarEnBaseDeDatos(idUsuarioTemporal, nombreUsuario, "BLOQUEADO", intentosFallidos);
                intentosFallidos = 0;
            } else {
                enviarOrden("PASS_ERROR");
                registrarEnBaseDeDatos(idUsuarioTemporal, nombreUsuario, "FALLIDO - PASS INCORRECTA", intentosFallidos);
            }
        }
    }
    private void registrarEnBaseDeDatos(int id, String nombre, String estado, int intentos) {
        DatosLogs nuevoLog = new DatosLogs();
        
        nuevoLog.setIdUsuario(id);
        nuevoLog.setNombreUsuario(nombre);
        nuevoLog.setEstadoLog(estado); 
        nuevoLog.setIntentoAct(intentos); 
        dao.registrarIntentoLog(nuevoLog);
    }

    private void enviarOrden(String orden) {
        if (puertoSerial != null && puertoSerial.isOpen()) {
            PrintWriter output = new PrintWriter(puertoSerial.getOutputStream());
            output.print(orden + "\n");
            output.flush();
        }
    }
}
