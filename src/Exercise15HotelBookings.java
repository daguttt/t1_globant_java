
/**
 * Ejercicio 15: Sistema de Reservas para un Hotel
 * Desarrolla un sistema para reservar habitaciones en un hotel.
 * 
 * Instrucciones:
 * 1. Crea una matriz de 4x4 que represente las habitaciones del hotel
 * (disponibles O y ocupadas X).
 * 2. Permite al usuario seleccionar una habitación para reservarla.
 * 3. Si la habitación está ocupada, informa al usuario y permite elegir otra.
 */

import utils.InputRequester;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Exercise15HotelBookings {
  private static final String[] columns = { "1", "2", "3", "4" };
  private static final String separator = "-".repeat(String.join(" | ", columns).length());

  private static final String[][] rooms = new String[][] {
      { "O", "O", "O", "O" },
      { "O", "O", "O", "O" },
      { "O", "O", "O", "O" },
      { "O", "O", "O", "O" },
  };

  // private static final String[][] rooms = new String[][] {
  // { "X", "X", "X", "X" },
  // { "X", "X", "X", "X" },
  // { "X", "X", "X", "X" },
  // { "X", "X", "X", "X" },
  // };

  public static String getFormattedRow(String[] row, int rowNumber) {
    String formattedRowNumber = String.format("%d | ", rowNumber);
    String formattedRooms = String.join(" | ", row);
    return formattedRowNumber + formattedRooms;
  }

  public static String getFormattedRooms() {
    ArrayList<String> formattedRows = new ArrayList<>();

    for (int i = 0; i < rooms.length; i++) {
      String[] row = rooms[i];
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
    int NUMBER_OF_ROWS = 4;

    while (true) {
      boolean areAllRoomsTaken = Arrays.stream(rooms).flatMap(Arrays::stream)
          .allMatch(room -> Objects.equals(room, "X"));
      if (areAllRoomsTaken) {
        JOptionPane.showMessageDialog(null, "¡Todas las habitaciones están ocupadas! ¡Hasta pronto!");
        break;
      }

      // Request row (number)
      String rowsMessagePrompt = String.format("""
          Bienvenido al sistema de reservas del Hotel

          Las habitaciones con 'O' están disponibles:

          %s

          Por favor, selecciona el número de la fila (1-4):
          """, getFormattedRooms());

      int selectedRow = InputRequester.requestInteger(rowsMessagePrompt).orElseThrow();
      if (selectedRow < 1 || selectedRow > NUMBER_OF_ROWS) {
        JOptionPane.showMessageDialog(null, "La fila seleccionada no existe. Inténtalo de nuevo.");
        continue;
      }

      // Request column (number)
      String formattedSelectedRow = getFormattedRow(rooms[selectedRow - 1], selectedRow);
      String columnsMessagePrompt = String.format("""
          Las habitaciones con 'O' están disponibles:

             | %s
          %s
          %S

          Por favor, selecciona el número de la columna (1-4):
          """, String.join(" | ", columns), separator, formattedSelectedRow);

      int selectedColumn = InputRequester.requestInteger(columnsMessagePrompt).orElseThrow();
      if (selectedColumn < 1 || selectedColumn > 4) {
        JOptionPane.showMessageDialog(null, "La columna seleccionada no existe. Inténtalo de nuevo.");
        continue;
      }

      // Validate room availability
      int selectedRowIndex = selectedRow - 1;
      int selectedColumnIndex = selectedColumn - 1;

      if (Objects.equals(rooms[selectedRowIndex][selectedColumnIndex], "X")) {
        JOptionPane.showMessageDialog(null, "La habitación seleccionada ya está ocupada. Por favor, elija otra.");
        continue;
      }

      // Create reservation
      rooms[selectedRowIndex][selectedColumnIndex] = "X";

      JOptionPane.showMessageDialog(null,
          String.format("¡Reserva exitosa! Has reservado la habitación en fila %d, columna %d",
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

    JOptionPane.showMessageDialog(null, "¡Gracias por usar nuestro sistema de reservas!");
  }
}