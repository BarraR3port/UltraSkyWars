package io.github.Leonardo0013YT.UltraSkyWars.controllers;

import io.github.Leonardo0013YT.UltraSkyWars.UltraSkyWars;
import io.github.Leonardo0013YT.UltraSkyWars.WorldEditUtils_New;
import io.github.Leonardo0013YT.UltraSkyWars.WorldEditUtils_Old;
import io.github.Leonardo0013YT.UltraSkyWars.interfaces.WorldEdit;

public class WorldController {

    private final WorldEdit edit;

    public WorldController(UltraSkyWars plugin) {
        if (plugin.getVc().is1_13to16()) {
            edit = new WorldEditUtils_New(plugin);
        } else {
            edit = new WorldEditUtils_Old(plugin);
        }
    }

    public WorldEdit getEdit() {
        return edit;
    }
}