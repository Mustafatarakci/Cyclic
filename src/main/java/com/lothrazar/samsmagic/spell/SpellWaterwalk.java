package com.lothrazar.samsmagic.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import com.lothrazar.samsmagic.ItemRegistry;
import com.lothrazar.samsmagic.ModMain;
import com.lothrazar.samsmagic.PotionRegistry;
import com.lothrazar.samsmagic.util.UtilSound;

public class SpellWaterwalk extends BaseSpellExp implements ISpell
{ 
	private static int seconds = 20 * 10; 
	
	@Override
	public String getSpellName()
	{
		return "waterwalk";
	}
	@Override
	public int getSpellID() 
	{
		return 8;
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{
		ModMain.addOrMergePotionEffect(player,new PotionEffect(PotionRegistry.waterwalk.id,seconds,0));

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
		return ModMain.cfg.waterwalk;
	}

	@Override
	public ItemStack getIconDisplay()
	{
		return new ItemStack(ItemRegistry.spell_waterwalk_dummy);
	}
}
