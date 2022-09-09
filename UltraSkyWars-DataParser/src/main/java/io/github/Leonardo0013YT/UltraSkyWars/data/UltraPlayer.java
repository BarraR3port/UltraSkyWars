package io.github.Leonardo0013YT.UltraSkyWars.data;

import io.github.Leonardo0013YT.UltraSkyWars.UltraSkyWarsDataParser;
import io.github.Leonardo0013YT.UltraSkyWars.interfaces.SWPlayer;

import java.util.*;

public class UltraPlayer implements SWPlayer {

    private ArrayList<String> favorites = new ArrayList<>();
    private HashMap<Integer, List<Integer>> kits = new HashMap<>();
    private List<Integer> perks = new ArrayList<>(), balloons = new ArrayList<>(), glasses = new ArrayList<>(), killeffects = new ArrayList<>(), killsounds = new ArrayList<>(), partings = new ArrayList<>(), taunts = new ArrayList<>(), trails = new ArrayList<>(), windances = new ArrayList<>(), wineffects = new ArrayList<>();
    private int ranking = 999999, headsCollected = 0, soulWellHead = 1, soulWellExtra = 0, soulWellMax = 0, soulanimation = 0, cubeanimation = 0, cubelets = 0, rows = 1, armorDance = 999999, balloon = 999999, glass = 0, killEffect = 999999, killSound = 999999, parting = 999999, taunt = 0, trail = 999999, winDance = 999999, winEffect = 0, soloKills = 0, teamKills = 0, rankedKills = 0, soloWins = 0, teamWins = 0, rankedWins = 0, soloShots = 0, teamShots = 0, rankedShots = 0, soloSShots = 0, teamSShots = 0, rankedSShots = 0, soloWalked = 0, teamWalked = 0, rankedWalked = 0, soloPlayed = 0, teamPlayed = 0, rankedPlayed = 0, soloBreak = 0, teamBreak = 0, rankedBreak = 0, soloPlaced = 0, teamPlaced = 0, rankedPlaced = 0, soloDeaths = 0, teamDeaths = 0, rankedDeaths = 0, soloAssists = 0, teamAssists = 0, rankedAssists = 0, soloKill50 = 0, soloKill25 = 0, soloKill5 = 0, teamKill50 = 0, teamKill25 = 0, teamKill5 = 0, rankedKill50 = 0, rankedKill25 = 0, rankedKill5 = 0, elo = 0, souls = 0, xp = 0, coins = 0, level = 1;
    private String name = "", eloRank = "classify";
    private float speed = 0.2F;
    private long lastChange = 0L;
    private boolean nightVision = true, spectatorsView = false, firstPerson = false, showLevel = true;
    private int soloKit = 999999, soloKitLevel = 1, teamKit = 999999, teamKitLevel = 1, rankedKit = 999999, rankedKitLevel = 1;
    private HashMap<String, HashMap<String, Long>> heads = new HashMap<>();

    @Override
    public boolean isShowLevel() {
        return showLevel;
    }

    @Override
    public void setShowLevel(boolean showLevel) {
        this.showLevel = showLevel;
    }

    @Override
    public int getRankedKit() {
        return rankedKit;
    }

    @Override
    public void setRankedKit(int rankedKit) {
        this.rankedKit = rankedKit;
    }

    @Override
    public int getRankedKitLevel() {
        return rankedKitLevel;
    }

    @Override
    public void setRankedKitLevel(int rankedKitLevel) {
        this.rankedKitLevel = rankedKitLevel;
    }

    @Override
    public int getSoloKit() {
        return soloKit;
    }

    @Override
    public void setSoloKit(int soloKit) {
        this.soloKit = soloKit;
    }

    @Override
    public int getSoloKitLevel() {
        return soloKitLevel;
    }

    @Override
    public void setSoloKitLevel(int soloKitLevel) {
        this.soloKitLevel = soloKitLevel;
    }

