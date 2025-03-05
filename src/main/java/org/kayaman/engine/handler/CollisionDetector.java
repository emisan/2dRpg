package org.kayaman.engine.handler;

import lombok.NonNull;
import org.kayaman.entities.GameCharacter;

public interface CollisionDetector {
    void prepareCollisionCheckCoordinates(@NonNull final GameCharacter gameCharacter);
}
