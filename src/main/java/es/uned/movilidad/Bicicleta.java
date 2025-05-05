package es.uned.movilidad;

/**
 * Clase que representa una bicicleta.
 * Hereda de la clase {@link Vehiculo}.
 */
public class Bicicleta extends Vehiculo {

    public static final double CONSUMO_BICICLETA = 1; // Consumo de una bicicleta

    /**
     * Constructor de la clase Bicicleta.
     *
     * @param matricula Matrícula de la bicicleta.
     * @param marca Marca de la bicicleta.
     * @param modelo Modelo de la bicicleta.
     * @param base Base donde se encuentra la bicicleta.
     */
    public Bicicleta(String matricula, String marca, String modelo, Base base) {
        super(matricula, marca, modelo, base);
    }

    /**
     * Devuelve una representación en forma de cadena de la bicicleta.
     *
     * @return Representación en forma de cadena de la bicicleta.
     */
    public String toString() {
        return "Bicicleta {" +
                "matricula='" + this.getMatricula() + '\'' +
                ", marca='" + this.getMarca() + '\'' +
                ", modelo='" + this.getModelo() + '\'' +
                ", bateria=" + this.getBateria() + "%" +
                ", alquilado=" + this.isAlquilado() +
                ", reservado=" + this.isReservado() +
                ", fechaReserva=" + this.getFechaReserva() +
                ", periodoInactividadInicio=" + this.getPeriodoInactividadInicio() +
                ", periodoInactividadFin=" + this.getPeriodoInactividadFin() +
                ", avisos=" + this.getAvisos().size() +
                ", base=" + this.getBase().getNombre() +
                '}';
    }
}