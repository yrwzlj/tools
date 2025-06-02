package com.yrw_.retry;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: rw.yang
 * @DateTime: 2025/5/18
 **/
class Solution {

    Integer[] chars = new Integer[27];

    public String smallestEquivalentString(String s1, String s2, String baseStr) {
        init(chars);
        for (int i = 0; i < s1.length(); i++) {
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(i);
            int index1 = c1 - 'a';
            int index2 = c2 - 'a';
            int small = smallerChar(c1, c2);
            combine(small - 'a', index1, index2);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < baseStr.length(); i++) {
            char c = baseStr.charAt(i);
            int index = c - 'a';
            int root = findRoot(index);
            sb.append((char) (root + 'a'));
        }

        return sb.toString();
    }

    private void combine(int i, int index1, int index2) {
        int root1 = findRoot(index1);
        int root2 = findRoot(index2);
        if (root1 < root2) {
            chars[root2] = root1;
        } else if (root1 > root2) {
            chars[root1] = root2;
        }
    }

    private int findRoot(int index1) {
        if (chars[index1] != index1) {
            return findRoot(chars[index1]);
        }

        return index1;
    }

    private void init(Integer[] chars) {
        for (int i = 0; i < chars.length; i++) {
            chars[i] = i;
        }
    }


    private Character smallerChar(Character a, Character b) {
        return a < b ? a : b;
    }
}