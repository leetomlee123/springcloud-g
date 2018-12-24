package com.example.demo.sort;

public class 冒泡排序 {
    public static void main(String[] args) {
        int a[] = {22, 1, 34, 5, 6, 23, 66, 56, 354};
        for (int i : bubbleSort(a)
                ) {
            System.out.println(i);
        }
    }

    public static int[] bubbleSort(int[] array) {
        if (array.length == 0) {
            return array;
        }
        int kk = 0;
        boolean f = true;
        for (int i = 0; i < array.length && f; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j + 1] < array[j]) {
                    int temp = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = temp;
                    kk++;
                }
            }
            if (kk == 0) {
                f = false;
            }

        }
        return array;
    }
}
