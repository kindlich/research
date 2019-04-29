package com.hrznstudio.research.helpers;

import com.hrznstudio.research.common.blocks.researchtable.TileResearchTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Contract;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ContainerResearchTable extends Container {

    private final TileResearchTable tile;

    public ContainerResearchTable(TileResearchTable tile, InventoryPlayer playerInv) {
        this.tile = tile;


        //final ItemStackHandler inventory = tile.getInventory();
        //for(int i = 0; i < inventory.getSlots(); i++)
        //    this.addSlotToContainer(new SlotResearchAid(inventory, i, 32 * i - 53, 0));


        //Player Inventory
        {
            for(int i = 0; i < 3; ++i) {
                for(int j = 0; j < 9; ++j) {
                    this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
                }
            }

            for(int k = 0; k < 9; ++k) {
                this.addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 142));
            }
        }
    }


    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}