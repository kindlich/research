package com.hrznstudio.research;

import com.hrznstudio.research.common.blocks.researchtable.BlockResearchTable;
import com.hrznstudio.research.common.blocks.researchtable.TileResearchTable;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@Mod.EventBusSubscriber(modid = ResearchMod.MODID)
public class CommonEvents {

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(Blocks.ResearchTable = new BlockResearchTable());
        GameRegistry.registerTileEntity(TileResearchTable.class, Blocks.ResearchTable.getRegistryName());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        final Item value = new ItemBlock(Blocks.ResearchTable).setRegistryName(Blocks.ResearchTable.getRegistryName());
        event.getRegistry().register(value);
    }
}
