package nativelevel.Classes.Blacksmithy.recipes;

import nativelevel.Classes.Blacksmithy.CustomCrafting;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Ziden
 */
public class DiamondBoots extends CustomCrafting {

    public DiamondBoots() {
        super("Botas de Diamante", "Crie botas de Diamante");
    }

    @Override
    public ItemStack[] getRecipe() {
        return new ItemStack[]{
                new ItemStack(Material.IRON_BOOTS, 1),
                new ItemStack(Material.LEATHER, 1),
                new ItemStack(Material.DIAMOND_BLOCK),};
    }

    @Override
    public double getExpRatio() {
        return 1;
    }

    @Override
    public int getMinimumSkill() {
        return 110;
    }

    @Override
    public int getHammerHits() {
        return 7;
    }

    @Override
    public ItemStack aplica(ItemStack ss) {

        if (ss.getType() == Material.IRON_BOOTS) {
            ss.setType(Material.DIAMOND_BOOTS);
        }
        return ss;
    }

    @Override
    public ItemStack aplicaNoCraftNormal(ItemStack ss) {

        if (ss.getType() == Material.DIAMOND_BOOTS) {
            ss.setType(Material.AIR);
        }
        return ss;

    }

    @Override
    public ItemStack getItemPrincipal() {
        return new ItemStack(Material.IRON_BOOTS);
    }

}
