package fit.nsu.labs.common.codec;

public interface DumpUtil {

    static void dumpPacket(byte[] bytes, String logMessage) {
        /*
               |  0  1  2  3  4  5  6  7  8  9  A  B  C  D  E  F |
        000000 | XX XX XX XX XX XX XX XX XX XX XX XX XX XX XX XX | ................
         */
        var sb = new StringBuilder("       |  0  1  2  3  4  5  6  7  8  9  A  B  C  D  E  F |\n");
        var sb2 = new StringBuilder();

        int lines = 0;
        int j = 0;
        for (byte b : bytes) {
            if (j == 0) sb.append("%06X |".formatted(lines));

            sb.append(String.format(" %02X", b));
            sb2.append(dumpedChar(b));
            j++;

            if (j == 16) {
                sb.append(" | ").append(sb2).append('\n');
                //TODO не оптимальное решение. Лучше будет сбрасывать состояние, но я забыл как это делается
                sb2 = new StringBuilder();
                j = 0;
                lines+=16;
            }
        }

        if (j < 16) {
            sb.append("   ".repeat((16 - j))).append(" | ").append(sb2).append('\n');
        }

        System.out.printf("[DEBUG] %s\n%s", logMessage, sb);
    }

    private static String dumpedChar(byte b) {
        if ((Character.isWhitespace(b) && !Character.isSpaceChar(b)) || !Character.isDefined(b)) {
            return ".";
        } else {
            return String.valueOf((char)b);
        }
    }
}
