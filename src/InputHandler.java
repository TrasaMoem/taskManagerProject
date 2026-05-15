import java.util.InputMismatchException;
import java.util.Scanner;

public class InputHandler {
    private final Scanner sc = new Scanner(System.in);

    public int readNumber(int limit) {
        while (true) {
            int action;
            try {
                action = Integer.parseInt(sc.nextLine());
                if (action < 1 || action > limit) {
                    System.out.println("Invalid input, please try again");
                    continue;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please try again");
                continue;
            }
            return action;
        }

    }

    public String readString() {
        return sc.nextLine();
    }
}
