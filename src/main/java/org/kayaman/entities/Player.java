package org.kayaman.entities;

import lombok.NonNull;
import org.kayaman.engine.ImageProcessingPerformance;
import org.kayaman.engine.RectangleCollisionDetector;
import org.kayaman.controls.GameCharacterKeyboardController;
import org.kayaman.loader.SpriteLoader;
import org.kayaman.screen.GameScreen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

public class Player implements GameCharacter, ImageProcessingPerformance {

    private static final Logger LOGGER = Logger.getLogger(Player.class.getName());

    private BufferedImage actualImage;
    private BufferedImage[] upMovements;
    private BufferedImage[] downMovements;
    private BufferedImage[] leftMovements;
    private BufferedImage[] rightMovements;

    private int upCounter;
    private int downCounter;
    private int leftCounter;
    private int rightCounter;

    private int tileSize;

    private double xPosOnWorld;
    private double yPosOnWorld;
    private int xPosOnScreen;
    private int yPosOnScreen;

    private double movementSpeed;
    private int imageUpdateCounter;
    private int imageUpdateSpeed;

    private Rectangle collisionArea;

    private RectangleCollisionDetector playCollisionDetection;
    private GameCharacterKeyboardController gameCharacterKeyboardController;

    public Player(@NonNull GameScreen gameScreen)
    {
        setDefaults();
        gameCharacterKeyboardController = new GameCharacterKeyboardController();
        tileSize = gameScreen.getTileSize();
        // where player starts on world x-index and y-index world map array coordinates multiplied by tileSize
        // this affects moving through the world x-y- index coordinates
        xPosOnWorld = 2 * (double)tileSize;
        yPosOnWorld = 2 * (double)tileSize;
        // player is drawn on screen center
        xPosOnScreen = gameScreen.getWidth()/2 - tileSize/2;
        yPosOnScreen = gameScreen.getHeight()/2 - tileSize/2;
        movementSpeed = 4;
        collisionArea = new Rectangle(tileSize/4, tileSize/2, tileSize/2, tileSize/2);
        initMovementImages();
        this.actualImage = getDownMovements()[0]; // default stand still, look forward
    }

    private void setDefaults() {
        upCounter = -1;
        leftCounter = -1;
        rightCounter = -1;
        downCounter = -1;
        imageUpdateSpeed = 12;
        imageUpdateCounter = 0;
    }

    private void initMovementImages() {
        final String resourceFolder = "/sprites/player/";
        downMovements = new BufferedImage[]
                {
                SpriteLoader.getSprite(resourceFolder + "PlayerMain_n_1.png"),
                SpriteLoader.getSprite(resourceFolder + "PlayerMain_n_2.png"),
                SpriteLoader.getSprite(resourceFolder + "PlayerMain_n_3.png")
                };
        upMovements = new BufferedImage[]
                {
                SpriteLoader.getSprite(resourceFolder + "PlayerMain_s_1.png"),
                SpriteLoader.getSprite(resourceFolder + "PlayerMain_s_2.png"),
                SpriteLoader.getSprite(resourceFolder + "PlayerMain_s_3.png")
                };
        leftMovements = new BufferedImage[]
                {
                SpriteLoader.getSprite(resourceFolder + "PlayerMain_w_1.png"),
                SpriteLoader.getSprite(resourceFolder + "PlayerMain_w_2.png"),
                SpriteLoader.getSprite(resourceFolder + "PlayerMain_w_3.png")
                };
        rightMovements = new BufferedImage[]
                {
                SpriteLoader.getSprite(resourceFolder + "PlayerMain_e_1.png"),
                SpriteLoader.getSprite(resourceFolder + "PlayerMain_e_2.png"),
                SpriteLoader.getSprite(resourceFolder + "PlayerMain_e_3.png")
                };
    }

    private void setActualImage(@NonNull final BufferedImage actualImage) {
        this.actualImage = actualImage;
    }

    @NonNull
    private BufferedImage getActualImage() {
        return this.actualImage;
    }

    private BufferedImage[] getUpMovements() {
        return this.upMovements;
    }

    private BufferedImage[] getDownMovements() {
        return this.downMovements;
    }

    private BufferedImage[] getLeftMovements() {
        return this.leftMovements;
    }

