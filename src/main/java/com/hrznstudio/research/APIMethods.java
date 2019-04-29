package com.hrznstudio.research;

import com.hrznstudio.research.api.player.PlayerProgress;
import com.hrznstudio.research.api.research.IResearch;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class APIMethods {
    private static final Map<EntityPlayer, PlayerProgress> playerMap = new HashMap<>();
    private static Collection<IResearch> researches = new ArrayList<>();

    public static PlayerProgress getProgress(EntityPlayer forPlayer) {
        return playerMap.computeIfAbsent(forPlayer, PlayerProgress::new);
    }

    public static void registerResearch(IResearch research) {
        researches.add(research);
    }

    @Contract(pure = true)
    public static Collection<IResearch> getAllResearches() {
        return researches;
    }
}
