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

    private SerialPort conexionSerialArduino;
    private traspasoDAO DatosBdDao;
    private int contadorFallidosContraseña = 0;
    private int idLlegadaTemp = 0; 
    
    private StringBuilder guardarDatosArduino = new StringBuilder();

    public ArduinoService(traspasoDAO DatosBdDao) {
        this.DatosBdDao = DatosBdDao;
    }

    public String[] obtenerPuertos() {
        SerialPort[] ports = SerialPort.getCommPorts();
        String[] nombres = new String[ports.length];
        for (int i = 0; i < ports.length; i++) {
            nombres[i] = ports[i].getSystemPortName();
        }
        return nombres;
    }

    public boolean conectarArduino(String nombrePuerto) {

        conexionSerialArduino = SerialPort.getCommPort(nombrePuerto);
        conexionSerialArduino.setBaudRate(9600);

        if (conexionSerialArduino.openPort()) {
            conexionSerialArduino.addDataListener(new SerialPortDataListener() {
                @Override
                public int getListeningEvents() {
                    return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
                }

                @Override
                public void serialEvent(SerialPortEvent event) {

                    if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE){
                        return;
                    }        
                    try {
                        byte[] buffer = new byte[conexionSerialArduino.bytesAvailable()];
                        conexionSerialArduino.readBytes(buffer, buffer.length);
                        
                        String llegadaDatos = new String(buffer);
                        guardarDatosArduino.append(llegadaDatos);

                        validarMensajeCompletoA();
                    } catch (Exception e) {
                        System.out.println("Error al momento de leer los datos enviados por el arduino: " + e.getMessage());
                    }
                }
            });
            return true;
        }
        return false;
    }

// Validar si el mensaje recibido del Arduino está completo (termina con un salto de línea)
    private void validarMensajeCompletoA() {

        int verificarSaltoDeLinea = guardarDatosArduino.indexOf("\n");
        
        while (verificarSaltoDeLinea != -1) {
            String mensajeCompleto = guardarDatosArduino.substring(0, verificarSaltoDeLinea).trim();
            if (!mensajeCompleto.isEmpty()) {
                procesarTipoMensaje(mensajeCompleto);
            }
            guardarDatosArduino.delete(0, verificarSaltoDeLinea + 1);            
            verificarSaltoDeLinea = guardarDatosArduino.indexOf("\n");
        }
    }

    private void procesarTipoMensaje(String mensajeLlegada) {
        try {
            if (mensajeLlegada.startsWith("ID:")) {
                
                String separarID = mensajeLlegada.substring(3).replaceAll("[^0-9]", "");
                
                if (!separarID.isEmpty()) {
                    int idRecibido = Integer.parseInt(separarID);
                    verificarIDLlegada(idRecibido);
                }
            }
            else if (mensajeLlegada.startsWith("PASS:")) {
                String separarContrasena = mensajeLlegada.substring(5).replaceAll("[^0-9]", "");
                
                if (!separarContrasena.isEmpty()) {
                    verificarContrasenaLlegada(separarContrasena);
                }
            }
        } catch (Exception e) {
            System.out.println("Error procesando el tipo de mensaje que envia el arduino: " + e.getMessage());
        }
    }
 // Metodo para verificar si el ID recibido desde el Arduino existe en la base de datos
   private void verificarIDLlegada(int idRecibido) {

        List<DatosUsuario> usuariosRegistradosBD = DatosBdDao.listarUsuarios();
        boolean usuarioEncontradoBD = false;

        for (DatosUsuario usuarioRegistrado : usuariosRegistradosBD) {
            if (usuarioRegistrado.getId() == idRecibido) {
                usuarioEncontradoBD = true;
                break;
            }
        }

        if (usuarioEncontradoBD) {
            idLlegadaTemp = idRecibido;
            contadorFallidosContraseña = 0;
            DarOrdenArduino("ID_OK");
            System.out.println("Se encontró el ID en la base de datos: " + idRecibido);
        } else {
            DarOrdenArduino("ID_ERROR");            
            registrarEnBaseDeDatos(0, "Desconocido", "Fallido", 1);          
            System.out.println("No se encontró el ID en la base de datos: " + idRecibido);
        }
    }
// Metodo para verificar si la contraseña recibida desde el Arduino es correcta
    private void verificarContrasenaLlegada(String contrasenaRecibida) {

        String contrasenaRealRegistrada = "";
        String nombreUsuario = "Desconocido";
        
        List<DatosUsuario> usuariosRegistradosBD = DatosBdDao.listarUsuarios();

        for (DatosUsuario usuarioRegistrado : usuariosRegistradosBD) {
            if (usuarioRegistrado.getId() == idLlegadaTemp) {
                contrasenaRealRegistrada = usuarioRegistrado.getContrasena();
                nombreUsuario = usuarioRegistrado.getNombreU();
                break;
            }
        }

        if (contrasenaRealRegistrada.equals(contrasenaRecibida)) {
            DarOrdenArduino("ACCESO_CONCEDIDO");
            registrarEnBaseDeDatos(idLlegadaTemp, nombreUsuario, "Exitoso", contadorFallidosContraseña + 1);
            contadorFallidosContraseña = 0;
            idLlegadaTemp = 0;
        } else {
            contadorFallidosContraseña++;

            if (contadorFallidosContraseña >= 3) {
                DarOrdenArduino("BLOQUEO");
                registrarEnBaseDeDatos(idLlegadaTemp, nombreUsuario, "Bloqueado", contadorFallidosContraseña);
                contadorFallidosContraseña = 0;
            } else {
                DarOrdenArduino("PASS_ERROR");
                registrarEnBaseDeDatos(idLlegadaTemp, nombreUsuario, "Fallido", contadorFallidosContraseña);
            }
        }
    }

    private void registrarEnBaseDeDatos(int idU, String nombreU, String estadoLog, int contadorIntentos) {
        DatosLogs logNuevo = new DatosLogs();
        logNuevo.setIdUsuario(idU);
        logNuevo.setNombreUsuario(nombreU);
        logNuevo.setEstadoLog(estadoLog); 
        logNuevo.setIntentoAct(contadorIntentos); 
        DatosBdDao.registrarIntentoLog(logNuevo);
    }

    private void DarOrdenArduino(String ordenA) {
        if (conexionSerialArduino != null && conexionSerialArduino.isOpen()) {
            PrintWriter salidaArduino = new PrintWriter(conexionSerialArduino.getOutputStream());
            salidaArduino.print(ordenA + "\n");
            salidaArduino.flush();
        }
    }
}