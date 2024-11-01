/**
 * Ejercicio 14: Gestión de Empleados con Búsqueda
 * Crea un sistema para gestionar empleados en una empresa.
 * Instrucciones:
 * 1. Usa un ArrayList para almacenar el nombre, puesto y salario de cada
 * empleado.
 * 2. Permite al usuario buscar empleados por nombre o puesto.
 * 3. Muestra los detalles del empleado encontrado.
 */

import java.util.ArrayList;
import java.util.Optional;

import javax.swing.JOptionPane;
import utils.InputRequester;

public class Exercise14 {
  private record Employee(String name, String position, double salary) {
  }

  public static void main(String[] args) {
    ArrayList<Employee> employees = new ArrayList<>();
    // ArrayList<Employee> employees = new ArrayList<>(List.of(
    // new Employee("Juan", "Desarrollador", 5000),
    // new Employee("Ana", "Diseñadora", 4500),
    // new Employee("Carlos", "Gerente", 6000)));

    boolean isMenuOpened = true;

    while (isMenuOpened) {
      String menuPrompt = """
          Sistema de Gestión de Empleados

          0. Salir
          1. Agregar empleado
          2. Buscar por nombre
          3. Buscar por puesto
          4. Mostrar todos los empleados

          Seleccione una opción:
          """;

      int selectedOption = InputRequester.requestInteger(menuPrompt).orElseThrow();

      switch (selectedOption) {
        case 0 -> {
          JOptionPane.showMessageDialog(null, "¡Hasta luego!");
          isMenuOpened = false;
        }
        case 1 -> {
          String name = InputRequester.requestString("Ingrese el nombre del empleado:");
          String position = InputRequester.requestString("Ingrese el puesto del empleado:");
          double salary = InputRequester.requestDouble("Ingrese el salario del empleado:").orElseThrow();

          employees.add(new Employee(name, position, salary));
          JOptionPane.showMessageDialog(null, "Empleado agregado exitosamente.");
        }
        case 2 -> {
          String searchName = InputRequester.requestString("Ingrese el nombre del empleado a buscar:");
          Optional<Employee> foundEmployee = employees.stream()
              .filter(employee -> employee.name().equalsIgnoreCase(searchName))
              .findFirst();

          if (foundEmployee.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Empleado no encontrado.");
            break;
          }

          showEmployeeDetails(foundEmployee.get());
        }
        case 3 -> {
          String searchPosition = InputRequester.requestString("Ingrese el puesto a buscar:");
          StringBuilder matchingEmployees = new StringBuilder("Empleados encontrados:\n\n");
          boolean couldFindEmployees = false;

          for (Employee employee : employees) {
            if (employee.position().equalsIgnoreCase(searchPosition)) {
              matchingEmployees.append(formatEmployeeDetails(employee));
              couldFindEmployees = true;
            }
          }

          if (!couldFindEmployees) {
            JOptionPane.showMessageDialog(null, "No se encontraron empleados con ese puesto.");
            break;
          }

          JOptionPane.showMessageDialog(null, matchingEmployees.toString());
        }
        case 4 -> {
          if (employees.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay empleados registrados.");
            break;
          }

          StringBuilder allEmployees = new StringBuilder("Lista de Empleados:\n\n");
          for (Employee employee : employees) {
            allEmployees.append(formatEmployeeDetails(employee));
          }
          JOptionPane.showMessageDialog(null, allEmployees.toString());
        }
        default -> JOptionPane.showMessageDialog(null, "Opción no válida. Por favor, intente nuevamente.");
      }
    }
  }

  private static void showEmployeeDetails(Employee employee) {
    JOptionPane.showMessageDialog(null, formatEmployeeDetails(employee));
  }

  private static String formatEmployeeDetails(Employee employee) {
    return String.format("Nombre: %s\nPuesto: %s\nSalario: $%.2f\n\n",
        employee.name(), employee.position(), employee.salary());
  }
}
