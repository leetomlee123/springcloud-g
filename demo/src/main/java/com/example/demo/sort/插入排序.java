package com.example.demo.sort;


public class 插入排序 {
    public static void main(String[] args) {
        int[] a = {22, 1, 34, 5, 6, 23, 66, 56, 354};
        for (int i : insertSort(a)
                ) {
            System.out.println(i);
        }

    }

    public static int[] insertSort(int[] a) {
        int index;
        for (int i = 0; i < a.length - 1; i++) {
            index = a[i + 1];
            while (i >= 0 && a[i] > index) {
                a[i + 1] = a[i];
                i--;
            }
            a[i + 1] = index;
        }
        return a;
    }

}
