package com.hrznstudio.research.api.research;

import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface IResearchTool {

    int getLevel(ItemStack stack);
}
