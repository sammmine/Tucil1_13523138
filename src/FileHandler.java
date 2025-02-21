import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;

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
                    String prevPiece = "zzz";
                    int row = 0;
                    int col = 0;

                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        row = 0;
                        for (int i=0; i<line.length(); i++) {
                            if (line.charAt(i) >= 'A' && line.charAt(i) <= 'Z') {
                                String currPiece = String.valueOf(line.charAt(i));
                                if (!currPiece.equals(prevPiece)) {
                                    prevPiece = currPiece;
                                    col = 0;
                                }          
                                Pieces.putIfAbsent(currPiece, new ArrayList<>());
                                Pieces.get(currPiece).add(new int[]{col, row});
                            }
                            row++;
                        }
                        col++;
                    }

                    // Check Jumlah Piece
                    if (Pieces.size() != P) {
                        System.out.println("\nInvalid Number of Pieces.");
                        err = -405;
                    }
                } else {
                    System.out.println("\nInvalid Type.");
                    err = -405;
                }
            } else {
                System.out.println("\nInvalid Dimensions.");
                err = -405;
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("\nFile not found.");
            err = -405;
        }
    }

    public static void WriteFile(String fileName) {
        try {
            int rows = Algorithm.Board.length;
            int cols = Algorithm.Board[0].length;
            FileWriter writer = new FileWriter("../test/" + fileName);

            for (int c = 0; c < cols; c++) {
                for (int r = 0; r < rows; r++) {
                    writer.write(Algorithm.Board[r][c]);
                }
                writer.write("\n");
            }
            writer.close();
            
            System.out.println("\nSuccessfully Wrote To File.");
        } catch (IOException e) {
            System.out.println("\nFailed To Write To File.");
        }
    }

    public static Map<Character, Color> colorImage = new HashMap<>();

    public static void initColorMap() {
        colorImage.put('A', new Color(255, 0, 0));     // Merah
        colorImage.put('B', new Color(0, 255, 0));     // Hijau
        colorImage.put('C', new Color(0, 0, 255));     // Biru
        colorImage.put('D', new Color(255, 255, 0));   // Kuning
        colorImage.put('E', new Color(255, 0, 255));   // Magenta
        colorImage.put('F', new Color(0, 255, 255));   // Cyan
        colorImage.put('G', new Color(255, 165, 0));   // Oranye
        colorImage.put('H', new Color(128, 0, 128));   // Ungu
        colorImage.put('I', new Color(255, 105, 180)); // Pink
        colorImage.put('J', new Color(50, 205, 50));   // Lime
        colorImage.put('K', new Color(64, 224, 208));  // Turquoise
        colorImage.put('L', new Color(255, 215, 0));   // Emas
        colorImage.put('M', new Color(139, 69, 19));   // Coklat
        colorImage.put('N', new Color(107, 142, 35));  // Olive
        colorImage.put('O', new Color(230, 230, 250)); // Lavender
        colorImage.put('P', new Color(0, 128, 128));   // Teal
        colorImage.put('Q', new Color(211, 211, 211)); // Abu-abu terang
        colorImage.put('R', new Color(169, 169, 169)); // Abu-abu gelap
        colorImage.put('S', new Color(128, 0, 0));     // Merah marun
        colorImage.put('T', new Color(0, 0, 139));     // Biru laut
        colorImage.put('U', new Color(0, 100, 0));     // Hijau gelap
        colorImage.put('V', new Color(255, 20, 147));  // Rose
        colorImage.put('W', new Color(192, 192, 192)); // Perak
        colorImage.put('X', new Color(30, 144, 255));  // Neon biru
        colorImage.put('Y', new Color(57, 255, 20));   // Neon hijau
        colorImage.put('Z', new Color(255, 20, 147));  // Neon pink
    }

    public static void WriteImage(String fileName) {
        initColorMap();

        int piece_size = 60;
        int margin = 0;

        int rows = Algorithm.Board.length;
        int cols = Algorithm.Board[0].length;
        int width = rows * (piece_size + margin);
        int height = cols * (piece_size + margin);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width, height);

        
        // for (int r = 0; r < rows; r++) {
        //     for (int c = 0; c < cols; c++) {
        //         char piece = Algorithm.Board[r][c];
        //         g2d.setColor(colorImage.getOrDefault(piece, Color.WHITE));
        //         int x = c * (piece_size + margin);
        //         int y = r * (piece_size + margin);
        //         g2d.fillOval(x, y, piece_size, piece_size);
        //     }
        // }

        // Print (kebalik)
        for (int c = 0; c < cols; c++) {
            for (int r = 0; r < rows; r++) {
                char piece = Algorithm.Board[r][c];
                g2d.setColor(colorImage.getOrDefault(piece, Color.WHITE));
                int x = c * (piece_size + margin);
                int y = r * (piece_size + margin);
                g2d.fillOval(y, x, piece_size, piece_size);
            }
        }

        g2d.dispose();
        try {
            File output = new File("../test/" + fileName);
            ImageIO.write(image, "png", output);
            System.out.println("\nSuccessfully Wrote To Image.");
        } catch (IOException e) {
            System.out.println("\nFailed To Write To Image.");
        }
    }

    // public static boolean IsElementsEqual(String[] array){
    //     for (int i = 1; i < array.length; i++) {
    //         if (!array[i].equals(array[0])) {
    //             return false;
    //         }
    //     }
    //     return true;
    // }

    /*
    public static void main(String[] args) {
        FileHandler fileHandler = new FileHandler();
        File filee = new File("../test/2.txt");
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