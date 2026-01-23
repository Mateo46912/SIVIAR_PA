package Modelo;

import BaseDeDatos.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class traspasoDAO {
    
    Connection conexionBD;
    PreparedStatement prepararConsulta;
    ResultSet recibirConsulta;
    
public boolean registrarUsuario(DatosUsuario usuario) {

        String peticionSql = "INSERT INTO usuarios (id, nombre, contrasena) VALUES (?, ?, ?)";
        
        try {
            conexionBD = ConexionBD.conectar();
            prepararConsulta = conexionBD.prepareStatement(peticionSql);
            
            prepararConsulta.setInt(1, usuario.getId());   
            prepararConsulta.setString(2, usuario.getNombreU());   
            prepararConsulta.setString(3, usuario.getContrasena()); 
            
            prepararConsulta.execute(); 
            
            return true; 
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar el usuario: " + e.getMessage());
            return false;
        } finally {
            ConexionBD.cerrar();
        }
    }
    
    public List<DatosUsuario> listarUsuarios() {
        List<DatosUsuario> listaUsuariosBD = new ArrayList<>();
        String peticionSql = "SELECT * FROM usuarios WHERE id != 0";
        
        try {
            conexionBD = ConexionBD.conectar();
            prepararConsulta = conexionBD.prepareStatement(peticionSql);
            recibirConsulta = prepararConsulta.executeQuery(); 
            
            while (recibirConsulta.next()) {
                DatosUsuario usuarioRecibido = new DatosUsuario();
                
                usuarioRecibido.setId(recibirConsulta.getInt("id"));             
                usuarioRecibido.setNombreU(recibirConsulta.getString("nombre"));   
                usuarioRecibido.setContrasena(recibirConsulta.getString("contrasena")); 
                
                listaUsuariosBD.add(usuarioRecibido);
            }          
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener la lista de usuarios de la base de datos: ");
        } finally {
            ConexionBD.cerrar();
        }
        
        return listaUsuariosBD; 
    }

    public boolean modificarUsuario(DatosUsuario usuario) {
        String peticionSql = "UPDATE usuarios SET contrasena = ? WHERE id = ?";
        
        try {
            conexionBD = ConexionBD.conectar();
            prepararConsulta = conexionBD.prepareStatement(peticionSql);
            
            prepararConsulta.setString(1, usuario.getContrasena()); 
            prepararConsulta.setInt(2, usuario.getId());           
            
            prepararConsulta.execute();
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar la contrasena en la base de datos: " );
            return false;
        } finally {
            ConexionBD.cerrar();
        }
    }
    
    public boolean eliminarUsuario(int id) {
        String peticionSql = "DELETE FROM usuarios WHERE id = ?";
        
        try {
            conexionBD = ConexionBD.conectar();
            prepararConsulta = conexionBD.prepareStatement(peticionSql);
            
            prepararConsulta.setInt(1, id); 
            
            prepararConsulta.execute();
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el usuario de la base de datos: " );
            return false;
        } finally {
            ConexionBD.cerrar();
        }
    }

    public boolean registrarIntentoLog(DatosLogs logAct) {
        String peticionSql = "INSERT INTO registros_acceso (id_usuario, nombre_usuario, fecha, hora, estado, intentos) VALUES (?, ?, CURDATE(), CURTIME(), ?, ?)";
        
        try {
            conexionBD = ConexionBD.conectar();
            prepararConsulta = conexionBD.prepareStatement(peticionSql);
            
            prepararConsulta.setInt(1, logAct.getIdUsuario());
            prepararConsulta.setString(2, logAct.getNombreUsuario());
            prepararConsulta.setString(3, logAct.getEstadoLog());
            prepararConsulta.setInt(4, logAct.getIntentoAct());            
            prepararConsulta.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al registrar el intento de log en la base de datos" );
            return false;
        } finally {
            ConexionBD.cerrar();
        }
    }

    public List<DatosLogs> filtrarListadoLogs(String fechaIni, String horaIni, String fechaFin, String horaFin) {
        List<DatosLogs> listaLogsFiltrada = new ArrayList<>();
        
        String peticionSql = "SELECT * FROM registros_acceso WHERE TIMESTAMP(fecha, hora) " +
                     "BETWEEN TIMESTAMP(?, ?) AND TIMESTAMP(?, ?) " +
                     "ORDER BY fecha DESC, hora DESC";
        
        try {
            conexionBD = ConexionBD.conectar();
            prepararConsulta = conexionBD.prepareStatement(peticionSql);

            prepararConsulta.setString(1, fechaIni);
            prepararConsulta.setString(2, horaIni);
            prepararConsulta.setString(3, fechaFin);
            prepararConsulta.setString(4, horaFin);
            
            recibirConsulta = prepararConsulta.executeQuery();
            
            while (recibirConsulta.next()) {
                DatosLogs logActual = new DatosLogs();
                logActual.setIdLogActual(recibirConsulta.getInt("id_registro"));
                logActual.setIdUsuario(recibirConsulta.getInt("id_usuario"));
                logActual.setNombreUsuario(recibirConsulta.getString("nombre_usuario"));
                logActual.setFechaLog(recibirConsulta.getString("fecha"));
                logActual.setHoraLog(recibirConsulta.getString("hora"));
                logActual.setEstadoLog(recibirConsulta.getString("estado"));
                logActual.setIntentoAct(recibirConsulta.getInt("intentos"));
                
                listaLogsFiltrada.add(logActual);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al filtrar logs: " + e.toString());
        } finally {
            ConexionBD.cerrar();
        }
        return listaLogsFiltrada;
    }
}