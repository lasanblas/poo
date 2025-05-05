package es.uned.movilidad;

import java.time.LocalDate;

/**
 * Clase que representa una reparación realizada a un vehículo en una base.
 */
public class Reparacion {

    private final Vehiculo vehiculo; // Vehículo que ha sido reparado
    private final Base base; // Base donde se realizó la reparación
    private final Double importe; // Importe de la reparación
    private final LocalDate fechaReparacion; // Fecha en la que se realizó la reparación

    /**
     * Constructor de la clase Reparacion.
     *
     * @param vehiculo Vehículo que ha sido reparado.
     * @param base Base donde se realizó la reparación.
     * @param importe Importe de la reparación.
     * @param fechaReparacion Fecha en la que se realizó la reparación.
     */
    public Reparacion(Vehiculo vehiculo, Base base, Double importe, LocalDate fechaReparacion) {
        this.vehiculo = vehiculo;
        this.base = base;
        this.importe = importe;
        this.fechaReparacion = fechaReparacion;
    }

    /**
     * Obtiene el vehículo que ha sido reparado.
     *
     * @return Vehículo reparado.
     */
    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    /**
     * Obtiene la base donde se realizó la reparación.
     *
     * @return Base de la reparación.
     */
    public Base getBase() {
        return base;
    }

    /**
     * Obtiene el importe de la reparación.
     *
     * @return Importe de la reparación.
     */
    public Double getImporte() {
        return importe;
    }

    /**
     * Obtiene la fecha en la que se realizó la reparación.
     *
     * @return Fecha de la reparación.
     */
    public LocalDate getFechaReparacion() {
        return fechaReparacion;
    }

    /**
     * Genera una factura para el vehículo reparado.
     *
     * @return Cadena que representa la factura del vehículo.
     */
    public String imprimirFacturaVehiculo() {
        return "Factura:\n" +
                "Vehículo: " + vehiculo.getMatricula() + "\n" +
                "Importe: " + importe + "€\n";
    }

    /**
     * Genera una factura para la base donde se realizó la reparación.
     *
     * @return Cadena que representa la factura de la base.
     */
    public String imprimirFacturaBase() {
        return "Factura:\n" +
                "Base: " + base.getNombre() + "\n" +
                "Importe: " + importe + "€\n";
    }
}