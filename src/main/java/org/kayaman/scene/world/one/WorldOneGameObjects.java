package org.kayaman.scene.world.one;

import lombok.NonNull;
import org.kayaman.entities.Door;
import org.kayaman.entities.DoorKey;
import org.kayaman.entities.GameObject;
import org.kayaman.loader.SpriteLoader;
import org.kayaman.screen.GameScreen;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public final class WorldOneGameObjects {

    private static final String DOORS_FOLDER = "/sprites/scene/items/doors/";
    private static final String KEYS_FOLDER = "/sprites/scene/items/keys/";
    private static final String STONES_FOLDER = "/sprites/scene/items/stones/";

    private static List<GameObject> gameObjectInThisWorld;

    private WorldOneGameObjects() {
    }

    private static void addDoorObjects(final int worldMapRowNum,
                                      final int worldMapColNum,
                                      final String resourcePathToImageName,
                                      final String itemName,
                                      final int tileSize)
    {
        final BufferedImage image = SpriteLoader.getSprite(resourcePathToImageName);
        if (image != null) {
            final Door door = new Door(itemName, image);
            door.setPositionInWorldMapAndOnScreen(worldMapRowNum, worldMapColNum, tileSize);
            gameObjectInThisWorld.add(door);
        }
    }

    private static void addDoorKeyObjects(final int worldMapRowNum,
                                             final int worldMapColNum,
                                             final String resourcePathToImageName,
                                             final String itemName,
                                             final int tileSize)
    {
        final BufferedImage image = SpriteLoader.getSprite(resourcePathToImageName);
        if (image != null) {
            final DoorKey key = new DoorKey(itemName, image);
            key.setPositionInWorldMapAndOnScreen(worldMapRowNum, worldMapColNum, tileSize);
            gameObjectInThisWorld.add(key);
        }
    }

    private static void addDoorsAndKeys(@NonNull final GameScreen gameScreen) {
        final int tileSize = gameScreen.getTileSize();
        addDoorKeyObjects(4, 3, KEYS_FOLDER + "blue_key.png", "blueDoorKey1", tileSize);
        addDoorObjects(5, 3, DOORS_FOLDER + "blue_carpet_door_closed.png", "blueCarpetDoorClosed1", tileSize);
        addDoorKeyObjects(8, 5, KEYS_FOLDER + "red_key.png", "redDoorKey1", tileSize);
        addDoorObjects(9, 5, DOORS_FOLDER + "red_carpet_door_closed.png", "redCarpetDoorClosed1", tileSize);

//            addDoorObjects(DOORS_FOLDER + "red_carpet_door_open.png", "redDoorOpen1", , tileSize);
//            addDoorObjects(DOORS_FOLDER + "red_carpet_door_closed.png", "redDoorClosed1", tileSize);
//            addDoorObjects(DOORS_FOLDER + "green_carpet_door_open.png", "greenDoorOpen1", tileSize);
//            addDoorObjects(DOORS_FOLDER + "green_carpet_door_closed.png", "greenDoorClosed1", tileSize);
//            addDoorObjects(DOORS_FOLDER + "orange_carpet_door_open.png", "orangeDoorOpen1", tileSize);
//            addDoorObjects(DOORS_FOLDER + "orange_carpet_door_closed.png", "orangeDoorClosed1", tileSize);
//            addDoorKeyObjects(KEYS_FOLDER + "red_key.png", "redKey1", tileSize);
//            addDoorKeyObjects(KEYS_FOLDER + "green_key.png", "greenKey1", tileSize);
//            addDoorKeyObjects(KEYS_FOLDER + "yellow_key.png",
//                    "yellowKey1", true);
//            addToWorldObjectsList(STONES_FOLDER + "stones1.png", "stones1", tileSize);
    }

    static List<GameObject> getGameObjects(@NonNull final GameScreen gameScreen) {
        if (gameObjectInThisWorld == null) {
            gameObjectInThisWorld = new ArrayList<>();
            addDoorsAndKeys(gameScreen);
        }
        return gameObjectInThisWorld;
    }
}
