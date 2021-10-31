
public class Cordic {
    private final static boolean DEBUG = true;
    private final static int MAX_ITERATIONS = 79; //max precision reached after 79 iteration regardless of input

    private static double x;
    private static double y;
    private static double z;
    private static double a;


    private static void iterate(int i, int d) {
        double tempX = x;
        double pow = Math.pow(2, -i);
        x -= d * pow * y;
        y += pow * d * tempX;
        z -= d * Math.atan(pow);
        a *= Math.sqrt(1 + Math.pow(2.0, -2 * (double)(i + 1)));
    }

    private static double[] startIteration(double alpha) {
        x = 1.0;
        y = 0;
        z = alpha;
        int d;
        if (DEBUG) System.out.println("  I ||  X                  ||  Y                  ||  Z                  ||" +
                "  A                  ||  D");
        for (int i = 0; i <= MAX_ITERATIONS; i++) {
            d = (z < 0) ? -1 : 1;
            if (z == 0) d = 0;
            if (DEBUG) System.out.printf("%3d || % .16f || % .16f || % .16f || % .16f || % 2d%n", i, x, y, z, a, d);
            iterate(i, d);
            if (z == 0) break;
        }
        return new double[]{x, y, a};
    }

    public static void main(String[] args) {
        double alpha = 0.4;
        a = Math.sqrt(2);
        double[] result  = startIteration(alpha);
        System.out.println("cos(" + alpha + ") = " + (result[0] * (1 / result[2])));
        System.out.println("sin(" + alpha + ") = " + (result[1] * (1 / result[2])));
    }
}
