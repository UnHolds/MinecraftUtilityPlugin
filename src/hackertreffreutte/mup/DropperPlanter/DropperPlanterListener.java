package hackertreffreutte.mup.DropperPlanter;

        import hackertreffreutte.mup.main.Main;
        import org.bukkit.Bukkit;
        import org.bukkit.Location;
        import org.bukkit.Material;
        import org.bukkit.block.Block;
        import org.bukkit.block.BlockFace;
        import org.bukkit.block.Dropper;
        import org.bukkit.event.EventHandler;
        import org.bukkit.event.Listener;
        import org.bukkit.event.block.BlockDispenseEvent;
        import org.bukkit.inventory.Inventory;
        import org.bukkit.inventory.ItemStack;

        import java.util.ArrayList;


public class DropperPlanterListener implements Listener {



    private boolean plantable(ItemStack item){

        switch (item.getType()){
            case WHEAT_SEEDS:
            case BEETROOT_SEEDS:
            case CARROT:
            case POTATO:
                return true;
        }

        return false;
    }


    private Material seedToPlant(ItemStack seed){

        switch (seed.getType()){
            case WHEAT_SEEDS:
                return Material.WHEAT;
            case BEETROOT_SEEDS:
                return Material.BEETROOTS;
            case CARROT:
                return Material.CARROTS;
            case POTATO:
                return Material.POTATOES;
            default:
                //just in case
                return Material.AIR;
        }
    }


    private ArrayList<Block> getField(Location soilOriginLocation, BlockFace direction){

        int posX = 0;
        int posZ = 0;


        if(direction == BlockFace.NORTH){

            posX = soilOriginLocation.getBlockX() - 1;
            posZ = soilOriginLocation.getBlockZ() - 2;


        }else if(direction == BlockFace.EAST){

            posX = soilOriginLocation.getBlockX();
            posZ = soilOriginLocation.getBlockZ() - 1;

        }else if(direction == BlockFace.SOUTH){

            posX = soilOriginLocation.getBlockX() - 1;
            posZ = soilOriginLocation.getBlockZ();

        }else if(direction == BlockFace.WEST){
            posX = soilOriginLocation.getBlockX() - 2;
            posZ = soilOriginLocation.getBlockZ() - 1;

        }else{
            return new ArrayList<Block>();
        }


        ArrayList<Block> field = new ArrayList<>();

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                Location loc = new Location(soilOriginLocation.getWorld(), (double) posX + i, (double) soilOriginLocation.getBlockY(), (double) posZ + j);
                if(loc.getBlock().getType().equals(Material.FARMLAND) && loc.add(0,1,0).getBlock().getType().equals(Material.AIR)){
                    field.add(loc.getBlock());
                }
            }
        }

        return field;
    }

    @EventHandler
    public void onDispenseEvent(BlockDispenseEvent event){
        if(event.getBlock().getBlockData().getMaterial().equals(Material.DROPPER)){


            //check if item is seed otherwise return
            if(plantable(event.getItem()) == false){
                return;
            }


            org.bukkit.block.data.type.Dispenser dropperType = (org.bukkit.block.data.type.Dispenser) event.getBlock().getBlockData();
            BlockFace targetFace = dropperType.getFacing();

            Block soilOriginBlock = null;
            Block wheatOriginBlock = null;





            if(targetFace.equals(BlockFace.NORTH)){

                soilOriginBlock = event.getBlock().getLocation().add(0,-1,-1).getBlock();
                wheatOriginBlock = event.getBlock().getLocation().add(0,0,-1).getBlock();

            }else if(targetFace.equals(BlockFace.EAST)){

                soilOriginBlock = event.getBlock().getLocation().add(1,-1,0).getBlock();
                wheatOriginBlock = event.getBlock().getLocation().add(1,0,0).getBlock();

            }else if(targetFace.equals(BlockFace.SOUTH)){

                soilOriginBlock = event.getBlock().getLocation().add(0,-1,1).getBlock();
                wheatOriginBlock = event.getBlock().getLocation().add(0,0,1).getBlock();

            }else if(targetFace.equals(BlockFace.WEST)){
                soilOriginBlock = event.getBlock().getLocation().add(-1,-1,0).getBlock();
                wheatOriginBlock = event.getBlock().getLocation().add(-1,0,0).getBlock();


            }else{
                return;
            }




            if(soilOriginBlock.getBlockData().getMaterial().equals(Material.FARMLAND)){
                //planting

                ArrayList<Block> field = getField(soilOriginBlock.getLocation(), targetFace);

                //throw nothing out
                event.setCancelled(true);


                //nothing to plant -> cancel
                if(field.size() == 0){
                    return;
                }

                //remove dropped item
                 Dropper dropper = (Dropper) event.getBlock().getState();
                 Inventory inv = dropper.getInventory();

                 Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
                    @Override
                    public void run() {
                        for(ItemStack stack : inv.getContents()){
                            if(stack != null){
                                if(stack.getType().equals(event.getItem().getType())){
                                    stack.setAmount(stack.getAmount() - 1);
                                    break;
                                }
                            }
                        }
                    }
                    },1L);


                int randomIndex = (int) (Math.random() * field.size());

                //overflow protection
                if(randomIndex == field.size()){
                    randomIndex = 0;
                }

                field.get(randomIndex).setType(seedToPlant(event.getItem()));

            }
        }
    }
}
