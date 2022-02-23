package student.adventure;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

public class Game {
    // These are the identifiers associated with any new instance of the game
    // active_flag represents whether the game is still going

    private List<Item> listOfItems;
    private String currentRoom;
    private HashMap<String, Integer> roomIndices;
    private static boolean active_flag;

    /**
    * Start the game at room: Entrance Courtyard
    * Fill in Hashmap with the index of each room according to its name
    * @param adventure the Adventure object from JSON file
    */
    public void Begin(Adventure adventure) {
        currentRoom = fillRoomIndices(adventure);
        listOfItems = new ArrayList<>();
        System.out.println("Welcome back for a new year at Hogwarts Harry!\n" +
                "Headmaster Umb-ridge is banning all sorts of Defense against Dark Arts wizardry\n" +
                "and we must practice in order to stay sharp if You Know Who strikes back.\n" +
                "We shall go around Hogwarts and try to find the Room of Requirement.\n" +
                "only there could we practice Magic and save our school!\n" +
                "Let's go!\n");
        active_flag = true;
        while (active_flag) {
            engine(adventure);
        }
    }

    /**
    * Runs the actual game:
    * Prompts user and prints quest questions
    * @param adventure JSON object file
    */
    public void engine(Adventure adventure) {
        Scanner sc = new Scanner(System.in);
        Room[] allRooms = adventure.getRooms();
        System.out.print(allRooms[roomIndices.get(parse(currentRoom))].getDescription() + "\n>");
        String action = parse(sc.nextLine());
        switch (action) {
            case "quit":
            case "exit":
                active_flag = false;
                break;
            case "examine":
                System.out.println(examine(adventure));
                break;
            default:
                identifyAction(allRooms, action);
                break;
        }
    }

    /** Print current state of the game and information
    *
    * @param adventure JSON object*/
    public String examine(Adventure adventure) {
        System.out.print("Items: ");
        for (Item item : listOfItems) {
            System.out.println(item.getItemName());
        }
        return adventure.getRooms()[roomIndices.get(parse(currentRoom))].getDescription();
    }

    /** Change the state of the game through the new user input
    *
    * @param allRooms All the rooms available to the player
    * @param action The newly inputted action given by the player
    */
    public void changeCurrentRoom(Room[] allRooms, String action) {
        String[] inputString = action.split("go");
        String parsedAction =  inputString[1];
        for (Directions direction : allRooms[roomIndices.get(parse(currentRoom))].getDirections()) {
            String parsedDirection = parse(direction.getDirectionName());
            if (parsedDirection.equals(parsedAction)) {
                currentRoom = direction.getRoom();
                checkWin(currentRoom);
            }
        }
    }

    /** Fill the hash map associating Room names to Array indices
    *
    * @param adventure JSON object
    * @return the starting room
    */
    public String fillRoomIndices(Adventure adventure) {
        String firstRoom = "";
        roomIndices = new HashMap<>();
        int i = -1;
        for (Room room : adventure.getRooms()) {
            i++;
            String roomName = parse(room.getName());
            if (i == 0) firstRoom = roomName;
            roomIndices.put(roomName, i);
        }
        return firstRoom;
    }

    /** Parse action input from player
    *
    * @param action Raw keyboard input
    */
    public String parse(String action) {
        action = action.replaceAll("\\s+","");
        action = action.toLowerCase();
        return action;
    }

    /** Identify action from user and execute it
    *
    * @param action input*/
    public String identifyAction(Room[] allRooms, String action) {
        if (action.contains("take")) {
            grabItem(action);
        } else if (action.contains("go")) {
            changeCurrentRoom(allRooms, action);
        } else {
            System.out.println("I don't understand " + action + " Harry");
            return "Bad input";
        }
        return "good";
    }

    /** Pick up item in room
    *
    * @param item string passed */
    public void grabItem(String item) {
        String[] inputString = item.split("take");
        Item itemGrabbed = new Item(inputString[1]);
        listOfItems.add(itemGrabbed);
    }

    /** Check if user has won
    *
    * @param presentRoom entered
    */
    public void checkWin(String presentRoom) {
        boolean friends = false, potion = false, trophy = false;
        if (presentRoom.equals("roomofrequirementdoor")) {
            for (Item item : listOfItems) {
                if (item.getItemName().equals("friends")) friends = true;
                if (item.getItemName().equals("potion")) potion = true;
                if (item.getItemName().equals("trophy")) trophy = true;
            }
        }
        if (friends && trophy && potion) {
            System.out.println("You made it to the room of requirement! Practice your skills here!\n");
            active_flag = false;
        }
    }
}
