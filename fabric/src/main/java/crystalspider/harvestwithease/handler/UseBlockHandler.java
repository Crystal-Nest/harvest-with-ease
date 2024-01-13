package crystalspider.harvestwithease.handler;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import crystalspider.harvestwithease.ModLoader;
import crystalspider.harvestwithease.api.HarvestWithEaseAPI;
import crystalspider.harvestwithease.api.event.HarvestWithEaseEvents;
import crystalspider.harvestwithease.api.event.HarvestWithEaseEvents.HarvestDrops;
import crystalspider.harvestwithease.config.ModConfig;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

/**
 * {@link UseBlockCallback} event handler.
 * Handles the {@link UseBlockCallback} event to right-click harvest when possible.
 * See {@link #handle(PlayerEntity, World, Hand, BlockHitResult)} for more details.
 */
public final class UseBlockHandler {
  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ModLoader.MOD_ID);

  /**
   * Handles the event {@link UseBlockCallback}.
   * Will cancel further event processing only if the {@link PlayerEntity player}
   * is not in spectator mode,
   * is not crouching,
   * is holding the correct item (depends on {@link ModConfig#getRequireHoe() requireHoe})
   * and the interaction involves a fully grown {@link #isCrop crop}.
   * 
   * @param player - {@link PlayerEntity player} executing the action.
   * @param world - {@link World world} where the event is happening.
   * @param hand - {@link Hand hand} player's hand.
   * @param result - {@link BlockHitResult} result of hitting the block.
   * @return - {@link ActionResult} result of the action.
   */
  public static ActionResult handle(PlayerEntity player, World world, Hand hand, BlockHitResult result) {
    ActionResult actionResult = ActionResult.PASS;
    if (!player.isSpectator()) {
      BlockPos blockPos = result.getBlockPos();
      BlockState blockState = world.getBlockState(blockPos);
      if (hand == getInteractionHand(player) && canHarvest(world, blockState, blockPos, player, hand, true)) {
        try {
          IntProperty cropAge = HarvestWithEaseAPI.getAge(blockState);
          if (HarvestWithEaseAPI.isMature(blockState, cropAge)) {
            actionResult = ActionResult.SUCCESS;
            if (!world.isClient()) {
              harvest((ServerWorld) world, cropAge, blockState, blockPos, result.getSide(), result, (ServerPlayerEntity) player, hand);
              if (player.getStackInHand(hand).getItem() instanceof ToolItem tool && isHoe(tool.getDefaultStack()) && HarvestWithEaseAPI.isTierForMultiHarvest(tool)) {
                int fromCenterToEdge = ((HarvestWithEaseAPI.getTierLevel(tool.getMaterial()) - HarvestWithEaseAPI.getTierLevel(ModConfig.getMultiHarvestStartingTier())) * ModConfig.getAreaIncrementStep().step + ModConfig.getAreaStartingSize().size - 1) / 2;
                BlockPos.stream(expandHorizontally(new BlockBox(blockPos), fromCenterToEdge)).filter(pos -> !pos.equals(blockPos)).forEach(pos -> {
                  BlockState state = world.getBlockState(pos);
                  if (canHarvest(world, state, pos, player, hand, false)) {
                    IntProperty age = HarvestWithEaseAPI.getAge(state);
                    if (HarvestWithEaseAPI.isMature(state, age)) {
                      harvest((ServerWorld) world, age, state, pos, result.getSide(), null, (ServerPlayerEntity) player, hand);
                    }
                  }
                });
              }
            }
          }
        } catch (NullPointerException | NoSuchElementException | ClassCastException e) {
          LOGGER.debug("Exception generated by block at [" + blockPos.toShortString() + "]");
          LOGGER.debug("This is a non blocking error, but can result in incorrect behavior for mod " + ModLoader.MOD_ID);
          LOGGER.debug("Most probably the cause of this issue was that a non-crop ID was added in the configuration and its age property could not be retrieved, see stack trace for more details", e);
        }
      }
    }
    return actionResult;
  }

  /**
   * Harvests the crop, handles all related actions (exp granting, hoe damaging, dropping resources, etc.) and dispatches all related events.
   * 
   * @param world - {@link ServerWorld world}.
   * @param age - {@link IntProperty age} of the crop.
   * @param blockState - {@link BlockState} of the crop.
   * @param blockPos - {@link BlockPos} of the crop.
   * @param face - clicked {@link Direction face} of the crop block.
   * @param hitResult - {@link BlockHitResult} of the {@link RightClickBlock} event.
   * @param player - {@link ServerPlayerEntity player} harvesting the crop.
   * @param hand - {@link InteractionHand hand} used to harvest.
   */
  private static void harvest(ServerWorld world, IntProperty age, BlockState blockState, BlockPos blockPos, Direction face, BlockHitResult hitResult, ServerPlayerEntity player, Hand hand) {
    HarvestWithEaseEvents.BEFORE_HARVEST.invoker().beforeHarvest(world, blockState, blockPos, face, hitResult, player, hand, hitResult != null);
    BlockPos basePos = getBasePos(world, blockState.getBlock(), blockPos);
    grantExp(world, basePos);
    damageHoe(player, hand);
    updateCrop(world, age, blockState.getBlock(), basePos, player, dropResources(world, world.getBlockState(basePos), basePos, face, hitResult, player, hand));
    playSound(world, blockState, blockPos);
    HarvestWithEaseEvents.AFTER_HARVEST.invoker().afterHarvest(world, blockState, blockPos, face, hitResult, player, hand, hitResult != null);
  }

  /**
   * Updates the crop in the world, reverting it to age 0 (simulate replanting) and, if it's a multi-block crop, breaks the crop blocks above.
   * 
   * @param world - {@link ServerWorld world}.
   * @param age - {@link IntProperty age} of the crop.
   * @param block - {@link Block} of the clicked crop.
   * @param basePos - {@link BlockPos} of the clicked crop base.
   * @param player - {@link ServerPlayerEntity player} harvesting the crop.
   * @param customDrops - whether {@link HarvestDrops} listeners have changed the drops to drop.
   */
  private static void updateCrop(ServerWorld world, IntProperty age, Block block, BlockPos basePos, ServerPlayerEntity player, boolean customDrops) {
    world.setBlockState(basePos, block == Blocks.PITCHER_CROP ? Blocks.AIR.getDefaultState() : world.getBlockState(basePos).with(age, 0));
    if (world.getBlockState(basePos).isIn(BlockTags.CROPS) && world.getBlockState(basePos.up()).isOf(block) && !isTallButSeparate(block)) {
      world.breakBlock(basePos.up(), !customDrops, player);
    }
  }

  /**
   * Returns the base pos of the clicked crop.
   * 
   * @param world - {@link ServerWorld world}.
   * @param block - {@link Block} of the clicked crop.
   * @param blockPos - {@link BlockPos} of the crop block clicked.
   * @return the base pos of the clicked crop.
   */
  private static BlockPos getBasePos(ServerWorld world, Block block, BlockPos blockPos) {
    BlockPos basePos;
    for (basePos = blockPos; world.getBlockState(blockPos).isIn(BlockTags.CROPS) && !isTallButSeparate(block) && world.getBlockState(basePos.down()).isOf(block); basePos = basePos.down());
    return basePos;
  }

  /**
   * Grants the given player the configured amount of experience, if any.
   *
   * @param world
   * @param pos
   */
  private static void grantExp(ServerWorld world, BlockPos pos) {
    if (ModConfig.getGrantedExp() > 0 && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS)) {
      ExperienceOrbEntity.spawn(world, Vec3d.ofCenter(pos), ModConfig.getGrantedExp());
    }
  }

  /**
   * If needed and possible, damages the hoe of the given {@link ModConfig#getDamageOnHarvest() damage}.
   * 
   * @param player - {@link ServerPlayerEntity player} holding the hoe. 
   * @param hand - {@link Hand hand} holding the hoe.
   */
  private static void damageHoe(ServerPlayerEntity player, Hand hand) {
    if (ModConfig.getRequireHoe() && ModConfig.getDamageOnHarvest() > 0 && !player.isCreative()) {
      player.getStackInHand(hand).damage(ModConfig.getDamageOnHarvest(), player, playerEntity -> playerEntity.sendToolBreakStatus(hand));
    }
  }

  /**
   * Drop the resources resulting from harvesting a crop in the given {@link ServerWorld world} and {@link BlockState blockState}, making them pop from the given face and using the item held in the given player hand.
   * Takes care of dispatching the {@link HarvestWithEaseEvents#HARVEST_DROPS} to retrieve the drops resulting from the harvest.
   * 
   * @param world - {@link ServerWorld server world} the drops should come from.
   * @param blockState - {@link BlockState state} of the crop being harvested.
   * @param blockPos - crop {@link BlockPos position}.
   * @param face - {@link Direction face} clicked of the crop.
   * @param hitResult - {@link BlockHitResult} of the {@link RightClickBlock} event.
   * @param player - {@link ServerPlayer player} harvesting the crop.
   * @param hand - {@link InteractionHand hand} used to harvest the crop.
   * @return whether {@link HarvestDrops} listeners have changed the drops to drop.
   */
  private static boolean dropResources(ServerWorld world, BlockState blockState, BlockPos blockPos, Direction face, BlockHitResult hitResult, ServerPlayerEntity player, Hand hand) {
    HarvestWithEaseEvents.HarvestDropsEvent event = new HarvestWithEaseEvents.HarvestDropsEvent(world, blockState, blockPos, player, hand);
    for (ItemStack stack : HarvestWithEaseEvents.HARVEST_DROPS.invoker().getDrops(world, blockState, blockPos, face, hitResult, player, hand, hitResult != null, event)) {
      if (blockState.getCollisionShape(world, blockPos) != VoxelShapes.empty()) {
        Block.dropStack(world, blockPos, face, stack);
      } else {
        Block.dropStack(world, blockPos, stack);
      }
    }
    return event.haveDropsChanged();
  }

  /**
   * If {@link ModConfig#getPlaySound() playSound} is true, plays the block breaking sound.
   * 
   * @param world - {@link ServerWorld} to play the sound.
   * @param blockState - {@link BlockState state} of the block emitting the sound.
   * @param blockPos - {@link BlockPos position} of the block emitting the sound.
   */
  private static void playSound(ServerWorld world, BlockState blockState, BlockPos blockPos) {
    if (ModConfig.getPlaySound()) {
      BlockSoundGroup soundGroup = blockState.getBlock().getSoundGroup(blockState);
      world.playSound(null, blockPos, soundGroup.getBreakSound(), SoundCategory.BLOCKS, soundGroup.getVolume(), soundGroup.getPitch());
    }
  }

  /**
   * Returns the most suitable interaction hand from the player.
   * Returns null if there was no suitable interaction hand.
   * 
   * @param player
   * @return most suitable interaction hand.
   */
  @Nullable
  private static Hand getInteractionHand(PlayerEntity player) {
    if (!player.isSneaking()) {
      if (isHoe(player.getStackInHand(Hand.MAIN_HAND))) {
        return Hand.MAIN_HAND;
      }
      if (isHoe(player.getStackInHand(Hand.OFF_HAND))) {
        return Hand.OFF_HAND;
      }
      if (!ModConfig.getRequireHoe()) {
        return Hand.MAIN_HAND;
      }
    }
    return null;
  }

  /**
   * Checks whether or not the given itemStack is an Item that extends {@link HoeItem}.
   * 
   * @param handItem
   * @return whether the given itemStack is a hoe tool.
   */
  private static boolean isHoe(ItemStack handItem) {
    return handItem.getItem() instanceof HoeItem;
  }

  /**
   * Checks whether the given {@link PlayerEntity} can right-click harvest the crop.
   * Dispatches the {@link HarvestWithEaseEvents#HARVEST_CHECK} event if the right-clicked block is indeed a crop.
   * 
   * @param world - {@link World} of the interaction.
   * @param blockState - {@link BlockState} of the crop to harvest.
   * @param blockPos - {@link BlockPos} of the crop.
   * @param player - {@link PlayerEntity} trying to harvest.
   * @param hand - {@link Hand} being used to harvest the crop.
   * @param first - whether the current crop is the actual right-clicked crop.
   * @return whether the player can right-click harvest the crop.
   */
  private static boolean canHarvest(World world, BlockState blockState, BlockPos blockPos, PlayerEntity player, Hand hand, boolean first) {
    return HarvestWithEaseAPI.isCrop(blockState.getBlock()) && player.canHarvest(blockState) && HarvestWithEaseEvents.HARVEST_CHECK.invoker().check(world, blockState, blockPos, player, hand, first, new HarvestWithEaseEvents.HarvestCheckEvent());
  }
  
  /**
   * Checks whether the given block is something that might be considered a tall crop, but should actually be treated as a normal crop.
   * <p>
   * Currently the only known crop with this behavior is Farmer's Delight tomatoes. 
   * 
   * @param block
   * @return whether to treat a tall crop as a normal crop.
   */
  private static boolean isTallButSeparate(Block block) {
    Optional<RegistryKey<Block>> key = Registries.BLOCK.getKey(block);
    if (key.isPresent()) {
      return key.get().getValue().toString().equals("farmersdelight:tomatoes");
    }
    return false;
  }

  /**
   * Expand a {@link BlockBox} horizontally.
   * 
   * @param box
   * @param distance
   * @return the expanded {@link BlockBox}.
   */
  private static BlockBox expandHorizontally(BlockBox box, int distance) {
    return new BlockBox(box.getMinX() - distance, box.getMinY(), box.getMinZ() - distance, box.getMaxX() + distance, box.getMaxY(), box.getMaxZ() + distance);
  }
}
