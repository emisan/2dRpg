package org.kayaman.engine.handler.inventory;

import lombok.NonNull;
import org.kayaman.entities.GameObject;

import javax.swing.JList;
import javax.swing.JWindow;
import java.awt.Color;
import java.awt.ScrollPane;
import java.util.List;

public class ItemInventoryWindow extends JWindow {

    private static final int OFFSET_X_LOCATION = 10;
    private static final int OFFSET_Y_LOCATION = 20;

    private ItemInventoryListModel model;
    private final JList<ItemInventoryEntry> inventoryListComponent;
    private final transient ItemInventoryHandler itemInventoryHandler;
    int visibilityCounter;

    public ItemInventoryWindow() {
        visibilityCounter = 0;
        setSize(400, 400);
        itemInventoryHandler = new ItemInventoryHandler();
        model = new ItemInventoryListModel();
        inventoryListComponent = new JList<>(model);
        inventoryListComponent.setCellRenderer(new ItemInventoryRenderer());
        inventoryListComponent.setBackground(Color.BLACK);
        inventoryListComponent.setForeground(Color.WHITE);
        final ScrollPane scrollPane = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
        scrollPane.add(inventoryListComponent);
        getContentPane().add(scrollPane);
        setOpacity(0.8f);
        validate();
    }

    public void updateLocation(final int screenX, final int screenY) {
        setLocation(screenX - OFFSET_X_LOCATION, screenY + OFFSET_Y_LOCATION);
        validate();
    }

    public void openOrCloseInventory(final boolean state) {
        this.setVisible(state);
    }

    private ItemInventoryListModel updateListModelWithInventory(@NonNull final List<ItemInventoryEntry> inventory) {
        model.clear();
        for (ItemInventoryEntry entry : inventory) {
            model.addElement(entry);
        }
        return model;
    }

    public void updateListModelWith(@NonNull final GameObject gameObject) {
        itemInventoryHandler.addToInventory(gameObject);
        model = updateListModelWithInventory(itemInventoryHandler.getInventory());
        inventoryListComponent.setModel(model);
    }
}
