import javax.swing.JOptionPane;
import utils.InputRequester;

/**
 * Ejercicio 17: Sistema de Evaluación de Proyectos
 * Crea un programa que permita evaluar proyectos en una empresa.
 * Instrucciones:
 * 1. Crea un arreglo para almacenar los nombres de 5 proyectos.
 * 2. Permite al usuario asignar una calificación a cada proyecto.
 * 3. Muestra los proyectos ordenados por su calificación, del más alto al más
 * bajo.
 */
public class Exercise17ProjectsEvaluation {
  private static record SortedProjects(String[] names, double[] scores) {
  }

  public static void main(String[] args) {
    String[] projectNames = new String[5];
    double[] projectScores = new double[5];

    // Register the 5 projects
    for (int i = 0; i < projectNames.length; i++) {
      projectNames[i] = InputRequester.requestString(
          String.format("Ingrese el nombre del proyecto #%d:", i + 1));

      double score;
      do {
        score = InputRequester.requestDouble(
            String.format("Ingrese la calificación para '%s' (0-10):", projectNames[i])).orElseThrow();

        if (score < 0 || score > 10) {
          JOptionPane.showMessageDialog(null, "La calificación debe estar entre 0 y 10.");
        }
      } while (score < 0 || score > 10);

      projectScores[i] = score;
    }

    // Get sorted arrays
    SortedProjects sorted = sortProjectsByScoreDesc(projectNames, projectScores);

    // Show results
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < sorted.names.length; i++) {
      result.append(String.format("%s: %.2f\n", sorted.names[i], sorted.scores[i]));
    }

    JOptionPane.showMessageDialog(null,
        String.format("""
            Proyectos ordenados por calificación:

            %s
            El proyecto mejor calificado es '%s' con %.2f puntos.
            """,
            result,
            sorted.names[0],
            sorted.scores[0]),
        "Resultados de la evaluación",
        JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * Ordena los proyectos por calificación de mayor a menor usando el algoritmo
   * de burbuja (bubble sort).
   * 
   * @param names  Array con los nombres de los proyectos
   * @param scores Array con las calificaciones de los proyectos
   * @return Un record {@link SortedProjects} con las copias de los arrays
   *         ordenados de nombres y calificaciones
   */
  private static SortedProjects sortProjectsByScoreDesc(String[] names, double[] scores) {
    String[] sortedNames = names.clone();
    double[] sortedScores = scores.clone();

    for (int i = 0; i < sortedNames.length - 1; i++) {
      for (int j = 0; j < sortedNames.length - i - 1; j++) {
        if (sortedScores[j] < sortedScores[j + 1]) {
          // Intercambiar calificaciones
          double tempScore = sortedScores[j];
          sortedScores[j] = sortedScores[j + 1];
          sortedScores[j + 1] = tempScore;

          // Intercambiar nombres
          String tempName = sortedNames[j];
          sortedNames[j] = sortedNames[j + 1];
          sortedNames[j + 1] = tempName;
        }
      }
    }

    return new SortedProjects(sortedNames, sortedScores);
  }
}
