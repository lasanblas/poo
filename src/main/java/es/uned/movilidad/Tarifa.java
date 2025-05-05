package es.uned.movilidad;

/**
* Clase que representa una tarifa asociada a un tipo de vehículo.
*/
public class Tarifa {

    private TipoVehiculo tipoVehiculo; // Tipo de vehículo al que se aplica la tarifa
    private String nombre; // Nombre descriptivo de la tarifa
    private Double importe; // Importe base de la tarifa
    private Double descuento; // Descuento aplicado a la tarifa (en porcentaje)

    /**
     * Constructor de la clase Tarifa.
     *
     * @param tipoVehiculo Tipo de vehículo al que se aplica la tarifa.
     * @param nombre Nombre descriptivo de la tarifa.
     * @param importe Importe base de la tarifa.
     * @param descuento Descuento aplicado a la tarifa (en porcentaje).
     */
    public Tarifa(TipoVehiculo tipoVehiculo, String nombre, Double importe, Double descuento) {
        this.tipoVehiculo = tipoVehiculo;
        this.nombre = nombre;
        this.importe = importe;
        this.descuento = descuento;
    }

    /**
     * Obtiene el tipo de vehículo al que se aplica la tarifa.
     *
     * @return Tipo de vehículo.
     */
    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    /**
     * Establece el tipo de vehículo al que se aplica la tarifa.
     *
     * @param tipoVehiculo Tipo de vehículo.
     */
    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    /**
     * Obtiene el nombre descriptivo de la tarifa.
     *
     * @return Nombre de la tarifa.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre descriptivo de la tarifa.
     *
     * @param nombre Nombre de la tarifa.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el importe base de la tarifa.
     *
     * @return Importe base de la tarifa.
     */
    public Double getImporte() {
        return importe;
    }

    /**
     * Establece el importe base de la tarifa.
     *
     * @param importe Importe base de la tarifa.
     */
    public void setImporte(Double importe) {
        this.importe = importe;
    }

    /**
     * Obtiene el descuento aplicado a la tarifa.
     *
     * @return Descuento aplicado (en porcentaje).
     */
    public Double getDescuento() {
        return descuento;
    }

    /**
     * Establece el descuento aplicado a la tarifa.
     *
     * @param descuento Descuento aplicado (en porcentaje).
     */
    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    /**
     * Calcula el importe final de la tarifa aplicando el descuento.
     *
     * @return Importe final con el descuento aplicado.
     */
    public Double calcularImporteConDescuento() {
        return this.importe - (this.importe * this.descuento);
    }
}