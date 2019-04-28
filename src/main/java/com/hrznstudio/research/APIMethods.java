package com.hrznstudio.research;

import com.hrznstudio.research.api.player.PlayerProgress;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashMap;
import java.util.Map;

public class APIMethods {
    private static final Map<EntityPlayer, PlayerProgress> playerMap = new HashMap<>();

    public static PlayerProgress getProgress(EntityPlayer forPlayer) {
        return playerMap.computeIfAbsent(forPlayer, PlayerProgress::new);
    }
}
