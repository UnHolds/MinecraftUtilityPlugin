package at.hackertreffreutte.mup.toolreplenisher;

import at.hackertreffreutte.mup.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

public class ToolBreakListener implements Listener {


    public boolean isReplenishAble(ItemStack item){
        switch (item.getType()){
            //Pickaxes
            case DIAMOND_PICKAXE:
            case GOLDEN_PICKAXE:
            case IRON_PICKAXE:
            case STONE_PICKAXE:
            case WOODEN_PICKAXE:

            // shovel
            case DIAMOND_SHOVEL:
            case GOLDEN_SHOVEL:
            case IRON_SHOVEL:
            case STONE_SHOVEL:
            case WOODEN_SHOVEL:

            //axe
            case DIAMOND_AXE:
            case GOLDEN_AXE:
            case IRON_AXE:
            case STONE_AXE:
            case WOODEN_AXE:

             //sword
            case DIAMOND_SWORD:
            case GOLDEN_SWORD:
            case IRON_SWORD:
            case STONE_SWORD:
            case WOODEN_SWORD:

             //hoe
            case DIAMOND_HOE:
            case GOLDEN_HOE:
            case IRON_HOE:
            case STONE_HOE:
            case WOODEN_HOE:

            //shield
            case SHIELD:

             //scissors / shears
            case SHEARS:

             //bow
            case BOW:

            //crossbow
            case CROSSBOW:

             //fishing rod
            case FISHING_ROD:

                return true;

            default:
                return false;
        }
    }


    @EventHandler
    public void onPlayerToolBreakEvent(PlayerItemBreakEvent event){

        if(isReplenishAble(event.getBrokenItem())){

            final PlayerInventory playerInventory = event.getPlayer().getInventory();
            for(int i = 0; i < playerInventory.getContents().length; i++){

                final ItemStack item = playerInventory.getItem(i);
                if(item != null){
                    if(!item.equals(event.getBrokenItem()) && item.getType().equals(event.getBrokenItem().getType())){

                        //replace the item
                        Bukkit.getScheduler().scheduleSyncDelayedTask(JavaPlugin.getPlugin(Main.class), new Runnable() {
                            public void run(){
                                if(event.getBrokenItem().getType().equals(event.getPlayer().getEquipment().getItemInMainHand().getType())){
                                    playerInventory.setItemInMainHand(item.clone());
                                    item.setAmount(0);
                                }else if(event.getBrokenItem().getType().equals(event.getPlayer().getEquipment().getItemInOffHand().getType())){
                                    playerInventory.setItemInOffHand(item.clone());
                                item.setAmount(0);
                            }

                            }
                        },1L);

                        break;
                    }
                }
            }

        }else{
            return;
        }

    }
}
