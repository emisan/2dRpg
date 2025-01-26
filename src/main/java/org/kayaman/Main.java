package org.kayaman;

import org.kayaman.screen.GameScreen;

import javax.swing.*;

public class Main extends JFrame {

    public Main() {
        this.init();
    }

    private void init() {
        final GameScreen gameScreen = new GameScreen(32, 2,  18, 13);
//        gameScreen.printScreenConstraints();
        getContentPane().add(gameScreen);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("My first 2D Adventure");
        setVisible(true);
        pack();
        // invoke frame centering after pack() as it computes the size of the window,
        // which is required to perform the centering calculation correctly
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}