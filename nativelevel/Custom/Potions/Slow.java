/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Custom.Potions;

import nativelevel.Custom.CustomPotion;
import nativelevel.KoM;
import nativelevel.Lang.L;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.*;
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

public class Slow extends CustomPotion {

    private PotionEffect efeito = new PotionEffect(PotionEffectType.SLOW, 20 * 10, 0);

    public Color cor() {
        return Color.WHITE;
    }


    public Slow() {
        super(L.m("Poção de Desaleceração Fraca"), L.m("Causa Desaceleração a Todos"), PotionType.SLOWNESS, true);
    }

    @Override
    public void interage(PlayerInteractEvent ev) {
        ThrownPotion thrownPotion = ev.getPlayer().launchProjectile(ThrownPotion.class);
        thrownPotion.setItem(new ItemStack(ev.getPlayer().getInventory().getItemInMainHand()));
        thrownPotion.setShooter(ev.getPlayer());
        this.consome(ev.getPlayer());
    }

    @Override
    public void splashEvent(PotionSplashEvent ev, Player p) {
        for (Entity e : ev.getAffectedEntities()) {
            if (ev.getIntensity((LivingEntity) e) == 0)
                continue;
            if (e instanceof LivingEntity) {
                if ((e.getType() == EntityType.PLAYER || e instanceof Monster) && !e.hasMetadata("NPC")) {
                    ((LivingEntity) e).addPotionEffect(efeito);
                    KoM.efeitoBlocos(e, Material.LAPIS_BLOCK);
                }
            }
        }
    }

    @Override
    public ItemStack[] getRecipe() {
        return new ItemStack[]{
                new ItemStack(Material.COOKED_MUTTON, 1),
                new ItemStack(Material.FLOWER_POT, 1),
                new ItemStack(Material.RED_ROSE, 1)};
    }

    @Override
    public int getMinimumSkill() {
        return 40;
    }

    @Override
    public double getExpRatio() {
        return 1;
    }

    @Override
    public ItemStack brewWith() {
        return new ItemStack(Material.SPIDER_EYE, 1);
    }

    @Override
    public void drink(PlayerItemConsumeEvent ev) {

    }

}
