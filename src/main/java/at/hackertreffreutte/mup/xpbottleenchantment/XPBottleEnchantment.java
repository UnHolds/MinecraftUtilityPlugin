package at.hackertreffreutte.mup.xpbottleenchantment;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class XPBottleEnchantment extends Enchantment {

    private String name = "";
    private int maxLevel = 0;

    //Singleton Design use this var is revering to the enchantment
    public static final Enchantment ENCHANTMENT = new XPBottleEnchantment("xpbottleenchantment", "xpBottleEnchantment", 3);

    private XPBottleEnchantment(String namespaceKey, String name, int maxLevel) {
        super(NamespacedKey.minecraft(namespaceKey));

        this.name = name;
        this.maxLevel = maxLevel;
    }

    @Override
    @Deprecated
    public String getName() {
        return this.name;
    }

    @Override
    public int getMaxLevel() {
        return this.maxLevel;
    }

    @Override
    public int getStartLevel() {
        return 0;
    }

    @Override
    @NotNull
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ALL;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    @Deprecated
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(@NotNull Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack itemStack) {
        return true;
    }
}
