package org.kayaman.engine.handler;

import lombok.NonNull;
import org.kayaman.engine.controls.GameCharacterKeyboardController;
import org.kayaman.entities.GameCharacter;
import org.kayaman.scene.Tile;

import java.awt.Rectangle;

public class RectangleTileCollisionDetector implements CollisionDetector {

    private int characterWorldXPos;
    private int characterWorldYPos;

    private int collisionLeftX;
    private int collisionRightX;
    private int collisionTopY;
    private int collisionBottomY;

    private int leftTileIndexCol;
    private int rightTileIndexCol;
    private int topTileIndexRow;
    private int bottomTileIndexRow;

    private final int maxWorldWidth;
    private final int maxWorldHeight;

    private int tileSize;
    private int movementSpeed;

    private final Tile[][] collisionMap;

    public RectangleTileCollisionDetector(@NonNull final Tile[][] worldMap,
                                          final int maxWorldWidth,
                                          final int maxWorldHeight)
    {
        collisionMap = worldMap;
        this.maxWorldWidth = maxWorldWidth;
        this.maxWorldHeight = maxWorldHeight;
    }

    @Override
    public void prepareCollisionCheckCoordinates(@NonNull final GameCharacter gameCharacter)
    {
        final Rectangle characterCollisionArea = gameCharacter.getCollisionArea();
        if (characterCollisionArea != null)
        {
            tileSize = gameCharacter.getTileSize();
            movementSpeed = (int)gameCharacter.getMovementSpeed();
            characterWorldXPos = (int) gameCharacter.getXPosOnWorld();
            characterWorldYPos = (int) gameCharacter.getYPosOnWorld();

            // find column and row number where player is actually on world map
            collisionLeftX = characterWorldXPos + characterCollisionArea.x;
            collisionRightX = collisionLeftX + characterCollisionArea.width;
            collisionTopY = characterWorldYPos + characterCollisionArea.y;
            collisionBottomY = collisionTopY + characterCollisionArea.height - 1;

            leftTileIndexCol = collisionLeftX /tileSize;
            rightTileIndexCol = collisionRightX /tileSize;
            topTileIndexRow = collisionTopY /tileSize;
            bottomTileIndexRow = collisionBottomY /tileSize;
        }
    }

    private boolean hasLeftAreaCollisionOn(@NonNull final Tile[][] map) {
        leftTileIndexCol = (collisionLeftX - movementSpeed)/tileSize;
        final Tile tile = map[topTileIndexRow][leftTileIndexCol];
        final Tile tile2 = map[bottomTileIndexRow][leftTileIndexCol];
        return tile.isCollisionActive() || tile2.isCollisionActive();
    }

    private boolean hasRightAreaCollisionOn(@NonNull final Tile[][] map) {
        rightTileIndexCol = (collisionRightX + movementSpeed)/tileSize;
        final Tile tile = map[topTileIndexRow][rightTileIndexCol];
        final Tile tile2 = map[bottomTileIndexRow][rightTileIndexCol];
        return tile.isCollisionActive() || tile2.isCollisionActive();
    }

    private boolean hasUpAreaCollisionOn(@NonNull final Tile[][] map) {
        topTileIndexRow = (collisionTopY - movementSpeed)/tileSize;
        final Tile tile = map[topTileIndexRow][leftTileIndexCol];
        final Tile tile2 = map[topTileIndexRow][rightTileIndexCol];
        return tile.isCollisionActive() || tile2.isCollisionActive();
    }

    private boolean hasDownAreaCollisionOn(@NonNull final Tile[][] map) {
        bottomTileIndexRow = (collisionBottomY + movementSpeed)/tileSize;
        final Tile tile = map[bottomTileIndexRow][leftTileIndexCol];
        final Tile tile2 = map[bottomTileIndexRow][rightTileIndexCol];
        return tile.isCollisionActive() || tile2.isCollisionActive();
    }

    private boolean hasCollisionWhenMovingLeft(@NonNull final Tile[][] map) {
        final int before = characterWorldXPos;
        characterWorldXPos -= movementSpeed;
        boolean state = false;
        if (characterWorldXPos > -1 && characterWorldXPos < maxWorldWidth) {
            state = hasLeftAreaCollisionOn(map);
        }
        else {
            characterWorldXPos = before;
        }
        if (state) {
            characterWorldXPos += movementSpeed;
        }
        return state;
    }

    private boolean hasCollisionWhenMovingRight(@NonNull final Tile[][] map) {
        final int before = characterWorldXPos;
        characterWorldXPos += movementSpeed;
        boolean state = false;
        if (characterWorldXPos > -1 && characterWorldXPos < (maxWorldWidth - tileSize)) {
            state = hasRightAreaCollisionOn(map);
        }
        else {
            characterWorldXPos = before;
        }
        if (state) {
            characterWorldXPos -= movementSpeed;
        }
        return state;
    }

    private boolean hasCollisionWhenMovingUp(@NonNull final Tile[][] map) {
        final int before = characterWorldYPos;
        characterWorldYPos -= movementSpeed;
        boolean state = false;
        if (characterWorldYPos > -1) {
            state = hasUpAreaCollisionOn(map);
        }
        else {
            characterWorldYPos = before;
        }
        if (state) {
            characterWorldYPos += movementSpeed;
        }
        return state;
    }

    private boolean hasCollisionWhenMovingDown(@NonNull final Tile[][] map) {
        final int before = characterWorldYPos;
        characterWorldYPos += movementSpeed;
        boolean state = false;
        if (characterWorldYPos > -1 && characterWorldYPos < (maxWorldHeight - tileSize)) {
            state = hasDownAreaCollisionOn(map);
        }
        else {
            characterWorldYPos = before;
        }
        if (state) {
            characterWorldYPos -= movementSpeed;
        }
        return state;
    }

    public boolean hasCollisionOnWorldTiles(@NonNull final GameCharacter gameCharacter)
    {
        boolean collision = false;
        prepareCollisionCheckCoordinates(gameCharacter);
        final String lastDirection = gameCharacter.getGameCharacterKeyboardController().getLastDirection();
        switch (lastDirection)
        {
            case GameCharacterKeyboardController.LAST_DIRECTION_LEFT:
                collision = hasCollisionWhenMovingLeft(collisionMap);
                gameCharacter.setXPosOnWorld(characterWorldXPos);
                break;
            case GameCharacterKeyboardController.LAST_DIRECTION_RIGHT:
                collision = hasCollisionWhenMovingRight(collisionMap);
                gameCharacter.setXPosOnWorld(characterWorldXPos);
                break;
            case GameCharacterKeyboardController.LAST_DIRECTION_UP:
                collision = hasCollisionWhenMovingUp(collisionMap);
                gameCharacter.setYPosOnWorld(characterWorldYPos);
                break;
            case GameCharacterKeyboardController.LAST_DIRECTION_DOWN:
                collision = hasCollisionWhenMovingDown(collisionMap);
                gameCharacter.setYPosOnWorld(characterWorldYPos);
                break;
            default:
                break;
        }
        return collision;
    }
}
