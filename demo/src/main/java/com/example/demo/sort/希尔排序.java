package com.example.demo.sort;

public class 希尔排序 {
    public static void main(String[] args) {
        int[] a = {22, 1, 34, 5, 6, 23, 66, 56, 354};
        for (int i : shellSort(a)
                ) {
            System.out.println(i);
        }

    }

    public static int[] shellSort(int[] a) {
        int len = a.length;
        int tem, gap = len / 2;
        while (gap > 0) {
            for (int i = gap; i < len; i++) {
                tem = a[i];
                int x = i - gap;
                while (x >= 0 && tem < a[x]) {
                    a[i] = a[x];
                    x-= gap;
                }
                a[x + gap] = tem;
            }
            gap /= 2;
        }
        return a;
    }
}
