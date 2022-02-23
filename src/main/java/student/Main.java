package student;

import student.adventure.*;

public class Main {
    public static void main(String[] args) {
        GameLoader gameLoader = new GameLoader();
        Game newGame = gameLoader.loadGameFromJsonFile("src/main/resources/hogwarts.json");
        newGame.start();
    }
}