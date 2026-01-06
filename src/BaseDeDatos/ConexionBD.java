package BaseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConexionBD {

    private static  String direccionBD = "jdbc:mysql://localhost:3306/control_acceso?serverTimezone=UTC";
    private static  String usuarioBD = "root";     
    private static  String contrasenaBD = "";     

    private static Connection conexion = null;

    public static Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            conexion = DriverManager.getConnection(direccionBD, usuarioBD, contrasenaBD);
            System.out.println("La conexion a la Base de Datos se ha realizado correctamente.");
            
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error en la conexion a la Base de datos contacte con un tecnico: " + e.getMessage());
        }
        return conexion; 
    }

    public static void cerrar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}