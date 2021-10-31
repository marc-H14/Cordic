import java.util.Arrays;

public class Cordic {
    private final static int iterations = 100;

    private static double x;
    private static double y;
    private static double z;
    private static double a;
    private static boolean debug = false;


    private static void iterate(int i, int d) {
        double tempX = x;
        double pow = Math.pow(2, -i);
        x = x - d * pow * y;
        y = y + pow * d * tempX;
        z = z - d * Math.atan(pow);
        a = a * Math.sqrt(1 + Math.pow(2, -2 * (i + 1)));
        //a = a * Math.sqrt(1 + Math.pow(2, -2 * (i + 1)));
        /*x = x - d * Math.pow(2, -i) * y;
        y = y + Math.pow(2, -i) * d * tempX;
        z = z - d * Math.atan(Math.pow(2, -i));*/
    }

    private static double[] startIteration(double alpha) {
        x = 1.0;
        y = 0;
        z = alpha;
        int d;
        if (debug) System.out.println("  I ||  X                  ||  Y                  ||  Z                  ||" +
                "  A                  ||  D");
        for (int i = 0; i < iterations; i++) {
            d = (z < 0) ? -1 : 1;
            if (z == 0) d = 0;
            if (debug) System.out.println(String.format("%3d || % .16f || % .16f || % .16f || % .16f || % 2d", i, x, y, z, a, d));
            iterate(i, d);
            if (z == 0) break;
        }
        return new double[]{x, y, a};
    }

    public static void main(String[] args) {
        debug = false;
        double alpha = 3.14;
        a = Math.sqrt(2);
        double[] result  = startIteration(alpha);
        System.out.println("cos(" + alpha + ") = " + (result[0] * (1 / result[2])));
        System.out.println("sin(" + alpha + ") = " + (result[1] * (1 / result[2])));
    }
}
