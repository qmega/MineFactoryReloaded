package powercrystals.minefactoryreloaded.tile.machine;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.ForgeDirection;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.core.HarvestAreaManager;
import powercrystals.minefactoryreloaded.core.IHarvestAreaContainer;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactory;

public class TileEntityMobCounter extends TileEntityFactory implements IHarvestAreaContainer
{
	private HarvestAreaManager _ham;
	private int _lastMobCount;
	
	public TileEntityMobCounter()
	{
		super(Machine.MobCounter);
		_ham = new HarvestAreaManager(this, 2, 2, 1);
		setCanRotate(true);
	}
	
	@Override
	public HarvestAreaManager getHAM()
	{
		return _ham;
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		
		if(worldObj == null)
		{
			return;
		}
		
		int mobCount = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, _ham.getHarvestArea().toAxisAlignedBB()).size();
		if(mobCount != _lastMobCount)
		{
			_lastMobCount = mobCount;
			if(!worldObj.isRemote)
			{
				worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, MineFactoryReloadedCore.machineBlocks.get(Machine.MobCounter.getBlockIndex()).blockID);
			}
		}
	}
	
	@Override
	public int getRedNetOutput(ForgeDirection side)
	{
		return _lastMobCount;
	}
}
