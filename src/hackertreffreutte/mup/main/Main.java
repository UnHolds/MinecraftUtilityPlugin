package hackertreffreutte.mup.main;

import hackertreffreutte.mup.DropperPlanter.DropperPlanterListener;
import hackertreffreutte.mup.ToolReplenisher.ToolBreakListener;
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
    }

    @Override
    public void onDisable(){
        //Fired when the server stops and disables all plugins
    }

}
