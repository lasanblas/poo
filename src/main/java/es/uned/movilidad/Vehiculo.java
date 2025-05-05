package es.uned.movilidad;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase abstracta que representa un vehículo.
 */
public abstract class Vehiculo {

    private final String matricula; // Matrícula del vehículo
    private String marca; // Marca del vehículo
    private String modelo; // Modelo del vehículo
    private Integer bateria; // Nivel de batería del vehículo (en porcentaje)
    private boolean alquilado; // Indica si el vehículo está alquilado
    private boolean reservado; // Indica si el vehículo está reservado
    private Date fechaReserva; // Fecha de la reserva del vehículo
    private LocalDate periodoInactividadInicio; // Inicio del periodo de inactividad
    private LocalDate periodoInactividadFin; // Fin del periodo de inactividad
    private Long tiempoDeUso; // Tiempo total de uso del vehículo (en milisegundos)

    private List<String> avisos; // Lista de avisos asociados al vehículo

    private Base base; // Base donde se encuentra el vehículo

    private List<Reparacion> reparaciones; // Lista de reparaciones realizadas al vehículo

    /**
     * Constructor de la clase Vehiculo.
     *
     * @param matricula Matrícula del vehículo.
     * @param marca Marca del vehículo.
     * @param modelo Modelo del vehículo.
     * @param base Base donde se encuentra el vehículo.
     */
    public Vehiculo(String matricula, String marca, String modelo, Base base) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.bateria = 100;
        this.base = base;
        this.avisos = new ArrayList<>();
        this.alquilado = false;
        this.reservado = false;
        this.fechaReserva = null;
        this.periodoInactividadInicio = null;
        this.periodoInactividadFin = null;
        this.reparaciones = new ArrayList<>();
        this.tiempoDeUso = 0L;
    }

    /**
     * Obtiene la matrícula del vehículo.
     *
     * @return Matrícula del vehículo.
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * Obtiene la marca del vehículo.
     *
     * @return Marca del vehículo.
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Obtiene el modelo del vehículo.
     *
     * @return Modelo del vehículo.
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Establece la marca del vehículo.
     *
     * @param marca Marca del vehículo.
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Establece el modelo del vehículo.
     *
     * @param modelo Modelo del vehículo.
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Obtiene la lista de avisos asociados al vehículo.
     *
     * @return Lista de avisos.
     */
    public List<String> getAvisos() {
        return avisos;
    }

    /**
     * Obtiene el nivel de batería del vehículo.
     *
     * @return Nivel de batería (en porcentaje).
     */
    public Integer getBateria() {
        return bateria;
    }

    /**
     * Establece el nivel de batería del vehículo.
     *
     * @param bateria Nivel de batería (en porcentaje).
     */
    public void setBateria(Integer bateria) {
        this.bateria = bateria;
    }

    /**
     * Agrega un aviso a la lista de avisos del vehículo.
     *
     * @param aviso Aviso a agregar.
     */
    public void addAviso(String aviso) {
        avisos.add(aviso);
    }

    /**
     * Obtiene la base donde se encuentra el vehículo.
     *
     * @return Base del vehículo.
     */
    public Base getBase() {
        return base;
    }

    /**
     * Establece la base donde se encuentra el vehículo.
     *
     * @param base Base del vehículo.
     */
    public void setBase(Base base) {
        this.base = base;
    }

    /**
     * Indica si el vehículo está alquilado.
     *
     * @return true si está alquilado, false en caso contrario.
     */
    public boolean isAlquilado() {
        return alquilado;
    }

    /**
     * Establece si el vehículo está alquilado.
     *
     * @param alquilado true si está alquilado, false en caso contrario.
     */
    public void setAlquilado(boolean alquilado) {
        this.alquilado = alquilado;
    }

    /**
     * Indica si el vehículo está reservado.
     *
     * @return true si está reservado, false en caso contrario.
     */
    public boolean isReservado() {
        return reservado;
    }

    /**
     * Establece si el vehículo está reservado.
     *
     * @param reservado true si está reservado, false en caso contrario.
     */
    public void setReservado(boolean reservado) {
        this.reservado = reservado;
    }

    /**
     * Obtiene la fecha de reserva del vehículo.
     *
     * @return Fecha de reserva.
     */
    public Date getFechaReserva() {
        return fechaReserva;
    }

    /**
     * Establece la fecha de reserva del vehículo.
     *
     * @param fechaReserva Fecha de reserva.
     */
    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    /**
     * Obtiene el inicio del periodo de inactividad del vehículo.
     *
     * @return Inicio del periodo de inactividad.
     */
    public LocalDate getPeriodoInactividadInicio() {
        return periodoInactividadInicio;
    }

    /**
     * Establece el inicio del periodo de inactividad del vehículo.
     *
     * @param periodoInactividadInicio Inicio del periodo de inactividad.
     */
    public void setPeriodoInactividadInicio(LocalDate periodoInactividadInicio) {
        this.periodoInactividadInicio = periodoInactividadInicio;
    }

    /**
     * Obtiene el fin del periodo de inactividad del vehículo.
     *
     * @return Fin del periodo de inactividad.
     */
    public LocalDate getPeriodoInactividadFin() {
        return periodoInactividadFin;
    }

    /**
     * Establece el fin del periodo de inactividad del vehículo.
     *
     * @param periodoInactividadFin Fin del periodo de inactividad.
     */
    public void setPeriodoInactividadFin(LocalDate periodoInactividadFin) {
        this.periodoInactividadFin = periodoInactividadFin;
    }

    /**
     * Obtiene la lista de reparaciones realizadas al vehículo.
     *
     * @return Lista de reparaciones.
     */
    public List<Reparacion> getReparaciones() {
        return reparaciones;
    }

    /**
     * Agrega una reparación a la lista de reparaciones del vehículo.
     *
     * @param reparacion Reparación a agregar.
     */
    public void addReparacion(Reparacion reparacion) {
        this.reparaciones.add(reparacion);
    }

    /**
     * Obtiene el tiempo total de uso del vehículo.
     *
     * @return Tiempo total de uso (en milisegundos).
     */
    public Long getTiempoDeUso() {
        return tiempoDeUso;
    }

    /**
     * Establece el tiempo total de uso del vehículo.
     *
     * @param tiempoDeUso Tiempo total de uso (en milisegundos).
     */
    public void setTiempoDeUso(Long tiempoDeUso) {
        this.tiempoDeUso = tiempoDeUso;
    }

    /**
     * Limpia la lista de avisos del vehículo.
     */
    public void limpiarAvisos() {
        this.avisos.clear();
    }

    /**
     * Devuelve una representación en forma de cadena del vehículo.
     *
     * @return Representación en forma de cadena.
     */
    public String toString() {
        return "Vehiculo{" +
                "matricula='" + matricula + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", bateria=" + bateria +
                ", alquilado=" + alquilado +
                ", reservado=" + reservado +
                ", fechaReserva=" + fechaReserva +
                ", periodoInactividadInicio=" + periodoInactividadInicio +
                ", periodoInactividadFin=" + periodoInactividadFin +
                ", avisos=" + avisos +
                ", base=" + base.getNombre() +
                '}';
    }
}