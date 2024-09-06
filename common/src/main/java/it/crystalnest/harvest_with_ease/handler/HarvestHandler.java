package it.crystalnest.harvest_with_ease.handler;

import it.crystalnest.cobweb.api.block.BlockUtils;
import it.crystalnest.cobweb.api.item.TierUtils;
import it.crystalnest.harvest_with_ease.Constants;
import it.crystalnest.harvest_with_ease.api.HarvestUtils;
import it.crystalnest.harvest_with_ease.api.event.HarvestEvent;
import it.crystalnest.harvest_with_ease.config.ModConfig;
import it.crystalnest.harvest_with_ease.platform.Services;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

import java.util.NoSuchElementException;

/**
 * Handler for harvest related events.
 */
public abstract class HarvestHandler {
  protected HarvestHandler() {}

  /**
   * Handles block breaking harvest (left-click harvest).
   *
   * @param level level.
   * @param crop crop.
   * @param pos crop position.
   */
  protected static void handle(LevelAccessor level, BlockState crop, BlockPos pos) {
    try {
      if (!level.isClientSide() && ModConfig.getGrantedExp() > 0 && HarvestUtils.isCrop(crop.getBlock()) && !HarvestUtils.isBlacklisted(crop) && HarvestUtils.isMature(crop) && ((ServerLevel) level).getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
        ExperienceOrb.award((ServerLevel) level, Vec3.atCenterOf(pos), ModConfig.getGrantedExp());
      }
    } catch (NullPointerException | NoSuchElementException | ClassCastException e) {
      logError(e, pos);
    }
  }

  /**
   * Handles right-click harvest.
   *
   * @param level level.
   * @param crop crop.
   * @param face clicked face.
   * @param pos crop position.
   * @param hitResult {@link BlockHitResult}.
   * @param player player.
   * @param hand player's hand.
   * @return whether to consume the action.
   */
  protected static boolean handle(Level level, BlockState crop, Direction face, BlockPos pos, BlockHitResult hitResult, Player player, InteractionHand hand) {
    boolean consume = false;
    if (hand != null && hand == getValidHand(player) && canHarvest(level, crop, pos, face, hitResult, player, hand)) {
      try {
        IntegerProperty age = HarvestUtils.getAge(crop);
        if (HarvestUtils.isMature(crop, age)) {
          consume = true;
          if (!level.isClientSide) {
            harvest((ServerLevel) level, age, crop, pos, face, hitResult, (ServerPlayer) player, hand);
            if (player.getItemInHand(hand).getItem() instanceof TieredItem tool && Services.HARVEST.isHoe(tool.getDefaultInstance()) && HarvestUtils.isTierForMultiHarvest(tool)) {
              int fromCenterToEdge = ((TierUtils.getLevel(tool.getTier()) - TierUtils.getLevel(ModConfig.getMultiHarvestStartingTier())) * ModConfig.getAreaIncrementStep().step + ModConfig.getAreaStartingSize().size - 1) / 2;
              BlockPos.betweenClosedStream(new AABB(pos, pos).inflate(fromCenterToEdge, 0, fromCenterToEdge)).filter(cropPos -> !pos.equals(cropPos)).forEach(cropPos -> {
                BlockState cropState = level.getBlockState(cropPos);
                if (canHarvest(level, cropState, cropPos, face, null, player, hand)) {
                  IntegerProperty cropAge = HarvestUtils.getAge(cropState);
                  if (HarvestUtils.isMature(cropState)) {
                    harvest((ServerLevel) level, cropAge, cropState, cropPos, face, null, (ServerPlayer) player, hand);
                  }
                }
              });
            }
          }
        }
      } catch (NullPointerException | NoSuchElementException | ClassCastException e) {
        logError(e, pos);
      }
    }
    return consume;
  }

