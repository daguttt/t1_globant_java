import utils.InputRequester;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Ejercicio 19: Sistema de Control de Inventario con Precios
 * Implementa un sistema para gestionar el inventario de productos y sus precios
 * en
 * una tienda.
 * Instrucciones:
 * 1. Usa dos arreglos: uno para los nombres de los productos y otro para sus
 * precios.
 * 2. Permite al usuario agregar productos y precios.
 * 3. Muestra el inventario junto con los precios al final.
 */
public class Exercise19InventoryWithPrices {
  private static final ArrayList<String> productNames = new ArrayList<>(
      List.of("Teclado", "Mouse", "Monitor"));
  private static final ArrayList<Double> productPrices = new ArrayList<>(
      List.of(500.0, 20.0, 1000.0));

  public static void main(String[] args) {
    boolean isMenuOpened = true;
    while (isMenuOpened) {
      String menuOptionsMessage = """
          ********************* Menu *********************

          Ingresa la opción que deseas hacer:

          0. Salir.
          1. Agregar producto.
          2. Mostrar inventario.

          ************************************************
          """;
      var option = InputRequester.requestString(menuOptionsMessage);

      switch (option) {
        case "0" -> {
          JOptionPane.showMessageDialog(null, "¡Hasta luego!");
          isMenuOpened = false;
          break;
        }
        case "1" -> {
          String name = InputRequester.requestString("Ingresa el nombre del producto:");

          // Validate name uniqueness
          List<String> productNamesLowerCase = productNames.stream().map(String::toLowerCase).toList();
          if (productNamesLowerCase.contains(name.toLowerCase())) {
            JOptionPane.showMessageDialog(null, "El producto ya existe. Inténtalo de nuevo.");
            break;
          }

          double price = InputRequester.requestDouble("Ingresa el precio del producto").orElseThrow();

          productNames.add(name);
          productPrices.add(price);
          JOptionPane.showMessageDialog(null, "¡Producto agregado satisfactoriamente!");
        }
        case "2" -> {
          if (productNames.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                "No hay productos disponibles actualmente.");
            break;
          }

          // Show inventory
          String message = """
              ********************* Inventario *********************
              Listado de productos:

              %s

              Total de productos: %d
              ******************************************************""".formatted(
              String.join("\n", convertProductsIntoListString()),
              productNames.size());

          JOptionPane.showMessageDialog(null, message);
        }
        default -> JOptionPane.showMessageDialog(null, "Opción inválida. Inténtalo de nuevo");
      }
    }
  }

  public static List<String> convertProductsIntoListString() {
    List<String> productList = new ArrayList<>();
    for (int i = 0; i < productNames.size(); i++) {
      String productPrice = String.format("$%,-12.2f", productPrices.get(i));
      productList.add(String.format("""
          | Producto: %-10s | Precio: %s |""", productNames.get(i), productPrice));
    }
    return productList;
  }
}