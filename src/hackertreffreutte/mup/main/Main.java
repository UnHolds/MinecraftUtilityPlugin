package hackertreffreutte.mup.main;

import hackertreffreutte.mup.DropperPlanter.DropperPlanterListener;
import hackertreffreutte.mup.Enchantments.CustomEnchantments;
import hackertreffreutte.mup.ToolReplenisher.ToolBreakListener;
import hackertreffreutte.mup.XPBottleEnchantment.XPBottleEnchantmentTableListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {


    public static Plugin plugin;




    @Override
    public void onEnable(){
        //register listeners
        plugin = this;

        //listener for dropper planter
        getServer().getPluginManager().registerEvents(new DropperPlanterListener(), this);

        //listener for item break replenisher
        getServer().getPluginManager().registerEvents(new ToolBreakListener(), this);


        CustomEnchantments.register(CustomEnchantments.XPBottle);

        getServer().getPluginManager().registerEvents(new XPBottleEnchantmentTableListener(), this);


    }

    @Override
    public void onDisable(){

    }



}
