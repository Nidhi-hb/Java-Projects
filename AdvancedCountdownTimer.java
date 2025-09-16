import java.util.Scanner;

public class AdvancedCountdownTimer {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int count = 0;
        int currentCount = 0;
        boolean running = false;

        while (true) {
            System.out.println("\n1. Set Countdown");
            System.out.println("2. Start Countdown");
            System.out.println("3. Pause Countdown");
            System.out.println("4. Reset Countdown");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter countdown start number: ");
                    count = scanner.nextInt();
                    currentCount = count;
                    running = false;
                    System.out.println("Countdown set to " + count);
                    break;
                case 2:
                    if (count <= 0) {
                        System.out.println("Set a countdown first.");
                        break;
                    }
                    running = true;
                    System.out.println("Starting countdown...");
                    while (currentCount >= 0 && running) {
                        System.out.println(currentCount);
                        Thread.sleep(1000);
                        currentCount--;
                        
                        if (!running) {
                            break;
                        }
                    }
                    if (currentCount < 0) {
                        System.out.println("Time's up!");
                        running = false;
                    }
                    break;
                case 3:
                    if (running) {
                        running = false;
                        System.out.println("Countdown paused at " + currentCount);
                    } else {
                        System.out.println("Countdown is not running.");
                    }
                    break;
                case 4:
                    currentCount = count;
                    running = false;
                    System.out.println("Countdown reset to " + count);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
