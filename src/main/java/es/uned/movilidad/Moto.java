package es.uned.movilidad;

/**
 * Clase que representa una moto.
 * Hereda de la clase {@link Vehiculo}.
 */
public class Moto extends Vehiculo {

    public static final double CONSUMO_MOTO_PEQUENA = 0.4; // Consumo de una moto pequeña
    public static final double CONSUMO_MOTO_GRANDE = 0.25; // Consumo de una moto grande

    private TipoMoto tipo; // Tipo de moto (grande o pequeña)
    private Integer coordenadaX; // Coordenada X de la ubicación de la moto
    private Integer coordenadaY; // Coordenada Y de la ubicación de la moto

    /**
     * Constructor de la clase Moto.
     *
     * @param matricula Matrícula de la moto.
     * @param marca Marca de la moto.
     * @param modelo Modelo de la moto.
     * @param base Base donde se encuentra la moto.
     * @param tipo Tipo de moto (grande o pequeña).
     * @param coordenadaX Coordenada X de la ubicación de la moto.
     * @param coordenadaY Coordenada Y de la ubicación de la moto.
     */
    public Moto(String matricula, String marca, String modelo, Base base, TipoMoto tipo, Integer coordenadaX, Integer coordenadaY) {
        super(matricula, marca, modelo, base);
        this.tipo = tipo;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
    }

    /**
     * Obtiene el tipo de moto.
     *
     * @return Tipo de moto.
     */
    public TipoMoto getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de moto.
     *
     * @param tipo Tipo de moto.
     */
    public void setTipo(TipoMoto tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la coordenada X de la ubicación de la moto.
     *
     * @return Coordenada X de la moto.
     */
    public Integer getCoordenadaX() {
        return coordenadaX;
    }

    /**
     * Establece la coordenada X de la ubicación de la moto.
     *
     * @param coordenadaX Coordenada X de la moto.
     */
    public void setCoordenadaX(Integer coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    /**
     * Obtiene la coordenada Y de la ubicación de la moto.
     *
     * @return Coordenada Y de la moto.
     */
    public Integer getCoordenadaY() {
        return coordenadaY;
    }

    /**
     * Establece la coordenada Y de la ubicación de la moto.
     *
     * @param coordenadaY Coordenada Y de la moto.
     */
    public void setCoordenadaY(Integer coordenadaY) {
        this.coordenadaY = coordenadaY;
    }

    /**
     * Devuelve una representación en forma de cadena de la moto.
     *
     * @return Representación en forma de cadena de la moto.
     */
    public String toString() {
        return "Moto {" +
                "matricula='" + this.getMatricula() + '\'' +
                ", tipo='" + this.getTipo().name() + '\'' +
                ", marca='" + this.getMarca() + '\'' +
                ", modelo='" + this.getModelo() + '\'' +
                ", bateria=" + this.getBateria() + "%" +
                ", alquilado=" + this.isAlquilado() +
                ", reservado=" + this.isReservado() +
                ", fechaReserva=" + this.getFechaReserva() +
                ", periodoInactividadInicio=" + this.getPeriodoInactividadInicio() +
                ", periodoInactividadFin=" + this.getPeriodoInactividadFin() +
                ", avisos=" + this.getAvisos().size() +
                ", coordenadas [" + this.getCoordenadaX() + "," + this.getCoordenadaY() + "]" +
                '}';
    }

}