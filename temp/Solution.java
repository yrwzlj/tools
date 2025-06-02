import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

class Solution {

    public static void main(String[] args) {
        System.out.println("main:"+test());
    }

    private static String test() {
        String str = "start";
        try {
            System.out.println("try:" + str);
            System.out.println("try is running......");
            return str = str + "->try return";
        } catch (Exception e) {
            System.out.println("catch:" + str);
            System.out.println("catch is running......");
            return str = str + "->catch return";
        }finally {
            System.out.println("finally:" + str);
            str = "finally:" + str;
            System.out.println("finally is running......");
        }
    }



    public String tryCatch01() {
        try {
            List<String> list = new ArrayList<>();
            //System.out.println(list.get(1));
            Future<Integer> future = new CompletableFuture<>();
            future.get(1, TimeUnit.SECONDS);
            return "try inner";
        } catch (TimeoutException e) {
            //return "catch";
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("finally");
            //return "ff";
        }

        return "out try catch";
    }

    public long countOfSubstrings(String word, int k) {
        char[] s = word.toCharArray();
        return f(s, k) - f(s, k + 1);
    }

    private long f(char[] word, int k) {
        long ans = 0;
        // 这里用哈希表实现，替换成数组会更快
        HashMap<Character, Integer> cnt1 = new HashMap<>(); // 每种元音的个数
        int cnt2 = 0; // 辅音个数
        int left = 0;
        for (char b : word) {
            if ("aeiou".indexOf(b) >= 0) {
                cnt1.merge(b, 1, Integer::sum); // ++cnt1[b]
            } else {
                cnt2++;
            }
            while (cnt1.size() == 5 && cnt2 >= k) {
                char out = word[left];
                if ("aeiou".indexOf(out) >= 0) {
                    if (cnt1.merge(out, -1, Integer::sum) == 0) { // --cnt1[out] == 0
                        cnt1.remove(out);
                    }
                } else {
                    cnt2--;
                }
                left++;
            }
            ans += left;
        }
        return ans;
    }
}