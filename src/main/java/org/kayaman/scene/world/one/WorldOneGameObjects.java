package org.kayaman.scene.world.one;

import lombok.NonNull;
import org.kayaman.entities.Door;
import org.kayaman.entities.DoorKey;
import org.kayaman.entities.GameObject;
import org.kayaman.loader.SpriteLoader;

import java.awt.Rectangle;
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
                                      @NonNull final String itemName,
                                      @NonNull final String keyNameToUnlock,
                                      final int tileSize)
    {
        final BufferedImage image = SpriteLoader.getSprite(resourcePathToImageName);
        final Door door = new Door(itemName, keyNameToUnlock, image);
        door.setCollisionArea(new Rectangle(0, 0, tileSize, tileSize));
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
        key.setCollisionArea(new Rectangle(0, 0, tileSize, tileSize));
        key.setPositionInWorldMapAndOnScreen(worldMapRowNum, worldMapColNum, tileSize);
        gameObjectInThisWorld.add(key);
    }

    private static void addDoorsAndKeys(@NonNull final int tileSize) {
        addDoorObjects(5, 3, DOORS_FOLDER + "blue_carpet_door_closed.png",
                "blueCarpetDoorClosed1", "blueDoorKey", tileSize);
        addDoorKeyObjects(4, 5, KEYS_FOLDER + "blue_key.png", "blueDoorKey", tileSize);
        addDoorObjects(5, 9, DOORS_FOLDER + "red_carpet_door_closed.png",
                "redCarpetDoorClosed1", "redDoorKey", tileSize);
        addDoorKeyObjects(11, 3, KEYS_FOLDER + "red_key.png", "redDoorKey", tileSize);
        addDoorKeyObjects(13, 5, KEYS_FOLDER + "red_key.png", "redDoorKey", tileSize);
    }

    public static List<GameObject> getGameObjects(@NonNull final int tileSizeInWorld) {
        if (gameObjectInThisWorld == null) {
            gameObjectInThisWorld = new ArrayList<>();
            addDoorsAndKeys(tileSizeInWorld);
        }
        return gameObjectInThisWorld;
    }
}
