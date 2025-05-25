package org.kayaman.engine.handler.inventory;

import lombok.NonNull;
import org.kayaman.entities.GameObject;

import java.util.ArrayList;
import java.util.List;

public class ItemInventoryHandler {

    private static final int INCREMENT_AMOUNT = 1;
    private final List<ItemInventoryEntry> inventory;

    public ItemInventoryHandler() {
        inventory = new ArrayList<>();
    }

    public void addToInventory(@NonNull final GameObject gameObject) {
        final ItemInventoryEntry entry = inventory.stream()
                .filter(obj -> gameObject.getItemName().equalsIgnoreCase(obj.getItemName()))
                .findFirst().orElse(null);
        if (entry == null) {
            inventory.add(new ItemInventoryEntry(INCREMENT_AMOUNT, gameObject));
        }
        else {
            entry.setAmount(entry.getAmount() + INCREMENT_AMOUNT);
        }
    }

    public void removeFromInventoryOrDecrementAmount(@NonNull final ItemInventoryEntry entry) {
        if (entry.getAmount() > 1) {
            for (final ItemInventoryEntry e : this.inventory)
            {
                if (e.getItemName().equals(entry.getItemName()))
                {
                    final int amount = e.getAmount() - 1;
                    e.setAmount(amount);
                    break;
                }
            }
        }
        else {
            inventory.remove(entry);
        }
    }

    public List<ItemInventoryEntry> getInventory() {
        return inventory;
    }
}
