package at.hackertreffreutte.mup.dropperplanter;

import at.hackertreffreutte.mup.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dropper;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class DropperPlanterListener implements Listener {

    @EventHandler
    public void onDispenseEvent(final BlockDispenseEvent event) {
        if (isDropper(event)) {

            if (!plantable(event.getItem())) {
                return;
            }

            Dispenser dropperType = (Dispenser) event.getBlock().getBlockData();
            BlockFace targetFace = dropperType.getFacing();
            Block soilOriginBlock = fetchSoilOriginBlock(event, targetFace);

            if (soilOriginBlock == null) {
                return;
            }

            if (isFarmable(soilOriginBlock)) {
                plant(event, targetFace, soilOriginBlock);
            }
        }
    }

    private static boolean isDropper(BlockDispenseEvent event) {
        return event.getBlock().getBlockData().getMaterial().equals(Material.DROPPER);
    }

    private boolean plantable(ItemStack item) {

        switch (item.getType()) {
            case WHEAT_SEEDS:
            case BEETROOT_SEEDS:
            case CARROT:
            case POTATO:
                return true;

            default: return false;
        }
    }

    @Nullable
    private static Block fetchSoilOriginBlock(BlockDispenseEvent event, BlockFace targetFace) {
        Location location = event.getBlock().getLocation();

        switch (targetFace) {
            case NORTH: return location.add(0, -1, -1).getBlock();
            case EAST: return location.add(1, -1, 0).getBlock();
            case SOUTH: return location.add(0, -1, 1).getBlock();
            case WEST: return location.add(-1, -1, 0).getBlock();
            default: return null;
        }
    }

    private static boolean isFarmable(Block soilOriginBlock) {
        return soilOriginBlock.getBlockData().getMaterial().equals(Material.FARMLAND);
    }

    @NotNull
    private static ArrayList<Block> buildField(Location soilOriginLocation, int posX, int posZ) {
        ArrayList<Block> field = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Location location = fetchLocation(soilOriginLocation, posX, posZ, i, j);

                if (isFarmable(location)) {
                    field.add(location.getBlock());
                }
            }
        }
        return field;
    }

    @NotNull
    private static Location fetchLocation(Location soilOriginLocation, double posX, double posZ, int i, int j) {
        return new Location(soilOriginLocation.getWorld(), posX + i, soilOriginLocation.getBlockY(), posZ + j);
    }

    private static boolean isFarmable(Location location) {
        return location.getBlock().getType().equals(Material.FARMLAND) && location.add(0, 1, 0).getBlock().getType().equals(Material.AIR);
    }

    private void plant(BlockDispenseEvent event, BlockFace targetFace, Block soilOriginBlock) {
        ArrayList<Block> field = getField(soilOriginBlock.getLocation(), targetFace);

        //throw nothing out
        event.setCancelled(true);

        if (field.isEmpty()) {
            return;
        }

        Bukkit.getScheduler().scheduleSyncDelayedTask(JavaPlugin.getPlugin(Main.class), () -> removeItem(event), 1L);

        int randomIndex = (int) (Math.random() * field.size());
        //overflow protection
        if (randomIndex == field.size()) {
            randomIndex = 0;
        }

        field.get(randomIndex).setType(seedToPlant(event.getItem()));
    }

    private ArrayList<Block> getField(Location soilOriginLocation, BlockFace direction) {
        int posX;
        int posZ;

        if (direction == BlockFace.NORTH) {
            posX = soilOriginLocation.getBlockX() - 1;
            posZ = soilOriginLocation.getBlockZ() - 2;

        } else if (direction == BlockFace.EAST) {
            posX = soilOriginLocation.getBlockX();
            posZ = soilOriginLocation.getBlockZ() - 1;

        } else if (direction == BlockFace.SOUTH) {
            posX = soilOriginLocation.getBlockX() - 1;
            posZ = soilOriginLocation.getBlockZ();

        } else if (direction == BlockFace.WEST) {
            posX = soilOriginLocation.getBlockX() - 2;
            posZ = soilOriginLocation.getBlockZ() - 1;

        } else {
            return new ArrayList<>();
        }

        return buildField(soilOriginLocation, posX, posZ);
    }

    private static void removeItem(BlockDispenseEvent event) {
        Dropper dropper = (Dropper) event.getBlock().getState();
        final Inventory inventory = dropper.getInventory();

        for (ItemStack stack : inventory.getContents()) {
            if (isValidItem(event, stack)) {
                stack.setAmount(stack.getAmount() - 1);
                break;
            }
        }
    }

    private static boolean isValidItem(BlockDispenseEvent event, ItemStack stack) {
        return stack != null && (stack.getType().equals(event.getItem().getType()));
    }

    private Material seedToPlant(ItemStack seed) {

        switch (seed.getType()) {
            case WHEAT_SEEDS: return Material.WHEAT;
            case BEETROOT_SEEDS: return Material.BEETROOTS;
            case CARROT: return Material.CARROTS;
            case POTATO: return Material.POTATOES;
            default: return Material.AIR;
        }
    }
}
