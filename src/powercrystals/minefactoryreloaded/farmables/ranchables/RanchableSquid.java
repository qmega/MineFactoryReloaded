package powercrystals.minefactoryreloaded.farmables.ranchables;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.IFactoryRanchable;
import powercrystals.minefactoryreloaded.api.RanchedItem;

public class RanchableSquid implements IFactoryRanchable
{
	@Override
	public Class<?> getRanchableEntity()
	{
		return EntitySquid.class;
	}
	
	@Override
	public List<RanchedItem> ranch(World world, EntityLivingBase entity, IInventory rancher)
	{
		NBTTagCompound tag = entity.getEntityData();
		if (tag.getLong("mfr:lastRanched") > world.getWorldTime())
			return null;
		tag.setLong("mfr:lastRanched", world.getWorldTime() + 20 * 5);
		
		List<RanchedItem> drops = new ArrayList<RanchedItem>();
		drops.add(new RanchedItem(Item.dyePowder, 1, 0));
		return drops;
	}
	
}
