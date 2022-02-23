package student.adventure;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Utility class for loading a game from Json and validating it. */
public final class GameLoader {
    /**
     * This static function returns a Game Object from a file.
     *
     * @param filePath The file path as a String
     * @return A Game object
     */
    public static Game loadGameFromJsonFile(String filePath) {
        if (filePath == null) {
            throw new IllegalArgumentException();
        }
        Gson gson = new Gson();
        File file = new File(filePath);

        JsonReader reader;
        try {
            reader = new JsonReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.out.println("Sorry, file not found");
            throw new IllegalArgumentException();
        }

        Game game = gson.fromJson(reader, Game.class);

        validateJsonSchema(game);

        return game;
    }

    /**
     * Checks that the game created matches the schema
     * @param game instance of the game to check
     */
    private static void validateJsonSchema(Game game) {
        try {
            checkRooms(game);
        } catch (Exception e) {
            System.out.println("Game doesn't match required schema");
            throw new IllegalArgumentException();
        }
    }

    /**
     * Checks that there are at least two rooms and they contain the starting and ending room
     * Also checks all the other fields in a room
     * @param game object
     */
    private static void checkRooms(Game game) {
        boolean noStartingRoom = true;
        boolean noEndingRoom = true;
        String startingRoom = game.getStartingRoom();
        String endingRoom = game.getEndingRoom();
        List<Room> rooms = game.getRooms();
        Map<String, Integer> roomCounts = new HashMap<>();

        for (Room room : rooms) {
            //Will also throw exception if any rooms are null.
            if (room.getName().equals(startingRoom)) {
                noStartingRoom = false;
            }
            //Will also throw exception if any rooms are null.
            if (room.getName().equals(endingRoom)) {
                noEndingRoom = false;
            }

            //Checks if there is more than one room with the same name.
            roomCounts.put(room.getName(), roomCounts.getOrDefault(room.getName(), 0) + 1);
            if (roomCounts.get(room.getName()) > 1) {
                System.out.println("there are duplicate rooms");
                throw new IllegalArgumentException();
            }

            checkDescription(room);
        }
        if (noEndingRoom || noStartingRoom) {
            System.out.println("either starting or ending room not in room list");
            throw new IllegalArgumentException();
        }
    }

    /**
     * Checks that the description isn't null
     * @param room  object
     */
    private static void checkDescription(Room room) {
        String description = room.getDescription();
        if (description == null) {
            System.out.println("null room description somewhere");
            throw new IllegalArgumentException();
        }
    }
}