package concesionariomultihilo;

import java.util.concurrent.Semaphore;

public class ConcesionarioMultihilo {

    public static void main(String[] args) {
        // Recurso compartido
        GestorCoches gestor = new GestorCoches();
        
        // Nombres para los 9 clientes 
        String[] nombres = {"Ana", "Bernardo", "Carlos", "Diana", "Elena", "Federico", "Gema", "Hugo", "Isabel"};

        System.out.println("--- Apertura del Concesionario (4 vehículos disponibles) ---\n");

        // Lanzamos los 9 hilos
        for (int i = 0; i < 9; i++) {
            Thread cliente = new Thread(new Cliente(gestor, nombres[i]));
            cliente.start();
        }
    }
}

class GestorCoches {
    // Semáforo para controlar que solo 4 clientes accedan a la vez
    private final Semaphore semaforo = new Semaphore(4, true);
    
    // Estado de los 4 vehículos
    private final boolean[] cochesOcupados = new boolean[4];

    public int cogerCoche() throws InterruptedException {
        // El cliente espera aquí si no hay coches libres
        semaforo.acquire(); 

        synchronized (this) {
            for (int i = 0; i < cochesOcupados.length; i++) {
                if (!cochesOcupados[i]) {
                    cochesOcupados[i] = true;
                    return i + 1; // Retorna el número de vehículo (1 al 4)
                }
            }
        }
        return -1; 
    }

    public void dejarCoche(int numVehiculo) {
        synchronized (this) {
            cochesOcupados[numVehiculo - 1] = false;
        }
        // Avisa que un vehículo ha quedado libre
        semaforo.release(); 
    }
}

class Cliente implements Runnable {
    private final GestorCoches gestor;
    private final String nombreCliente;

    public Cliente(GestorCoches gestor, String nombre) {
        this.gestor = gestor;
        this.nombreCliente = nombre;
    }

    @Override
    public void run() {
        int vehiculo = -1;
        try {
            // 1. Intentar acceder al recurso
            vehiculo = gestor.cogerCoche();

            // 2. Mensaje de inicio
            System.out.println(nombreCliente + "... probando vehículo... " + vehiculo);

            // 3. Simulación de la prueba (Tiempo aleatorio)
            Thread.sleep((long) (Math.random() * 3000) + 1000);

            // 4. Mensaje de fin
            System.out.println(nombreCliente + "... terminó de probar el vehículo... " + vehiculo);

        } catch (InterruptedException e) {
            System.err.println("Error en la prueba del cliente " + nombreCliente);
        } finally {
            // 5. Siempre devolvemos el coche, incluso si hubo un error
            if (vehiculo != -1) {
                gestor.dejarCoche(vehiculo);
            }
        }
    }
}