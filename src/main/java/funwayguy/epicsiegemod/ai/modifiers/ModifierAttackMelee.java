package funwayguy.epicsiegemod.ai.modifiers;

import java.lang.reflect.Field;
import org.apache.logging.log4j.Level;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.IAnimals;
import funwayguy.epicsiegemod.ai.ESM_EntityAIAttackMelee;
import funwayguy.epicsiegemod.api.ITaskModifier;
import funwayguy.epicsiegemod.core.ESM;

public class ModifierAttackMelee implements ITaskModifier
{
	public static Field f_speed = null;
	
	@Override
	public boolean isValid(EntityLiving entityLiving, EntityAIBase task)
	{
		return task.getClass() == EntityAIAttackMelee.class && !(entityLiving instanceof IAnimals);
	}
	
	@Override
	public EntityAIBase getReplacement(EntityLiving host, EntityAIBase entry)
	{
		double speed = 1D;
		
		try
		{
			speed = f_speed.getDouble(entry);
		} catch(Exception e){}
		
		return new ESM_EntityAIAttackMelee(host, speed, true);
	}
	
	static
	{
		try
		{
			f_speed = EntityAIAttackMelee.class.getDeclaredField("field_75440_e");
			f_speed.setAccessible(true);
		} catch(Exception e1)
		{
			try
			{
				f_speed = EntityAIAttackMelee.class.getDeclaredField("speedTowardsTarget");
				f_speed.setAccessible(true);
			} catch(Exception e2)
			{
				ESM.logger.log(Level.INFO, "Unable to access melee attack speed", e2);
			}
		}
	}
}
