package es.uned.movilidad;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static es.uned.movilidad.MovilidadApplication.bases;
import static es.uned.movilidad.MovilidadApplication.personas;
import static es.uned.movilidad.MovilidadApplication.tarifas;
import static es.uned.movilidad.MovilidadApplication.vehiculos;

/**
 * Clase que representa a un administrador del sistema.
 * Permite gestionar usuarios, vehículos, bases y tarifas.
 * Hereda de la clase {@link Persona}.
 */
public class Administrador extends Persona {

    /**
     * Constructor de la clase Administrador.
     *
     * @param dni            DNI del administrador.
     * @param nombre         Nombre del administrador.
     * @param apellidos      Apellidos del administrador.
     * @param numeroTelefono Número de teléfono del administrador.
     * @param email          Email del administrador.
     */
    public Administrador(String dni, String nombre, String apellidos, Integer numeroTelefono, String email) {
        super(dni, nombre, apellidos, numeroTelefono, email);
    }

    /**
     * Crea un usuario estándar y lo añade al sistema.
     *
     * @param dni            DNI del usuario.
     * @param nombre         Nombre del usuario.
     * @param apellidos      Apellidos del usuario.
     * @param numeroTelefono Número de teléfono del usuario.
     * @param email          Email del usuario.
     * @param saldo          Saldo inicial del usuario.
     */
    public void crearUsuario(String dni, String nombre, String apellidos, Integer numeroTelefono, String email, Integer saldo) {
        Persona persona = buscarPersona(dni);
        if(persona != null){
            System.out.println("El usuario ya existe");
            return;
        }
        Usuario usuario = new Usuario(dni, nombre, apellidos, numeroTelefono, email, TipoUsuario.ESTANDAR, saldo.doubleValue());
        personas.add(usuario);
    }

    /**
     * Crea un usuario premium y lo añade al sistema.
     *
     * @param dni            DNI del usuario.
     * @param nombre         Nombre del usuario.
     * @param apellidos      Apellidos del usuario.
     * @param numeroTelefono Número de teléfono del usuario.
     * @param email          Email del usuario.
     * @param saldo          Saldo inicial del usuario.
     */
    public void crearUsuarioPremium(String dni, String nombre, String apellidos, Integer numeroTelefono, String email, Integer saldo) {
        Persona persona = buscarPersona(dni);
        if(persona != null){
            System.out.println("El usuario ya existe");
            return;
        }
        Usuario usuario = new Usuario(dni, nombre, apellidos, numeroTelefono, email, TipoUsuario.PREMIUM, saldo.doubleValue());
        personas.add(usuario);
    }

    /**
     * Crea un mecánico y lo añade al sistema.
     *
     * @param dni            DNI del mecánico.
     * @param nombre         Nombre del mecánico.
     * @param apellidos      Apellidos del mecánico.
     * @param numeroTelefono Número de teléfono del mecánico.
     * @param email          Email del mecánico.
     */
    public void crearMecanico(String dni, String nombre, String apellidos, Integer numeroTelefono, String email) {
        Persona persona = buscarPersona(dni);
        if(persona != null){
            System.out.println("El usuario ya existe");
            return;
        }
        Mecanico mecanico = new Mecanico(dni, nombre, apellidos, numeroTelefono, email);
        personas.add(mecanico);
    }

    /**
     * Crea un encargado de mantenimiento y lo añade al sistema.
     *
     * @param dni            DNI del encargado.
     * @param nombre         Nombre del encargado.
     * @param apellidos      Apellidos del encargado.
     * @param numeroTelefono Número de teléfono del encargado.
     * @param email          Email del encargado.
     */
    public void crearEncargadoManetimiento(String dni, String nombre, String apellidos, Integer numeroTelefono, String email) {
        Persona persona = buscarPersona(dni);
        if(persona != null){
            System.out.println("El usuario ya existe");
            return;
        }
        EncargadoMantenimiento encargadoMantenimiento = new EncargadoMantenimiento(dni, nombre, apellidos, numeroTelefono, email);
        personas.add(encargadoMantenimiento);
    }

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param dni            DNI del usuario.
     * @param email          Nuevo email del usuario.
     * @param nombre         Nuevo nombre del usuario.
     * @param apellidos      Nuevos apellidos del usuario.
     * @param numeroTelefono Nuevo número de teléfono del usuario.
     */
    public void actualizarUsuario(String dni, String email, String nombre, String apellidos, Integer numeroTelefono) {
        Persona persona = buscarPersona(dni);
        if(persona != null){
            persona.setNombre(nombre != null ? nombre : persona.getNombre());
            persona.setApellidos(apellidos != null ? apellidos : persona.getApellidos());
            persona.setNumeroTelefono(numeroTelefono != null ? numeroTelefono : persona.getNumeroTelefono());
            persona.setEmail(email != null ? email : persona.getEmail());
        }
    }

    /**
     * Elimina un usuario del sistema.
     *
     * @param dni DNI del usuario a eliminar.
     */
    public void eliminarUsuario(String dni) {
        Persona persona = buscarPersona(dni);
        if(persona != null){
            personas.remove(persona);
        }
    }

