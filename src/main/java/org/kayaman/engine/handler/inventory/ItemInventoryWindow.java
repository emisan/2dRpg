package org.kayaman.engine.handler.inventory;

import lombok.NonNull;
import org.kayaman.screen.GameScreen;

import javax.swing.JList;
import javax.swing.JWindow;
import java.awt.Color;
import java.awt.Point;
import java.awt.ScrollPane;
import java.util.List;

public class ItemInventoryWindow extends JWindow {

    private ItemInventoryListModel model;
    private final JList<ItemInventoryEntry> inventory;
    int visibilityCounter;

    public ItemInventoryWindow(@NonNull GameScreen gameScreen) {
        visibilityCounter = 0;
        setSize(400, 400);
        model = new ItemInventoryListModel();
        inventory = new JList<>(model);
        inventory.setCellRenderer(new ItemInventoryRenderer());
        inventory.setBackground(Color.BLACK);
        inventory.setForeground(Color.WHITE);
        final ScrollPane scrollPane = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
        scrollPane.add(inventory);
        setLocationRelativeTo(gameScreen);
        getContentPane().add(scrollPane);
        setOpacity(0.8f);
        validate();
    }

    public void updateLocation(@NonNull final GameScreen relativeToScreen) {
        final Point location = relativeToScreen.getLocation();
        setLocation(location.x + relativeToScreen.getWidth()/4,
                location.y + relativeToScreen.getHeight()/4);
        validate();
    }

    public void openInventory() {
        this.setVisible(true);
    }

    public void initList(@NonNull final List<ItemInventoryEntry> list) {
        list.forEach(model::add);
        inventory.updateUI();
    }
}
