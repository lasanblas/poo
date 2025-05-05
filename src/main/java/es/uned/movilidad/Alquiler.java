package es.uned.movilidad;

import java.util.Date;

/**
 * Clase que representa un alquiler de un vehículo.
 */
public class Alquiler {
    private final Vehiculo vehiculo; // Vehículo alquilado
    private final Base baseInicial; // Base inicial del alquiler
    private Base baseFinal; // Base final del alquiler
    private final Integer coordenadaXInicial; // Coordenada X inicial del alquiler
    private final Integer coordenadaYInicial; // Coordenada Y inicial del alquiler
    private Integer coordenadaXFinal; // Coordenada X final del alquiler
    private Integer coordenadaYFinal; // Coordenada Y final del alquiler
    private final Tarifa tarifa; // Tarifa aplicada al alquiler
    private final Usuario usuario; // Usuario que realiza el alquiler
    private Date fechaInicio; // Fecha y hora de inicio del alquiler
    private Date fechaFin; // Fecha y hora de finalización del alquiler
    private Double importe; // Importe total del alquiler

    /**
     * Constructor de la clase Alquiler.
     *
     * @param vehiculo Vehículo alquilado.
     * @param baseInicial Base inicial del alquiler.
     * @param baseFinal Base final del alquiler.
     * @param coordenadaXInicial Coordenada X inicial.
     * @param coordenadaYInicial Coordenada Y inicial.
     * @param coordenadaXFinal Coordenada X final.
     * @param coordenadaYFinal Coordenada Y final.
     * @param tarifa Tarifa aplicada.
     * @param usuario Usuario que realiza el alquiler.
     * @param fechaInicio Fecha y hora de inicio del alquiler.
     */
    public Alquiler(Vehiculo vehiculo, Base baseInicial, Base baseFinal, Integer coordenadaXInicial, Integer coordenadaYInicial, Integer coordenadaXFinal, Integer coordenadaYFinal, Tarifa tarifa, Usuario usuario, Date fechaInicio) {
        this.vehiculo = vehiculo;
        this.baseInicial = baseInicial;
        this.baseFinal = baseFinal;
        this.coordenadaXInicial = coordenadaXInicial;
        this.coordenadaYInicial = coordenadaYInicial;
        this.coordenadaXFinal = coordenadaXFinal;
        this.coordenadaYFinal = coordenadaYFinal;
        this.tarifa = tarifa;
        this.usuario = usuario;
        this.fechaInicio = fechaInicio;
    }

    /**
     * Obtiene el vehículo alquilado.
     *
     * @return Vehículo alquilado.
     */
    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    /**
     * Obtiene la tarifa aplicada al alquiler.
     *
     * @return Tarifa aplicada.
     */
    public Tarifa getTarifa() {
        return tarifa;
    }

    /**
     * Obtiene el usuario que realiza el alquiler.
     *
     * @return Usuario que realiza el alquiler.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Obtiene la fecha y hora de inicio del alquiler.
     *
     * @return Fecha y hora de inicio.
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Establece la fecha y hora de inicio del alquiler.
     *
     * @param fechaInicio Fecha y hora de inicio.
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Obtiene la fecha y hora de finalización del alquiler.
     *
     * @return Fecha y hora de finalización.
     */
    public Date getFechaFin() {
        return fechaFin;
    }


    /**
     * Obtiene la base inicial del alquiler.
     *
     * @return Base inicial.
     */
    public Base getBaseInicial() {
        return baseInicial;
    }

    /**
     * Obtiene la base final del alquiler.
     *
     * @return Base final.
     */
    public Base getBaseFinal() {
        return baseFinal;
    }

    /**
     * Obtiene la coordenada X inicial del alquiler.
     *
     * @return Coordenada X inicial.
     */
    public Integer getCoordenadaXInicial() {
        return coordenadaXInicial;
    }

    /**
     * Obtiene la coordenada Y inicial del alquiler.
     *
     * @return Coordenada Y inicial.
     */
    public Integer getCoordenadaYInicial() {
        return coordenadaYInicial;
    }

    /**
     * Obtiene la coordenada X final del alquiler.
     *
     * @return Coordenada X final.
     */
    public Integer getCoordenadaXFinal() {
        return coordenadaXFinal;
    }

    /**
     * Obtiene la coordenada Y final del alquiler.
     *
     * @return Coordenada Y final.
     */
    public Integer getCoordenadaYFinal() {
        return coordenadaYFinal;
    }

    /**
     * Establece la fecha y hora de finalización del alquiler.
     *
     * @param fechaFin Fecha y hora de finalización.
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * Establece la base final del alquiler.
     *
     * @param baseFinal Base final.
     */
    public void setBaseFinal(Base baseFinal) {
        this.baseFinal = baseFinal;
    }

    /**
     * Establece la coordenada X final del alquiler.
     *
     * @param coordenadaXFinal Coordenada X final.
     */
    public void setCoordenadaXFinal(Integer coordenadaXFinal) {
        this.coordenadaXFinal = coordenadaXFinal;
    }

    /**
     * Establece la coordenada Y final del alquiler.
     *
     * @param coordenadaYFinal Coordenada Y final.
     */
    public void setCoordenadaYFinal(Integer coordenadaYFinal) {
        this.coordenadaYFinal = coordenadaYFinal;
    }

    /**
     * Obtiene el importe total del alquiler.
     *
     * @return Importe total.
     */
    public Double getImporte() {
        return importe;
    }

    /**
     * Establece el importe total del alquiler.
     *
     * @param importe Importe total.
     */
    public void setImporte(Double importe) {
        this.importe = importe;
    }

    /**
     * Calcula la duración del alquiler en minutos.
     *
     * @return Duración en minutos o null si el alquiler no ha finalizado.
     */
    public Long obtenerDuracionEnMinutos(){
        if(fechaFin == null){
            System.out.println("El alquiler no ha finalizado.");
            return null;
        }
        long diferencia = fechaFin.getTime() - fechaInicio.getTime();
        long segundos = diferencia / 1000;
        long minutos = segundos / 60;
        long horas = minutos / 60;
        long dias = horas / 24;
        //System.out.println("El alquiler ha durado " + dias + " días, " + horas % 24 + " horas, " + minutos % 60 + " minutos y " + segundos % 60 + " segundos.");
        return minutos;
    }

    /**
     * Calcula la duración total del alquiler en minutos.
     *
     * @return Duración total en minutos o null si el alquiler no ha finalizado.
     */
    public Long obtenerDuracionTotal(){
        if(fechaFin == null){
            System.out.println("El alquiler no ha finalizado.");
            return null;
        }
        long diferencia = fechaFin.getTime() - fechaInicio.getTime();
        long segundos = diferencia / 1000;
        long minutos = segundos / 60;
        long horas = minutos / 60;
        long dias = horas / 24;
        //System.out.println("El alquiler ha durado " + dias + " días, " + horas % 24 + " horas, " + minutos % 60 + " minutos y " + segundos % 60 + " segundos.");
        return minutos;
    }

}
