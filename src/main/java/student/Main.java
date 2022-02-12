package student;

import student.adventure.Adventure;
import com.google.gson.*;
import student.adventure.Game;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/*
* Takes in a JSON file and runs Adventure game
* @author Alan Santiago Andrade
* */

public class Main {
    public static void main(String[] args) throws Exception {
        // Initialize I/O devices and GSON, ask for File path
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter File Path\n> ");
        String inputFile = sc.nextLine();
        Gson gson = new Gson();

        // If the input file is valid we use that, else the default is hogwarts.json
        String fileName = "src/main/resources/hogwarts.json";
        File file = new File(inputFile);
        if (file.exists()) {
            fileName = inputFile;
        } else {
            System.out.println("File not found, running default.");
        }

        // Run the game
        try {
            Reader reader = Files.newBufferedReader(Paths.get(fileName));
            // Create Adventure Java object from JSON
            Adventure adventure = gson.fromJson(reader, Adventure.class);
            Game game = new Game();

            // Execute game
            game.Begin(adventure);

        } catch (Exception exception) {
            System.out.println("Could not load file");
            exception.printStackTrace();
        }
    }
}
