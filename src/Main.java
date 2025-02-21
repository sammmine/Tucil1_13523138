import java.io.File;
import java.util.*;

// 4.txt belum berhasil

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
        // File filee = new File("../test/5.txt");
        System.out.println("\nEnter File Name (ex: ../test/file.txt):");
        System.out.print(">> ");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();
        File file = new File(answer);
        FileHandler.ReadFile(file.getAbsolutePath());

        if (FileHandler.err == 0) {
            // System.out.println("Berhasil membaca file...");
            // System.out.println("\nWorking On It, Please Wait...\n");
            System.out.println("\n...\n");
            
            Algorithm.initializeBoard();

            long startTime = System.nanoTime();

            // System.out.println("Sedang mencari solusi...");
            if(Algorithm.solve(new ArrayList<>(FileHandler.Pieces.entrySet()))) {
                System.out.println("Solution Found!\n");
                Algorithm.printBoard();

                long endTime = System.nanoTime();
                double elapsedTime = (endTime - startTime) / 1e9;
                
                System.out.printf("\nTotal Combination Searched: %d\n", Algorithm.counter);
                System.out.printf("Computation Time: %.6f detik\n", elapsedTime);

                System.out.println("\nDo You Want To Save The Solution To .txt? (y/n)");
                System.out.print(">> ");
                scanner = new Scanner(System.in);
                answer = scanner.nextLine();

                if (answer.equals("y")) {
                    System.out.println("\nEnter File Name (ex: file1.txt):");
                    System.out.print(">> ");
                    String fileName = scanner.nextLine();
                    FileHandler.WriteFile(fileName);
                }

                System.out.println("\nDo You Want To Save The Solution To .png? (y/n)");
                System.out.print(">> ");
                scanner = new Scanner(System.in);
                answer = scanner.nextLine();

                if (answer.equals("y")) {
                    System.out.println("\nEnter File Name (ex: image1.png):");
                    System.out.print(">> ");
                    String fileName = scanner.nextLine();
                    FileHandler.WriteImage(fileName);
                }
            } else {
                System.out.println("No Solution Found.");
            }
        }
    }
}