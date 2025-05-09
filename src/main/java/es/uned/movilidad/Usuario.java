package es.uned.movilidad;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static es.uned.movilidad.MovilidadApplication.bases;
import static es.uned.movilidad.MovilidadApplication.coordenadaX;
import static es.uned.movilidad.MovilidadApplication.coordenadaY;
import static es.uned.movilidad.MovilidadApplication.tarifas;
import static es.uned.movilidad.MovilidadApplication.vehiculos;

/**
 * Clase que representa un usuario del sistema de movilidad.
 * Hereda de la clase {@link Persona}.
 */
public class Usuario extends Persona {

    /**
     * Tipo de usuario (NORMAL o PREMIUM).
     */
    private TipoUsuario tipo;

    /**
     * Saldo disponible del usuario.
     */
    private Double saldo;

    /**
     * Alquiler actual del usuario.
     */
    private Alquiler alquiler;

    /**
     * Histórico de alquileres realizados por el usuario.
     */
    private List<Alquiler> historicoAlquileres = new ArrayList<>();

    /**
     * Constructor de la clase Usuario.
     *
     * @param dni            DNI del usuario.
     * @param nombre         Nombre del usuario.
     * @param apellidos      Apellidos del usuario.
     * @param numeroTelefono Número de teléfono del usuario.
     * @param email          Email del usuario.
     * @param tipo           Tipo de usuario.
     * @param saldo          Saldo inicial del usuario.
     */
    public Usuario(String dni, String nombre, String apellidos, Integer numeroTelefono, String email, TipoUsuario tipo, Double saldo) {
        super(dni, nombre, apellidos, numeroTelefono, email);
        this.tipo = tipo;
        this.saldo = saldo;
    }

    /**
     * Verifica si el usuario es de tipo PREMIUM.
     *
     * @return true si el usuario es PREMIUM, false en caso contrario.
     */
    private boolean esPremium() {
        return tipo == TipoUsuario.PREMIUM;
    }

    /**
     * Establece el tipo de usuario.
     *
     * @param tipo Tipo de usuario.
     */
    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene el tipo de usuario.
     *
     * @return Tipo de usuario.
     */
    public TipoUsuario getTipo() {
        return tipo;
    }

    /**
     * Obtiene el saldo del usuario.
     *
     * @return Saldo del usuario.
     */
    private Double getSaldo() {
        return saldo;
    }

    /**
     * Establece el saldo del usuario.
     *
     * @param saldo Saldo del usuario.
     */
    private void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    /**
     * Obtiene el histórico de alquileres del usuario.
     *
     * @return Lista de alquileres históricos.
     */
    public List<Alquiler> getHistoricoAlquileres() {
        return historicoAlquileres;
    }

    /**
     * Visualiza los vehículos disponibles en las bases y en las coordenadas.
     */
    public void visualizarVehiculosDisponibles(){
        if(vehiculos.isEmpty()){
            System.out.println("No existen vehículos disponibles\n");
            return;
        }
        List<Vehiculo> vehiculosReservados = vehiculos.stream().filter(p -> !p.isAlquilado() && p.isReservado()).toList();
        for(Vehiculo vehiculo : vehiculosReservados){
            int minutos = (int) ((new Date().getTime() - vehiculo.getFechaReserva().getTime()) / 1000 / 60);
            if(minutos > 20){
                vehiculo.setReservado(false);
                System.out.println("El vehículo con matrícula " + vehiculo.getMatricula() + " ha sido liberado tras 20 minutos de reserva\n");
            }
        }
        for(Base base : bases){
            if(base.getAvisos().isEmpty()){
                List<Vehiculo> vehiculosBase = vehiculos.stream().filter(p -> p.getBase() != null && p.getBase().equals(base) && !p.isAlquilado() && !p.isReservado() && p.getAvisos().isEmpty()).toList();
                if(vehiculosBase.isEmpty()){
                    System.out.println("No existen vehículos disponibles en la base " + base.getNombre());
                    continue;
                } else {
                    System.out.println("Visualizando los vehículos disponibles en la base " + base.getNombre());
                    for(Vehiculo vehiculo : vehiculosBase){
                        TipoVehiculo tipo = vehiculo instanceof Bicicleta ? TipoVehiculo.BICICLETA : TipoVehiculo.PATINETE;
                        System.out.println(tipo.getNombre() + " con matrícula " + vehiculo.getMatricula() + " está disponible con batería al " + vehiculo.getBateria() + "%");
                    }
                }
                System.out.println("Huecos disponibles en la base " + base.getNombre() + " es de " + base.getPlazas());
            } else {
                System.out.println("Fallos mecánicos registrados en la base " + base.getNombre() + ": Base NO DISPONIBLE");
                if(base.getPeriodoInactividadInicio() != null && base.getPeriodoInactividadFin() != null){
                    System.out.println("La base " + base.getNombre() + " estará inactiva desde " + base.getPeriodoInactividadInicio() + " hasta " + base.getPeriodoInactividadFin());
                }
            }
        }
        List<Vehiculo> motosDisponibles = vehiculos.stream().filter(p -> p instanceof Moto && !p.isAlquilado() && !p.isReservado() && p.getAvisos().isEmpty()).toList();
        System.out.println("Visualizando las motos disponibles: ");
        for(Vehiculo vehiculo : motosDisponibles){
            Moto moto = (Moto) vehiculo;
            System.out.println("Moto " + moto.getTipo() + " con matrícula " + moto.getMatricula()+ " en las coordenadas [" + moto.getCoordenadaX() + "," + moto.getCoordenadaY() + "]" + " está disponible con batería al " + moto.getBateria() + "%");
        }

    }

