import java.util.*;

public class Algorithm {
    public static char[][] Board = new char[FileHandler.M][FileHandler.N];
    public static long counter = 0; // Backtrack count
    public static HashMap<Character, String> colorMap = new HashMap<>();
    public static String ANSI_RESET = "\u001B[0m";

    public static void colorANSI() {
        colorMap.put('A', "\033[38;5;196m"); // Merah
        colorMap.put('B', "\033[38;5;46m"); // Hijau
        colorMap.put('C', "\033[38;5;21m"); // Biru
        colorMap.put('D', "\033[38;5;226m"); // Kuning
        colorMap.put('E', "\033[38;5;201m"); // Magenta
        colorMap.put('F', "\033[38;5;51m"); // Cyan
        colorMap.put('G', "\033[38;5;208m"); // Oranye
        colorMap.put('H', "\033[38;5;93m"); // Ungu
        colorMap.put('I', "\033[38;5;205m"); // Pink
        colorMap.put('J', "\033[38;5;118m"); // Lime
        colorMap.put('K', "\033[38;5;80m"); // Turquoise
        colorMap.put('L', "\033[38;5;214m"); // Emas
        colorMap.put('M', "\033[38;5;130m"); // Coklat
        colorMap.put('N', "\033[38;5;100m"); // Olive
        colorMap.put('O', "\033[38;5;147m"); // Lavender
        colorMap.put('P', "\033[38;5;30m"); // Teal
        colorMap.put('Q', "\033[38;5;250m"); // Abu-abu terang
        colorMap.put('R', "\033[38;5;240m"); // Abu-abu gelap
        colorMap.put('S', "\033[38;5;88m"); // Merah marun
        colorMap.put('T', "\033[38;5;25m"); // Biru laut
        colorMap.put('U', "\033[38;5;22m"); // Hijau gelap
        colorMap.put('V', "\033[38;5;169m"); // Rose
        colorMap.put('W', "\033[38;5;7m"); // Perak
        colorMap.put('X', "\033[38;5;45m"); // Neon biru
        colorMap.put('Y', "\033[38;5;82m"); // Neon hijau
        colorMap.put('Z', "\033[38;5;198m"); // Neon pink
    }

    public static void initializeBoard() {
        for (int i = 0; i < FileHandler.M; i++) {
            Arrays.fill(Board[i], '.');
        }
    }

    // Rotate 90°
    public static List<int[]> rotate(List<int[]> piece) {
        List<int[]> rotated = new ArrayList<>();
        for (int[] p : piece) {
            rotated.add(new int[]{p[1], -p[0]});
        }
        return rotated;
    }

    // Refleksi terhadap sumbu y
    public static List<int[]> reflect(List<int[]> piece) {
        List<int[]> reflected = new ArrayList<>();
        for (int[] p : piece) {
            reflected.add(new int[]{-p[0], p[1]});
        }
        return reflected;
    }

    // Generate semua kombinasi piece di HashSet (agar tidak ada duplikat)
    // Return dalam bentuk List
    public static List<List<int[]>> generateVariants(List<int[]> piece) {
        Set<List<int[]>> variants = new HashSet<>();
        List<int[]> currentPiece = piece;
        for (int i = 0; i < 4; i++) { // 4 kali rotasi (0°, 90°, 180°, 270°)
            currentPiece = rotate(currentPiece);
            variants.add(new ArrayList<>(currentPiece));
            variants.add(reflect(currentPiece));
        }
        return new ArrayList<>(variants);
    }

    // Check piece di board
    public static boolean placeable(List<int[]> piece, int r, int c) {
        for (int[] p : piece) {
            int nr = r + p[0], nc = c + p[1];
            if (nr < 0 || nr >= FileHandler.M || nc < 0 || nc >= FileHandler.N || Board[nr][nc] != '.') {
                // Syarat: tidak boleh keluar dimensi board, dan tidak boleh menimpa piece lain
                return false;
            }
        }
        return true;
    }

    // Place per koordinat
    public static void placePiece(List<int[]> piece, int r, int c, char label) {
        for (int[] p : piece) {
            Board[r + p[0]][c + p[1]] = label;
        }
    }

    // Remove per koordinat
    public static void removePiece(List<int[]> piece, int r, int c) {
        for (int[] p : piece) {
            Board[r + p[0]][c + p[1]] = '.';
        }
    }

    public static boolean solve(List<Map.Entry<String, List<int[]>>> remainingPieces) {
        if (remainingPieces.isEmpty()) return true; // Basis
        
        Map.Entry<String, List<int[]>> entry = remainingPieces.get(0);
        String label = entry.getKey();
        List<List<int[]>> pieceVariants = generateVariants(entry.getValue());
        
        for (List<int[]> piece : pieceVariants) {
            for (int r = 0; r < FileHandler.M; r++) {
                for (int c = 0; c < FileHandler.N; c++) {
                    if (placeable(piece, r, c)) {
                        placePiece(piece, r, c, label.charAt(0));
                        
                        // Rekursi
                        List<Map.Entry<String, List<int[]>>> nextPieces = new ArrayList<>(remainingPieces.subList(1, remainingPieces.size()));
                        if (solve(nextPieces)) {
                            return true;
                        }
                        
                        removePiece(piece, r, c);
                    }
                }
            }
        }
        counter++;

        return false;
    }

    // Print board (kebalik)
    public static void printBoard() {
        int rows = Board.length;
        int cols = Board[0].length;

        colorANSI();
    
        for (int c = 0; c < cols; c++) {
            for (int r = 0; r < rows; r++) {
                System.out.print(colorMap.get(Board[r][c]) + Board[r][c] + ANSI_RESET);
            }
            System.out.println();
        }
    } 
}

/*
public static void main(String[] args) {
        initializeBoard();
        initializePieces();
        
        long startTime = System.nanoTime();
        
        System.out.println("Mencari solusi...");
        if (solve(new ArrayList<>(FileHandler.Pieces.entrySet()))) {
            System.out.println("Solusi ditemukan:");
            printBoard();
        } else {
            System.out.println("Tidak ada solusi.");
        }
        
        long endTime = System.nanoTime();
        double elapsedTime = (endTime - startTime) / 1e9;
        System.out.printf("Jumlah kemungkinan yang ditinjau: %d\n", counter);
        System.out.printf("Waktu komputasi: %.6f detik\n", elapsedTime);
    }
*/
