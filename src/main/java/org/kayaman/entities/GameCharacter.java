package org.kayaman.entities;

import lombok.NonNull;
import org.kayaman.engine.controls.GameCharacterKeyboardController;
import org.kayaman.engine.handler.RectangleGameObjectCollisionDetection;
import org.kayaman.engine.handler.RectangleTileCollisionDetector;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public interface GameCharacter {

    // related to player movements in the world, must be double according zoom in-out calculation
    void setXPosOnWorld(final double xPosOnWorld);
    double getXPosOnWorld();
    void setYPosOnWorld(final double yPosOnWorld);
    double getYPosOnWorld();

    // related to where the player is drawn on screen
    void setXPosOnScreen(final int xPosOnScreen);
    int getXPosOnScreen();
    void setYPosOnScreen(final int yPosOnScreen);
    int getYPosOnScreen();

    // tileSize in world
    void setTileSize(final int tileSize);
    int getTileSize();

    void setMovementSpeed(final double movementSpeed);
    double getMovementSpeed();

    void setCollisionArea(final Rectangle collisionArea);
    Rectangle getCollisionArea();

    void setGameCharacterKeyboardController(final GameCharacterKeyboardController gameCharacterKeyboardController);
    GameCharacterKeyboardController getGameCharacterKeyboardController();

    void setCollisionDetector(@NonNull final RectangleTileCollisionDetector collisionDetector);
    RectangleTileCollisionDetector getCollisionDetector();

    void setGameObjectsCollisionDetector(@NonNull final RectangleGameObjectCollisionDetection collisionDetector);
    RectangleGameObjectCollisionDetection getGameObjectsCollisionDetector();

    void draw(@NonNull final Graphics2D graphics2D);
}
