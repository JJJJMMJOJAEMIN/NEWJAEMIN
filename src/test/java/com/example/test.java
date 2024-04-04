package com.example;

public class test {

    public static void main(String[] args) {
        Object[][] arrayOfArrays = {
                {0, 0, "루트"},
                {1, 0, "의류"},
                {2, 0, "가전"},
                {3, 1, "남성"},
                {4, 1, "여성"},
                {5, 3, "바지"},
                {6, 2, "티비"}
        };

        for(int i=1; i<arrayOfArrays.length; i++)
        {
            printCategory(arrayOfArrays, i);
            System.out.println();
        }
    }

    private static void printCategory(Object[][] arrayOfArrays, int i) {


    }
}
