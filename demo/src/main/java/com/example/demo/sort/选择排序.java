package com.example.demo.sort;

public class 选择排序 {
    public static void main(String[] args) {
        int a[] = {22, 1, 34, 5, 6, 23, 626, 56, 354};
        for (int aa : selectSort(a)

                ) {
            System.out.println(aa);
        }
    }

    public static int[] selectSort(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int index = i;
            for (int j = i; j >=0; j--) {
                if (a[i] < a[j]) {
                    index = j;
                }
            }
            int temp = a[index];
            a[index] = a[i];
            a[i] = temp;
            int x = 0;
        }
        return a;
    }
}
