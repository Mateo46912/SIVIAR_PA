package Controlador;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import BaseDeDatos.ConexionBD;
import Modelo.DatosUsuario;
import Modelo.traspasoDAO;
import Vista.VentanaGeneralP;

public class ControladorS {
    
    private VentanaGeneralP ventanaGeneral;
    private traspasoDAO instruccionesDAO;
    private DatosUsuario usuarioUsado;

    public ControladorS(VentanaGeneralP ventanaGeneral, traspasoDAO instruccionesDAO, DatosUsuario usuarioUsado) {
        this.ventanaGeneral = ventanaGeneral;
        this.instruccionesDAO = instruccionesDAO;
        this.usuarioUsado = usuarioUsado;

        mostrarUsuariosTabla();
    }
   

    public void guardarNuevoUsuario() {
         
        String nombreUsuario = ventanaGeneral.getTxtNombre().getText();
        String contrasenaUsuario = String.valueOf(ventanaGeneral.getTxtContrasena().getPassword());

        if(nombreUsuario.isEmpty() || contrasenaUsuario.isEmpty()){
            JOptionPane.showMessageDialog(null, "Por favor, complete los campos de nombre y contraseña.");
            return;
        }

        usuarioUsado.setNombreU(nombreUsuario);
        usuarioUsado.setContrasena(contrasenaUsuario);

        if(instruccionesDAO.registrarUsuario(usuarioUsado)){
            JOptionPane.showMessageDialog(null, "Se agrego correctamente al usuario.");
            mostrarUsuariosTabla();
            JOptionPane.showMessageDialog(null, "Se le asigno el ID: " + usuarioUsado.getId()+"\n"+"Recueredelo lo usara como clave de acceso");
            ventanaGeneral.getTxtNombre().setText("");
            ventanaGeneral.getTxtContrasena().setText("");
        } else {
            System.out.println("Error al registrar el usuario del programa.");
        }

    }

    public void modificarContraseña(){
        String idUsarioModificar = ventanaGeneral.getTxtID().getText();
        
        if (idUsarioModificar.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Primero seleccione un usuario de la tabla para modificar.");
            return;
        }
        
        String contraseñaActual = String.valueOf(ventanaGeneral.getTxtContrasena().getPassword());
        
        String  contraseñaActualValidar = JOptionPane.showInputDialog(null, "Ingrese la contraseña actual de el usuario seleccionado para proseguir:");
        
        if (contraseñaActualValidar == null || contraseñaActualValidar.isEmpty()) {
            return; 
        }
        
        if (contraseñaActualValidar.equals(contraseñaActual)) {
            
            String contraseñaNuva = JOptionPane.showInputDialog(null, "Se verifico su identidad, ingrese la nueva contraseña: ");
            
            if (contraseñaNuva != null && !contraseñaNuva.isEmpty()) {

                usuarioUsado.setId(Integer.parseInt(idUsarioModificar));
                usuarioUsado.setContrasena(contraseñaNuva);
                
                if (instruccionesDAO.modificarUsuario(usuarioUsado)) {
                    JOptionPane.showMessageDialog(ventanaGeneral, "Contraseña modificada correctamente.");
                    mostrarUsuariosTabla();
                    ventanaGeneral.getTxtNombre().setText("");
                    ventanaGeneral.getTxtContrasena().setText("");
                }
            } else {
                JOptionPane.showMessageDialog(ventanaGeneral, "No se ingresó ninguna contraseña nueva, Operación cancelada.");
            }
            
        } else {
            JOptionPane.showMessageDialog(ventanaGeneral, "La contraseña ingresada no es correcta, Acceso denegado, intentelo de nuevo.");
        }

    }

    public void eliminarUsuario(){

        String idUsarioEliminar = ventanaGeneral.getTxtID().getText();
        
        if (idUsarioEliminar.isEmpty()) {
            JOptionPane.showMessageDialog(ventanaGeneral, "Primero debe seleccionar un usuario de la tabla para eliminar.");
            return;
        }
        
        String contraseñaActual = String.valueOf(ventanaGeneral.getTxtContrasena().getPassword());
        
       String  contraseñaActualValidar = JOptionPane.showInputDialog(null, "Ingrese la contraseña actual de el usuario seleccionado para proseguir:");
        
        if (contraseñaActualValidar == null || contraseñaActualValidar.isEmpty()) {
            return; 
        }

        if (contraseñaActualValidar.equals(contraseñaActual)) {
            
            int confirmacion = JOptionPane.showConfirmDialog(ventanaGeneral, "¿Está seguro que desea eliminar borrar al usuario " + ventanaGeneral.getTxtNombre().getText() + "?");
            
            if (confirmacion == 0) { 
                int idEliminar = Integer.parseInt(idUsarioEliminar);
                
                if (instruccionesDAO.eliminarUsuario(idEliminar)) {
                    JOptionPane.showMessageDialog(ventanaGeneral, "Se eliminó permanentemente a el usuario."+ ventanaGeneral.getTxtNombre().getText());
                    mostrarUsuariosTabla();
                    ventanaGeneral.getTxtNombre().setText(""); 
                    ventanaGeneral.getTxtContrasena().setText("");
                }
            }        
        } else {
            JOptionPane.showMessageDialog(ventanaGeneral, "La contraseña ingresada no es correcta, Acceso denegado, intentelo de nuevo.");
        }
    }

    public void mostrarUsuariosTabla() {
        DefaultTableModel tablaDatosUsuario = (DefaultTableModel) ventanaGeneral.getTblUsuarios().getModel();
        tablaDatosUsuario.setRowCount(0);
        List<DatosUsuario> listaUsuariosRecibida = instruccionesDAO.listarUsuarios();
        Object[] filaTablaNueva = new Object[3];
        for (DatosUsuario usuarioExtraido : listaUsuariosRecibida) {
            filaTablaNueva[0] = usuarioExtraido.getId();
            filaTablaNueva[1] = usuarioExtraido.getNombreU();
            filaTablaNueva[2] = usuarioExtraido.getContrasena();
            tablaDatosUsuario.addRow(filaTablaNueva);
        }
        ventanaGeneral.getTblUsuarios().setModel(tablaDatosUsuario);
    }

    public void seleccionarUsuarioDeTabla() {
        int filaSeleccionada = ventanaGeneral.getTblUsuarios().getSelectedRow();
        if (filaSeleccionada >= 0) {
            ventanaGeneral.getTxtID().setText(ventanaGeneral.getTblUsuarios().getValueAt(filaSeleccionada, 0).toString());
            ventanaGeneral.getTxtNombre().setText(ventanaGeneral.getTblUsuarios().getValueAt(filaSeleccionada, 1).toString());
            ventanaGeneral.getTxtContrasena().setText(ventanaGeneral.getTblUsuarios().getValueAt(filaSeleccionada, 2).toString());
        }
    }

    
    
}
