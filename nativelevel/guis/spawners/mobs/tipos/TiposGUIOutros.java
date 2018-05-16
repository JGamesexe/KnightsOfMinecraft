package nativelevel.guis.spawners.mobs.tipos;

import nativelevel.guis.spawners.SpawnerGUIMain;
import nativelevel.utils.EntityHelp;
import nativelevel.utils.GUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SpawnEggMeta;

import java.util.ArrayList;

public class TiposGUIOutros extends GUI {

    private CreatureSpawner spawner;

    public TiposGUIOutros(CreatureSpawner spawner) {
        super(Bukkit.createInventory(null, EntityHelp.tamanhoDoBau(EntityHelp.mobs[2]), "§3MobSPAWNAER, Mobs, Outros "), spawner);
        this.spawner = (CreatureSpawner) spawner.getBlock().getState();
        cria();

    }

    private void cria() {

        botaVidros();

        ArrayList<ItemStack> outros = EntityHelp.chocadeira(EntityHelp.mobs[2]);

        for (int x = 0; x < outros.size(); x++) {
            inventory.setItem(x, outros.get(x));
        }

    }

    @Override
    public void interage(InventoryClickEvent event) {
        super.interage(event);

        Player player = (Player) event.getWhoClicked();
        int slot = event.getSlot();


        if (inventory.getItem(slot) == null || inventory.getItem(slot).getType().equals(Material.STAINED_GLASS_PANE)) {
            return;
        }

        spawner.setSpawnedType(((SpawnEggMeta) inventory.getItem(slot).getItemMeta()).getSpawnedType());
        spawner.update(true);

        SpawnerGUIMain.removeAttr(spawner, "attack");
        SpawnerGUIMain.tiraBebe(spawner);

        GUI.open(player, new SpawnerGUIMain(spawner));

    }

}