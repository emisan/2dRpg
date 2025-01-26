package org.kayaman.entities;

import lombok.NonNull;

import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Door implements GameObject {

    private static final Logger LOGGER = Logger.getLogger(Door.class.getName());

    private int worldXPos;
    private int worldYPos;
    private final String itemName;
    private final BufferedImage image;

    public Door(@NonNull final String itemName,
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
        worldXPos = mapColNum * tileSize;
        worldYPos = mapRowNum * tileSize;
        LOGGER.log(Level.INFO, () -> "Door at worldX " + worldXPos + ", worldY " + worldYPos);
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
