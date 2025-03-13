package com.example;

import java.io.InputStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        InputStream inputStream = Pokesorter.class.getClassLoader().getResourceAsStream("resources/PokeTiering.json");

    }
    
}
