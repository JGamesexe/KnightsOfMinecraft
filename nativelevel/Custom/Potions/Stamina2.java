/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Custom.Potions;

import nativelevel.Attributes.Stamina;
import nativelevel.Custom.CustomPotion;
import nativelevel.KoM;
import nativelevel.Lang.L;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

/**
 * @author User
 */

public class Stamina2 extends CustomPotion {

    public Stamina2() {
        super(L.m("Poção de Stamina Media"), L.m("Cura Stamina"), PotionType.WATER_BREATHING, false);
    }

    @Override
    public void interage(PlayerInteractEvent ev) {

    }

    public Color cor() {
        return Color.ORANGE;
    }


    @Override
    public void splashEvent(PotionSplashEvent ev, Player p) {

    }

    @Override
    public ItemStack[] getRecipe() {
        return new ItemStack[]{
                new ItemStack(Material.BOWL, 1),
                new ItemStack(Material.COAL_BLOCK, 1),
                new ItemStack(Material.HAY_BLOCK, 1)};
    }

    @Override
    public int getMinimumSkill() {
        return 60;
    }

    @Override
    public double getExpRatio() {
        return 1.5;
    }

    @Override
    public ItemStack brewWith() {
        return new ItemStack(Material.NETHER_STALK, 1);
    }

    @Override
    public void drink(PlayerItemConsumeEvent ev) {
        Stamina.changeStamina(ev.getPlayer(), 65);
        KoM.efeitoBlocos(ev.getPlayer(), Material.LAPIS_BLOCK);
        ev.getPlayer().sendMessage(ChatColor.GREEN + "Voce recuperou um pouco de seu Stamina");
    }

}
