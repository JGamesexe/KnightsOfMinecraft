/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Classes.Mage.spelllist;

import nativelevel.Classes.Mage.Elements;
import nativelevel.Classes.Mage.MageSpell;
import nativelevel.Equipment.Atributo;
import nativelevel.Equipment.EquipManager;
import nativelevel.KoM;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author User
 */
public class Disperse extends MageSpell {

    public Disperse() {
        super("Dispersão Astral"); // pensando
    }

    @Override
    public void cast(Player p) {
        double magia = EquipManager.getPlayerAttribute(Atributo.Dano_Magico, p);
        PotionEffect ef1 = new PotionEffect(PotionEffectType.ABSORPTION, (int) (20 * 3 * (1 + magia / 100 / 2d)), (int) (1 + ((magia / 10d))));
        PotionEffect ef2 = new PotionEffect(PotionEffectType.BLINDNESS, (int) (20 * 3 * (1 + magia / 100 / 2d)), 1);
        PotionEffect ef3 = new PotionEffect(PotionEffectType.INVISIBILITY, (int) (20 * 3 * (1 + magia / 100 / 2d)), 1);
        PotionEffect ef4 = new PotionEffect(PotionEffectType.SPEED, (int) (20 * 3 * (1 + magia / 100 / 2d)), 1);
        p.addPotionEffect(ef1);
        p.addPotionEffect(ef2);
        p.addPotionEffect(ef3);
        p.addPotionEffect(ef4);
        p.sendMessage(ChatColor.GREEN + "Voce sente seu corpo esvanecendo");
        KoM.act(p, "esvaneceu");
    }

    @Override
    public double getManaCost() {
        return 30;
    }

    @Override
    public double getExpRatio() {
        return 1;
    }

    @Override
    public int getMinSkill() {
        return 45;
    }

    @Override
    public Elements[] getElements() {
        return new Elements[]{Elements.Raio, Elements.Fogo, Elements.Raio};
    }

    @Override
    public int getCooldownInSeconds() {
        return 15;
    }

}
