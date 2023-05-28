package at.hackertreffreutte.mup.enchantments;

import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CustomEnchantments {

    public static void register(Enchantment enchantment) {
        boolean registered = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(enchantment);

        if (!registered) {
            try {
                Field f = Enchantment.class.getDeclaredField("acceptingNew");
                f.setAccessible(true);
                f.set(null, true);
                Enchantment.registerEnchantment(enchantment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
