package student.adventure;

import com.google.gson.annotations.SerializedName;

public class Room {
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("directions")
    private Directions[] directions;
    @SerializedName("itemsHere")
    private Item[] items;

    public Room(String newName, String newDescription, Directions[] newDirections, Item[] newItems) {
        name = newName;
        description = newDescription;
        directions = newDirections;
        items = newItems;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Directions[] getDirections() {
        return directions;
    }

    public Item[] getItems() {
        return items;
    }
}
