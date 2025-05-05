package es.uned.movilidad;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static es.uned.movilidad.MovilidadApplication.bases;
import static es.uned.movilidad.MovilidadApplication.coordenadaX;
import static es.uned.movilidad.MovilidadApplication.coordenadaY;

/**
 * Clase que representa a un encargado de mantenimiento en el sistema de movilidad.
 * Hereda de la clase {@link Persona}.
 */
public class EncargadoMantenimiento extends Persona {

    /**
     * Lista de vehículos asignados al encargado de mantenimiento.
     */
    private List<Vehiculo> vehiculos;
    /**
     * Número de intervenciones realizadas por el encargado de mantenimiento.
     */
    private Integer numeroDeIntervenciones;

    /**
     * Constructor de la clase EncargadoMantenimiento.
     *
     * @param dni            DNI del encargado de mantenimiento.
     * @param nombre         Nombre del encargado de mantenimiento.
     * @param apellidos      Apellidos del encargado de mantenimiento.
     * @param numeroTelefono Número de teléfono del encargado de mantenimiento.
     * @param email          Email del encargado de mantenimiento.
     */
    public EncargadoMantenimiento(String dni, String nombre, String apellidos, Integer numeroTelefono, String email) {
        super(dni, nombre, apellidos, numeroTelefono, email);
        this.vehiculos = new ArrayList<>();
        this.numeroDeIntervenciones = 0;
    }

    /**
     * Obtiene la lista de vehículos asignados al encargado de mantenimiento.
     *
     * @return Lista de vehículos asignados.
     */
    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    /**
     * Asigna un vehículo al encargado de mantenimiento.
     *
     * @param vehiculo Vehículo a asignar.
     */
    public void asignarVehiculo(Vehiculo vehiculo) {
        this.vehiculos.add(vehiculo);
    }

    /**
     * Obtiene el número de intervenciones realizadas por el encargado de mantenimiento.
     *
     * @return Número de intervenciones realizadas.
     */
    public Integer getNumeroDeIntervenciones() {
        return numeroDeIntervenciones;
    }

    /**
     * Establece el número de intervenciones realizadas por el encargado de mantenimiento.
     *
     * @param numeroDeIntervenciones Número de intervenciones realizadas.
     */
    private void setNumeroDeIntervenciones(Integer numeroDeIntervenciones) {
        this.numeroDeIntervenciones = numeroDeIntervenciones;
    }

    /**
     * Visualiza los vehículos asignados que necesitan mantenimiento.
     */
    public void visualizarVehiculos() {
        for (Vehiculo vehiculo : vehiculos) {
            System.out.println("El vehículo con matrícula " + vehiculo.getMatricula() + " necesita mantenimiento");
        }
    }

    /**
     * Recoge un vehículo para mantenimiento, ya sea en una base o en unas coordenadas específicas.
     *
     * @param matricula         Matrícula del vehículo a recoger.
     * @param base              Nombre de la base donde se encuentra el vehículo.
     * @param coordenadaXDestino Coordenada X de destino.
     * @param coordenadaYDestino Coordenada Y de destino.
     */
    public void recogerVehiculo(String matricula, String base, Integer coordenadaXDestino, Integer coordenadaYDestino) {
        if(matricula == null || matricula.isEmpty()){
            System.out.println("La matrícula no puede estar vacía");
            return;

        }
        Vehiculo vehiculo = this.buscarVehiculo(matricula);
        if(vehiculo == null){
            System.out.println("El vehículo no existe");
        } else {
            if(base != null && !base.isEmpty()){
                Base resultado = this.buscarBase(base);
                if(resultado == null){
                    System.out.println("La base no existe");
                    return;
                }
                resultado.setPlazas(resultado.getPlazas() + 1);
                vehiculo.setBase(resultado);
            } else {
                if(coordenadaXDestino == null || coordenadaYDestino == null || coordenadaXDestino < 0 || coordenadaYDestino < 0 || coordenadaXDestino > coordenadaX || coordenadaYDestino > coordenadaY){
                    System.out.println("Las coordenadas indicadas no son correctas. Por favor, vuelva a intentarlo.");
                    return;
                } else if(vehiculo instanceof Moto moto) {
                    moto.setCoordenadaX(null);
                    moto.setCoordenadaY(null);
                } else {
                    System.out.println("El vehículo con matrícula " + vehiculo.getMatricula() + " no es una moto por lo que no puede ser recogido en unas coordenadas");
                    return;
                }
            }
            System.out.println("El vehículo con matrícula " + vehiculo.getMatricula() + " ha sido recogido para su mantenimiento");
        }
    }

