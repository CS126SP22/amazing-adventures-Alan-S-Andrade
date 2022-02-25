package student;

import org.glassfish.grizzly.http.server.HttpServer;
import student.adventure.*;
import student.server.AdventureResource;

import student.server.AdventureServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        GameLoader gameLoader = new GameLoader();
        Game newGame = gameLoader.loadGameFromJsonFile("src/main/resources/hogwarts.json");
        newGame.start();
//        newGame.start();
    }
}