package com.hrznstudio.research;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
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
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent ev) {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent ev) {
    }

}
