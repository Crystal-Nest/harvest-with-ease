package crystalspider.harvestwithease.api;

import java.util.Collections;
import java.util.NoSuchElementException;

import crystalspider.harvestwithease.HarvestWithEaseLoader;
import crystalspider.harvestwithease.config.HarvestWithEaseConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.neoforge.registries.ForgeRegistries;

/**
 * Utility class that serves as an API for mods interfacing with Harvest With Ease mod.
 */
public final class HarvestWithEaseAPI {
  private HarvestWithEaseAPI() {}

  /**
   * Checks whether the given block is a crop that can be harvested using this mod.
   * 
   * @param block
   * @return whether the given block is a valid crop.
   */
  public static boolean isCrop(Block block) {
    // Hardcoded block id, might consider importing the mod and get the id from its list of registered blocks.
    return !getKey(block).equals("farmersdelight:tomatoes") && isBreakableCrop(block);
  }

  /**
   * Checks whether the given block is a crop that can be broken and, optionally, drop xp.
   * 
   * @param block
   * @return whether the given block is a valid breakable crop.
   */
  public static boolean isBreakableCrop(Block block) {
    return block instanceof CropBlock || block instanceof NetherWartBlock || block instanceof CocoaBlock || HarvestWithEaseConfig.getCrops().contains(getKey(block));
  }

  /**
   * Returns the age integer property from the given blockState.
   * 
   * @param blockState - {@link BlockState state} to take the age property from.
   * @return the age property from the given blockState.
   * @throws NullPointerException if the age property was null.
   * @throws NoSuchElementException if no value for the age property is present.
   * @throws ClassCastException if the age property is not an {@link IntegerProperty}.
   */
  public static IntegerProperty getAge(BlockState blockState) throws NullPointerException, NoSuchElementException, ClassCastException {
    return (IntegerProperty) blockState.getProperties().stream().filter(property -> property.getName().equals("age")).findFirst().orElseThrow();
  }

  /**
   * Checks whether the given blockstate is a mature crop.
   * 
   * @param blockState - {@link BlockState state} to take the age property from.
   * @param age - {@link IntegerProperty integer property} for the crop age.
   * @return whether the given blockstate is a mature crop.
   */
  public static boolean isMature(BlockState blockState, IntegerProperty age) {
    return blockState.getOptionalValue(age).orElse(0) >= Collections.max(age.getPossibleValues());
  }

  /**
   * Checks whether the given blockstate is a mature crop.
   * 
   * @param blockState - {@link BlockState state} to take the age property from.
   * @return whether the given blockstate is a mature crop.
   * @throws NullPointerException if the age property was null.
   * @throws NoSuchElementException if no value for the age property is present.
   * @throws ClassCastException if the age property is not an {@link IntegerProperty}.
   */
  public static boolean isMature(BlockState blockState) throws NullPointerException, NoSuchElementException, ClassCastException {
    return HarvestWithEaseAPI.isMature(blockState, HarvestWithEaseAPI.getAge(blockState));
  }

  /**
   * Checks whether the given crop is a multi-block crop (a crop made of multiple vertically connected blocks).
   * 
   * @param level - {@link Level world} in which the crop is placed.
   * @param blockState - {@link BlockState} of the crop.
   * @param blockPos - {@link BlockPos} of the crop.
   * @return whether the given crop is a multi-block crop.
   */
  public static boolean isTallCrop(Level level, BlockState blockState, BlockPos blockPos) {
    return blockState.is(BlockTags.CROPS) && level.getBlockState(blockPos.below()).is(blockState.getBlock()) || level.getBlockState(blockPos.above()).is(blockState.getBlock());
  }

  /**
   * Returns the in-game ID of the block passed as parameter.
   * 
   * @param block
   * @return in-game ID of the given block.
   */
  private static String getKey(Block block) {
    ResourceLocation blockLocation = ForgeRegistries.BLOCKS.getKey(block);
    if (blockLocation != null) {
      return blockLocation.toString();
    }
    HarvestWithEaseLoader.LOGGER.debug("Couldn't get key for block [" + block + "].");
    return "";
  }
}
