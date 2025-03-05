package org.kayaman.loader;

import javafx.scene.media.MediaPlayer;
import lombok.NonNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SoundLoader {

    private static final Logger LOGGER = Logger.getLogger(SoundLoader.class.getName());

    private static final String SOUND_FILES_PATH = "sound";

    private String soundFolderPath;

    private Mp3MusicLoader mp3MusicLoader;

    public SoundLoader() {
        try (final InputStream is = SoundLoader.class.getClassLoader().getResourceAsStream(SOUND_FILES_PATH)) {
            URL folderPath = null;
            File folder = null;
            if (is != null) {
                folderPath = SoundLoader.class.getClassLoader().getResource(SOUND_FILES_PATH);
            }
            if (folderPath != null) {
                folder = new File(folderPath.getFile());
            }
            if (folder != null) {
                soundFolderPath = folderPath.getPath();
            }
            else {
                throw new NullPointerException("Sound resource folder could not be found!");
            }
        }
        catch (final IOException e) {
            LOGGER.log(Level.SEVERE, e::getMessage);
        }
    }

    public void playMusic(@NonNull final String fileName, final boolean shouldLoop) {

        final File file = new File(soundFolderPath + File.separator + fileName);
        if (file.isFile()) {
            mp3MusicLoader = new Mp3MusicLoader(file, shouldLoop);
            mp3MusicLoader.start();
        }
    }

    public void stopPlayingMusic() {
        mp3MusicLoader.interrupt();
    }

    public Mp3MusicLoader getMediaPlayer()
    {
        return mp3MusicLoader;
    }
}
