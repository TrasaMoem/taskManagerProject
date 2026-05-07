import java.time.LocalDateTime;
import java.util.Scanner;

public class InputHandler {

    public static int readNumber(Scanner sc, int actionsQuantity) {
        while (true) {
            int action;
            try {
                action = sc.nextInt();
                if (action < 1 || action > actionsQuantity) {
                    System.out.println("Invalid input, please try again");
                    sc.nextLine();
                    continue;
                }
            } catch (Exception e) {
                System.out.println("Invalid input, please try again");
                sc.nextLine();
                continue;
            }
            sc.nextLine();
            return action;
        }
    }

    public static String readString(Scanner sc) {
        while(true) {
            String str;
            try {
                str = sc.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid input, please try another one");
                sc.nextLine();
                continue;
            }
            return str;
        }
    }

    public static LocalDateTime readDateTime(Scanner sc, boolean everydayOrGlobal) {
        if (everydayOrGlobal) {
            while(true) {
                LocalDateTime dateTime;
                try {
                    int hour = sc.nextInt();
                    if (hour < 1) {
                        System.out.println("Invalid input, please try again");
                        continue;
                    }
                    dateTime = LocalDateTime.now().plusHours(hour);
                } catch (Exception e) {
                    System.out.println("Invalid input, please try another one");
                    sc.nextLine();
                    continue;
                }
                sc.nextLine();
                return dateTime;
            }
        } else {
            while(true) {
                LocalDateTime dateTime;
                try {
                    int days = sc.nextInt();
                    if (days < 1) {
                        System.out.println("Invalid input, please try again");
                        continue;
                    }
                    dateTime = LocalDateTime.now().plusDays(days);
                } catch (Exception e) {
                    System.out.println("Invalid input, please try another one");
                    sc.nextLine();
                    continue;
                }
                sc.nextLine();
                return dateTime;
            }
        }
    }

}
