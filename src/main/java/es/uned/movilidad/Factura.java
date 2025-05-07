package es.uned.movilidad;

/**
 * Representa una factura con un número, un importe y un estado de pago.
 */
public class Factura {

    private final String numeroFactura; // Número de la factura
    private final Double importe; // Importe de la reparación
    private final boolean pagada; // Indica si la factura ha sido pagada

    /**
     * Constructor de la clase Factura.
     *
     * @param numeroFactura El número único de la factura.
     * @param importe El importe total de la factura.
     * @param pagada Indica si la factura ha sido pagada (true) o no (false).
     */
    public Factura(String numeroFactura, Double importe, boolean pagada) {
        this.numeroFactura = numeroFactura;
        this.importe = importe;
        this.pagada = pagada;
    }

    /**
     * Obtiene el número de la factura.
     *
     * @return El número único de la factura.
     */
    public String getNumeroFactura() {
        return numeroFactura;
    }

    /**
     * Obtiene el importe de la factura.
     *
     * @return El importe total de la factura.
     */
    public Double getImporte() {
        return importe;
    }

    /**
     * Indica si la factura ha sido pagada.
     *
     * @return true si la factura ha sido pagada, false en caso contrario.
     */
    public boolean isPagada() {
        return pagada;
    }

    /**
     * Imprime la factura para el vehículo o base reparada.
     *
     */
    public void imprimirFactura() {
        System.out.println("Factura:\n" +
                "Número factura: " + this.getNumeroFactura() + "\n" +
                "Importe: " + this.getImporte() + "€\n" +
                "Pagada: " + this.isPagada() + "\n");
    }

}