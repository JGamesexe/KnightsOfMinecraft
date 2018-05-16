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
public class Cure3 extends CustomPotion {

    public Cure3() {
        super(L.m("Antibiotico Forte"), L.m("Cura venenos e decomposicao"), PotionType.INSTANT_HEAL, false);
    }

    public Color cor() {
        return Color.fromRGB(244, 168, 6);
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
                new ItemStack(Material.MAGMA_CREAM, 1),
                new ItemStack(Material.BOWL, 1),
                new ItemStack(Material.LOG_2, 1)};
    }

    @Override
    public int getMinimumSkill() {
        return 90;
    }

    @Override
    public double getExpRatio() {
        return 1d;
    }

    @Override
    public ItemStack brewWith() {
        return new ItemStack(Material.NETHER_STALK, 1);
    }

    @Override
    public void drink(PlayerItemConsumeEvent ev) {
        if (ev.getPlayer().hasPotionEffect(PotionEffectType.POISON) || ev.getPlayer().hasPotionEffect(PotionEffectType.WITHER)) {
            for (PotionEffect effect : ev.getPlayer().getActivePotionEffects()) {
                if (effect.getType().getName() == PotionEffectType.POISON.getName()) {
                    ev.getPlayer().removePotionEffect(PotionEffectType.POISON);
                    ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Você se curou do veneno !"));
                    KoM.efeitoBlocos(ev.getPlayer(), Material.PUMPKIN);
                }
                if (effect.getType().getName() == PotionEffectType.WITHER.getName()) {
                    ev.getPlayer().removePotionEffect(PotionEffectType.WITHER);
                    ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Você se curou da decomposição !"));
                    KoM.efeitoBlocos(ev.getPlayer(), Material.PUMPKIN);
                }
            }
        } else {
            ev.setCancelled(true);
            ev.getPlayer().sendMessage(ChatColor.RED + L.m("Você não está envenenado nem decompondo para beber isto !"));

        }
    }

}
