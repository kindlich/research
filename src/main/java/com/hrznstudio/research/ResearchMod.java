package com.hrznstudio.research;

import com.hrznstudio.research.api.research.IResearchStep;
import com.hrznstudio.research.helpers.LinearResearch;
import com.hrznstudio.research.helpers.SimpleResearchStep;
import com.hrznstudio.research.helpers.SimpleResearchStepProgress;
import com.hrznstudio.research.helpers.SimpleResearchStepWithProgress;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

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
        final ResourceLocation researchId = new ResourceLocation("research", "id1");
        final ResourceLocation table = new ResourceLocation("research", "place/table");

        final List<IResearchStep> steps = new ArrayList<>();

        steps.add(new SimpleResearchStep(new ResourceLocation("research", "step1")));
        steps.add(new SimpleResearchStep(new ResourceLocation("research", "step2")));
        steps.add(new SimpleResearchStep(new ResourceLocation("research", "step3")));
        steps.add(new SimpleResearchStepWithProgress(new ResourceLocation("research", "step4"), (s, p) -> new SimpleResearchStepProgress(s)));

        APIMethods.registerResearch(new LinearResearch(researchId, table, steps));
        APIMethods.registerResearch(new LinearResearch(researchId, table, steps));
        APIMethods.registerResearch(new LinearResearch(researchId, table, steps));
        APIMethods.registerResearch(new LinearResearch(researchId, table, steps));
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent ev) {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent ev) {
    }

}
