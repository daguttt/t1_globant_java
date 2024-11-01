
/**
 * Ejercicio 20: Sistema de Gestión de Reservas de Vuelos (Matriz y
 * Condicionales)
 * Desarrolla un sistema para gestionar la reserva de asientos en un avión.
 * Instrucciones:
 * 1. Crea una matriz de 6x6 para representar los asientos del avión.
 * 2. Permite al usuario seleccionar un asiento para reservar.
 * 3. Si el asiento ya está ocupado, muestra un mensaje y permite elegir otro.
 */

import utils.InputRequester;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Exercise20FlightReservations {
  private static final String[] columns = { "1", "2", "3", "4", "5", "6" };
  private static final String separator = "-".repeat(String.join(" | ", columns).length());

  private static final String[][] seats = new String[][] {
      { "O", "O", "O", "O", "O", "O" },
      { "O", "O", "O", "O", "O", "O" },
      { "O", "O", "O", "O", "O", "O" },
      { "O", "O", "O", "O", "O", "O" },
      { "O", "O", "O", "O", "O", "O" },
      { "O", "O", "O", "O", "O", "O" }
  };

  public static String getFormattedRow(String[] row, int rowNumber) {
    String formattedRowNumber = String.format("%d | ", rowNumber);
    String formattedSeats = String.join(" | ", row);
    return formattedRowNumber + formattedSeats;
  }

  public static String getFormattedSeats() {
    ArrayList<String> formattedRows = new ArrayList<>();

    for (int i = 0; i < seats.length; i++) {
      String[] row = seats[i];
      String formattedRow = getFormattedRow(row, i + 1);
      formattedRows.add(formattedRow);
    }

    return String.format("""
           | %s
        %s
        %s
        """, String.join(" | ", columns), separator, String.join("\n", formattedRows));
  }

  public static void main(String[] args) {
    int NUMBER_OF_ROWS = 6;

    while (true) {
      boolean areAllSeatsTaken = Arrays.stream(seats).flatMap(Arrays::stream)
          .allMatch(seat -> Objects.equals(seat, "X"));
      if (areAllSeatsTaken) {
        JOptionPane.showMessageDialog(null, "¡Todos los asientos están ocupados! ¡Hasta pronto!");
        break;
      }

      // Request row (number)
      String rowsMessagePrompt = String.format("""
          Bienvenido al sistema de reservas de vuelo

          Los asientos con 'O' están disponibles:

          %s

          Por favor, selecciona el número de la fila (1-6):
          """, getFormattedSeats());

      int selectedRow = InputRequester.requestInteger(rowsMessagePrompt).orElseThrow();
      if (selectedRow < 1 || selectedRow > NUMBER_OF_ROWS) {
        JOptionPane.showMessageDialog(null, "La fila seleccionada no existe. Inténtalo de nuevo.");
        continue;
      }

      // Request column (number)
      String formattedSelectedRow = getFormattedRow(seats[selectedRow - 1], selectedRow);
      String columnsMessagePrompt = String.format("""
          Los asientos con 'O' están disponibles:

             | %s
          %s
          %S

          Por favor, selecciona el número de la columna (1-6):
          """, String.join(" | ", columns), separator, formattedSelectedRow);

      int selectedColumn = InputRequester.requestInteger(columnsMessagePrompt).orElseThrow();
      if (selectedColumn < 1 || selectedColumn > 6) {
        JOptionPane.showMessageDialog(null, "La columna seleccionada no existe. Inténtalo de nuevo.");
        continue;
      }

      // Validate seat availability
      int selectedRowIndex = selectedRow - 1;
      int selectedColumnIndex = selectedColumn - 1;

      if (Objects.equals(seats[selectedRowIndex][selectedColumnIndex], "X")) {
        JOptionPane.showMessageDialog(null, "El asiento seleccionado ya está ocupado. Por favor, elija otro.");
        continue;
      }

      // Create reservation
      seats[selectedRowIndex][selectedColumnIndex] = "X";

      JOptionPane.showMessageDialog(null,
          String.format("¡Reserva exitosa! Has reservado el asiento en fila %d, columna %d",
              selectedRow, selectedColumn));

      // Ask if user wants to make another reservation
      int wantsToContinue = JOptionPane.showConfirmDialog(null,
          "¿Deseas hacer otra reserva?",
          "Continuar",
          JOptionPane.YES_NO_OPTION);

      if (wantsToContinue == JOptionPane.NO_OPTION) {
        break;
      }
    }

    JOptionPane.showMessageDialog(null, "¡Gracias por usar nuestro sistema de reservas de vuelo!");
  }
}
