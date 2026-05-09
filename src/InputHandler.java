import java.time.LocalDateTime;
import java.util.Scanner;

public class InputHandler {
    public int readNumber(int limit) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            int action;
            try {
                action = sc.nextInt();
                if (action < 1 || action > limit) {
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
            sc.close();
            return action;
        }
    }

    public String readString() {
        Scanner sc = new Scanner(System.in);
        while(true) {
            String str;
            try {
                str = sc.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid input, please try another one");
                sc.nextLine();
                continue;
            }
            sc.close();
            return str;
        }
    }

    public LocalDateTime readDateTime(TaskScale scale) {
        Scanner sc = new Scanner(System.in);
        if (scale == TaskScale.EVERYDAY) {
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
                sc.close();
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
                sc.close();
                return dateTime;
            }
        }
    }

}
