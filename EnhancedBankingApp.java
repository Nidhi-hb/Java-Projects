import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EnhancedBankingApp {
    static double balance = 0.0;
    static List<String> transactions = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Enhanced Banking Application ---");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transaction History");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Current balance: $" + balance);
                    break;
                case 2:
                    System.out.print("Enter amount to deposit: ");
                    double deposit = scanner.nextDouble();
                    if (deposit > 0) {
                        balance += deposit;
                        transactions.add("Deposited: $" + deposit);
                        System.out.println("Deposited: $" + deposit);
                    } else {
                        System.out.println("Invalid amount.");
                    }
                    break;
                case 3:
                    System.out.print("Enter amount to withdraw: ");
                    double withdraw = scanner.nextDouble();
                    if (withdraw > 0 && withdraw <= balance) {
                        balance -= withdraw;
                        transactions.add("Withdrawn: $" + withdraw);
                        System.out.println("Withdrawn: $" + withdraw);
                    } else {
                        System.out.println("Invalid amount or insufficient balance.");
                    }
                    break;
                case 4:
                    System.out.println("\nTransaction History:");
                    if (transactions.isEmpty()) {
                        System.out.println("No transactions yet.");
                    } else {
                        for (String t : transactions) {
                            System.out.println(t);
                        }
                    }
                    break;
                case 5:
                    exit = true;
                    System.out.println("Thank you for using the Enhanced Banking App.");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
