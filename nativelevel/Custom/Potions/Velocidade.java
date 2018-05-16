/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Custom.Potions;

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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

/**
 * @author User
 */

public class Velocidade extends CustomPotion {

    public final PotionEffect efeito = new PotionEffect(PotionEffectType.SPEED, 20 * 60 * 3, 0);

    public Velocidade() {
        super(L.m("Poção de Velocidade Fraca"), L.m("Permite correr rapidamente"), PotionType.SPEED, false);
    }

    public Color cor() {
        return Color.BLACK;
    }


    @Override
    public void interage(PlayerInteractEvent ev) {

    }

    @Override
    public void splashEvent(PotionSplashEvent ev, Player p) {

    }

    @Override
    public ItemStack[] getRecipe() {
        return new ItemStack[]{
                new ItemStack(Material.RABBIT_STEW, 1),
                new ItemStack(Material.WATER_BUCKET, 1),
                new ItemStack(Material.RABBIT_FOOT, 1)};
    }

    @Override
    public int getMinimumSkill() {
        return 50;
    }

    @Override
    public double getExpRatio() {
        return 1.5;
    }

    @Override
    public ItemStack brewWith() {
        return new ItemStack(Material.RABBIT_FOOT, 1);
    }

    @Override
    public void drink(PlayerItemConsumeEvent ev) {
        ev.getPlayer().addPotionEffect(efeito);
        ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("* gulp gulp *"));
        KoM.efeitoBlocos(ev.getPlayer(), Material.WOOL);

    }

}
