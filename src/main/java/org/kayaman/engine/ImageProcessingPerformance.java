package org.kayaman.engine;

import lombok.NonNull;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public interface ImageProcessingPerformance {
    void drawFasterByScalingImage(@NonNull final Graphics2D g2,
                                  @NonNull final BufferedImage image,
                                  final int byScale,
                                  final int screenXPos,
                                  final int screenYPos);
}
