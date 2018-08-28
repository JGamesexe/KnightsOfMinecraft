/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Custom.Potions;

import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.Custom.CustomPotion;
import nativelevel.Lang.L;
import nativelevel.Listeners.DamageListener;
import nativelevel.Listeners.DeathEvents;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

/**
 * @author User
 */

public class Explosion1 extends CustomPotion {

    private int damage = 4;
    private DamageCause damageType = DamageCause.ENTITY_EXPLOSION;

    public Explosion1() {
        super(L.m("Poção de Explosão"), L.m("Faz um KBOOMZINHO"), PotionType.INSTANT_DAMAGE, true);
    }


    public Color cor() {
        return Color.GRAY;
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
        PlayEffect.play(VisualEffect.EXPLOSION, ev.getEntity().getLocation(), "num:1");
        for (Entity e : ev.getAffectedEntities()) {
            if (ev.getIntensity((LivingEntity) e) == 0)
                continue;
            if (e instanceof LivingEntity) {
                if ((e.getType() == EntityType.PLAYER || e instanceof Monster) && !e.hasMetadata("NPC")) {
                    DamageListener.darDano(p, (double) damage, (LivingEntity) e, DamageCause.ENTITY_EXPLOSION);
                    Vector ve = e.getLocation().toVector();
                    Vector v = ve.subtract(ev.getPotion().getLocation().toVector()).normalize().multiply(1.5);
                    v.setY(0.3);
                    e.setVelocity(v);
                }
            }
        }
    }

    @Override
    public ItemStack[] getRecipe() {
        return new ItemStack[]{
                new ItemStack(Material.GLASS, 1),
                new ItemStack(Material.SANDSTONE, 1),
                new ItemStack(Material.COAL, 1)};
    }

    @Override
    public int getMinimumSkill() {
        return 5;
    }

    @Override
    public double getExpRatio() {
        return 1d;
    }

    @Override
    public ItemStack brewWith() {
        return new ItemStack(Material.SPIDER_EYE, 1);
    }

    @Override
    public void drink(PlayerItemConsumeEvent ev) {

    }

}
