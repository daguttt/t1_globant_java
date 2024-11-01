
/**
 * Ejercicio 11: Sistema de Inventario de Productos con Cantidades
 * Desarrolla un sistema para gestionar el inventario de productos en una
 * tienda,
 * donde cada producto tiene una cantidad asociada.
 * Instrucciones:
 * 1. Crea dos arreglos: uno para los nombres de los productos y otro para la
 * cantidad en stock.
 * 2. Permite al usuario agregar nuevos productos y actualizar las cantidades.
 * 3. Muestra la lista de productos junto con las cantidades actualizadas.
 */

import javax.swing.JOptionPane;
import utils.InputRequester;

public class Exercise11 {
  public static void main(String[] args) {
    String[] productNames = new String[100]; // Array para nombres de productos
    int[] productQuantities = new int[100]; // Array para cantidades
    int productCount = 0;
    boolean isMenuOpened = true;

    while (isMenuOpened) {
      String menuPrompt = """
          Sistema de Inventario de Productos

          0. Salir
          1. Agregar producto
          2. Actualizar cantidad
          3. Mostrar inventario

          Seleccione una opción:
          """;

      int selectedOption = InputRequester.requestInteger(menuPrompt).orElseThrow();

      switch (selectedOption) {
        case 0 -> {
          JOptionPane.showMessageDialog(null, "¡Hasta luego!");
          isMenuOpened = false;
        }
        case 1 -> {
          if (productCount >= productNames.length) {
            JOptionPane.showMessageDialog(null, "El inventario está lleno.");
            break;
          }

          String name = InputRequester.requestString("Ingrese el nombre del producto:");
          int quantity = InputRequester.requestInteger("Ingrese la cantidad del producto:").orElseThrow();

          productNames[productCount] = name;
          productQuantities[productCount] = quantity;
          productCount++;

          JOptionPane.showMessageDialog(null, "Producto agregado exitosamente.");
        }
        case 2 -> {
          if (productCount == 0) {
            JOptionPane.showMessageDialog(null, "No hay productos registrados.");
            break;
          }

          String productName = InputRequester.requestString("Ingrese el nombre del producto a actualizar:");
          int productIndex = -1;

          for (int i = 0; i < productCount; i++) {
            if (productNames[i].equalsIgnoreCase(productName)) {
              productIndex = i;
              break;
            }
          }

          if (productIndex == -1) {
            JOptionPane.showMessageDialog(null, "Producto no encontrado.");
            break;
          }

          int newQuantity = InputRequester.requestInteger(
              "Ingrese la nueva cantidad para '" + productNames[productIndex] + "':").orElseThrow();

          productQuantities[productIndex] = newQuantity;
          JOptionPane.showMessageDialog(null, "Cantidad actualizada exitosamente.");
        }
        case 3 -> {
          if (productCount == 0) {
            JOptionPane.showMessageDialog(null, "No hay productos registrados.");
            break;
          }

          StringBuilder inventory = new StringBuilder("Inventario de Productos:\n\n");
          for (int i = 0; i < productCount; i++) {
            inventory.append(String.format("%s - Cantidad: %d%n",
                productNames[i], productQuantities[i]));
          }
          JOptionPane.showMessageDialog(null, inventory.toString());
        }
        default -> JOptionPane.showMessageDialog(null, "Opción no válida. Por favor, intente nuevamente.");
      }
    }
  }
}