    /**
     * Obtiene una lista de usuarios que cumplen los requisitos para ser promocionados.
     */
    public void obtenerUsuariosAPromocionar(){
        List<Usuario> usuariosAPromocionar = new ArrayList<>();
        for (Persona persona : personas) {
            if (persona instanceof Usuario usuario) {
                if (this.obtenerUsosUltimoMes(usuario) >= 15 || this.obtenerUsosUltimoTresMeses(usuario) >= 10 || this.vehiculosUsadosUltimoSeisMeses(usuario)) {
                    usuariosAPromocionar.add(usuario);
                }
            }
        }
        if(usuariosAPromocionar.isEmpty()){
            System.out.println("No existen actualmente usuarios a promocionar");
            return;
        }
        usuariosAPromocionar.forEach(usuario -> {
            System.out.println(usuario.toString());
        });
    }

    /**
     * Promociona un usuario estándar a premium.
     *
     * @param dni DNI del usuario a promocionar.
     */
    public void promocionarUsuario(String dni) {
        Usuario usuario = (Usuario) buscarPersona(dni);
        if(usuario != null){
            usuario.setTipo(TipoUsuario.PREMIUM);
        }

    }

    /**
     * Muestra todos los usuarios registrados en el sistema.
     */
    public void obtenerUsuariosRegistrados() {
        for (Persona persona : personas) {
            System.out.println(persona.toString());
        }
    }

    /**
     * Busca usuarios en el sistema según los criterios especificados.
     *
     * @param dni            DNI del usuario.
     * @param nombre         Nombre del usuario.
     * @param numeroTelefono Número de teléfono del usuario.
     * @param email          Email del usuario.
     * @param premium        Indica si se buscan solo usuarios premium.
     */
    public void buscarUsuario(String dni, String nombre, Integer numeroTelefono, String email, boolean premium) {
        List<Persona> resultado = new ArrayList<>();
        for (Persona persona : personas) {
            if (dni != null && !dni.isEmpty() && persona.getDni().equalsIgnoreCase(dni)) {
                resultado.add(persona);
            } else if (nombre != null && !nombre.isEmpty() && persona.getNombre().contains(nombre)) {
                resultado.add(persona);
            } else if (numeroTelefono != null && Objects.equals(persona.getNumeroTelefono(), numeroTelefono)) {
                resultado.add(persona);
            } else if (email != null && !email.isEmpty() && persona.getEmail().contains(email)) {
                resultado.add(persona);
            } else if(dni == null && nombre == null && numeroTelefono == null && email == null){
                resultado.add(persona);
            }
        }
        if(resultado.isEmpty()){
            System.out.println("No existen usuarios con los datos introducidos");
        }
        System.out.println("Usuarios encontrados:");
        for(Persona persona : resultado){
            if(premium){
               if(persona instanceof Usuario usuario && usuario.getTipo() == TipoUsuario.PREMIUM){
                   System.out.println((usuario).toString());
               }
            } else {
                if (persona instanceof Usuario) {
                    System.out.println(((Usuario) persona).toString());
                } else if (persona instanceof Mecanico) {
                    System.out.println(((Mecanico) persona).toString());
                } else if (persona instanceof EncargadoMantenimiento) {
                    System.out.println(((EncargadoMantenimiento) persona).toString());
                }
            }
        }
    }

    /**
     * Crea una moto pequeña y la añade al sistema.
     *
     * @param marca        Marca de la moto.
     * @param modelo       Modelo de la moto.
     * @param matricula    Matrícula de la moto.
     * @param coordenadaX  Coordenada X de la ubicación inicial.
     * @param coordenadaY  Coordenada Y de la ubicación inicial.
     */
    public void crearMotoPequena(String marca, String modelo, String matricula, Integer coordenadaX, Integer coordenadaY) {
        Vehiculo vehiculo = buscarVehiculo(matricula);
        if(vehiculo != null){
            System.out.println("El vehículo ya existe");
            return;
        }
        Moto moto = new Moto(matricula, marca, modelo, null, TipoMoto.PEQUENA, coordenadaX, coordenadaY);
        vehiculos.add(moto);
    }

    /**
     * Crea una moto grande y la añade al sistema.
     *
     * @param marca        Marca de la moto.
     * @param modelo       Modelo de la moto.
     * @param matricula    Matrícula de la moto.
     * @param coordenadaX  Coordenada X de la ubicación inicial.
     * @param coordenadaY  Coordenada Y de la ubicación inicial.
     */
    public void crearMotoGrande(String marca, String modelo, String matricula, Integer coordenadaX, Integer coordenadaY) {
        Vehiculo vehiculo = buscarVehiculo(matricula);
        if(vehiculo != null){
            System.out.println("El vehículo ya existe");
            return;
        }
        Moto moto = new Moto(matricula, marca, modelo, null, TipoMoto.GRANDE, coordenadaX, coordenadaY);
        vehiculos.add(moto);
    }

