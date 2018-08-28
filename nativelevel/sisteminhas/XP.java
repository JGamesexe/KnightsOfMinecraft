/*

 ╭╮╭━╮╱╱╭━╮╭━╮
 ┃┃┃╭╯╱╱┃┃╰╯┃┃
 ┃╰╯╯╭━━┫╭╮╭╮┃
 ┃╭╮┃┃╭╮┃┃┃┃┃┃
 ┃┃┃╰┫╰╯┃┃┃┃┃┃
 ╰╯╰━┻━━┻╯╰╯╰╯

 Desenvolvedor: ZidenVentania

 */
package nativelevel.sisteminhas;

import nativelevel.Custom.Items.DoubleXP;
import nativelevel.KoM;
import nativelevel.MetaShit;
import nativelevel.Planting.PlantCache;
import nativelevel.utils.TitleAPI;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class XP {

    private static int[] tabelaNiveis = new int[100];

    private static int acoesParaUpar = 35;

    private static double expoenteNivel = 2;

    private static double maisAcoesPorNivel = 1.5; // cada nivel, +25% ações pra upar

    public static boolean debug = true;

    static {
        if (debug) {
            System.out.println("Calculando tabela de níveis.... (ETA BAGUI FRITADOR DE MENTE)");
            System.out.println("CONFIGURACOES:");
            System.out.println("Ações por nivel: " + acoesParaUpar);
            System.out.println("Aumento de numero de ações por tier: " + (maisAcoesPorNivel - 1) + "%");
            System.out.println("Expoente de nivel: " + expoenteNivel);
        }

        for (double lvl = 0; lvl < 100; lvl++) {

            double tier = (lvl / 10) + 1;

            double expPorItem = Math.pow(2, tier);

            double qtdAcoesParaUpar = acoesParaUpar;
            for (double x = 0; x < tier; x += 1) {
                qtdAcoesParaUpar *= (maisAcoesPorNivel);
            }

            double xpPraUpar = expPorItem * qtdAcoesParaUpar;

            if (debug) {
                System.out.println("------------------------------------------");
                System.out.println("LVL " + (lvl + 1) + " - TIER " + tier);
                System.out.println("Media de XP por ação ou craft: " + expPorItem);
                System.out.println("Quantidade de ações médias para upar com exp do tier: " + qtdAcoesParaUpar);
                System.out.println("Exp total para upar (qtd ações X xp média da ação) = " + xpPraUpar);
            }
            tabelaNiveis[(int) lvl] = (int) Math.round(xpPraUpar);
        }
    }

    public static int getExpProximoNivel(int nivel) {
        if (nivel < 1) nivel = 1;
        return tabelaNiveis[nivel - 1];
    }

    public static int getExpPorAcao(int nivel) {
        if (nivel <= 0) {
            nivel = 1;
        }
        double lvl = nivel;
        double tier = (lvl / 10) + 1;
        double expPorItem = Math.round(Math.pow(2, tier));
        return (int) Math.round(expPorItem);
    }

    public static void main(String[] args) {

    }

    static DecimalFormat df = new DecimalFormat("#.###");

    private static int getAliadosPerto(Player p) {
        int ct = 0;
        Clan doMano = ClanLand.manager.getClanByPlayerUniqueId(p.getUniqueId());
        if (doMano == null) {
            return 1;
        }
        for (Entity e : p.getNearbyEntities(6, 6, 6)) {
            if (e.getType() == EntityType.PLAYER) {
                Clan clan = ClanLand.manager.getClanByPlayerUniqueId(e.getUniqueId());
                if (clan != null) {
                    if (doMano.getTag().equalsIgnoreCase(clan.getTag()) || doMano.isAlly(clan.getTag())) {
                        ct++;
                    }
                }
            }
        }
        return ct;
    }

    public static void changeExp(Player p, double exp) {
        changeExp(p, exp, 1);
    }

    public static void changeExp(Player p, double exp, double ratioBonus) {
        changeExp(p, exp, ratioBonus, true, true);
    }

    public static void changeExp(Player p, double exp, boolean useMods) {
        changeExp(p, exp, 1, useMods, true);
    }

    public static void changeExp(Player p, double exp, boolean useMods, boolean nerfExp) {
        changeExp(p, exp, 1, useMods, nerfExp);
    }

    public static void changeExp(Player p, double exp, double ratioBonus, boolean useMods) {
        changeExp(p, exp, ratioBonus, useMods, true);
    }

    public static void changeExp(Player p, double exp, double ratioBonus, boolean useMods, boolean nerfExp) {

        if (nerfExp) exp = exp * 0.045; //QUALQUER OUTRO XP ANTIGO FICA NA MERDA.

        KoM.debug(p.getName() + " ganhando " + exp + " com ratio " + ratioBonus);

        if (ratioBonus == 0)
            ratioBonus = 1;

        double modAliados = 1;

        int aliados = getAliadosPerto(p);
        aliados--;

        if (aliados == 1) modAliados = 0.1;
        else if (aliados <= 3) modAliados = 0.25;
        else if (aliados <= 5) modAliados = 0.5;
        else modAliados = 1;

        ratioBonus += modAliados;

        double xpFinalComBonus = exp * ratioBonus;

        KoM.debug("Depois das contas vou dar " + xpFinalComBonus);

        int extra = (int) Math.round(xpFinalComBonus - exp);

        KoM.debug("Ou seja, da original to dando " + extra + " extra de XP");

        bruteChangeExp(p, xpFinalComBonus, useMods);

    }

    public static void bruteChangeExp(Player p, double exp) {
        bruteChangeExp(p, exp, false);
    }

    public static void bruteChangeExp(Player p, double exp, boolean useMods) {

        if (useMods) {

            if (DoubleXP.ativo) exp *= 2;

            if (p.hasPermission("kom.lord")) exp *= 2;
            else if (p.hasPermission("kom.templario")) exp *= 1.6;
            else if (p.hasPermission("kom.cavaleiro")) exp *= 1.3;

        }

        KoM.debug(p.getName() + " ganhando " + exp);

        int level = p.getLevel();

        if (level == 0) level = 1;

        float xpNecessariaPraUpar = tabelaNiveis[level - 1];

        KoM.debug("xp pra up " + xpNecessariaPraUpar);

        float expAtual = p.getTotalExperience();
        if (p.hasMetadata("expAtual")) expAtual = (float) MetaShit.getMetaObject("expAtual", p);

        KoM.debug("xp atual " + expAtual);

        expAtual += exp;

        KoM.debug("xp final " + expAtual);

        while (expAtual >= xpNecessariaPraUpar) {
            expAtual -= xpNecessariaPraUpar;
            p.setLevel(p.getLevel() + 1);
            xpNecessariaPraUpar = tabelaNiveis[p.getLevel() - 1];
            KoM.debug("upou");
        }

        float ratioBarra = expAtual / xpNecessariaPraUpar;

        if (p.hasMetadata("mostraXP")) {
            double pct = ratioBarra * 100;
            TitleAPI.sendActionBar(p, "§a+" + exp + " exp (" + df.format(pct) + "%)");
        }

        KoM.debug("ratio barra " + ratioBarra);

        MetaShit.setMetaObject("expAtual", p, expAtual);
        p.setTotalExperience((int) expAtual);
        p.setExp(ratioBarra);

    }

    public static void debugLevel(Player p, int nivel) {
        double tier = (nivel / 10) + 1;

        double expPorItem = Math.pow(2, tier);

        double qtdAcoesParaUpar = acoesParaUpar;
        for (int x = 0; x < tier; x++) {
            qtdAcoesParaUpar *= maisAcoesPorNivel;
        }

        double xpPraUpar = expPorItem * qtdAcoesParaUpar;

        p.sendMessage("LVL " + (nivel + 1) + " - TIER " + tier);
        p.sendMessage("Media de XP por ação ou craft: " + (int) expPorItem);
        p.sendMessage("Quantidade de ações médias para upar com exp do tier: " + (int) qtdAcoesParaUpar);
        p.sendMessage("Exp total para upar (qtd ações X xp média da ação) = " + (int) xpPraUpar);
    }

    public static void setaLevel(Player p, int level) {
        p.setExp(0f);
        p.setTotalExperience(0);
        p.setLevel(level);
    }

    /*
     private static int hardMaxLevel = 100;

     private static int xpTotalToReachLevel[];

     static {
     initLookupTables(100);
     }

    
    
     public static int getHardMaxLevel() {
     return hardMaxLevel;
     }

     public static void setHardMaxLevel(int hardMaxLevel) {
     XP.hardMaxLevel = hardMaxLevel;
     }

    
     public static int getXpNeededToLevelUp(int level) {
     Validate.isTrue(level >= 0, "Level may not be negative.");
     return level > 30 ? 62 + (level - 30) * 7 : level >= 16 ? 17 + (level - 15) * 3 : 17;
     }

    
     private static void initLookupTables(int maxLevel) {
     xpTotalToReachLevel = new int[maxLevel];
        
     for (int i = 0; i < xpTotalToReachLevel.length; i++) {
     xpTotalToReachLevel[i]
     = i >= 30 ? (int) (3.5 * i * i - 151.5 * i + 2220)
     : i >= 16 ? (int) (1.5 * i * i - 29.5 * i + 360)
     : 17 * i;
     }
     }

     private static int calculateLevelForExp(int exp) {
     int level = 0;
     int curExp = 7; // level 1
     int incr = 10;

     while (curExp <= exp) {
     curExp += incr;
     level++;
     incr += (level % 2 == 0) ? 3 : 4;
     }
     return level;
     }

     public static void changeExp(Player p, int amt) {
     changeExp(p, (double) amt);
     }

     static DecimalFormat df = new DecimalFormat("#.###");
    
     public static void changeExp(Player p, double amt) {
        
     if(DoubleXP.ativo)
     amt = amt * 2;
        
     if(p.hasMetadata("mostraXp")) {
            
     TitleAPI.sendActionBar(p, ChatColor.GREEN+"+"+df.format(amt)+"xp");
            
     }
        
     setExp(p, getCurrentFractionalXP(p), amt);
     }

     public static void setExp(Player p, int amt) {
     setExp(p, 0, amt);
     }

     public static void setaLevel(Player p, int qto) {
     XP.setExp(p, XP.getXpForLevel(qto));
     }

     public static void setExp(Player p, double amt) {
     setExp(p, 0, amt);
     }

     private static void setExp(Player player, double base, double amt) {
     int xp = (int) Math.max(base + amt, 0);

     int curLvl = player.getLevel();
     int newLvl = getLevelForExp(xp);

     // Increment level
     if (curLvl != newLvl) {
     if(newLvl > curLvl)
     newLvl = curLvl + 1;
     player.setLevel(newLvl);
     }
        
     // Increment total experience - this should force the server to send an update packet
     if (xp > base) {
     player.setTotalExperience(player.getTotalExperience() + xp - (int) base);
     }

     double pct = (base - getXpForLevel(newLvl) + amt) / (double) (getXpNeededToLevelUp(newLvl));
     player.setExp((float) pct);
     }

     public static int getCurrentExp(Player player) {

     int lvl = player.getLevel();
     int cur = getXpForLevel(lvl) + (int) Math.round(getXpNeededToLevelUp(lvl) * player.getExp());
     return cur;
     }

     public static double getCurrentFractionalXP(Player player) {

     int lvl = player.getLevel();
     double cur = getXpForLevel(lvl) + (double) (getXpNeededToLevelUp(lvl) * player.getExp());
     return cur;
     }

     public static boolean hasExp(Player p, int amt) {
     return getCurrentExp(p) >= amt;
     }

     public static boolean hasExp(Player p, double amt) {
     return getCurrentFractionalXP(p) >= amt;
     }

     public static int getLevelForExp(int exp) {
     if (exp <= 0) {
     return 0;
     }
     if (exp > xpTotalToReachLevel[xpTotalToReachLevel.length - 1]) {
     // need to extend the lookup tables
     int newMax = calculateLevelForExp(exp) * 2;
     Validate.isTrue(newMax <= hardMaxLevel, "Level for exp " + exp + " > hard max level " + hardMaxLevel);
     initLookupTables(newMax);
     }
     int pos = Arrays.binarySearch(xpTotalToReachLevel, exp);
     return pos < 0 ? -pos - 2 : pos;
     }

   
     public static int getXpForLevel(int level) {
     Validate.isTrue(level >= 0 && level <= hardMaxLevel, "Invalid level " + level + "(must be in range 0.." + hardMaxLevel + ")");
     if (level >= xpTotalToReachLevel.length) {
     initLookupTables(level * 2);
     }
     return xpTotalToReachLevel[level];
     }
     */
}
