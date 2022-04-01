package crystalspider.harvestwithease.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
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

public class RightClickBlockHandler {
	private final ArrayList<String> crops = new ArrayList<String>(List.of(getKey(Blocks.NETHER_WART), getKey(Blocks.COCOA)));
	private final Boolean requireHoe;

	public RightClickBlockHandler(ArrayList<String> configCropsList, Boolean requireHoe) {
		crops.addAll(configCropsList);
		this.requireHoe = requireHoe;
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
    public void onRightClickBlock(RightClickBlock event) {
    	Level world = event.getWorld();
    	Player player = event.getPlayer();
		BlockState blockState = world.getBlockState(event.getPos());
    	InteractionHand interactionHand = getInteractionHand(player);
    	if (isCrop(blockState.getBlock()) && tryHarvest(interactionHand, blockState, event, world)) {
    		player.swing(interactionHand, true);
    	}
    }

    private boolean tryHarvest(InteractionHand interactionHand, BlockState cropState, RightClickBlock event, Level world) {
    	try {
    		IntegerProperty age = getAge(cropState);
    		if (cropState.getOptionalValue(age).orElse(0) >= Collections.max(age.getPossibleValues())) {
    			cancel(event);
        		if (!world.isClientSide() && interactionHand != null) {
            		harvest(world.getServer().getLevel(world.dimension()), cropState, event, age);
            		return true;
            	}
    		}
		} catch (NullPointerException | NoSuchElementException e) {
			e.printStackTrace();
		}
    	return false;
    }

    private void harvest(ServerLevel serverLevel, BlockState cropState, RightClickBlock event, IntegerProperty age) {
    	BlockPos pos = event.getPos();
		dropResources(serverLevel, cropState, event, pos);
		serverLevel.setBlockAndUpdate(pos, cropState.setValue(age, Integer.valueOf(0)));
    }

    @SuppressWarnings("deprecation")
	private void dropResources(ServerLevel serverLevel, BlockState cropState, RightClickBlock event, BlockPos pos) {
    	List<ItemStack> drops = getDrops(serverLevel, cropState, event.getPlayer(), pos);
    	boolean seedRemoved = false;
        for (ItemStack stack : drops) {
        	if (!seedRemoved && stack.sameItem(cropState.getBlock().getCloneItemStack(serverLevel, pos, cropState))) {
        		stack.shrink(1);
        		seedRemoved = true;
        	}
			Block.popResourceFromFace(serverLevel, pos, event.getFace(), stack);
        }
    }

    private List<ItemStack> getDrops(ServerLevel serverLevel, BlockState cropState, Player player, BlockPos pos) {
    	return cropState.getDrops(
    		new LootContext.Builder(serverLevel)
    			.withParameter(LootContextParams.ORIGIN, new Vec3(pos.getX(), pos.getY(), pos.getZ()))
    			.withParameter(LootContextParams.BLOCK_STATE, cropState)
    			.withParameter(LootContextParams.THIS_ENTITY, player)
    			.withParameter(LootContextParams.TOOL, player.getMainHandItem())
    	);
    }

    private void cancel(RightClickBlock event) {
    	event.setCancellationResult(InteractionResult.CONSUME);
		event.setCanceled(true);
    }

    private IntegerProperty getAge(BlockState cropState) throws NullPointerException, NoSuchElementException {
    	return (IntegerProperty) cropState.getProperties().stream().filter(property -> property.getName().equals("age")).findFirst().orElseThrow();
    }

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
    
    private boolean isHoe(ItemStack handItem) {
    	return ToolActions.DEFAULT_HOE_ACTIONS.stream().allMatch(toolAction -> handItem.canPerformAction(toolAction));
    }
    
    private boolean isCrop(Block block) {
		return block instanceof CropBlock || crops.contains(getKey(block));
	}

    private String getKey(Block block) {
    	return ForgeRegistries.BLOCKS.getKey(block).toString();
    }
}
