import utils.InputRequester;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Exercise6 {
    private static final String[] letters = {"A", "B", "C", "D", "E"};
    private static final String separator = "-".repeat(String.join(" | ", letters).length());

    private static final String[][] seats = new String[][] {
        {"O", "O", "O", "O", "O"},
        {"O", "O", "O", "O", "O"},
        {"O", "O", "O", "O", "O"},
        {"O", "O", "O", "O", "O"},
        {"O", "O", "O", "O", "O"},
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
                """, String.join(" | ", letters), separator, String.join("\n", formattedRows));
    }

    public static void main(String[] args) {

        int NUMBER_OF_ROWS = 5;

        boolean areAllSeatsTaken = false;
        while (!areAllSeatsTaken) {
            // Request row (number)
            String rowsMessagePropmpt = String.format("""
                    Bienvenido al sistema de  reserva de asientos de cine
                    
                    Los asientos con 'O' estan libres:
                    
                    %s
                    
                    Por favor, selecciona el número de la fila que quieres reservar:
                    """, getFormattedSeats());

            int selectedRow = InputRequester.requestInteger(rowsMessagePropmpt).orElseThrow();
            if (selectedRow < 1 || selectedRow > NUMBER_OF_ROWS) {
                JOptionPane.showMessageDialog(null, "El fila seleccionada no existe. Inténtalo de nuevo.");
                continue;
            }

            // Request column (letter)
            String formattedSelectedRow = getFormattedRow(seats[selectedRow - 1], selectedRow);
            String columnsMessagePropmpt = String.format("""
                    Los asientos con 'O' estan libres:
                    
                       | %s
                    %s
                    %S
                    
                    Por favor, selecciona la letra de la columna que quieres reservar:
                    """, String.join(" | ", letters), separator, formattedSelectedRow);

            String selectedColumnLetter = InputRequester.requestString(columnsMessagePropmpt).toUpperCase();
            System.out.printf("Selected column letter: %s\n", selectedColumnLetter);
            int selectedColumnIndex = Arrays.asList(letters).indexOf(selectedColumnLetter);
            if (selectedColumnIndex < 0) {
                JOptionPane.showMessageDialog(null, "La columna seleccionada no existe. Inténtalo de nuevo.");
                continue;
            }

            // Validate seat availability
            int selectedRowIndex = selectedRow - 1;
            if (Objects.equals(seats[selectedRowIndex][selectedColumnIndex], "X")) {
                JOptionPane.showMessageDialog(null, "El asiento seleccionado ya fue reservado. Inténtalo de nuevo.");
                continue;
            }

            // Create reservation
            seats[selectedRowIndex][selectedColumnIndex] = "X";

            System.out.println(getFormattedSeats());

            // Check if all seats are taken
            areAllSeatsTaken = Arrays.stream(seats).flatMap(Arrays::stream).allMatch(seat -> Objects.equals(seat, "X"));
            System.out.printf("Are all seats taken: %b\n", areAllSeatsTaken);
        }

        JOptionPane.showMessageDialog(null, "Todos los asientos han sido reservados. Hasta pronto!");
    }
}
