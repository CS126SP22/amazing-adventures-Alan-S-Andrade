package student.adventure;

import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("itemName")
    private String itemName;

    public Item(String newItemName) {
        itemName = newItemName;
    }

    public String getItemName() {
        return itemName;
    }
}
