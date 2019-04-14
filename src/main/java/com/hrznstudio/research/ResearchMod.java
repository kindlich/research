package com.hrznstudio.research;

import com.hrznstudio.research.api.impl.ResearchBuilder;
import com.hrznstudio.research.api.impl.ResearchStepBuilder;
import com.hrznstudio.research.api.research.*;
import net.minecraft.init.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = ResearchMod.MODID, name = ResearchMod.NAME, version = ResearchMod.VERSION)
public class ResearchMod {
    
    public static final String MODID = "research";
    public static final String NAME = "Research";
    public static final String VERSION = "GRADLE:VERSION";
    public static final Logger LOGGER = LogManager.getLogger(NAME);
    
    @Mod.Instance(value = ResearchMod.MODID)
    public static ResearchMod instance;
    
    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent ev) {
        final IResearchTool tool_sword = APIMethods.getResearchTool(new ResourceLocation(MODID, "tool_sword"), true);
        tool_sword.ingredientsToLevel().put(Ingredient.fromItem(Items.DIAMOND_SWORD), 1);
        
        final IResearchStep step1 = new ResearchStepBuilder(new ResourceLocation(MODID, "step_one"), 0.50).addResearchTool(tool_sword, 1).toResearchStep();
        final IResearchStep step2 = new ResearchStepBuilder(new ResourceLocation(MODID, "step_two"), 0.33).toResearchStep();
        
        APIMethods.registerResearchStep(step1);
        APIMethods.registerResearchStep(step2);
        
        final IResearch researchA = new ResearchBuilder(new ResourceLocation(MODID, "research_a"), step1).addResearchStep(step2).toResearch();
        APIMethods.registerResearch(researchA);
        
    }
    
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent ev) {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
    }
    
}