    /**
     * Alquila un vehículo según el tipo, base y coordenadas proporcionadas.
     *
     * @param tipoVehiculo     Tipo de vehículo a alquilar.
     * @param nombreBase       Nombre de la base de inicio.
     * @param coordenadaXInicio Coordenada X de inicio.
     * @param coordenadaYInicio Coordenada Y de inicio.
     */
    public void alquilarVehiculo(String tipoVehiculo, String nombreBase, Integer coordenadaXInicio, Integer coordenadaYInicio) {
        TipoVehiculo tipo = TipoVehiculo.get(tipoVehiculo);
        if(tipo == null){
            System.out.println("El tipo de vehículo no es correcto. Por favor, vuelva a intentarlo. Los tipos disponibles son: " + TipoVehiculo.BICICLETA.name() + ", " + TipoVehiculo.PATINETE.name() + ", " + TipoVehiculo.MOTOGRANDE.name() + " y " + TipoVehiculo.MOTOPEQUENA.name());
            return;
        }
        if(TipoVehiculo.BICICLETA.equals(tipo)){
            this.alquilarBicicleta(nombreBase);
        } else if (TipoVehiculo.PATINETE.equals(tipo)) {
            this.alquilarPatinete(nombreBase);
        } else if (TipoVehiculo.MOTOPEQUENA.equals(tipo)) {
            this.alquilarMoto(TipoMoto.PEQUENA, coordenadaXInicio, coordenadaYInicio);
        } else if (TipoVehiculo.MOTOGRANDE.equals(tipo)) {
            this.alquilarMoto(TipoMoto.GRANDE, coordenadaXInicio, coordenadaYInicio);
        } else {
            System.out.println("Tipo de vehículo " + tipoVehiculo + " no disponible ");
        }
    }

    private void alquilarBicicleta(String nombreBase){
        if (validacionesGeneralesPrevias()) return;
        if (validarDatosBase(nombreBase)) return;
        List<Vehiculo> vehiculosBase;
        if(this.esPremium()){
            vehiculosBase = vehiculos.stream().filter(p -> p instanceof Bicicleta && p.getBase().getNombre().equals(nombreBase) && !p.isAlquilado() && !p.isReservado() && p.getBateria() >= 10 && p.getAvisos().isEmpty()).toList();
        } else {
            vehiculosBase = vehiculos.stream().filter(p -> p instanceof Bicicleta && p.getBase().getNombre().equals(nombreBase) && !p.isAlquilado() && !p.isReservado() && p.getBateria() >= 20 && p.getAvisos().isEmpty()).toList();
        }
        if (alquilarEnBase(vehiculosBase, nombreBase, TipoVehiculo.BICICLETA, false)) return;
    }

    private void alquilarPatinete(String nombreBase){
        if (validacionesGeneralesPrevias()) return;
        if (validarDatosBase(nombreBase)) return;
        List<Vehiculo> vehiculosBase;
        if(this.esPremium()){
            vehiculosBase = vehiculos.stream().filter(p -> p instanceof Patinete && p.getBase().getNombre().equals(nombreBase) && !p.isAlquilado() && !p.isReservado() && p.getBateria() >= 10 && p.getAvisos().isEmpty()).toList();
        } else {
            vehiculosBase = vehiculos.stream().filter(p -> p instanceof Patinete && p.getBase().getNombre().equals(nombreBase) && !p.isAlquilado() && !p.isReservado() && p.getBateria() >= 20 && p.getAvisos().isEmpty()).toList();
        }
        if (alquilarEnBase(vehiculosBase, nombreBase, TipoVehiculo.PATINETE, false)) return;
    }

