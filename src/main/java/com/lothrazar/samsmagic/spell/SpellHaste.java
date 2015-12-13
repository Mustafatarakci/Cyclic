 package com.lothrazar.samsmagic.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import com.lothrazar.samsmagic.ItemRegistry;
import com.lothrazar.samsmagic.PotionRegistry; 
import com.lothrazar.samsmagic.ModMain; 
import com.lothrazar.samsmagic.util.UtilSound;

public class SpellHaste extends BaseSpellExp implements ISpell
{
	private static int seconds = 20* 10; 
	 
	@Override
	public String getSpellName()
	{
		return "haste";
	}
	@Override
	public int getSpellID() 
	{
		return 4;
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{ 
		ModMain.addOrMergePotionEffect(player,new PotionEffect(Potion.digSpeed.id,seconds,PotionRegistry.II));
		 
	}

	@Override
	public void onCastSuccess(World world, EntityPlayer player, BlockPos pos)
	{
		UtilSound.playSoundAt(player, "random.drink");

		super.onCastSuccess(world, player, pos);
	}
	
	@Override
	public int getExpCost()
	{
		return ModMain.cfg.haste;
	}
	
	@Override
	public ItemStack getIconDisplay()
	{
		return new ItemStack(ItemRegistry.spell_haste_dummy);
	} 
}
