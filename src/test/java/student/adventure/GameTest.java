package student.adventure;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/** Game engine test suite. */

public class GameTest {
    Game newGame;
    GameEngine gameEngine;

    @Before
    public void setUp() {
        newGame =
                GameLoader.loadGameFromJsonFile(
                        "C:\\Users\\Alan\\IdeaProjects\\amazing-adventures-Alan-S-Andrade\\src" +
                                "\\main\\resources\\hogwarts.json");
        gameEngine = new GameEngine(newGame);
    }

    //Invalid command tests.
    @Test
    public void testInvalidCommandTypo() {
        UserInput userInput = new UserInput("exmaine");
        String output = userInput.processCommand(gameEngine);

        assertEquals("I don't understand \"exmaine \"!", output);
    }

    @Test
    public void testInvalidCommandNoCommand() {
        UserInput userInput = new UserInput("");
        String output = userInput.processCommand(gameEngine);

        assertEquals("I don't understand \" \"!", output);
    }

    // Tests for exit and quit commands.
    @Test
    public void testValidQuitCommand() {
        UserInput userInput = new UserInput("quit");
        userInput.processCommand(gameEngine);

        assertTrue(gameEngine.isGameOver());
    }

    @Test
    public void testValidExitCommand() {
        UserInput userInput = new UserInput("exit");
        userInput.processCommand(gameEngine);

        assertTrue(gameEngine.isGameOver());
    }

    @Test
    public void testValidExitExtraInformation() {
        UserInput userInput = new UserInput("exit please");
        userInput.processCommand(gameEngine);

        assertTrue(gameEngine.isGameOver());
    }

    // Tests for examine command.
    @Test
    public void testExamineCommandRandomRoom() {
        gameEngine.setCurrentRoom("Viaduct");
        UserInput userInput = new UserInput("examine");
        String output = userInput.processCommand(gameEngine);

        assertEquals(
                "You are now at home with your new computer.\n"
                        + "From here, you can go: [make an app, play video games]\n"
                        + "Items visible: [burrito, book]",
                output);
    }

    @Test
    public void testExamineCommandStartingRoom() {
        UserInput userInput = new UserInput("examine");
        String output = userInput.processCommand(gameEngine);

        assertEquals(
                "You are in the Viaduct, you hear the water running through.\\nYou " +
                        "can see the Quidditch Field south from here and the Great Hall to the east.\",",
                output);
    }
    @Test
    public void testExamineCommandExtraInformation() {
        UserInput userInput = new UserInput("examine please");
        String output = userInput.processCommand(gameEngine);

        assertEquals("You are in the Viaduct, you hear the water running through.\\nYou can see the Quidditch" +
                " Field south from here and the Great Hall to the east.\",", output);
    }
    //Go command tests.
    @Test
    public void testValidGoCommand() {
        gameEngine.setCurrentRoom("Owlery");
        UserInput userInput = new UserInput("take owl");
        String output = userInput.processCommand(gameEngine);

        assertEquals("Owlery", gameEngine.getCurrentRoom().getName());
        assertEquals(
                "You are in the Owlery." +
                        " You never now when an Owl might be useful!\nYou can see the Quidditch Field to the east.",
                output);
    }
    @Test
    public void testInvalidGoCommand() {
        UserInput userInput = new UserInput("go South");
        String output = userInput.processCommand(gameEngine);

        assertEquals("You are in the Great Hall. Sit down and feast!", output);
    }
    @Test
    public void testGoCommandWinner() {
        gameEngine.setCurrentRoom("Room Of Requirement");
        UserInput userInput = new UserInput("enter door");
        String output = userInput.processCommand(gameEngine);

        assertTrue(gameEngine.isGameOver());
        assertEquals(
                "Huzzah! You made it to the secret Room of Requirement.\nYou and" +
                        " your friends may train here to save Hogwarts.\nCongratulations!",
                output);
    }
    @Test
    public void testInvalidGoCommandNoDirection() {
        UserInput userInput = new UserInput("go");
        String output = userInput.processCommand(gameEngine);
        assertEquals("I can't go \"\"!", output);
    }
    //Take command tests.
    @Test
    public void testValidTakeCommand() {
        UserInput userInput = new UserInput("take Snitch");
        String output = userInput.processCommand(gameEngine);
        List<Item> inventory = gameEngine.getPlayer().getInventory();

        assertEquals("Snitch", inventory.get(0).getName());
        assertEquals("", output);

        UserInput newUserInput = new UserInput("examine");
        String secondOutput = newUserInput.processCommand(gameEngine);

        assertEquals(
                "You are in the Quidditch Field. You can try and catch the snitch!",
                secondOutput);
    }
    @Test
    public void testInvalidTakeCommandNoItem() {
        UserInput userInput = new UserInput("take Frog");
        String output = userInput.processCommand(gameEngine);
        List<Item> inventory = gameEngine.getPlayer().getInventory();

        assertEquals(0, inventory.size());
        assertEquals("There is no item \"Frog\" in the room.", output);
    }
    @Test
    public void testInvalidTakeCommandNoInputItem() {
        UserInput userInput = new UserInput("take");
        String output = userInput.processCommand(gameEngine);
        List<Item> inventory = gameEngine.getPlayer().getInventory();

        assertEquals(0, inventory.size());
        assertEquals("There is no item \"\" in the room.", output);
    }
    // Drop command tests
    @Test
    public void testValidDropCommandSameItemName() {
        gameEngine.setInventory(new String[] {"Snitch", "Friends"});
        UserInput userInput = new UserInput("drop snitch");
        String output = userInput.processCommand(gameEngine);

        assertEquals(1, gameEngine.getPlayer().getInventory().size());
        assertEquals(2, gameEngine.getCurrentRoom().getItems().size());

        UserInput newUserInput = new UserInput("examine");
        String newOutput = newUserInput.processCommand(gameEngine);

        assertEquals(
                "You are in the Quidditch Field. " +
                        "Items visible [Snitch, Friends]",
                newOutput);
    }
    @Test
    public void testInvalidDropCommandNoItem() {
        gameEngine.setInventory(new String[]{"Snitch"});
        UserInput userInput = new UserInput("drop Owl");
        String output = userInput.processCommand(gameEngine);

        assertEquals("You don't have \"Owl\"!", output);
    }
    @Test
    public void testInvalidDropCommandEmptyInventory() {
        UserInput userInput = new UserInput("drop Frog");
        String output = userInput.processCommand(gameEngine);
        assertEquals("You don't have \"Frog\"!", output);
    }
}