    /**
     * Define un periodo de inactividad para un vehículo.
     *
     * @param matricula Matrícula del vehículo.
     * @param inicio    Fecha de inicio del periodo de inactividad (formato dd/MM/yyyy).
     * @param fin       Fecha de fin del periodo de inactividad (formato dd/MM/yyyy).
     */
    public void definirPeriodoInactividad(String matricula, String inicio, String fin) {
        if(matricula == null || matricula.isEmpty()){
            System.out.println("La matrícula no puede estar vacía");
            return;

        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaInicio;
        LocalDate fechaFin;
        try {
            fechaInicio = LocalDate.parse(inicio, formatter);
            fechaFin = LocalDate.parse(fin, formatter);
        } catch (Exception e) {
            System.out.println("El formato de fecha no es correcto. Debe ser dd/MM/yyyy");
            return;
        }
        Vehiculo vehiculo = this.buscarVehiculo(matricula);
        if(vehiculo == null){
            System.out.println("El vehículo no existe");
        } else {
            if (fechaInicio.isAfter(fechaFin)) {
                System.out.println("La fecha de inicio no puede ser posterior a la fecha de fin");
                return;
            }
            if (vehiculo.getPeriodoInactividadInicio() != null || vehiculo.getPeriodoInactividadFin() != null) {
                System.out.println("El vehículo ya tiene un periodo de inactividad definido");
                return;
            }
            vehiculo.setPeriodoInactividadInicio(fechaInicio);
            vehiculo.setPeriodoInactividadFin(fechaFin);
            System.out.println("El vehículo con matrícula " + vehiculo.getMatricula() + " estará inactivo desde " + vehiculo.getPeriodoInactividadInicio() + " hasta " + vehiculo.getPeriodoInactividadFin());
        }

    }

    /**
     * Devuelve un vehículo después de su mantenimiento, ya sea a una base o a unas coordenadas específicas.
     *
     * @param matricula         Matrícula del vehículo a devolver.
     * @param base              Nombre de la base de destino.
     * @param coordenadaXDestino Coordenada X de destino.
     * @param coordenadaYDestino Coordenada Y de destino.
     */
    public void devolverVehiculo(String matricula, String base, Integer coordenadaXDestino, Integer coordenadaYDestino){
        if(matricula == null || matricula.isEmpty()){
            System.out.println("La matrícula no puede estar vacía");
            return;

        }
        Vehiculo vehiculo = this.buscarVehiculo(matricula);
        if(vehiculo == null){
            System.out.println("El vehículo no existe");
        } else {
            if(base != null && !base.isEmpty()){
                Base resultado = this.buscarBase(base);
                if(resultado == null){
                    System.out.println("La base no existe");
                    return;
                }
                if (resultado.getPlazas() <= 0) {
                    System.out.println("No hay plazas disponibles en la base");
                    return;
                }
                resultado.setPlazas(resultado.getPlazas() - 1);
                vehiculo.setBase(resultado);
            } else {
                if(coordenadaXDestino == null || coordenadaYDestino == null || coordenadaXDestino < 0 || coordenadaYDestino < 0 || coordenadaXDestino > coordenadaX || coordenadaYDestino > coordenadaY){
                    System.out.println("Las coordenadas indicadas no son correctas. Por favor, vuelva a intentarlo.");
                    return;
                } else if(vehiculo instanceof Moto moto) {
                    moto.setCoordenadaX(coordenadaXDestino);
                    moto.setCoordenadaY(coordenadaYDestino);
                } else {
                    System.out.println("El vehículo con matrícula " + vehiculo.getMatricula() + " no es una moto por lo que no puede ser devuelto en unas coordenadas");
                    return;
                }
            }
            if(vehiculo.getBateria() < 20){
                vehiculo.setBateria(100);
                System.out.println("El vehículo con matrícula " + vehiculo.getMatricula() + " ha sido recargado al 100%");
            }
            System.out.println("El vehículo con matrícula " + vehiculo.getMatricula() + " ha sido devuelto");
            numeroDeIntervenciones++;
            vehiculo.setPeriodoInactividadInicio(null);
            vehiculo.setPeriodoInactividadFin(null);
            vehiculos.remove(vehiculo);
            vehiculo.limpiarAvisos();
        }
    }

    /**
     * Busca un vehículo en la lista de vehículos asignados al encargado de mantenimiento por su matrícula.
     *
     * @param matricula Matrícula del vehículo a buscar.
     * @return Vehículo encontrado o null si no existe.
     */
    private Vehiculo buscarVehiculo(String matricula) {
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getMatricula().equalsIgnoreCase(matricula)) {
                return vehiculo;
            }
        }
        return null;
    }

    /**
     * Busca una base en el sistema por su nombre.
     *
     * @param nombre Nombre de la base a buscar.
     * @return Base encontrada o null si no existe.
     */
    private Base buscarBase(String nombre) {
        for (Base base : bases) {
            if (base.getNombre().equalsIgnoreCase(nombre)) {
                return base;
            }
        }
        return null;
    }

    /**
     * Devuelve una representación en forma de String del encargado de mantenimiento.
     *
     * @return String que representa al encargado de mantenimiento.
     */
    @Override
    public String toString() {
        return "EncargadoMantenimiento {" +
                "dni='" + this.getDni() + '\'' +
                ", nombre='" + this.getNombre() + '\'' +
                ", apellidos='" + this.getApellidos() + '\'' +
                ", telefono=" + this.getNumeroTelefono() +
                ", email='" + this.getEmail() + '\'' +
                ", numeroDeIntervenciones=" + numeroDeIntervenciones +
                '}';
    }
}
