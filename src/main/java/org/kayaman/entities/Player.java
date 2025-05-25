package org.kayaman.entities;

import lombok.NonNull;
import org.kayaman.engine.GameEngine;
import org.kayaman.engine.handler.RectangleGameObjectCollisionDetection;
import org.kayaman.engine.handler.RectangleTileCollisionDetector;
import org.kayaman.engine.controls.GameCharacterMoveController;
import org.kayaman.loader.SpriteLoader;
import org.kayaman.screen.GameScreen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.kayaman.engine.controls.GameCharacterMoveController.LAST_DIRECTION_DOWN;
import static org.kayaman.engine.controls.GameCharacterMoveController.LAST_DIRECTION_LEFT;
import static org.kayaman.engine.controls.GameCharacterMoveController.LAST_DIRECTION_RIGHT;
import static org.kayaman.engine.controls.GameCharacterMoveController.LAST_DIRECTION_UP;

public class Player implements GameCharacter {

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
    private RectangleTileCollisionDetector playCollisionDetection;
    private RectangleGameObjectCollisionDetection gameObjectCollisionDetection;

    private boolean canMove;

    private GameCharacterMoveController moveController;

    private final GameScreen gameScreen;

    public Player(@NonNull GameScreen gameScreen)
    {
        this.gameScreen = gameScreen;
        setDefaults();
        initMovementImages();
    }

    private void setDefaults() {
        upCounter = -1;
        leftCounter = -1;
        rightCounter = -1;
        downCounter = -1;
        imageUpdateSpeed = 12;
        imageUpdateCounter = 0;
        canMove = true;
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
        moveController = new GameCharacterMoveController(this);
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
        this.actualImage = getDownMovements()[0]; // default stand still, look forward
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
        final boolean moves = moveController.getUpPressed() && moveController.canMove();
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
        final boolean moves = moveController.getDownPressed() && moveController.canMove();
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
        final boolean moves = moveController.getLeftPressed() && moveController.canMove();
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
        final boolean moves = moveController.getRightPressed() && moveController.canMove();
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

    private void setImageStandingStillRelatedToDirection(@NonNull final String direction) {
        BufferedImage image = null;
        switch (direction) {
            case LAST_DIRECTION_UP:
                image = getUpMovements()[0];
                break;
            case LAST_DIRECTION_DOWN:
                image = getDownMovements()[0];
                break;
            case LAST_DIRECTION_LEFT:
                image = getLeftMovements()[0];
                break;
            case LAST_DIRECTION_RIGHT:
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
        updateMovementAndCheckCollisions();
    }

    private void updateMovementAndCheckCollisions() {

        final boolean leftPressed = moveController.getLeftPressed();
        final boolean rightPressed = moveController.getRightPressed();
        final boolean upPressed = moveController.getUpPressed();
        final boolean downPressed = moveController.getDownPressed();
        final String direction = moveController.getLastDirection();

        final boolean hasWorldTileCollision = playCollisionDetection.hasCollisionOnWorldTiles(this);
        final boolean hasInteraction = hasGameObjectCollision() || hasObjectInteraction();
//        System.out.println("World tile collision " + hasWorldTileCollision + ", Object collision " + hasGameObject);

        this.canMove(true);
        if (hasWorldTileCollision) {
            this.canMove(false);
        }
        if (hasInteraction) {
            LOGGER.log(Level.INFO, "Object collision");
            this.canMove(false);
        }
//        System.out.println("Can move " + canMove);
        moveController.hasMovement(isMoving());

        if (leftPressed && isMoving()) {
            updateLeftMovementImages();
            moveController.setLastDirection(LAST_DIRECTION_LEFT);
        }
        else if (rightPressed && isMoving()) {
            updateRightMovementImages();
            moveController.setLastDirection(LAST_DIRECTION_RIGHT);
        }
        else if (upPressed && isMoving()) {
            updateUpMovementImages();
            moveController.setLastDirection(LAST_DIRECTION_UP);
        }
        else if (downPressed && isMoving()) {
            updateDownMovementImages();
            moveController.setLastDirection(LAST_DIRECTION_DOWN);
        }
        else {
            moveController.setLastDirection(GameCharacterMoveController.STAND_STILL);
            setImageStandingStillRelatedToDirection(direction);
        }
    }

    private boolean hasGameObjectCollision() {
        boolean state = false;
        final GameObject gameObject = gameObjectCollisionDetection.getGameObjectColliedWith(this);
        if (gameObject != null && gameObject.isCollectible()) {
            state = gameObjectCollisionDetection.pickUp(gameScreen,gameObject);
            LOGGER.log(Level.INFO, "picked up [{0}]", state);
        }
//        else if (gameObject instanceof Door) {
//            state = gameObjectCollisionDetection.doorOpened(gameScreen, (Door)gameObject);
//            System.out.println("door opened" + state);
//        }
        return state;
    }

    private boolean hasObjectInteraction() {
        boolean state = false;
        final GameObject gameObject = gameObjectCollisionDetection.getGameObjectColliedWith(this);
        if (gameObject instanceof Door) {
            state = gameObjectCollisionDetection.doorOpened(gameScreen, (Door)gameObject);
            LOGGER.log(Level.INFO, "door opened [{0}]", state);
        }
        return state;
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
        GameEngine.drawFasterByScalingImage(
                graphics2d, getActualImage(), getTileSize(), getXPosOnScreen(), getYPosOnScreen());
//        drawCollisionArea(g2);
    }

    @Override
    public void canMove(boolean state) {
        canMove = state;
    }

    @Override
    public boolean isMoving() {
        return this.canMove;
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
            final GameCharacterMoveController gameCharacterMoveController)
    {
        this.moveController = gameCharacterMoveController;
    }

    @Override
    public GameCharacterMoveController getGameCharacterKeyboardController() {
        return moveController;
    }

    @Override
    public void setCollisionDetector(@NonNull final RectangleTileCollisionDetector collisionDetector) {
        playCollisionDetection = collisionDetector;
    }

    @Override
    public RectangleTileCollisionDetector getCollisionDetector() {
        return this.playCollisionDetection;
    }

    @Override
    public void setGameObjectsCollisionDetector(@NonNull final RectangleGameObjectCollisionDetection collisionDetector)
    {
        gameObjectCollisionDetection = collisionDetector;
    }

    @Override
    public RectangleGameObjectCollisionDetection getGameObjectsCollisionDetector() {
        return gameObjectCollisionDetection;
    }
}
