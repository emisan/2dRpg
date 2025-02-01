package org.kayaman.entities;

import lombok.NonNull;

import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DoorKey implements GameObject {

    private static final Logger LOGGER = Logger.getLogger(DoorKey.class.getName());

    private int worldXPos;
    private int worldYPos;
    private final String itemName;
    private final BufferedImage image;

    public DoorKey(@NonNull final String itemName,
                   @NonNull final BufferedImage image)
    {
        this.itemName = itemName;
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
        //LOGGER.log(Level.INFO, () -> "Door at worldX " + worldXPos + ", worldY " + worldYPos);
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
}
