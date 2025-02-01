package org.kayaman.controls;

import lombok.NonNull;
import org.kayaman.screen.GameScreen;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class GameScreenController implements KeyListener, MouseListener, MouseWheelListener {

    private int mouseScrollSpeed;
    private final GameScreen gameScreen;
    private boolean showDrawingTime;

    public GameScreenController(@NonNull final GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void setMouseScrollSpeed(final int mouseScrollSpeed) {
        this.mouseScrollSpeed = mouseScrollSpeed;
    }
    @Override
    public void keyTyped(KeyEvent e) {
        // actually nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_Q) {
            // nice effect: when keyPressed hold this will zoom tileSize using images zoom-in endlessly, can be used to
            // make player jump like rick dangerous when hit or to raise the player up in the sky or down:)
            //
            // if zoomInOut-method of game screen is part of gameScreen-updateMovement only one time pressing is needed
            gameScreen.zoomInOut(1);
        }
        else if (code == KeyEvent.VK_E) {
            // nice effect: if only placed in keyPressed this will zoom out endlessly when keyPressed hold
            // make player jump like rick dangerous when hit or to raise the player up in the sky or down:)
            // also: if player is zoomed out so far that it is reaching tileSize = 0 and moving negative
            // the image of the player is flipped vertically and zoom in appears making tileSize greater again
            //
            //  if zoomInOut-method of game screen is part of gameScreen-updateMovement only one time pressing is needed
            gameScreen.zoomInOut(-1);
        }
        else if (code == KeyEvent.VK_T) {
            if (!showDrawingTime) {
                showDrawingTime = true;
            }
            else {
                showDrawingTime = false;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // actually nothing to do
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // actually nothing
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // actually nothing
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // actually nothing
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // actually nothing
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // actually nothing
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int zoom = e.getWheelRotation();
        if (zoom < 0) {
            // mouse wheel up, zoom in
            zoom += mouseScrollSpeed;
        }
        else {
            // zoom out
            zoom -= mouseScrollSpeed;
        }
        gameScreen.zoomInOut(zoom);
    }

    public boolean isShowDrawingTime() {
        return showDrawingTime;
    }
}
