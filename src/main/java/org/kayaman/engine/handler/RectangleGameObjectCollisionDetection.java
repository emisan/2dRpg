package org.kayaman.engine.handler;

import lombok.NonNull;
import org.kayaman.entities.GameCharacter;
import org.kayaman.entities.GameObject;

import javax.annotation.Nullable;
import java.util.List;

public class RectangleGameObjectCollisionDetection {

    private final List<GameObject> gameObjects;

    public RectangleGameObjectCollisionDetection(@NonNull final List<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    @Nullable
    public GameObject getGameObjectColliedWith(@NonNull final GameCharacter gameCharacter)
    {
        for (GameObject gameObject : gameObjects) {
            if (gameObject != null && gameObject.getCollisionArea().intersects(gameCharacter.getCollisionArea())) {
                return gameObject;
            }
        }
        return null;
    }

    public void removeGameObject(@NonNull final GameObject gameObject) {
        gameObjects.remove(gameObject);
    }
}