  /**
   * Harvests the crop, resetting its age.
   *
   * @param level level.
   * @param age crop age.
   * @param crop crop.
   * @param pos crop position.
   * @param face clicked face.
   * @param hitResult {@link BlockHitResult}.
   * @param player player.
   * @param hand player's hand.
   */
  protected static void harvest(ServerLevel level, IntegerProperty age, BlockState crop, BlockPos pos, Direction face, BlockHitResult hitResult, ServerPlayer player, InteractionHand hand) {
    Services.EVENT.fireBeforeHarvestEvent(level, crop, pos, face, hitResult, player, hand);
    BlockPos basePos = getBasePos(level, crop.getBlock(), pos);
    grantExp(level, basePos);
    damageHoe(player, hand);
    updateCrop(level, age, crop.getBlock(), basePos, player, dropResources(level, level.getBlockState(basePos), basePos, face, hitResult, player, hand));
    playSound(level, player, crop, pos);
    Services.EVENT.fireAfterHarvestEvent(level, crop, pos, face, hitResult, player, hand);
  }

  /**
   * Updates the crop resetting its age.
   *
   * @param level level.
   * @param age crop age.
   * @param crop crop.
   * @param basePos position of the crop base.
   * @param player player.
   * @param dropsFlags a Pair, with left equal to whether the crop seed was in the drops, and right equal to whether custom drops were added.
   */
  protected static void updateCrop(ServerLevel level, IntegerProperty age, Block crop, BlockPos basePos, ServerPlayer player, Pair<Boolean, Boolean> dropsFlags) {
    BlockState cropState = level.getBlockState(basePos);
    int i = player.getInventory().findSlotMatchingItem(crop.getCloneItemStack(level, basePos, cropState));
    if (dropsFlags.getLeft()) {
      level.setBlockAndUpdate(basePos, level.getBlockState(basePos).setValue(age, 0));
    } else if (ModConfig.getUseSeedsFromInventory() && i >= 0) {
      level.setBlockAndUpdate(basePos, level.getBlockState(basePos).setValue(age, 0));
      if (!player.isCreative()) {
        player.getInventory().getItem(i).shrink(1);
      }
    } else {
      level.setBlockAndUpdate(basePos, Blocks.AIR.defaultBlockState());
    }
    if (level.getBlockState(basePos).is(BlockTags.CROPS) && level.getBlockState(basePos.above()).is(crop) && isNotTallButSeparate(crop)) {
      level.destroyBlock(basePos.above(), !dropsFlags.getRight(), player);
    }
  }

  /**
   * Retrieves the crop base position.
   *
   * @param level level.
   * @param crop crop.
   * @param pos crop position.
   * @return crop base position.
   */
  protected static BlockPos getBasePos(ServerLevel level, Block crop, BlockPos pos) {
    BlockPos basePos = pos;
    while (level.getBlockState(pos).is(BlockTags.CROPS) && isNotTallButSeparate(crop) && level.getBlockState(basePos.below()).is(crop)) {
      basePos = basePos.below();
    }
    return basePos;
  }

  /**
   * Optionally gives XP points to the player for harvesting.
   *
   * @param level level.
   * @param pos crop position.
   */
  protected static void grantExp(ServerLevel level, BlockPos pos) {
    if (ModConfig.getGrantedExp() > 0 && level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
      ExperienceOrb.award(level, Vec3.atCenterOf(pos), ModConfig.getGrantedExp());
    }
  }

  /**
   * Optionally damages the hoe.
   *
   * @param player player.
   * @param hand player's hand.
   */
  protected static void damageHoe(ServerPlayer player, InteractionHand hand) {
    if (ModConfig.getRequireHoe() && ModConfig.getDamageOnHarvest() > 0 && !player.isCreative()) {
      player.getItemInHand(hand).hurtAndBreak(ModConfig.getDamageOnHarvest(), player, playerEntity -> playerEntity.broadcastBreakEvent(hand));
    }
  }