    @Override
    public int getTeamKit() {
        return teamKit;
    }

    @Override
    public void setTeamKit(int teamKit) {
        this.teamKit = teamKit;
    }

    @Override
    public int getTeamKitLevel() {
        return teamKitLevel;
    }

    @Override
    public void setTeamKitLevel(int teamKitLevel) {
        this.teamKitLevel = teamKitLevel;
    }

    @Override
    public int getRanking() {
        return ranking;
    }

    @Override
    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    @Override
    public long getLastChange() {
        return lastChange;
    }

    @Override
    public void setLastChange(long lastChange) {
        this.lastChange = lastChange;
    }

    @Override
    public HashMap<String, HashMap<String, Long>> getHeads() {
        return heads;
    }

    @Override
    public void setHeads(HashMap<String, HashMap<String, Long>> heads) {
        this.heads = heads;
    }

    @Override
    public int getHeadsCollected() {
        return headsCollected;
    }

    @Override
    public void setHeadsCollected(int headsCollected) {
        this.headsCollected = headsCollected;
    }

    @Override
    public boolean hasHead(String rarity, String name) {
        heads.putIfAbsent(rarity, new HashMap<>());
        return heads.get(rarity).containsKey(name);
    }

    @Override
    public void addHead(String rarity, String name, long date) {
        heads.putIfAbsent(rarity, new HashMap<>());
        if (headsCollected > 27) {
            NavigableMap<String, Long> nav = new TreeMap<>(heads.get(rarity));
            nav.remove(nav.firstKey());
            headsCollected--;
        }
        heads.get(rarity).putIfAbsent(name, date);
        headsCollected++;
    }

    @Override
    public int getHeadCount(String rarity) {
        if (heads.containsKey(rarity)) {
            return heads.get(rarity).size();
        }
        return 0;
    }

    @Override
    public int getSoulWellHead() {
        return soulWellHead;
    }

    @Override
    public void setSoulWellHead(int soulWellHead) {
        this.soulWellHead = soulWellHead;
    }

    @Override
    public int getSoulWellExtra() {
        return soulWellExtra;
    }

    @Override
    public void setSoulWellExtra(int soulWellExtra) {
        this.soulWellExtra = soulWellExtra;
    }

    @Override
    public int getSoulWellMax() {
        return soulWellMax;
    }

    @Override
    public void setSoulWellMax(int soulWellMax) {
        this.soulWellMax = soulWellMax;
    }

    @Override
    public int getSoulanimation() {
        return soulanimation;
    }

