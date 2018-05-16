/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Classes.Mage.spelllist;

import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.Classes.Mage.Elements;
import nativelevel.Classes.Mage.MageSpell;
import nativelevel.Equipment.Atributo;
import nativelevel.Equipment.EquipManager;
import nativelevel.integration.SimpleClanKom;
import nativelevel.integration.WorldGuardKom;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.utils.Tralhas;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.List;

/**
 * @author User
 */
public class ConeOfCold extends MageSpell {

    public ConeOfCold() {
        super("Cone de Frio");
    }

    PotionEffect efeito = new PotionEffect(PotionEffectType.SLOW, 20 * 6, 2);

    @Override
    public void cast(Player p) {
        double magia = EquipManager.getPlayerAttribute(Atributo.Magia, p);
        List<Vector> blocos = Tralhas.getPositionsInCone(p.getLocation().toVector(), 5, 45, p.getLocation().getDirection());
        // Location loc = null;
        ClanPlayer eu = ClanLand.manager.getClanPlayer(p);
        List<Entity> lista = p.getNearbyEntities(4, 3, 4);
        lista = Tralhas.getEntitiesInCone(lista, p.getLocation().toVector(), 5, 45, p.getLocation().getDirection());
        for (Entity e : lista) {
            if (e instanceof Monster || e.getType() == EntityType.PLAYER) {
                if (e.getType() == EntityType.PLAYER) {
                    Player alvo = (Player) e;
                    if (!SimpleClanKom.canPvp(p, (Player) e) || WorldGuardKom.ehSafeZone(e.getLocation())) {
                        continue;
                    }
                    ClanPlayer clanAlvo = ClanLand.manager.getClanPlayer(alvo);
                    // nao pegar em aliados
                    if (clanAlvo != null && eu != null && (eu.isAlly(alvo) || eu.getTag().equalsIgnoreCase(clanAlvo.getTag()))) {
                        continue;
                    }
                }
                ((LivingEntity) e).addPotionEffect(efeito);
                Vector ve = e.getLocation().toVector();
                Vector v = ve.subtract(p.getLocation().toVector()).normalize();
                v.setY(0.32 * (1 + magia / 100 / 2));
                e.setVelocity(v);
            }
        }
        int tempo = 1;
        for (Vector v : blocos) {
            final Location loc = new Location(p.getWorld(), v.getX(), v.getY(), v.getZ());
            //SpellParticleEffects.colorida(loc, 0, 0, 255);
            //KoM.efeitoBlocos(loc, Material.SNOW_BALL);
            PlayEffect.play(VisualEffect.SNOW_SHOVEL, loc, "num:2 speed:0.15");
        }
    }

    @Override
    public double getManaCost() {
        return 80;
    }

    @Override
    public double getExpRatio() {
        return 1;
    }

    @Override
    public int getMinSkill() {
        return 50;
    }

    @Override
    public Elements[] getElements() {
        return new Elements[]{Elements.Terra, Elements.Raio, Elements.Terra};
    }

    @Override
    public int getCooldownInSeconds() {
        return 10;
    }

}
