package org.kayaman.engine.controls;

import lombok.NonNull;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameCharacterKeyboardController extends KeyAdapter {

    public static final String STAND_STILL = "stand_still";
    public static final String LAST_DIRECTION_UP = "up";
    public static final String LAST_DIRECTION_DOWN = "down";
    public static final String LAST_DIRECTION_RIGHT = "RIGHT";
    public static final String LAST_DIRECTION_LEFT = "left";


    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;

    private String lastDirection;

    public GameCharacterKeyboardController() {
        lastDirection = STAND_STILL;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            upPressed = true;
        }
        else if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        else if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downPressed = true;
        }
        else if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            lastDirection = LAST_DIRECTION_UP;
            upPressed = false;
        }
        else if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            lastDirection = LAST_DIRECTION_LEFT;
            leftPressed = false;
        }
        else if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            lastDirection = LAST_DIRECTION_DOWN;
            downPressed = false;
        }
        else if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            lastDirection = LAST_DIRECTION_RIGHT;
            rightPressed = false;
        }
    }

    @NonNull
    public String getLastDirection() {
        return lastDirection;
    }

    public void setLastDirection(@NonNull final String lastDirection) {
        this.lastDirection = lastDirection;
    }

    public boolean getUpPressed() {
        return upPressed;
    }

    public boolean getDownPressed() {
        return downPressed;
    }

    public boolean getLeftPressed() {
        return leftPressed;
    }

    public boolean getRightPressed() {
        return rightPressed;
    }
}