    @Override
    public void setSoulanimation(int soulanimation) {
        this.soulanimation = soulanimation;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public boolean isNightVision() {
        return nightVision;
    }

    @Override
    public void setNightVision(boolean nightVision) {
        this.nightVision = nightVision;
    }

    @Override
    public boolean isSpectatorsView() {
        return spectatorsView;
    }

    @Override
    public void setSpectatorsView(boolean spectatorsView) {
        this.spectatorsView = spectatorsView;
    }

    @Override
    public boolean isFirstPerson() {
        return firstPerson;
    }

    @Override
    public void setFirstPerson(boolean firstPerson) {
        this.firstPerson = firstPerson;
    }

    @Override
    public ArrayList<String> getFavorites() {
        return favorites;
    }

    @Override
    public void setFavorites(ArrayList<String> favorites) {
        this.favorites = favorites;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<Integer> getPerks() {
        return perks;
    }

    @Override
    public void setPerks(List<Integer> perks) {
        this.perks = perks;
    }

    @Override
    public void addPerks(int id) {
        perks.add(id);
    }

    @Override
    public void removePerks(int id) {
        perks.remove(id);
    }

    @Override
    public List<Integer> getBalloons() {
        return balloons;
    }

    @Override
    public void setBalloons(List<Integer> balloons) {
        this.balloons = balloons;
    }

    @Override
    public void addBalloons(int id) {
        balloons.add(id);
    }

    @Override
    public void removeBalloons(int id) {
        balloons.remove(id);
    }

    @Override
    public List<Integer> getKilleffects() {
        return killeffects;
    }

    @Override
    public void setKilleffects(List<Integer> killeffects) {
        this.killeffects = killeffects;
    }

    @Override
    public void addKillEffects(int id) {
        killeffects.add(id);
    }

    @Override
    public void removeKillEffects(int id) {
        killeffects.remove(id);
    }

    @Override
    public List<Integer> getKillsounds() {
        return killsounds;
    }

    @Override
    public void setKillsounds(List<Integer> killsounds) {
        this.killsounds = killsounds;
    }

    @Override
    public void addKillSounds(int id) {
        killsounds.add(id);
    }

    @Override
    public void removeKillSounds(int id) {
        killsounds.remove(id);
    }

    @Override
    public HashMap<Integer, List<Integer>> getKits() {
        return kits;
    }

    @Override
    public void setKits(HashMap<Integer, List<Integer>> kits) {
        this.kits = kits;
    }

    @Override
    public void addKitLevel(int kitID, int level) {
        if (!kits.containsKey(kitID)) {
            kits.put(kitID, new ArrayList<>());
        }
        kits.get(kitID).add(level);
    }

    @Override
    public void removeKitLevel(int kitID, int level) {
        if (kits.containsKey(kitID)) {
            kits.get(kitID).remove(level);
        }
    }

    @Override
    public boolean hasKitLevel(int kitID, int level) {
        if (kits.containsKey(kitID)) {
            return kits.get(kitID).contains(level);
        }
        return false;
    }

    @Override
    public List<Integer> getPartings() {
        return partings;
    }

    @Override
    public void setPartings(List<Integer> partings) {
        this.partings = partings;
    }

    @Override
    public void addPartings(int id) {
        partings.add(id);
    }

    @Override
    public void removePartings(int id) {
        partings.remove(id);
    }

    @Override
    public List<Integer> getTaunts() {
        return taunts;
    }

    @Override
    public void setTaunts(List<Integer> taunts) {
        this.taunts = taunts;
    }

    @Override
    public void addTaunts(int id) {
        taunts.add(id);
    }

    @Override
    public void removeTaunts(int id) {
        taunts.remove(id);
    }

    @Override
    public List<Integer> getTrails() {
        return trails;
    }

    @Override
    public void setTrails(List<Integer> trails) {
        this.trails = trails;
    }

    @Override
    public void addTrails(int id) {
        trails.add(id);
    }

    @Override
    public void removeTrails(int id) {
        trails.remove(id);
    }

    @Override
    public List<Integer> getWindances() {
        return windances;
    }

    @Override
    public void setWindances(List<Integer> windances) {
        this.windances = windances;
    }

    @Override
    public void addWinDances(int id) {
        windances.add(id);
    }

    @Override
    public void removeWinDances(int id) {
        windances.remove(id);
    }

    @Override
    public List<Integer> getWineffects() {
        return wineffects;
    }

    @Override
    public void setWineffects(List<Integer> wineffects) {
        this.wineffects = wineffects;
    }

    @Override
    public void addWinEffects(int id) {
        wineffects.add(id);
    }

    @Override
    public void removeWinEffects(int id) {
        wineffects.remove(id);
    }

    @Override
    public List<Integer> getGlasses() {
        return glasses;
    }

    @Override
    public void setGlasses(List<Integer> glasses) {
        this.glasses = glasses;
    }

    @Override
    public void addGlasses(int id) {
        glasses.add(id);
    }

    @Override
    public void removeGlasses(int id) {
        glasses.remove(id);
    }

    @Override
    public int getCubeAnimation() {
        return cubeanimation;
    }

    @Override
    public void setCubeAnimation(int cubeanimation) {
        this.cubeanimation = cubeanimation;
    }

    @Override
    public void addCubeAnimation(int cubeanimation) {
        this.cubeanimation += cubeanimation;
    }

    @Override
    public void removeCubeAnimation(int cubeanimation) {
        this.cubeanimation -= cubeanimation;
    }

    @Override
    public int getCubelets() {
        return cubelets;
    }

    @Override
    public void setCubelets(int cubelets) {
        this.cubelets = cubelets;
    }

    @Override
    public void addCubelets(int cubelets) {
        this.cubelets += cubelets;
    }

    @Override
    public void removeCubelets(int cubelets) {
        this.cubelets -= cubelets;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public void setRows(int rows) {
        this.rows = rows;
    }

    @Override
    public void addRows(int rows) {
        this.rows += rows;
    }

    @Override
    public void removeRows(int rows) {
        this.rows -= rows;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String getEloRank() {
        return eloRank;
    }

    @Override
    public void setEloRank(String eloRank) {
        this.eloRank = eloRank;
    }

    @Override
    public void removeElo(int elo) {
        this.elo -= elo;
    }

    @Override
    public void addSoloKills(int soloKills) {
        this.soloKills += soloKills;
    }

    @Override
    public void addTeamKills(int teamKills) {
        this.teamKills += teamKills;
    }

    @Override
    public void addRankedKills(int rankedKills) {
        this.rankedKills += rankedKills;
    }

    @Override
    public void addSoloWins(int soloWins) {
        this.soloWins += soloWins;
    }

    @Override
    public void addTeamWins(int teamWins) {
        this.teamWins += teamWins;
    }

    @Override
    public void addRankedWins(int rankedWins) {
        this.rankedWins += rankedWins;
    }

    @Override
    public void addSoloShots(int soloShots) {
        this.soloShots += soloShots;
    }

    @Override
    public void addTeamShots(int teamShots) {
        this.teamShots += teamShots;
    }

    @Override
    public void addRankedShots(int rankedShots) {
        this.rankedShots += rankedShots;
    }

    @Override
    public void addSoloSShots(int soloSShots) {
        this.soloSShots += soloSShots;
    }

    @Override
    public void addTeamSShots(int teamSShots) {
        this.teamSShots += teamSShots;
    }

    @Override
    public void addRankedSShots(int rankedSShots) {
        this.rankedSShots += rankedSShots;
    }

    @Override
    public void addSoloWalked(int soloWalked) {
        this.soloWalked += soloWalked;
    }

    @Override
    public void addTeamWalked(int teamWalked) {
        this.teamWalked += teamWalked;
    }

    @Override
    public void addRankedWalked(int rankedWalked) {
        this.rankedWalked += rankedWalked;
    }

    @Override
    public void addSoloPlayed(int soloPlayed) {
        this.soloPlayed += soloPlayed;
    }

    @Override
    public void addTeamPlayed(int teamPlayed) {
        this.teamPlayed += teamPlayed;
    }

    @Override
    public void addRankedPlayed(int rankedPlayed) {
        this.rankedPlayed += rankedPlayed;
    }

    @Override
    public void addSoloBreak(int soloBreak) {
        this.soloBreak += soloBreak;
    }

    @Override
    public void addTeamBreak(int teamBreak) {
        this.teamBreak += teamBreak;
    }

    @Override
    public void addRankedBreak(int rankedBreak) {
        this.rankedBreak += rankedBreak;
    }

    @Override
    public void addSoloPlaced(int soloPlaced) {
        this.soloPlaced += soloPlaced;
    }

    @Override
    public void addTeamPlaced(int teamPlaced) {
        this.teamPlaced += teamPlaced;
    }

    @Override
    public void addRankedPlaced(int rankedPlaced) {
        this.rankedPlaced += rankedPlaced;
    }

    @Override
    public void addSoloDeaths(int soloDeaths) {
        this.soloDeaths += soloDeaths;
    }

    @Override
    public void addTeamDeaths(int teamDeaths) {
        this.teamDeaths += teamDeaths;
    }

    @Override
    public void addRankedDeaths(int rankedDeaths) {
        this.rankedDeaths += rankedDeaths;
    }

    @Override
    public void addElo(int elo) {
        this.elo += elo;
    }

    @Override
    public int getArmorDance() {
        return armorDance;
    }

    @Override
    public void setArmorDance(int armorDance) {
        this.armorDance = armorDance;
    }

    @Override
    public int getBalloon() {
        return balloon;
    }

    @Override
    public void setBalloon(int balloon) {
        this.balloon = balloon;
    }

    @Override
    public int getGlass() {
        return glass;
    }

    @Override
    public void setGlass(int glass) {
        this.glass = glass;
    }

    @Override
    public int getKillEffect() {
        return killEffect;
    }

    @Override
    public void setKillEffect(int killEffect) {
        this.killEffect = killEffect;
    }

    @Override
    public int getKillSound() {
        return killSound;
    }

    @Override
    public void setKillSound(int killSound) {
        this.killSound = killSound;
    }

    @Override
    public int getParting() {
        return parting;
    }

    @Override
    public void setParting(int parting) {
        this.parting = parting;
    }

    @Override
    public int getTaunt() {
        return taunt;
    }

    @Override
    public void setTaunt(int taunt) {
        this.taunt = taunt;
    }

    @Override
    public int getTrail() {
        return trail;
    }

    @Override
    public void setTrail(int trail) {
        this.trail = trail;
    }

    @Override
    public int getWinDance() {
        return winDance;
    }

    @Override
    public void setWinDance(int winDance) {
        this.winDance = winDance;
    }

    @Override
    public int getWinEffect() {
        return winEffect;
    }

    @Override
    public void setWinEffect(int winEffect) {
        this.winEffect = winEffect;
    }

    @Override
    public int getSoloKills() {
        return soloKills;
    }

    @Override
    public void setSoloKills(int soloKills) {
        this.soloKills = soloKills;
    }

    @Override
    public int getTeamKills() {
        return teamKills;
    }

    @Override
    public void setTeamKills(int teamKills) {
        this.teamKills = teamKills;
    }

    @Override
    public int getRankedKills() {
        return rankedKills;
    }

    @Override
    public void setRankedKills(int rankedKills) {
        this.rankedKills = rankedKills;
    }

    @Override
    public int getSoloWins() {
        return soloWins;
    }

    @Override
    public void setSoloWins(int soloWins) {
        this.soloWins = soloWins;
    }

    @Override
    public int getTeamWins() {
        return teamWins;
    }

    @Override
    public void setTeamWins(int teamWins) {
        this.teamWins = teamWins;
    }

    @Override
    public int getRankedWins() {
        return rankedWins;
    }

    @Override
    public void setRankedWins(int rankedWins) {
        this.rankedWins = rankedWins;
    }

    @Override
    public int getSoloShots() {
        return soloShots;
    }

    @Override
    public void setSoloShots(int soloShots) {
        this.soloShots = soloShots;
    }

    @Override
    public int getTeamShots() {
        return teamShots;
    }

    @Override
    public void setTeamShots(int teamShots) {
        this.teamShots = teamShots;
    }

    @Override
    public int getRankedShots() {
        return rankedShots;
    }

    @Override
    public void setRankedShots(int rankedShots) {
        this.rankedShots = rankedShots;
    }

    @Override
    public int getSoloSShots() {
        return soloSShots;
    }

    @Override
    public void setSoloSShots(int soloSShots) {
        this.soloSShots = soloSShots;
    }

    @Override
    public int getTeamSShots() {
        return teamSShots;
    }

    @Override
    public void setTeamSShots(int teamSShots) {
        this.teamSShots = teamSShots;
    }

    @Override
    public int getRankedSShots() {
        return rankedSShots;
    }

    @Override
    public void setRankedSShots(int rankedSShots) {
        this.rankedSShots = rankedSShots;
    }

    @Override
    public int getSoloWalked() {
        return soloWalked;
    }

    @Override
    public void setSoloWalked(int soloWalked) {
        this.soloWalked = soloWalked;
    }

    @Override
    public int getTeamWalked() {
        return teamWalked;
    }

    @Override
    public void setTeamWalked(int teamWalked) {
        this.teamWalked = teamWalked;
    }

    @Override
    public int getRankedWalked() {
        return rankedWalked;
    }

    @Override
    public void setRankedWalked(int rankedWalked) {
        this.rankedWalked = rankedWalked;
    }

    @Override
    public int getSoloPlayed() {
        return soloPlayed;
    }

    @Override
    public void setSoloPlayed(int soloPlayed) {
        this.soloPlayed = soloPlayed;
    }

    @Override
    public int getTeamPlayed() {
        return teamPlayed;
    }

    @Override
    public void setTeamPlayed(int teamPlayed) {
        this.teamPlayed = teamPlayed;
    }

    @Override
    public int getRankedPlayed() {
        return rankedPlayed;
    }

    @Override
    public void setRankedPlayed(int rankedPlayed) {
        this.rankedPlayed = rankedPlayed;
    }

    @Override
    public int getSoloBreak() {
        return soloBreak;
    }

    @Override
    public void setSoloBreak(int soloBreak) {
        this.soloBreak = soloBreak;
    }

    @Override
    public int getTeamBreak() {
        return teamBreak;
    }

    @Override
    public void setTeamBreak(int teamBreak) {
        this.teamBreak = teamBreak;
    }

    @Override
    public int getRankedBreak() {
        return rankedBreak;
    }

    @Override
    public void setRankedBreak(int rankedBreak) {
        this.rankedBreak = rankedBreak;
    }

    @Override
    public int getSoloPlaced() {
        return soloPlaced;
    }

    @Override
    public void setSoloPlaced(int soloPlaced) {
        this.soloPlaced = soloPlaced;
    }

    @Override
    public int getTeamPlaced() {
        return teamPlaced;
    }

    @Override
    public void setTeamPlaced(int teamPlaced) {
        this.teamPlaced = teamPlaced;
    }

    @Override
    public int getRankedPlaced() {
        return rankedPlaced;
    }

    @Override
    public void setRankedPlaced(int rankedPlaced) {
        this.rankedPlaced = rankedPlaced;
    }

    @Override
    public int getSoloDeaths() {
        return soloDeaths;
    }

    @Override
    public void setSoloDeaths(int soloDeaths) {
        this.soloDeaths = soloDeaths;
    }

    @Override
    public int getTeamDeaths() {
        return teamDeaths;
    }

    @Override
    public void setTeamDeaths(int teamDeaths) {
        this.teamDeaths = teamDeaths;
    }

    @Override
    public int getRankedDeaths() {
        return rankedDeaths;
    }

    @Override
    public void setRankedDeaths(int rankedDeaths) {
        this.rankedDeaths = rankedDeaths;
    }

    @Override
    public int getElo() {
        return elo;
    }

    @Override
    public void setElo(int elo) {
        this.elo = elo;
    }

    @Override
    public void addSoloAssists(int soloAssists) {
        this.soloAssists += soloAssists;
    }

    @Override
    public void addTeamAssists(int teamAssists) {
        this.teamAssists += teamAssists;
    }

    @Override
    public void addRankedAssists(int rankedAssists) {
        this.rankedAssists += rankedAssists;
    }

    @Override
    public void addSoloKill50(int soloKill50) {
        this.soloKill50 += soloKill50;
    }

    @Override
    public void addSoloKill25(int soloKill25) {
        this.soloKill25 += soloKill25;
    }

    @Override
    public void addSoloKill5(int soloKill5) {
        this.soloKill5 += soloKill5;
    }

    @Override
    public void addTeamKill50(int teamKill50) {
        this.teamKill50 += teamKill50;
    }

    @Override
    public void addTeamKill25(int teamKill25) {
        this.teamKill25 += teamKill25;
    }

    @Override
    public void addTeamKill5(int teamKill5) {
        this.teamKill5 += teamKill5;
    }

    @Override
    public void addRankedKill50(int rankedKill50) {
        this.rankedKill50 += rankedKill50;
    }

    @Override
    public void addRankedKill25(int rankedKill25) {
        this.rankedKill25 += rankedKill25;
    }

    @Override
    public void addRankedKill5(int rankedKill5) {
        this.rankedKill5 += rankedKill5;
    }

    @Override
    public void addSouls(int souls) {
        this.souls += souls;
    }

    @Override
    public void addXp(int xp) {
        this.xp += xp;
    }

    @Override
    public void addCoins(int coins) {
        this.coins += coins;
    }

    @Override
    public void removeCoins(int coins) {
        this.coins -= coins;
    }

    @Override
    public void removeSouls(int souls) {
        this.souls -= souls;
    }

    @Override
    public void removeXp(int xp) {
        this.xp -= xp;
    }

    @Override
    public int getSoloAssists() {
        return soloAssists;
    }

    @Override
    public void setSoloAssists(int soloAssists) {
        this.soloAssists = soloAssists;
    }

    @Override
    public int getTeamAssists() {
        return teamAssists;
    }

    @Override
    public void setTeamAssists(int teamAssists) {
        this.teamAssists = teamAssists;
    }

    @Override
    public int getRankedAssists() {
        return rankedAssists;
    }

    @Override
    public void setRankedAssists(int rankedAssists) {
        this.rankedAssists = rankedAssists;
    }

    @Override
    public int getSoloKill50() {
        return soloKill50;
    }

    @Override
    public void setSoloKill50(int soloKill50) {
        this.soloKill50 = soloKill50;
    }

    @Override
    public int getSoloKill25() {
        return soloKill25;
    }

    @Override
    public void setSoloKill25(int soloKill25) {
        this.soloKill25 = soloKill25;
    }

    @Override
    public int getSoloKill5() {
        return soloKill5;
    }

    @Override
    public void setSoloKill5(int soloKill5) {
        this.soloKill5 = soloKill5;
    }

    @Override
    public int getTeamKill50() {
        return teamKill50;
    }

    @Override
    public void setTeamKill50(int teamKill50) {
        this.teamKill50 = teamKill50;
    }

    @Override
    public int getTeamKill25() {
        return teamKill25;
    }

    @Override
    public void setTeamKill25(int teamKill25) {
        this.teamKill25 = teamKill25;
    }

    @Override
    public int getTeamKill5() {
        return teamKill5;
    }

    @Override
    public void setTeamKill5(int teamKill5) {
        this.teamKill5 = teamKill5;
    }

    @Override
    public int getRankedKill50() {
        return rankedKill50;
    }

    @Override
    public void setRankedKill50(int rankedKill50) {
        this.rankedKill50 = rankedKill50;
    }

    @Override
    public int getRankedKill25() {
        return rankedKill25;
    }

    @Override
    public void setRankedKill25(int rankedKill25) {
        this.rankedKill25 = rankedKill25;
    }

    @Override
    public int getRankedKill5() {
        return rankedKill5;
    }

    @Override
    public void setRankedKill5(int rankedKill5) {
        this.rankedKill5 = rankedKill5;
    }

    @Override
    public int getSouls() {
        return souls;
    }

    @Override
    public void setSouls(int souls) {
        this.souls = souls;
    }

    @Override
    public int getXp() {
        return xp;
    }

    @Override
    public void setXp(int xp) {
        this.xp = xp;
    }

    @Override
    public int getCoins() {
        return coins;
    }

    @Override
    public void setCoins(int coins) {
        this.coins = coins;
    }

    @Override
    public String toString() {
        return "UltraPlayer{" +
                "favorites=" + favorites +
                ", kits=" + kits +
                ", perks=" + perks +
                ", balloons=" + balloons +
                ", glasses=" + glasses +
                ", killeffects=" + killeffects +
                ", killsounds=" + killsounds +
                ", partings=" + partings +
                ", taunts=" + taunts +
                ", trails=" + trails +
                ", windances=" + windances +
                ", wineffects=" + wineffects +
                ", ranking=" + ranking +
                ", headsCollected=" + headsCollected +
                ", soulWellHead=" + soulWellHead +
                ", soulWellExtra=" + soulWellExtra +
                ", soulWellMax=" + soulWellMax +
                ", soulanimation=" + soulanimation +
                ", cubeanimation=" + cubeanimation +
                ", cubelets=" + cubelets +
                ", rows=" + rows +
                ", armorDance=" + armorDance +
                ", balloon=" + balloon +
                ", glass=" + glass +
                ", killEffect=" + killEffect +
                ", killSound=" + killSound +
                ", parting=" + parting +
                ", taunt=" + taunt +
                ", trail=" + trail +
                ", winDance=" + winDance +
                ", winEffect=" + winEffect +
                ", soloKills=" + soloKills +
                ", teamKills=" + teamKills +
                ", rankedKills=" + rankedKills +
                ", soloWins=" + soloWins +
                ", teamWins=" + teamWins +
                ", rankedWins=" + rankedWins +
                ", soloShots=" + soloShots +
                ", teamShots=" + teamShots +
                ", rankedShots=" + rankedShots +
                ", soloSShots=" + soloSShots +
                ", teamSShots=" + teamSShots +
                ", rankedSShots=" + rankedSShots +
                ", soloWalked=" + soloWalked +
                ", teamWalked=" + teamWalked +
                ", rankedWalked=" + rankedWalked +
                ", soloPlayed=" + soloPlayed +
                ", teamPlayed=" + teamPlayed +
                ", rankedPlayed=" + rankedPlayed +
                ", soloBreak=" + soloBreak +
                ", teamBreak=" + teamBreak +
                ", rankedBreak=" + rankedBreak +
                ", soloPlaced=" + soloPlaced +
                ", teamPlaced=" + teamPlaced +
                ", rankedPlaced=" + rankedPlaced +
                ", soloDeaths=" + soloDeaths +
                ", teamDeaths=" + teamDeaths +
                ", rankedDeaths=" + rankedDeaths +
                ", soloAssists=" + soloAssists +
                ", teamAssists=" + teamAssists +
                ", rankedAssists=" + rankedAssists +
                ", soloKill50=" + soloKill50 +
                ", soloKill25=" + soloKill25 +
                ", soloKill5=" + soloKill5 +
                ", teamKill50=" + teamKill50 +
                ", teamKill25=" + teamKill25 +
                ", teamKill5=" + teamKill5 +
                ", rankedKill50=" + rankedKill50 +
                ", rankedKill25=" + rankedKill25 +
                ", rankedKill5=" + rankedKill5 +
                ", elo=" + elo +
                ", souls=" + souls +
                ", xp=" + xp +
                ", coins=" + coins +
                ", level=" + level +
                ", name='" + name + '\'' +
                ", eloRank='" + eloRank + '\'' +
                ", speed=" + speed +
                ", lastChange=" + lastChange +
                ", nightVision=" + nightVision +
                ", spectatorsView=" + spectatorsView +
                ", firstPerson=" + firstPerson +
                ", soloKit=" + soloKit +
                ", soloKitLevel=" + soloKitLevel +
                ", teamKit=" + teamKit +
                ", teamKitLevel=" + teamKitLevel +
                ", rankedKit=" + rankedKit +
                ", rankedKitLevel=" + rankedKitLevel +
                ", heads=" + heads +
                '}';
    }
}