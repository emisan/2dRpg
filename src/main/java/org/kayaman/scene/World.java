package org.kayaman.scene;

import lombok.NonNull;
import org.kayaman.screen.GameScreen;

import java.awt.Graphics2D;

public interface World {

    int getMapNumber();
    int getMaxWorldColumns();
    int getMaxWorldRows();
    void setTileSize(final int tileSize);
    Tile[][] getWorldMap();
    void setupWorldGameObjects(@NonNull final GameScreen gameScreen);
    void drawMap(@NonNull final Graphics2D g2);
    void drawObjects(@NonNull final Graphics2D g2,
                     final int playerWorldXpos,
                     final int playerWorldYpos,
                     final int playerScreenXPos,
                     final int playerScreenYPos,
                     final int tileSize);
}
