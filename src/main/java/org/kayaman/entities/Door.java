package org.kayaman.entities;

import lombok.NonNull;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Door implements GameObject {

    private static final Logger LOGGER = Logger.getLogger(Door.class.getName());

    private int worldXPos;
    private int worldYPos;
    private final String itemName;
    private final String keyNameToUnlock;
    private Rectangle collisionArea;
    private final BufferedImage image;

    public Door(@NonNull final String itemName,
                @NonNull final String keyNameToUnlock,
                @NonNull final BufferedImage image)
    {
        this.itemName = itemName;
        this.keyNameToUnlock = keyNameToUnlock;
        this.image = image;
    }

    @Override
    public void setPositionInWorldMapAndOnScreen(final int mapRowNum,
                                                 final int mapColNum,
                                                 final int tileSize)
    {
        // we need to subtract by one to get starting index to draw on screen
        worldXPos = (mapColNum-1) * tileSize;
        worldYPos = (mapRowNum-1) * tileSize;
    }

    @Override
    public int getWorldXPos() {
        return worldXPos;
    }

    @Override
    public int getWorldYPos() {
        return worldYPos;
    }

    @Override
    public String getItemName() {
        return itemName;
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }

    @NonNull
    public String getKeyNameToUnlock() {
        return keyNameToUnlock;
    }

    @Override
    public void setCollisionArea(@NonNull final Rectangle collisionArea) {
        this.collisionArea = collisionArea;
    }

    @Override
    public Rectangle getCollisionArea() {
        return collisionArea;
    }
}
