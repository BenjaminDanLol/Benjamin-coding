package com.example;

import java.util.Arrays;

public class arrayTesting{
    public static void main(String[] args) {
        int[] array1 = {2, 5, 6, 7, 1, 34, 7};
        int[] array2 = {500, 5000, 50000, -500};

        int[] array1andto2elems = new int[array1.length + array2.length];
        for (int i = 0; i < array1andto2elems.length; i++) {
            if (i < array1.length) {
                array1andto2elems[i] = array1[i];
            }
            else {
            array1andto2elems[i] = array2[i];
            }
        }
        System.out.println("1: " + Arrays.toString(array1));
        System.out.println("2: " + Arrays.toString(array2));
        System.out.println("1 and 2 together: " + Arrays.toString(array1andto2elems));
    }
}