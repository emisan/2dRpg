package org.kayaman.controls;

import org.kayaman.screen.GameScreen;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class GameLoopEngine {

    private static final Logger LOGGER = Logger.getLogger(GameLoopEngine.class.getName());

    private static final int FRAMES_PER_SECOND = 60;
    private static final double ONE_BILLION_NANOSECEONDS = 1000000000.0;
    private static final double DRAW_INTERVAL = ONE_BILLION_NANOSECEONDS/FRAMES_PER_SECOND;
    private static GameScreen gameScreen;

    private GameLoopEngine() {
    }

    public static void setGameScreen(final GameScreen parent) {
        gameScreen = parent;
    }

    public static void deltaAccumulaterGameLoop() {
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        // show frames per seconds, uncomment to see, not part of game loop, leave commented if not used
//        long timer = 0;
//        int drawCount = 0;

        while (gameScreen.getThread() != null) {
            currentTime = System.nanoTime();
            double passedTime = currentTime - lastTime;
            delta += passedTime / DRAW_INTERVAL;
            lastTime = currentTime;
            // only used for output of fps , otherwhise leave commented
            //timer += passedTime;
            if (delta >= 1) {
                gameScreen.update();
                gameScreen.repaint();
                delta--;
                // only used for output of fps , otherwhise leave commented
                //drawCount++;
            }
            // uncomment for FPS output checks, comment if not needed
//            if (timer >= ONE_BILLION_NANOSECEONDS) {
//                LOGGER.log(Level.INFO, "FPS: " + drawCount);
//                drawCount = 0;
//                timer = 0;
//            }
        }
    }
}
