/*
 * Ejercicio 7: Gestión de Reservas en un Restaurante
 * Desarrolla un sistema para gestionar las reservas en un restaurante.
 * Instrucciones:
 * 1. Usa un ArrayList para almacenar los nombres de los clientes que han
 * hecho una reserva.
 * 2. Permite al usuario agregar y cancelar reservas.
 * 3. Al final, muestra la lista de reservas confirmadas.
 * 
 */

import java.util.ArrayList;

import javax.swing.JOptionPane;

import utils.InputRequester;

public class Exercise7 {
  public static void main(String[] args) {
    ArrayList<String> reservations = new ArrayList<>();
    boolean isRunning = true;

    while (isRunning) {
      String menuPrompt = String.format("""
          Sistema de Reservas del Restaurante

          Reservas actuales:
          %s

          1. Agregar reserva
          2. Cancelar reserva
          3. Mostrar confirmadas y salir

          Seleccione una opción:
          """,
          reservations.isEmpty() ? "No hay reservas" : String.join("\n", reservations));

      int selectedOption = InputRequester.requestInteger(menuPrompt).orElseThrow();

      switch (selectedOption) {
        case 1 -> {
          String namePrompt = "Ingrese el nombre para la reserva:";
          String name = InputRequester.requestString(namePrompt);
          reservations.add(name);
          JOptionPane.showMessageDialog(null, "Reserva agregada exitosamente.");
        }
        case 2 -> {
          if (reservations.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay reservas para cancelar.");
            continue;
          }

          int reservationIndex = InputRequester.requestAnIndexFrom(reservations,
              "Seleccione el número de la reserva a cancelar:");

          reservations.remove(reservationIndex);
          JOptionPane.showMessageDialog(null, "Reserva cancelada exitosamente.");
        }
        case 3 -> {
          String finalMessage = String.format("""
              Reservas confirmadas:
              %s

              ¡Gracias por usar nuestro sistema!""",
              reservations.isEmpty() ? "No hay reservas" : String.join("\n", reservations));

          JOptionPane.showMessageDialog(null, finalMessage);
          isRunning = false;
        }
        default -> JOptionPane.showMessageDialog(null, "Opción inválida. Por favor, intente nuevamente.");
      }
    }
  }
}