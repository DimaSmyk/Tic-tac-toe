import java.util.Scanner;

public class Validate {

    public static int validateNumberInt(Scanner scanner){
        while (!scanner.hasNextInt()) {
            String str = scanner.nextLine().trim();
            System.out.printf("\"%s\" - it is not number!\n", str);
            System.out.println("Enter number: ");
        }
        int quantity = scanner.nextInt();
        while (quantity <= 0){
            System.out.println("Неверное значение!! Введите количество: ");
            while (!scanner.hasNextInt()) {
                String str = scanner.next().trim();
                System.out.printf("\"%s\" - не число!\n", str);
                System.out.println("Введите количество !!: ");
            }
            quantity = scanner.nextInt();
        }
        return quantity;
    }

    public static String extracted(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