    private void alquilarMoto(TipoMoto tipoMoto, Integer coordenadaXInicio, Integer coordenadaYInicio){
        if (validacionesGeneralesPrevias()) return;
        if(coordenadaXInicio == null || coordenadaYInicio == null || coordenadaXInicio < 0 || coordenadaYInicio < 0 || coordenadaXInicio > coordenadaX || coordenadaYInicio > coordenadaY){
            System.out.println("Las coordenadas indicadas no son correctas. Por favor, vuelva a intentarlo.");
            return;
        }
        else {
            List<Vehiculo> motosDisponibles;
            if(this.esPremium()){
                if(TipoMoto.GRANDE.equals(tipoMoto)){
                    motosDisponibles = vehiculos.stream().filter(p -> p instanceof Moto && ((Moto) p).getCoordenadaX().equals(coordenadaXInicio) && ((Moto) p).getCoordenadaY().equals(coordenadaYInicio) && !p.isAlquilado() && !p.isReservado() && p.getBateria() >= 10 && TipoMoto.GRANDE.equals(((Moto) p).getTipo()) && p.getAvisos().isEmpty()).toList();
                    if (alquilarMoto(motosDisponibles, coordenadaXInicio, coordenadaYInicio, TipoVehiculo.MOTOGRANDE, false)) return;
                } else {
                    motosDisponibles = vehiculos.stream().filter(p -> p instanceof Moto && ((Moto) p).getCoordenadaX().equals(coordenadaXInicio) && ((Moto) p).getCoordenadaY().equals(coordenadaYInicio) && !p.isAlquilado() && !p.isReservado() && p.getBateria() >= 10 && TipoMoto.PEQUENA.equals(((Moto) p).getTipo()) && p.getAvisos().isEmpty()).toList();
                    if (alquilarMoto(motosDisponibles, coordenadaXInicio, coordenadaYInicio, TipoVehiculo.MOTOPEQUENA, false)) return;
                }
            } else {
                if(TipoMoto.GRANDE.equals(tipoMoto)){
                    motosDisponibles = vehiculos.stream().filter(p -> p instanceof Moto && ((Moto) p).getCoordenadaX().equals(coordenadaXInicio) && ((Moto) p).getCoordenadaY().equals(coordenadaYInicio) &&  !p.isAlquilado() && !p.isReservado() && p.getBateria() >= 20 && TipoMoto.GRANDE.equals(((Moto) p).getTipo()) && p.getAvisos().isEmpty()).toList();
                    if (alquilarMoto(motosDisponibles, coordenadaXInicio, coordenadaYInicio, TipoVehiculo.MOTOGRANDE, false)) return;
                } else {
                    motosDisponibles = vehiculos.stream().filter(p -> p instanceof Moto && ((Moto) p).getCoordenadaX().equals(coordenadaXInicio) && ((Moto) p).getCoordenadaY().equals(coordenadaYInicio) && !p.isAlquilado() && !p.isReservado() && p.getBateria() >= 20 && TipoMoto.PEQUENA.equals(((Moto) p).getTipo()) && p.getAvisos().isEmpty()).toList();
                    if (alquilarMoto(motosDisponibles, coordenadaXInicio, coordenadaYInicio,  TipoVehiculo.MOTOPEQUENA, false)) return;
                }
            }
        }
    }

    /**
     * Reserva un vehículo según el tipo, base y coordenadas proporcionadas.
     *
     * @param tipoVehiculo     Tipo de vehículo a reservar.
     * @param nombreBase       Nombre de la base de inicio.
     * @param coordenadaXInicio Coordenada X de inicio.
     * @param coordenadaYInicio Coordenada Y de inicio.
     */
    public void reservarVehiculo(String tipoVehiculo, String nombreBase, Integer coordenadaXInicio, Integer coordenadaYInicio) {
        if(!this.esPremium()){
            System.out.println("No eres usuario premium. No puedes reservar un vehículo");
            return;
        }
        TipoVehiculo tipo = TipoVehiculo.get(tipoVehiculo);
        if(tipo == null){
            System.out.println("El tipo de vehículo no es correcto. Por favor, vuelva a intentarlo. Los tipos disponibles son: " + TipoVehiculo.BICICLETA.name() + ", " + TipoVehiculo.PATINETE.name() + ", " + TipoVehiculo.MOTOPEQUENA.name() + " y " + TipoVehiculo.MOTOPEQUENA.name());
            return;
        }
        if(TipoVehiculo.BICICLETA.equals(tipo)){
            this.reservarBicicleta(nombreBase);
        } else if (TipoVehiculo.PATINETE.equals(tipo)) {
            this.reservarPatinete(nombreBase);
        } else if (TipoVehiculo.MOTOPEQUENA.equals(tipo)) {
            this.reservarMoto(TipoMoto.PEQUENA, coordenadaXInicio, coordenadaYInicio);
        } else if (TipoVehiculo.MOTOGRANDE.equals(tipo)) {
            this.reservarMoto(TipoMoto.GRANDE, coordenadaXInicio, coordenadaYInicio);
        } else {
            System.out.println("Tipo de vehículo " + tipoVehiculo + " no disponible ");
        }
    }

    private void reservarBicicleta(String nombreBase) {
        if (validacionesGeneralesPrevias()) return;
        if (validarDatosBase(nombreBase)) return;
        List<Vehiculo> vehiculosBase = vehiculos.stream().filter(p -> p instanceof Bicicleta && p.getBase().getNombre().equals(nombreBase) && !p.isAlquilado() && !p.isReservado() && p.getBateria() >= 10 && p.getAvisos().isEmpty()).toList();
        if (alquilarEnBase(vehiculosBase, nombreBase, TipoVehiculo.BICICLETA, true)) return;
        System.out.println("Alquilando bicicleta desde la base" + nombreBase);
    }

    private void reservarPatinete(String nombreBase) {
        if (validacionesGeneralesPrevias()) return;
        if (validarDatosBase(nombreBase)) return;
        List<Vehiculo> vehiculosBase = vehiculos.stream().filter(p -> p instanceof Patinete && p.getBase().getNombre().equals(nombreBase) && !p.isAlquilado() && !p.isReservado() && p.getBateria() >= 10 && p.getAvisos().isEmpty()).toList();
        if (alquilarEnBase(vehiculosBase, nombreBase, TipoVehiculo.PATINETE, true)) return;
        System.out.println("Reservado patinete en la base" + nombreBase);
    }

