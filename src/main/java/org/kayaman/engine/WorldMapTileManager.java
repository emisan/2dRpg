package org.kayaman.engine;

import lombok.NonNull;
import org.kayaman.scene.Tile;
import org.kayaman.scene.World;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages an amount of tiles in an internal tile-array to set and to use in {@link World}.
 */
public class WorldMapTileManager {

    private static final Logger LOGGER = Logger.getLogger(WorldMapTileManager.class.getName());

    private static final String DELIMITER = " ";
    private static final String FAILURE_IN_MAP_INDEXING = "Failure in map indexing.";
    private static final String TILE_NUM_NOT_INDEXED = "Found tile number not part of tiles.";
    private static final String RESOURCE_MAP_NOT_LOADED_MSG = "Resource path not found to load world map!\n";

    private int worldMapMaxColumns;
    private int worldMapMaxRows;

    private final int[][] mapTileNumbers;
    private Tile[] tiles;

    /**
     * Initializes new manager with amount of columns and rows filled with digits of a world (map).
     * The world map from the resource path must be a map consisting of columns (including mapping numbers of tiles)
     * set in rows (new lines) saved inside a TXT-File.
     */
    public WorldMapTileManager(@NonNull final String mapResourcePath)
    {
        // amount of columns and rows of the world map will be set after reading the world map digits
        // from resource in getActualWorldMapNumbersFrom-method
        mapTileNumbers = getActualWorldMapNumbersFrom(mapResourcePath);
    }

    /*
     creates a new int-array from the given map information columns and row size.
     the created array will be null, when there are wrong information read from the map e.g. when the map includes
     other characters as 0-9. In this case, an error message is logged.
     */
    @Nullable
    private int[][] getActualWorldMapNumbersFrom(@NonNull final String resourceMapPath) {
        int[][] created = null;
        final List<String[]> worldMapRowContents = getWorldMapRowContents(resourceMapPath);

        if (worldMapRowContents != null && !worldMapRowContents.isEmpty()) {
            worldMapMaxRows = worldMapRowContents.size();
            worldMapMaxColumns = worldMapRowContents.get(1).length;
            created = getWorldMapAsIntArray(worldMapRowContents, worldMapMaxColumns);
        }
        return created;
    }

    @Nullable
    private List<String[]> getWorldMapRowContents(@NonNull final String resourceMapPath) {
        List<String[]> content = null;
        try (final InputStream is = this.getClass().getResourceAsStream(resourceMapPath))
        {
            if (is != null) {
                try (final InputStreamReader isr = new InputStreamReader(is);
                     final BufferedReader br = new BufferedReader(isr))
                {
                    content = new ArrayList<>();
                    String input;
                    while ((input = br.readLine()) != null) {
                        content.add(input.split(DELIMITER));
                    }
                }
            }
            else {
                LOGGER.log(Level.SEVERE, () -> RESOURCE_MAP_NOT_LOADED_MSG);
            }
        }
        catch (final IOException e) {
            LOGGER.log(Level.SEVERE, e::getMessage);
        }
        return content;
    }

    private int[][] getWorldMapAsIntArray(@NonNull final List<String[]> rows, int worldColumnCount) {

        final int[][] created = new int[worldColumnCount][rows.size()];
        int i = 0;
        for (final String[] row : rows) {
            int j = 0;
            for (final String elem : row) {
                int c = -1;
                if (isDigit(elem.charAt(0))) {
                    c = Integer.parseInt(elem);
                }
                if (c > -1) {
                    created[i][j] = c;
                }
                j++;
            }
            i++;
        }
        return created;
    }

    private boolean isDigit(final char c) {
        return c >= '0' && c <= '9';
    }

    private boolean indexExistsInTilesOfThisWorld(final int index, final int tilesArrayLength) {
        return index > -1 && index < tilesArrayLength;
    }

    public int getWorldMapMaxColumns() {
        return worldMapMaxColumns;
    }

    public int getWorldMapMaxRows() {
        return worldMapMaxRows;
    }

    @Nullable
    public Tile[][] getTileMap()
    {
        Tile[][] map = null;
        if (mapTileNumbers != null && tiles.length > 0) {
            map = new Tile[worldMapMaxRows][worldMapMaxColumns];
            // load map, line by line and add image for related number according world configuration
            int x = 0;
            int y = 0;
            for(int row = 0; row < worldMapMaxRows; row++, x++) {
                for(int column = 0; column < worldMapMaxColumns; column++, y++) {
                    final int tileNum = mapTileNumbers[row][column];
                    if (indexExistsInTilesOfThisWorld(tileNum, tiles.length)) {
                        map[row][column] = tiles[tileNum];
                    }
                    else
                    {
                        LOGGER.log(Level.WARNING, () -> TILE_NUM_NOT_INDEXED);
                    }
                }
            }
        }
        else {
            LOGGER.log(Level.SEVERE, () -> FAILURE_IN_MAP_INDEXING);
        }
        return map;
    }

    public void setMapTiles(@NonNull final Tile[] tilesOfThisWorld) {
        tiles =tilesOfThisWorld;
    }
}
