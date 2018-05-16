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

public class Stamina1 extends CustomPotion {

    public Stamina1() {
        super(L.m("Poção de Stamina Fraca"), L.m("Cura um pouco de Stamina"), PotionType.WATER_BREATHING, false);
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
                new ItemStack(Material.WOOD, 1),
                new ItemStack(Material.COAL, 1),
                new ItemStack(Material.WHEAT, 1)};
    }

    @Override
    public int getMinimumSkill() {
        return 15;
    }

    @Override
    public double getExpRatio() {
        return 1d;
    }

    @Override
    public ItemStack brewWith() {
        return new ItemStack(Material.REDSTONE, 1);
    }

    @Override
    public void drink(PlayerItemConsumeEvent ev) {
        Stamina.changeStamina(ev.getPlayer(), 20);
        KoM.efeitoBlocos(ev.getPlayer(), Material.LAPIS_BLOCK);
        ev.getPlayer().sendMessage(ChatColor.GREEN + "Voce recuperou um pouco de seu Stamina");
    }

}
