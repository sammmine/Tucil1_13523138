import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.print("\033[H\033[2J");
        System.out.println("\n" + //
                        " ██▓  █████      ██▓███   █    ██ ▒███████▒▒███████▒ ██▓    ▓█████  ██▀███      ██▓███   ██▀███   ▒█████  \n" + //
                        "▓██▒▒██▓  ██▒   ▓██░  ██▒ ██  ▓██▒▒ ▒ ▒ ▄▀░▒ ▒ ▒ ▄▀░▓██▒    ▓█   ▀ ▓██ ▒ ██▒   ▓██░  ██▒▓██ ▒ ██▒▒██▒  ██▒\n" + //
                        "▒██▒▒██▒  ██░   ▓██░ ██▓▒▓██  ▒██░░ ▒ ▄▀▒░ ░ ▒ ▄▀▒░ ▒██░    ▒███   ▓██ ░▄█ ▒   ▓██░ ██▓▒▓██ ░▄█ ▒▒██░  ██▒\n" + //
                        "░██░░██  █▀ ░   ▒██▄█▓▒ ▒▓▓█  ░██░  ▄▀▒   ░  ▄▀▒   ░▒██░    ▒▓█  ▄ ▒██▀▀█▄     ▒██▄█▓▒ ▒▒██▀▀█▄  ▒██   ██░\n" + //
                        "░██░░▒███▒█▄    ▒██▒ ░  ░▒▒█████▓ ▒███████▒▒███████▒░██████▒░▒████▒░██▓ ▒██▒   ▒██▒ ░  ░░██▓ ▒██▒░ ████▓▒░\n" + //
                        "░▓  ░░ ▒▒░ ▒    ▒▓▒░ ░  ░░▒▓▒ ▒ ▒ ░▒▒ ▓░▒░▒░▒▒ ▓░▒░▒░ ▒░▓  ░░░ ▒░ ░░ ▒▓ ░▒▓░   ▒▓▒░ ░  ░░ ▒▓ ░▒▓░░ ▒░▒░▒░ \n" + //
                        " ▒ ░ ░ ▒░  ░    ░▒ ░     ░░▒░ ░ ░ ░░▒ ▒ ░ ▒░░▒ ▒ ░ ▒░ ░ ▒  ░ ░ ░  ░  ░▒ ░ ▒░   ░▒ ░       ░▒ ░ ▒░  ░ ▒ ▒░ \n" + //
                        " ▒ ░   ░   ░    ░░        ░░░ ░ ░ ░ ░ ░ ░ ░░ ░ ░ ░ ░  ░ ░      ░     ░░   ░    ░░         ░░   ░ ░ ░ ░ ▒  \n" + //
                        " ░      ░                   ░       ░ ░      ░ ░        ░  ░   ░  ░   ░                    ░         ░ ░  \n" + //
                        "                                  ░        ░                                                              \n" + //
                        "");
        
        // AUTO (PROGRAM)
        // File filee = new File("../test/4.txt");
        // FileHandler.ReadFile(filee.getAbsolutePath());

        // MANUAL
        System.out.println("\nEnter File Name (ex: ../test/file.txt):");
        System.out.print(">> ");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();
        File file = new File(answer);
        FileHandler.ReadFile(file.getAbsolutePath());

        if (FileHandler.err == 0) {
            System.out.println("\n...\n");
            
            Algorithm.initializeBoard();

            long startTime = System.nanoTime();

            if(Algorithm.solve(new ArrayList<>(FileHandler.Pieces.entrySet()))) {
                System.out.println("Solution Found!\n");
                Algorithm.printBoard();

                long endTime = System.nanoTime();
                double elapsedTime = (endTime - startTime) / 1e9;
                
                System.out.printf("\nTotal Combination Searched: %d\n", Algorithm.counter);
                System.out.printf("Computation Time: %.6f detik\n", elapsedTime);

                System.out.println("\nDo You Want To Save The Solution To .txt? (y/n)");
                System.out.print(">> ");
                Scanner scanner1 = new Scanner(System.in);
                String answer1 = scanner1.nextLine();

                if (answer1.equals("y")) {
                    System.out.println("\nEnter File Name (ex: file1.txt):");
                    System.out.print(">> ");
                    String fileName = scanner1.nextLine();
                    FileHandler.WriteFile(fileName);
                }

                System.out.println("\nDo You Want To Save The Solution To .png? (y/n)");
                System.out.print(">> ");
                Scanner scanner2 = new Scanner(System.in);
                String answer2 = scanner2.nextLine();

                if (answer2.equals("y")) {
                    System.out.println("\nEnter File Name (ex: image1.png):");
                    System.out.print(">> ");
                    String fileName = scanner2.nextLine();
                    FileHandler.WriteImage(fileName);
                }
            } else {
                System.out.println("No Solution Found.");
            }
        }
    }
}