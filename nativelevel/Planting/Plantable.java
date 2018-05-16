/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Planting;

import nativelevel.Jobs;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vntgasl
 */
public class Plantable {

    public Plantable(Material m, byte data, String skill, int minSkill, double expRatio) {
        this.m = m;
        this.data = data;
        this.classe = Jobs.Classe.valueOf(skill);
        this.difficulty = minSkill;
        this.expRatio = expRatio;
    }

    public ItemStack getIcon() {
        ItemStack ss = new ItemStack(m, 1, data);
        ItemMeta meta = ss.getItemMeta();
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.YELLOW + classe.name());
        lore.add(ChatColor.GREEN + "Dificuldade: " + ChatColor.YELLOW + difficulty);
        lore.add(ChatColor.GREEN + "Bonus XP: " + ChatColor.YELLOW + expRatio);
        meta.setLore(lore);
        ss.setItemMeta(meta);
        return ss;
    }

    public double expRatio;
    public Material m;
    public byte data = 0;
    public Jobs.Classe classe; // MEDIUM will be calculated
    public int difficulty;

}
