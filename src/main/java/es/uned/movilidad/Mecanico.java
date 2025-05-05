package es.uned.movilidad;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static es.uned.movilidad.MovilidadApplication.coordenadaX;
import static es.uned.movilidad.MovilidadApplication.coordenadaY;

/**
 * Clase que representa un mecánico encargado de la reparación de vehículos y bases.
 * Hereda de la clase {@link Persona}.
 */
public class Mecanico extends Persona {

    /**
     * Lista de vehículos asignados al mecánico.
     */
    private List<Vehiculo> vehiculos;

    /**
     * Lista de bases asignadas al mecánico.
     */
    private List<Base> bases;

    /**
     * Base en la que el mecánico se encuentra realizando una reparación actualmente.
     */
    private Base baseEnReparacion;

    /**
     * Número total de reparaciones realizadas por el mecánico.
     */
    private Integer numeroDeReparaciones;

    /**
     * Constructor de la clase Mecanico.
     *
     * @param dni            DNI del mecánico.
     * @param nombre         Nombre del mecánico.
     * @param apellidos      Apellidos del mecánico.
     * @param numeroTelefono Número de teléfono del mecánico.
     * @param email          Email del mecánico.
     */
    public Mecanico(String dni, String nombre, String apellidos, Integer numeroTelefono, String email) {
        super(dni, nombre, apellidos, numeroTelefono, email);
        this.vehiculos = new ArrayList<>();
        this.bases = new ArrayList<>();
        this.baseEnReparacion = null;
        this.numeroDeReparaciones = 0;
    }

    /**
     * Obtiene la lista de vehículos asignados al mecánico.
     *
     * @return Lista de vehículos.
     */
    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    /**
     * Asigna un vehículo al mecánico.
     *
     * @param vehiculo Vehículo a asignar.
     */
    public void asignarVehiculo(Vehiculo vehiculo) {
        this.vehiculos.add(vehiculo);
    }

    /**
     * Obtiene la lista de bases asignadas al mecánico.
     *
     * @return Lista de bases.
     */
    private List<Base> getBases() {
        return bases;
    }

    /**
     * Asigna una base al mecánico.
     *
     * @param base Base a asignar.
     */
    public void asignarBase(Base base) {
        this.bases.add(base);
    }

    /**
     * Obtiene el número total de reparaciones realizadas por el mecánico.
     *
     * @return Número de reparaciones.
     */
    public Integer getNumeroDeReparaciones() {
        return numeroDeReparaciones;
    }

    /**
     * Establece el número total de reparaciones realizadas por el mecánico.
     *
     * @param numeroDeReparaciones Número de reparaciones.
     */
    private void setNumeroDeReparaciones(Integer numeroDeReparaciones) {
        this.numeroDeReparaciones = numeroDeReparaciones;
    }

    /**
     * Muestra en consola los vehículos y bases asignados al mecánico.
     */
    public void visualizarVehiculosYBases() {
        for (Vehiculo vehiculo : vehiculos) {
            System.out.println("El vehículo con matrícula " + vehiculo.getMatricula() + " reparación");
        }
        for (Base base : bases) {
            System.out.println("La base " + base.getNombre() + " necesita reparación");
        }
        if(baseEnReparacion != null){
            System.out.println("Actualmente me encuentro en la base " + baseEnReparacion.getNombre() + " para su reparación");
        }
    }

    /**
     * Recoge un vehículo para su reparación.
     *
     * @param matricula          Matrícula del vehículo.
     * @param base               Nombre de la base.
     * @param coordenadaXDestino Coordenada X del destino.
     * @param coordenadaYDestino Coordenada Y del destino.
     */
    public void recogerVehiculo(String matricula, String base, Integer coordenadaXDestino, Integer coordenadaYDestino) {
        if(matricula == null || matricula.isEmpty()){
            System.out.println("La matrícula no puede estar vacía");
            return;

        }
        if(baseEnReparacion != null){
            System.out.println("No puedes recoger un vehículo mientras estás en la base " + baseEnReparacion.getNombre());
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
            System.out.println("El vehículo con matrícula " + vehiculo.getMatricula() + " ha sido recogido para su reparación");
        }
    }

