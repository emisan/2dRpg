package org.kayaman.scene.world.one;

import lombok.NonNull;
import org.kayaman.engine.GameEngine;
import org.kayaman.entities.GameCharacter;
import org.kayaman.entities.GameObject;
import org.kayaman.loader.SpriteLoader;
import org.kayaman.scene.Tile;
import org.kayaman.engine.WorldMapTileManager;
import org.kayaman.scene.World;
import org.kayaman.screen.GameScreen;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

public class WorldOne implements World {

    private static final int WORLD_NUMBER = 1;

    private int tileSize;

    // world settings
    private final int maxWorldColumns;
    private final int maxWorldRows;

    private final GameCharacter player;

    private List<GameObject> worldGameObjects;
    private final Tile[][] worldMap;

    public WorldOne(@NonNull final GameScreen gameScreen)
    {
        player = gameScreen.getPlayer();
        tileSize = gameScreen.getTileSize();
        // prepare mapping of world map, also how large the map is in max columns and rows
        final WorldMapTileManager worldMapTileManager = new WorldMapTileManager("/maps/world_1.txt");
        worldMapTileManager.setMapTiles(WorldOneTiles.getTilesOfThisWorld(tileSize));
        maxWorldColumns = worldMapTileManager.getWorldMapMaxColumns();
        maxWorldRows = worldMapTileManager.getWorldMapMaxColumns();
        worldMap = worldMapTileManager.getTileMap();
    }

    @Override
    public Tile[][] getWorldMap() {
        return worldMap;
    }

    @Override
    public void setWorldGameObjects(@NonNull final List<GameObject> gameObjects) {
        worldGameObjects = gameObjects;
    }

    @Override
    public List<GameObject> getWorldGameObjects() {
        return worldGameObjects;
    }

    @Override
    public int getMaxWorldColumns() {
        return maxWorldColumns;
    }

    @Override
    public int getMaxWorldRows() {
        return maxWorldRows;
    }

    @Override
    public int getMapNumber() {
        return WORLD_NUMBER;
    }

    @Override
    public void setTileSize(final int tileSize) {
        this.tileSize = tileSize;
    }

    @Override
    public void drawMap(@NonNull final Graphics2D g2)
    {
        final int playerPosXOnWorldMap = (int)player.getXPosOnWorld();
        final int playerPosYOnWorldMap = (int)player.getYPosOnWorld();
        final int playerPosXOnScreen = player.getXPosOnScreen();
        final int playerPosYOnScreen = player.getYPosOnScreen();

        if (worldMap != null)
        {
            for (int row = 0; row < maxWorldRows; row++) {
                for (int col = 0; col < maxWorldColumns; col++) {
                    final int worldXMapPos = col * tileSize;
                    final int worldYMapPos = row * tileSize;
                    final int screenPosX = worldXMapPos - playerPosXOnWorldMap + playerPosXOnScreen;
                    final int screenPosY = worldYMapPos - playerPosYOnWorldMap + playerPosYOnScreen;

                    // world tile images boundary to draw only the tiles we need to see on screen while moving the player
                    if (worldXMapPos + tileSize > playerPosXOnWorldMap - playerPosXOnScreen &&
                            worldXMapPos - tileSize < playerPosXOnWorldMap + playerPosXOnScreen &&
                            worldYMapPos + tileSize > playerPosYOnWorldMap - playerPosYOnScreen &&
                            worldYMapPos - tileSize < playerPosYOnWorldMap + playerPosYOnScreen)
                    {
                        // drawFasterByScalingImage does not make impact on performance on world tiles
                        // so we don't use it for world tiles
                        g2.drawImage(
                                worldMap[row][col].getTileImage(), screenPosX, screenPosY, tileSize, tileSize, null);
                    }
                }
            }
        }
        else {
            final BufferedImage error = SpriteLoader.getSprite("misc/error.png");

            g2.drawImage(error, 0, 0, null);
        }
        // use of drawFasterByScalingImage in this method makes a slight difference
        drawObjects(g2, playerPosXOnWorldMap, playerPosYOnWorldMap, playerPosXOnScreen, playerPosYOnScreen, tileSize);
    }

    @Override
    public void drawObjects(Graphics2D g2,
                            final int playerWorldXPos,
                            final int playerWorldYPos,
                            final int playerScreenXPos,
                            final int playerScreenYPos,
                            final int tileSize) {
        if (worldGameObjects != null) {
            for (final GameObject obj : worldGameObjects) {
                final int worldXPos = obj.getWorldXPos();
                final int worldYPos = obj.getWorldYPos();
                final int onScreenX = worldXPos - playerWorldXPos + playerScreenXPos;
                final int onScreenY = worldYPos - playerWorldYPos + playerScreenYPos;
                GameEngine.drawFasterByScalingImage(g2, obj.getImage(), tileSize, onScreenX, onScreenY);
            }
        }
    }


}
