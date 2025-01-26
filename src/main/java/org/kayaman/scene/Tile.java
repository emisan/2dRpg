package org.kayaman.scene;

import lombok.NonNull;

import java.awt.image.BufferedImage;

public class Tile {

    private boolean collision;
    private int widthOnScreen;
    private int heightOnScreen;

    private int tileNumberOnWorldMap;
    private final BufferedImage tileImage;

    /**
     * Adds a tile image with no collision capability
     * @param tileImage image of this tile
     * @param widthOnScreen drawing size on screen
     * @param heightOnScreen drawing size on screen
     */
    public Tile(@NonNull final BufferedImage tileImage,
                final int widthOnScreen, final int heightOnScreen,
                final int tileNumberOnWorldMap, final boolean collision)
    {
        this.tileImage = tileImage;
        this.widthOnScreen = widthOnScreen;
        this.heightOnScreen = heightOnScreen;
        this.tileNumberOnWorldMap = tileNumberOnWorldMap;
        this.collision = collision;
    }

    public BufferedImage getTileImage() {
        return tileImage;
    }

    public boolean isCollisionActive() {
        return collision;
    }

    public void isCollisionActive(boolean collision) {
        this.collision = collision;
    }

    public int getWidthOnScreen() {
        return widthOnScreen;
    }

    public void setWidthOnScreen(int widthOnScreen) {
        this.widthOnScreen = widthOnScreen;
    }

    public int getHeightOnScreen() {
        return heightOnScreen;
    }

    public void setHeightOnScreen(int heightOnScreen) {
        this.heightOnScreen = heightOnScreen;
    }
}