    private BufferedImage[] getRightMovements() {
        return this.rightMovements;
    }

    private void updateUpMovementImages() {
        final BufferedImage[] movements = getUpMovements();
        final int movementEnd = movements.length;
        final boolean moves = gameCharacterKeyboardController.getUpPressed();
        if (moves && upCounter < movementEnd) {
            upCounter++;
        }
        if (upCounter == movementEnd && moves) {
            upCounter = 0;
        }
        imageUpdateCounter++;
        if (imageUpdateCounter > imageUpdateSpeed) { // program runs FPS = 60, when 10 reached, pause so that smooth images are shown
            this.setActualImage(movements[upCounter]);
            imageUpdateCounter = 0;
        }
    }

    public void updateDownMovementImages() {
        final BufferedImage[] movements = getDownMovements();
        final int movementEnd = movements.length;
        final boolean moves = gameCharacterKeyboardController.getDownPressed();
        if (moves && downCounter < movementEnd) {
            downCounter++;
        }
        if (downCounter == movementEnd && moves) {
            downCounter = 0;
        }
        imageUpdateCounter++;
        if (imageUpdateCounter > imageUpdateSpeed) { // program runs FPS = 60, when 10 reached, pause so that smooth images are shown
            this.setActualImage(movements[downCounter]);
            imageUpdateCounter = 0;
        }
    }

    private void updateLeftMovementImages() {
        final BufferedImage[] movements = getLeftMovements();
        final int movementEnd = movements.length;
        final boolean moves = gameCharacterKeyboardController.getLeftPressed();
        if (moves && leftCounter < movementEnd) {
            leftCounter++;
        }
        if (leftCounter == movementEnd && moves) {
            leftCounter = 0;
        }
        imageUpdateCounter++;
        if (imageUpdateCounter > imageUpdateSpeed) { // program runs FPS = 60, when 10 reached, pause so that smooth images are shown
            this.setActualImage(movements[leftCounter]);
            imageUpdateCounter = 0;
        }
    }

    private void updateRightMovementImages() {
        final BufferedImage[] movements = getRightMovements();
        final int movementEnd = movements.length;
        final boolean moves = gameCharacterKeyboardController.getRightPressed();
        if (moves && rightCounter < movementEnd) {
            rightCounter++;
        }
        if (rightCounter == movementEnd && moves) {
            rightCounter = 0;
        }
        imageUpdateCounter++;
        if (imageUpdateCounter > imageUpdateSpeed) { // program runs FPS = 60, when 10 reached, pause so that smooth images are shown
            this.setActualImage(movements[rightCounter]);
            imageUpdateCounter = 0;
        }
    }

    private void setUpdateToFirstImageStandingStillRelatedToDirection(@NonNull final String direction) {
        BufferedImage image = null;
        switch (direction) {
            case GameCharacterKeyboardController.LAST_DIRECTION_UP:
                image = getUpMovements()[0];
                break;
            case GameCharacterKeyboardController.LAST_DIRECTION_DOWN:
                image = getDownMovements()[0];
                break;
            case GameCharacterKeyboardController.LAST_DIRECTION_LEFT:
                image = getLeftMovements()[0];
                break;
            case GameCharacterKeyboardController.LAST_DIRECTION_RIGHT:
                image = getRightMovements()[0];
                break;
            default:
                break;
        }
        if (image != null) {
            setActualImage(image);
        }
    }

    public void update() {
        updateMovement();
    }

