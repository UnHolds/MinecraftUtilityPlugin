package at.hackertreffreutte.mup.xpbottleenchantment;

import at.hackertreffreutte.mup.xpcalculator.XPCalculator;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

import java.util.Map;

public class XPBottleBreakListener implements Listener {

    //TODO
    @EventHandler
    public void onExpBottleBreak(ExpBottleEvent event) {
        ProjectileSource projectileSource = event.getEntity().getShooter();

        if (!(projectileSource instanceof Player)) {
            return;
        }

        Player player = (Player) projectileSource;
        ItemStack item = event.getEntity().getItem();

        if (item.getType().equals(Material.EXPERIENCE_BOTTLE)) {

            Enchantment enchantment = null;

            Map<Enchantment, Integer> enchantments = item.getEnchantments();
            int level = 0;
            for (Enchantment e : enchantments.keySet()) {
                if (e.equals(XPBottleEnchantment.ENCHANTMENT)) {
                    enchantment = e;
                    level = enchantments.get(e);
                    break;
                }
            }

            if (enchantment != null) {

                float levelProgress = player.getExp();
                int xpAtPlayerLevel = XPCalculator.levelToExp(player.getLevel());
                int xpAtNextPlayerLevel = XPCalculator.levelToExp(player.getLevel() + 1);
                int xpPlayer = Math.round((xpAtNextPlayerLevel - xpAtPlayerLevel) * levelProgress + xpAtPlayerLevel);

                switch (level) {
                    case 1: xpPlayer += 55; break;
                    case 2: xpPlayer += 160; break;
                    case 3: xpPlayer += 550; break;
                    default: break;
                }

                //calculate the new values
                int newPlayerLevel = XPCalculator.expToLevel(xpPlayer);
                xpAtPlayerLevel = XPCalculator.levelToExp(newPlayerLevel);
                xpAtNextPlayerLevel = XPCalculator.levelToExp(newPlayerLevel + 1);

                // 0.8 for slightly worse conversion rate and to include the effects of the enchanting potion
                levelProgress = ((xpAtNextPlayerLevel - xpPlayer) / (xpAtNextPlayerLevel - xpAtPlayerLevel + 0.0f)) * 0.8f;

                player.setExp(levelProgress);
                player.setLevel(newPlayerLevel);
            }
        }
    }
}
