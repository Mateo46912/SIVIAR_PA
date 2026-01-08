package Modelo;

import BaseDeDatos.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class traspasoDAO {
    
    Connection conexionBD;
    PreparedStatement prepararConsulta;
    ResultSet recibirConsulta;
    
    public boolean registrarUsuario(DatosUsuario usuario) {

        String sql = "INSERT INTO usuarios (nombre, contrasena) VALUES (?, ?)";
        
        try {
            conexionBD = ConexionBD.conectar();

            prepararConsulta = conexionBD.prepareStatement(sql);
            
            prepararConsulta.setString(1, usuario.getNombreU());   
            prepararConsulta.setString(2, usuario.getContrasena()); 
            
            prepararConsulta.execute();
            return true; 
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro al registrar el usuario en la base de datos: " + e.toString());
            return false;
        } finally {
            ConexionBD.cerrar();
        }
    }
    
    public List<DatosUsuario> listarUsuarios() {
        List<DatosUsuario> listaUsuariosBD = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        
        try {
            conexionBD = ConexionBD.conectar();
            prepararConsulta = conexionBD.prepareStatement(sql);
            recibirConsulta = prepararConsulta.executeQuery(); 
            
            while (recibirConsulta.next()) {
                DatosUsuario usuarioRecibido = new DatosUsuario();
                
                usuarioRecibido.setId(recibirConsulta.getInt("id"));             
                usuarioRecibido.setNombreU(recibirConsulta.getString("nombre"));   
                usuarioRecibido.setContrasena(recibirConsulta.getString("contrasena")); 
                
                listaUsuariosBD.add(usuarioRecibido);
            }          
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener la lista de usuarios de la base de datos: " + e.toString());
        } finally {
            ConexionBD.cerrar();
        }
        
        return listaUsuariosBD; 
    }

    public boolean modificarUsuario(DatosUsuario usuario) {
        String sql = "UPDATE usuarios SET contrasena = ? WHERE id = ?";
        
        try {
            conexionBD = ConexionBD.conectar();
            prepararConsulta = conexionBD.prepareStatement(sql);
            
            prepararConsulta.setString(1, usuario.getContrasena()); 
            prepararConsulta.setInt(2, usuario.getId());           
            
            prepararConsulta.execute();
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar la contrasena en la base de datos: " + e.toString());
            return false;
        } finally {
            ConexionBD.cerrar();
        }
    }
    
    public boolean eliminarUsuario(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        
        try {
            conexionBD = ConexionBD.conectar();
            prepararConsulta = conexionBD.prepareStatement(sql);
            
            prepararConsulta.setInt(1, id); 
            
            prepararConsulta.execute();
            return true;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el usuario de la base de datos: " + e.toString());
            return false;
        } finally {
            ConexionBD.cerrar();
        }
    }
}