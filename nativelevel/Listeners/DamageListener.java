package nativelevel.Listeners;

import nativelevel.Attributes.AtributeListener;
import nativelevel.Classes.Alchemy.Alchemist;
import nativelevel.Classes.Blacksmithy.Blacksmith;
import nativelevel.Classes.*;
import nativelevel.Classes.Mage.Wizard;
import nativelevel.Dano;
import nativelevel.Equipment.WeaponDamage;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.utils.LocUtils;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author Ziden
 */
public class DamageListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void doDamage(EntityDamageByEntityEvent ev) {

        if (ev.getEntity() instanceof LivingEntity)
            if (ev.getCause() == EntityDamageEvent.DamageCause.POISON || ev.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK)
                ((LivingEntity) ev.getEntity()).setNoDamageTicks(0);

        if (ev.getDamager().getType().equals(EntityType.FIREWORK)) {
            ev.setCancelled(true);
            KoM.debug("Cancelei firework " + ev.getCause().name());
            return;
        }

        if (ev.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && ev.getDamager() instanceof Player)
            playerBate(ev);
        if (ev.isCancelled()) {
            KoM.debug("Cancelado dps playerBate");
            return;
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void reciveDamage(EntityDamageEvent ev) {

        if (ev.getEntity().hasMetadata("NPC") || ev.getEntity().getType().equals(EntityType.ARMOR_STAND)) return;

        KoM.debug("!=- reciveDamage " + ev.getEntity().getType() + " " + ev.getCause() + " " + LocUtils.loc2str(ev.getEntity().getLocation()));

        if (ev.getEntity() instanceof Player) playerLevaHit(ev);
        if (ev.isCancelled()) {
            KoM.debug("Cancelado dps playerLevaHit");
            return;
        }

    }

    //ToDo MAGIAS!!!MAGIAS!!! E MAIS MAGIAS!!! PROJETEIS !!!
    private void playerBate(EntityDamageByEntityEvent ev) {

        hitOnArmorStand(ev);
        if (ev.getEntity().hasMetadata("NPC")) ev.setCancelled(true);

        if (ev.isCancelled()) return;

        if (ev.isCancelled()) {
            KoM.debug("Sei lá como, mas tem algo cancelando antes de playerBate...");
            return;
        }

        KoM.debug("!=- playerBate chegou com " + ev.getDamage());

        Player attacker = (Player) ev.getDamager();
        ItemStack hand = attacker.getInventory().getItemInMainHand();

        WeaponDamage.getItemDamage(hand);

        if (hand != null && hand.getType() != Material.AIR && ev.getEntity() instanceof LivingEntity) {

            if (Jobs.getPrimarias(attacker).contains(Paladin.name)) Paladin.onHit(ev);
            else if (Jobs.getSecundarias(attacker).contains(Paladin.name)) Paladin.onHitSec(ev);
            else Paladin.noHit(ev);

            if (Jobs.getPrimarias(attacker).contains(Thief.name)) Thief.onHit(ev);
            else if (!Jobs.getSecundarias(attacker).contains(Thief.name)) Thief.noHit(ev);

            if (Jobs.getPrimarias(attacker).contains("Mago")) Wizard.onHit(ev);
            else Wizard.noHit(ev);

            if (Jobs.getPrimarias(attacker).contains("Lenhador")) Lumberjack.onHit(ev);

            if (Jobs.getPrimarias(attacker).contains("Minerador")) Minerador.onHit(ev);

            if (Jobs.getPrimarias(attacker).contains(Farmer.name)) Farmer.onHit(ev);

            if (Jobs.getPrimarias(attacker).contains("Alquimista")) Alchemist.onHit(ev);

            if (Jobs.getPrimarias(attacker).contains("Ferreiro")) Blacksmith.onHit(ev);

        }

        AtributeListener.entityDamage(ev);

        //Leveling de ZONAs
        ev.setDamage(ev.getDamage() * (1 - (attacker.getLevel() - ClanLand.getMobLevel(attacker.getLocation())) / 200));

        if (Thief.taInvisivel(attacker)) Thief.revela(attacker);

        if (ev.isCancelled()) KoM.debug("!=- playerBate foi cancelado");
        else KoM.debug("!=- playerBate saiu com " + ev.getDamage());
    }

    private void playerLevaHit(EntityDamageEvent ev) {

        //TODO TERMINAR NÉ

        KoM.debug("!=- playerLevaHit chegou com " + ev.getDamage() + " " + ev.getCause());

        Player receptor = (Player) ev.getEntity();

        if (Jobs.getPrimarias(receptor).contains(Paladin.name)) Paladin.onDamaged(ev);
        else if (Jobs.getSecundarias(receptor).contains(Paladin.name)) Paladin.onDamagedSec(ev);

        if (Jobs.getPrimarias(receptor).contains(Thief.name)) Thief.onDamaged(ev);
        else if (Jobs.getSecundarias(receptor).contains(Thief.name)) Thief.onDamagedSec(ev);

        if (Jobs.getPrimarias(receptor).contains(Farmer.name)) Farmer.onDamaged(ev);

        if (Jobs.getPrimarias(receptor).contains("Alquimista")) Alchemist.onDamaged(ev);

        if (Jobs.getPrimarias(receptor).contains("Ferreiro")) Blacksmith.onDamaged(ev);

        if (Jobs.getPrimarias(receptor).contains("Engenheiro")) ; //TODO LIGHTNING?

        AtributeListener.takeDamage(ev);

        //Leveling de ZONAs
        ev.setDamage(ev.getDamage() * (1 + (receptor.getLevel() - ClanLand.getMobLevel(receptor.getLocation())) / 400));

        if (!ev.isCancelled() && Thief.taInvisivel(receptor)) Thief.revela(receptor);

        if (ev.isCancelled()) KoM.debug("!=- playerLevaHit foi cancelado");
        else KoM.debug("!=- playerLevaHit saiu com " + ev.getDamage());

    }

    private void mobBate(EntityDamageByEntityEvent ev) {
    }

    private void playerLevaHitMob(EntityDamageEvent ev) {

    }

    private void hitOnArmorStand(EntityDamageByEntityEvent ev) {
        //Não vai quebrar armorstand...
        if (ev.getEntity().getType().equals(EntityType.ARMOR_STAND)) {
            if (ev.getDamager().isOp() && ((Player) ev.getDamager()).getInventory().getItemInMainHand().getType().equals(Material.TRIPWIRE_HOOK)) {
                KoM.debug("Deixei " + ev.getDamager().getName() + " quebrar ArmorStand em " + ev.getEntity().getLocation());
                return;
            } else {
                ev.setCancelled(true);
                KoM.debug("Cancelando HIT em ArmorSTAND");
                return;
            }
        }
    }

////    @EventHandler(priority = EventPriority.HIGHEST)
//    public void tomaDano2(EntityDamageEvent ev) {
//
//        KoM.debug("Recebendo do tomaDano2 " + ev.getDamage() + " em " + ev.getEntity().getName());
//
//        if(ev.getEntity().getType().equals(EntityType.ARMOR_STAND)) {
//            KoM.debug("Cancelando tomaDano2 em ArmorStand");
//            ev.setCancelled(true);PlayerInteractEventP
//            return;
//        }
//
//        if (ev.getCause() == DamageCause.SUFFOCATION) {
//            if (ev.getEntity() instanceof Monster) {
//                ev.getEntity().remove();
//            } else if (ev.getEntity().getType() == EntityType.PLAYER) {
//
//                if (KoM.database.hasRegisteredClass(ev.getEntity().getUniqueId().toString())) {
//
//                    /*
//                     if (WorldGuardKom.ehSafeZone(ev.getEntity().getLocation())) {
//                     BungeeCordKom.tp((Player) ev.getEntity(), CFG.spawnTree);
//                     } else if (ev.getEntity().getWorld().getName().equalsIgnoreCase("NewDungeon")) {
//
//                     }
//                     */
//                } else {
//                    KoM.log.info("Forcei " + ev.getEntity().getName() + " ir para localTutorial");
//                    BungeeCordKom.tp((Player) ev.getEntity(), CFG.localTutorial);
//                }
//
//            }
//        }
//
//        if (ev.getEntity().getType() == EntityType.BAT) {
//            if (ev.getEntity().getVehicle() != null) {
//                ev.setCancelled(true);
//                return;
//            }
//        }
//
//        if (ev.getEntity().getType() == EntityType.HORSE) {
//            Horse h = (Horse) ev.getEntity();
//            if (h.getPassenger() != null && h.getPassenger().getType() == EntityType.PLAYER) {
//                h.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 3, 4));
//            }
//            ev.setCancelled(true);
//            return;
//        }
//
//        if (ev.getEntity().getType() == EntityType.PLAYER) {
//
//            MetaShit.setMetaObject("tempoDano", ev.getEntity(), System.currentTimeMillis() / 1000);
//            Player p = (Player) ev.getEntity();
//            if (p.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
//                if (p.getInventory().getItemInMainHand() != null && p.getInventory().getItemInMainHand().getType() == Material.CARPET) {
//                    ev.setDamage(ev.getDamage() * 0.75);
//                }
//            }
//
//            Atadura.tomaDano(p, ev.getDamage());
//
//        }
//        if (ev.getEntity().getVehicle() != null && ev.getEntity().getVehicle().getType() == EntityType.PLAYER && ev.getEntity().getType() == EntityType.BAT) {
//            ev.setCancelled(true);
//            return;
//        }
//        if (ev.getEntity().hasMetadata("mount")) {
//            ev.setCancelled(true);
//            return;
//        }
//        if (!ev.isCancelled() && ev.getDamage() < 1) {
//            ev.setDamage(1D);
//        }
//
//        KoM.debug("final do tomaDano2 " + ev.getDamage() + " em " + ev.getEntity().getName());
//
//    }
//
//    ==================================================================================================================================================================================================
//
//    //    @EventHandler(priority = EventPriority.HIGH)
//    public void tomaDano(EntityDamageEvent ev) {
//
//        KoM.debug("recebendo do tomaDano " + ev.getDamage() + " (" + ev.getCause() + ") no [" + ev.getEntity().getType() + "] " + ev.getEntity().getName());
//
//        // evitar multiclick/multihit
//        if (ev.getEntity() instanceof LivingEntity) {
//            if (((LivingEntity) ev.getEntity()).getNoDamageTicks() > 5) {
//                ev.setCancelled(true);
//                return;
//            }
//        }
//
//        if (ev.getEntity().hasMetadata("NPC")) return;
//
//        if (ev.getEntity().getType().equals(EntityType.ARMOR_STAND)) {
//            ev.setCancelled(true);
//            KoM.debug("Cancelando tomaDano em ArmorSTAND");
//        }
//
//        if (ev.isCancelled()) return;
//
//        if (!SimpleClanKom.canDamage(ev)) {
//            ev.setCancelled(true);
//            return;
//        }
//
//        if (ev.getCause() == DamageCause.SUFFOCATION) {
//            return;
//        }
//
//        if (ev.getCause() == EntityDamageEvent.DamageCause.DROWNING) {
//            ev.setDamage(ev.getDamage() * 2);
//            return;
//        } else if (ev.getCause() == EntityDamageEvent.DamageCause.FALL) {
//            if (ev.getEntity().getType() == EntityType.PIG) {
//                ev.setCancelled(true);
//                return;
//            }
//            if (ev.getEntity().getType() == EntityType.PLAYER) {
//                if (ClanLand.getTypeAt(ev.getEntity().getLocation()).equalsIgnoreCase("SAFE")) {
//                    ev.setCancelled(true);
//                    return;
//                }
//                if (ev.getDamage() >= 2) {
//                    ((Player) ev.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 10, 1));
//                    ((Player) ev.getEntity()).sendMessage(ChatColor.RED + L.get("HitLeg"));
//                }
//            }
//        } else if (ev.getCause() == EntityDamageEvent.DamageCause.LIGHTNING) {
//            ev.setDamage(ev.getDamage() * 1.5);
//            if (ev.getEntity() instanceof LivingEntity) {
//                if (ev.getEntity().getType() == EntityType.PLAYER) {
//                    // engenheiro primário imune eletrecidade
//                    if (Jobs.getJobLevel("Engenheiro", (Player) ev.getEntity()) == 1) {
//                        ev.setCancelled(true);
//                        return;
//                    }
//                }
//                Mobs.doRandomKnock((LivingEntity) ev.getEntity(), 0.9f);
//            }
//        } else if (ev.getCause() == EntityDamageEvent.DamageCause.FIRE || ev.getCause() == EntityDamageEvent.DamageCause.LAVA) {
//            if (ev.getEntity() instanceof LivingEntity) {
//                if (ev.getEntity().getType() == EntityType.PLAYER) {
//                    // engenheiro primário imune eletrecidade
//                    if (ev.getDamage() > 0 && !ev.isCancelled() && Jobs.getJobLevel("Fazendeiro", (Player) ev.getEntity()) == 1) {
//                        ev.setDamage(ev.getDamage() * 2);
//                    }
//                }
//            }
//        } else if (ev.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION || ev.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
//            if (ev.getEntity() instanceof Player) {
//                // explosões power
//                ev.setDamage(((Player) ev.getEntity()).getMaxHealth() / 3);
//            }
//        }
//
//        boolean mobHitou = false;
//
//        ///// PLAYER TOMA DE KKER COISA
//        if (ev.getEntity() instanceof Player) {
//
//            ExecutaSkill.playerToma(ev);
//            KoM.debug("Dano no final do playertoma " + ev.getDamage());
//            if (ev.isCancelled()) {
//                return;
//            }
//        }
//
//        if (ev instanceof EntityDamageByEntityEvent) {
//            EntityDamageByEntityEvent ev2 = (EntityDamageByEntityEvent) ev;
//
//            if (ev.getEntity().getType() == EntityType.PLAYER && ev2.getDamager().getType() == EntityType.WOLF && ev.getEntity().getWorld().getName().equalsIgnoreCase("NewDungeon")) {
//                ev.setCancelled(true);
//                return;
//            } else {
//                this.rolaAPorrada(ev2);
//            }
//            if (ev2.getDamager() instanceof Monster || (ev2.getDamager() instanceof Projectile && ((Projectile) ev2.getDamager()).getShooter() instanceof Monster)) {
//                mobHitou = true;
//            }
//        }
//
//        if (ev.getEntity() instanceof Player || ev.getEntity() instanceof Animals) {
//
//            if (!mobHitou && ev.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK || ev.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
//                ev.setDamage(ev.getDamage() * 0.75);
//            }
//
//            Location l = ev.getEntity().getLocation();
//            l.setY(l.getBlockY() + 1.1D);
//            if (ev.getDamage() >= 1 && !ev.isCancelled()) {
//                ev.getEntity().getLocation().getWorld().playEffect(ev.getEntity().getLocation().add(0.0D, 1.0D, 0.0D), Effect.STEP_SOUND, 152);
//            }
//        }
//
//        KoM.debug("final do tomaDano " + ev.getDamage() + " em " + ev.getEntity().getName());
//    }
//
//    public void rolaAPorrada(final EntityDamageByEntityEvent event) {
//
//        KoM.debug("Dano no começo do rolaporrada " + event.getDamage());
//
//        if (event.getCause() == DamageCause.BLOCK_EXPLOSION || event.getCause() == DamageCause.ENTITY_EXPLOSION) {
//            if (WorldGuardKom.ehSafeZone(event.getEntity().getLocation())) {
//                event.setCancelled(true);
//                return;
//            }
//        }
//
//        if (!(event.getEntity().getType() == EntityType.PLAYER) && event.getDamager().getType() == EntityType.PLAYER) {
//            Farmer.coletaDropExtraDeAnimal((Player) event.getDamager(), event.getEntity());
//        }
//
//        if (event.getDamager().getType() == EntityType.WOLF) {
//            if (event.getEntity() instanceof Monster) {
//                event.setDamage(event.getDamage() / 4);
//            } else if (event.getEntity().getType() == EntityType.PLAYER) {
//                if (WorldGuardKom.ehSafeZone(event.getEntity().getLocation())) {
//                    event.setCancelled(true);
//                    return;
//                } else {
//                    event.setDamage(event.getDamage() / 2);
//                }
//            }
//        }
//
//        if (event.getDamager().hasMetadata("prendeu")) {
//            event.setCancelled(true);
//            return;
//        }
//
//        if (event.getEntity() instanceof ItemFrame) {
//            event.setCancelled(true);
//            return;
//        }
//        // nao matar animais na vila
//        if (event.getEntity().getWorld().getName().equalsIgnoreCase("vila")) {
//            if (event.getEntity() instanceof Creature) {
//                if (event.getDamager() instanceof Player) {
//                    if (!((Player) event.getDamager()).isOp()) {
//                        event.setCancelled(true);
//                        return;
//                    }
//                }
//            }
//        }
//
//        KoM.debug("Meio rola porrada dano = " + event.getDamage());
//
//        IronGolem.dano(event);
//        if (event.isCancelled()) {
//            return;
//        }
//
//        if (event.getEntity().getType() == EntityType.WOLF) {
//            event.setDamage(event.getDamage() / 5);
//        }
//
//        if (event.getEntity() instanceof Horse) {
//            event.setDamage(event.getDamage() / 3);
//            String tipo = ClanLand.getTypeAt(event.getEntity().getLocation());
//            if (tipo.equalsIgnoreCase("SAFE")) {
//                event.setCancelled(true);
//            } else if (tipo.equalsIgnoreCase("CLAN")) {
//                Clan c = ClanLand.getClanAt(event.getEntity().getLocation());
//                Horse h = (Horse) event.getEntity();
//                if (h.getOwner() != null) {
//                    AnimalTamer dono = h.getOwner();
//                    Clan clanDono = ClanLand.manager.getClanByPlayerName(dono.getName());
//                    // se o cavalo ta na terra do dono
//                    boolean cavaloImune = false;
//
//                    if (clanDono != null && clanDono.getTag().equalsIgnoreCase(c.getTag())) {
//                        cavaloImune = true;
//                    }
//                    if (event.getDamager().getType() == EntityType.PLAYER) {
//                        ClanPlayer cp = ClanLand.manager.getClanPlayer((Player) event.getDamager());
//                        if (cp != null) {
//                            if (cp.getTag().equalsIgnoreCase(clanDono.getTag())) {
//                                cavaloImune = false;
//                            }
//                        }
//                    }
//                    if (cavaloImune) {
//                        event.setCancelled(true);
//                    }
//                }
//            }
//        }
//
//        if (event.getDamager() instanceof Projectile) {
//            Projectile projetil = (Projectile) event.getDamager();
//            LivingEntity atirador = (LivingEntity) projetil.getShooter();
//
//            if (projetil.hasMetadata("visual")) {
//                event.setCancelled(true);
//                return;
//            }
//
//            if (projetil.getCustomName() != null && projetil.getCustomName().equalsIgnoreCase("Foguete VIP")) {
//                event.setCancelled(true);
//                return;
//            }
//
//            // NEW MAGE SPELLS
//            if (projetil.hasMetadata("spell")) {
//                event.setCancelled(true);
//                MageSpell spell = (MageSpell) MetaShit.getMetaObject("spell", projetil);
//                spell.spellHit((LivingEntity) event.getEntity(), projetil.getLocation(), projetil);
//                return;
//            }
//
//            String meta = MetaShit.getMetaString("tipoTiro", projetil);
//            if (meta != null && meta.equalsIgnoreCase("kaboom")) {
//                event.setDamage(2D);
//            }
//
//            if (atirador != null && atirador.getType() == EntityType.PLAYER && event.getEntity().getType() == EntityType.PLAYER && event.getEntity().getWorld().getName().equalsIgnoreCase("NewDungeon")) {
//                event.setCancelled(true);
//                return;
//            }
//
//            if (projetil instanceof SmallFireball) {
//                event.getEntity().setFireTicks(100);
//            }
//
//            if (event.getEntity() instanceof Monster) {
//                Object objEf = MetaShit.getMetaObject("efeitos", event.getEntity());
//                if (objEf != null) {
//                    HashSet<Mobs.EfeitoMobs> efeitos = (HashSet) objEf;
//                    if (efeitos.contains(Mobs.EfeitoMobs.antiProjetil)) {
//                        event.setCancelled(true);
//                    }
//                }
//            }
//
//            if (atirador == null) {
//                return;
//            }
//            if (atirador.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
//                atirador.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
//            }
//
//            if (atirador instanceof Player && event.getEntity() instanceof Player) {
//                if (!SimpleClanKom.canPvp((Player) atirador, (Player) event.getEntity())) {
//                    event.setCancelled(true);
//                    return;
//                }
//                if (event.getDamage() > 0 && !event.isCancelled()) {
//                    MetaShit.setMetaObject("logout", event.getEntity(), System.currentTimeMillis() / 1000);
//                }
//            }
//
//            // se um player atira num mob
//            if (event.getEntity() instanceof Monster && atirador instanceof Player) {
//                Mobs.playerBateEmMob((Player) atirador, (Monster) event.getEntity(), event);
//                // se um mob atira num player
//            } else if (event.getEntity() instanceof Player && atirador instanceof Monster) {
//                Mobs.enemyCauseDamage((Monster) atirador, (Player) event.getEntity(), event);
//            }
//            meta = MetaShit.getMetaString("tipoTiro", projetil);
//            if (meta != null && meta.equalsIgnoreCase("kaboom")) {
//                if (event.getEntity() instanceof LivingEntity) {
//                    event.setDamage(event.getDamage() + 2D);
//                    //if (projetil.hasMetadata("modDano")) {
//                    //    event.setDamage(event.getDamage() * (double) MetaShit.getMetaObject("modDano", projetil));
//                    //cp = Terrenos.manager.getClanPlayer(ev.getEntity().getUniqueId());
//                    //}
//
//                    double alturaflecha = projetil.getLocation().getY();
//                    double diferenca = alturaflecha - event.getEntity().getLocation().getY();
//
//                    if (PlayerSpec.temSpec((Player) atirador, PlayerSpec.Fuzileiro)) {
//                        event.setDamage(event.getDamage() * 1.3);
//                    } else if (PlayerSpec.temSpec((Player) atirador, PlayerSpec.Inventor)) {
//                        event.setDamage(event.getDamage() * 0.6);
//                    }
//
//                    double pode = 1.5;
//                    if (event.getEntity() instanceof Player) {
//                        if (((Player) event.getEntity()).isSneaking()) {
//                            pode = 1.19;
//                        }
//                    }
//                    //BOOM PERANHA
//                    if (diferenca > pode) {
//                        if (atirador instanceof Player) {
//                            ((Player) atirador).sendMessage(ChatColor.GREEN + "! Head Shot !");
//                            event.setDamage(event.getDamage() * 3.5);
//                            if (event.getEntity().getType() == EntityType.PLAYER) {
//                                Player tomou = (Player) event.getEntity();
//                                if (tomou.getInventory().getHelmet() != null && tomou.getInventory().getHelmet().getType() == Material.GOLD_HELMET) {
//                                    event.setDamage(event.getDamage() * 0.5);
//                                    tomou.getInventory().getHelmet().setDurability((short) (tomou.getInventory().getHelmet().getDurability() + 5));
//                                    if (tomou.getInventory().getHelmet().getDurability() >= tomou.getInventory().getHelmet().getType().getMaxDurability()) {
//                                        tomou.getInventory().setHelmet(null);
//                                    }
//                                }
//                            }
//
//                        } else {
//                            event.setDamage(event.getDamage() * 0.9);
//                        }
//                    }
//
//                    //((LivingEntity) event.getEntity()).damage(5D, atirador);
//                    if (event.getEntity().getType() == EntityType.PLAYER && atirador.getType() == EntityType.PLAYER) {
//                        ClanPlayer atirou = ClanLand.manager.getClanPlayer((Player) atirador);
//                        ClanPlayer tomou = ClanLand.manager.getClanPlayer((Player) event.getEntity());
//                        if (atirou != null && tomou != null && (atirou.isAlly(tomou.toPlayer()) || atirou.getTag().equalsIgnoreCase(tomou.getTag()))) {
//                            event.setDamage(0D);
//                            event.setCancelled(true);
//                            return;
//                        }
//                    }
//
//                    if (event.getEntity() instanceof Monster) {
//                        event.setDamage(event.getDamage() / 1.5);
//                    }
//
//                    PlayEffect.play(VisualEffect.LAVA, event.getEntity().getLocation(), "num:3");
//                    event.getEntity().getWorld().playEffect(event.getEntity().getLocation(), Effect.SMOKE, 10);
//                    if (atirador.getType() == EntityType.PLAYER) {
//                        ((Player) atirador).sendMessage(ChatColor.RED + "*boom*");
//                    }
//                    if (KoM.debugMode) {
//                        KoM.log.info("Dano Final Bonka: " + event.getDamage());
//                    }
//                }
//            }
//
//            if (meta != null && meta.equalsIgnoreCase("autoDispenser")) {
//                event.getEntity().getWorld().playEffect(event.getEntity().getLocation(), Effect.SMOKE, 10);
//                if (event.getEntity() instanceof LivingEntity) {
//                    ((LivingEntity) event.getEntity()).damage(4D, atirador);
//                }
//            }
//            // se tomou magia
//            if (event.getDamager().getType() == EntityType.ARROW || event.getDamager().getType() == EntityType.SNOWBALL || event.getDamager().getType() == EntityType.FIREBALL || event.getDamager().getType() == EntityType.SMALL_FIREBALL) {
//                if (event.getEntity().getType() == EntityType.PLAYER) {
//                    Player tomou = (Player) event.getEntity();
//
//                    /*
//                     AttributeInfo info = KnightsOfMania.database.getAtributos(tomou);
//                     if (KnightsOfMania.debugMode) {
//                     tomou.sendMessage("Tomei " + event.getDamage());
//                     }
//                     */
//                    //double ratio = Attributes.calcDamageAbsorbtion(info.attributes.get(Attr.tuffness));
//                    //double dano = event.getDamage() * (1 - ratio);
//                    //event.setDamage(dano);
//                    //if (KnightsOfMania.debugMode) {
//                    //    tomou.sendMessage("Com calculo de rigidez tomei " + event.getDamage() + " - " + dano + " ratio=" + ratio);
//                    //}
//                }
//            }
//            Thief.bonusDanoDeLonge(event);
//
//            //////// CALCULANDO BONUS /////////
//            if (projetil.hasMetadata("modDano")) {
//
//                double ratio = (double) MetaShit.getMetaObject("modDano", projetil);
//                if (KoM.debugMode) {
//                    KoM.log.info("Dano Inicial: " + event.getDamage());
//                }
//                if (KoM.debugMode) {
//                    KoM.log.info("Mod dano EntityDamage: " + ratio);
//                }
//                event.setDamage(event.getDamage() * ratio);
//                if (KoM.debugMode) {
//                    KoM.log.info("Dano Final: " + ratio);
//                }
//            }
//        } else if (event.getEntity() instanceof Monster && event.getDamager() instanceof Player) {
//            Mobs.playerBateEmMob((Player) event.getDamager(), (Monster) event.getEntity(), event);
//        }
//        if (event.getEntity() instanceof Monster) {
//            KoM.debug("inicio mob efeitos = " + event.getDamage());
//            HashSet<Mobs.EfeitoMobs> efeitos = (HashSet) MetaShit.getMetaObject("efeitos", event.getEntity());
//            if (efeitos != null) {
//                if (efeitos.contains(Mobs.EfeitoMobs.bateFogo)) {
//                    if (event.getCause() == EntityDamageEvent.DamageCause.STARVATION || event.getCause() == EntityDamageEvent.DamageCause.THORNS || event.getCause() == EntityDamageEvent.DamageCause.SUICIDE || event.getCause() == EntityDamageEvent.DamageCause.WITHER || event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION || event.getCause() == EntityDamageEvent.DamageCause.FIRE || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK || event.getCause() == EntityDamageEvent.DamageCause.LAVA || event.getCause() == EntityDamageEvent.DamageCause.MELTING) {
//                        event.setCancelled(true);
//                        event.setDamage(0D);
//                    }
//
//                }
//                if (efeitos.contains(Mobs.EfeitoMobs.fantasma)) {
//                    if (event.getCause() == EntityDamageEvent.DamageCause.CONTACT || event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK || event.getCause() == EntityDamageEvent.DamageCause.SUICIDE) {
//                        event.setDamage(0.5d);
//                    }
//                }
//            }
//        }
//        if (event.getEntity() instanceof Player && event.getDamager() instanceof Monster) {
//            Monster m = (Monster) event.getDamager();
//            Mobs.enemyCauseDamage(m, (Player) event.getEntity(), event);
//        }
//
//        // player hits something..
//        ExecutaSkill.playerBate(event);
//        KoM.debug("Dano no final do rolaporrada " + event.getDamage());
//    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void danoHighest(EntityDamageByEntityEvent ev) {
        KoM.debug("Recebendo do danoHighest " + ev.getDamage() + " em " + ev.getEntity().getName() + " cancelado " + ev.isCancelled());
        KoM.debug("DAMAGER " + (ev.getDamager() instanceof Player) + "  canceled " + ev.isCancelled() + "  dano " + ev.getDamage());
        if (ev.getDamager() instanceof Player && !ev.isCancelled() && ev.getDamage() > 0) {
            KoM.dano.mostraDano((Player) ev.getDamager(), ev.getDamage(), Dano.BATI);
        }

    }

}
