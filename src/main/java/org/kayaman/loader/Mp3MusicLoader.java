package org.kayaman.loader;

import javazoom.jl.player.Player;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Mp3MusicLoader extends Thread {

    private Player player;
    private final boolean shouldLoop;
    private final File file;

    /**
     * Constructor to by-pass MP3-File to handle.
     * <blockquote>
     *     An {@link IllegalArgumentException} will be thrown when the given file is not in a MP3-FileFormat.
     * </blockquote>
     * @param file instance of a {@link File} which points to a MP3-File resource.
     */
    public Mp3MusicLoader(@NotNull final File file, final boolean shouldLoop) {
        this.shouldLoop = shouldLoop;
        String fileName = null;
        if (file.isFile()) {
            fileName = file.getName();
        }
        if (fileName != null && fileName.endsWith(".mp3")) {
            this.file = file;
        }
        else {
            throw new IllegalArgumentException("Given File " + fileName + " must be a MP3-File.");
        }
    }

    private synchronized void playMediaFileName() {

        if (this.file != null) {
            try (final FileInputStream fis = new FileInputStream(this.file)) {
                player = new Player(fis);
                player.play();
                if (player.isComplete() && shouldLoop) {
                    playMediaFileName();
                }
            }
            catch (final Exception e) {
                Logger.getLogger(Mp3MusicLoader.class.getName()).log(Level.SEVERE, e::getMessage);
            }
        }
    }

    public void stopMedia() {
        player.close();
    }

    @Override
    public void run() {
        this.playMediaFileName();
    }
}
