import utils.InputRequester;

import java.util.Optional;

public class Exercise1 {

    public static void main(String[] args) {
        Integer age = InputRequester.requestInteger("Ingresa tu edad: ").orElseThrow();
        if (age >= 18) System.out.println("¡Se te permite entrar a la party!");
        else System.out.println("No puedes entrar a la fiesta. Lo lamento");
    }
}
