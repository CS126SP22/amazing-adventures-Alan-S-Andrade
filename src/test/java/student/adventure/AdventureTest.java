package student.adventure;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import java.io.File;

public class AdventureTest {
    Adventure objAdventure;
    Game objGame;

    @Before
    public void setUp() {
        File file = new File("src/main/resources/hogwarts.json");
        try {
            objAdventure = new ObjectMapper().readValue(file, Adventure.class);
        } catch (Exception exception) {

        }
        objGame = new Game();

    }

    @Test
    public void isGoValidRandomLetters() {
        String isDirectionValid = objGame.identifyAction("kjla");
        assertEquals("Bad input", isDirectionValid);
    }
    @Test
    public void isGoValid() {
        Boolean isDirectionValid = objGame.identifyAction("goEast");
        assertEquals(false, isDirectionValid);
    }
    @Test
    public void isGoValid1() {
        Boolean isDirectionValid = objGame.identifyAction("gO East");
        assertEquals(true, isDirectionValid);
    }
    @Test
    public void RoomDescriptionCorrect() {
        String roomName = "TrophyRoom";
        String roomDescription =  objAdventure.getRoom(roomName).getDescription();

        assertEquals("You are in the Viaduct, you hear the water running through.\nYou can see the Quidditch Field south from here and the Great Hall to the east.", roomDescription);
    }
    @Test
    public void isValidDirectionFromViaduct() {
        String roomName = "GreatHall";
        String direction = "eAst";
        String nextRoom = objAdventure.getRoom(roomName).getDirectionByName(direction).getRoom();
        String nextRoomDescription = objAdventure.getRoom(nextRoom).getDescription();

        assertEquals("You are in the Viaduct, you hear the water running through.\nYou can see the Quidditch Field south from here and the Great Hall to the east.."
                ,nextRoomDescription);
    }
    @Test
    public void isValidDirectionFromOwlery() {
        String roomName = "TrophyRoom";
        String direction = "UP";
        String nextRoom = objAdventure.getRoom(roomName).getDirectionByName(direction).getRoom();
        String nextRoomDescription = objAdventure.getRoom(nextRoom).getDescription();

        assertEquals("You are in the Owlery. You never now when an Owl might be useful!\nYou can see the Quidditch Field to the east."
                , nextRoomDescription);
    }
    @Test
    public void isValidDirectionFromOwleryNorth() {
        String roomName = "Room of Requirement";
        String direction = "noRThEast";
        String nextRoom = objAdventure.getRoom(roomName).getDirectionByName(direction).getRoom();
        String nextRoomDescription = objAdventure.getRoom(nextRoom).getDescription();

        assertEquals("You are in the Owlery. You never now when an Owl might be useful!\nYou can see the Quidditch Field to the east"
                , nextRoomDescription);
    }
    @Test
    public void testingRandomInput() {
        String direction = "kdjak";
        assertEquals("I don't understand 'kdjak'", objGame.identifyAction(direction));
    }
}