    private void reservarMoto(TipoMoto tipoMoto, Integer coordenadaXInicio, Integer coordenadaYInicio) {
        if (validacionesGeneralesPrevias()) return;
        if(coordenadaXInicio == null || coordenadaYInicio == null || coordenadaXInicio < 0 || coordenadaYInicio < 0 || coordenadaXInicio > coordenadaX || coordenadaYInicio > coordenadaY){
            System.out.println("Las coordenadas indicadas no son correctas. Por favor, vuelva a intentarlo.");
            return;
        }
        else {
            List<Vehiculo> motosDisponibles;
            if(TipoMoto.GRANDE.equals(tipoMoto)){
                motosDisponibles = vehiculos.stream().filter(p -> p instanceof Moto && ((Moto) p).getCoordenadaX().equals(coordenadaXInicio) && ((Moto) p).getCoordenadaY().equals(coordenadaYInicio) && !p.isAlquilado() && !p.isReservado() && p.getBateria() >= 10 && TipoMoto.GRANDE.equals(((Moto) p).getTipo()) && p.getAvisos().isEmpty()).toList();
                if (alquilarMoto(motosDisponibles, coordenadaXInicio, coordenadaYInicio, TipoVehiculo.MOTOGRANDE, true)) return;
            } else {
                motosDisponibles = vehiculos.stream().filter(p -> p instanceof Moto && ((Moto) p).getCoordenadaX().equals(coordenadaXInicio) && ((Moto) p).getCoordenadaY().equals(coordenadaYInicio) && !p.isAlquilado() && !p.isReservado() && p.getBateria() >= 10 && TipoMoto.PEQUENA.equals(((Moto) p).getTipo()) && p.getAvisos().isEmpty()).toList();
                if (alquilarMoto(motosDisponibles, coordenadaXInicio, coordenadaYInicio, TipoVehiculo.MOTOPEQUENA, true)) return;
            }
        }
        System.out.println("Reservada moto en las coordenadas [" + coordenadaXInicio + "," + coordenadaYInicio + "]");
    }

    /**
     * Confirma la reserva actual del usuario.
     */
    public void confirmarReserva(){
        if(this.alquiler == null){
            System.out.println("No tiene ninguna reserva");
            return;
        }
        if(!this.alquiler.getVehiculo().isReservado()){
            System.out.println("El vehículo con matrícula " + this.alquiler.getVehiculo().getMatricula() + " ha sido liberado tras exceder los 20 minutos de reserva. Se ha cancelado la reserva");
            this.alquiler = null;
            return;
        }
        this.alquiler.getVehiculo().setAlquilado(true);
        this.alquiler.getVehiculo().setReservado(false);
        this.alquiler.getVehiculo().setFechaReserva(null);
        this.alquiler.setFechaInicio(new Date());
        System.out.println("Reserva confirmada. Alquiler del vehículo iniciado.");
    }

    /**
     * Elimina la reserva actual del usuario.
     */
    public void eliminarReserva(){
        if(this.alquiler == null){
            System.out.println("No tiene ninguna reserva");
            return;
        }
        this.alquiler.getVehiculo().setReservado(false);
        this.alquiler = null;
        System.out.println("Reserva eliminada");
    }

    /**
     * Finaliza el alquiler actual del usuario en una base específica.
     *
     * @param nombreBaseFin Nombre de la base de finalización.
     */
    public void finalizarAlquiler(String nombreBaseFin){
        if(this.alquiler == null){
            System.out.println("No tiene ningún alquiler activo");
            return;
        }
        if(nombreBaseFin != null){
            Base baseFin = buscarBase(nombreBaseFin);
            if(baseFin == null){
                System.out.println("Los datos de la bases indicadas no son correctos. Por favor, vuelva a intentarlo.");
                return;
            }
            if(!baseFin.getAvisos().isEmpty()){
                System.out.println("Fallos mecánicos registrados en la base " + nombreBaseFin + " Base NO DISPONIBLE");
                if(baseFin.getPeriodoInactividadInicio() != null && baseFin.getPeriodoInactividadFin() != null){
                    System.out.println("La base " + nombreBaseFin + " estará inactiva desde " + baseFin.getPeriodoInactividadInicio() + " hasta " + baseFin.getPeriodoInactividadFin());
                }
                return;
            }
            if(baseFin.getPlazas() == 0){
                System.out.println("No existen plazas disponibles para aparcar en la base " + nombreBaseFin + " Base NO DISPONIBLE");
                return;
            }
            System.out.println("Aparcando el vehículo en la base " + nombreBaseFin);
            baseFin.setPlazas(baseFin.getPlazas() - 1);
            baseFin.setContadorDeUsos(baseFin.getContadorDeUsos() + 1);
            this.alquiler.getVehiculo().setBase(baseFin);
            this.alquiler.setBaseFinal(baseFin);
        }
        if(this.alquiler.getVehiculo() instanceof Moto){
            System.out.println("Las motos se deben finalizar indicadas las coordenadas correspondientes. Por favor, vuelva a intentarlo.");
            return;
        }
        this.alquiler.getVehiculo().setAlquilado(false);
        this.alquiler.setFechaFin(new Date());
        double bateriaResidual = this.alquiler.getVehiculo().getBateria() - this.calcularConsumo(this.alquiler.obtenerDuracionEnMinutos());
        if(bateriaResidual <= 0.0){
            System.out.println("Batería agotada. Se aplicará un recargo de 1 euro");
        }
        double importe = bateriaResidual <= 0.0 ? this.alquiler.getTarifa().getImporte() + 1 : this.alquiler.getTarifa().getImporte();
        double importeConDescuento = bateriaResidual <= 0.0 ? this.alquiler.getTarifa().calcularImporteConDescuento() + 1 : this.alquiler.getTarifa().calcularImporteConDescuento();
        this.saldo -= this.esPremium() ? importeConDescuento : importe;
        this.alquiler.setImporte(this.esPremium() ? importeConDescuento : importe);
        this.alquiler.getVehiculo().setTiempoDeUso(this.alquiler.obtenerDuracionTotal());
        this.alquiler.getVehiculo().setBateria((int) bateriaResidual);
        this.historicoAlquileres.add(this.alquiler);
        System.out.println("El alquiler ha sido finalizado. El saldo actual es de " + this.saldo + " euros");
        this.alquiler = null;
    }

