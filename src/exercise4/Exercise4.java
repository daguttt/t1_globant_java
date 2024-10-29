package exercise4;

import utils.InputRequester;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Exercise4 {
    private static final ArrayList<Product> productList = new ArrayList<>(
            List.of(new Product("Teclado", 500, 10),
                    new Product("Mouse", 20, 5),
                    new Product("Monitor", 1000, 20)));

    public static void main(String[] args) {
        boolean isMenuOpened = true;
        while (isMenuOpened) {
            String menuOptionsMessage = """
                    ********************* Menu *********************
                    
                    Ingresa la opción que deseas hacer:
                    
                    0. Salir.
                    1. Agregar producto.
                    2. Comprar producto.
                    3. Eliminar producto.
                    4. Mostrar inventario.
                    
                    ************************************************
                    """;
            var option = InputRequester.requestString(menuOptionsMessage);

            switch (option) {
                case "0" -> isMenuOpened = false;
                case "1" -> {
                    String name = InputRequester.requestString("Ingresa el nombre del producto:");

                    // Validate name uniqueness
                    Optional<Product> foundProduct = productList.stream().filter(product -> product.name().equals(name)).findFirst();

                    if (foundProduct.isPresent()) {
                        JOptionPane.showMessageDialog(null, "El producto ya existe. Inténtalo de nuevo.");
                        break;
                    }

                    double price = InputRequester.requestDouble("Ingresa el precio del producto").orElseThrow();
                    int stock = InputRequester.requestInteger("Ingresa el stock del producto").orElseThrow();
                    Product newProduct = new Product(name, price, stock);
                    productList.add(newProduct);
                    JOptionPane.showMessageDialog(null, "¡Producto agregado satisfactoriamente!");
                }
                case "2" -> {
                    if (productList.isEmpty()) {
                        JOptionPane.showMessageDialog(null,
                                "No hay productos disponibles actualmente.");
                        break;
                    }

                    // Request product to buy
                    int productIndexToBuy = InputRequester.requestAnIndexFrom(convertProductsIntoListString(),
                            String.format("Ingresa el número del producto que quieres comprar:%n(Presiona enter para cancelar)"), true);

                    if (productIndexToBuy == -1) break;

                    Product productToBuy = productList.get(productIndexToBuy);

                    // Check if product is out of stock
                    if (productToBuy.stock() == 0) {
                        JOptionPane.showMessageDialog(null,
                                String.format("No hay stock de %s disponible.", productToBuy.name()));
                        break;
                    }

                    // Request amount to buy
                    int amountToBuy = InputRequester.requestInteger(String.format("Ingresa la cantidad de '%s' que quieres comprar:", productToBuy.name())).orElseThrow();

                    if (amountToBuy > productToBuy.stock()) {
                        JOptionPane.showMessageDialog(
                                null,
                                String.format("No puedes comprar '%1$d' %2$s. El stock de %2$s es '%3$d'.%n", amountToBuy, productToBuy.name(), productToBuy.stock()),
                                "Stock insuficiente",
                                JOptionPane.ERROR_MESSAGE);
                        break;
                    }

                    var updatedProduct = new Product(productToBuy.name(), productToBuy.price(), productToBuy.stock() - amountToBuy);
                    productList.set(productIndexToBuy, updatedProduct);
                    JOptionPane.showMessageDialog(null, String.format("¡Has comprado %d %s!", amountToBuy, productToBuy.name()));
                }
                case "3" -> {
                    if (productList.isEmpty()) {
                        JOptionPane.showMessageDialog(null,
                                "No hay productos disponibles actualmente.");
                        break;
                    }

                    // Request product to remove
                    int productIndexToRemove = InputRequester.requestAnIndexFrom(convertProductsIntoListString(),
                            "Ingresa el número del producto que quieres comprar:");
                    Product productToRemove = productList.get(productIndexToRemove);

                    // Check if the product does not have stock available
                    if (productToRemove.stock() > 0) {
                        JOptionPane.showMessageDialog(
                                null,
                                String.format("No puedes eliminar el producto: %s. Aun tiene stock disponible: '%d'.%n", productToRemove.name(), productToRemove.stock()),
                                "No puedes eliminar producto con stock",
                                JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    productList.remove(productToRemove);
                    JOptionPane.showMessageDialog(null, String.format("¡Producto '%s' eliminado!", productToRemove.name()));
                }
                case "4" -> {
                    if (productList.isEmpty()) {
                        JOptionPane.showMessageDialog(null,
                                "No hay productos disponibles actualmente.");
                        break;
                    }

                    // Show inventory
                    int totalStock = productList.stream().mapToInt(Product::stock).sum();
                    String message = String.format("""
                            ********************* Inventario *********************
                            Listado de productos:
                            
                            %s
                            
                            Stock total de productos: %d
                            ******************************************************""",
                            String.join("\n", convertProductsIntoListString()), totalStock);

                    JOptionPane.showMessageDialog(null, message);
                }
                default -> JOptionPane.showMessageDialog(null, "Opción inválida. Inténtalo de nuevo");
            }
        }
    }

    public static List<String> convertProductsIntoListString() {
        return productList.stream().map(product -> {
            String productStock = (product.stock() > 0) ? String.valueOf(product.stock()) : "Sin stock";
            String productPrice = String.format("$%,-12.2f", product.price());
            return String.format("""
                    | Producto: %-10s | Precio: %s | Stock: %-4s |""", product.name(), productPrice, productStock);
        }).toList();
    }
}
