import java.util.*;

class Main {

    static long[] cache = new long[51];
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        cache[1] = 1;
        cache[2] = 2;

        while (in.hasNext()) {
            int n = in.nextInt();
            System.out.println(calculate(n));
        }
    }

    private static long calculate(int n) {
        if (cache[n] != 0) {
            return cache[n];
        }

        long ans = calculate(n - 1) + calculate(n - 2);
        cache[n] = ans;
        return cache[n];
    }
}