package com.yrw_.retry.sort;

public class qucikSort {

    public static void main(String[] args) {
        int[] num = {1, 3, 0, 2, 44};
        qucikSort(num, 0, 4);
        System.out.println(num);
    }

    public static int[] qucikSort(int[] num, int l, int r) {
        if (l >= r) {
            return num;
        }
        int mid = partition(num,l,r);
        qucikSort(num,l,mid - 1);
        qucikSort(num,mid + 1,r);
        return num;
    }

    private static int partition(int[] num, int l, int r) {
        int pivot = num[l];
        int left = l;
        int right = r;
        int index = l;
        while (left < right) {
            while (left < right && num[right] > pivot) {
                right--;
            }
            if (num[right] < pivot) {
                num[index] = num[right];
                index = right;
                right--;
            }

            while (left < right && num[left] < pivot) {
                left++;
            }

            if (num[left] > pivot) {
                num[index] = num[left];
                index = left;
                left++;
            }
        }
        num[index] = pivot;
        return index;
    }
}
