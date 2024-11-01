
/**
 * Ejercicio 10: Aplicación de Encuestas de Satisfacción
 * Crea una aplicación para encuestar a los clientes sobre su nivel de
 * satisfacción
 * con un servicio.
 * Instrucciones:
 * 1. Solicita a 10 clientes que califiquen su satisfacción del 1 al 5.
 * 2. Usa un arreglo para almacenar las respuestas.
 * 3. Calcula el promedio de satisfacción al final y muestra cuántos clientes
 * dieron la calificación máxima (5).
 */

import javax.swing.JOptionPane;
import utils.InputRequester;

public class Exercise10 {
  private static final int NUMBER_OF_CLIENTS = 10;
  private static final Integer[] satisfactionScores = new Integer[NUMBER_OF_CLIENTS];

  private static class SurveyResult {
    private final double averageSatisfaction;
    private final int maxScoreCount;

    SurveyResult(double averageSatisfaction, int maxScoreCount) {
      this.averageSatisfaction = averageSatisfaction;
      this.maxScoreCount = maxScoreCount;
    }
  }

  private static SurveyResult calculateSurveyResults(Integer[] scores) {
    int sum = 0;
    int maxScoreCount = 0;

    for (int score : scores) {
      sum += score;
      if (score == 5) {
        maxScoreCount++;
      }
    }

    double average = (double) sum / scores.length;
    return new SurveyResult(average, maxScoreCount);
  }

  public static void main(String[] args) {
    // Request satisfaction scores from clients
    for (int i = 0; i < NUMBER_OF_CLIENTS; i++) {
      while (true) {
        Integer score = InputRequester
            .requestInteger(String.format("Cliente #%d, califique su nivel de satisfacción (1-5):", i + 1))
            .orElseThrow();

        if (score >= 1 && score <= 5) {
          satisfactionScores[i] = score;
          break;
        }
        JOptionPane.showMessageDialog(null, "La calificación debe estar entre 1 y 5. Inténtalo de nuevo.");
      }
    }

    SurveyResult result = calculateSurveyResults(satisfactionScores);

    JOptionPane.showMessageDialog(null, String.format("""
        Resultados de la encuesta:

        Promedio de satisfacción: %.2f
        Cantidad de clientes que dieron calificación máxima (5): %d
        """, result.averageSatisfaction, result.maxScoreCount));
  }
}
