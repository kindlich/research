package com.hrznstudio.research.common.blocks.researchtable;

import com.hrznstudio.research.GuiHandler;
import com.hrznstudio.research.ResearchMod;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class BlockResearchTable extends Block implements ITileEntityProvider {

    public BlockResearchTable() {
        super(Material.WOOD);
        setRegistryName(ResearchMod.MODID, "research_table");
        setTranslationKey(String.format("%s.research_table", ResearchMod.MODID));
        setHardness(3.5f);
        setSoundType(SoundType.WOOD);
        setCreativeTab(CreativeTabs.MISC);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileResearchTable();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(worldIn.isRemote)
            return true;

        final TileEntity tile = worldIn.getTileEntity(pos);
        if(tile instanceof TileResearchTable)
            playerIn.openGui(ResearchMod.instance, GuiHandler.GUIs.ResearchTable.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());

        return true;
    }
}