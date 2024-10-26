import utils.InputRequester;

import javax.swing.*;

public class Exercise2 {
    public static void main(String[] args) {
        int maxAssistants = InputRequester.requestInteger("Ingresa el número máximo de asistentes para el evento:").orElseThrow();

        while (maxAssistants > 0) {
            Integer entriesToBuy;
            while (true) {
                entriesToBuy = InputRequester.requestInteger("¿Cuántas entradas deseas comprar?").orElseThrow();
                if (entriesToBuy <= maxAssistants) break;
                JOptionPane.showMessageDialog(
                        null,
                        String.format("No puedes comprar '%d' entradas. Inténtalo de nuevo.%n", entriesToBuy),
                        "Entradas inválidas",
                        JOptionPane.ERROR_MESSAGE);
            }
            maxAssistants -= entriesToBuy;

            JOptionPane.showMessageDialog(null, String.format("Has comprado '%d' entradas ¡Disfruta el evento!", entriesToBuy));
        }
        JOptionPane.showMessageDialog(null, "No hay más entradas");
    }
}
