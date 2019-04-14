package com.hrznstudio.research.common.blocks.researchtable;

import com.hrznstudio.research.api.research.*;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TileResearchTable extends TileEntity {
    
    private final List<IResearchStep> completedSteps = new ArrayList<>();
    //TODO make them actually work
    private final Map<BlockPos, IResearchAid> knownAids = new HashMap<>();
    private final List<IResearchTool> placedTools = new ArrayList<>();
    //Where the research tools are actually stored
    private final ItemStackHandler inventory;
    IResearch currentResearch = null;
    private IResearchStepInstance currentStep = null;
    
    public TileResearchTable() {
        inventory = new ItemStackHandler();
        inventory.setSize(9);
        for(int i = 0; i < 9; ) {
            inventory.setStackInSlot(i, new ItemStack(Blocks.BEDROCK, ++i));
        }
        
        
    }
    
    @Contract(pure = true)
    public List<IResearchStep> getCompletedSteps() {
        return completedSteps;
    }
    
    @Contract(pure = true)
    @Nullable
    public IResearchStepInstance getCurrentStep() {
        return currentStep;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        
        //Research
        {
            currentResearch = null;
            currentStep = null;
            if(!compound.hasKey("research", 10)) {
                return;
            }
            final NBTTagCompound research = compound.getCompoundTag("research");
            if(research.hasKey("currentResearch", 8)) {
                currentResearch = APIMethods.getResearch(new ResourceLocation(research.getString("currentResearch")));
            }
            
            if(research.hasKey("completedSteps", 9)) {
                completedSteps(research.getTagList("completedSteps", 8));
            }
        }
        
        //Inventory
        if(compound.hasKey("inventory", 9)) {
            inventory.deserializeNBT(compound.getCompoundTag("inventory"));
        }
    }
    
    private void completedSteps(NBTTagList completedStepsTag) {
        if(currentResearch == null) {
            return;
        }
        currentStep = currentResearch.getStart();
        for(final NBTBase nbtBase : completedStepsTag) {
            final IResearchStep step = getResearchStep(nbtBase);
            
            if(step == null) {
                return;
            } else {
                final Optional<IResearchStepInstance> any = currentStep.getNextSteps().stream().filter(stepInstance -> stepInstance.getStep().getId().equals(step.getId())).findAny();
                if(!any.isPresent()) {
                    return;
                }
                completedSteps.add(currentStep.getStep());
                currentStep = any.get();
            }
        }
    }
    
    
    @Nullable
    @Contract("null -> null")
    private IResearchStep getResearchStep(NBTBase tag) {
        if(tag.getId() != 8)
            return null;
        return APIMethods.getResearchStep(new ResourceLocation(((NBTTagString) tag).getString()));
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        
        //"research" Tag
        {
            final NBTTagCompound researchTag = new NBTTagCompound();
            if(currentResearch != null)
                researchTag.setString("currentResearch", currentResearch.getId().toString());
            if(!completedSteps.isEmpty()) {
                final NBTTagList completedList = new NBTTagList();
                for(IResearchStep completedStep : completedSteps) {
                    completedList.appendTag(new NBTTagString(completedStep.getId().toString()));
                }
                researchTag.setTag("completedSteps", completedList);
            }
            compound.setTag("research", researchTag);
        }
        
        //"inventory" tag
        compound.setTag("inventory", inventory.serializeNBT());
        
        
        return compound;
    }
    
    public ItemStackHandler getInventory() {
        return inventory;
    }
    
    public void setCurrentResearch(@Nullable IResearch research) {
        this.currentResearch = research;
        this.completedSteps.clear();
    }
}
