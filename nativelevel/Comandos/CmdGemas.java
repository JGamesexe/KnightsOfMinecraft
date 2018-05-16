/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Comandos;

import nativelevel.gemas.Gema;
import nativelevel.gemas.Raridade;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

/**
 * @author vntgasl
 */
public class CmdGemas implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        if (!cs.isOp())
            return true;

        try {
            Player p = (Player) cs;
            Inventory i = Bukkit.createInventory(null, InventoryType.CHEST);
            for (Gema g : Gema.values()) {
                i.addItem(Gema.gera(g, Raridade.Raro));
                i.addItem(Gema.gera(g, Raridade.Epico));
            }
            p.openInventory(i);
        } catch (Exception e) {
            cs.sendMessage("Use /gemas ");
            e.printStackTrace();
        }
        return true;
    }

}