    /**
     * Finaliza el alquiler actual del usuario en unas coordenadas específicas.
     *
     * @param coordenadaXFin Coordenada X de finalización.
     * @param coordenadaYFin Coordenada Y de finalización.
     */
    public void finalizarAlquiler(Integer coordenadaXFin, Integer coordenadaYFin){
        if(this.alquiler == null){
            System.out.println("No tiene ningún alquiler activo");
            return;
        }
        if(coordenadaXFin == null || coordenadaYFin == null || coordenadaXFin < 0 || coordenadaYFin < 0 ||  coordenadaXFin > coordenadaX || coordenadaYFin > coordenadaY){
            System.out.println("Las coordenadas indicadas no son correctas. Por favor, vuelva a intentarlo.");
            return;
        }
        if(this.alquiler.getVehiculo() instanceof Bicicleta || this.alquiler.getVehiculo() instanceof Patinete){
            System.out.println("Las bicicletas y patinetes se deben finalizar en una base. Por favor, vuelva a intentarlo.");
            return;
        }
        if(this.existeMotoEnCoordenadas(coordenadaXFin, coordenadaYFin)){
            System.out.println("Ya existe una moto aparcada en las coordenadas [" + coordenadaXFin + "," + coordenadaYFin + "]. Por favor, vuelva a intentarlo.");
            return;

        }
        System.out.println("Aparcando el vehículo en las coordenadas [" + coordenadaXFin + "," + coordenadaYFin + "]");
        this.alquiler.setCoordenadaXFinal(coordenadaXFin);
        this.alquiler.setCoordenadaYFinal(coordenadaYFin);
        Moto moto = (Moto) this.alquiler.getVehiculo();
        moto.setCoordenadaX(coordenadaXFin);
        moto.setCoordenadaY(coordenadaYFin);

        this.alquiler.getVehiculo().setAlquilado(false);
        this.alquiler.setFechaFin(new Date());
        double bateriaResidual = this.alquiler.getVehiculo().getBateria() - this.calcularConsumo(this.alquiler.obtenerDuracionEnMinutos());
        double importe = bateriaResidual <= 0.0 ? this.alquiler.getTarifa().getImporte() + 1 : this.alquiler.getTarifa().getImporte();
        double importeConDescuento = bateriaResidual <= 0.0 ? this.alquiler.getTarifa().calcularImporteConDescuento() + 1 : this.alquiler.getTarifa().calcularImporteConDescuento();
        this.saldo -= this.esPremium() ? importeConDescuento : importe;
        if(bateriaResidual <= 0.0){
            this.alquiler.getVehiculo().setBateria(0);
        } else {
            this.alquiler.getVehiculo().setBateria((int) bateriaResidual);
        }
        this.alquiler.setImporte(this.esPremium() ? importeConDescuento : importe);
        this.alquiler.getVehiculo().setTiempoDeUso(this.alquiler.obtenerDuracionTotal());
        this.historicoAlquileres.add(this.alquiler);
        System.out.println("El alquiler ha sido finalizado. El saldo actual es de " + this.saldo + " euros");
        this.alquiler = null;
    }

    /**
     * Visualiza el histórico de alquileres del usuario.
     */
    public void visualizarHistoricoAlquileres(){
        if(this.historicoAlquileres.isEmpty()){
            System.out.println("No tiene ningún alquiler en el histórico");
            return;
        }
        for(Alquiler alquiler : this.historicoAlquileres){
            String inicio = alquiler.getBaseInicial() != null ? alquiler.getBaseInicial().getNombre() : "[" + alquiler.getCoordenadaXInicial() + "," + alquiler.getCoordenadaYInicial() + "]";
            String fin = alquiler.getBaseFinal() != null ? alquiler.getBaseFinal().getNombre() : "[" + alquiler.getCoordenadaXFinal() + "," + alquiler.getCoordenadaYFinal() + "]";
            System.out.println("Alquiler del vehículo con matrícula " + alquiler.getVehiculo().getMatricula() + " desde " + alquiler.getFechaInicio() + " hasta " + alquiler.getFechaFin() + " con origen " + inicio + " hasta " + fin + " con un importe de " + alquiler.getImporte());
        }
    }

    /**
     * Visualiza el saldo actual del usuario.
     */
    public void visualizarSaldo(){
        System.out.println("El saldo actual es de " + this.saldo + " euros");
    }

    /**
     * Recarga el saldo del usuario con una cantidad específica.
     *
     * @param cantidad Cantidad a recargar.
     */
    public void recargarSaldo(Integer cantidad){
        if(cantidad <= 0){
            System.out.println("La cantidad a recargar no puede ser menor o igual a 0");
            return;
        }
        this.saldo += cantidad;
        System.out.println("El saldo ha sido recargado. El saldo actual es de " + this.saldo + " euros");
    }

