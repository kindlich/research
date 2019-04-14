package com.hrznstudio.research;

import com.hrznstudio.research.api.impl.Research;
import com.hrznstudio.research.api.impl.ResearchBuilder;
import com.hrznstudio.research.api.impl.ResearchStepBuilder;
import com.hrznstudio.research.api.research.*;
import com.hrznstudio.research.command.ResearchCommand;
import net.minecraft.init.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

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
        final IResearchStep step3 = new ResearchStepBuilder(new ResourceLocation(MODID, "step_three"), 0.33).toResearchStep();
        final IResearchStep step4 = new ResearchStepBuilder(new ResourceLocation(MODID, "step_four"), 0.33).toResearchStep();
        final IResearchStep step5 = new ResearchStepBuilder(new ResourceLocation(MODID, "step_five"), 0.33).toResearchStep();
        final IResearchStep step6 = new ResearchStepBuilder(new ResourceLocation(MODID, "step_six"), 0.33).toResearchStep();
        final IResearchStep step7 = new ResearchStepBuilder(new ResourceLocation(MODID, "step_seven"), 0.33).toResearchStep();
        final IResearchStep step8 = new ResearchStepBuilder(new ResourceLocation(MODID, "step_eight"), 0.33).toResearchStep();
        final IResearchStep step9 = new ResearchStepBuilder(new ResourceLocation(MODID, "step_nine"), 0.33).toResearchStep();
        final IResearchStep step10 = new ResearchStepBuilder(new ResourceLocation(MODID, "step_ten"), 0.33).toResearchStep();
        final IResearchStep step11 = new ResearchStepBuilder(new ResourceLocation(MODID, "step_eleven"), 0.33).toResearchStep();
        final IResearchStep step12 = new ResearchStepBuilder(new ResourceLocation(MODID, "step_twelve"), 0.33).toResearchStep();
        final IResearchStep step13 = new ResearchStepBuilder(new ResourceLocation(MODID, "step_thirteen"), 0.33).toResearchStep();
        final IResearchStep step14 = new ResearchStepBuilder(new ResourceLocation(MODID, "step_fourteen"), 0.33).toResearchStep();
        
        
        Arrays.asList(step1, step2, step3, step4, step5, step6, step7, step8, step9, step10, step11, step12, step13, step14).forEach(APIMethods::registerResearchStep);
        
        final IResearch researchA = new ResearchBuilder(new ResourceLocation(MODID, "research_a"), step1).addResearchStep(step2).toResearch();
        APIMethods.registerResearch(researchA);
        
        final Research researchB = new ResearchBuilder(new ResourceLocation(MODID, "research_b"), step3).addResearchStep(step4).addResearchStep(step5).toResearch();
        APIMethods.registerResearch(researchB);
        
        final Research researchC = new ResearchBuilder(new ResourceLocation(MODID, "research_c"), step6).addChoice(step7, step8).toResearch();
        APIMethods.registerResearch(researchC);
        
        final Research researchD = new ResearchBuilder(new ResourceLocation(MODID, "research_d"), step9).addOptional(step10, step11, step12).addChoice(step13).addResearchStep(step14).toResearch();
        researchD.addPrerequisite(researchC);
        APIMethods.registerResearch(researchD);
    }
    
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent ev) {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
    }
    
    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent ev) {
        ev.registerServerCommand(new ResearchCommand());
    }
    
}