    private void updateMovement() {
        final boolean leftPressed = gameCharacterKeyboardController.getLeftPressed();
        final boolean rightPressed = gameCharacterKeyboardController.getRightPressed();
        final boolean upPressed = gameCharacterKeyboardController.getUpPressed();
        final boolean downPressed = gameCharacterKeyboardController.getDownPressed();
        final String direction = gameCharacterKeyboardController.getLastDirection();

        final boolean collision = playCollisionDetection.hasPlayerCollisionOnWorldTiles(this);

//        if (!leftPressed && !rightPressed && !downPressed && !upPressed)
//        {
//            gameCharacterKeyboardController.setLastDirection(GameCharacterKeyboardController.STAND_STILL);
//            setUpdateToFirstImageStandingStillRelatedToDirection(direction);
//        }
        if (leftPressed && !collision) {
            updateLeftMovementImages();
            gameCharacterKeyboardController.setLastDirection(GameCharacterKeyboardController.LAST_DIRECTION_LEFT);
        }
        else if (rightPressed && !collision) {
            updateRightMovementImages();
            gameCharacterKeyboardController.setLastDirection(GameCharacterKeyboardController.LAST_DIRECTION_RIGHT);
        }
        else if (upPressed && !collision) {
            updateUpMovementImages();
            gameCharacterKeyboardController.setLastDirection(GameCharacterKeyboardController.LAST_DIRECTION_UP);
        }
        else if (downPressed && !collision) {
            updateDownMovementImages();
            gameCharacterKeyboardController.setLastDirection(GameCharacterKeyboardController.LAST_DIRECTION_DOWN);
        }
        else {
            gameCharacterKeyboardController.setLastDirection(GameCharacterKeyboardController.STAND_STILL);
            setUpdateToFirstImageStandingStillRelatedToDirection(direction);
        }
    }

    private void drawCollisionArea(@NonNull final Graphics2D g) {
        g.setColor(Color.RED);
        final Rectangle collArea = getCollisionArea();
        collArea.x = getXPosOnScreen() - (int)getXPosOnWorld();
        collArea.y = getYPosOnScreen() - (int)getYPosOnWorld();
        g.drawRect(collArea.x, collArea.y, collArea.width, collArea.height);
    }

    @Override
    public void draw(@NonNull final Graphics2D graphics2d) {
        drawFasterByScalingImage(graphics2d, getActualImage(), getTileSize(), getXPosOnScreen(), getYPosOnScreen());
//        drawCollisionArea(g2);
    }

    @Override
    public void drawFasterByScalingImage(@NonNull final Graphics2D g2,
                                         @NonNull final  BufferedImage image,
                                         final int byScale,
                                         final int screenXPos,
                                         final int screenYPos)
    {
        g2.drawImage(SpriteLoader.getScaledImage(getActualImage(), getTileSize()), screenXPos, screenYPos, null);
    }

    @Override
    public void setXPosOnWorld(final double xPosOnWorld) {
        this.xPosOnWorld = xPosOnWorld;
    }

    @Override
    public double getXPosOnWorld() {
        return xPosOnWorld;
    }

    @Override
    public void setYPosOnWorld(final double yPosOnWorld) {
        this.yPosOnWorld = yPosOnWorld;
    }

    @Override
    public double getYPosOnWorld() {
        return yPosOnWorld;
    }

    @Override
    public void setXPosOnScreen(final int xPosOnScreen) {
        this.xPosOnScreen = xPosOnScreen;
    }

    @Override
    public int getXPosOnScreen() {
        return xPosOnScreen;
    }

    @Override
    public void setYPosOnScreen(final int yPosOnScreen) {
        this.yPosOnScreen = yPosOnScreen;
    }

    @Override
    public int getYPosOnScreen() {
        return yPosOnScreen;
    }

    @Override
    public void setTileSize(final int tileSize) {
        this.tileSize = tileSize;
    }

    @Override
    public int getTileSize() {
        return tileSize;
    }

    @Override
    public void setMovementSpeed(final double movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    @Override
    public double getMovementSpeed() {
        return movementSpeed;
    }

    @Override
    public void setCollisionArea(final Rectangle collisionArea) {
        this.collisionArea = collisionArea;
    }

    @Override
    public Rectangle getCollisionArea() {
        return collisionArea;
    }

    @Override
    public void setGameCharacterKeyboardController(
            final GameCharacterKeyboardController gameCharacterKeyboardController)
    {
        this.gameCharacterKeyboardController = gameCharacterKeyboardController;
    }

    @Override
    public GameCharacterKeyboardController getGameCharacterKeyboardController() {
        return gameCharacterKeyboardController;
    }

    @Override
    public void setCollisionDetector(@NonNull final RectangleCollisionDetector collisionDetector) {
        this.playCollisionDetection = collisionDetector;
    }

    @Override
    public RectangleCollisionDetector getCollisionDetector() {
        return this.playCollisionDetection;
    }
}
