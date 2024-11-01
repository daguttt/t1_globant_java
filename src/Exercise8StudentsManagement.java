/*
 * Ejercicio 8: Sistema de Gestión de Estudiantes
 * Crea un programa para gestionar la información de los estudiantes en una
 * universidad.
 * Instrucciones:
 * 1. Usa un ArrayList para almacenar los nombres y promedios de los
 * estudiantes.
 * 2. Permite al usuario agregar nuevos estudiantes y mostrar la lista de
 * estudiantes con su promedio.
 * 3. Implementa una opción para buscar estudiantes por nombre y mostrar su
 * promedio.
 */

import java.util.ArrayList;
import java.util.Optional;

import javax.swing.JOptionPane;
import utils.InputRequester;

public class Exercise8StudentsManagement {
  private record Student(String name, double average) {
  }

  public static void main(String[] args) {
    ArrayList<Student> students = new ArrayList<>();
    boolean isMenuOpened = true;

    while (isMenuOpened) {
      String menuPrompt = """
          Sistema de Gestión de Estudiantes

          0. Salir
          1. Agregar estudiante
          2. Mostrar lista de estudiantes
          3. Buscar estudiante

          Seleccione una opción:
          """;

      int selectedOption = InputRequester.requestInteger(menuPrompt).orElseThrow();

      switch (selectedOption) {
        case 0 -> {
          JOptionPane.showMessageDialog(null, "¡Hasta luego!");
          isMenuOpened = false;
        }
        case 1 -> {
          String name = InputRequester.requestString("Ingrese el nombre del estudiante:");
          double average = InputRequester.requestDouble("Ingrese el promedio del estudiante:").orElseThrow();

          students.add(new Student(name, average));
          JOptionPane.showMessageDialog(null, "Estudiante agregado exitosamente.");
        }
        case 2 -> {
          if (students.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay estudiantes registrados.");
            break;
          }

          StringBuilder listedStudents = new StringBuilder("Lista de Estudiantes:\n\n");
          for (Student student : students) {
            listedStudents.append(String.format("%s - Promedio: %.2f%n",
                student.name(), student.average()));
          }
          JOptionPane.showMessageDialog(null, listedStudents.toString());
        }
        case 3 -> {
          String searchName = InputRequester.requestString("Ingrese el nombre del estudiante a buscar:");
          Optional<Student> foundStudent = students.stream()
              .filter(student -> student.name().equalsIgnoreCase(searchName))
              .findFirst();

          if (foundStudent.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Estudiante no encontrado.");
            break;
          }

          JOptionPane.showMessageDialog(null,
              String.format("Estudiante '%s' encontrado - Promedio: %.2f", foundStudent.get().name(),
                  foundStudent.get().average()));
        }
        default -> JOptionPane.showMessageDialog(null, "Opción no válida. Por favor, intente nuevamente.");
      }
    }
  }
}
