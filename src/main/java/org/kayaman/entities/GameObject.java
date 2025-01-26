package org.kayaman.entities;

import org.kayaman.screen.GameScreen;

import java.awt.image.BufferedImage;

public interface GameObject {

    void setPositionInWorldMapAndOnScreen(final int mapRowNum,
                                          final int mapColNum,
                                          final int tileSize);
    int getWorldXPos();
    int getWorldYPos();
    String getItemName();
    BufferedImage getImage();
}
