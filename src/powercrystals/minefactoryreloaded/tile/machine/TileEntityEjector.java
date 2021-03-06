package powercrystals.minefactoryreloaded.tile.machine;

import buildcraft.api.transport.IPipeTile.PipeType;

import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.core.inventory.IInventoryManager;
import powercrystals.core.inventory.InventoryManager;
import powercrystals.core.util.Util;
import powercrystals.core.util.UtilInventory;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactory;

public class TileEntityEjector extends TileEntityFactory
{
	private boolean _lastRedstoneState;
	
	public TileEntityEjector()
	{
		super(Machine.Ejector);
		setManageSolids(true);
		setCanRotate(true);
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if(worldObj.isRemote)
		{
			return;
		}
		boolean redstoneState = Util.isRedstonePowered(this);
		if(redstoneState && !_lastRedstoneState)
		{
			Map<ForgeDirection, IInventory> chests = UtilInventory.
					findChests(worldObj, xCoord, yCoord, zCoord);
			inv: for(Entry<ForgeDirection, IInventory> chest : chests.entrySet())
			{
				if(chest.getKey() == getDirectionFacing())
				{
					continue;
				}
				
				IInventoryManager inventory = InventoryManager.create(chest.getValue(),
						chest.getKey().getOpposite());
				Map<Integer, ItemStack> contents = inventory.getContents();
				
				for(Entry<Integer, ItemStack> stack : contents.entrySet())
				{
					if(stack == null || stack.getValue() == null)
					{
						continue;
					}
					
					if(chest.getValue() instanceof ISidedInventory)
					{ // TODO: expose canRemoveItem in IInventoryManager
						ISidedInventory sided = (ISidedInventory)chest.getValue();
						if(!sided.canExtractItem(stack.getKey(), stack.getValue(),
								chest.getKey().getOpposite().ordinal()))
						{
							continue;
						}
					}
					
					ItemStack stackToDrop = stack.getValue().copy();
					stackToDrop.stackSize = 1;
					ItemStack remaining = UtilInventory.dropStack(this, stackToDrop,
							this.getDirectionFacing(), this.getDirectionFacing());
					
					// remaining == null if dropped successfully.
					if(remaining == null)
					{
						inventory.removeItem(1, stackToDrop);
						break inv;
					}
				}
			}
		}
		_lastRedstoneState = redstoneState;
	}

	@Override
	public ConnectOverride overridePipeConnection(PipeType type, ForgeDirection with) {
		if (type == PipeType.STRUCTURE)
			return ConnectOverride.CONNECT;
		if (with == getDirectionFacing())
			return super.overridePipeConnection(type, with);
		return ConnectOverride.DISCONNECT;
	}

	@Override
	public boolean canConduitConnect(ForgeDirection side)
	{
		if (side == getDirectionFacing())
			return super.canConduitConnect(side);
		return false;
	}
}
