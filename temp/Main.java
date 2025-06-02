import model.PaymentBO;

import java.math.BigDecimal;
import java.util.*;

class Main {

    static long[] cache = new long[51];
    public static void main(String[] args) {



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