    /**
     * Informa de un problema en un vehículo específico.
     *
     * @param matricula    Matrícula del vehículo.
     * @param descripcion Descripción del problema.
     */
    public void informarProblemaVehiculo(String matricula, String descripcion){
        if(matricula.isEmpty()){
            System.out.println("La matrícula no puede estar vacía");
            return;
        }
        Vehiculo vehiculo = vehiculos.stream().filter(p -> p.getMatricula().equals(matricula)).findFirst().orElse(null);
        if(vehiculo == null){
            System.out.println("No existe ningún vehículo con la matrícula " + matricula);
            return;
        }
        vehiculo.getAvisos().add(descripcion);
        System.out.println("El vehículo con matrícula " + matricula + " ha sido marcado como averiado");

    }

    /**
     * Informa de un problema en una base específica.
     *
     * @param nombre       Nombre de la base.
     * @param descripcion Descripción del problema.
     */
    public void informarProblemaBase(String nombre, String descripcion){
        if(nombre.isEmpty()){
            System.out.println("El nombre de la base no puede estar vacío");
            return;
        }
        Base base = buscarBase(nombre);
        if(base == null){
            System.out.println("No existe ninguna base con el nombre " + nombre);
            return;
        }
        base.getAvisos().add(descripcion);
        System.out.println("La base " + nombre + " ha sido marcada como averiada");
    }

    /**
     * Ubica el vehículo más cercano al usuario según el tipo y coordenadas iniciales.
     *
     * @param tipoVehiculo     Tipo de vehículo a buscar.
     * @param coordenadaXInicio Coordenada X inicial.
     * @param coordenadaYInicio Coordenada Y inicial.
     */
    public void ubicarVehiculoMasCercano(String tipoVehiculo, Integer coordenadaXInicio, Integer coordenadaYInicio){

        TipoVehiculo tipo = TipoVehiculo.get(tipoVehiculo);
        if(tipo == null){
            System.out.println("El tipo de vehículo no es correcto. Por favor, vuelva a intentarlo. Los tipos disponibles son: " + TipoVehiculo.BICICLETA.name() + ", " + TipoVehiculo.PATINETE.name() + ", " + TipoVehiculo.MOTOPEQUENA.name() + " y " + TipoVehiculo.MOTOPEQUENA.name());
            return;
        }
        if(coordenadaXInicio == null || coordenadaYInicio == null || coordenadaXInicio < 0 || coordenadaYInicio < 0 || coordenadaXInicio > coordenadaX || coordenadaYInicio > coordenadaY){
            System.out.println("Las coordenadas indicadas no son correctas. Por favor, vuelva a intentarlo.");
            return;
        }
        if(TipoVehiculo.BICICLETA.equals(tipo) || TipoVehiculo.PATINETE.equals(tipo)){
            Base baseMasCercana = null;
            Double distanciaMasCercana = null;
            List<Vehiculo> vehiculoEnBase = null;
            for(Base base : bases){
                if(base.getAvisos().isEmpty()){
                    Double distancia = calcularDistancia(coordenadaXInicio, coordenadaYInicio, base.getCoordenadaX(), base.getCoordenadaY());
                    if(distanciaMasCercana == null || distancia < distanciaMasCercana && (vehiculoEnBase != null && !obtenerVehiculoEnBase(tipo, base.getNombre()).isEmpty())){
                        distanciaMasCercana = distancia;
                        baseMasCercana = base;
                        vehiculoEnBase = obtenerVehiculoEnBase(tipo, base.getNombre());
                    }
                }
            }
            if(baseMasCercana != null) {
                for (Vehiculo vehiculo : vehiculoEnBase) {
                    if (vehiculo instanceof Bicicleta) {
                        System.out.println("Bicicleta disponible con matrícula: " + vehiculo.getMatricula() + " con batería al " + vehiculo.getBateria() + "% en la base " + baseMasCercana.getNombre());
                    } else if (vehiculo instanceof Patinete) {
                        System.out.println("Patinete disponible con matrícula: " + vehiculo.getMatricula() + " con batería al " + vehiculo.getBateria() + "% en la base " + baseMasCercana.getNombre());
                    }
                }
            } else {
                System.out.println("No existen vehículos disponibles");
            }
        }  else if (TipoVehiculo.MOTOPEQUENA.equals(tipo) || TipoVehiculo.MOTOGRANDE.equals(tipo)) {
            List<Vehiculo> motosDisponibles;
            if(TipoVehiculo.MOTOPEQUENA.equals(tipo)){
                motosDisponibles = vehiculos.stream().filter(p -> p instanceof Moto && !p.isAlquilado() && TipoMoto.PEQUENA.equals(((Moto) p).getTipo()) && p.getAvisos().isEmpty()).toList();
            } else {
                motosDisponibles = vehiculos.stream().filter(p -> p instanceof Moto && !p.isAlquilado() && TipoMoto.GRANDE.equals(((Moto) p).getTipo()) && p.getAvisos().isEmpty()).toList();
            }
            Moto motoMasCercana = null;
            Double distanciaMasCercana = null;
            for(Vehiculo motosDisponible : motosDisponibles){
                Moto moto = (Moto) motosDisponible;
                Double distancia = calcularDistancia(coordenadaXInicio, coordenadaYInicio, moto.getCoordenadaX(), moto.getCoordenadaY());
                if(distanciaMasCercana == null || distancia < distanciaMasCercana){
                    distanciaMasCercana = distancia;
                    motoMasCercana = moto;
                }
            }
            if(motoMasCercana != null){
                System.out.println("Moto " + motoMasCercana.getMatricula() + " está disponible en las coordenadas [" + motoMasCercana.getCoordenadaX() + "," + motoMasCercana.getCoordenadaY() + "]" + " con batería al " + motoMasCercana.getBateria() + "%");
            } else {
                System.out.println("No existen motos disponibles");
            }
        } else {
            System.out.println("Tipo de vehículo " + tipoVehiculo + " no disponible ");
        }

    }

