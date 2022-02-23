package student.adventure;

import java.util.Scanner;

/** The command line interface for playing the game */
public class LocalOutput {

    /** Starts the game loop that prompts user input and prints user output. */
    public static void startGameLoop(GameEngine gameEngine) {
        Scanner console = new Scanner(System.in);
        System.out.println(gameEngine.getCurrentRoom());
        System.out.println("Welcome back for a new year at Hogwarts Harry!\n" +
                "Headmaster Umb-ridge is banning all sorts of Defense against Dark Arts wizardry\n" +
                "and we must practice in order to stay sharp if You Know Who strikes back.\n" +
                "We shall go around Hogwarts and try to find the Room of Requirement.\n" +
                "only there could we practice Magic and save our school!\n" +
                "Let's go!\n");

        while (!(gameEngine.isGameOver())) {
            System.out.print("> ");

            UserInput userInput = new UserInput(console.nextLine());

            String gameOutput = userInput.processCommand(gameEngine);

            // All the commands that don't need to print any information return an empty string
            if (gameOutput.equals("")) {
                continue;
            }

            System.out.println(gameOutput);
        }
    }
}