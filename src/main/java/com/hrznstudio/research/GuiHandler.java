package com.hrznstudio.research;

import com.hrznstudio.research.common.blocks.researchtable.GuiResearchTable2;
import com.hrznstudio.research.common.blocks.researchtable.TileResearchTable;
import com.hrznstudio.research.helpers.ContainerResearchTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        final TileEntity tile;
        if(ID > GUIs.values().length || (tile = world.getTileEntity(new BlockPos(x, y, z))) == null)
            return null;

        //Replace with switch if bigger
        if(GUIs.values()[ID] == GUIs.ResearchTable) {
            if(tile instanceof TileResearchTable)
                return new ContainerResearchTable();
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        final TileEntity tile;
        if(ID > GUIs.values().length || (tile = world.getTileEntity(new BlockPos(x, y, z))) == null)
            return null;

        //Replace with switch if bigger
        if(GUIs.values()[ID] == GUIs.ResearchTable) {
            if(tile instanceof TileResearchTable)
                return new GuiResearchTable2((TileResearchTable) tile, player);
        }
        return null;
    }

    public enum GUIs {
        ResearchTable()
    }
}
