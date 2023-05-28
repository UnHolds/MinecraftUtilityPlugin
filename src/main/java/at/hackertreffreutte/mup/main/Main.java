package at.hackertreffreutte.mup.main;

import at.hackertreffreutte.mup.blockreplenisher.BlockReplenisher;
import at.hackertreffreutte.mup.dropperplanter.DropperPlanterListener;
import at.hackertreffreutte.mup.enchantments.CustomEnchantments;
import at.hackertreffreutte.mup.toolreplenisher.ToolBreakListener;
import at.hackertreffreutte.mup.xpbottleenchantment.XPBottleBreakListener;
import at.hackertreffreutte.mup.xpbottleenchantment.XPBottleEnchantment;
import at.hackertreffreutte.mup.xpbottleenchantment.XPBottleEnchantmentTableListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginManager manager = getServer().getPluginManager();

        manager.registerEvents(new DropperPlanterListener(), this);
        manager.registerEvents(new ToolBreakListener(), this);
        CustomEnchantments.register(XPBottleEnchantment.ENCHANTMENT);
        manager.registerEvents(new XPBottleEnchantmentTableListener(), this);
        manager.registerEvents(new XPBottleBreakListener(), this);
        manager.registerEvents(new BlockReplenisher(), this);
    }
}
