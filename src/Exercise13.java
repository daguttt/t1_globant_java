import java.util.Arrays;

import javax.swing.JOptionPane;

import utils.InputRequester;

/**
 * Ejercicio 13: Sistema de Votación Electrónica
 * Implementa un sistema de votación electrónica para elegir un representante.
 * Instrucciones:
 * 1. Crea un arreglo con los nombres de 3 candidatos.
 * 2. Permite a 10 usuarios votar por uno de los candidatos.
 * 3. Muestra los resultados de la votación al final, indicando quién ganó.
 */

public class Exercise13 {
  public static void main(String[] args) {
    String[] candidates = { "Diego", "Juan", "Maria" };
    int[] votes = new int[candidates.length];

    for (int i = 0; i < 10; i++) {
      int candidateIndex = InputRequester.requestAnIndexFrom(Arrays.asList(candidates),
          "Ingresa el número del candidato al que deseas votar:");
      votes[candidateIndex]++;
      JOptionPane.showMessageDialog(null, String.format("Voto registrado para %s", candidates[candidateIndex]),
          String.format("Registrando voto: #%d", i + 1), JOptionPane.INFORMATION_MESSAGE);
    }

    StringBuilder result = new StringBuilder();
    for (int i = 0; i < candidates.length; i++) {
      result.append(String.format("%s: %d\n", candidates[i], votes[i]));
    }

    int winnerIndex = Arrays.stream(votes).max().orElseThrow();

    JOptionPane.showMessageDialog(null, String.format("""
        Resultados de la votación:

        %s
        El ganador es %s con %d votos.
        """, result, candidates[winnerIndex], votes[winnerIndex]), "Resultados de la votación",
        JOptionPane.INFORMATION_MESSAGE);
  }
}
