import utils.InputRequester;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Exercise3 {
    private static final ArrayList<HashMap<String, Object>> taskList = new ArrayList<>();

    public static void main(String[] args) {
        // In memory Db


        boolean isMenuOpened = true;
        while (isMenuOpened) {
            String menuOptionsMessage = """
                    ********************* Menu *********************

                    Ingresa la opción que deseas hacer:

                    0. Salir.
                    1. Agregar tarea.
                    2. Marcar tarea como completada.
                    3. Listar tareas.

                    ************************************************
                    """;
            var option = InputRequester.requestString(menuOptionsMessage);

            switch (option) {
                case "0" -> isMenuOpened = false;
                case "1" -> {
                    String taskTitle = InputRequester.requestString("Ingresa el título de la tarea:");
                    HashMap<String, Object> newTask = new HashMap<>(Map.of("title", taskTitle, "status", false));
                    taskList.add(newTask);
                    JOptionPane.showMessageDialog(null, "¡Tarea agregada satisfactoriamente!");
                }
                case "2" -> {
                    if (taskList.isEmpty()) {
                        JOptionPane.showMessageDialog(null,
                                "No hay tareas agregadas actualmente.");
                        break;
                    }

                    // Request task to modify status
                    int taskIndexToComplete = InputRequester.requestAnIndexFrom(convertTasksIntoString(),
                            "Ingresa el número de la tarea que quieres marcar como completada:");
                    HashMap<String, Object> taskToUpdate = taskList.get(taskIndexToComplete);
                    taskToUpdate.put("status", true);
                    JOptionPane.showMessageDialog(null, String.format("¡Tarea %s marcada como completada!", taskToUpdate.get("title")));


                }
                case "3" -> {
                    if (taskList.isEmpty()) {
                        JOptionPane.showMessageDialog(null,
                                "No hay tareas agregadas actualmente.");
                        break;
                    }

                    JOptionPane.showMessageDialog(null,
                            String.format("Listado de tareas: %n%s", String.join("\n--------------\n", convertTasksIntoString())));
                }
                default -> JOptionPane.showMessageDialog(null, "Opción inválida. Inténtalo de nuevo");
            }
        }
    }

    public static List<String> convertTasksIntoString() {
        return taskList.stream().map(task -> {
            String taskStatusAsString = (boolean) task.get("status") ? "Completada" : "Pendiente";
            return String.format("""
                    Titulo: '%s' | Estado: '%s'""", task.get("title"), taskStatusAsString);
        }).toList();
    }
}
