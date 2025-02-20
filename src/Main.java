import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        File filee = new File("../test/2.txt");
        FileHandler.ReadFile(filee.getAbsolutePath());

        if (FileHandler.err == 0) {
            System.out.println("Berhasil membaca file...");
            
            Algorithm.initializeBoard();

            long startTime = System.nanoTime();

            System.out.println("Sedang mencari solusi...");
            if(Algorithm.solve(new ArrayList<>(FileHandler.Pieces.entrySet()))) {
                System.out.println("Solusi ditemukan:");
                Algorithm.printBoard();
            } else {
                System.out.println("Tidak ada solusi.");
            }

            long endTime = System.nanoTime();
            double elapsedTime = (endTime - startTime) / 1e9;
            
            System.out.printf("Jumlah kemungkinan yang ditinjau: %d\n", Algorithm.counter);
            System.out.printf("Waktu komputasi: %.6f detik\n", elapsedTime);

            System.out.println("Apakah anda ingin menyimpan solusi? (ya/tidak)");
            System.out.print(">> ");
            Scanner scanner = new Scanner(System.in);
            String answer = scanner.nextLine();
            if (answer.equals("ya")) {
                System.out.println("Masukkan nama file (contoh: namafile.txt):");
                System.out.print(">> ");
                String fileName = scanner.nextLine();
                FileHandler.WriteFile(fileName);
            }
        }
    }
}