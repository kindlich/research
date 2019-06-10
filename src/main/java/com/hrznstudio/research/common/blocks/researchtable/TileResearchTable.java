package com.hrznstudio.research.common.blocks.researchtable;

import com.hrznstudio.research.ResearchMod;
import com.hrznstudio.research.api.place.IResearchPlace;
import com.hrznstudio.research.api.player.PlayerProgress;
import com.hrznstudio.research.api.research.IResearchAid;
import com.hrznstudio.research.api.research.IResearchTool;
import com.hrznstudio.research.common.blocks.aids.aidBattery.AidBattery;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.Collections;

public class TileResearchTable extends TileEntity {
    private final ItemStackHandlerResearchTable toolSlots;
    private IResearchAid activeAid = new AidBattery();

    public TileResearchTable() {

        toolSlots = new ItemStackHandlerResearchTable(18, 3);
        for (int i = 0; i < toolSlots.getSlots(); i++) {
            toolSlots.setStackInSlot(i, new ItemStack(Blocks.WOOL, i + 1, i % 16));
        }

        toolSlots.setToolSlot(3, ItemStack.EMPTY);
    }

    public ItemStackHandlerResearchTable getToolSlots() {
        return toolSlots;
    }

    public IResearchPlace makePlace(PlayerProgress progress) {
        return new ResearchPlaceResearchTable(new AidBattery(), progress);
    }
}
