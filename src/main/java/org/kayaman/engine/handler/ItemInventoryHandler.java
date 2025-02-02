package org.kayaman.engine.handler;

import lombok.NonNull;
import org.kayaman.entities.GameObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemInventoryHandler {

    public Map<String, List<GameObject>> inventory;

    public ItemInventoryHandler() {
        inventory = new HashMap<>();
    }

    public void addToInventory(@NonNull final GameObject gameObject) {
        List<GameObject> gameObjectsInInventory = inventory.get(gameObject.getItemName());
        if (gameObjectsInInventory == null) {
            gameObjectsInInventory = new ArrayList<>();
        }
        gameObjectsInInventory.add(gameObject);
        inventory.put(gameObject.getItemName(), gameObjectsInInventory);
    }

    public List<GameObject> getGameObjectsBy(final String objectName) {
        return inventory.get(objectName);
    }
}
