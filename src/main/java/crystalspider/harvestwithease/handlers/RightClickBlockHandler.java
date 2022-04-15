package crystalspider.harvestwithease.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import javax.annotation.Nullable;

import crystalspider.harvestwithease.config.HarvestWithEaseConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * {@link RightClickBlock} event handler.
 * Handles the {@link RightClickBlock} event with {@link EventPriority#HIGH high priority} to right-click harvest when possible.
 * See {@link #onRightClickBlock(RightClickBlock)} for further details.
 */
public class RightClickBlockHandler {
  /**
   * List of additional in-game IDs for crops that need to be supported but do not extend {@link CropBlock}.
   */
	private final ArrayList<String> crops = new ArrayList<String>(List.of(getKey(Blocks.NETHER_WART), getKey(Blocks.COCOA)));
  /**
   * Whether holding a hoe (either hands) is required.
   */
  private final Boolean requireHoe;
  /**
   * Amount of damage to deal on a hoe when it is used to right-click harvest.
   * Effective only if greater than 0 and {@link #requireHoe} is true.
   */
	private final Integer damageOnHarvest;
  /**
   * Amount of experience to grant on harvest.
   * Effective only if greater than 0.
   */
  private final Integer grantedExp;

  /**
   * 
   * @param configCropsList
   * @param requireHoe
   * @param damageOnHarvest
   */
	public RightClickBlockHandler() {
		crops.addAll(HarvestWithEaseConfig.getCrops());
		this.requireHoe = HarvestWithEaseConfig.getRequireHoe();
    this.damageOnHarvest = HarvestWithEaseConfig.getDamageOnHarvest();
    this.grantedExp = HarvestWithEaseConfig.getGrantedExp();
	}

  /**
   * Listens and handles the event {@link RightClickBlock} with {@link EventPriority#HIGH high priority}.
   * Will cancel further event processing only if the {@link Player player}
   * is not in spectator mode,
   * is not crouching,
   * is holding the correct item (depends on {@link #requireHoe})
   * and the interaction involves a fully grown {@link #isCrop crop}.
   * 
   * @param event
   */
	@SubscribeEvent(priority = EventPriority.HIGH)
  public void onRightClickBlock(RightClickBlock event) {
    Level world = event.getWorld();
    Player player = event.getPlayer();
    if (!player.isSpectator()) {
      BlockPos blockPos = event.getPos();
      BlockState blockState = world.getBlockState(blockPos);
      InteractionHand interactionHand = getInteractionHand(player);
      if (isCrop(blockState.getBlock()) && interactionHand != null) {
        try {
          IntegerProperty age = getAge(blockState);
          if (blockState.getOptionalValue(age).orElse(0) >= Collections.max(age.getPossibleValues())) {
            cancel(event);
            if (!world.isClientSide()) {
              grantExp(player);
              damageHoe(player, interactionHand);
              dropResources(world.getServer().getLevel(world.dimension()), blockState, event.getFace(), blockPos, player, interactionHand);
              world.setBlockAndUpdate(blockPos, blockState.setValue(age, Integer.valueOf(0)));
              player.swing(interactionHand, true);
            }
          }
        } catch (NullPointerException | NoSuchElementException | ClassCastException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private void grantExp(Player player) {
    if (grantedExp >= 0) {
      player.giveExperiencePoints(grantedExp);
    }
  }

  /**
   * If needed and possible, damages the hoe of the given {@link #damageOnHarvest damage}.
   * 
   * @param player - {@link Player player} holding the hoe. 
   * @param interactionHand - {@link InteractionHand hand} holding the hoe.
   */
  private void damageHoe(Player player, InteractionHand interactionHand) {
    if (requireHoe && damageOnHarvest > 0) {
      ItemStack hoe = player.getItemInHand(interactionHand);
      if (hoe.isDamageableItem()) {
        hoe.setDamageValue(hoe.getDamageValue() + (damageOnHarvest > hoe.getMaxDamage() ? hoe.getMaxDamage() : damageOnHarvest));
      }
    }
  }

  /**
   * Drop the resources resulting from harvesting a crop in the given servelLevl and blockState, making them pop from the given face and using the item held in the given player hand.
   * Also removes 1 seed from the drops, if any seed is found in the {@link #getDrops drops list}.
   * A seed is here defined as the item needed to plant the crop.
   * 
   * @param serverLevel - {@link ServerLevel server level} of the {@link Level world} the drops should come from.
   * @param blockState - {@link BlockState state} of the crop being harvested.
   * @param face - {@link Direction face} clicked of the crop.
   * @param blockPos crop {@link BlockPos position}.
   * @param player - {@link Player player} harvesting the crop.
   * @param interactionHand - {@link InteractionHand hand} used to harvest the crop.
   */
  @SuppressWarnings("deprecation")
	private void dropResources(ServerLevel serverLevel, BlockState blockState, Direction face, BlockPos blockPos, Player player, InteractionHand interactionHand) {
    List<ItemStack> drops = getDrops(serverLevel, blockState, blockPos, player, interactionHand);
    boolean seedRemoved = false;
    for (ItemStack stack : drops) {
      if (!seedRemoved && stack.sameItem(blockState.getBlock().getCloneItemStack(serverLevel, blockPos, blockState))) {
        stack.shrink(1);
        seedRemoved = true;
      }
      Block.popResourceFromFace(serverLevel, blockPos, face, stack);
    }
  }

  /**
   * Returns the list of drops calculated from the parameters.
   * 
   * @param serverLevel - {@link ServerLevel server level} of the {@link Level world} the drops should come from.
   * @param blockState - {@link BlockState state} of the block breaking.
   * @param blockPos - {@link BlockPos position} of the block breaking.
   * @param player - {@link Player player} breaking the block.
   * @param interactionHand - {@link InteractionHand hand} the player is using to break the block.
   * @return the list of drops.
   */
  private List<ItemStack> getDrops(ServerLevel serverLevel, BlockState blockState, BlockPos blockPos, Player player, InteractionHand interactionHand) {
    return blockState.getDrops(
      new LootContext.Builder(serverLevel)
        .withParameter(LootContextParams.ORIGIN, new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ()))
        .withParameter(LootContextParams.BLOCK_STATE, blockState)
        .withParameter(LootContextParams.THIS_ENTITY, player)
        .withParameter(LootContextParams.TOOL, player.getItemInHand(interactionHand))
    );
  }

  /**
   * Cancel the event to avoid further processing.
   * 
   * @param event
   */
  private void cancel(RightClickBlock event) {
    event.setCancellationResult(InteractionResult.CONSUME);
		event.setCanceled(true);
  }

  /**
   * Returns the age integer property from the given blockState.
   * 
   * @param blockState - {@link BlockState state} to take the age property from.
   * @return the age property from the given blockState.
   * @throws NullPointerException - if the age property was null.
   * @throws NoSuchElementException - if no value for the age property is present.
   * @throws ClassCastException - if the age property is not an {@link IntegerProperty}.
   */
  private IntegerProperty getAge(BlockState blockState) throws NullPointerException, NoSuchElementException, ClassCastException {
    return (IntegerProperty) blockState.getProperties().stream().filter(property -> property.getName().equals("age")).findFirst().orElseThrow();
  }

  /**
   * Returns the most suitable interaction hand from the player.
   * Returns null if there was no suitable interaction hand.
   * 
   * @param player
   * @return most suitable interaction hand.
   */
  @Nullable
  private InteractionHand getInteractionHand(Player player) {
    if (!player.isCrouching()) {
      if (isHoe(player.getMainHandItem())) {
        return InteractionHand.MAIN_HAND;
      }
      if (isHoe(player.getOffhandItem())) {
        return InteractionHand.OFF_HAND;
      }
      if (!requireHoe) {
        return InteractionHand.MAIN_HAND;
      }
    }
    return null;
  }

  /**
   * Checks whether or not the given itemStack can perform all the {@link ToolActions#DEFAULT_HOE_ACTIONS default hoe actions}.
   * 
   * @param handItem
   * @return whether the given itemStack is a hoe tool.
   */
  private boolean isHoe(ItemStack handItem) {
    return ToolActions.DEFAULT_HOE_ACTIONS.stream().allMatch(toolAction -> handItem.canPerformAction(toolAction));
  }

  /**
   * Checks whether or not the block passed as parameter is a crop that can be harvested using this mod.
   * 
   * @param block
   * @return whether the given block it's a valid crop.
   */
  private boolean isCrop(Block block) {
		return block instanceof CropBlock || crops.contains(getKey(block));
	}

  /**
   * Returns the in-game ID of the block passed as parameter.
   * 
   * @param block
   * @return in-game ID of the given block.
   */
  private String getKey(Block block) {
    return ForgeRegistries.BLOCKS.getKey(block).toString();
  }
}
