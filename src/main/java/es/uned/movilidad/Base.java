package es.uned.movilidad;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una base para bicicletas y patinetes.
 */
public class Base {
    private final String nombre; // Nombre de la base
    private final Integer coordenadaX; // Coordenada X de la base
    private final Integer coordenadaY; // Coordenada Y de la base
    private Integer plazas; // Número de plazas disponibles en la base
    private LocalDate periodoInactividadInicio; // Inicio del periodo de inactividad de la base
    private LocalDate periodoInactividadFin; // Fin del periodo de inactividad de la base
    private Integer contadorDeUsos; // Contador de usos de la base

    private List<String> avisos; // Lista de avisos asociados a la base

    private List<Reparacion> reparaciones; // Lista de reparaciones realizadas en la base

    /**
     * Constructor de la clase Base.
     *
     * @param nombre Nombre de la base.
     * @param coordenadaX Coordenada X de la base.
     * @param coordenadaY Coordenada Y de la base.
     * @param plazas Número de plazas disponibles en la base.
     */
    public Base(String nombre, Integer coordenadaX, Integer coordenadaY, Integer plazas) {
        this.nombre = nombre;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.plazas = plazas;
        this.avisos = new ArrayList<>();
        this.periodoInactividadInicio = null;
        this.periodoInactividadFin = null;
        this.contadorDeUsos = 0;
        this.reparaciones = new ArrayList<>();
    }

    /**
     * Obtiene el nombre de la base.
     *
     * @return Nombre de la base.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la coordenada X de la base.
     *
     * @return Coordenada X de la base.
     */
    public Integer getCoordenadaX() {
        return coordenadaX;
    }

    /**
     * Obtiene la coordenada Y de la base.
     *
     * @return Coordenada Y de la base.
     */
    public Integer getCoordenadaY() {
        return coordenadaY;
    }

    /**
     * Obtiene el número de plazas disponibles en la base.
     *
     * @return Número de plazas disponibles.
     */
    public Integer getPlazas() {
        return plazas;
    }

    /**
     * Establece el número de plazas disponibles en la base.
     *
     * @param plazas Número de plazas disponibles.
     */
    public void setPlazas(Integer plazas) {
        this.plazas = plazas;
    }

    /**
     * Obtiene la lista de avisos asociados a la base.
     *
     * @return Lista de avisos.
     */
    public List<String> getAvisos() {
        return avisos;
    }

    /**
     * Agrega un aviso a la lista de avisos de la base.
     *
     * @param aviso Aviso a agregar.
     */
    public void addAviso(String aviso) {
        avisos.add(aviso);
    }

    /**
     * Obtiene el inicio del periodo de inactividad de la base.
     *
     * @return Inicio del periodo de inactividad.
     */
    public LocalDate getPeriodoInactividadInicio() {
        return periodoInactividadInicio;
    }

    /**
     * Establece el inicio del periodo de inactividad de la base.
     *
     * @param periodoInactividadInicio Inicio del periodo de inactividad.
     */
    public void setPeriodoInactividadInicio(LocalDate periodoInactividadInicio) {
        this.periodoInactividadInicio = periodoInactividadInicio;
    }

    /**
     * Obtiene el fin del periodo de inactividad de la base.
     *
     * @return Fin del periodo de inactividad.
     */
    public LocalDate getPeriodoInactividadFin() {
        return periodoInactividadFin;
    }

    /**
     * Establece el fin del periodo de inactividad de la base.
     *
     * @param periodoInactividadFin Fin del periodo de inactividad.
     */
    public void setPeriodoInactividadFin(LocalDate periodoInactividadFin) {
        this.periodoInactividadFin = periodoInactividadFin;
    }

    /**
     * Obtiene el contador de usos de la base.
     *
     * @return Contador de usos.
     */
    public Integer getContadorDeUsos() {
        return contadorDeUsos;
    }

    /**
     * Establece el contador de usos de la base.
     *
     * @param contadorDeUsos Contador de usos.
     */
    public void setContadorDeUsos(Integer contadorDeUsos) {
        this.contadorDeUsos = contadorDeUsos;
    }

    /**
     * Obtiene la lista de reparaciones realizadas en la base.
     *
     * @return Lista de reparaciones.
     */
    public List<Reparacion> getReparaciones() {
        return reparaciones;
    }

    /**
     * Agrega una reparación a la lista de reparaciones de la base.
     *
     * @param reparacion Reparación a agregar.
     */
    public void addReparacion(Reparacion reparacion) {
        this.reparaciones.add(reparacion);
    }

    /**
     * Limpia la lista de avisos de la base.
     */
    public void limpiarAvisos() {
        this.avisos.clear();
    }
}