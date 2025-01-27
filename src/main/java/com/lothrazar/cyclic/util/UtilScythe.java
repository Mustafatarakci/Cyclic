/*******************************************************************************
 * The MIT License (MIT)
 *
 * Copyright (C) 2014-2018 Sam Bassett (aka Lothrazar)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package com.lothrazar.cyclic.util;

import com.lothrazar.cyclic.data.DataTags;
import com.lothrazar.cyclic.item.scythe.ScytheType;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public class UtilScythe {

  // hardcoded as MAIN hand
  public static boolean harvestSingle(Level world, Player player, BlockPos posCurrent, ScytheType type) {
    boolean doBreak = false;
    BlockState blockState = world.getBlockState(posCurrent);
    if (blockState.hasProperty(DoublePlantBlock.HALF) && blockState.getValue(DoublePlantBlock.HALF).equals(DoubleBlockHalf.LOWER)) {
      //the upper half has the drops, so only do that. avoid glitches and item loss
      // ie: rose, lilac, sunflower
      return false;
    }
    switch (type) {
      case LEAVES:
        doBreak = blockState.is(BlockTags.LEAVES);
        break;
      case BRUSH:
        doBreak = blockState.is(DataTags.PLANTS);
        break;
      case FORAGE:
        doBreak = blockState.is(BlockTags.FLOWERS)
            || blockState.is(BlockTags.CORALS) || blockState.is(BlockTags.WALL_CORALS)
            || blockState.is(DataTags.MUSHROOMS)
            || blockState.is(DataTags.VINES)
            || blockState.is(DataTags.CACTUS)
            || blockState.is(DataTags.CROP_BLOCKS);
        break;
    }
    if (doBreak) {
      if (blockState.is(DataTags.CROP_BLOCKS)) {
        world.destroyBlock(posCurrent, false, player);
        UtilItemStack.drop(world, posCurrent, blockState.getBlock()); //like shears
      }
      else {
        //harvest block with player context: better mod compatibility
        if (type == ScytheType.BRUSH && EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, player) > 0) {
          //only brush needed silk override, tree leaves worked regardless
          UtilItemStack.drop(world, posCurrent, blockState.getBlock());
        }
        else {
          blockState.getBlock().playerDestroy(world, player, posCurrent, blockState, world.getBlockEntity(posCurrent), player.getMainHandItem());
        }
        //sometimes this doesnt work and/or doesnt sync ot client, so force it
        world.destroyBlock(posCurrent, false, player);
        //break with false to disable dropsfor the above versions, dont want to dupe tallflowers
      }
      return true;
    }
    return false;
  }
}
