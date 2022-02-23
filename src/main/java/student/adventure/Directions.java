package student.adventure;

import com.google.gson.annotations.SerializedName;

public class Directions {
    @SerializedName("directionName")
    private String directionName;
    @SerializedName("room")
    private String room;

    public Directions(String newDirectionName, String newRoom) {
        directionName = newDirectionName;
        room = newRoom;
    }

    public String getDirectionName() {
        return directionName;
    }

    public String getRoom() {
        return room;
    }
    @Override
    public String toString() {
        return directionName;
    }
}