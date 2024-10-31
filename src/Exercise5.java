import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import utils.InputRequester;

import javax.swing.*;

public class Exercise5 {
    public record User(String name, String password, String email) {
        @Override
        public String toString() {
            return String.format("Username: %-10s | Password: %-10s | Email: %-10s", name(), password(), email());
        }
    }

    public static void main(String[] args) {
        boolean shouldContinueAsking = true;
        ArrayList<User> users = new ArrayList<>();

        while (shouldContinueAsking) {
            String username = InputRequester.requestString("Ingresa tu nombre de usuario");

            // Validate username
            Optional<User> foundUser = users.stream().filter(user -> user.name().equals(username)).findFirst();

            if (foundUser.isPresent()) {
                JOptionPane.showMessageDialog(null, "El nombre de usuario ya existe. Inténtalo de nuevo.");
                continue;
            }

            String password = InputRequester.requestString("Ingresa tu contraseña");

            // Password validation
            boolean isPasswordLongEnough = password.length() >= 8;
            if (!isPasswordLongEnough) {
                JOptionPane.showMessageDialog(null, "La contraseña debe tener al menos 8 caracteres. Inténtalo de nuevo.");
                continue;
            }

            boolean passwordHasUppercase = password.matches(".*[A-Z].*");
            if (!passwordHasUppercase) {
                JOptionPane.showMessageDialog(null, "La contraseña debe tener al menos una letra mayúscula. Inténtalo de nuevo.");
                continue;
            }
            boolean passwordHasOneNumber = password.matches(".*\\d.*");
            if (!passwordHasOneNumber) {
                JOptionPane.showMessageDialog(null, "La contraseña debe tener al menos un número. Inténtalo de nuevo.");
                continue;
            }

            String email = InputRequester.requestString("Ingresa tu correo");
            // Email validation
            boolean isEmailValid = email.matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?");
            if (!isEmailValid) {
                JOptionPane.showMessageDialog(null, "El correo electrónica no es válido. Inténtalo de nuevo.");
                continue;
            }

            users.add(new User(username, password, email));
            JOptionPane.showMessageDialog(null, "¡Usuario registrado satisfactoriamente!");

            shouldContinueAsking = JOptionPane.showConfirmDialog(
                    null,
                    "¿Deseas registrar otro usuario?",
                    "¿Añadir otro usuario?",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION;
        }

        List<String> usersAsstringList = convertUsersIntoStringList(users);
        String userListMessage = String.format("""
                ********************************* Lista de usuarios *********************************
                %s
                *************************************************************************************
                """, String.join("\n", usersAsstringList));
        JOptionPane.showMessageDialog(null, userListMessage);
    }

    public static List<String> convertUsersIntoStringList(ArrayList<User> users) {
        return users.stream().map(user -> String.format("""
                    | Username: %-15s | Password: %-15s | Email: %-15s |""", user.name(), user.password(), user.email())).toList();
    }

}
