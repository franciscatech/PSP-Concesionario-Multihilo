# PSP-Concesionario-Multihilo
Simulación de gestión de pruebas de vehículos mediante hilos y semáforos en Java.
# PSP - Simulación de Concesionario Multihilo

## Descripción del Proyecto
Este proyecto es el "Trabajo de Enfoque" para la asignatura de **Programación de Servicios y Procesos**. Consiste en una aplicación Java que simula la gestión de pruebas de vehículos en un concesionario.

El sistema controla el acceso de **9 clientes** que desean probar coches, con la restricción de que el concesionario solo dispone de **4 vehículos** de exposición simultáneos.

## Tecnologías Utilizadas
* **Java SE**
* **Hilos (Threads):** Para representar el comportamiento concurrente de los clientes.
* **Semáforos (Semaphore):** Para limitar el acceso a los recursos compartidos (los 4 vehículos).
* **Sincronización:** Uso de bloques `synchronized` para garantizar la integridad de la asignación de IDs de vehículos.

## Funcionamiento
1. El programa lanza 9 hilos independientes.
2. Cada hilo solicita un permiso al semáforo (`acquire`).
3. [cite_start]Si hay un vehículo libre, el cliente comienza la prueba (se imprime mensaje de inicio).
4. La prueba dura un tiempo aleatorio (simulado con `Thread.sleep`).
5. [cite_start]Al terminar, el cliente libera el vehículo (se imprime mensaje de fin) y devuelve el permiso al semáforo (`release`).

## Ejecución
Para ejecutar el proyecto, clona el repositorio y corre la clase `ConcesionarioMultihilo.java` desde cualquier IDE compatible con Java (NetBeans, Eclipse, IntelliJ).
