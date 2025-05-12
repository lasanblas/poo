package es.uned.movilidad;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase principal de la aplicación de movilidad.
 * Contiene la configuración inicial y el punto de entrada de la aplicación.
 */
@SpringBootApplication
public class MovilidadApplication {
	/**
	 * Coordenada X máxima del sistema.
	 */
	public static Integer coordenadaX;
	/**
	 * Coordenada Y máxima del sistema.
	 */
	public static Integer coordenadaY;
	/**
	 * Lista de vehículos registrados en el sistema.
	 */
	protected static List<Vehiculo> vehiculos = new ArrayList<>();
	/**
	 * Lista de bases registradas en el sistema.
	 */
	protected static List<Base> bases = new ArrayList<>();
	/**
	 * Lista de tarifas disponibles en el sistema.
	 */
	protected static List<Tarifa> tarifas = new ArrayList<>();
	/**
	 * Lista de personas registradas en el sistema.
	 */
	protected static List<Persona> personas = new ArrayList<>();

	/**
	 * Metodo principal que inicia la aplicación
	 *
	 * @param args Argumentos de línea de comandos.
	 */
	public static void main(String[] args) {
		SpringApplication.run(MovilidadApplication.class, args);
	}

	@Bean
	CommandLineRunner init() {
		return args -> {
			System.out.println("Inicializando Datos\n");

			MovilidadApplication.coordenadaX = 10;
			MovilidadApplication.coordenadaY = 10;

			// Usuario administrador
			Administrador administrador = new Administrador("49199346T", "Leo", "SS", 637232760, "nada@nada.com");
			System.out.println("Usuario administrador creado\n");

			// Crear bases
			administrador.crearBase("Base 1", 4, 4, 10);
			administrador.crearBase("Base 2", 6, 6, 5);
			administrador.crearBase("Base 3", 9, 9, 8);
			System.out.println("Bases creadas");
			administrador.visualizarEstadoBases();
			System.out.println("\n");

			// Crear tarifas
			administrador.crearTarifa("patinete", "patinete", 10.0, 0.5);
			administrador.crearTarifa("bicicleta", "bicicleta", 10.0, 0.25);
			administrador.crearTarifa("moto grande", "moto grande", 20.0, 0.5);
			administrador.crearTarifa("moto pequeña", "moto pequeña", 15.0, 0.25);
			System.out.println("Tarifas creadas\n");

			// Crear flota de vehículos
			administrador.crearBicicleta("Bicicleta 1", "Marca A", "1234-ABC", "Base 1");
			administrador.crearBicicleta("Bicicleta 2", "Marca B", "1234-NOP", "Base 2");
			administrador.crearPatinete("Patinete 1", "Marca A", "1234-DEF", "Base 2");
			administrador.crearMotoPequena("Moto 1", "Marca A", "1234-HIJ",3, 3);
			administrador.crearMotoGrande("Moto 2", "Marca A", "1234-KLM",5, 5);
			administrador.crearMotoGrande("Moto 3", "Marca B", "1234-QRS",7, 7);
			System.out.println("Flota de vehículos creada");
			administrador.obtenerStockVehiculos();
			System.out.println("\n");

			// Actualizar Flota de vehículos
			administrador.actualizarVehiculo("1234-DEF", "Patinete 1", "Marca B", 100);
			administrador.actualizarVehiculo("1234-KLM","Moto 2", "Marca B",  15);
			System.out.println("Flota de vehículos actualizada");
			administrador.obtenerStockVehiculos();
			System.out.println("\n");

			// Crear usuarios
			administrador.crearUsuario("14967154L", "pepe", "goteras", 132456789, "nada@nada.com", 50);
			administrador.crearUsuarioPremium("23314801T", "john", "smith", 132456789, "nada@nada.com", 100);
			administrador.crearMecanico("18567767M", "salva", "tore", 132456789, "nada@nada.com");
			administrador.crearEncargadoManetimiento("35611496K", "joe", "avg", 132456789, "nada@nada.com");
			System.out.println("Usuarios creados");
			administrador.obtenerUsuariosRegistrados();
			System.out.println("\n");

			// Actualizar usuarios
			administrador.actualizarUsuario("14967154L", "nada@nada.com", "pepe", "goteras", 987654321 );
			administrador.actualizarUsuario("35611496K", "joe@nada.com", "joe", "average", 132456789);
			System.out.println("Usuarios actualizados");
			administrador.obtenerUsuariosRegistrados();
			System.out.println("\n");

			// Buscar usuarios
			System.out.println("Buscando usuarios");
			administrador.buscarUsuario("14967154L", null, null, null,false);
			administrador.buscarUsuario(null, "john", null, null,false);
			administrador.buscarUsuario(null, null, 132456789, null,false);
			administrador.buscarUsuario(null, null, null, "nada",false);
			System.out.println("\n");
			System.out.println("Datos inicializados\n");

			System.out.println("Probando funcionalidades\n");
			Usuario usuario = (Usuario) buscarPersona("14967154L");
            assert usuario != null;

			System.out.println("visualizarVehiculosDisponibles");
            usuario.visualizarVehiculosDisponibles();
			System.out.println("\n");

			System.out.println("ubicarVehiculoMasCercano");
			usuario.ubicarVehiculoMasCercano("bicicleta", 1, 1);
			usuario.ubicarVehiculoMasCercano("moto grande", 1, 1);
			System.out.println("\n");

			//FLUJO DE ALQUILER Y RESERVA
			System.out.println("alquilar bicicleta");
			usuario.alquilarVehiculo("bicicleta", "Base 1", null, null);
			Thread.sleep(60 * 1000);
			System.out.println("\n");
			usuario.finalizarAlquiler("Base 2");
			System.out.println("\n");
			usuario.visualizarVehiculosDisponibles();
			System.out.println("\n");
			usuario.visualizarHistoricoAlquileres();
			System.out.println("\n");

			System.out.println("alquilar bicicleta moto baja batería");
			usuario.alquilarVehiculo("moto grande", null, 5, 5);
			System.out.println("\n");

			System.out.println("reservar patinete");
			usuario = (Usuario) buscarPersona("23314801T");
            assert usuario != null;
            usuario.reservarVehiculo("patinete", "Base 2", null, null);
			System.out.println("\n");
			usuario.visualizarVehiculosDisponibles();
			System.out.println("\n");
			usuario.confirmarReserva();
			Thread.sleep(60 * 1000);
			System.out.println("\n");
			usuario.finalizarAlquiler("Base 3");
			System.out.println("\n");
			usuario.visualizarVehiculosDisponibles();
			System.out.println("\n");
			usuario.visualizarHistoricoAlquileres();
			System.out.println("\n");

			System.out.println("alquilar moto baja batería usuario premium");
			usuario.alquilarVehiculo("moto grande", null, 5, 5);
			Thread.sleep(60 * 1000);
			System.out.println("\n");
			usuario.finalizarAlquiler(6, 6);
			System.out.println("\n");
			usuario.visualizarVehiculosDisponibles();
			System.out.println("\n");
			usuario.visualizarHistoricoAlquileres();
			System.out.println("\n");

			System.out.println("recarga de saldo");
			usuario.recargarSaldo(15);
			System.out.println("\n");


			// FLUJO DE REPARACIONES
			System.out.println("informar un problema");
			usuario.informarProblemaBase("Base 1", "Problema con la base");
			System.out.println("\n");
			usuario.informarProblemaVehiculo("1234-QRS", "Problema con el vehiculo");
			System.out.println("\n");
			usuario.visualizarVehiculosDisponibles();
			System.out.println("\n");

			System.out.println("visualizar Vehiculos Con Fallos");
			administrador.visualizarVehiculosConFallos();
			System.out.println("\n");

			System.out.println("visualizar Bases Con Fallos");
			administrador.visualizarBasesConFallos();
			System.out.println("\n");

			System.out.println("visualizar Vehiculos Sin Bateria");
			administrador.visualizarVehiculosSinBateria();
			System.out.println("\n");

			System.out.println("asignar Vehiculo Con Fallos");
			administrador.asignarVehiculo( "35611496K", "18567767M","1234-QRS");
			System.out.println("\n");

			System.out.println("asignar Base Con Fallos");
			administrador.asignarBase("18567767M", "Base 1");
			System.out.println("\n");

			System.out.println("asignar Vehiculo Sin Bateria");
			administrador.asignarVehiculo("35611496K", null,"1234-KLM");
			System.out.println("\n");

			Mecanico mecanico = (Mecanico) buscarPersona("18567767M");
			assert mecanico != null;

			System.out.println("Visualización de los vehículos y bases asignados para su reparación");
			mecanico.visualizarVehiculosYBases();
			System.out.println("\n");

			System.out.println("Recogida de un vehículo para su reparación");
			mecanico.recogerVehiculo("1234-QRS", null, 7, 7);
			System.out.println("\n");

			System.out.println("Definición del período de inactividad de una base o vehículo");
			mecanico.definirPeriodoInactividad("1234-QRS", null, "02/05/2025", "03/05/2025");
			System.out.println("\n");

			System.out.println("Generación de facturas por la reparación de un vehículo o una base de bicicletas o patinetes");
			Factura factura = mecanico.generarFactura("1234-QRS", null, 10.0, "123456", false);
			factura.imprimirFactura();
			System.out.println("\n");

			EncargadoMantenimiento encargadoMantenimiento = (EncargadoMantenimiento) buscarPersona("35611496K");
			assert encargadoMantenimiento != null;

			System.out.println("Devolución de un vehículo a una base o a unas coordenadas específicas");
			encargadoMantenimiento.devolverVehiculo("1234-QRS", null, 8, 8);
			System.out.println("\n");

			usuario.visualizarVehiculosDisponibles();
			System.out.println("\n");

			System.out.println("Visualización de los vehículos asignados para su mantenimiento");
			encargadoMantenimiento.visualizarVehiculos();
			System.out.println("\n");

			System.out.println("Recogida de un vehículo para su mantenimiento");
			encargadoMantenimiento.recogerVehiculo("1234-KLM", null, 5, 5);
			System.out.println("\n");

			System.out.println("Definición del período de inactividad de un vehículo");
			encargadoMantenimiento.definirPeriodoInactividad("1234-KLM", "02/05/2025", "03/05/2025");
			System.out.println("\n");

			System.out.println("Devolución de un vehículo a una base o a unas coordenadas específicas");
			encargadoMantenimiento.devolverVehiculo("1234-KLM", null, 2, 2);
			System.out.println("\n");

			System.out.println("Visualización de los vehículos y bases asignados para su reparación");
			mecanico.visualizarVehiculosYBases();
			System.out.println("\n");

			usuario.visualizarVehiculosDisponibles();
			System.out.println("\n");

			System.out.println("Recogida de un vehículo para su reparación");
			mecanico.desplazarABase("Base 1");
			System.out.println("\n");

			System.out.println("Definición del período de inactividad de una base o vehículo");
			mecanico.definirPeriodoInactividad(null, "Base 1", "02/05/2025", "03/05/2025");
			System.out.println("\n");

			System.out.println("Generación de facturas por la reparación de un vehículo o una base de bicicletas o patinetes");
			factura = mecanico.generarFactura(null, "Base 1", 20.0, "321654", true);
			factura.imprimirFactura();
			System.out.println("\n");

			usuario.visualizarVehiculosDisponibles();
			System.out.println("\n");

			// FLUJO USUARIO A PROMOCIONAR
			usuario = (Usuario) buscarPersona("14967154L");
			assert usuario != null;
			System.out.println("alquilar bicicleta");
			usuario.alquilarVehiculo("bicicleta", "Base 2", null, null);
			Thread.sleep(60 * 1000);
			System.out.println("\n");
			usuario.finalizarAlquiler("Base 1");
			System.out.println("\n");
			usuario.visualizarVehiculosDisponibles();
			System.out.println("\n");
			usuario.visualizarHistoricoAlquileres();
			System.out.println("\n");

			System.out.println("alquilar patinete");
			usuario.alquilarVehiculo("patinete", "Base 3", null, null);
			Thread.sleep(60 * 1000);
			System.out.println("\n");
			usuario.finalizarAlquiler("Base 1");
			System.out.println("\n");
			usuario.visualizarVehiculosDisponibles();
			System.out.println("\n");
			usuario.visualizarHistoricoAlquileres();
			System.out.println("\n");

			System.out.println("alquilar moto");
			usuario.alquilarVehiculo("moto pequeña", null, 3, 3);
			Thread.sleep(60 * 1000);
			System.out.println("\n");
			usuario.finalizarAlquiler(4, 4);
			System.out.println("\n");
			usuario.visualizarVehiculosDisponibles();
			System.out.println("\n");
			usuario.visualizarHistoricoAlquileres();
			System.out.println("\n");

			System.out.println("obtener Usuarios A Promocionar");
			administrador.obtenerUsuariosAPromocionar();
			System.out.println("\n");

			System.out.println("Promocionar usuario");
			administrador.promocionarUsuario("14967154L");
			System.out.println("\n");

			System.out.println("Buscando usuarios premium");
			administrador.buscarUsuario(null, null, null, null,true);
			System.out.println("\n");


			// FLUJO LISTADOS Y ESTADÍSTICAS
			System.out.println("obtener Listado Bases Segun Demanda \n");
			administrador.obtenerListadoBasesSegunDemanda(false);

			System.out.println("obtener Listado Vehiculos Reparados");
			administrador.obtenerListadoVehiculosReparados("01/05/2025", "13/05/2025");

			System.out.println("obtener Listado Encargados Y Mecanicos");
			administrador.obtenerListadoEncargadosYMecanicos();

			System.out.println("obtener Listado Vehiculos Por Tipo");
			administrador.obtenerListadoVehiculosPorTipo();
			System.out.println("\n");

			System.out.println("obtener Listado Usuarios Segun Gasto En Alquiler");
			administrador.obtenerListadoUsuariosSegunGastoEnAlquiler("01/05/2025", "13/05/2025");
			System.out.println("\n");

		};
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

}
