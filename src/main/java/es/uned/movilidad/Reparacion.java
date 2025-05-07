package es.uned.movilidad;

import java.time.LocalDate;

/**
 * Clase que representa una reparación realizada a un vehículo en una base.
 */
public class Reparacion {

    private final Vehiculo vehiculo; // Vehículo que ha sido reparado
    private final Base base; // Base donde se realizó la reparación
    private final LocalDate fechaReparacion; // Fecha en la que se realizó la reparación
    private final Factura factura; // Factura generada para la reparación

    /**
     * Constructor de la clase Reparacion.
     *
     * @param vehiculo Vehículo que ha sido reparado.
     * @param base Base donde se realizó la reparación.
     * @param fechaReparacion Fecha en la que se realizó la reparación.
     */
    public Reparacion(Vehiculo vehiculo, Base base, LocalDate fechaReparacion, Factura factura) {
        this.vehiculo = vehiculo;
        this.base = base;
        this.fechaReparacion = fechaReparacion;
        this.factura = factura;
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
     * Obtiene la fecha en la que se realizó la reparación.
     *
     * @return Fecha de la reparación.
     */
    public LocalDate getFechaReparacion() {
        return fechaReparacion;
    }

    /**
     * Obtiene la factura generada para la reparación.
     *
     * @return Factura de la reparación.
     */
    public Factura getFactura() {
        return factura;
    }

}