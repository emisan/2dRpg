package org.kayaman.loader;

import lombok.NonNull;
import org.kayaman.screen.GameScreen;
import org.kayaman.scene.World;
import org.kayaman.scene.world.one.WorldOne;

/**
 * Gets the world maps.
 */
public final class WorldMapLoader {

    private WorldMapLoader() {
    }

    public static World loadWorldOne(@NonNull final GameScreen gameScreen)
    {
        return new WorldOne(gameScreen);
    }
}
