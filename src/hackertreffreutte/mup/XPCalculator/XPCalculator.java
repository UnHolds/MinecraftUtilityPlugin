package hackertreffreutte.mup.XPCalculator;

public class XPCalculator {


    //those are some serious magic numbers
    //https://minecraft.gamepedia.com/Experience#Leveling_up
    public static int levelToExp(int level){

        if(level <= 15){
            return (int) Math.round(Math.pow(level,2) + 6 * level);
        } else if(level <= 31){
            return (int) Math.round( 2.5 * Math.pow(level,2) - 40.5 * level + 360);
        } else {
            return (int) Math.round( 4.5 * Math.pow(level,2) - 162.5 * level + 2220);
        }

    }

    //those are some serious magic numbers
    //https://minecraft.gamepedia.com/Experience#Leveling_up
    public static int expToLevel(int exp){

        int level = 0;
        if(exp <= 351){
            level = (int) Math.floor(-3 + Math.sqrt(exp + 9));
        }else if(exp <= 1623){
            level = (int) Math.floor(8.1 + 0.1 * Math.sqrt(40*exp - 7839));
        }else{
            level = (int) Math.floor(18.0556 + 0.05555555 * Math.sqrt(72 * exp - 54215));
        }

        if(level < 0){
            level = 0;
        }

        return level;

    }
}