    /**
     * Crea una bicicleta y la añade al sistema.
     *
     * @param marca       Marca de la bicicleta.
     * @param modelo      Modelo de la bicicleta.
     * @param matricula   Matrícula de la bicicleta.
     * @param nombreBase  Nombre de la base donde se ubicará.
     */
    public void crearBicicleta(String marca, String modelo, String matricula, String nombreBase) {
        Vehiculo vehiculo = buscarVehiculo(matricula);
        if(vehiculo != null){
            System.out.println("El vehículo ya existe");
            return;
        }
        Base base = this.buscarBase(nombreBase);
        if(base == null){
            System.out.println("La base no existe");
            return;
        }
        if(base.getPlazas() == 0){
            System.out.println("No hay plazas disponibles en la base " + base.getNombre());
        } else {
            Bicicleta bicicleta = new Bicicleta(matricula, marca, modelo, base);
            base.setPlazas(base.getPlazas() - 1);
            vehiculos.add(bicicleta);
        }
    }

    /**
     * Crea un patinete y lo añade al sistema.
     *
     * @param marca       Marca del patinete.
     * @param modelo      Modelo del patinete.
     * @param matricula   Matrícula del patinete.
     * @param nombreBase  Nombre de la base donde se ubicará.
     */
    public void crearPatinete(String marca, String modelo, String matricula, String nombreBase) {
        Vehiculo vehiculo = buscarVehiculo(matricula);
        if(vehiculo != null){
            System.out.println("El vehículo ya existe");
            return;
        }
        Base base = this.buscarBase(nombreBase);
        if(base == null){
            System.out.println("La base no existe");
            return;
        }
        if(base.getPlazas() == 0){
            System.out.println("No hay plazas disponibles en la base " + base.getNombre());
        } else {
            Patinete patinete = new Patinete(matricula, marca, modelo, base);
            base.setPlazas(base.getPlazas() - 1);
            vehiculos.add(patinete);
        }
    }

