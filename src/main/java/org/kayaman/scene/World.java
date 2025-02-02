package org.kayaman.scene;

import lombok.NonNull;
import org.kayaman.engine.ImageProcessingPerformance;
import org.kayaman.entities.GameObject;

import java.awt.Graphics2D;
import java.util.List;

public interface World extends ImageProcessingPerformance {

    void setTileSize(final int tileSize);
    void drawMap(@NonNull final Graphics2D g2);
    void drawObjects(@NonNull final Graphics2D g2,
                     final int playerWorldXPos,
                     final int playerWorldYPos,
                     final int playerScreenXPos,
                     final int playerScreenYPos,
                     final int tileSize);
    int getMapNumber();
    int getMaxWorldColumns();
    int getMaxWorldRows();
    Tile[][] getWorldMap();
    void setWorldGameObjects(@NonNull final List<GameObject> gameObjects);
    List<GameObject> getWorldGameObjects();
}
