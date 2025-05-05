package es.uned.movilidad;

/**
 * Clase abstracta que representa una Persona con atributos básicos como DNI, nombre, apellidos,
 * número de teléfono y correo electrónico.
 */
public abstract class Persona {
    private final String dni; // Documento Nacional de Identidad, único e inmutable.
    private String nombre; // Nombre de la persona.
    private String apellidos; // Apellidos de la persona.
    private Integer numeroTelefono; // Número de teléfono de la persona.
    private String email; // Dirección de correo electrónico de la persona.

    /**
     * Constructor de la clase Persona.
     *
     * @param dni            DNI de la persona (obligatorio y final).
     * @param nombre         Nombre de la persona.
     * @param apellidos      Apellidos de la persona.
     * @param numeroTelefono Número de teléfono de la persona.
     * @param email          Dirección de correo electrónico de la persona.
     */
    public Persona(String dni, String nombre, String apellidos, Integer numeroTelefono, String email) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.numeroTelefono = numeroTelefono;
        this.email = email;
    }

    /**
     * Obtiene el DNI de la persona.
     *
     * @return DNI de la persona.
     */
    public String getDni() {
        return dni;
    }

    /**
     * Obtiene el nombre de la persona.
     *
     * @return Nombre de la persona.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la persona.
     *
     * @param nombre Nombre de la persona.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene los apellidos de la persona.
     *
     * @return Apellidos de la persona.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Establece los apellidos de la persona.
     *
     * @param apellidos Apellidos de la persona.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Obtiene el número de teléfono de la persona.
     *
     * @return Número de teléfono de la persona.
     */
    public Integer getNumeroTelefono() {
        return numeroTelefono;
    }

    /**
     * Establece el número de teléfono de la persona.
     *
     * @param numeroTelefono Número de teléfono de la persona.
     */
    public void setNumeroTelefono(Integer numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    /**
     * Obtiene el correo electrónico de la persona.
     *
     * @return Correo electrónico de la persona.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico de la persona.
     *
     * @param email Correo electrónico de la persona.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Devuelve una representación en forma de cadena de la persona.
     *
     * @return Cadena que representa a la persona.
     */
    @Override
    public String toString() {
        return "Usuario {" +
                "dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", telefono=" + numeroTelefono +
                ", email='" + email + '\'' +
                '}';
    }
}
