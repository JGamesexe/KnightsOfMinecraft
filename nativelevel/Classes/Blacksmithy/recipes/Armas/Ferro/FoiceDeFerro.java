package nativelevel.Classes.Blacksmithy.recipes.Armas.Ferro;

import nativelevel.Classes.Blacksmithy.CustomCrafting;
import nativelevel.Equipment.WeaponDamage;
import nativelevel.KoM;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Ziden
 */
public class FoiceDeFerro extends CustomCrafting {

    public FoiceDeFerro() {
        super("Fooices de Ferro", "Crie foices de ferro aprimoradas");
    }

    @Override
    public ItemStack[] getRecipe() {
        return new ItemStack[]{
                new ItemStack(Material.IRON_INGOT, 1),
                new ItemStack(Material.COAL, 1),
                new ItemStack(Material.SAND),};
    }

    @Override
    public double getExpRatio() {
        return 1;
    }

    @Override
    public int getMinimumSkill() {
        return 65;
    }

    @Override
    public int getHammerHits() {
        return 4;
    }

    @Override
    public ItemStack aplica(ItemStack ss) {
        KoM.debug("Aplicando foice de ferro");
        double dano = WeaponDamage.getDamage(ss);
        //  dano += 1;
        ss = WeaponDamage.setDano(ss, dano);
        return ss;
    }

    @Override
    public ItemStack aplicaNoCraftNormal(ItemStack ss) {
        if (ss.getType() != Material.IRON_HOE)
            return ss;
        double dano = WeaponDamage.getDamage(ss);
        dano -= 1;
        return WeaponDamage.setDano(ss, dano);
    }

    @Override
    public ItemStack getItemPrincipal() {
        return new ItemStack(Material.IRON_HOE);
    }

}
