import javax.swing.JOptionPane;
import utils.InputRequester;

/**
 * Ejercicio 16: Control de Gastos Mensuales
 * Crea una aplicación para que el usuario registre sus gastos mensuales.
 * Instrucciones:
 * 1. Usa un arreglo para almacenar los gastos de cada mes.
 * 2. Permite al usuario ingresar un gasto mensual durante 12 meses.
 * 3. Calcula el gasto total y el promedio al final del año
 */
public class Exercise16MonthlyExpenses {
  private static final int MONTHS_IN_YEAR = 12;
  private static final Double[] monthlyExpenses = new Double[MONTHS_IN_YEAR];
  private static final String[] months = {
      "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
      "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
  };

  private static class ExpenseSummary {
    private final double totalExpenses;
    private final double averageExpense;

    ExpenseSummary(double totalExpenses, double averageExpense) {
      this.totalExpenses = totalExpenses;
      this.averageExpense = averageExpense;
    }
  }

  private static ExpenseSummary calculateExpenseSummary(Double[] expenses) {
    double total = 0;
    for (Double expense : expenses) {
      total += expense;
    }
    double average = total / expenses.length;
    return new ExpenseSummary(total, average);
  }

  public static void main(String[] args) {
    // Input expenses for each month
    for (int i = 0; i < MONTHS_IN_YEAR; i++) {
      while (true) {
        Double expense = InputRequester
            .requestDouble(String.format("Ingrese el gasto para %s:", months[i]))
            .orElseThrow();

        if (expense >= 0) {
          monthlyExpenses[i] = expense;
          break;
        }
        JOptionPane.showMessageDialog(null,
            "El gasto no puede ser negativo. Inténtalo de nuevo.");
      }
    }

    ExpenseSummary summary = calculateExpenseSummary(monthlyExpenses);

    JOptionPane.showMessageDialog(null,
        String.format("""
            Resumen de Gastos Mensuales:

            Gasto total anual: $%.2f
            Promedio mensual: $%.2f""",
            summary.totalExpenses,
            summary.averageExpense));
  }
}