    /**
     * Desplaza al mecánico a una base para realizar una reparación.
     *
     * @param base Nombre de la base.
     */
    public void desplazarABase(String base){
        if(baseEnReparacion != null){
            System.out.println("No puedes desplazar a la base " + base + " mientras estás en la base " + baseEnReparacion.getNombre());
            return;
        }
        if(base == null || base.isEmpty()){
            System.out.println("La base no puede estar vacía");
            return;
        }
        Base resultado = this.buscarBase(base);
        if(resultado == null){
            System.out.println("La base no existe");
            return;
        }
        this.baseEnReparacion = resultado;
        System.out.println("Te has desplazado a la base " + resultado.getNombre() + " para su reparación");
    }

    /**
     * Define un periodo de inactividad para un vehículo o una base.
     *
     * @param matricula Matrícula del vehículo.
     * @param base      Nombre de la base.
     * @param inicio    Fecha de inicio del periodo (formato dd/MM/yyyy).
     * @param fin       Fecha de fin del periodo (formato dd/MM/yyyy).
     */
    public void definirPeriodoInactividad(String matricula, String base, String inicio, String fin) {
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
        if(matricula != null && !matricula.isEmpty()){
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
        if(base != null && !base.isEmpty()){
            Base resultado = this.buscarBase(base);
            if(resultado == null){
                System.out.println("La base no existe");
            } else {
                if (fechaInicio.isAfter(fechaFin)) {
                    System.out.println("La fecha de inicio no puede ser posterior a la fecha de fin");
                    return;
                }
                if (resultado.getPeriodoInactividadInicio() != null || resultado.getPeriodoInactividadFin() != null) {
                    System.out.println("La base ya tiene un periodo de inactividad definido");
                    return;
                }
                resultado.setPeriodoInactividadInicio(fechaInicio);
                resultado.setPeriodoInactividadFin(fechaFin);
                System.out.println("La base " + resultado.getNombre() + " estará inactiva desde " + resultado.getPeriodoInactividadInicio() + " hasta " + resultado.getPeriodoInactividadFin());
            }
        }
    }

    /**
     * Genera una factura para un vehículo o una base tras una reparación.
     *
     * @param matricula Matrícula del vehículo.
     * @param base      Nombre de la base.
     * @param importe   Importe de la reparación.
     */
    public void generarFactura(String matricula, String base, Double importe){
        if(importe == null || importe <= 0){
            System.out.println("El importe no puede ser nulo o menor o igual a cero");
            return;

        }
        if(matricula != null && !matricula.isEmpty()){
            Vehiculo vehiculo = this.buscarVehiculo(matricula);
            if(vehiculo == null){
                System.out.println("El vehículo no existe");
                return;
            }
            Reparacion reparacion = new Reparacion(vehiculo, null, importe, LocalDate.now());
            this.baseEnReparacion = null;
            vehiculo.addReparacion(reparacion);
            numeroDeReparaciones++;
            vehiculos.remove(vehiculo);
            vehiculo.limpiarAvisos();
            System.out.println(reparacion.imprimirFacturaVehiculo());
        }
        else if(base != null && !base.isEmpty()){
            Base resultado = this.buscarBase(base);
            if(resultado == null){
                System.out.println("La base no existe");
                return;
            }
            Reparacion reparacion = new Reparacion(null, resultado, importe, LocalDate.now());
            this.baseEnReparacion = null;
            resultado.addReparacion(reparacion);
            numeroDeReparaciones++;
            bases.remove(resultado);
            resultado.limpiarAvisos();
            System.out.println(reparacion.imprimirFacturaBase());
        } else {
            System.out.println("No se ha indicado ni vehículo ni base");
        }
    }

    /**
     * Busca un vehículo por su matrícula.
     *
     * @param matricula Matrícula del vehículo.
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
     * Busca una base por su nombre.
     *
     * @param nombre Nombre de la base.
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
     * Representación en forma de String del mecánico.
     *
     * @return String con los datos del mecánico.
     */
    @Override
    public String toString() {
        return "Mecanico {" +
                "dni='" + this.getDni() + '\'' +
                ", nombre='" + this.getNombre() + '\'' +
                ", apellidos='" + this.getApellidos() + '\'' +
                ", telefono=" + this.getNumeroTelefono() +
                ", email='" + this.getEmail() + '\'' +
                ", numeroDeReparaciones=" + numeroDeReparaciones +
                '}';
    }
}

