package wueffi.checker.helpers;

public class BoardFormatter {
    // formats the board to be printed

    public static String format(int[][] board) {
        StringBuilder line = new StringBuilder("");
        for (int[] row : board) {
            line.append("[");
            for (int slot : row) {
                if (slot == 0) line.append("_");
                else if (slot == 1) line.append("+");
                else if (slot == 2) line.append("o");
            }
            line.append("]\n");
        }
        return line.toString();
    }
}
