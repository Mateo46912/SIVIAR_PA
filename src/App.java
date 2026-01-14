import Controlador.ControladorS;

import Vista.VentanaGeneralP;
import Modelo.traspasoDAO;
import Modelo.DatosUsuario;
public class App {
    public static void main(String[] args) throws Exception {
        
        traspasoDAO instruccionesDAO = new traspasoDAO();
        DatosUsuario usuarioUsado = new DatosUsuario();       
        VentanaGeneralP ventana = new VentanaGeneralP(instruccionesDAO, usuarioUsado);
        ventana.setVisible(true);

    }
}
