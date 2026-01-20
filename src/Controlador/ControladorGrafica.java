package Controlador;

import Modelo.DatosLogs;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ControladorGrafica extends JFrame {

    private Map<String, int[]> datosPorID;
    private int maxCantidad = 0;
    private int cantidadInvalidos = 0;

    public ControladorGrafica(List<DatosLogs> listaLogs) {
        this.setTitle("Grafica por Id");
        this.setSize(900, 600);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.datosPorID = new HashMap<>();
        procesarDatos(listaLogs);
        
        this.add(new PanelGrafico());
        
        this.setVisible(true);
    }

    private void procesarDatos(List<DatosLogs> listaLogs) {
        for (DatosLogs log : listaLogs) {
            
            if (log.getIdUsuario() == 0) {
                cantidadInvalidos++;
                if (cantidadInvalidos > maxCantidad) maxCantidad = cantidadInvalidos;
            }
            else {
                String claveId = String.valueOf(log.getIdUsuario());
                int[] contadores = datosPorID.getOrDefault(claveId, new int[]{0, 0});
                
                if ("Exitoso".equalsIgnoreCase(log.getEstadoLog())) {
                    contadores[0]++;
                } else {
                    contadores[1]++;
                }
                
                maxCantidad = Math.max(maxCantidad, Math.max(contadores[0], contadores[1]));
                datosPorID.put(claveId, contadores);
            }
        }
    }

    private class PanelGrafico extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int ancho = getWidth();
            int alto = getHeight();
            int margenInferior = 50;
            int margenIzquierdo = 50;
            
            g2.drawLine(margenIzquierdo, alto - margenInferior, ancho - 50, alto - margenInferior);
            g2.drawLine(margenIzquierdo, alto - margenInferior, margenIzquierdo, 50);

            if (datosPorID.isEmpty() && cantidadInvalidos == 0) {
                g2.drawString("No hay datos para mostrar", 100, 100);
                return;
            }

            int numeroGrupos = datosPorID.size() + (cantidadInvalidos > 0 ? 1 : 0);
            int espacioDisponible = ancho - 100;
            int anchoGrupo = espacioDisponible / Math.max(1, numeroGrupos);
            int anchoBarra = Math.min(40, (anchoGrupo - 20) / 2);
            
            int xActual = margenIzquierdo + 20;

            for (Map.Entry<String, int[]> entrada : datosPorID.entrySet()) {
                String idUsuario = entrada.getKey();
                int correctos = entrada.getValue()[0];
                int incorrectos = entrada.getValue()[1];

                dibujarBarra(g2, xActual, alto - margenInferior, anchoBarra, correctos, new Color(46, 204, 113));
                
                dibujarBarra(g2, xActual + anchoBarra, alto - margenInferior, anchoBarra, incorrectos, new Color(231, 76, 60));

                g2.setColor(Color.BLACK);
                g2.drawString("ID: " + idUsuario, xActual, alto - margenInferior + 20);

                xActual += anchoGrupo;
            }

            if (cantidadInvalidos > 0) {
                int xInvalido = xActual + (anchoBarra / 2);
                dibujarBarra(g2, xInvalido, alto - margenInferior, anchoBarra, cantidadInvalidos, new Color(192, 57, 43));
                
                g2.setColor(Color.BLACK);
                g2.drawString("Id Inválido", xInvalido - 10, alto - margenInferior + 20);
            }
            
            dibujarLeyenda(g2);
        }

        private void dibujarBarra(Graphics2D g2, int x, int yBase, int ancho, int valor, Color color) {
            if (valor == 0) return;
            int alturaBarra = (int) ((double) valor / maxCantidad * (getHeight() - 150));
            
            g2.setColor(color);
            g2.fillRect(x, yBase - alturaBarra, ancho, alturaBarra);
            g2.setColor(Color.BLACK);
            g2.drawRect(x, yBase - alturaBarra, ancho, alturaBarra);
            
            g2.drawString(String.valueOf(valor), x + (ancho/2) - 3, yBase - alturaBarra - 5);
        }

        private void dibujarLeyenda(Graphics2D g2) {
            int x = getWidth() - 150;
            int y = 50;
            
            g2.setColor(new Color(46, 204, 113));
            g2.fillRect(x, y, 15, 15);
            g2.setColor(Color.BLACK);
            g2.drawString("Correctos", x + 20, y + 12);
            
            g2.setColor(new Color(231, 76, 60));
            g2.fillRect(x, y + 25, 15, 15);
            g2.setColor(Color.BLACK);
            g2.drawString("Incorrectos", x + 20, y + 37);
        }
    }
}