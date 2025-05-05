package es.uned.movilidad;

/**
 * Enumeración que representa los diferentes tipos de vehículos disponibles.
 */
public enum TipoVehiculo {
    MOTOPEQUENA("moto pequeña"), // Representa una moto pequeña
    MOTOGRANDE("moto grande"),   // Representa una moto grande
    BICICLETA("bicicleta"),      // Representa una bicicleta
    PATINETE("patinete");        // Representa un patinete

    private final String nombre; // Nombre descriptivo del tipo de vehículo

    /**
     * Constructor de la enumeración TipoVehiculo.
     *
     * @param nombre Nombre descriptivo del tipo de vehículo.
     */
    TipoVehiculo(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el nombre descriptivo del tipo de vehículo.
     *
     * @return Nombre descriptivo del tipo de vehículo.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene un tipo de vehículo a partir de su nombre descriptivo.
     *
     * @param nombre Nombre descriptivo del tipo de vehículo.
     * @return TipoVehiculo correspondiente al nombre, o null si no se encuentra.
     */
    public static TipoVehiculo get(String nombre) {
        for (TipoVehiculo tipo : TipoVehiculo.values()) {
            if (tipo.getNombre().equalsIgnoreCase(nombre)) {
                return tipo;
            }
        }
        return null;
    }
}