package io.github.Leonardo0013YT.UltraSkyWars.data;

import io.github.Leonardo0013YT.UltraSkyWars.UltraSkyWars;
import io.github.Leonardo0013YT.UltraSkyWars.UltraSkyWarsDataParser;
import io.github.Leonardo0013YT.UltraSkyWars.enums.StatType;
import io.github.Leonardo0013YT.UltraSkyWars.interfaces.SWPlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class DatabaseParser {

    private final UltraSkyWarsDataParser parser;

    public DatabaseParser(UltraSkyWarsDataParser parser) {
        this.parser = parser;
    }

    public void parse(){
        new BukkitRunnable(){
            int correct = 0, failed = 0, already = 0;
            @Override
            public void run() {
                Connection connection = UltraSkyWars.get().getDb().getConnection();
                if (connection == null){
                    parser.sendMessage("§aLa conexion es nulla.");
                    cancel();
                    return;
                }
                try {
                    PreparedStatement statement = connection.prepareStatement("SELECT * FROM UltraSkyWars;");
                    if (statement == null){
                        parser.sendMessage("§aLa conexion statement es nula.");
                        cancel();
                        return;
                    }
                    ResultSet result = statement.executeQuery();
                    if (result == null){
                        parser.sendMessage("§aEl result statement es nula.");
                        cancel();
                        return;
                    }
                    parser.sendMessage("Primer resultado " + result.next() + " " + result.getRow());
                    while (result.next()) {
                        //UUID, Name, Data, Kills, Wins, Deaths, Coins, Elo
                        if (result.getString("UUID") == null){
                            failed++;
                            continue;
                        }
                        UUID uuid = UUID.fromString(result.getString("UUID"));
                        String name = result.getString("Name");
                        if (result.getString("Name") == null){
                            failed++;
                            continue;
                        }
                        String data = result.getString("Data");
                        if (data == null){
                            failed++;
                            continue;
                        }
                        if (!data.startsWith("{\"SWPLAYER\"")) {
                            already++;
                            parser.sendMessage("§c" + name + "§e already parsed.");
                            continue;
                        }
                        try {
                            SWPlayer sw = parser.getGson().fromJson(data, SWPlayer.class);
                            String SAVE = "UPDATE UltraSkyWars SET Data=? WHERE UUID=?;";
                            PreparedStatement saveStatement = connection.prepareStatement(SAVE);
                            saveStatement.setString(1, UltraSkyWars.getGson().toJson(parseData(sw), io.github.Leonardo0013YT.UltraSkyWars.data.SWPlayer.class));
                            saveStatement.setString(2, uuid.toString());
                            saveStatement.execute();
                            parser.sendMessage("§c" + name + "§a parsed correctly.");
                            correct++;
                        } catch (NullPointerException ex) {
                            already++;
                            parser.sendMessage("§c" + name + "§e already parsed.");
                        }
                    }
                } catch (SQLException e) {
                    failed++;
                    parser.sendMessage("Error ocurrido " + e.getMessage());
                }
                parser.sendMessage("§a§lDATA PARSED WITH §e" + correct + " correct§7, §e" + failed + "§7 and §e" + already +" failed§a§l.");
            }
        }.runTaskAsynchronously(parser);
    }

    public io.github.Leonardo0013YT.UltraSkyWars.data.SWPlayer parseData(SWPlayer sw){
        io.github.Leonardo0013YT.UltraSkyWars.data.SWPlayer pw = new io.github.Leonardo0013YT.UltraSkyWars.data.SWPlayer();
        pw.setShowLevel(sw.isShowLevel());
        pw.setLastChange(sw.getLastChange());
        if (pw.getLastChange() + (1000 * 60 * 10) < System.currentTimeMillis()) {
            pw.setLastChange(System.currentTimeMillis());
            pw.setRanking(sw.getRanking());
        }
        pw.setHeadsCollected(sw.getHeadsCollected());
        pw.setHeads(sw.getHeads());
        pw.setSoulWellHead(sw.getSoulWellHead());
        pw.setSoulWellExtra(sw.getSoulWellExtra());
        pw.setSoulWellMax(sw.getSoulWellMax());
        pw.setFavorites(sw.getFavorites());
        pw.setKits(sw.getKits());
        pw.setBalloons(sw.getBalloons());
        pw.setGlasses(sw.getGlasses());
        pw.setKilleffects(sw.getKilleffects());
        pw.setKillsounds(sw.getKillsounds());
        pw.setPartings(sw.getPartings());
        pw.setTaunts(sw.getTaunts());
        pw.setTrails(sw.getTrails());
        pw.setWindances(sw.getWindances());
        pw.setWineffects(sw.getWineffects());
        pw.setSoulanimation(sw.getSoulanimation());
        pw.setCubeAnimation(sw.getCubeAnimation());
        pw.setCubelets(sw.getCubelets());
        pw.setRows(sw.getRows());
        pw.setArmorDance(sw.getArmorDance());
        pw.setBalloon(sw.getBalloon());
        pw.setGlass(sw.getGlass());
        pw.setKillEffect(sw.getKillEffect());
        pw.setKillSound(sw.getKillSound());
        pw.setSoloKit(sw.getSoloKit());
        pw.setSoloKitLevel(sw.getSoloKitLevel());
        pw.setTeamKit(sw.getTeamKit());
        pw.setTeamKitLevel(sw.getTeamKitLevel());
        pw.setRankedKit(sw.getRankedKit());
        pw.setRankedKitLevel(sw.getRankedKitLevel());
        pw.setParting(sw.getParting());
        pw.setTaunt(sw.getTaunt());
        pw.setTrail(sw.getTrail());
        pw.setWinDance(sw.getWinDance());
        pw.setWinEffect(sw.getWinEffect());
        pw.addStat(StatType.KILLS, "SOLO", sw.getSoloKills());
        pw.addStat(StatType.KILLS, "TEAM", sw.getTeamKills());
        pw.addStat(StatType.KILLS, "RANKED", sw.getRankedKills());
        pw.addStat(StatType.WINS, "SOLO", sw.getSoloWins());
        pw.addStat(StatType.WINS, "TEAM", sw.getTeamWins());
        pw.addStat(StatType.WINS, "RANKED", sw.getRankedWins());
        pw.addStat(StatType.SHOTS, "SOLO", sw.getSoloShots());
        pw.addStat(StatType.SHOTS, "TEAM", sw.getTeamShots());
        pw.addStat(StatType.SHOTS, "RANKED", sw.getRankedShots());
        pw.addStat(StatType.SUCCESS_SHOTS, "SOLO", sw.getSoloSShots());
        pw.addStat(StatType.SUCCESS_SHOTS, "TEAM", sw.getTeamSShots());
        pw.addStat(StatType.SUCCESS_SHOTS, "RANKED", sw.getRankedSShots());
        pw.addStat(StatType.WALKED, "SOLO", sw.getSoloWalked());
        pw.addStat(StatType.WALKED, "TEAM", sw.getTeamWalked());
        pw.addStat(StatType.WALKED, "RANKED", sw.getRankedWalked());
        pw.addStat(StatType.PLAYED, "SOLO", sw.getSoloPlayed());
        pw.addStat(StatType.PLAYED, "TEAM", sw.getTeamPlayed());
        pw.addStat(StatType.PLAYED, "RANKED", sw.getRankedPlayed());
        pw.addStat(StatType.BREAK, "SOLO", sw.getSoloBreak());
        pw.addStat(StatType.BREAK, "TEAM", sw.getTeamBreak());
        pw.addStat(StatType.BREAK, "RANKED", sw.getRankedBreak());
        pw.addStat(StatType.PLACED, "SOLO", sw.getSoloPlaced());
        pw.addStat(StatType.PLACED, "TEAM", sw.getTeamPlaced());
        pw.addStat(StatType.PLACED, "RANKED", sw.getRankedPlaced());
        pw.addStat(StatType.DEATHS, "SOLO", sw.getSoloDeaths());
        pw.addStat(StatType.DEATHS, "TEAM", sw.getTeamDeaths());
        pw.addStat(StatType.DEATHS, "RANKED", sw.getRankedDeaths());
        pw.addStat(StatType.ASSISTS, "SOLO", sw.getSoloAssists());
        pw.addStat(StatType.ASSISTS, "TEAM", sw.getTeamAssists());
        pw.addStat(StatType.ASSISTS, "RANKED", sw.getRankedAssists());
        pw.addStat(StatType.KILL5, "SOLO", sw.getSoloKill5());
        pw.addStat(StatType.KILL5, "TEAM", sw.getTeamKill5());
        pw.addStat(StatType.KILL5, "RANKED", sw.getRankedKill5());
        pw.addStat(StatType.KILL25, "SOLO", sw.getSoloKill25());
        pw.addStat(StatType.KILL25, "TEAM", sw.getTeamKill25());
        pw.addStat(StatType.KILL25, "RANKED", sw.getRankedKill25());
        pw.addStat(StatType.KILL50, "SOLO", sw.getSoloKill50());
        pw.addStat(StatType.KILL50, "TEAM", sw.getTeamKill50());
        pw.addStat(StatType.KILL50, "RANKED", sw.getRankedKill50());
        pw.setElo(sw.getElo());
        pw.setSouls(sw.getSouls());
        pw.setXp(sw.getXp());
        pw.setCoins(sw.getCoins());
        pw.setLevel(sw.getLevel());
        pw.setName(sw.getName());
        pw.setEloRank(sw.getEloRank());
        pw.setSpeed(sw.getSpeed());
        pw.setNightVision(sw.isNightVision());
        pw.setSpectatorsView(sw.isSpectatorsView());
        pw.setFirstPerson(sw.isFirstPerson());
        return pw;
    }

}