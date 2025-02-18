import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileHandler {
    public static int N;
    public static int M;
    public static int P;
    public static HashMap<String, List<int[]>> Pieces;
    public static double err;

    public static void ReadFile(String path) {
        err = 0;
        Pieces = new HashMap<>();
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);

            // Read Dimensions
            String dimensions = scanner.nextLine();
            String[] dimension = dimensions.split(" ");

            // Check N, M, and P
            if (dimension.length == 3) {
                N = Integer.parseInt(dimension[0]);
                M = Integer.parseInt(dimension[1]);
                P = Integer.parseInt(dimension[2]);

                // Read Type
                String Type = scanner.nextLine();

                // Check Type and Read Pieces
                if (Type.equals("DEFAULT")) {
                    String prevPiece = "";
                    int row = 0;

                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        String[] line_array = line.split("");
                        String currPiece = line_array[0];

                        // Check Apakah Dalam Satu Baris, Alphabet Sama
                        if (!IsElementsEqual(line_array)){
                            System.out.println("Invalid Piece.");
                            err = -405;
                            break;
                        }

                        // Loop Tiap Baris
                        for (int col = 0; col<line_array.length; col++) {
                            if (!currPiece.equals(prevPiece)) {
                                prevPiece = currPiece;
                                row = 0; // Reset Row Jika Merupakan Piece Baru
                            }          
                            Pieces.putIfAbsent(currPiece, new ArrayList<>());
                            Pieces.get(currPiece).add(new int[]{row, col});
                        }
                        row++;
                    }

                    // Check Jumlah Piece
                    if (Pieces.size() != P) {
                        System.out.println("Invalid Number of Pieces.");
                        err = -405;
                    }
                } else {
                    System.out.println("Invalid Type.");
                    err = -405;
                }
            } else {
                System.out.println("Invalid Dimensions.");
                err = -405;
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
            err = -405;
        }
    }

    public static boolean IsElementsEqual(String[] array){
        for (int i = 1; i < array.length; i++) {
            if (!array[i].equals(array[0])) {
                return false;
            }
        }
        return true;
    }

    /*
    public static void main(String[] args) {
        FileHandler fileHandler = new FileHandler();
        File filee = new File("../test/1.txt");
        fileHandler.ReadFile(filee.getAbsolutePath());

        if (err == 0) {
            System.out.println("Done");
            System.out.println(FileHandler.N);
            System.out.println(FileHandler.M);
            System.out.println(FileHandler.P);
            // System.out.println(FileHandler.Pieces);

            for (Map.Entry<String, List<int[]>> entry : FileHandler.Pieces.entrySet()) {
                String piece = entry.getKey();
                List<int[]> coordinates = entry.getValue();
                System.out.print(piece + ": ");
                for (int[] coord : coordinates) {
                    System.out.print(Arrays.toString(coord) + " ");
                }
                System.out.println();
            }
        }
    }
    */
    
}