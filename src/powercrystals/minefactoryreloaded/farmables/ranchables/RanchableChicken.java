package powercrystals.minefactoryreloaded.farmables.ranchables;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import powercrystals.minefactoryreloaded.api.IFactoryRanchable;
import powercrystals.minefactoryreloaded.api.RanchedItem;

public class RanchableChicken implements IFactoryRanchable
{
	protected Random rand = new Random();
	
	@Override
	public Class<?> getRanchableEntity()
	{
		return EntityChicken.class;
	}
	
	@Override
	public List<RanchedItem> ranch(World world, EntityLivingBase entity, IInventory rancher)
	{
		List<RanchedItem> drops = new LinkedList<RanchedItem>();
		EntityChicken chicken = ((EntityChicken)entity);
		if (chicken.timeUntilNextEgg < 300)
		{
			chicken.playSound("mob.chicken.plop", 1.0F, (chicken.rand.nextFloat() - chicken.rand.nextFloat()) * 0.2F + 1.0F);
			chicken.attackEntityFrom(DamageSource.generic, 0);
			chicken.setRevengeTarget(chicken); // panic
			chicken.timeUntilNextEgg = chicken.rand.nextInt(6000) + 6200;
			if (rand.nextInt(4) != 0)
			{
				drops.add(new RanchedItem(Item.egg));
			}
			else
			{
				int k = chicken.rand.nextInt(4) + 1;
				drops.add(new RanchedItem(Item.feather, k));
			}
		}
		return drops;
	}
}
