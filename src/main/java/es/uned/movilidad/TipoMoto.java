package es.uned.movilidad;

/**
 * Enumeración que representa los diferentes tipos de motos disponibles.
 */
public enum TipoMoto {
    GRANDE("grande"),   // Representa una moto grande
    PEQUENA("pequeña"); // Representa una moto pequeña

    private final String nombre; // Nombre descriptivo del tipo de moto

    /**
     * Constructor de la enumeración TipoMoto.
     *
     * @param nombre Nombre descriptivo del tipo de moto.
     */
    TipoMoto(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el nombre descriptivo del tipo de moto.
     *
     * @return Nombre descriptivo del tipo de moto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene un tipo de moto a partir de su nombre descriptivo.
     *
     * @param nombre Nombre descriptivo del tipo de moto.
     * @return TipoMoto correspondiente al nombre, o null si no se encuentra.
     */
    public static TipoMoto get(String nombre) {
        for (TipoMoto tipo : TipoMoto.values()) {
            if (tipo.getNombre().equalsIgnoreCase(nombre)) {
                return tipo;
            }
        }
        return null;
    }
}