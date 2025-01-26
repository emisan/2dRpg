package org.kayaman.scene;

import lombok.NonNull;
import org.kayaman.entities.GameCharacter;
import org.kayaman.entities.GameObject;
import org.kayaman.screen.GameScreen;

import java.awt.Graphics2D;
import java.util.List;

public interface World {

    int getMapNumber();
    int getMaxWorldColumns();
    int getMaxWorldRows();
    void setTileSize(final int tileSize);
    Tile[][] getWorldMap();
    void setupWorldGameObjects(@NonNull final GameScreen gameScreen);
    void drawMap(@NonNull final Graphics2D g2);
    void drawObjects(@NonNull final Graphics2D g2,
                     final int worldWidth,
                     final int worldHeight,
                     final int tileSize);
}
