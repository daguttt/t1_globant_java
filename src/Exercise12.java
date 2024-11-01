import javax.swing.JOptionPane;

import utils.InputRequester;

/**
 * Ejercicio 12: Sistema de Pago para Facturas
 * Desarrolla un sistema de pago para facturas de servicios (electricidad, agua,
 * internet).
 * 
 * Instrucciones:
 * 1. Crea un sistema que permita al usuario ingresar el monto de tres facturas.
 * 2. Calcula el total a pagar.
 * 3. Permite al usuario pagar cada factura individualmente, descontando el
 * monto total.
 */

public class Exercise12 {
  private static class Bill {
    private final String name;
    private double amount;

    public Bill(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    public double getAmount() {
      return amount;
    }

    public void setAmount(double amount) {
      this.amount = amount;
    }
  }

  public static void main(String[] args) {
    Bill[] billsToPay = { new Bill("agua"), new Bill("electricidad"), new Bill("internet") };
    double balance = 10_000d;

    // Request the amount of three bills
    double waterBill = InputRequester.requestDouble("Ingrese el monto de la factura de agua:").orElseThrow();
    billsToPay[0].setAmount(waterBill);

    double electricityBill = InputRequester.requestDouble("Ingrese el monto de la factura de electricidad:")
        .orElseThrow();
    billsToPay[1].setAmount(electricityBill);

    double internetBill = InputRequester.requestDouble("Ingrese el monto de la factura de internet:").orElseThrow();
    billsToPay[2].setAmount(internetBill);

    double totalBill = waterBill + electricityBill + internetBill;
    JOptionPane.showMessageDialog(null, String.format("El total a pagar es: %.2f", totalBill));

    // Ask the user to pay each bill
    for (Bill bill : billsToPay) {
      int wantsToPay = JOptionPane.showConfirmDialog(null,
          String.format("¿Desea pagar la factura de %s?", bill.getName()), "Pago de factura",
          JOptionPane.YES_NO_OPTION);
      if (wantsToPay == JOptionPane.NO_OPTION) {
        JOptionPane.showMessageDialog(null, String.format("Saltando el pago de la factura de %s...", bill.getName()));
        continue;
      }

      balance -= bill.getAmount();
      JOptionPane.showMessageDialog(null,
          String.format("Factura de %s pagada exitosamente. El saldo restante es: %.2f", bill.getName(), balance));
    }

    JOptionPane.showMessageDialog(null, String.format("El saldo restante final es: %.2f. ¡Hasta pronto!", balance));
  }
}
