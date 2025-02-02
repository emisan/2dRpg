package org.kayaman.screen;

import lombok.NonNull;
import org.kayaman.engine.handler.RectangleGameObjectCollisionDetection;
import org.kayaman.entities.Player;
import org.kayaman.engine.GameLoopEngine;
import org.kayaman.engine.controls.GameScreenController;
import org.kayaman.engine.handler.RectangleTileCollisionDetector;
import org.kayaman.loader.WorldMapLoader;
import org.kayaman.scene.World;
import org.kayaman.scene.world.one.WorldOneGameObjects;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameScreen extends JPanel implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(GameScreen.class.getName());

    // image or sprite drawing size settings
    private int originalTileSize; // 16x16 pixel
    private int scaleTile;
    private int gpTileSize;

    // game screen settings
    private int maxScreenCols;
    private int maxScreenRows;
    private int screenWidth;
    private int screenHeight;

    // world map values for zoom in-out and for collision detection
    private int maxWorldColumns;

    private double zoomFactor;

    private transient Thread gameThread;
    private transient Player player;
    private transient World world;

    private final GameScreenController gameScreenController;

    public GameScreen(final int originalTileSize, final int scaleTile, final int maxScreenCols, final int maxScreenRows)
    {
        gameScreenController = new GameScreenController(this);
        gameScreenController.setMouseScrollSpeed(4);
        this.addKeyListener(gameScreenController);
        this.addMouseWheelListener(gameScreenController);
        setupGameField(originalTileSize, scaleTile , maxScreenCols, maxScreenRows);
        startGameThread();
    }

    private void setupGameField(int originalTileSize, int scale, int maxScreenCols, int maxScreenRows) {
        this.originalTileSize = originalTileSize;
        this.maxScreenCols = maxScreenCols;
        this.maxScreenRows = maxScreenRows;
        scaleTile = scale;
        gpTileSize = this.originalTileSize * scale;
        screenWidth = gpTileSize * maxScreenCols;
        screenHeight = gpTileSize * maxScreenRows;
        this.setFocusable(true); // to receive key inputs
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        initGameCharacters();
        initWorldMap();
    }

    private void initGameCharacters() {
        player = new Player(this);
        this.addKeyListener(player.getGameCharacterKeyboardController());
    }

    private void initWorldMap() {
        world = WorldMapLoader.loadWorldOne(this);
        world.setWorldGameObjects(WorldOneGameObjects.getGameObjects(getTileSize()));
        maxWorldColumns = world.getMaxWorldColumns();
        final int maxWorldWidth = gpTileSize * maxWorldColumns;
        final int maxWorldHeight = gpTileSize * world.getMaxWorldRows();
        initCollisionDetection(world, maxWorldWidth, maxWorldHeight);
    }

    private void initCollisionDetection(@NonNull final World onWorld,
                                        final int maxWorldWidth,
                                        final int maxWorldHeight)
    {
        final RectangleTileCollisionDetector tileCollisionDetector =
                new RectangleTileCollisionDetector(getWorld().getWorldMap(), maxWorldWidth, maxWorldHeight);
        tileCollisionDetector.prepareCollisionCheckCoordinates(player);
        player.setCollisionDetector(tileCollisionDetector);

        final RectangleGameObjectCollisionDetection gameObjectsCollisionDetector =
                new RectangleGameObjectCollisionDetection(onWorld.getWorldGameObjects());
        player.setGameObjectsCollisionDetector(gameObjectsCollisionDetector);
    }

    public Player getPlayer() {
        return player;
    }

    public void printScreenConstraints() {
        final String nl = "\n";
        String sb = "originalTileSize:" + originalTileSize + nl +
                "scaleTile:" + scaleTile + nl +
                "tileSize:" + gpTileSize + nl +
                "maxScreenCols:" + maxScreenCols + nl +
                "maxScreenRows:" + maxScreenRows + nl +
                "screenWidth:" + screenWidth + nl +
                "maxScreenHeight:" + screenHeight + nl;
        LOGGER.log(Level.INFO, () -> sb);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        GameLoopEngine.setGameScreen(this);
        gameThread.start();
    }

    public void stopGame() {
        gameThread = null;
    }

    //TODO
    /**
     * Actually when zooming this destroys rectangle collision detection. Need to fix this.
     * @param byFactor zooming factor
     */
    public void zoomInOut(final int byFactor) {
        final int oldWorldWidth = gpTileSize * maxWorldColumns;
        // if only this is set, the comments in PlayerController-keyPressed will effect rendering
        gpTileSize += byFactor;
        zoomFactor = byFactor;
        // beneath code must be commented to let the effect happen otherwise we'll have correct zoom in-out
        final int newWorldWidth = gpTileSize * maxWorldColumns;
        player.setMovementSpeed((double) newWorldWidth /600);
        final double worldFactor = (double) newWorldWidth / oldWorldWidth;
        final double playerXPosOnWorld = player.getXPosOnWorld() * worldFactor;
        final double playerYPosOnWorld = player.getYPosOnWorld() * worldFactor;
        player.setXPosOnWorld(playerXPosOnWorld);
        player.setYPosOnWorld(playerYPosOnWorld);
    }

    public void update() {
        // needed to update maybe changed tileSize by zooming
        getWorld().setTileSize(gpTileSize);
        getPlayer().setTileSize(gpTileSize);
        // update the characters always, so that changed states are updated, like tileSize or collision, etc.
        getPlayer().update();
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        final long before = System.nanoTime();
        getWorld().drawMap(graphics2D);
        getPlayer().draw(graphics2D);
        final long after = System.nanoTime();
        drawSystemMeasurements(graphics2D, before, after);
        graphics2D.dispose(); // graphics context and release any system resources that it is using
    }

    private void drawSystemMeasurements(@NonNull final Graphics2D graphics2D ,long before, long after) {
        if (gameScreenController.isShowDrawingTime()) {
            after = System.nanoTime();
            graphics2D.setColor(Color.GREEN);
            graphics2D.drawString("System measurements:", 10, 100);
            graphics2D.drawString("===================", 10, 110);
            graphics2D.drawString("drawing time: " + (after - before)/1000000000.0, 10, 125);
//            LOGGER.log(Level.INFO, "drawing time: " + (after - before/1000000000.0));
        }
    }

    public Thread getThread() {
        return gameThread;
    }

    public int getTileSize() {
        return gpTileSize;
    }

    public void setWorldMap(@NonNull final World world) {
        this.world = world;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public int getWidth() {
        return screenWidth;
    }

    @Override
    public int getHeight() {
        return screenHeight;
    }

    @Override
    public void run() {
        GameLoopEngine.deltaAccumulaterGameLoop();
    }
}
