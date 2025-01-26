package org.kayaman.loader;


import lombok.NonNull;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class SpriteLoader {

    private static final Logger LOGGER = Logger.getLogger(SpriteLoader.class.getName());

    private SpriteLoader() {
    }

    @Nullable
    public static BufferedImage getSprite(@NonNull final String resourcePath) {
        BufferedImage image = null;
        try (final InputStream imageSrc = SpriteLoader.class.getResourceAsStream(resourcePath)) {
            if (imageSrc != null) {
                image = ImageIO.read(imageSrc);
            }
            else {
                final String msg = String.format("Given sprite name path %s can not be found in resource folder %s!",
                        resourcePath, "sprites");
                throw new IllegalArgumentException(msg);
            }
        }
        catch (final IOException e) {
            LOGGER.log(Level.SEVERE, e::getMessage);
        }
        return image;
    }
}
