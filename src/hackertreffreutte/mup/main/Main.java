package hackertreffreutte.mup.main;

import hackertreffreutte.mup.DropperPlanter.DropperPlanterListener;
import hackertreffreutte.mup.Enchantments.CustomEnchantments;
import hackertreffreutte.mup.BlockReplenisher.BlockReplenisher;
import hackertreffreutte.mup.ToolReplenisher.ToolBreakListener;
import hackertreffreutte.mup.XPBottleEnchantment.XPBottleBreakListener;
import hackertreffreutte.mup.XPBottleEnchantment.XPBottleEnchantment;
import hackertreffreutte.mup.XPBottleEnchantment.XPBottleEnchantmentTableListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {





    @Override
    public void onEnable(){

        //listener for dropper planter
        getServer().getPluginManager().registerEvents(new DropperPlanterListener(), this);

        //listener for item break replenisher
        getServer().getPluginManager().registerEvents(new ToolBreakListener(), this);


        //register new enchantment
        CustomEnchantments.register(XPBottleEnchantment.enchantment);
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