    /**
     * Muestra el stock de vehículos disponibles en el sistema.
     */
    public void obtenerStockVehiculos() {
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo instanceof Bicicleta) {
                System.out.println(((Bicicleta) vehiculo).toString());
            } else if (vehiculo instanceof Patinete) {
                System.out.println(((Patinete) vehiculo).toString());
            } else if (vehiculo instanceof Moto) {
                System.out.println(((Moto) vehiculo).toString());
            }
        }
    }

    /**
     * Actualiza los datos de un vehículo existente.
     *
     * @param matricula Matrícula del vehículo.
     * @param marca     Nueva marca del vehículo.
     * @param modelo    Nuevo modelo del vehículo.
     * @param bateria   Nueva batería del vehículo.
     */
    public void actualizarVehiculo(String matricula, String marca, String modelo, Integer bateria) {
        Vehiculo vehiculo = buscarVehiculo(matricula);
        if(vehiculo != null){
            vehiculo.setMarca(marca != null ? marca : vehiculo.getMarca());
            vehiculo.setModelo(modelo != null ? modelo : vehiculo.getModelo());
            vehiculo.setBateria(bateria != null ? bateria : vehiculo.getBateria());

        }
    }

    /**
     * Elimina un vehículo del sistema.
     *
     * @param matricula Matrícula del vehículo a eliminar.
     */
    public void eliminarVehiculo(String matricula) {
        Vehiculo vehiculo = buscarVehiculo(matricula);
        if(vehiculo != null){
            vehiculos.remove(vehiculo);
        }
    }

    /**
     * Visualiza los avisos de mantenimiento de la flota de vehículos.
     */
    public void visualizarAvisosFlota() {
        for(Vehiculo vehiculo : vehiculos){
            for(String aviso : vehiculo.getAvisos()){
                System.out.println(aviso);
            }
        }

    }

    /**
     * Elimina toda la flota de vehículos del sistema.
     */
    public void eliminarFlota() {
        vehiculos.clear();
    }

    /**
     * Crea una base y la añade al sistema.
     *
     * @param nombre       Nombre de la base.
     * @param coordenadaX  Coordenada X de la ubicación de la base.
     * @param coordenadaY  Coordenada Y de la ubicación de la base.
     * @param plazas       Número de plazas disponibles en la base.
     */
    public void crearBase(String nombre, Integer coordenadaX, Integer coordenadaY, Integer plazas) {
        Base base = new Base(nombre, coordenadaX, coordenadaY, plazas);
        bases.add(base);
    }

    /**
     * Elimina una base del sistema.
     *
     * @param nombre Nombre de la base a eliminar.
     */
    public void eliminarBase(String nombre) {
        Base base = buscarBase(nombre);
        if(base != null){
            bases.remove(base);
        }
    }

    /**
     * Visualiza el estado de la batería de la flota de vehículos.
     */
    public void visualizarBateriaFlota() {
        for(Vehiculo vehiculo : vehiculos){
            System.out.println("La batería del vehículo con matrícula " + vehiculo.getMatricula() + " es de " + vehiculo.getBateria() + "%");
        }
    }

    /**
     * Visualiza el estado de las bases del sistema.
     */
    public void visualizarEstadoBases(){
        for(Base base : bases){
            System.out.println("Visualizando el estado de la base " + base.getNombre());
            List<Vehiculo> vehiculosBase = vehiculos.stream().filter(p -> p.getBase().equals(base) && !p.isAlquilado()).toList();
            for(Vehiculo vehiculo : vehiculosBase){
                String tipo = vehiculo instanceof Bicicleta ? "bicicleta" : "patinete";
                System.out.println(tipo + " " + vehiculo.getMatricula() + " está disponible ");
            }
            System.out.println("Huecos disponibles en la base " + base.getNombre() + " es de " + base.getPlazas());
            if(!base.getAvisos().isEmpty()){
                System.out.println("Fallos mecánicos registrados en la base " + base.getNombre());
                for(String aviso : base.getAvisos()){
                    System.out.println(aviso);
                }
            }
        }
    }

    /**
     * Crea una tarifa y la añade al sistema.
     *
     * @param tipoVehiculo Tipo de vehículo al que aplica la tarifa.
     * @param nombre       Nombre de la tarifa.
     * @param importe      Importe de la tarifa.
     * @param descuento    Descuento aplicado a la tarifa.
     */
    public void crearTarifa(String tipoVehiculo, String nombre, Double importe, Double descuento){
        TipoVehiculo tipo = TipoVehiculo.get(tipoVehiculo);
        if(tipo == null){
            System.out.println("El tipo de vehículo no es correcto. Por favor, vuelva a intentarlo. Los tipos disponibles son: " + TipoVehiculo.BICICLETA.name() + ", " + TipoVehiculo.PATINETE.name() + ", " + TipoVehiculo.MOTOPEQUENA.name() + " y " + TipoVehiculo.MOTOPEQUENA.name());
            return;
        }
        tarifas.add(new Tarifa(tipo, nombre, importe, descuento));
    }

    /**
     * Actualiza los datos de una tarifa existente.
     *
     * @param tipoVehiculo Tipo de vehículo al que aplica la tarifa.
     * @param nombre       Nombre de la tarifa.
     * @param importe      Nuevo importe de la tarifa.
     * @param descuento    Nuevo descuento aplicado a la tarifa.
     */
    public void actualizarTarifa(String tipoVehiculo,String nombre, Double importe, Double descuento){
        TipoVehiculo tipo = TipoVehiculo.get(tipoVehiculo);
        if(tipo == null){
            System.out.println("El tipo de vehículo no es correcto. Por favor, vuelva a intentarlo. Los tipos disponibles son: " + TipoVehiculo.BICICLETA.name() + ", " + TipoVehiculo.PATINETE.name() + ", " + TipoVehiculo.MOTOPEQUENA.name() + " y " + TipoVehiculo.MOTOPEQUENA.name());
            return;
        }
        Tarifa tarifa = buscarTarifa(nombre);
        if(tarifa != null){
            tarifa.setImporte(importe);
            tarifa.setDescuento(descuento);
            tarifa.setTipoVehiculo(tipo);
        }
    }

    /**
     * Elimina una tarifa del sistema.
     *
     * @param nombre Nombre de la tarifa a eliminar.
     */
    public void eliminarTarifa(String nombre){
        Tarifa tarifa = buscarTarifa(nombre);
        if(tarifa != null){
            tarifas.remove(tarifa);
        }
    }

    /**
     * Visualiza los vehículos que tienen fallos registrados.
     */
    public void visualizarVehiculosConFallos() {
        for (Vehiculo vehiculo : vehiculos) {
            if (!vehiculo.getAvisos().isEmpty()) {
                System.out.println("El vehículo con matrícula " + vehiculo.getMatricula() + " necesita reparación");
                System.out.println("Fallos registrados:");
                for (String aviso : vehiculo.getAvisos()) {
                    System.out.println(aviso);
                }
            }
        }
    }

    /**
     * Visualiza las bases que tienen fallos registrados.
     */
    public void visualizarBasesConFallos() {
        for (Base base : bases) {
            if (!base.getAvisos().isEmpty()) {
                System.out.println("La base " + base.getNombre() + " necesita reparación");
                System.out.println("Fallos registrados:");
                for (String aviso : base.getAvisos()) {
                    System.out.println(aviso);
                }
            }
        }
    }

    /**
     * Visualiza los vehículos que tienen batería insuficiente.
     */
    public void visualizarVehiculosSinBateria(){
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getBateria() < 20) {
                System.out.println("El vehículo con matrícula " + vehiculo.getMatricula() + " no tiene batería suficiente");
            }
        }
    }

    /**
     * Visualiza los vehículos que están en reparación.
     */
    public void visualizarVehiculosEnReparacion() {
        for (Vehiculo vehiculo : vehiculos) {
            if (!vehiculo.getAvisos().isEmpty()) {
                System.out.println("El vehículo con matrícula " + vehiculo.getMatricula() + " estará inaccesible durante su reparación desde el " + vehiculo.getPeriodoInactividadInicio() + " hasta el " + vehiculo.getPeriodoInactividadFin());
            }
        }
    }

    /**
     * Visualiza las bases que están en reparación.
     */
    public void visualizarBasesEnReparacion() {
        for (Base base : bases) {
            if (!base.getAvisos().isEmpty()) {
                System.out.println("La base " + base.getNombre() + " estará inaccesible durante su reparación desde el " + base.getPeriodoInactividadInicio() + " hasta el " + base.getPeriodoInactividadFin());
            }
        }
    }

    /**
     * Asigna un vehículo a un encargado de mantenimiento y opcionalmente a un mecánico.
     *
     * @param dniEncargado DNI del encargado de mantenimiento.
     * @param dniMecanico  DNI del mecánico (opcional).
     * @param matricula    Matrícula del vehículo.
     */
    public void asignarVehiculo(String dniEncargado, String dniMecanico, String matricula) {
        if((dniEncargado != null && dniEncargado.isEmpty()) || (matricula != null && matricula.isEmpty())){
            System.out.println("No se han introducido todos los datos necesarios");
            return;
        }
        EncargadoMantenimiento encargado = (EncargadoMantenimiento) buscarPersona(dniEncargado);
        if(encargado == null){
            System.out.println("El encargado de mantenimiento no existe");
            return;
        }
        Mecanico mecanico = null;
        if(dniMecanico != null && !dniMecanico.isEmpty()){
            mecanico = (Mecanico) buscarPersona(dniMecanico);
            if(mecanico == null){
                System.out.println("El mecánico no existe");
                return;
            }
        }

        Vehiculo vehiculo = buscarVehiculo(matricula);
        if(vehiculo == null){
            System.out.println("El vehículo no existe");
        } else {
            if(vehiculo.getBateria() < 20){
                encargado.asignarVehiculo(vehiculo);
            } else if(!vehiculo.getAvisos().isEmpty()){
                encargado.asignarVehiculo(vehiculo);
                if(mecanico == null){
                    System.out.println("Para reparar un vehículo es necesario indicar a un mecánico");
                    return;
                }
                mecanico.asignarVehiculo(vehiculo);
            } else {
                System.out.println("El vehículo no necesita reparación");
            }
            System.out.println("El vehículo con matrícula " + vehiculo.getMatricula() + " ha sido asignado para su mantenimiento/reparación ");
        }

    }

    /**
     * Asigna una base a un mecánico para su mantenimiento o reparación.
     *
     * @param dniMecanico DNI del mecánico.
     * @param nombreBase  Nombre de la base.
     */
    public void asignarBase(String dniMecanico, String nombreBase) {
        if((dniMecanico != null && dniMecanico.isEmpty()) || (nombreBase != null && nombreBase.isEmpty())){
            System.out.println("No se han introducido todos los datos necesarios");
            return;
        }
        Mecanico mecanico = (Mecanico) buscarPersona(dniMecanico);
        if(mecanico == null){
            System.out.println("El mecánico no existe");
            return;
        }
        Base base = buscarBase(nombreBase);
        if(base == null){
            System.out.println("La base no existe");
        } else {
            mecanico.asignarBase(base);
            System.out.println("La base " + base.getNombre() + " ha sido asignada al mecánico "+  mecanico.getNombre() + mecanico.getApellidos() + " para su mantenimiento/reparación ");
        }
    }

    /**
     * Obtiene un listado de bases ordenadas según su demanda.
     *
     * @param ascendente Indica si el orden debe ser ascendente.
     */
    public void obtenerListadoBasesSegunDemanda(boolean ascendente) {
        bases.sort(new Comparator<Base>() {
            @Override
            public int compare(Base o1, Base o2) {
                if(Objects.equals(o1.getContadorDeUsos(), o2.getContadorDeUsos()))
                    return 0;
                return o1.getContadorDeUsos() < o2.getContadorDeUsos() ? -1 : 1;
            }
        });
        if(!ascendente){
            Collections.reverse(bases);
            bases.forEach(base -> {
                System.out.println("Base: " + base.getNombre());
                System.out.println("Huecos disponibles en la base " + base.getNombre() + " es de " + base.getPlazas());
                if(!base.getAvisos().isEmpty()){
                    System.out.println("Fallos mecánicos registrados en la base " + base.getNombre() + "Base NO DISPONIBLE");
                    System.out.println("La base " + base.getNombre() + " estará inactiva desde " + base.getPeriodoInactividadInicio() + " hasta " + base.getPeriodoInactividadFin());
                }
                System.out.println("Total de usos de la base " + base.getNombre() + ": " + base.getContadorDeUsos() + "\n");
            });
        } else {
            bases.forEach(base -> {
                System.out.println("Base: " + base.getNombre());
                System.out.println("Huecos disponibles en la base " + base.getNombre() + " es de " + base.getPlazas());
                if(!base.getAvisos().isEmpty()){
                    System.out.println("Fallos mecánicos registrados en la base " + base.getNombre() + "Base NO DISPONIBLE");
                    System.out.println("La base " + base.getNombre() + " estará inactiva desde " + base.getPeriodoInactividadInicio() + " hasta " + base.getPeriodoInactividadFin());
                }
                System.out.println("Total de usos de la base " + base.getNombre() + ": " + base.getContadorDeUsos() + "\n");
            });
        }
    }

    /**
     * Obtiene un listado de vehículos reparados en un rango de fechas.
     *
     * @param inicio Fecha de inicio del rango (formato dd/MM/yyyy).
     * @param fin    Fecha de fin del rango (formato dd/MM/yyyy).
     */
    public void obtenerListadoVehiculosReparados(String inicio, String fin) {
        boolean filtrar = inicio != null && fin != null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaInicio;
        LocalDate fechaFin;
        try {
            assert inicio != null;
            fechaInicio = LocalDate.parse(inicio, formatter);
            assert fin != null;
            fechaFin = LocalDate.parse(fin, formatter);
        } catch (Exception e) {
            System.out.println("El formato de fecha no es correcto. Debe ser dd/MM/yyyy");
            return;
        }
        Double importe = 0.0;
        int reparaciones = 0;
        if(fechaInicio.isAfter(fechaFin)){
            System.out.println("La fecha de inicio no puede ser posterior a la fecha de fin");
            return;
        }
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getReparaciones() != null && !vehiculo.getReparaciones().isEmpty()) {
                if(filtrar){
                    for (Reparacion reparacion : vehiculo.getReparaciones()) {
                        if (reparacion.getFechaReparacion().isAfter(fechaInicio) && reparacion.getFechaReparacion().isBefore(fechaFin)) {
                            System.out.println("El vehículo con matrícula " + vehiculo.getMatricula() + " ha sido reparado el " + reparacion.getFechaReparacion() + " por un importe de " + reparacion.getImporte());
                            reparaciones++;
                            importe += reparacion.getImporte();
                        }
                    }
                    System.out.println("Total de reparaciones del vehículo: " + vehiculo.getReparaciones().size() + ", por un importe total de " + importe+ "\n");
                }else {
                    for (Reparacion reparacion : vehiculo.getReparaciones()) {
                        System.out.println("El vehículo con matrícula " + vehiculo.getMatricula() + " ha sido reparado el " + reparacion.getFechaReparacion() + " por un importe de " + reparacion.getImporte());
                        reparaciones++;
                        importe += reparacion.getImporte();
                    }
                    System.out.println("Total de reparaciones del vehículo: " + vehiculo.getReparaciones().size() + ", por un importe total de " + importe + "\n");
                }
            }
        }

    }

    /**
     * Obtiene un listado de encargados de mantenimiento y mecánicos ordenados por su actividad.
     */
    public void obtenerListadoEncargadosYMecanicos() {
        List<Mecanico> mecanicos = new ArrayList<>();
        List<EncargadoMantenimiento> encargadoMantenimientos = new ArrayList<>();
        for (Persona persona : personas) {
            if (persona instanceof Mecanico) {
                mecanicos.add((Mecanico) persona);
            } else if (persona instanceof EncargadoMantenimiento) {
                encargadoMantenimientos.add((EncargadoMantenimiento) persona);
            }
        }
        mecanicos.sort(new Comparator<Mecanico>() {
            @Override
            public int compare(Mecanico o1, Mecanico o2) {
                if(Objects.equals(o1.getNumeroDeReparaciones(), o2.getNumeroDeReparaciones()))
                    return 0;
                return o1.getNumeroDeReparaciones() < o2.getNumeroDeReparaciones() ? -1 : 1;
            }
        });
        encargadoMantenimientos.sort(new Comparator<EncargadoMantenimiento>() {
            @Override
            public int compare(EncargadoMantenimiento o1, EncargadoMantenimiento o2) {
                if(Objects.equals(o1.getNumeroDeIntervenciones(), o2.getNumeroDeIntervenciones()))
                    return 0;
                return o1.getNumeroDeIntervenciones() < o2.getNumeroDeIntervenciones() ? -1 : 1;
            }
        });
        Collections.reverse(mecanicos);
        mecanicos.forEach(mecanico -> {
            System.out.println("Visualizando datos del mecánico " + mecanico.getNombre() + " " + mecanico.getApellidos());
            System.out.println("El mecánico tiene el dni " + mecanico.getDni());
            if(!mecanico.getVehiculos().isEmpty()){
                System.out.println("El mecánico actualmente tiene asignados "+ mecanico.getVehiculos().size() +" vehículos");
            } else {
                System.out.println("El mecánico actualmente no tiene vehículos asignados");
            }
            System.out.println("El mecánico ha realizado un total de " + mecanico.getNumeroDeReparaciones() + " reparaciones"+ "\n");
        });
        Collections.reverse(encargadoMantenimientos);
        encargadoMantenimientos.forEach(encargadoMantenimiento -> {
            System.out.println("Visualizando datos del encargado de mantenimiento " + encargadoMantenimiento.getNombre() + " " + encargadoMantenimiento.getApellidos());
            System.out.println("El encargado de mantenimiento tiene el dni " + encargadoMantenimiento.getDni());
            if(!encargadoMantenimiento.getVehiculos().isEmpty()){
                System.out.println("El encargado de mantenimiento actualmente tiene asignados "+ encargadoMantenimiento.getVehiculos().size() +" vehículos");
            } else {
                System.out.println("El encargado de mantenimiento actualmente no tiene vehículos asignados");
            }
            System.out.println("El encargado de mantenimiento ha dado servicio a un total de " + encargadoMantenimiento.getNumeroDeIntervenciones() + " vehículos" + "\n");
        });
    }

    /**
     * Obtiene un listado de vehículos agrupados por tipo.
     */
    public void obtenerListadoVehiculosPorTipo(){
        List<Bicicleta> bicicletas = new ArrayList<>();
        List<Patinete> patinetes = new ArrayList<>();
        List<Moto> motos = new ArrayList<>();
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo instanceof Bicicleta) {
                bicicletas.add((Bicicleta) vehiculo);
            } else if (vehiculo instanceof Patinete) {
                patinetes.add((Patinete) vehiculo);
            } else if (vehiculo instanceof Moto) {
                motos.add((Moto) vehiculo);
            }
        }
        this.imprimirListadoBicicletas(bicicletas);
        this.imprimirListadoPatinetes(patinetes);
        this.imprimirListadoMotos(motos);
    }

    /**
     * Obtiene un listado de usuarios ordenados según su gasto en alquileres en un rango de fechas.
     *
     * @param inicio Fecha de inicio del rango (formato dd/MM/yyyy).
     * @param fin    Fecha de fin del rango (formato dd/MM/yyyy).
     */
    public void obtenerListadoUsuariosSegunGastoEnAlquiler(String inicio, String fin) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaInicio;
        LocalDate fechaFin;
        try {
            assert inicio != null;
            fechaInicio = LocalDate.parse(inicio, formatter);
            assert fin != null;
            fechaFin = LocalDate.parse(fin, formatter);
        } catch (Exception e) {
            System.out.println("El formato de fecha no es correcto. Debe ser dd/MM/yyyy");
            return;
        }
        if(fechaInicio.isAfter(fechaFin)){
            System.out.println("La fecha de inicio no puede ser posterior a la fecha de fin");
            return;
        }
        List<Usuario> usuarios = new ArrayList<>();
        for (Persona persona : personas) {
            if (persona instanceof Usuario) {
                usuarios.add((Usuario) persona);
            }
        }
        if(usuarios.isEmpty()){
            System.out.println("No hay usuarios registrados en el sistema");
            return;
        }
        usuarios.sort(new Comparator<Usuario>() {
            @Override
            public int compare(Usuario o1, Usuario o2) {
                Double gastos1 = obtenerGastos(o1, fechaInicio, fechaFin);
                Double gastos2 = obtenerGastos(o2, fechaInicio, fechaFin);
                if (gastos1.equals(gastos2)) {
                    return 0;
                }
                return gastos1 < gastos2 ? -1 : 1;
            }
        });
        Collections.reverse(usuarios);
        System.out.println("Visualizando los datos de los usuarios registrados en el sistema según sus gastos en alquileres");
        usuarios.forEach(usuario -> {
            System.out.println(usuario.toString());
        });
    }

    /**
     * Calcula el gasto de un usuario en alquileres dentro de un rango de fechas.
     *
     * @param usuario Usuario del que se desea calcular el gasto.
     * @param inicio  Fecha de inicio del rango.
     * @param fin     Fecha de fin del rango.
     * @return Gasto total del usuario en el rango de fechas.
     */
    private Double obtenerGastos(Usuario usuario, LocalDate inicio, LocalDate fin){
        Date fechaInicio = Date.from(inicio.atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant());
        Date fechaFin = Date.from(fin.atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant());
        Double gastos = 0.0;
        for (Alquiler alquiler : usuario.getHistoricoAlquileres()) {
            if (alquiler.getFechaInicio().after(fechaInicio) && alquiler.getFechaFin().before(fechaFin)) {
                gastos += alquiler.getImporte();
            }
        }
        return gastos;
    }

    /**
     * Imprime un listado de bicicletas ordenadas por tiempo de uso.
     *
     * @param bicicletas Lista de bicicletas a imprimir.
     */
    private void imprimirListadoBicicletas(List<Bicicleta> bicicletas){
        if(bicicletas.isEmpty()){
            System.out.println("No hay bicicletas registradas en el sistema");
            return;
        }
        System.out.println("Visualizando los datos de las bicicletas registradas en el sistema");
        bicicletas.sort(new Comparator<Bicicleta>() {
            @Override
            public int compare(Bicicleta o1, Bicicleta o2) {
                if(Objects.equals(o1.getTiempoDeUso(), o2.getTiempoDeUso()))
                    return 0;
                return o1.getTiempoDeUso() < o2.getTiempoDeUso() ? -1 : 1;
            }
        });
        Collections.reverse(bicicletas);
        bicicletas.forEach(bicicleta -> {
            System.out.println(bicicleta.toString());
        });
    }

    /**
     * Imprime un listado de patinetes ordenados por tiempo de uso.
     *
     * @param patinetes Lista de patinetes a imprimir.
     */
    private void imprimirListadoPatinetes(List<Patinete> patinetes){
        if(patinetes.isEmpty()){
            System.out.println("No hay patinetes registrados en el sistema");
            return;
        }
        System.out.println("Visualizando los datos de los patinetes registrados en el sistema");
        patinetes.sort(new Comparator<Patinete>() {
            @Override
            public int compare(Patinete o1, Patinete o2) {
                if(Objects.equals(o1.getTiempoDeUso(), o2.getTiempoDeUso()))
                    return 0;
                return o1.getTiempoDeUso() < o2.getTiempoDeUso() ? -1 : 1;
            }
        });
        Collections.reverse(patinetes);
        patinetes.forEach(patinete -> {
            System.out.println(patinete.toString());
        });
    }

    /**
     * Imprime un listado de motos ordenadas por tiempo de uso.
     *
     * @param motos Lista de motos a imprimir.
     */
    private void imprimirListadoMotos(List<Moto> motos){
        if(motos.isEmpty()){
            System.out.println("No hay motos registradas en el sistema");
            return;
        }
        System.out.println("Visualizando los datos de las motos registradas en el sistema");
        motos.sort(new Comparator<Moto>() {
            @Override
            public int compare(Moto o1, Moto o2) {
                if(Objects.equals(o1.getTiempoDeUso(), o2.getTiempoDeUso()))
                    return 0;
                return o1.getTiempoDeUso() < o2.getTiempoDeUso() ? -1 : 1;
            }
        });
        Collections.reverse(motos);
        motos.forEach(moto -> {
            System.out.println(moto.toString());
        });
    }

    /**
     * Busca una persona en el sistema por su DNI.
     *
     * @param dni DNI de la persona a buscar.
     * @return Persona encontrada o null si no existe.
     */
    private Persona buscarPersona(String dni) {
        for (Persona persona : personas) {
            if (persona.getDni().equalsIgnoreCase(dni)) {
                return persona;
            }
        }
        return null;
    }

    /**
     * Busca un vehículo en el sistema por su matrícula.
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
     * Busca una tarifa en el sistema por su nombre.
     *
     * @param nombre Nombre de la tarifa a buscar.
     * @return Tarifa encontrada o null si no existe.
     */
    private Tarifa buscarTarifa(String nombre){
        for (Tarifa tarifa : tarifas) {
            if (tarifa.getNombre().equalsIgnoreCase(nombre)) {
                return tarifa;
            }
        }
        return null;
    }

    /**
     * Obtiene el número de usos de un usuario en el último mes.
     *
     * @param usuario Usuario del que se desea obtener los usos.
     * @return Número de usos en el último mes.
     */
    private int obtenerUsosUltimoMes(Usuario usuario){
        int usos = 0;
        for (Alquiler alquiler : usuario.getHistoricoAlquileres()) {
            if (alquiler.getFechaInicio().after(new Date(System.currentTimeMillis() - 30L * 24 * 60 * 60 * 1000))) {
                usos++;
            }
        }
        return usos;
    }

    /**
     * Obtiene el número de usos de un usuario en los últimos tres meses.
     *
     * @param usuario Usuario del que se desea obtener los usos.
     * @return Número de usos en los últimos tres meses.
     */
    private int obtenerUsosUltimoTresMeses(Usuario usuario){
        int usos = 0;
        for (Alquiler alquiler : usuario.getHistoricoAlquileres()) {
            if (alquiler.getFechaInicio().after(new Date(System.currentTimeMillis() - 90L * 24 * 60 * 60 * 1000))) {
                usos++;
            }
        }
        return usos;
    }

    /**
     * Verifica si un usuario ha utilizado todos los tipos de vehículos en los últimos seis meses.
     *
     * @param usuario Usuario a verificar.
     * @return true si ha utilizado todos los tipos de vehículos, false en caso contrario.
     */
    private boolean vehiculosUsadosUltimoSeisMeses(Usuario usuario){
        boolean moto = false;
        boolean bicicleta = false;
        boolean patinete = false;
        for (Alquiler alquiler : usuario.getHistoricoAlquileres()) {
            if (alquiler.getFechaInicio().after(new Date(System.currentTimeMillis() - 180L * 24 * 60 * 60 * 1000))) {
                if(alquiler.getVehiculo() instanceof Moto){
                    moto = true;
                } else if(alquiler.getVehiculo() instanceof Bicicleta){
                    bicicleta = true;
                } else if(alquiler.getVehiculo() instanceof Patinete){
                    patinete = true;

                }
            }
        }
        return moto && bicicleta && patinete;
    }

}
