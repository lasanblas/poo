//package es.uned.movilidad;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Scanner;
//
//import static es.uned.movilidad.MovilidadApplication.bases;
//import static es.uned.movilidad.MovilidadApplication.coordenadaX;
//import static es.uned.movilidad.MovilidadApplication.coordenadaY;
//import static es.uned.movilidad.MovilidadApplication.tarifas;
//import static es.uned.movilidad.MovilidadApplication.vehiculos;
//
//public class Usuario extends Persona {
//
//    private TipoUsuario tipo;
//    private Double saldo;
//    private Alquiler alquiler;
//    private List<Alquiler> historicoAlquileres = new ArrayList<>();
//
//    public Usuario(String dni, String nombre, String apellidos, Long numeroTelefono, String email, TipoUsuario tipo, Double saldo) {
//        super(dni, nombre, apellidos, numeroTelefono, email);
//        this.tipo = tipo;
//        this.saldo = saldo;
//    }
//
//    private boolean esPremium() {
//        return tipo == TipoUsuario.PREMIUM;
//    }
//
//    public void setTipo(TipoUsuario tipo) {
//        this.tipo = tipo;
//    }
//
//    private TipoUsuario getTipo() {
//        return tipo;
//    }
//
//    private Double getSaldo() {
//        return saldo;
//    }
//
//    private void setSaldo(Double saldo) {
//        this.saldo = saldo;
//    }
//
//    public void visualizarVehiculosDisponibles(){
//        if(vehiculos.isEmpty()){
//            System.out.println("No existen vehículos disponibles");
//            return;
//        }
//        List<Vehiculo> vehiculosReservados = vehiculos.stream().filter(p -> !p.isAlquilado() && p.isReservado()).toList();
//        for(Vehiculo vehiculo : vehiculosReservados){
//            int minutos = (int) ((new Date().getTime() - vehiculo.getFechaReserva().getTime()) / 1000 / 60);
//            if(minutos > 20){
//                vehiculo.setReservado(false);
//                System.out.println("El vehículo con matrícula " + vehiculo.getMatricula() + " ha sido liberado tras 20 minutos de reserva");
//            }
//        }
//        for(Base base : bases){
//            if(base.getAvisos().isEmpty()){
//                System.out.println("Visualizando los vehículos disponibles de la base " + base.getNombre());
//                List<Vehiculo> vehiculosBase = vehiculos.stream().filter(p -> p.getBase().equals(base) && !p.isAlquilado()).toList();
//                for(Vehiculo vehiculo : vehiculosBase){
//                    TipoVehiculo tipo = vehiculo instanceof Bicicleta ? TipoVehiculo.BICICLETA : TipoVehiculo.PATINETE;
//                    System.out.println(tipo + " con matrícula " + vehiculo.getMatricula() + " está disponible con batería al " + vehiculo.getBateria() + "%");
//                }
//                System.out.println("Huecos disponibles en la base " + base.getNombre() + " es de " + base.getPlazas());
//            } else {
//                System.out.println("Fallos mecánicos registrados en la base " + base.getNombre() + "Base NO DISPONIBLE");
//            }
//        }
//        List<Vehiculo> motosDisponibles = vehiculos.stream().filter(p -> p instanceof Moto && !p.isAlquilado()).toList();
//        for(Vehiculo vehiculo : motosDisponibles){
//            Moto moto = (Moto) vehiculo;
//            System.out.println("Moto " + moto.getTipo() + " con matrícula " + moto.getMatricula()+ "en las coordenadas [" + moto.getCoordenadaX() + "," + moto.getCoordenadaY() + "]" + " está disponible con batería al " + moto.getBateria() + "%");
//        }
//
//    }
//
//    public void alquilarVehiculo(String inicio, TipoVehiculo tipoVehiculo, Integer coordenadaXInicio, Integer coordenadaYInicio){
//        if(this.saldo <= 0){
//            System.out.println("No tiene saldo suficiente para alquilar un vehículo");
//            return;
//        }
//        if(this.alquiler != null){
//            System.out.println("Ya tiene un alquiler/reserva creada. Por favor, finalice/confirme el actual antes de alquilar/reservar otro vehículo.");
//            return;
//        }
//        if(TipoVehiculo.BICICLETA.equals(tipoVehiculo) || TipoVehiculo.PATINETE.equals(tipoVehiculo)){
//            Base baseIncio = buscarBase(inicio);
//            if(inicio.isEmpty() || baseIncio == null){
//                System.out.println("Los datos de la bases indicadas no son correctos. Por favor, vuelva a intentarlo.");
//                return;
//            }
//            if(!baseIncio.getAvisos().isEmpty()){
//                System.out.println("Fallos mecánicos registrados en la base " + inicio + "Base NO DISPONIBLE");
//                return;
//            }
//            if(this.esPremium()){
//                List<Vehiculo> vehiculosBase = vehiculos.stream().filter(p -> p.getBase().getNombre().equals(inicio) && !p.isAlquilado() && !p.isReservado()).toList();
//                if (alquilarEnBase(vehiculosBase, inicio, tipoVehiculo, false)) return;
//            } else {
//                List<Vehiculo> vehiculosBase = vehiculos.stream().filter(p -> p.getBase().getNombre().equals(inicio) && !p.isAlquilado() && !p.isReservado() && p.getBateria() >= 20).toList();
//                if (alquilarEnBase(vehiculosBase, inicio, tipoVehiculo, false)) return;
//            }
//            System.out.println("Alquilando " + tipoVehiculo + " desde la base" + inicio);
//        } else if(TipoVehiculo.MOTOPEQUENA.equals(tipoVehiculo) || TipoVehiculo.MOTOGRANDE.equals(tipoVehiculo)){
//            if(coordenadaXInicio == null || coordenadaYInicio == null || coordenadaXInicio < 0 || coordenadaYInicio < 0 || coordenadaXInicio > coordenadaX || coordenadaYInicio > coordenadaY){
//                System.out.println("Las coordenadas indicadas no son correctas. Por favor, vuelva a intentarlo.");
//                return;
//            }
//            else {
//                if(this.esPremium()){
//                    if(TipoVehiculo.MOTOPEQUENA.equals(tipoVehiculo)){
//                        List<Vehiculo> motosDisponibles = vehiculos.stream().filter(p -> p instanceof Moto && !p.isAlquilado() && !p.isReservado() && TipoMoto.GRANDE.equals(((Moto) p).getTipo())).toList();
//                        if (alquilarMoto(motosDisponibles, coordenadaXInicio, coordenadaYInicio, TipoVehiculo.MOTOGRANDE, false)) return;
//                    } else {
//                        List<Vehiculo> motosDisponibles = vehiculos.stream().filter(p -> p instanceof Moto && !p.isAlquilado() && !p.isReservado() && TipoMoto.PEQUENA.equals(((Moto) p).getTipo())).toList();
//                        if (alquilarMoto(motosDisponibles, coordenadaXInicio, coordenadaYInicio, TipoVehiculo.MOTOPEQUENA, false)) return;
//                    }
//                } else {
//                    if(TipoVehiculo.MOTOPEQUENA.equals(tipoVehiculo)){
//                        List<Vehiculo> motosDisponibles = vehiculos.stream().filter(p -> p instanceof Moto && !p.isAlquilado() && !p.isReservado() && p.getBateria() >= 20 && TipoMoto.GRANDE.equals(((Moto) p).getTipo())).toList();
//                        if (alquilarMoto(motosDisponibles, coordenadaXInicio, coordenadaYInicio, TipoVehiculo.MOTOGRANDE, false)) return;
//                    } else {
//                        List<Vehiculo> motosDisponibles = vehiculos.stream().filter(p -> p instanceof Moto && !p.isAlquilado() && !p.isReservado() && p.getBateria() >= 20 && TipoMoto.PEQUENA.equals(((Moto) p).getTipo())).toList();
//                        if (alquilarMoto(motosDisponibles, coordenadaXInicio, coordenadaYInicio,  TipoVehiculo.MOTOPEQUENA, false)) return;
//                    }
//                }
//            }
//            System.out.println("Alquilando " + tipoVehiculo + " desde las coordenadas [" + coordenadaXInicio + "," + coordenadaYInicio + "]");
//        } else {
//            System.out.println("Tipo de vehículo " + tipoVehiculo + " no disponible ");
//
//        }
//    }
//
//    public void alquilarVehiculoInteractivo(){
//        if(this.saldo <= 0){
//            System.out.println("No tiene saldo suficiente para alquilar un vehículo");
//            return;
//        }
//        Scanner scn = new Scanner(System.in);
//        System.out.println("Indique el tipo de vehículo a alquilar: " + TipoVehiculo.MOTOPEQUENA + " || " + TipoVehiculo.MOTOGRANDE + " || " + TipoVehiculo.BICICLETA + " || " + TipoVehiculo.PATINETE);
//        String tipoVehiculo = scn.next();
//        if(TipoVehiculo.BICICLETA.name().equals(tipoVehiculo) || TipoVehiculo.PATINETE.name().equals(tipoVehiculo)){
//            System.out.println("Indique nombre de la base inicio.");
//            String inicio = scn.next();
//            System.out.println("Indique nombre de la base final.");
//            String fin = scn.next();
//            Base baseIncio = buscarBase(inicio);
//            Base baseFin = buscarBase(fin);
//            if(inicio.isEmpty() || fin.isEmpty() || inicio.equals(fin) || baseIncio == null || baseFin == null){
//                System.out.println("Los datos de la bases indicadas no son correctos. Por favor, vuelva a intentarlo.");
//                return;
//            }
//            if(!baseIncio.getAvisos().isEmpty()){
//                System.out.println("Fallos mecánicos registrados en la base " + inicio + "Base NO DISPONIBLE");
//                return;
//            }
//            if(!baseFin.getAvisos().isEmpty()){
//                System.out.println("Fallos mecánicos registrados en la base " + fin + "Base NO DISPONIBLE");
//                return;
//            }
//            if(this.esPremium()){
//                List<Vehiculo> vehiculosBase = vehiculos.stream().filter(p -> p.getBase().getNombre().equals(inicio) && !p.isAlquilado() && !p.isReservado() && p.getBateria() >= 10).toList();
//                if (alquilarEnBase(vehiculosBase, inicio, TipoVehiculo.BICICLETA.name().equals(tipoVehiculo) ? TipoVehiculo.BICICLETA : TipoVehiculo.PATINETE, false)) return;
//            } else {
//                List<Vehiculo> vehiculosBase = vehiculos.stream().filter(p -> p.getBase().getNombre().equals(inicio) && !p.isAlquilado() && !p.isReservado() && p.getBateria() >= 20).toList();
//                if (alquilarEnBase(vehiculosBase, inicio, TipoVehiculo.BICICLETA.name().equals(tipoVehiculo) ? TipoVehiculo.BICICLETA : TipoVehiculo.PATINETE, false)) return;
//            }
//            System.out.println("Alquilando " + tipoVehiculo + " desde " + inicio + " hasta " + fin);
//        } else if(TipoVehiculo.MOTOPEQUENA.name().equals(tipoVehiculo) || TipoVehiculo.MOTOGRANDE.name().equals(tipoVehiculo)){
//            System.out.println("Indique la coordenada X inicio.");
//            int coordenadaXInicio = scn.nextInt();
//            System.out.println("Indique la coordenada Y inicio.");
//            int coordenadaYInicio = scn.nextInt();
//            System.out.println("Indique la coordenada X final.");
//            int coordenadaXFin = scn.nextInt();
//            System.out.println("Indique la coordenada Y final.");
//            int coordenadaYFin = scn.nextInt();
//            if(coordenadaXInicio <= 0 || coordenadaYInicio <= 0 || coordenadaXFin <= 0 || coordenadaYFin <= 0 || coordenadaXInicio == coordenadaXFin || coordenadaYInicio == coordenadaYFin
//                    || coordenadaXInicio > coordenadaX || coordenadaYInicio > coordenadaY || coordenadaXFin > coordenadaX || coordenadaYFin > coordenadaY){
//                System.out.println("Las coordenadas indicadas no son correctas. Por favor, vuelva a intentarlo.");
//                return;
//            }
//            else {
//                if(this.esPremium()){
//                    if(TipoVehiculo.MOTOPEQUENA.name().equals(tipoVehiculo)){
//                        List<Vehiculo> motosDisponibles = vehiculos.stream().filter(p -> p instanceof Moto && !p.isAlquilado() && !p.isReservado() && p.getBateria() >= 10 && TipoMoto.GRANDE.equals(((Moto) p).getTipo())).toList();
//                        if (alquilarMoto(motosDisponibles, coordenadaXInicio, coordenadaYInicio, TipoVehiculo.MOTOGRANDE, false)) return;
//                    } else {
//                        List<Vehiculo> motosDisponibles = vehiculos.stream().filter(p -> p instanceof Moto && !p.isAlquilado() && !p.isReservado() && p.getBateria() >= 10 && TipoMoto.PEQUENA.equals(((Moto) p).getTipo())).toList();
//                        if (alquilarMoto(motosDisponibles, coordenadaXInicio, coordenadaYInicio, TipoVehiculo.MOTOPEQUENA, false)) return;
//                    }
//                } else {
//                    if(TipoVehiculo.MOTOPEQUENA.name().equals(tipoVehiculo)){
//                        List<Vehiculo> motosDisponibles = vehiculos.stream().filter(p -> p instanceof Moto && !p.isAlquilado() && !p.isReservado() && p.getBateria() >= 20 && TipoMoto.GRANDE.equals(((Moto) p).getTipo())).toList();
//                        if (alquilarMoto(motosDisponibles, coordenadaXInicio, coordenadaYInicio, TipoVehiculo.MOTOGRANDE, false)) return;
//                    } else {
//                        List<Vehiculo> motosDisponibles = vehiculos.stream().filter(p -> p instanceof Moto && !p.isAlquilado() && !p.isReservado() && p.getBateria() >= 20 && TipoMoto.PEQUENA.equals(((Moto) p).getTipo())).toList();
//                        if (alquilarMoto(motosDisponibles, coordenadaXInicio,  coordenadaYInicio,  TipoVehiculo.MOTOPEQUENA, false)) return;
//                    }
//                }
//            }
//            System.out.println("Alquilando " + tipoVehiculo + " desde las coordenadas [" + coordenadaXInicio + "," + coordenadaYInicio + "]"+ " hasta [" + coordenadaXFin + "," + coordenadaYFin + "]");
//        } else {
//            System.out.println("Tipo de vehículo " + tipoVehiculo + " no disponible ");
//
//        }
//    }
//
//    public void reservarAlquiler(String inicio, TipoVehiculo tipoVehiculo, Integer coordenadaXInicio, Integer coordenadaYInicio){
//        if(this.saldo <= 0){
//            System.out.println("No tiene saldo suficiente para alquilar un vehículo");
//            return;
//        }
//        if(this.alquiler != null){
//            System.out.println("Ya tiene un alquiler/reserva creada. Por favor, finalice/confirme/elimine el actual antes de reservar otro vehículo.");
//            return;
//        }
//        if(TipoVehiculo.BICICLETA.equals(tipoVehiculo) || TipoVehiculo.PATINETE.equals(tipoVehiculo)){
//            Base baseIncio = buscarBase(inicio);
//            if(inicio.isEmpty() || baseIncio == null ){
//                System.out.println("Los datos de la bases indicadas no son correctos. Por favor, vuelva a intentarlo.");
//                return;
//            }
//            if(!baseIncio.getAvisos().isEmpty()){
//                System.out.println("Fallos mecánicos registrados en la base " + inicio + "Base NO DISPONIBLE");
//                return;
//            }
//            if(this.esPremium()){
//                List<Vehiculo> vehiculosBase = vehiculos.stream().filter(p -> p.getBase().getNombre().equals(inicio) && !p.isAlquilado() && !p.isReservado()).toList();
//                if (alquilarEnBase(vehiculosBase, inicio, tipoVehiculo, true)) return;
//            } else {
//                List<Vehiculo> vehiculosBase = vehiculos.stream().filter(p -> p.getBase().getNombre().equals(inicio) && !p.isAlquilado() && !p.isReservado() && p.getBateria() >= 20).toList();
//                if (alquilarEnBase(vehiculosBase, inicio, tipoVehiculo, true)) return;
//            }
//            System.out.println("Alquilando " + tipoVehiculo + " desde la base" + inicio);
//        } else if(TipoVehiculo.MOTOPEQUENA.equals(tipoVehiculo) || TipoVehiculo.MOTOGRANDE.equals(tipoVehiculo)){
//            if(coordenadaXInicio == null || coordenadaYInicio == null || coordenadaXInicio < 0 || coordenadaYInicio < 0 || coordenadaXInicio > coordenadaX || coordenadaYInicio > coordenadaY){
//                System.out.println("Las coordenadas indicadas no son correctas. Por favor, vuelva a intentarlo.");
//                return;
//            }
//            else {
//                if(this.esPremium()){
//                    if(TipoVehiculo.MOTOPEQUENA.equals(tipoVehiculo)){
//                        List<Vehiculo> motosDisponibles = vehiculos.stream().filter(p -> p instanceof Moto && !p.isAlquilado() && !p.isReservado() && TipoMoto.GRANDE.equals(((Moto) p).getTipo())).toList();
//                        if (alquilarMoto(motosDisponibles, coordenadaXInicio, coordenadaYInicio, TipoVehiculo.MOTOGRANDE, true)) return;
//                    } else {
//                        List<Vehiculo> motosDisponibles = vehiculos.stream().filter(p -> p instanceof Moto && !p.isAlquilado() && !p.isReservado() && TipoMoto.PEQUENA.equals(((Moto) p).getTipo())).toList();
//                        if (alquilarMoto(motosDisponibles, coordenadaXInicio,  coordenadaYInicio, TipoVehiculo.MOTOPEQUENA, true)) return;
//                    }
//                } else {
//                    if(TipoVehiculo.MOTOPEQUENA.equals(tipoVehiculo)){
//                        List<Vehiculo> motosDisponibles = vehiculos.stream().filter(p -> p instanceof Moto && !p.isAlquilado() && !p.isReservado() && p.getBateria() >= 20 && TipoMoto.GRANDE.equals(((Moto) p).getTipo())).toList();
//                        if (alquilarMoto(motosDisponibles, coordenadaXInicio,  coordenadaYInicio, TipoVehiculo.MOTOGRANDE, true)) return;
//                    } else {
//                        List<Vehiculo> motosDisponibles = vehiculos.stream().filter(p -> p instanceof Moto && !p.isAlquilado() && !p.isReservado() && p.getBateria() >= 20 && TipoMoto.PEQUENA.equals(((Moto) p).getTipo())).toList();
//                        if (alquilarMoto(motosDisponibles, coordenadaXInicio, coordenadaYInicio, TipoVehiculo.MOTOPEQUENA, true)) return;
//                    }
//                }
//            }
//            System.out.println("Reservando " + tipoVehiculo + " desde las coordenadas [" + coordenadaXInicio + "," + coordenadaYInicio + "]");
//        } else {
//            System.out.println("Tipo de vehículo " + tipoVehiculo + " no disponible ");
//
//        }
//    }
//
//    public void confirmarReserva(){
//        if(this.alquiler == null){
//            System.out.println("No tiene ninguna reserva");
//            return;
//        }
//        if(!this.alquiler.getVehiculo().isReservado()){
//            System.out.println("El vehículo con matrícula " + this.alquiler.getVehiculo().getMatricula() + " ha sido liberado tras exceder los 20 minutos de reserva. Se ha cancelado la reserva");
//            this.alquiler = null;
//            return;
//        }
//        this.alquiler.getVehiculo().setAlquilado(true);
//        this.alquiler.getVehiculo().setReservado(false);
//        this.alquiler.setFechaInicio(new Date());
//        this.saldo -= this.esPremium() ? this.alquiler.getTarifa().calcularImporteConDescuento() : this.alquiler.getTarifa().getImporte();
//        System.out.println("Reserva confirmada. Alquiler del vehículo iniciado.");
//    }
//
//    public void eliminarReserva(){
//        if(this.alquiler == null){
//            System.out.println("No tiene ninguna reserva");
//            return;
//        }
//        this.alquiler.getVehiculo().setReservado(false);
//        this.alquiler = null;
//        System.out.println("Reserva eliminada");
//    }
//
//    public void finalizarAlquiler(String nombreBaseFin, Integer coordenadaXFin, Integer coordenadaYFin){
//        if(this.alquiler == null){
//            System.out.println("No tiene ningún alquiler activo");
//            return;
//        }
//        if(nombreBaseFin != null){
//            Base baseFin = buscarBase(nombreBaseFin);
//            if(baseFin == null){
//                System.out.println("Los datos de la bases indicadas no son correctos. Por favor, vuelva a intentarlo.");
//                return;
//            }
//            if(!baseFin.getAvisos().isEmpty()){
//                System.out.println("Fallos mecánicos registrados en la base " + nombreBaseFin + "Base NO DISPONIBLE");
//                return;
//            }
//            if(baseFin.getPlazas() == 0){
//                System.out.println("No existen plazas disponibles para aparcar en la base " + nombreBaseFin + "Base NO DISPONIBLE");
//                return;
//            }
//            System.out.println("Aparcando el vehículo en la base " + nombreBaseFin);
//            baseFin.setPlazas(baseFin.getPlazas() - 1);
//            this.alquiler.getVehiculo().setBase(baseFin);
//            this.alquiler.setBaseFinal(baseFin);
//        } else {
//            if(coordenadaXFin == null || coordenadaYFin == null || coordenadaXFin < 0 || coordenadaYFin < 0 ||  coordenadaXFin > coordenadaX || coordenadaYFin > coordenadaY){
//                System.out.println("Las coordenadas indicadas no son correctas. Por favor, vuelva a intentarlo.");
//                return;
//            }
//            System.out.println("Aparcando el vehículo en las coordenadas [" + coordenadaXFin + "," + coordenadaYFin + "]");
//            this.alquiler.setCoordenadaXFinal(coordenadaXFin);
//            this.alquiler.setCoordenadaYFinal(coordenadaYFin);
//            Moto moto = (Moto) this.alquiler.getVehiculo();
//            moto.setCoordenadaX(coordenadaXFin);
//            moto.setCoordenadaY(coordenadaYFin);
//        }
//
//        this.alquiler.getVehiculo().setAlquilado(false);
//        this.alquiler.setFechaFin(new Date());
//        Double consumoBateria = this.alquiler.getVehiculo().getBateria() - this.calcularConsumo(this.alquiler.obtenerDuracion());
//        double bateriaResidual = this.alquiler.getVehiculo().getBateria() - consumoBateria;
//        double importe = bateriaResidual <= 0.0 ? this.alquiler.getTarifa().getImporte() - 1 : this.alquiler.getTarifa().getImporte();
//        double importeConDescuento = bateriaResidual <= 0.0 ? this.alquiler.getTarifa().calcularImporteConDescuento() - 1 : this.alquiler.getTarifa().calcularImporteConDescuento();
//        this.saldo -= this.esPremium() ? importeConDescuento : importe;
//        this.historicoAlquileres.add(this.alquiler);
//        System.out.println("El alquiler ha sido finalizado. El saldo actual es de " + this.saldo + " euros");
//        this.alquiler = null;
//    }
//
//    public void visualizarHistoricoAlquileres(){
//        if(this.historicoAlquileres.isEmpty()){
//            System.out.println("No tiene ningún alquiler en el histórico");
//            return;
//        }
//        for(Alquiler alquiler : this.historicoAlquileres){
//            String inicio = alquiler.getBaseInicial() != null ? alquiler.getBaseInicial().getNombre() : "[" + alquiler.getCoordenadaXInicial() + "," + alquiler.getCoordenadaYInicial() + "]";
//            String fin = alquiler.getBaseFinal() != null ? alquiler.getBaseFinal().getNombre() : "[" + alquiler.getCoordenadaXFinal() + "," + alquiler.getCoordenadaYFinal() + "]";
//            System.out.println("Alquiler del vehículo con matrícula " + alquiler.getVehiculo().getMatricula() + " desde " + alquiler.getFechaInicio() + " hasta " + alquiler.getFechaFin() + " con origen " + inicio + " hasta " + fin + " con un importe de " + alquiler.getTarifa().getImporte());
//        }
//    }
//
//    public void visualizarSaldo(){
//        System.out.println("El saldo actual es de " + this.saldo + " euros");
//    }
//
//    public void recargarSaldo(Double cantidad){
//        if(cantidad <= 0){
//            System.out.println("La cantidad a recargar no puede ser menor o igual a 0");
//            return;
//        }
//        this.saldo += cantidad;
//        System.out.println("El saldo ha sido recargado. El saldo actual es de " + this.saldo + " euros");
//    }
//
//    public void informarProblemaVehiculo(String matricula, String descripcion){
//        if(matricula.isEmpty()){
//            System.out.println("La matrícula no puede estar vacía");
//            return;
//        }
//        Vehiculo vehiculo = vehiculos.stream().filter(p -> p.getMatricula().equals(matricula)).findFirst().orElse(null);
//        if(vehiculo == null){
//            System.out.println("No existe ningún vehículo con la matrícula " + matricula);
//            return;
//        }
//        vehiculo.getAvisos().add(descripcion);
//        System.out.println("El vehículo con matrícula " + matricula + " ha sido marcado como averiado");
//
//    }
//
//    public void informarProblemaBase(String nombre, String descripcion){
//        if(nombre.isEmpty()){
//            System.out.println("El nombre de la base no puede estar vacío");
//            return;
//        }
//        Base base = buscarBase(nombre);
//        if(base == null){
//            System.out.println("No existe ninguna base con el nombre " + nombre);
//            return;
//        }
//        base.getAvisos().add(descripcion);
//        System.out.println("La base " + nombre + " ha sido marcada como averiada");
//    }
//
//    public void ubicarMotoMasCercana(int coordenadaX, int coordenadaY){
//        List<Vehiculo> motosDisponibles = vehiculos.stream().filter(p -> p instanceof Moto && !p.isAlquilado()).toList();
//        Moto motoMasCercana = null;
//        Double distanciaMasCercana = null;
//        for(Vehiculo motosDisponible : motosDisponibles){
//            Moto moto = (Moto) motosDisponible;
//            Double distancia = calcularDistancia(coordenadaX, coordenadaY, moto.getCoordenadaX(), moto.getCoordenadaY());
//            if(distanciaMasCercana == null || distancia < distanciaMasCercana){
//                distanciaMasCercana = distancia;
//                motoMasCercana = moto;
//            }
//        }
//        if(motoMasCercana != null){
//            System.out.println("La moto " + motoMasCercana.getMatricula() + " está disponible en las coordenadas [" + motoMasCercana.getCoordenadaX() + "," + motoMasCercana.getCoordenadaY() + "]" + " está disponible con batería al " + motoMasCercana.getBateria() + "%");
//        } else {
//            System.out.println("No existen motos disponibles");
//        }
//    }
//
//    private Double calcularDistancia(int coordenadaX, int coordenadaY, int coordenadaXFin, int coordenadaYFin){
//        return Math.sqrt(Math.pow(coordenadaX - coordenadaXFin, 2) + Math.pow(coordenadaY - coordenadaYFin, 2));
//    }
//
//    private boolean alquilarEnBase(List<Vehiculo> vehiculosBase, String inicio, TipoVehiculo tipoVehiculo, boolean esReserva) {
//        if(vehiculosBase.isEmpty()){
//            System.out.println("No existen vehículos disponibles en la base " + inicio);
//            return true;
//        } else {
//            Vehiculo vehiculo = vehiculosBase.get(0);
//            vehiculo.setAlquilado(true);
//            Tarifa tarifa = tarifas.stream().filter(p -> p.getTipoVehiculo().equals(tipoVehiculo)).findFirst().orElse(null);
//            if(tarifa == null){
//                System.out.println("No existe tarifa para el tipo de vehículo " + tipoVehiculo);
//                return true;
//            }
//            vehiculo.getBase().setPlazas(vehiculo.getBase().getPlazas() + 1);
//            if(esReserva){
//                vehiculo.setReservado(true);
//                this.alquiler = new Alquiler(vehiculo, vehiculo.getBase(), null, null, null, null, null, tarifa, this, null);
//            } else {
//                this.alquiler = new Alquiler(vehiculo, vehiculo.getBase(), null, null, null, null, null, tarifa, this, new Date());
//            }
//            System.out.println("El vehículo con matrícula " + vehiculo.getMatricula() + " ha sido alquilado por el usuario con DNI " + this.getDni());
//        }
//        return false;
//    }
//
//    private boolean alquilarMoto(List<Vehiculo> motosDisponibles, int coordenadaXInicio, int coordenadaYInicio, TipoVehiculo tipoVehiculo, boolean esReserva) {
//        if(motosDisponibles.isEmpty()){
//            System.out.println("No existen vehículos disponibles  en las coordenadas [" + coordenadaXInicio + "," + coordenadaYInicio + "]");
//            return true;
//        } else {
//            Moto moto = (Moto) motosDisponibles.get(0);
//            moto.setAlquilado(true);
//            Tarifa tarifa = tarifas.stream().filter(p -> p.getTipoVehiculo().equals(tipoVehiculo)).findFirst().orElse(null);
//            if(tarifa == null){
//                System.out.println("No existe tarifa para el tipo de vehículo " + tipoVehiculo);
//                return true;
//            }
//            if(esReserva) {
//                moto.setReservado(true);
//                this.alquiler = new Alquiler(moto, null, null, coordenadaXInicio, coordenadaYInicio, null, null, tarifa, this, null);
//            } else {
//                this.alquiler = new Alquiler(moto, null, null, coordenadaXInicio, coordenadaYInicio, null, null, tarifa, this, new Date());
//            }
//            System.out.println("El vehículo con matrícula " + moto.getMatricula() + " ha sido alquilado por el usuario con DNI " + this.getDni());
//        }
//        return false;
//    }
//
//    private Base buscarBase(String nombre) {
//        for (Base base : bases) {
//            if (base.getNombre().equalsIgnoreCase(nombre)) {
//                return base;
//            }
//        }
//        return null;
//    }
//
//    private Double calcularConsumo(Long duracion){
//        Vehiculo vehiculo = this.alquiler.getVehiculo();
//        if(vehiculo instanceof Bicicleta){
//            return Bicicleta.CONSUMO_BICICLETA * duracion;
//        } else if(vehiculo instanceof Patinete){
//            return Patinete.CONSUMO_PATINETE * duracion;
//        } else if(vehiculo instanceof Moto moto){
//            if(TipoMoto.GRANDE.equals(moto.getTipo())){
//                return Moto.CONSUMO_MOTO_GRANDE * duracion;
//            } else if(TipoMoto.PEQUENA.equals(moto.getTipo())){
//                return Moto.CONSUMO_MOTO_PEQUENA * duracion;
//            }
//        }
//        return 0.0;
//    }
//
//    private Long obtenerDuracion(Date fechaReserva){
//        long diferencia = new Date().getTime() - fechaReserva.getTime();
//        long segundos = diferencia / 1000;
//        return segundos / 60;
//    }
//}
