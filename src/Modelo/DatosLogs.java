package Modelo;

public class DatosLogs {

    private int idLogActual;
    private int idUsuario;
    private String nombreUsuario;
    private String fechaLog;
    private String horaLog;
    private String estadoLog;
    private int intentoAct;

    public int getIdLogActual() {
        return idLogActual;
    }

    public void setIdLogActual(int idLogActual) {
        this.idLogActual = idLogActual;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getFechaLog() {
        return fechaLog;
    }

    public void setFechaLog(String fechaLog) {
        this.fechaLog = fechaLog;
    }

    public String getHoraLog() {
        return horaLog;
    }

    public void setHoraLog(String horaLog) {
        this.horaLog = horaLog;
    }

    public String getEstadoLog() {
        return estadoLog;
    }

    public void setEstadoLog(String estadoLog) {
        this.estadoLog = estadoLog;
    }

    public int getIntentoAct() {
        return intentoAct;
    }

    public void setIntentoAct(int intentoAct) {
        this.intentoAct = intentoAct;
    }

}
