package at.hackertreffreutte.mup.toolreplenisher;

import at.hackertreffreutte.mup.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

public class ToolBreakListener implements Listener {

    public boolean isReplenishAble(ItemStack item) {
        switch (item.getType()) {

            case DIAMOND_PICKAXE:
            case GOLDEN_PICKAXE:
            case IRON_PICKAXE:
            case STONE_PICKAXE:
            case WOODEN_PICKAXE:

            case DIAMOND_SHOVEL:
            case GOLDEN_SHOVEL:
            case IRON_SHOVEL:
            case STONE_SHOVEL:
            case WOODEN_SHOVEL:

            case DIAMOND_AXE:
            case GOLDEN_AXE:
            case IRON_AXE:
            case STONE_AXE:
            case WOODEN_AXE:

            case DIAMOND_SWORD:
            case GOLDEN_SWORD:
            case IRON_SWORD:
            case STONE_SWORD:
            case WOODEN_SWORD:

            case DIAMOND_HOE:
            case GOLDEN_HOE:
            case IRON_HOE:
            case STONE_HOE:
            case WOODEN_HOE:

            case SHIELD:
            case SHEARS:
            case BOW:
            case CROSSBOW:
            case FISHING_ROD:
                return true;

            default:
                return false;
        }
    }

    @EventHandler
    public void onPlayerToolBreakEvent(PlayerItemBreakEvent event) {

        if (isReplenishAble(event.getBrokenItem())) {
            final PlayerInventory playerInventory = event.getPlayer().getInventory();

            for (ItemStack item : playerInventory) {
                if (isValidItem(event, item)) {
                    replaceItem(event, playerInventory, item);
                    break;
                }
            }
        }
    }

    private static void replaceItem(PlayerItemBreakEvent event, PlayerInventory playerInventory, ItemStack item) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(JavaPlugin.getPlugin(Main.class), () -> replaceHand(event, playerInventory, item), 1L);
    }

    private static void replaceHand(PlayerItemBreakEvent event, PlayerInventory playerInventory, ItemStack item) {
        Material brokenItem = event.getBrokenItem().getType();
        Material mainHandItem = event.getPlayer().getEquipment().getItemInMainHand().getType();
        Material offHandItem = event.getPlayer().getEquipment().getItemInMainHand().getType();

        if (brokenItem.equals(mainHandItem)) {
            playerInventory.setItemInMainHand(item.clone());
            item.setAmount(0);

        } else if (brokenItem.equals(offHandItem)) {
            playerInventory.setItemInOffHand(item.clone());
            item.setAmount(0);
        }
    }

    private static boolean isValidItem(PlayerItemBreakEvent event, ItemStack item) {
        return item != null && (!item.equals(event.getBrokenItem()) && item.getType().equals(event.getBrokenItem().getType()));
    }
}
