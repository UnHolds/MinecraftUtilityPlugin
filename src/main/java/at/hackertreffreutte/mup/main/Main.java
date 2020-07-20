package at.hackertreffreutte.mup.main;

import at.hackertreffreutte.mup.dropperplanter.DropperPlanterListener;
import at.hackertreffreutte.mup.enchantments.CustomEnchantments;
import at.hackertreffreutte.mup.blockreplenisher.BlockReplenisher;
import at.hackertreffreutte.mup.toolreplenisher.ToolBreakListener;
import at.hackertreffreutte.mup.xpbottleenchantment.XPBottleBreakListener;
import at.hackertreffreutte.mup.xpbottleenchantment.XPBottleEnchantment;
import at.hackertreffreutte.mup.xpbottleenchantment.XPBottleEnchantmentTableListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {


    @Override
    public void onEnable(){

        //listener for dropper planter
        getServer().getPluginManager().registerEvents(new DropperPlanterListener(), this);

        //listener for item break replenisher
        getServer().getPluginManager().registerEvents(new ToolBreakListener(), this);


        //register new enchantment
        CustomEnchantments.register(XPBottleEnchantment.ENCHANTMENT);
        //create new listener for enchantment table
        getServer().getPluginManager().registerEvents(new XPBottleEnchantmentTableListener(), this);
        //create new listener for throwing xp bottles
        getServer().getPluginManager().registerEvents(new XPBottleBreakListener(), this);



        //register listener for replenisher
        getServer().getPluginManager().registerEvents(new BlockReplenisher(), this);

    }

    @Override
    public void onDisable(){

    }



}
