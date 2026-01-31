package Vista;

import Controlador.ControladorS;
import Modelo.DatosUsuario;
import Modelo.traspasoDAO;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class VentanaGeneralP extends javax.swing.JFrame {

    private ControladorS controlador;
    private traspasoDAO instruccionesDAO;
    private DatosUsuario usuarioUsado;

    public VentanaGeneralP(traspasoDAO instruccionesDAO, DatosUsuario usuarioUsado) {
        initComponents();
        this.instruccionesDAO = instruccionesDAO;
        this.usuarioUsado = usuarioUsado;

        this.controlador = new ControladorS(this, instruccionesDAO, usuarioUsado);
        
        this.setTitle("S.I.V.A.R - Sistema de Vigilancia y Acceso Remoto");
        this.setResizable(false);

        this.setSize(1000, 560); 
        this.setLocationRelativeTo(null);

        actualizarSiguienteID();

        boxPuertosArduino.removeAllItems();
        String[] puertos = controlador.obtenerPuertosDisponibles();
        if (puertos.length == 0) {
            boxPuertosArduino.addItem("Sin puertos disponibles");
        }
        for (String puerto : puertos) {
            boxPuertosArduino.addItem(puerto);
        }

        tblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuariosMouseClicked(evt);
            }
        });
    }

    public void actualizarSiguienteID() {
        int maxId = 0;
        for (int i = 0; i < tblUsuarios.getRowCount(); i++) {
            Object valor = tblUsuarios.getValueAt(i, 0);
            if (valor != null) {
                try {
                    int id = Integer.parseInt(valor.toString());
                    if (id > maxId) {
                        maxId = id;
                    }
                } catch (NumberFormatException e) {
                }
            }
        }
        txtID.setText(String.valueOf(maxId + 1));
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        pnlPestañas1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        lblContra = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        lblID = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblNombre = new javax.swing.JLabel();
        btnNuevoUsuario = new javax.swing.JButton();
        btnCambiarContrasena = new javax.swing.JButton();
        btnEliminarUsuario = new javax.swing.JButton();
        txtContrasena = new javax.swing.JPasswordField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDatosLogs = new javax.swing.JTable();
        txtFechaInicio = new javax.swing.JFormattedTextField();
        btnFiltrarFecha = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtFechaFin = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtHoraFin = new javax.swing.JFormattedTextField();
        txtHoraInicio = new javax.swing.JFormattedTextField();
        btnGraficarResultados = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        boxPuertosArduino = new javax.swing.JComboBox<>();
        btnConectarArduino = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAreaArduino = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 247, 250));

        pnlPestañas1.setBackground(new Color(245, 247, 250));
        pnlPestañas1.setFont(new Font("Segoe UI", Font.BOLD, 13));
        pnlPestañas1.setOpaque(true);
        pnlPestañas1.setBorder(new EmptyBorder(6, 6, 6, 6));

        jPanel4.setBackground(new Color(245, 247, 250));
        jPanel4.setOpaque(true);

        jPanel5.setBackground(Color.WHITE);
        jPanel5.setOpaque(true);
        jPanel5.setBorder(new CompoundBorder(
            new LineBorder(new Color(220, 225, 231), 1, true),
            new EmptyBorder(12, 12, 12, 12)
        ));

        lblContra.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblContra.setText("Contraseña:");

        txtID.setEditable(false);
        txtID.setBackground(Color.WHITE); // Fondo blanco
        txtID.setForeground(new Color(17, 24, 39));
        txtID.setFont(new Font("Segoe UI", Font.BOLD, 13)); // ID en negrita
        txtID.setBorder(new CompoundBorder(
            new LineBorder(new Color(210, 215, 221), 1, true),
            new EmptyBorder(6, 10, 6, 10)
        ));
        txtID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDActionPerformed(evt);
            }
        });

        lblID.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblID.setText("ID:");

        txtNombre.setBackground(Color.WHITE);
        txtNombre.setForeground(new Color(17, 24, 39));
        txtNombre.setBorder(new CompoundBorder(
            new LineBorder(new Color(210, 215, 221), 1, true),
            new EmptyBorder(6, 10, 6, 10)
        ));
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNombre.setText("Nombre:");
        
        btnNuevoUsuario.setBackground(new Color(37, 99, 235));
        btnNuevoUsuario.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnNuevoUsuario.setForeground(Color.WHITE);
        btnNuevoUsuario.setText("Guardar Nuevo Usuario");
        // Aquí aumenté el grosor a 2 y oscurecí más el color del borde
        btnNuevoUsuario.setBorder(new CompoundBorder(
            new LineBorder(new Color(20, 80, 200), 2, true), 
            new EmptyBorder(10, 14, 10, 14)
        ));
        btnNuevoUsuario.setFocusPainted(false);
        btnNuevoUsuario.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNuevoUsuario.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnNuevoUsuario.setBackground(new Color(37, 99, 235).brighter()); }
            public void mouseExited(MouseEvent e) { btnNuevoUsuario.setBackground(new Color(37, 99, 235)); }
        });
        btnNuevoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoUsuarioActionPerformed(evt);
            }
        });

        btnCambiarContrasena.setBackground(new Color(100, 116, 139));
        btnCambiarContrasena.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnCambiarContrasena.setForeground(Color.WHITE);
        btnCambiarContrasena.setText("Modificar Contraseña");
        btnCambiarContrasena.setBorder(new CompoundBorder(
            new LineBorder(new Color(80, 96, 120), 2, true),
            new EmptyBorder(10, 14, 10, 14)
        ));
        btnCambiarContrasena.setFocusPainted(false);
        btnCambiarContrasena.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCambiarContrasena.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnCambiarContrasena.setBackground(new Color(100, 116, 139).brighter()); }
            public void mouseExited(MouseEvent e) { btnCambiarContrasena.setBackground(new Color(100, 116, 139)); }
        });
        btnCambiarContrasena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCambiarContrasenaActionPerformed(evt);
            }
        });

        btnEliminarUsuario.setBackground(new Color(220, 38, 38));
        btnEliminarUsuario.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnEliminarUsuario.setForeground(Color.WHITE);
        btnEliminarUsuario.setText("Eliminar Usuario");
        btnEliminarUsuario.setBorder(new CompoundBorder(
            new LineBorder(new Color(180, 30, 30), 2, true),
            new EmptyBorder(10, 14, 10, 14)
        ));
        btnEliminarUsuario.setFocusPainted(false);
        btnEliminarUsuario.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEliminarUsuario.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnEliminarUsuario.setBackground(new Color(220, 38, 38).brighter()); }
            public void mouseExited(MouseEvent e) { btnEliminarUsuario.setBackground(new Color(220, 38, 38)); }
        });
        btnEliminarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarUsuarioActionPerformed(evt);
            }
        });

        txtContrasena.setBackground(Color.WHITE);
        txtContrasena.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtContrasena.setForeground(new Color(17, 24, 39));
        txtContrasena.setBorder(new CompoundBorder(
            new LineBorder(new Color(210, 215, 221), 1, true),
            new EmptyBorder(6, 10, 6, 10)
        ));
        txtContrasena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtContrasenaActionPerformed(evt);
            }
        });
        txtContrasena.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtContrasenaKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblID, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnEliminarUsuario, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCambiarContrasena, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNuevoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 180, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblContra, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(txtID)
                            .addComponent(txtContrasena))))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblID, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblContra, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txtContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addComponent(btnNuevoUsuario)
                .addGap(18, 18, 18)
                .addComponent(btnCambiarContrasena)
                .addGap(18, 18, 18)
                .addComponent(btnEliminarUsuario))
        );

        jScrollPane1.setBackground(Color.WHITE);
        jScrollPane1.setOpaque(true);
        jScrollPane1.getViewport().setBackground(Color.WHITE);
        jScrollPane1.setBorder(new CompoundBorder(
            new EmptyBorder(10, 10, 10, 10),
            BorderFactory.createTitledBorder(
                new LineBorder(new Color(220, 225, 231), 1, true),
                " Usuarios Registrados ",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14),
                new Color(55, 65, 81)
            )
        ));

        tblUsuarios.setRowHeight(28);
        tblUsuarios.setShowHorizontalLines(false);
        tblUsuarios.setShowVerticalLines(false);
        tblUsuarios.setGridColor(new Color(230, 233, 237));
        tblUsuarios.setSelectionBackground(new Color(220, 235, 252));
        tblUsuarios.setSelectionForeground(new Color(17, 24, 39));
        tblUsuarios.getTableHeader().setReorderingAllowed(false);
        tblUsuarios.getTableHeader().setResizingAllowed(false);
        tblUsuarios.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblUsuarios.getTableHeader().setBackground(new Color(30, 41, 59));
        tblUsuarios.getTableHeader().setForeground(Color.WHITE);
        tblUsuarios.getTableHeader().setPreferredSize(new Dimension(tblUsuarios.getTableHeader().getWidth(), 30));

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] { "ID Usuario", "Nombre", "Contraseña" }
        ) {
            Class[] types = new Class [] { java.lang.Integer.class, java.lang.String.class, java.lang.String.class };
            boolean[] canEdit = new boolean [] { false, false, false };
            public Class getColumnClass(int columnIndex) { return types [columnIndex]; }
            public boolean isCellEditable(int rowIndex, int columnIndex) { return canEdit [columnIndex]; }
        });
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 250, 252));
                }
                setBorder(new EmptyBorder(0, 10, 0, 10));
                setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        };
        for(int i=0; i<tblUsuarios.getColumnCount(); i++) tblUsuarios.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);

        DefaultTableCellRenderer passwordRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                String texto = "";
                if(value != null) for(int i=0; i<value.toString().length(); i++) texto += "•";
                Component c = super.getTableCellRendererComponent(table, texto, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 250, 252));
                }
                setBorder(new EmptyBorder(0, 10, 0, 10));
                setHorizontalAlignment(JLabel.CENTER);
                return c;
            }
        };
        tblUsuarios.getColumnModel().getColumn(2).setCellRenderer(passwordRenderer);

        jScrollPane1.setViewportView(tblUsuarios);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlPestañas1.addTab("Gestión de Usuario", jPanel4);

        jPanel2.setBackground(new Color(245, 247, 250));
        jPanel2.setOpaque(true);

        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel1.setText("Hora de fin:");

        jScrollPane2.setBackground(Color.WHITE);
        jScrollPane2.setOpaque(true);
        jScrollPane2.getViewport().setBackground(Color.WHITE);
        jScrollPane2.setBorder(new CompoundBorder(
            new EmptyBorder(10, 10, 10, 10),
            BorderFactory.createTitledBorder(
                new LineBorder(new Color(220, 225, 231), 1, true),
                " Accesos ",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14),
                new Color(55, 65, 81)
            )
        ));

        tblDatosLogs.setRowHeight(28);
        tblDatosLogs.setShowHorizontalLines(false);
        tblDatosLogs.setShowVerticalLines(false);
        tblDatosLogs.setGridColor(new Color(230, 233, 237));
        tblDatosLogs.setSelectionBackground(new Color(220, 235, 252));
        tblDatosLogs.setSelectionForeground(new Color(17, 24, 39));
        tblDatosLogs.getTableHeader().setReorderingAllowed(false);
        tblDatosLogs.getTableHeader().setResizingAllowed(false);
        tblDatosLogs.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblDatosLogs.getTableHeader().setBackground(new Color(30, 41, 59));
        tblDatosLogs.getTableHeader().setForeground(Color.WHITE);
        tblDatosLogs.getTableHeader().setPreferredSize(new Dimension(tblDatosLogs.getTableHeader().getWidth(), 30));

        tblDatosLogs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] { "ID", "USUARIO", "FECHA", "HORA", "INTENTOS", "ESTADO" }
        ) {
            Class[] types = new Class [] { java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class };
            boolean[] canEdit = new boolean [] { false, false, false, false, false, false };
            public Class getColumnClass(int columnIndex) { return types [columnIndex]; }
            public boolean isCellEditable(int rowIndex, int columnIndex) { return canEdit [columnIndex]; }
        });
        
        for(int i=0; i<tblDatosLogs.getColumnCount(); i++) tblDatosLogs.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);

        jScrollPane2.setViewportView(tblDatosLogs);

        try {
            txtFechaInicio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####-##-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtFechaInicio.setBackground(Color.WHITE);
        txtFechaInicio.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtFechaInicio.setForeground(new Color(17, 24, 39));
        txtFechaInicio.setBorder(new CompoundBorder(
            new LineBorder(new Color(210, 215, 221), 1, true),
            new EmptyBorder(6, 10, 6, 10)
        ));
        txtFechaInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaInicioActionPerformed(evt);
            }
        });

        btnFiltrarFecha.setBackground(new Color(37, 99, 235));
        btnFiltrarFecha.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnFiltrarFecha.setForeground(Color.WHITE);
        btnFiltrarFecha.setText("Buscar Por Fecha");
        btnFiltrarFecha.setBorder(new CompoundBorder(
            new LineBorder(new Color(20, 80, 200), 2, true),
            new EmptyBorder(10, 14, 10, 14)
        ));
        btnFiltrarFecha.setFocusPainted(false);
        btnFiltrarFecha.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnFiltrarFecha.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnFiltrarFecha.setBackground(new Color(37, 99, 235).brighter()); }
            public void mouseExited(MouseEvent e) { btnFiltrarFecha.setBackground(new Color(37, 99, 235)); }
        });
        btnFiltrarFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltrarFechaActionPerformed(evt);
            }
        });

        jLabel3.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel3.setText("Fecha de fin:");

        try {
            txtFechaFin.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("####-##-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtFechaFin.setBackground(Color.WHITE);
        txtFechaFin.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtFechaFin.setForeground(new Color(17, 24, 39));
        txtFechaFin.setBorder(new CompoundBorder(
            new LineBorder(new Color(210, 215, 221), 1, true),
            new EmptyBorder(6, 10, 6, 10)
        ));
        txtFechaFin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaFinActionPerformed(evt);
            }
        });

        jLabel4.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel4.setText("Fecha de inicio:");

        jLabel5.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel5.setText("Hora de inicio:");

        try {
            txtHoraFin.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtHoraFin.setBackground(Color.WHITE);
        txtHoraFin.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtHoraFin.setForeground(new Color(17, 24, 39));
        txtHoraFin.setBorder(new CompoundBorder(
            new LineBorder(new Color(210, 215, 221), 1, true),
            new EmptyBorder(6, 10, 6, 10)
        ));
        txtHoraFin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHoraFinActionPerformed(evt);
            }
        });
        txtHoraFin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHoraFinKeyTyped(evt);
            }
        });

        try {
            txtHoraInicio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##:##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtHoraInicio.setBackground(Color.WHITE);
        txtHoraInicio.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtHoraInicio.setForeground(new Color(17, 24, 39));
        txtHoraInicio.setBorder(new CompoundBorder(
            new LineBorder(new Color(210, 215, 221), 1, true),
            new EmptyBorder(6, 10, 6, 10)
        ));
        txtHoraInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHoraInicioActionPerformed(evt);
            }
        });
        txtHoraInicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHoraInicioKeyTyped(evt);
            }
        });

        btnGraficarResultados.setBackground(new Color(100, 116, 139));
        btnGraficarResultados.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnGraficarResultados.setForeground(Color.WHITE);
        btnGraficarResultados.setText("Graficar Resultados");
        btnGraficarResultados.setBorder(new CompoundBorder(
            new LineBorder(new Color(80, 96, 120), 2, true),
            new EmptyBorder(10, 14, 10, 14)
        ));
        btnGraficarResultados.setFocusPainted(false);
        btnGraficarResultados.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGraficarResultados.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnGraficarResultados.setBackground(new Color(100, 116, 139).brighter()); }
            public void mouseExited(MouseEvent e) { btnGraficarResultados.setBackground(new Color(100, 116, 139)); }
        });

        btnGraficarResultados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnGraficarResultadosActionPerformed(evt);
        }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)

                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtHoraInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHoraFin, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(120)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGraficarResultados)
                    .addComponent(btnFiltrarFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtHoraInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnFiltrarFecha)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtHoraFin, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btnGraficarResultados)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlPestañas1.addTab("Reportes de Acceso", jPanel2);

        jPanel3.setBackground(new Color(245, 247, 250));
        jPanel3.setOpaque(true);

        jLabel2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel2.setText("Seleccionar Puerto COM:");

        boxPuertosArduino.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        boxPuertosArduino.setBackground(Color.WHITE);
        boxPuertosArduino.setBorder(new LineBorder(new Color(210, 215, 221), 1, true));
        boxPuertosArduino.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "COM1", "COM2", "COM3", "COM4", "COM5" }));
        boxPuertosArduino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxPuertosArduinoActionPerformed(evt);
            }
        });

        btnConectarArduino.setBackground(new Color(16, 185, 129));
        btnConectarArduino.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnConectarArduino.setForeground(Color.WHITE);
        btnConectarArduino.setText("CONECTAR ARDUINO");
        btnConectarArduino.setBorder(new CompoundBorder(
            new LineBorder(new Color(12, 140, 95), 2, true),
            new EmptyBorder(10, 14, 10, 14)
        ));
        btnConectarArduino.setFocusPainted(false);
        btnConectarArduino.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnConectarArduino.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnConectarArduino.setBackground(new Color(16, 185, 129).brighter()); }
            public void mouseExited(MouseEvent e) { btnConectarArduino.setBackground(new Color(16, 185, 129)); }
        });
        btnConectarArduino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConectarArduinoActionPerformed(evt);
            }
        });

        txtAreaArduino.setEditable(false);
        txtAreaArduino.setBackground(new Color(30, 41, 59));
        txtAreaArduino.setForeground(new Color(50, 255, 100));
        txtAreaArduino.setFont(new Font("Consolas", Font.PLAIN, 12));
        txtAreaArduino.setColumns(20);
        txtAreaArduino.setRows(5);
        jScrollPane3.setViewportView(txtAreaArduino);
        jScrollPane3.setOpaque(true);
        jScrollPane3.getViewport().setBackground(Color.WHITE);
        jScrollPane3.setBorder(new CompoundBorder(
                new EmptyBorder(10, 10, 10, 10),
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(220, 225, 231), 1, true),
                        " Monitor ",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("Segoe UI", Font.BOLD, 14),
                        new Color(55, 65, 81)
                )
        ));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel2)
                .addGap(29, 29, 29)
                .addComponent(boxPuertosArduino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                .addComponent(btnConectarArduino, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boxPuertosArduino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConectarArduino, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE))
        );

        pnlPestañas1.addTab("Monitor Del Sistema", jPanel3);

        String[] headers = {"Gestión de Usuario", "Reportes de Acceso", "Monitor del Sistema"};
        String[] icons = {"/Vista/img/user.png", "/Vista/img/report.png", "/Vista/img/arduino.png"};
        for(int i=0; i<headers.length; i++) {
            JPanel tab = new JPanel();
            tab.setOpaque(true);
            tab.setBackground(new Color(15, 23, 42));
            JLabel lbl = new JLabel(headers[i]);
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
            try {
                ImageIcon icon = new ImageIcon(getClass().getResource(icons[i]));
                Image img = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
                lbl.setIcon(new ImageIcon(img));
                lbl.setIconTextGap(8);
            } catch(Exception e){}
            tab.setBorder(new EmptyBorder(6, 12, 6, 12));
            tab.add(lbl);
            pnlPestañas1.setTabComponentAt(i, tab);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPestañas1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPestañas1)
        );
    }

    private void txtIDActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void txtFechaInicioActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void txtContrasenaKeyTyped(java.awt.event.KeyEvent evt) {
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }

    private void btnConectarArduinoActionPerformed(java.awt.event.ActionEvent evt) {
        if (boxPuertosArduino.getItemCount() > 0) {
            String puertoAsignadoArduino = boxPuertosArduino.getSelectedItem().toString();
            
            if (controlador.validarConexionArduino(puertoAsignadoArduino)) {
                javax.swing.JOptionPane.showMessageDialog(null, "El arduino se conectó correctamente en el puerto: " + puertoAsignadoArduino );
                btnConectarArduino.setText("ARDUINO CONECTADO");
                btnConectarArduino.setEnabled(false);
                
                txtAreaArduino.append("Sistema S.I.V.A.R conectado en " + puertoAsignadoArduino + "\n");
                txtAreaArduino.append("Esperando ingreso de ID en el teclado matricial\n");
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "No se pudo conectar al puerto de la computadora. Verifique el cable.");
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "No se detectaron puertos asignados al Arduino, Conectelo");
        }
    }

    private void txtFechaFinActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void txtHoraFinKeyTyped(java.awt.event.KeyEvent evt) {
        char d = evt.getKeyChar();
        if (!Character.isDigit(d)) {
            evt.consume();
        }
    }

    private void txtHoraInicioKeyTyped(java.awt.event.KeyEvent evt) {
    }

    private void txtContrasenaActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void btnNuevoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {    
        txtContrasena.setEditable(true);
        txtNombre.setEditable(true);                                          
        controlador.guardarNuevoUsuario();
        
        actualizarSiguienteID();
    }

    private void btnCambiarContrasenaActionPerformed(java.awt.event.ActionEvent evt) {
        controlador.modificarContraseña();
        txtContrasena.setEditable(true);
        txtNombre.setEditable(true);   
    }

    private void btnEliminarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {
        controlador.eliminarUsuario();
        txtContrasena.setEditable(true);
        txtNombre.setEditable(true);   
        
        actualizarSiguienteID();
    }

    private void txtHoraInicioActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void txtHoraFinActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void btnFiltrarFechaActionPerformed(java.awt.event.ActionEvent evt) {
        controlador.filtraListadoLogsPorFecha();
    }

    private void boxPuertosArduinoActionPerformed(java.awt.event.ActionEvent evt) {
    }  
    
    private void tblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {
        txtContrasena.setEditable(false);
        txtNombre.setEditable(false);
        controlador.seleccionarUsuarioDeTabla();    
    }

    private void btnGraficarResultadosActionPerformed(java.awt.event.ActionEvent evt) {                                                      
        controlador.graficarLogs();
    }

    public JTable getTblUsuarios() {
        return tblUsuarios;
    }

    public void setTblUsuarios(JTable tblUsuarios) {
        this.tblUsuarios = tblUsuarios;
    }

    public javax.swing.JPasswordField getTxtContrasena() {
        return txtContrasena;
    }

    public void setTxtContrasena(javax.swing.JPasswordField txtContrasena) {
        this.txtContrasena = txtContrasena;
    }

    public JTextField getTxtID() {
        return txtID;
    }

    public void setTxtID(JTextField txtID) {
        this.txtID = txtID;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

     public javax.swing.JFormattedTextField getTxtFechaFin() {
        return txtFechaFin;
    }

    public javax.swing.JFormattedTextField getTxtFechaInicio() {
        return txtFechaInicio;
    }

    public javax.swing.JFormattedTextField getTxtHoraFin() {
        return txtHoraFin;
    }

    public javax.swing.JFormattedTextField getTxtHoraInicio() {
        return txtHoraInicio;
    }

    public JTable getTblDatosLogs() {
        return tblDatosLogs;
    }

    private javax.swing.JComboBox<String> boxPuertosArduino;
    private javax.swing.JButton btnCambiarContrasena;
    private javax.swing.JButton btnConectarArduino;
    private javax.swing.JButton btnEliminarUsuario;
    private javax.swing.JButton btnFiltrarFecha;
    private javax.swing.JButton btnGraficarResultados;
    private javax.swing.JButton btnNuevoUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea txtAreaArduino;
    private javax.swing.JLabel lblContra;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JTabbedPane pnlPestañas1;
    private javax.swing.JTable tblDatosLogs;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JPasswordField txtContrasena;
    private javax.swing.JFormattedTextField txtFechaFin;
    private javax.swing.JFormattedTextField txtFechaInicio;
    private javax.swing.JFormattedTextField txtHoraFin;
    private javax.swing.JFormattedTextField txtHoraInicio;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtNombre;
}