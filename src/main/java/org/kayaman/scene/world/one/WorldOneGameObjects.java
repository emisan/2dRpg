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

    private static final String DOORS_FOLDER = "/sprites/scene/doors/";
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
        final Door door = new Door(itemName, image);
        door.setPositionInWorldMapAndOnScreen(worldMapRowNum, worldMapColNum, tileSize);
        gameObjectInThisWorld.add(door);
    }

    private static void addDoorKeyObjects(final int worldMapRowNum,
                                             final int worldMapColNum,
                                             final String resourcePathToImageName,
                                             final String itemName,
                                             final int tileSize)
    {
        final BufferedImage image = SpriteLoader.getSprite(resourcePathToImageName);
        final DoorKey key = new DoorKey(itemName, image);
        key.setPositionInWorldMapAndOnScreen(worldMapRowNum, worldMapColNum, tileSize);
        gameObjectInThisWorld.add(key);
    }

    private static void addDoorsAndKeys(@NonNull final GameScreen gameScreen) {
        final int tileSize = gameScreen.getTileSize();
        addDoorObjects(5, 3, DOORS_FOLDER + "blue_carpet_door_closed.png", "blueCarpetDoorClosed1", tileSize);
        addDoorKeyObjects(4, 5, KEYS_FOLDER + "blue_key.png", "blueDoorKey1", tileSize);
        addDoorObjects(5, 9, DOORS_FOLDER + "red_carpet_door_closed.png", "redCarpetDoorClosed1", tileSize);
        addDoorKeyObjects(11, 3, KEYS_FOLDER + "red_key.png", "redDoorKey1", tileSize);

    }

    static List<GameObject> getGameObjects(@NonNull final GameScreen gameScreen) {
        if (gameObjectInThisWorld == null) {
            gameObjectInThisWorld = new ArrayList<>();
            addDoorsAndKeys(gameScreen);
        }
        return gameObjectInThisWorld;
    }
}
