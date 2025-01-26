package org.kayaman.scene.world.one;

import lombok.NonNull;
import org.kayaman.entities.GameCharacter;
import org.kayaman.entities.GameObject;
import org.kayaman.loader.SpriteLoader;
import org.kayaman.scene.Tile;
import org.kayaman.loader.WorldMapTileManager;
import org.kayaman.scene.World;
import org.kayaman.screen.GameScreen;

import java.awt.Graphics2D;
import java.util.List;

public class WorldOne implements World {

    private static final int WORLD_NUMBER = 1;

    private int tileSize;

    // world settings
    private final int maxWorldColumns;
    private final int maxWorldRows;

    private final GameCharacter player;

    private List<GameObject> worldObjects;
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
        worldObjects = WorldOneGameObjects.getGameObjects(gameScreen);
    }

    @Override
    public Tile[][] getWorldMap() {
        return worldMap;
    }

    @Override
    public void setupWorldGameObjects(@NonNull final GameScreen gameScreen) {
        worldObjects = WorldOneGameObjects.getGameObjects(gameScreen);
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
        if (worldMap != null)
        {
            for (int row = 0; row < maxWorldRows; row++) {
                for (int col = 0; col < maxWorldColumns; col++) {
                    final int worldXMapPos = col * tileSize;
                    final int worldYMapPos = row * tileSize;

                    final int playerPosXOnWorldMap = (int)player.getXPosOnWorld();
                    final int playerPosYOnWorldMap = (int)player.getYPosOnWorld();
                    final int playerPosXOnScreen = player.getXPosOnScreen();
                    final int playerPosYOnScreen = player.getYPosOnScreen();

                    final int screenPosX = worldXMapPos - playerPosXOnWorldMap + playerPosXOnScreen;
                    final int screenPosY = worldYMapPos - playerPosYOnWorldMap + playerPosYOnScreen;

                    // world tile images boundary to draw only the tiles we need to see on screen while moving the player
                    if (worldXMapPos + tileSize > playerPosXOnWorldMap - playerPosXOnScreen &&
                            worldXMapPos - tileSize < playerPosXOnWorldMap + playerPosXOnScreen &&
                            worldYMapPos + tileSize > playerPosYOnWorldMap - playerPosYOnScreen &&
                            worldYMapPos - tileSize < playerPosYOnWorldMap + playerPosYOnScreen)
                    {
                        g2.drawImage(worldMap[row][col].getTileImage(),
                                screenPosX, screenPosY, tileSize, tileSize, null);
                    }
                }
            }
        }
        else {
            g2.drawImage(SpriteLoader.getSprite("misc/error.png"),
                    0, 0, tileSize, tileSize, null);
        }
    }

    @Override
    public void drawObjects(Graphics2D g2, final int worldWidth, final int worldHeight, final int tileSize) {
        final List<GameObject> objects = worldObjects;
        if (worldObjects != null) {
            for (final GameObject obj : objects) {
                final int worldXPos = obj.getWorldXPos();
                final int worldYPos = obj.getWorldYPos();
                if (worldXPos > 0 && worldXPos <= worldWidth && worldYPos > 0 && worldYPos <= worldHeight) {
                    g2.drawImage(obj.getImage(), worldXPos, worldYPos, tileSize, tileSize, null);
                }
            }
        }
    }


}
