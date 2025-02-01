package org.kayaman.loader;


import lombok.NonNull;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class SpriteLoader {

    private static final Logger LOGGER = Logger.getLogger(SpriteLoader.class.getName());

    private SpriteLoader() {
    }

    @NonNull
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

    @NonNull
    public static BufferedImage getScaledImage(@NonNull final BufferedImage image, final int bySize) {
        // pre-draw
        final BufferedImage scaledImage = new BufferedImage(bySize, bySize, image.getType());
        // what ever g2 draws will be saved in scaledImage
        final Graphics2D g2 = (Graphics2D) scaledImage.getGraphics();
        g2.drawImage(image, 0, 0, bySize, bySize, null);
        g2.dispose(); // otherwise painting on gamescreen will leave screen blank as we will invoke Graphics2D there too
        return scaledImage;
    }
}
