package es.uned.movilidad;

/**
 * Clase que representa un patinete.
 * Hereda de la clase {@link Vehiculo}.
 */
public class Patinete extends Vehiculo {

    public static final double CONSUMO_PATINETE = 0.5; // Consumo de un patinete

    /**
     * Constructor de la clase Patinete.
     *
     * @param matricula Matrícula del patinete.
     * @param marca Marca del patinete.
     * @param modelo Modelo del patinete.
     * @param base Base donde se encuentra el patinete.
     */
    public Patinete(String matricula, String marca, String modelo, Base base) {
        super(matricula, marca, modelo, base);
    }

    /**
     * Devuelve una representación en forma de cadena del patinete.
     *
     * @return Representación en forma de cadena del patinete.
     */
    public String toString() {
        return "Patinete {" +
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