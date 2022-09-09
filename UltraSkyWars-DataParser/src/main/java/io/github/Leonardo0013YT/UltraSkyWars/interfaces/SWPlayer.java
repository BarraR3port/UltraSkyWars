package io.github.Leonardo0013YT.UltraSkyWars.interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface SWPlayer {

    boolean isShowLevel();

    void setShowLevel(boolean showLevel);

    int getRankedKit();

    void setRankedKit(int rankedKit);

    int getRankedKitLevel();

    void setRankedKitLevel(int rankedKitLevel);

    int getSoloKit();

    void setSoloKit(int soloKit);

    int getSoloKitLevel();

    void setSoloKitLevel(int soloKitLevel);

    int getTeamKit();

    void setTeamKit(int teamKit);

    int getTeamKitLevel();

    void setTeamKitLevel(int teamKitLevel);

    int getRanking();

    void setRanking(int ranking);

    long getLastChange();

    void setLastChange(long lastChange);

    HashMap<String, HashMap<String, Long>> getHeads();

    void setHeads(HashMap<String, HashMap<String, Long>> heads);

    int getHeadsCollected();

    void setHeadsCollected(int headsCollected);

    boolean hasHead(String rarity, String name);

    void addHead(String rarity, String name, long date);

    int getHeadCount(String rarity);

    int getSoulWellHead();

    void setSoulWellHead(int soulWellHead);

    int getSoulWellExtra();

    void setSoulWellExtra(int soulWellExtra);

    int getSoulWellMax();

    void setSoulWellMax(int soulWellMax);

    int getSoulanimation();

    void setSoulanimation(int soulanimation);

    float getSpeed();

    void setSpeed(float speed);

    boolean isNightVision();

    void setNightVision(boolean nightVision);

    boolean isSpectatorsView();

    void setSpectatorsView(boolean spectatorsView);

    boolean isFirstPerson();

    void setFirstPerson(boolean firstPerson);

    ArrayList<String> getFavorites();

    void setFavorites(ArrayList<String> favorites);

    String getName();

    void setName(String name);

    List<Integer> getPerks();

    void setPerks(List<Integer> perks);

    void addPerks(int id);

    void removePerks(int id);

    List<Integer> getBalloons();

    void setBalloons(List<Integer> balloons);

    void addBalloons(int id);

    void removeBalloons(int id);

    List<Integer> getKilleffects();

    void setKilleffects(List<Integer> killeffects);

    void addKillEffects(int id);

    void removeKillEffects(int id);

    List<Integer> getKillsounds();

    void setKillsounds(List<Integer> killsounds);

    void addKillSounds(int id);

    void removeKillSounds(int id);

    HashMap<Integer, List<Integer>> getKits();

    void setKits(HashMap<Integer, List<Integer>> kits);

    void addKitLevel(int kitID, int level);

    void removeKitLevel(int kitID, int level);

    boolean hasKitLevel(int kitID, int level);

    List<Integer> getPartings();

    void setPartings(List<Integer> partings);

    void addPartings(int id);

    void removePartings(int id);

    List<Integer> getTaunts();

    void setTaunts(List<Integer> taunts);

    void addTaunts(int id);

    void removeTaunts(int id);

    List<Integer> getTrails();

    void setTrails(List<Integer> trails);

    void addTrails(int id);

    void removeTrails(int id);

    List<Integer> getWindances();

    void setWindances(List<Integer> windances);

    void addWinDances(int id);

    void removeWinDances(int id);

    List<Integer> getWineffects();

    void setWineffects(List<Integer> wineffects);

    void addWinEffects(int id);

    void removeWinEffects(int id);

    List<Integer> getGlasses();

    void setGlasses(List<Integer> glasses);

    void addGlasses(int id);

    void removeGlasses(int id);

    int getCubeAnimation();

    void setCubeAnimation(int cubeanimation);

    void addCubeAnimation(int cubeanimation);

    void removeCubeAnimation(int cubeanimation);

    int getCubelets();

    void setCubelets(int cubelets);

    void addCubelets(int cubelets);

    void removeCubelets(int cubelets);

    int getRows();

    void setRows(int rows);

    void addRows(int rows);

    void removeRows(int rows);

    int getLevel();

    void setLevel(int level);

    String getEloRank();

    void setEloRank(String eloRank);

    void removeElo(int elo);

    void addSoloKills(int soloKills);

    void addTeamKills(int teamKills);

    void addRankedKills(int rankedKills);

    void addSoloWins(int soloWins);

    void addTeamWins(int teamWins);

    void addRankedWins(int rankedWins);

    void addSoloShots(int soloShots);

    void addTeamShots(int teamShots);

    void addRankedShots(int rankedShots);

    void addSoloSShots(int soloSShots);

    void addTeamSShots(int teamSShots);

    void addRankedSShots(int rankedSShots);

    void addSoloWalked(int soloWalked);

    void addTeamWalked(int teamWalked);

    void addRankedWalked(int rankedWalked);

    void addSoloPlayed(int soloPlayed);

    void addTeamPlayed(int teamPlayed);

    void addRankedPlayed(int rankedPlayed);

    void addSoloBreak(int soloBreak);

    void addTeamBreak(int teamBreak);

    void addRankedBreak(int rankedBreak);

    void addSoloPlaced(int soloPlaced);

    void addTeamPlaced(int teamPlaced);

    void addRankedPlaced(int rankedPlaced);

    void addSoloDeaths(int soloDeaths);

    void addTeamDeaths(int teamDeaths);

    void addRankedDeaths(int rankedDeaths);

    void addElo(int elo);

    int getArmorDance();

    void setArmorDance(int armorDance);

    int getBalloon();

    void setBalloon(int balloon);

    int getGlass();

    void setGlass(int glass);

    int getKillEffect();

    void setKillEffect(int killEffect);

    int getKillSound();

    void setKillSound(int killSound);

    int getParting();

    void setParting(int parting);

    int getTaunt();

    void setTaunt(int taunt);

    int getTrail();

    void setTrail(int trail);

    int getWinDance();

    void setWinDance(int winDance);

    int getWinEffect();

    void setWinEffect(int winEffect);

    int getSoloKills();

    void setSoloKills(int soloKills);

    int getTeamKills();

    void setTeamKills(int teamKills);

    int getRankedKills();

    void setRankedKills(int rankedKills);

    int getSoloWins();

    void setSoloWins(int soloWins);

    int getTeamWins();

    void setTeamWins(int teamWins);

    int getRankedWins();

    void setRankedWins(int rankedWins);

    int getSoloShots();

    void setSoloShots(int soloShots);

    int getTeamShots();

    void setTeamShots(int teamShots);

    int getRankedShots();

    void setRankedShots(int rankedShots);

    int getSoloSShots();

    void setSoloSShots(int soloSShots);

    int getTeamSShots();

    void setTeamSShots(int teamSShots);

    int getRankedSShots();

    void setRankedSShots(int rankedSShots);

    int getSoloWalked();

    void setSoloWalked(int soloWalked);

    int getTeamWalked();

    void setTeamWalked(int teamWalked);

    int getRankedWalked();

    void setRankedWalked(int rankedWalked);

    int getSoloPlayed();

    void setSoloPlayed(int soloPlayed);

    int getTeamPlayed();

    void setTeamPlayed(int teamPlayed);

    int getRankedPlayed();

    void setRankedPlayed(int rankedPlayed);

    int getSoloBreak();

    void setSoloBreak(int soloBreak);

    int getTeamBreak();

    void setTeamBreak(int teamBreak);

    int getRankedBreak();

    void setRankedBreak(int rankedBreak);

    int getSoloPlaced();

    void setSoloPlaced(int soloPlaced);

    int getTeamPlaced();

    void setTeamPlaced(int teamPlaced);

    int getRankedPlaced();

    void setRankedPlaced(int rankedPlaced);

    int getSoloDeaths();

    void setSoloDeaths(int soloDeaths);

    int getTeamDeaths();

    void setTeamDeaths(int teamDeaths);

    int getRankedDeaths();

    void setRankedDeaths(int rankedDeaths);

    int getElo();

    void setElo(int elo);

    void addSoloAssists(int soloAssists);

    void addTeamAssists(int teamAssists);

    void addRankedAssists(int rankedAssists);

    void addSoloKill50(int soloKill50);

    void addSoloKill25(int soloKill25);

    void addSoloKill5(int soloKill5);

    void addTeamKill50(int teamKill50);

    void addTeamKill25(int teamKill25);

    void addTeamKill5(int teamKill5);

    void addRankedKill50(int rankedKill50);

    void addRankedKill25(int rankedKill25);

    void addRankedKill5(int rankedKill5);

    void addSouls(int souls);

    void addXp(int xp);

    void addCoins(int coins);

    void removeCoins(int coins);

    void removeSouls(int souls);

    void removeXp(int xp);

    int getSoloAssists();

    void setSoloAssists(int soloAssists);

    int getTeamAssists();

    void setTeamAssists(int teamAssists);

    int getRankedAssists();

    void setRankedAssists(int rankedAssists);

    int getSoloKill50();

    void setSoloKill50(int soloKill50);

    int getSoloKill25();

    void setSoloKill25(int soloKill25);

    int getSoloKill5();

    void setSoloKill5(int soloKill5);

    int getTeamKill50();

    void setTeamKill50(int teamKill50);

    int getTeamKill25();

    void setTeamKill25(int teamKill25);

    int getTeamKill5();

    void setTeamKill5(int teamKill5);

    int getRankedKill50();

    void setRankedKill50(int rankedKill50);

    int getRankedKill25();

    void setRankedKill25(int rankedKill25);

    int getRankedKill5();

    void setRankedKill5(int rankedKill5);

    int getSouls();

    void setSouls(int souls);

    int getXp();

    void setXp(int xp);

    int getCoins();

    void setCoins(int coins);
}