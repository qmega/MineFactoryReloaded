package powercrystals.minefactoryreloaded.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.setup.Machine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockFactoryMachine extends ItemBlockFactory
{
	private int _machineBlockIndex;
	
	public ItemBlockFactoryMachine(int blockId)
	{
		super(blockId);
		setMaxDamage(0);
		setHasSubtypes(true);
		
		_machineBlockIndex = ((BlockFactoryMachine)Block.blocksList[getBlockID()]).getBlockIndex();
		int highestMeta = Machine.getHighestMetadata(_machineBlockIndex);
		String[] names = new String[highestMeta + 1];
		for(int i = 0; i <= highestMeta; i++)
		{
			names[i] = Machine.getMachineFromIndex(_machineBlockIndex, i).getInternalName();
		}
		setNames(names);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return _names[Math.min(stack.getItemDamage(), _names.length - 1)];
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean adv)
	{
		Machine.getMachineFromId(getBlockID(), stack.getItemDamage()).
				addInformation(stack, player, info, adv);
	}
}
