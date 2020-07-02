package hackertreffreutte.mup.Enchantments;

import org.bukkit.enchantments.Enchantment;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CustomEnchantments {

    public static final Enchantment XPBottle = new EnchantmentWrapper("xpbottleenchantment", "xpBottleEnchantment", 3);



    public static boolean register(Enchantment enchantment) {
        boolean registered = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(enchantment);

        if(registered){
            //enchantment already registered
        }else{
            try{
                Field f = Enchantment.class.getDeclaredField("acceptingNew");
                f.setAccessible(true);
                f.set(null, true);
                Enchantment.registerEnchantment(enchantment);
            }catch (Exception e){
                registered = false;
                e.printStackTrace();
            }

            if(registered){
                //send message to console
                //TODO send message to console

            }
        }

        return registered;
    }


}