  /**
   * Drops the resources for harvesting.
   *
   * @param level level.
   * @param crop crop.
   * @param pos crop position.
   * @param face clicked face.
   * @param hitResult {@link BlockHitResult}.
   * @param player player.
   * @param hand player's hand.
   * @return a Pair, with left equal to whether the crop seed was in the drops, and right equal to whether custom drops were added.
   */
  protected static Pair<Boolean, Boolean> dropResources(ServerLevel level, BlockState crop, BlockPos pos, Direction face, @Nullable BlockHitResult hitResult, ServerPlayer player, InteractionHand hand) {
    if (level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
      HarvestEvent.HarvestDropsEvent event = Services.EVENT.fireHarvestDropsEvent(level, crop, pos, face, hitResult, player, hand);
      boolean seedIncluded = player.isCreative();
      for (ItemStack stack : event.getDrops()) {
        if (stack.is(crop.getBlock().getCloneItemStack(level, pos, crop).getItem())) {
          seedIncluded = true;
        }
        if (crop.getCollisionShape(level, pos) != Shapes.empty()) {
          Block.popResourceFromFace(level, pos, face, stack);
        } else {
          Block.popResource(level, pos, stack);
        }
      }
      return Pair.of(seedIncluded, event.didDropsChange());
    }
    return Pair.of(false, false);
  }

  /**
   * Plays a sound for harvesting.
   *
   * @param level level.
   * @param player player.
   * @param crop crop.
   * @param pos crop position.
   */
  protected static void playSound(ServerLevel level, ServerPlayer player, BlockState crop, BlockPos pos) {
    SoundType soundType = Services.HARVEST.getSoundType(level, player, crop, pos);
    level.playSound(null, pos, soundType.getBreakSound(), SoundSource.BLOCKS, soundType.getVolume(), soundType.getPitch());
  }

  /**
   * Retrieves the most suitable player's hand for harvesting a crop.<br>
   * Returns {@code null} if no hand was valid.
   *
   * @param player player.
   * @return the most suitable hand for harvesting or {@code null} if none.
   */
  @Nullable
  protected static InteractionHand getValidHand(Player player) {
    if (!player.isCrouching()) {
      if (Services.HARVEST.isHoe(player.getMainHandItem())) {
        return InteractionHand.MAIN_HAND;
      }
      if (Services.HARVEST.isHoe(player.getOffhandItem())) {
        return InteractionHand.OFF_HAND;
      }
      if (!ModConfig.getRequireHoe()) {
        return InteractionHand.MAIN_HAND;
      }
    }
    return null;
  }

  /**
   * Checks whether the player can harvest the crop.
   *
   * @param level level.
   * @param crop crop.
   * @param pos crop position.
   * @param face clicked face.
   * @param hitResult {@link BlockHitResult}.
   * @param player player.
   * @param hand player's hand.
   * @return whether the player can harvest the crop.
   */
  protected static boolean canHarvest(Level level, BlockState crop, BlockPos pos, Direction face, @Nullable BlockHitResult hitResult, Player player, InteractionHand hand) {
    return HarvestUtils.isCrop(crop.getBlock()) && player.hasCorrectToolForDrops(crop) && !HarvestUtils.isBlacklisted(crop) && Services.EVENT.fireHarvestCheckEvent(level, crop, pos, face, hitResult, player, hand);
  }

  /**
   * Checks whether the crop is tall, but each block should be considered as a single one.
   *
   * @param crop crop.
   * @return whether the crop is tall, but should be considered as a single one.
   */
  protected static boolean isNotTallButSeparate(Block crop) {
    return !"farmersdelight:tomatoes".equalsIgnoreCase(BlockUtils.getStringKey(crop));
  }

  /**
   * Logs an error.
   *
   * @param e exception.
   * @param pos crop position.
   */
  private static void logError(Exception e, BlockPos pos) {
    Constants.LOGGER.debug("Exception generated by block at [{}]", pos.toShortString());
    Constants.LOGGER.debug("This is a non blocking error, but can result in incorrect behavior for mod {}", Constants.MOD_ID);
    Constants.LOGGER.debug("Most likely, it wasn't possible to retrieve a crop age property, either for an invalid item in the cropIds configuration option or for a mod incompatibility; see stack trace for more details", e);
  }
}