    /**
     * Devuelve una representación en forma de String del usuario.
     *
     * @return String que representa al usuario.
     */
    @Override
    public String toString() {
        return "Usuario {" +
                "dni='" + this.getDni() + '\'' +
                ", nombre='" + this.getNombre() + '\'' +
                ", apellidos='" + this.getApellidos() + '\'' +
                ", telefono=" + this.getNumeroTelefono() +
                ", email='" + this.getEmail() + '\'' +
                ", tipo='" + this.getTipo() + '\'' +
                ", saldo='" + saldo + '\'' +
                '}';
    }

    /**
     * Calcula la distancia entre dos puntos dados por sus coordenadas.
     *
     * @param coordenadaX    Coordenada X inicial.
     * @param coordenadaY    Coordenada Y inicial.
     * @param coordenadaXFin Coordenada X final.
     * @param coordenadaYFin Coordenada Y final.
     * @return Distancia calculada.
     */
    private Double calcularDistancia(int coordenadaX, int coordenadaY, int coordenadaXFin, int coordenadaYFin){
        return Math.sqrt(Math.pow(coordenadaX - coordenadaXFin, 2) + Math.pow(coordenadaY - coordenadaYFin, 2));
    }

    /**
     * Obtiene los vehículos disponibles en una base específica según el tipo.
     *
     * @param tipoVehiculo Tipo de vehículo a buscar.
     * @param nombreBase   Nombre de la base.
     * @return Lista de vehículos disponibles.
     */
    private List<Vehiculo> obtenerVehiculoEnBase(TipoVehiculo tipoVehiculo, String nombreBase) {
        List<Vehiculo> vehiculosBase = new ArrayList<>();
        if(TipoVehiculo.BICICLETA.equals(tipoVehiculo)){
            vehiculosBase = vehiculos.stream().filter(p -> p.getBase() != null && p.getBase().getNombre().equals(nombreBase) && !p.isAlquilado() && !p.isReservado() && p.getAvisos().isEmpty() && (p instanceof Bicicleta)).toList();
            if(!vehiculosBase.isEmpty()){
                return vehiculosBase;
            }
        } else if(TipoVehiculo.PATINETE.equals(tipoVehiculo)){
            vehiculosBase = vehiculos.stream().filter(p -> p.getBase() != null && p.getBase().getNombre().equals(nombreBase) && !p.isAlquilado() && !p.isReservado() && p.getAvisos().isEmpty() && (p instanceof Patinete)).toList();
            if(!vehiculosBase.isEmpty()){
                return vehiculosBase;
            }
        }

        return vehiculosBase;
    }

    /**
     * Realiza validaciones generales previas a una operación.
     *
     * @return true si alguna validación falla, false en caso contrario.
     */
    private boolean validacionesGeneralesPrevias() {
        if(this.saldo <= 0){
            System.out.println("No tiene saldo suficiente para alquilar un vehículo");
            return true;
        }
        if(this.alquiler != null){
            System.out.println("Ya tiene un alquiler/reserva creada. Por favor, finalice/confirme el actual antes de alquilar/reservar otro vehículo.");
            return true;
        }
        return false;
    }

    /**
     * Valida los datos de una base específica.
     *
     * @param nombreBase Nombre de la base.
     * @return true si la validación falla, false en caso contrario.
     */
    private boolean validarDatosBase(String nombreBase) {
        Base baseIncio = buscarBase(nombreBase);
        if(nombreBase.isEmpty() || baseIncio == null){
            System.out.println("Los datos de la bases indicadas no son correctos. Por favor, vuelva a intentarlo.");
            return true;
        }
        if(!baseIncio.getAvisos().isEmpty()){
            System.out.println("Fallos mecánicos registrados en la base " + nombreBase + " Base NO DISPONIBLE");
            if(baseIncio.getPeriodoInactividadInicio() != null && baseIncio.getPeriodoInactividadFin() != null){
                System.out.println("La base " + nombreBase + " estará inactiva desde " + baseIncio.getPeriodoInactividadInicio() + " hasta " + baseIncio.getPeriodoInactividadFin());
            }
            return true;
        }
        return false;
    }

