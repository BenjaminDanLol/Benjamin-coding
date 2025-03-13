package com.example;

import java.io.InputStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);

        InputStream inputStream = Pokesorter.class.getClassLoader().getResourceAsStream("resources/PokeTiering.json");
        Player p1 = new Player(myScanner);
        for (int i = 0; i < 10; i++) {
            p1.addPokemon(i, myScanner);
        }
        p1.displayPokemonTeam();
        p1.displayASpecificPokemon(6, myScanner);
        myScanner.close();
    }
    
}
