package org.kayaman.scene.world.one;


import org.kayaman.loader.SpriteLoader;
import org.kayaman.scene.Tile;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public final class WorldOneTiles {

    private static final String BUILDING_FOLDER = "/sprites/scene/building/";
    private static final String WORLD_FOLDER = "/sprites/scene/world/";

    private static int tileSizeInThisWorld;
    private static List<Tile> tilesOfThisWorld;

    private WorldOneTiles() {
    }

    private static void setTileSizeInThisWorld(final int size) {
        tileSizeInThisWorld = size;
    }

    private static void addToTileList(final String resourcePath,
                                      final String tilePicName,
                                      final int tileNumberOnWorldMap,
                                      final boolean colliedAble)
    {
        final BufferedImage sprite = SpriteLoader.getSprite(resourcePath + tilePicName);
        if (tileSizeInThisWorld > 0)
        {
            tilesOfThisWorld.add(
                    new Tile(sprite, tileSizeInThisWorld, tileSizeInThisWorld, tileNumberOnWorldMap, colliedAble));
        }
    }

    private static void addGroundTiles() {
        addGroundElements();
        addBuildings();
    }

    private static void addGroundElements() {
        // tileNum 0
        addToTileList(WORLD_FOLDER, "forest.png", 0, true);
        // tileNum 1
        addToTileList(WORLD_FOLDER, "grass_light.png", 1, false);
        // tileNum 14
        addToTileList(WORLD_FOLDER, "grass_light_right.png", 14, false);
        // tileNum 2
        addToTileList(WORLD_FOLDER, "sand.png", 2, false);
        addTrees();
    }

    private static void addTrees() {
        // tileNum 10
        addToTileList(WORLD_FOLDER, "tree_left_border_sand.png", 10, true);
        // tileNum 11
        addToTileList(WORLD_FOLDER, "tree_right_border_sand.png", 11, true);
        // tileNum 12
        addToTileList(WORLD_FOLDER, "tree_top_border_sand.png", 12, true);
        // tileNum 13
        addToTileList(WORLD_FOLDER, "tree_bottom_border_sand.png", 13, true);
    }

    private static void addBuildings() {
        // tileNum 3
        addToTileList(BUILDING_FOLDER, "brown_bricked_wall_down_left.png", 3, true);
        // tileNum 4
        addToTileList(BUILDING_FOLDER, "brown_bricked_wall_down_right.png", 4, true);
        // tileNum 5
        addToTileList(BUILDING_FOLDER, "brown_bricked_wall_left.png", 5, true);
        // tileNum 6
        addToTileList(BUILDING_FOLDER, "brown_bricked_wall_right.png", 6, true);
        // tileNum 7
        addToTileList(BUILDING_FOLDER, "brown_bricked_wall_down_left.png", 7, true);
        // tileNum 8
        addToTileList(BUILDING_FOLDER, "brown_bricked_wall_down_right.png", 8, true);
        // tileNum 9
        addToTileList(BUILDING_FOLDER, "brown_bricked_wall_front_backyard.png", 9, true);
    }

    public static List<Tile> getTilesOfThisWorld(final int tileSize) {
        if (tilesOfThisWorld == null) {
            setTileSizeInThisWorld(tileSize);
            tilesOfThisWorld = new ArrayList<>();
            addGroundTiles();
        }
        return tilesOfThisWorld;
    }
}
