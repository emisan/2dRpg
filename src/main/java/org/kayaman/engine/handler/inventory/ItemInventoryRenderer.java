package org.kayaman.engine.handler.inventory;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import java.awt.Color;
import java.awt.Component;

public class ItemInventoryRenderer extends JLabel implements ListCellRenderer<ItemInventoryEntry> {

    public ItemInventoryRenderer() {
        setOpaque(true);
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends ItemInventoryEntry> list,
                                                  ItemInventoryEntry value,
                                                  int index,
                                                  boolean isSelected,
                                                  boolean cellHasFocus)
    {
        setText(value.getItemName() + " : " + value.getAmount());
        setIcon(new ImageIcon(value.getGameObject().getImage()));
        return this;
    }
}
