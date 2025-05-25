package org.kayaman.engine.handler;

import lombok.NonNull;
import org.kayaman.engine.handler.inventory.ItemInventoryEntry;
import org.kayaman.entities.Door;
import org.kayaman.entities.DoorKey;
import org.kayaman.entities.GameCharacter;
import org.kayaman.entities.GameObject;
import org.kayaman.screen.GameScreen;

import javax.annotation.Nullable;
import java.awt.Rectangle;
import java.util.List;

public class RectangleGameObjectCollisionDetection {

    private final List<GameObject> gameObjects;

    public RectangleGameObjectCollisionDetection(@NonNull final List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    private boolean hasObjectCollision(@NonNull final GameObject gameObject, @NonNull final GameCharacter gameCharacter)
    {
        // we need to save characters collision area x,y in order to prevent creating new rectangle objects.
        // Rethink, that this check is part of the game loop and would create
        // otherwise each time 2 new rectangles to be clean up later by garbage collector if we do otherwise!
        //
        final Rectangle objectRect = gameObject.getCollisionArea();
        final Rectangle characterRect = gameCharacter.getCollisionArea();
        final int savedRectX = characterRect.x;
        final int savedRectY = characterRect.y;
        final int saveObjX = objectRect.x;
        final int saveObjY = objectRect.y;
        objectRect.x = gameObject.getWorldXPos() + objectRect.x;
        objectRect.y = gameObject.getWorldYPos() + objectRect.y;
        characterRect.x = (int)gameCharacter.getXPosOnWorld() + characterRect.x;
        characterRect.y = (int)gameCharacter.getYPosOnWorld() + characterRect.y;
        final boolean state = characterRect.intersects(objectRect);

        // if this would not be done, character can not move in the world properly as its collision area has been
        // changed above, so set it to its default for other collision detections in the world!
        characterRect.x = savedRectX;
        characterRect.y = savedRectY;
        objectRect.x = saveObjX;
        objectRect.y = saveObjY;
        gameCharacter.setCollisionArea(characterRect);
        return state;
    }

    @Nullable
    public GameObject getGameObjectColliedWith(@NonNull final GameCharacter gameCharacter)
    {
        GameObject gameObj = null;
        for (GameObject gameObject : gameObjects) {
            if (hasObjectCollision(gameObject, gameCharacter)) {
                gameObj = gameObject;
                break;
            }
        }
        return gameObj;
    }

    public void addGameObject(@NonNull final GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void removeGameObject(@NonNull final GameObject gameObject) {
        gameObjects.remove(gameObject);
    }

    public boolean doorOpened(@NonNull final GameScreen gameScreen, @NonNull final Door door)
    {
        final List<ItemInventoryEntry> itemInventory =
                gameScreen.getItemInventoryWindow().getItemInventoryHandler().getInventory();
        final ItemInventoryEntry doorKey = itemInventory.stream()
                .filter(gameObject -> gameObject.getItemName().equals(door.getKeyNameToUnlock()))
                .findFirst().orElse(null);
        if (doorKey != null && doorKey.getAmount() > 0) {
            gameScreen.getOnScreenUI().setMessage("You used " + door.getKeyNameToUnlock() + " to unlock the Door!");
            gameScreen.getItemInventoryWindow().removeFromListModel(doorKey);
            gameObjects.remove(door);
            return true;
        }
        else {
            gameScreen.getOnScreenUI().setMessage("You need " + door.getKeyNameToUnlock() + " to unlock the Door!");
        }
        return false;
    }

    public boolean pickUp(@NonNull final GameScreen gameScreen, @NonNull final GameObject gameObject) {
        if (gameObject instanceof DoorKey) {
            gameScreen.getSoundFxLoader().playMusic("Pickup.mp3", false);
            gameScreen.getOnScreenUI().setMessage("Picked up " + gameObject.getItemName());
            gameScreen.addToItemInventoryWindow(gameObject);
            removeGameObject(gameObject);
            return true;
        }
        return false;
    }
}
