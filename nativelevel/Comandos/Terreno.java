/*

 ╭╮╭━╮╱╱╭━╮╭━╮
 ┃┃┃╭╯╱╱┃┃╰╯┃┃
 ┃╰╯╯╭━━┫╭╮╭╮┃
 ┃╭╮┃┃╭╮┃┃┃┃┃┃
 ┃┃┃╰┫╰╯┃┃┃┃┃┃
 ╰╯╰━┻━━┻╯╰╯╰╯

 Desenvolvedor: ZidenVentania
 Colaboradores: NeT32, Gabripj, Feldmann
 Patrocionio: InstaMC

 */
package nativelevel.Comandos;

import nativelevel.CFG;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.rankings.Estatistica;
import nativelevel.rankings.RankDB;
import nativelevel.sisteminhas.ClanLand;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Terreno implements CommandExecutor {

    private static void sendHelp(CommandSender cs, boolean leader) {
        ClanLand.msg(cs, L.m("Use: /terreno amigo add (nome)"));
        ClanLand.msg(cs, L.m("Use: /terreno amigo remover (nome)"));
        ClanLand.msg(cs, L.m("Use: /terreno amigo limpar"));
        ClanLand.msg(cs, L.m("Use: /terreno info"));
        if (leader) {
            ClanLand.msg(cs, ChatColor.YELLOW + L.m("|____________Lider____________|"));
            ClanLand.msg(cs, ChatColor.YELLOW + L.m("Use: /terreno conquistar"));
            ClanLand.msg(cs, ChatColor.YELLOW + L.m("Use: /terreno deixar"));
            ClanLand.msg(cs, ChatColor.YELLOW + L.m("Use: /terreno publico"));
            ClanLand.msg(cs, ChatColor.YELLOW + L.m("Use: /terreno dono (nome)"));
        }
    }

    public static ArrayList<String> getGuildasPerto(Location l) {

        int x = l.getChunk().getBlock(8, 0, 8).getLocation().getBlockX();
        int y = l.getChunk().getBlock(8, 0, 8).getLocation().getBlockZ();

        Location up = new Location(l.getWorld(), x + 16, 0, y);
        Location down = new Location(l.getWorld(), x - 16, 0, y);
        Location right = new Location(l.getWorld(), x, 0, y + 16);
        Location left = new Location(l.getWorld(), x, 0, y - 16);
        Location upright = new Location(l.getWorld(), x + 16, 0, y + 16);
        Location upleft = new Location(l.getWorld(), x + 16, 0, y - 16);
        Location downright = new Location(l.getWorld(), x - 16, 0, y + 16);
        Location downleft = new Location(l.getWorld(), x - 16, 0, y - 16);

        Location[] locs = new Location[]{l, up, down, right, left, upright, upleft, downright, downleft};

        ArrayList<String> tipos = new ArrayList<>();

        tipos.add(ClanLand.getTypeAt(l));

        tipos.add(ClanLand.getTypeAt(up));
        tipos.add(ClanLand.getTypeAt(down));
        tipos.add(ClanLand.getTypeAt(right));
        tipos.add(ClanLand.getTypeAt(left));

        tipos.add(ClanLand.getTypeAt(upright));
        tipos.add(ClanLand.getTypeAt(upleft));
        tipos.add(ClanLand.getTypeAt(downright));
        tipos.add(ClanLand.getTypeAt(downleft));

        int index = 0;

        for (String tipo : tipos) {
            if (tipo.equalsIgnoreCase("CLAN")) tipos.set(index, ClanLand.getClanAt(locs[index]).getTag());
            index++;
        }

//        ArrayList<Clan> clans = new ArrayList<>();
//
//        clans.add(ClanLand.getClanAt(up));
//        clans.add(ClanLand.getClanAt(down));
//        clans.add(ClanLand.getClanAt(right));
//        clans.add(ClanLand.getClanAt(left));
//        clans.add(ClanLand.getClanAt(upright));
//        clans.add(ClanLand.getClanAt(upleft));
//        clans.add(ClanLand.getClanAt(downright));
//        clans.add(ClanLand.getClanAt(downleft));

        return tipos;
    }

    public static boolean temGuildaPerto(Player p, Location l, boolean sogld) {

        ClanPlayer cp = ClanLand.manager.getClanPlayer(p);
        String minhaTag;

        if (cp != null) minhaTag = cp.getTag();
        else minhaTag = "Dacaris, nega do fogo, dona da areia, nega foda rainha da vida";


        List<String> glds = getGuildasPerto(l);

        for (String gld : glds) {
            if (sogld) {
                if (!gld.equalsIgnoreCase(minhaTag)
                        && !gld.equalsIgnoreCase("WILD")
                        && !gld.equalsIgnoreCase("WARZ")
                        && !gld.equalsIgnoreCase("SAFE")) return true;
            } else {
                if (!gld.equalsIgnoreCase("WILD") && !gld.equalsIgnoreCase(minhaTag)) return true;
            }
        }
//        int x;
//        int y;
//        x = l.getChunk().getBlock(1, 0, 1).getLocation().getBlockX();
//        y = l.getChunk().getBlock(1, 0, 1).getLocation().getBlockZ();
//        Location up = new Location(l.getWorld(), x + 16, 0, y);
//        Clan cUp = ClanLand.getClanAt(up);
//        // nao tem clan em cima ou não é meu clan
//        if (cUp == null || !cUp.getTag().equalsIgnoreCase(minhaTag)) {
//            if (cUp != null && !cUp.getTag().equalsIgnoreCase(minhaTag)) {
//                return true;
//            }
//            Location down = new Location(l.getWorld(), x - 16, 0, y);
//            Clan cDown = ClanLand.getClanAt(down);
//            if (cDown == null || !cDown.getTag().equalsIgnoreCase(minhaTag)) {
//                if (cDown != null && !cDown.getTag().equalsIgnoreCase(minhaTag)) {
//                    return true;
//                }
//                Location left = new Location(l.getWorld(), x, 0, y + 16);
//                Clan cLeft = ClanLand.getClanAt(left);
//                if (cLeft == null || !cLeft.getTag().equalsIgnoreCase(minhaTag)) {
//                    if (cLeft != null && !cLeft.getTag().equalsIgnoreCase(minhaTag)) {
//                        return true;
//                    }
//                    Location right = new Location(l.getWorld(), x, 0, y - 16);
//                    Clan cRight = ClanLand.getClanAt(right);
//                    if (cRight == null || !cRight.getTag().equalsIgnoreCase(minhaTag)) {
//                        if (cRight != null && !cRight.getTag().equalsIgnoreCase(minhaTag)) {
//                            return true;
//                        }
//                        right = new Location(l.getWorld(), x - 16, 0, y - 16);
//                        cRight = ClanLand.getClanAt(right);
//                        if (cRight == null || !cRight.getTag().equalsIgnoreCase(minhaTag)) {
//                            if (cRight != null && !cRight.getTag().equalsIgnoreCase(minhaTag)) {
//                                return true;
//                            }
//                            right = new Location(l.getWorld(), x + 16, 0, y - 16);
//                            cRight = ClanLand.getClanAt(right);
//                            if (cRight == null || !cRight.getTag().equalsIgnoreCase(minhaTag)) {
//                                if (cRight != null && !cRight.getTag().equalsIgnoreCase(minhaTag)) {
//                                    return true;
//                                }
//                                right = new Location(l.getWorld(), x - 16, 0, y + 16);
//                                cRight = ClanLand.getClanAt(right);
//                                if (cRight == null || !cRight.getTag().equalsIgnoreCase(minhaTag)) {
//                                    if (cRight != null && !cRight.getTag().equalsIgnoreCase(minhaTag)) {
//                                        return true;
//                                    }
//                                    right = new Location(l.getWorld(), x + 16, 0, y + 16);
//                                    cRight = ClanLand.getClanAt(right);
//                                    if (cRight == null || !cRight.getTag().equalsIgnoreCase(minhaTag)) {
//                                        if (cRight != null && !cRight.getTag().equalsIgnoreCase(minhaTag)) {
//                                            return true;
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
        return false;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if (cs instanceof ConsoleCommandSender) {
            ClanLand.msg(cs, L.m("Admin, favor entrar no jogo."));
            return true;
        }
        boolean leader = ClanLand.manager.getClanPlayer((Player) cs) == null ? false : ClanLand.manager.getClanPlayer((Player) cs).isLeader();
        if (args.length == 0 || args.length > 3) {
            sendHelp(cs, leader);
            return true;
        }
        Player p = (Player) cs;
        if (!p.isOp() && p.getWorld().getName().equalsIgnoreCase("woe") || p.getWorld().getName().equalsIgnoreCase("vila") || p.getWorld().getName().equalsIgnoreCase("NewDungeon")) {
            p.sendMessage(ChatColor.RED + L.m("Este comando nao funciona aqui !"));
            return true;
        }
        Clan c = ClanLand.getClanAt(p.getLocation());
        ClanPlayer cp = ClanLand.manager.getClanPlayer(p);
        if (ClanLand.isSafeZone(p.getLocation())) {
            if (!p.isOp()) {
                ClanLand.msg(p, L.m("Voce nao tem poder aqui, Voce esta numa Cidade."));
                return true;
            }
        }
        if (ClanLand.isWarZone(p.getLocation())) {
            if (!p.isOp()) {
                ClanLand.msg(p, L.m("Voce nao tem poder aqui, Voce esta numa WarZone."));
                return true;
            }
        }
        if (args.length == 3) {
            if (!args[0].equals("mem") && !args[0].equals("amigo")) {
                sendHelp(cs, leader);
                return true;
            }
            if (c == null || cp == null) {
                ClanLand.msg(p, L.m("Voce nem tem um clan."));
                return true;
            }
            if (!c.getTag().equals(cp.getClan().getTag())) {
                ClanLand.msg(p, L.m("Este terreno nao eh do seu clan."));
                return true;
            }
            if (ClanLand.getOwnerAt(p.getLocation()).equals("none")) {
                ClanLand.msg(p, L.m("Esse terreno eh publico do clan."));
                return true;
            }
            if (!ClanLand.getOwnerAt(p.getLocation()).equals(p.getUniqueId().toString()) && !leader) {
                ClanLand.msg(p, L.m("Voce nao eh dono desse terreno."));
                return true;
            }
            String player = args[2];
            Player p2 = Bukkit.getPlayer(player);
            if (p2 != null) {
                UUID uuid = p2.getUniqueId();
                if (!c.getAllMembers().contains(ClanLand.manager.getClanPlayer(player))) {
                    ClanLand.msg(p, L.m("Este jogador nao eh do seu clan."));
                    return true;
                }
                if (args[1].equals("add")) {
                    if (ClanLand.isMemberAt(p.getLocation(), uuid)) {
                        ClanLand.msg(p, L.m("Este jogador ja eh membro deste terreno."));
                        return true;
                    }
                    ClanLand.addMemberAt(p.getLocation(), p2);
                    ClanLand.msg(p, L.m("O jogador %" + ChatColor.GREEN + " agora eh membro deste terreno.", player));
                    return true;
                } else if (args[1].equals("rem") || args[1].equals("remover")) {
                    if (!ClanLand.isMemberAt(p.getLocation(), uuid)) {
                        ClanLand.msg(p, L.m("Este jogador nao eh membro deste terreno."));
                        return true;
                    }
                    ClanLand.removeMemberAt(p.getLocation(), uuid);
                    ClanLand.msg(p, L.m("O jogador %" + ChatColor.GREEN + " agora nao eh mais membro deste terreno.", player));
                    return true;
                } else {
                    sendHelp(cs, leader);
                    return true;
                }
            } else {
                ClanLand.msg(p, L.m("O jogador %" + ChatColor.GREEN + " está offline.", player));
                return true;
            }
        } else if (args.length == 2) {
            if (args[0].equals("membros") || args[0].equals("mem")) {
                if (ClanLand.getOwnerAt(p.getLocation()).equals("none")) {
                    ClanLand.msg(p, L.m("Esse terreno eh publico do clan."));
                    return true;
                }
                if (args[1].equals("limpar")) {
                    if (cp == null) {
                        ClanLand.msg(p, L.m("Voce nem tem um clan."));
                        return true;
                    }
                    if (!c.getTag().equals(cp.getClan().getTag())) {
                        ClanLand.msg(p, L.m("Este terreno nao eh do seu clan."));
                        return true;
                    }
                    if (!ClanLand.getOwnerAt(p.getLocation()).equals(p.getUniqueId().toString()) && !leader) {
                        ClanLand.msg(p, L.m("Voce nao eh dono desse terreno."));
                        return true;
                    }
                    ClanLand.clearMembersAt(p.getLocation());
                    ClanLand.msg(p, L.m("Seu terreno nao tem mais membros."));
                    return true;
                } else {
                    sendHelp(cs, leader);
                    return true;
                }
            } else if (args[0].equals("dono")) {
                if (cp == null) {
                    ClanLand.msg(p, L.m("Voce nem tem um clan."));
                    return true;
                } else if (!leader) {
                    ClanLand.msg(p, L.m("Voce nao eh lider do seu clan."));
                    return true;
                }
                if (c == null || !c.getTag().equals(cp.getClan().getTag())) {
                    ClanLand.msg(p, L.m("Este terreno nem eh do seu clan."));
                    return true;
                }
                Player alvo = Bukkit.getPlayer((args[1]));
                if (c != null && c.getAllAllyMembers() != null && !c.getAllMembers().contains(ClanLand.manager.getClanPlayer(alvo))) {
                    ClanLand.msg(p, L.m("Este jogador nao esta no seu clan."));
                    return true;
                }
                String owner[] = ClanLand.getOwnerAt(p.getLocation());
                if (owner.length != 0 && owner[0].equals(alvo.getUniqueId().toString())) {
                    ClanLand.msg(p, L.m("Esse terreno ja eh do jogador %", args[1]));
                    return true;
                }
                ClanLand.setOwnerAt(p.getLocation(), alvo);
                ClanLand.msg(p, L.m("Esse terreno agora eh do jogador %" + ChatColor.GREEN + ", do seu clan.", args[1]));
                ClanLand.update(p, p.getLocation());
                return true;
            } else {
                sendHelp(cs, leader);
                return true;
            }
        } else if (args[0].equals("publico") || args[0].equals("pub")) {
            if (cp == null) {
                ClanLand.msg(p, L.m("Voce nem tem um clan."));
                return true;
            } else if (!leader) {
                ClanLand.msg(p, L.m("Voce nao eh lider do seu clan."));
                return true;
            }
            if (c == null || !c.getTag().equals(cp.getClan().getTag())) {
                ClanLand.msg(p, L.m("Este terreno nem eh do seu clan."));
                return true;
            }
            if (ClanLand.getOwnerAt(p.getLocation()).equals("none")) {
                ClanLand.msg(p, L.m("Este terreno ja eh publico."));
                return true;
            }
            ClanLand.msg(p, L.m("Este terreno agora eh publico."));
            ClanLand.setOwnerAt(p.getLocation(), null);
            ClanLand.update(p, p.getLocation());
            return true;
        } else if (args[0].equals("deixar")) {
            if (cp == null) {
                ClanLand.msg(p, L.m("Voce nem tem um clan."));
                return true;
            } else if (!leader) {
                ClanLand.msg(p, L.m("Voce nao eh lider do seu clan."));
                return true;
            }
            if (c == null) {
                ClanLand.msg(p, L.m("Este terreno nem tem dono."));
                return true;
            } else if (!c.getTag().equals(cp.getTag())) {
                ClanLand.msg(p, L.m("Voce nao pode deixar terreno do clan %" + ChatColor.GREEN + ".", c.getColorTag()));
                return true;
            }
            ClanLand.removeClanAt(p.getLocation());
            ClanLand.msg(p, L.m("Este terreno nao eh mais do seu clan."));
            ClanLand.update(p, p.getLocation());
            return true;
        } else if (args[0].equals("conquistar") || args[0].equals("conq")) {
            if (p.getWorld().getName().equalsIgnoreCase("vila")) {
                p.sendMessage(ChatColor.RED + L.m("Nao se pode conquistar Rhodes... pelo menos não sem um bom exercito !"));
                return true;
            }
            if (ClanLand.isSafeZone(p.getLocation()) || ClanLand.isWarZone(p.getLocation())) {
                p.sendMessage(ChatColor.RED + L.m("Voce nao pode conquistar aqui !"));
                return true;
            }
            if (cp == null || cp.getClan() == null) {
                ClanLand.msg(p, L.m("Voce nao tem um clan."));
                return true;
            } else if (!leader) {
                ClanLand.msg(p, L.m("Voce nao eh lider do seu clan."));
                return true;
            }
            ////// SE JA TEM UM CLAN AQUI
            if (c != null) {
                if (c.getTag().equals(cp.getTag())) {
                    ClanLand.msg(p, L.m("Seu clan ja eh dono desse terreno."));
                    return true;
                }
                if (KoM.debugMode) {
                    KoM.log.info("TEM UM CLA INIMIGO AKI");
                }
                ClanLand.msg(p, L.m("O clan %" + ChatColor.GREEN + " ja eh dono desse terreno.", c.getColorTag()));
                if (c.isRival(cp.getTag())) {
                    int ptosPilhagem = ClanLand.getPtosPilagem(cp.getTag(), c.getTag());
                    if (ptosPilhagem < CFG.custoAbaixarPower) {
                        ClanLand.msg(p, L.m("Voce precisa de % pontos de pilhagem para dominar esta area", CFG.custoAbaixarPower));
                        return true;
                    }
                    ptosPilhagem -= CFG.custoAbaixarPower;
                    String tagInimiga = c.getTag();
                    int x = p.getLocation().getChunk().getBlock(1, 0, 1).getLocation().getBlockX();
                    int y = p.getLocation().getChunk().getBlock(1, 0, 1).getLocation().getBlockZ();
                    Location up = new Location(p.getWorld(), x + 16, 0, y);
                    Clan cUp = ClanLand.getClanAt(up);
                    // se naõ tem clan em cima
                    if (cUp != null && cUp.getTag().equalsIgnoreCase(tagInimiga)) {
                        Location down = new Location(p.getWorld(), x - 16, 0, y);
                        Clan cDown = ClanLand.getClanAt(down);
                        // se nao tem clan em baixo
                        if (cDown != null && cDown.getTag().equalsIgnoreCase(tagInimiga)) {
                            Location left = new Location(p.getWorld(), x, 0, y + 16);
                            Clan cLeft = ClanLand.getClanAt(left);
                            if (cLeft != null && cLeft.getTag().equalsIgnoreCase(tagInimiga)) {
                                Location right = new Location(p.getWorld(), x, 0, y - 16);
                                Clan cRight = ClanLand.getClanAt(right);
                                if (cRight != null && cRight.getTag().equalsIgnoreCase(tagInimiga)) {
                                    ClanLand.msg(p, L.m("Voce tem que dominar inimigos pelas bordas !"));
                                    return true;
                                }
                            }
                        }
                    }
                    if (!ClanLand.econ.has(p.getName(), 50)) {
                        p.sendMessage(ChatColor.RED + L.m("Voce precisa de 50 Esmeraldas para dominar o terreno !"));
                        return true;
                    }

                    ClanLand.removeClanAt(p.getLocation());
                    ClanLand.setClanAt(p.getLocation(), cp.getTag());
                    ClanLand.msg(p, ChatColor.GREEN + L.m("Voce dominou o terreno da guilda por 1 hora !"));
                    ClanLand.setPtosPilhagem(cp.getTag(), c.getTag(), ptosPilhagem);
                    RankDB.addPontoCache(p, Estatistica.DOMINADOR, 1);
                    for (ClanPlayer p2 : c.getOnlineMembers()) {
                        p2.toPlayer().sendMessage(ChatColor.RED + L.m("Um territorio de sua guilda foi dominado pela guilda % por uma hora !!", cp.getTag()));
                    }
                    ClanLand.update(p, p.getLocation());
                    final Location loc = p.getLocation();
                    final Clan c2 = c;

                    Runnable r;
                    r = new Runnable() {
                        public void run() {
                            ClanLand.removeClanAt(loc);
                            ClanLand.setClanAt(loc, c2.getTag());
                            for (ClanPlayer p : c2.getOnlineMembers()) {
                                if (p != null && p.toPlayer() != null) {
                                    p.toPlayer().sendMessage(ChatColor.GREEN + L.m("A terra conquistada voltou a sua guilda !"));
                                }
                            }
                        }
                    };
                    Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, r, 20 * 60 * 60);
                }
                return true;
            }
            c = cp.getClan();
            int qtdTerrenos = ClanLand.getQtdTerrenos(c.getTag());
            int poder = ClanLand.getPoder(c.getTag());
            if (KoM.debugMode) {
                KoM.log.info("pegando terrenos e poder de |" + c.getTag() + "| de tamanho de tag =" + c.getTag().length());
                KoM.log.info("INT qtdTerrenos: " + qtdTerrenos);
                KoM.log.info("INT poder: " + poder);
            }
            int preco = qtdTerrenos * CFG.landPrice;
            if (!ClanLand.econ.has(p.getName(), preco)) {
                ClanLand.msg(p, L.m("Voce precisa de % esmeraldas para dominar uma nova terra !", preco));
                return true;
            }
            if (KoM.debugMode) {
                KoM.log.info("TEM GRANA PRA COMPRAR");
            }
            int numeroMembrosGuilda = c.getSize();
            if (qtdTerrenos >= CFG.landMax + poder) {
                ClanLand.msg(p, L.m("Sua guilda pode apenas ter % terrenos !", ((int) CFG.landMax + (int) poder)));
                ClanLand.msg(p, L.m("Para ter mais terrenos, consiga mais poder recrutando membros novos ou com a pedra do poder !"));
                return true;
            }
            // if (qtdTerrenos > 0) {
            String minhaTag = c.getTag();
            int x = p.getLocation().getChunk().getBlock(1, 0, 1).getLocation().getBlockX();
            int y = p.getLocation().getChunk().getBlock(1, 0, 1).getLocation().getBlockZ();
            Location up = new Location(p.getWorld(), x + 16, 0, y);
            Clan cUp = ClanLand.getClanAt(up);
            // nao tem clan em cima ou não é meu clan
            if (cUp == null || !cUp.getTag().equalsIgnoreCase(minhaTag)) {
                if (cUp != null && !cUp.getTag().equalsIgnoreCase(minhaTag)) {
                    p.sendMessage(ChatColor.RED + L.m("Voce nao pode conquistar colado em outras guildas !"));
                    return true;
                }
                Location down = new Location(p.getWorld(), x - 16, 0, y);
                Clan cDown = ClanLand.getClanAt(down);
                if (cDown == null || !cDown.getTag().equalsIgnoreCase(minhaTag)) {
                    if (cDown != null && !cDown.getTag().equalsIgnoreCase(minhaTag)) {
                        p.sendMessage(ChatColor.RED + L.m("Voce nao pode conquistar colado em outras guildas !"));
                        return true;
                    }
                    Location left = new Location(p.getWorld(), x, 0, y + 16);
                    Clan cLeft = ClanLand.getClanAt(left);
                    if (cLeft == null || !cLeft.getTag().equalsIgnoreCase(minhaTag)) {
                        if (cLeft != null && !cLeft.getTag().equalsIgnoreCase(minhaTag)) {
                            p.sendMessage(ChatColor.RED + L.m("Voce nao pode conquistar colado em outras guildas !"));
                            return true;
                        }
                        Location right = new Location(p.getWorld(), x, 0, y - 16);
                        Clan cRight = ClanLand.getClanAt(right);
                        if (cRight == null || !cRight.getTag().equalsIgnoreCase(minhaTag)) {
                            if (cRight != null && !cRight.getTag().equalsIgnoreCase(minhaTag)) {
                                p.sendMessage(ChatColor.RED + L.m("Voce nao pode conquistar colado em outras guildas !"));
                                return true;
                            }
                            right = new Location(p.getWorld(), x - 16, 0, y - 16);
                            cRight = ClanLand.getClanAt(right);
                            if (cRight == null || !cRight.getTag().equalsIgnoreCase(minhaTag)) {
                                if (cRight != null && !cRight.getTag().equalsIgnoreCase(minhaTag)) {
                                    p.sendMessage(ChatColor.RED + L.m("Voce nao pode conquistar colado em outras guildas !"));
                                    return true;
                                }
                                right = new Location(p.getWorld(), x + 16, 0, y - 16);
                                cRight = ClanLand.getClanAt(right);
                                if (cRight == null || !cRight.getTag().equalsIgnoreCase(minhaTag)) {
                                    if (cRight != null && !cRight.getTag().equalsIgnoreCase(minhaTag)) {
                                        p.sendMessage(ChatColor.RED + L.m("Voce nao pode conquistar colado em outras guildas !"));
                                        return true;
                                    }
                                    right = new Location(p.getWorld(), x - 16, 0, y + 16);
                                    cRight = ClanLand.getClanAt(right);
                                    if (cRight == null || !cRight.getTag().equalsIgnoreCase(minhaTag)) {
                                        if (cRight != null && !cRight.getTag().equalsIgnoreCase(minhaTag)) {
                                            p.sendMessage(ChatColor.RED + L.m("Voce nao pode conquistar colado em outras guildas !"));
                                            return true;
                                        }
                                        right = new Location(p.getWorld(), x + 16, 0, y + 16);
                                        cRight = ClanLand.getClanAt(right);
                                        if (cRight == null || !cRight.getTag().equalsIgnoreCase(minhaTag)) {
                                            if (cRight != null && !cRight.getTag().equalsIgnoreCase(minhaTag)) {
                                                p.sendMessage(ChatColor.RED + L.m("Voce nao pode conquistar colado em outras guildas !"));
                                                return true;
                                            }
                                            if (qtdTerrenos > 0) {
                                                ClanLand.msg(p, L.m("Voce so pode conquistar terrenos do lado do seu ou longe de inimigos !"));
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            // }
            ClanLand.econ.withdrawPlayer(p.getName(), preco);
            p.playSound(p.getLocation(), Sound.BLOCK_GRASS_BREAK, 10, 0);
            ClanLand.setClanAt(p.getLocation(), cp.getClan().getTag());
            ClanLand.msg(p, L.m("Este terreno agora eh da sua guilda."));
            ClanLand.update(p, p.getLocation());
            return true;
        } else if (args[0].equals("info") || args[0].equals("i")) {
            if (c == null) {
                ClanLand.msg(p, L.m("Esse terreno nao tem dono."));
                return true;
            }
            if (cp != null && c.getTag().equals(cp.getClan().getTag())) {
                String[] owner = ClanLand.getOwnerAt(p.getLocation());
                if (owner.equals("none")) {
                    ClanLand.msg(p, L.m("Esse terreno eh publico, do seu clan."));
                    return true;
                } else {
                    HashMap<String, UUID> membros = ClanLand.getMembersAt(p.getLocation());
                    ClanLand.msg(p, "Esse terreno eh do jogador " + owner[0] + ChatColor.GREEN + ", do seu clan.");
                    if (membros.size() == 0) {
                        ClanLand.msg(p, L.m("Este terreno nao tem membros."));
                    } else {
                        ClanLand.msg(p, L.m("Membros do terreno:"));
                        for (String s : membros.keySet()) {
                            ClanLand.msg(p, "- " + s);
                        }
                    }
                    return true;
                }
            }
            ClanLand.msg(p, L.m("Este terreno eh do clan ", c.getColorTag()));
            return true;
        } else if (args[0].equals("safe")) {
            if (p.isOp()) {
                if (ClanLand.isSafeZone(p.getLocation())) {
                    ClanLand.setClanAt(p.getLocation(), "WILD");
                    ClanLand.msg(p, L.m("Nao eh mais safezone"));
                } else {
                    ClanLand.setClanAt(p.getLocation(), "SAFE");
                    if (args.length > 2) ClanLand.setCoisasSafe(p.getLocation(), args[1], args[2]);
                    ClanLand.msg(p, L.m("Agora eh safezone"));
                }
                ClanLand.update(p, p.getLocation());
                return true;
            } else {
                sendHelp(cs, leader);
                return true;
            }
        } else if (args[0].equals("war")) {
            if (p.isOp()) {
                if (ClanLand.isWarZone(p.getLocation())) {
                    ClanLand.setClanAt(p.getLocation(), "WILD");
                    ClanLand.msg(p, L.m("Nao eh mais warzone"));
                } else {
                    ClanLand.setClanAt(p.getLocation(), "WARZ");
                    ClanLand.msg(p, L.m("Agora eh warzone"));
                }
                ClanLand.update(p, p.getLocation());
                return true;
            } else {
                sendHelp(cs, leader);
                return true;
            }
        } else {
            sendHelp(cs, leader);
            return true;
        }
    }
}
