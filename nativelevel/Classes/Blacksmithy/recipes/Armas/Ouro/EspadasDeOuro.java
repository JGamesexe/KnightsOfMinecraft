package nativelevel.Classes.Blacksmithy.recipes.Armas.Ouro;

import nativelevel.Classes.Blacksmithy.CustomCrafting;
import nativelevel.Equipment.WeaponDamage;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

/**
 * @author Ziden
 */
public class EspadasDeOuro extends CustomCrafting {

    public EspadasDeOuro() {
        super("Espadas de Ouro Aprimoradas", "Crie espadas de ouro aprimoradas");
    }

    @Override
    public ItemStack[] getRecipe() {
        return new ItemStack[]{
                new ItemStack(Material.GOLD_INGOT, 1),
                new ItemStack(Material.COAL_BLOCK, 1),
                new ItemStack(Material.OBSIDIAN),};
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
        double dano = WeaponDamage.getDamage(ss);
        dano += 1;
        ss = WeaponDamage.setDano(ss, dano);
        ss.addEnchantment(Enchantment.DURABILITY, 0);
        return ss;
    }

    @Override
    public ItemStack aplicaNoCraftNormal(ItemStack ss) {
        if (ss.getType() != Material.GOLD_SWORD)
            return ss;
        double dano = WeaponDamage.getDamage(ss);
        dano -= 1;
        return WeaponDamage.setDano(ss, dano);
    }

    @Override
    public ItemStack getItemPrincipal() {
        return new ItemStack(Material.GOLD_SWORD);
    }

}
