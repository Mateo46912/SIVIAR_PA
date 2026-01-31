package Vista;

import Modelo.DatosLogs;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VentanaGraficaLogs extends JFrame {

    private Map<String, int[]> datosProcesados;
    private int maxCantidad = 0;

    public VentanaGraficaLogs(List<DatosLogs> listaLogs) {
        this.setTitle("Estadísticas de Acceso");
        this.setSize(900, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.datosProcesados = new HashMap<>();
        procesarDatos(listaLogs);

        this.add(new PanelGrafico());
    }

    private void procesarDatos(List<DatosLogs> listaLogs) {
        datosProcesados.clear();
        maxCantidad = 0;

        for (DatosLogs log : listaLogs) {
            String claveEtiqueta;

            if (log.getIdUsuario() == 0 || "Desconocido".equalsIgnoreCase(log.getNombreUsuario())) {
                claveEtiqueta = "Sin ID";
            } else {
                claveEtiqueta = log.getNombreUsuario() + " (ID=" + log.getIdUsuario() + ")";
            }

            int[] contadores = datosProcesados.getOrDefault(claveEtiqueta, new int[]{0, 0});

            if ("Exitoso".equalsIgnoreCase(log.getEstadoLog())) {
                contadores[0]++;
            } else {
                contadores[1]++;
            }

            datosProcesados.put(claveEtiqueta, contadores);
        }

        for (int[] contadores : datosProcesados.values()) {
            int mayorDelGrupo = Math.max(contadores[0], contadores[1]);
            if (mayorDelGrupo > maxCantidad) {
                maxCantidad = mayorDelGrupo;
            }
        }

        if (maxCantidad < 6) maxCantidad = 6;
    }

    private class PanelGrafico extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, getWidth(), getHeight());

            int ancho = getWidth();
            int alto = getHeight();
            int margenIzq = 80;
            int margenInf = 60;
            int margenSup = 50;
            int margenDer = 50;

            int altoUtil = alto - margenInf - margenSup;
            int anchoUtil = ancho - margenIzq - margenDer;

            g2.setColor(Color.DARK_GRAY);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 20));
            String titulo = "GRÁFICA INTENTOS DE INGRESO";
            int anchoTxt = g2.getFontMetrics().stringWidth(titulo);
            g2.drawString(titulo, (ancho / 2) - (anchoTxt / 2), 30);

            g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));

            int salto = 1;
            if (maxCantidad > 20) salto = maxCantidad / 10;

            for (int i = 0; i <= maxCantidad; i += salto) {
                int yPos = (margenSup + altoUtil) - (int)((double)i / maxCantidad * altoUtil);

                g2.setColor(new Color(230, 230, 230));
                g2.drawLine(margenIzq, yPos, ancho - margenDer, yPos);

                g2.setColor(Color.BLACK);
                String numero = String.valueOf(i);
                int anchoNum = g2.getFontMetrics().stringWidth(numero);
                g2.drawString(numero, margenIzq - anchoNum - 10, yPos + 5);
            }

            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(2));
            g2.drawLine(margenIzq, margenSup, margenIzq, alto - margenInf);
            g2.drawLine(margenIzq, alto - margenInf, ancho - margenDer, alto - margenInf);

            if (datosProcesados.isEmpty()) {
                g2.drawString("No hay datos para mostrar", ancho / 2 - 80, alto / 2);
                return;
            }

            int totalGrupos = datosProcesados.size();
            int anchoGrupo = anchoUtil / Math.max(1, totalGrupos);
            int separacionEntreBarras = 10;
            int anchoBarra = Math.min(60, (anchoGrupo - 20 - separacionEntreBarras) / 2);

            int xActual = margenIzq + (anchoGrupo / 2) - anchoBarra - (separacionEntreBarras / 2);

            Color colorValido = new Color(46, 204, 113);
            Color colorInvalido = new Color(231, 76, 60);

            for (Map.Entry<String, int[]> entrada : datosProcesados.entrySet()) {
                String etiqueta = entrada.getKey();
                int validos = entrada.getValue()[0];
                int invalidos = entrada.getValue()[1];

                dibujarBarra(g2, xActual, alto - margenInf, anchoBarra, validos, colorValido, altoUtil);
                
                dibujarBarra(g2, xActual + anchoBarra + separacionEntreBarras, alto - margenInf, anchoBarra, invalidos, colorInvalido, altoUtil);

                g2.setColor(Color.BLACK);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
                int anchoEtiqueta = g2.getFontMetrics().stringWidth(etiqueta);

                int centroGrupo = xActual + anchoBarra + (separacionEntreBarras / 2);
                int xTexto = centroGrupo - (anchoEtiqueta / 2);
                
                g2.drawString(etiqueta, xTexto, alto - margenInf + 25);

                xActual += anchoGrupo;
            }

            dibujarLeyenda(g2, ancho, alto, colorValido, colorInvalido);
        }

        private void dibujarBarra(Graphics2D g2, int x, int yBase, int ancho, int valor, Color color, int alturaMax) {
            if (valor == 0) return;

            int alturaBarra = (int) ((double) valor / maxCantidad * alturaMax);

            g2.setColor(color);
            g2.fillRect(x, yBase - alturaBarra, ancho, alturaBarra);

            g2.setColor(color.darker());
            g2.drawRect(x, yBase - alturaBarra, ancho, alturaBarra);

            g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
            
            String textoNumero = String.valueOf(valor);
            FontMetrics fm = g2.getFontMetrics();
            
            int xTexto = x + (ancho - fm.stringWidth(textoNumero)) / 2;
            int yTexto = (yBase - alturaBarra) + (alturaBarra / 2) + (fm.getAscent() / 2) - 2;
            
            g2.drawString(textoNumero, xTexto, yTexto);
        }

        private void dibujarLeyenda(Graphics2D g2, int ancho, int alto, Color c1, Color c2) {
            int x = ancho - 200;
            int y = 50;

            g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));

            g2.setColor(c1);
            g2.fillRect(x, y, 15, 15);
            g2.setColor(Color.BLACK);
            g2.drawString("Válidos", x + 20, y + 12);

            g2.setColor(c2);
            g2.fillRect(x, y + 25, 15, 15);
            g2.setColor(Color.BLACK);
            g2.drawString("Inválidos", x + 20, y + 37);
        }
    }
}