package com.hrznstudio.research.common.blocks.researchtable;

import com.hrznstudio.research.api.research.IResearchAid;
import com.hrznstudio.research.api.research.IResearchTool;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TileResearchTable extends TileEntity {
    
    //TODO make them actually work
    private final Map<BlockPos, IResearchAid> knownAids = new HashMap<>();
    private final List<IResearchTool> placedTools = new ArrayList<>();
    //Where the research tools are actually stored
    private final ItemStackHandler inventory;
    
    public TileResearchTable() {
        inventory = new ItemStackHandler();
        inventory.setSize(9);
        for(int i = 0; i < 9; ) {
            inventory.setStackInSlot(i, new ItemStack(Blocks.BEDROCK, ++i));
        }
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        
        //Inventory
        if(compound.hasKey("inventory", 9)) {
            inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        }
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        
        //"inventory" tag
        compound.setTag("inventory", inventory.serializeNBT());
        
        
        return compound;
    }
    
    public ItemStackHandler getInventory() {
        return inventory;
    }
}
