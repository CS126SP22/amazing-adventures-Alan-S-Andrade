package student.adventure;

import com.google.gson.annotations.SerializedName;


public class Adventure {
    @SerializedName("startingRoom")
    private String startingRoom;
    @SerializedName("endingRoom")
    private String endingRoom;
    @SerializedName("rooms")
    private Room[] rooms;

    public Adventure(String newStartingRoom, String newEndingRoom, Room[] newRooms) {
        startingRoom = newStartingRoom;
        endingRoom = newEndingRoom;
        rooms = newRooms;
    }

    public String getStartingRoom() {
        return startingRoom;
    }

    public String getEndingRoom() {
        return endingRoom;
    }

    public Room[] getRooms() {
        return rooms;
    }
}
