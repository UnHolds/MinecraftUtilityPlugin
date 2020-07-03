package hackertreffreutte.mup.XPBottleEnchantment;

import hackertreffreutte.mup.Enchantments.CustomEnchantments;
import hackertreffreutte.mup.XPCalculator.XPCalculator;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import java.util.Map;

public class XPBottleBreakListener implements Listener {


    //TODO
    @EventHandler
    public void onExpBottleBreak(PlayerInteractEvent event) {

        ItemStack item = event.getItem();

        if(item.getType().equals(Material.EXPERIENCE_BOTTLE)){

            Map<Enchantment, Integer> enchantments = item.getEnchantments();



            Enchantment  enchantment = null;
            int level = 0;
            for(Enchantment ench : enchantments.keySet()){
                if(ench.equals(CustomEnchantments.XPBottle)){
                    enchantment = ench;
                    level = enchantments.get(ench);
                    break;
                }
            }

            if(enchantment != null){

                //otherwise double event "left-click air"
                if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){

                    float levelProgress = event.getPlayer().getExp();

                    int xpAtPlayerLevel = XPCalculator.levelToExp(event.getPlayer().getLevel());
                    int xpAtNextPlayerLevel = XPCalculator.levelToExp(event.getPlayer().getLevel() + 1);

                    int xpPlayer = Math.round((xpAtNextPlayerLevel - xpAtPlayerLevel) * levelProgress + xpAtPlayerLevel);


                    switch (level){
                        case 1:
                            xpPlayer += 55;
                            break;
                        case 2:
                            xpPlayer += 160;
                            break;
                        case 3:
                            xpPlayer += 550;
                            break;
                    }


                    //calculate the new values
                    int newPlayerLevel = XPCalculator.expToLevel(xpPlayer);

                    xpAtPlayerLevel = XPCalculator.levelToExp(newPlayerLevel);
                    xpAtNextPlayerLevel = XPCalculator.levelToExp(newPlayerLevel + 1);

                    // 0.8 for slightly worse conversion rate and to include the effects of the enchanting potion
                    levelProgress = ((xpAtNextPlayerLevel - xpPlayer) / (xpAtNextPlayerLevel - xpAtPlayerLevel + 0.0f)) * 0.8f;

                    event.getPlayer().setExp(levelProgress);
                    event.getPlayer().setLevel(newPlayerLevel);


                }
            }

        }
    }
}
