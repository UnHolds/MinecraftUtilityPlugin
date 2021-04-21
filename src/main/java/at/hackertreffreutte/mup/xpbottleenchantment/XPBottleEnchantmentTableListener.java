package at.hackertreffreutte.mup.xpbottleenchantment;

import at.hackertreffreutte.mup.xpcalculator.XPCalculator;
import at.hackertreffreutte.mup.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentOffer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.Arrays;

public class XPBottleEnchantmentTableListener implements Listener {




    @EventHandler
    public void onItemInEnchantmentTable(PrepareItemEnchantEvent event){


        if(event.getItem().getType().equals(Material.POTION) ) {
            //checks if the item is the right type

            PotionData potionData = ((PotionMeta) event.getItem().getItemMeta()).getBasePotionData();

            if(potionData.getType().equals(PotionType.WATER)) {


                //Bukkit.broadcastMessage(event.getOffers()[0] + " offer");

                if(event.getEnchantmentBonus() >= 2){
                    event.getOffers()[0] = new EnchantmentOffer(XPBottleEnchantment.ENCHANTMENT, 1, 5);
                }

                if(event.getEnchantmentBonus() >= 8){
                    event.getOffers()[1] = new EnchantmentOffer(XPBottleEnchantment.ENCHANTMENT, 2, 10);
                }

                if(event.getEnchantmentBonus() >= 15){
                    event.getOffers()[2] = new EnchantmentOffer(XPBottleEnchantment.ENCHANTMENT, 3, 20);
                }


                event.setCancelled(false);
            }
        }else{
            return;
        }
    }




    @EventHandler
    public void onItemEnchant(EnchantItemEvent event){

        if(event.getItem().getType().equals(Material.POTION)) {
            //checks if the item is the right type

            PotionData potionData = ((PotionMeta) event.getItem().getItemMeta()).getBasePotionData();

            if(potionData.getType().equals(PotionType.WATER)) {

                //this is a bad fix and you should never do this but i have no idea how to do it otherwise ...
                // event.getEnchantsToAdd() is empty and that is the problem and why this workaround exists



                //just a placeholder so that the enchantment works
                event.getEnchantsToAdd().put(XPBottleEnchantment.ENCHANTMENT, 0);

                final ItemStack enchantedItem = new ItemStack(Material.EXPERIENCE_BOTTLE);

                //create the enchantment of the item
                enchantedItem.addUnsafeEnchantment(XPBottleEnchantment.ENCHANTMENT, event.whichButton() + 1);

                //level String
                String level = "";
                for(int i = 0; i < event.whichButton() + 1; i++){
                    level += "I";
                }


                //change item meta
                ItemMeta meta = enchantedItem.getItemMeta();
                meta.setLore(Arrays.asList("Exp Bottle: " + level));
                enchantedItem.setItemMeta(meta);

                final EnchantingInventory inv = (EnchantingInventory) event.getInventory();

                Bukkit.getScheduler().scheduleSyncDelayedTask(JavaPlugin.getPlugin(Main.class), new Runnable() {
                    public void run() {
                        inv.setItem(enchantedItem);
                    }
                },1L);



                //remove xp from player due to enchanting
                int currentXP = XPCalculator.levelToExp(event.getEnchanter().getLevel());
                int newXP = 0;

                switch (event.getExpLevelCost()){
                    case 5:
                        newXP = currentXP - 55;
                        break;

                    case 10:
                        newXP = currentXP - 160;
                        break;

                    case 20:
                        newXP = currentXP - 550;
                        break;
                }


                //just some catching so that it is not below zero
                if(newXP < 0){
                    newXP = 0;
                }

                //set the new exp level
                event.getEnchanter().setLevel(XPCalculator.expToLevel(newXP));




            }
        }else{
            return;
        }




    }
}