    /**
     * Realiza el alquiler de un vehículo en una base específica.
     *
     * @param vehiculosBase Lista de vehículos disponibles en la base.
     * @param inicio        Nombre de la base de inicio.
     * @param tipoVehiculo  Tipo de vehículo a alquilar.
     * @param esReserva     Indica si es una reserva.
     * @return true si el alquiler falla, false en caso contrario.
     */
    private boolean alquilarEnBase(List<Vehiculo> vehiculosBase, String inicio, TipoVehiculo tipoVehiculo, boolean esReserva) {
        if(vehiculosBase.isEmpty()){
            System.out.println("No existen vehículos disponibles en la base " + inicio);
            return true;
        } else {
            Vehiculo vehiculo = vehiculosBase.get(0);
            Tarifa tarifa = tarifas.stream().filter(p -> p.getTipoVehiculo().equals(tipoVehiculo)).findFirst().orElse(null);
            if(tarifa == null){
                System.out.println("No existe tarifa para el tipo de vehículo " + tipoVehiculo);
                return true;
            }
            vehiculo.getBase().setPlazas(vehiculo.getBase().getPlazas() + 1);
            vehiculo.getBase().setContadorDeUsos(vehiculo.getBase().getContadorDeUsos() + 1);
            if(esReserva){
                vehiculo.setReservado(true);
                vehiculo.setFechaReserva(new Date());
                this.alquiler = new Alquiler(vehiculo, vehiculo.getBase(), null, null, null, null, null, tarifa, this, null);
                System.out.println("El vehículo con matrícula " + vehiculo.getMatricula() + " en la base " + inicio + " ha sido reservado por el usuario con DNI " + this.getDni());
            } else {
                vehiculo.setAlquilado(true);
                this.alquiler = new Alquiler(vehiculo, vehiculo.getBase(), null, null, null, null, null, tarifa, this, new Date());
                System.out.println("El vehículo con matrícula " + vehiculo.getMatricula() + " en la base " + inicio + " ha sido alquilado por el usuario con DNI " + this.getDni());
            }
        }
        return false;
    }

    /**
     * Realiza el alquiler de una moto en unas coordenadas específicas.
     *
     * @param motosDisponibles Lista de motos disponibles.
     * @param coordenadaXInicio Coordenada X de inicio.
     * @param coordenadaYInicio Coordenada Y de inicio.
     * @param tipoVehiculo      Tipo de vehículo a alquilar.
     * @param esReserva         Indica si es una reserva.
     * @return true si el alquiler falla, false en caso contrario.
     */
    private boolean alquilarMoto(List<Vehiculo> motosDisponibles, int coordenadaXInicio, int coordenadaYInicio, TipoVehiculo tipoVehiculo, boolean esReserva) {
        if(motosDisponibles.isEmpty()){
            System.out.println("No existen vehículos disponibles en las coordenadas [" + coordenadaXInicio + "," + coordenadaYInicio + "]");
            return true;
        } else {
            Moto moto = (Moto) motosDisponibles.get(0);
            Tarifa tarifa = tarifas.stream().filter(p -> p.getTipoVehiculo().equals(tipoVehiculo)).findFirst().orElse(null);
            if(tarifa == null){
                System.out.println("No existe tarifa para el tipo de vehículo " + tipoVehiculo);
                return true;
            }
            if(esReserva) {
                moto.setReservado(true);
                moto.setFechaReserva(new Date());
                this.alquiler = new Alquiler(moto, null, null, coordenadaXInicio, coordenadaYInicio, null, null, tarifa, this, null);
                System.out.println("El vehículo con matrícula " + moto.getMatricula() + " en las coordenadas [" + coordenadaXInicio + "," + coordenadaYInicio + "]" + " ha sido reservado por el usuario con DNI " + this.getDni());
            } else {
                moto.setAlquilado(true);
                this.alquiler = new Alquiler(moto, null, null, coordenadaXInicio, coordenadaYInicio, null, null, tarifa, this, new Date());
                System.out.println("El vehículo con matrícula " + moto.getMatricula() + " en las coordenadas [" + coordenadaXInicio + "," + coordenadaYInicio + "]" + " ha sido alquilado por el usuario con DNI " + this.getDni());
            }
        }
        return false;
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
     * Calcula el consumo de batería según la duración del uso.
     *
     * @param duracion Duración en minutos.
     * @return Consumo calculado.
     */
    private Double calcularConsumo(Long duracion){
        if(duracion > 0){
            Vehiculo vehiculo = this.alquiler.getVehiculo();
            if(vehiculo instanceof Bicicleta){
                return Bicicleta.CONSUMO_BICICLETA * duracion;
            } else if(vehiculo instanceof Patinete){
                return Patinete.CONSUMO_PATINETE * duracion;
            } else if(vehiculo instanceof Moto moto){
                if(TipoMoto.GRANDE.equals(moto.getTipo())){
                    return Moto.CONSUMO_MOTO_GRANDE * duracion;
                } else if(TipoMoto.PEQUENA.equals(moto.getTipo())){
                    return Moto.CONSUMO_MOTO_PEQUENA * duracion;
                }
            }
        }
        return 100.0;
    }

    /**
     * Obtiene la duración en minutos desde una fecha de reserva.
     *
     * @param fechaReserva Fecha de reserva.
     * @return Duración en minutos.
     */
    private Long obtenerDuracion(Date fechaReserva){
        long diferencia = new Date().getTime() - fechaReserva.getTime();
        long segundos = diferencia / 1000;
        return segundos / 60;
    }

    /**
     * Verifica si existe una bicicleta en las coordenadas dadas.
     *
     * @param coordenadaXBase Coordenada X de la bicicleta.
     * @param coordenadaYBase Coordenada Y de la bicicleta.
     * @return true si existe una bicicleta en las coordenadas, false en caso contrario.
     */
    private boolean existeMotoEnCoordenadas(int coordenadaXBase, int coordenadaYBase){
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo instanceof Moto && ((Moto) vehiculo).getCoordenadaX() == coordenadaXBase && ((Moto) vehiculo).getCoordenadaY() == coordenadaYBase) {
                return true;
            }
        }
        return false;
    }
}
