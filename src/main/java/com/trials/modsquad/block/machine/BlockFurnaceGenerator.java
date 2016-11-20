package com.trials.modsquad.block.machine;

import com.trials.modsquad.ModSquad;
import com.trials.modsquad.Ref;
import com.trials.modsquad.block.tile.TileFurnaceGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;
import javax.annotation.Nullable;
import static com.trials.modsquad.Ref.GUI_ID_FURNACE_GEN;

@SuppressWarnings("deprecation")
public class BlockFurnaceGenerator extends Block {

    public static final PropertyDirection PROPERTYFACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockFurnaceGenerator(String s, String s1){
        super(Material.IRON);
        setUnlocalizedName(s);
        setRegistryName(s1);
        setCreativeTab(Ref.tabModSquad);
        setHarvestLevel("pickaxe", 1);
        setResistance(30F);
        setHardness(5F);
        setDefaultState(blockState.getBaseState().withProperty(PROPERTYFACING, EnumFacing.NORTH));
    }

    @Override
    protected BlockStateContainer createBlockState() { return new BlockStateContainer(this, PROPERTYFACING); }

    @Override
    public int getMetaFromState(IBlockState state) { return 0; }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(PROPERTYFACING, EnumFacing.NORTH);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase user, ItemStack stack)
    {
        super.onBlockPlacedBy(world, pos, state, user,stack);
        world.setBlockState(pos, state.withProperty(PROPERTYFACING, user.getHorizontalFacing().rotateAround(EnumFacing.Axis.Y)));
    }

    @Override
    public TileEntity createTileEntity(World worldIn, IBlockState state) {
        return new TileFurnaceGenerator();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(worldIn.getTileEntity(pos) == null || playerIn.isSneaking()) return false;
        playerIn.openGui(ModSquad.instance, GUI_ID_FURNACE_GEN, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) { return true; }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) { // Drop item when block breaks
        TileEntity t = worldIn.getTileEntity(pos);
        if(!(t instanceof IItemHandlerModifiable)) return;
        IItemHandlerModifiable h = (IItemHandlerModifiable) t;
        for(int i = 0; i<h.getSlots(); ++i) {
            if(h.getStackInSlot(i)!=null && h.getStackInSlot(i).getCount()>0)
                InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), h.getStackInSlot(i));
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
        return false; // Since the model doesn't inherit a full block model
    }
}
