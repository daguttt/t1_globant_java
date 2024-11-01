import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JOptionPane;

import utils.InputRequester;

/**
 * Ejercicio 18: Sistema de Gestión de Vehículos
 * Desarrolla un sistema para gestionar la flota de vehículos de una empresa de
 * transporte.
 * 
 * Instrucciones:
 * 1. Usa un ArrayList para almacenar la matrícula, modelo y estado de cada
 * vehículo (disponible u ocupado).
 * 2. Permite al usuario registrar nuevos vehículos y actualizar el estado de
 * los
 * existentes.
 * 3. Muestra la lista de vehículos disponibles.
 */
public class Exercise18VehiclesManagement {
  private static final List<String> VALID_STATUSES = List.of("disponible", "ocupado");

  private record Vehicle(String licensePlate, String model, String status) {
  }

  public static void main(String[] args) {
    ArrayList<Vehicle> vehicles = new ArrayList<>();
    boolean isMenuOpened = true;

    while (isMenuOpened) {
      String menuPrompt = """
          Sistema de Gestión de Vehículos

          0. Salir
          1. Agregar vehículo
          2. Actualizar estado
          3. Mostrar vehículos disponibles
          """;

      int selectedOption = InputRequester.requestInteger(menuPrompt).orElseThrow();

      switch (selectedOption) {
        case 0 -> {
          JOptionPane.showMessageDialog(null, "¡Hasta luego!");
          isMenuOpened = false;
        }
        case 1 -> {
          String licensePlate = InputRequester.requestString("Ingrese la matrícula del vehículo:");
          String model = InputRequester.requestString("Ingrese el modelo del vehículo:");
          int statusIndex = InputRequester.requestAnIndexFrom(VALID_STATUSES,
              "Ingrese el estado del vehículo (disponible o ocupado):");
          String status = VALID_STATUSES.get(statusIndex);

          vehicles.add(new Vehicle(licensePlate, model, status));
          JOptionPane.showMessageDialog(null, "Vehículo registrado exitosamente", "Vehículo registrado",
              JOptionPane.INFORMATION_MESSAGE);
        }
        case 2 -> {
          if (vehicles.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay vehículos agregados en el sistema", "No hay vehículos",
                JOptionPane.INFORMATION_MESSAGE);
            continue;
          }

          String licensePlate = InputRequester.requestString("Ingrese la matrícula del vehículo:");

          Optional<Vehicle> foundVehicle = vehicles.stream().filter(v -> {
            return v.licensePlate().equalsIgnoreCase(licensePlate);
          }).findFirst();

          if (foundVehicle.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vehículo no encontrado", "Vehículo no encontrado",
                JOptionPane.ERROR_MESSAGE);
            continue;
          }

          int statusIndex = InputRequester.requestAnIndexFrom(VALID_STATUSES,
              "Ingrese el estado del vehículo (disponible o ocupado):");
          String status = VALID_STATUSES.get(statusIndex);

          int vehicleIndex = vehicles.indexOf(foundVehicle.get());
          vehicles.set(vehicleIndex,
              new Vehicle(foundVehicle.get().licensePlate(), foundVehicle.get().model(), status));

          JOptionPane.showMessageDialog(null, "Estado del vehículo actualizado exitosamente", "Estado actualizado",
              JOptionPane.INFORMATION_MESSAGE);
        }
        case 3 -> {
          if (vehicles.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay vehículos agregados en el sistema", "No hay vehículos",
                JOptionPane.INFORMATION_MESSAGE);
            continue;
          }

          List<String> availableVehicles = vehicles.stream().filter(v -> v.status().equals("disponible")).map(v -> {
            return String.format("%s - %s - %s", v.licensePlate(), v.model(), v.status());
          }).toList();

          if (availableVehicles.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay vehículos disponibles", "No hay vehículos",
                JOptionPane.INFORMATION_MESSAGE);
            continue;
          }

          JOptionPane.showMessageDialog(null, String.join("\n", availableVehicles), "Vehículos disponibles",
              JOptionPane.INFORMATION_MESSAGE);
        }
      }
    }
  }
}
