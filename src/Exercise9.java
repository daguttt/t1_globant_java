/*
 * Ejercicio 9: Sistema de Evaluación de Empleados (Condicionales y Arrays)
 * Desarrolla un sistema de evaluación de desempeño para empleados.
 * Instrucciones:
 * 1. Crea un arreglo para almacenar los nombres de 5 empleados.
 * 2. Permite al usuario asignar una calificación de desempeño (entre 1 y 10) a
 * cada empleado.
 * 3. Muestra el nombre del empleado con la calificación más alta y más baja al
 * final.
 */

import javax.swing.JOptionPane;

import utils.InputRequester;

public class Exercise9 {
  private static final int NUMBER_OF_EMPLOYEES = 5;
  private static final String[] employees = new String[NUMBER_OF_EMPLOYEES];
  private static final Integer[] scores = new Integer[NUMBER_OF_EMPLOYEES];

  private static class EvaluationResult {
    private final String highestScoreEmployeeName;
    private final String lowestScoreEmployeeName;
    private final int highestScore;
    private final int lowestScore;

    EvaluationResult(String highestScoreEmployee, String lowestScoreEmployee, int highestScore, int lowestScore) {
      this.highestScoreEmployeeName = highestScoreEmployee;
      this.lowestScoreEmployeeName = lowestScoreEmployee;
      this.highestScore = highestScore;
      this.lowestScore = lowestScore;
    }
  }

  private static EvaluationResult findHighestAndLowestScores(String[] employees, Integer[] scores) {
    int highestScore = scores[0];
    int lowestScore = scores[0];
    String employeeWithHighestScore = employees[0];
    String employeeWithLowestScore = employees[0];

    for (int i = 1; i < scores.length; i++) {
      if (scores[i] > highestScore) {
        highestScore = scores[i];
        employeeWithHighestScore = employees[i];
      }
      if (scores[i] < lowestScore) {
        lowestScore = scores[i];
        employeeWithLowestScore = employees[i];
      }
    }

    return new EvaluationResult(employeeWithHighestScore, employeeWithLowestScore, highestScore, lowestScore);
  }

  public static void main(String[] args) {
    // Request employees names
    for (int i = 0; i < NUMBER_OF_EMPLOYEES; i++) {
      String employeeName = InputRequester.requestString(String.format("Ingresa el nombre del empleado #%d:", i + 1));
      employees[i] = employeeName;
    }

    // Request employees scores
    for (int i = 0; i < NUMBER_OF_EMPLOYEES; i++) {
      while (true) {
        Integer score = InputRequester
            .requestInteger(String.format("Ingresa la calificación para %s (1-10):", employees[i])).orElseThrow();
        if (score >= 1 && score <= 10) {
          scores[i] = score;
          break;
        }
        JOptionPane.showMessageDialog(null, "La calificación debe estar entre 1 y 10. Inténtalo de nuevo.");
      }
    }

    EvaluationResult result = findHighestAndLowestScores(employees, scores);

    JOptionPane.showMessageDialog(null, String.format("""
        Resultados de la evaluación:

        Mejor desempeño: %s con calificación de %d
        Menor desempeño: %s con calificación de %d
        """, result.highestScoreEmployeeName, result.highestScore,
        result.lowestScoreEmployeeName, result.lowestScore));
  }
}
