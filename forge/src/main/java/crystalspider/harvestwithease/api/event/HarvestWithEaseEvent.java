package crystalspider.harvestwithease.api.event;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;

/**
 * Event fired by Harvest With Ease mod during right-click harvest.
 * It extends {@link PlayerEvent}, which means that a method subscribing to {@link PlayerEvent} will receive also this event and all of its children.
 * Like {@link PlayerEvent}, all children of this event are fired on the {@link MinecraftForge#EVENT_BUS}.
 */
public abstract class HarvestWithEaseEvent<P extends Player, L extends Level> extends PlayerEvent {
  /**
   * {@link Level} of the interaction.
   */
  protected final L level;
  /**
   * {@link BlockState} of the crop being harvested.
   */
  protected final BlockState target;
  /**
   * {@link BlockPos} of the crop being harvested.
   */
  protected final BlockPos pos;
  /**
   * {@link InteractionHand} used when harvesting.
   */
  protected final InteractionHand hand;

  /**
   * @param level {@link #level}.
   * @param target {@link #target}.
   * @param pos {@link #pos}.
   * @param player player right-click harvesting.
   * @param hand {@link #hand}.
   */
  public HarvestWithEaseEvent(L level, BlockState target, BlockPos pos, P player, InteractionHand hand) {
    super(player);
    this.level = level;
    this.target = target;
    this.pos = pos;
    this.hand = hand;
  }

  @Override
  @SuppressWarnings("unchecked")
  public P getEntity() {
    return (P) super.getEntity();
  }

  /**
   * Returns this {@link #level}.
   * 
   * @return this {@link #level}.
   */
  public L getLevel() {
    return level;
  }

  /**
   * Returns this {@link #target}.
   * 
   * @return this {@link #target}.
   */
  public BlockState getTargetBlock() {
    return target;
  }

  /**
   * Returns this {@link #pos}.
   * 
   * @return this {@link #pos}.
   */
  public BlockPos getPos() {
    return pos;
  }

  /**
   * Returns this {@link #hand}.
   * 
   * @return this {@link #hand}.
   */
  public InteractionHand getHand() {
    return hand;
  }

  /**
   * Similar to {@link HarvestWithEaseEvent}, but only gets fired server-side.
   */
  public static abstract class HarvestWithEaseServerEvent extends HarvestWithEaseEvent<ServerPlayer, ServerLevel> {
    /**
     * {@link Direction face} of the crop block clicked.
     */
    protected final Direction face;
    /**
     * {@link BlockHitResult}.
     */
    protected final BlockHitResult hitResult;

    /**
     * @param level {@link #level}.
     * @param target {@link #target}.
     * @param pos {@link #pos}.
     * @param face {@link #face}.
     * @param hitResult {@link #hitResult}.
     * @param player {@link ServerPlayer player} right-click harvesting.
     * @param hand {@link #hand}.
     */
    public HarvestWithEaseServerEvent(ServerLevel level, BlockState target, BlockPos pos, Direction face, BlockHitResult hitResult, ServerPlayer player, InteractionHand hand) {
      super(level, target, pos, player, hand);
      this.face = face;
      this.hitResult = hitResult;
    }

    /**
     * Returns this {@link #face}.
     * 
     * @return this {@link face}.
     */
    public Direction getFace() {
      return face;
    }

    /**
     * Returns this {@link #hitResult}.
     * 
     * @return this {@link hitResult}.
     */
    public BlockHitResult getHitVec() {
      return hitResult;
    }
  }

  /**
   * Event fired when checking whether the player can right-click harvest.
   */
  @Cancelable
  public static class RightClickHarvestCheck extends HarvestWithEaseEvent<Player, Level> {
    /**
     * Whether the player can right-click harvest the crop.
     */
    private boolean canHarvest;

    /**
     * @param level {@link #level}.
     * @param target {@link #target}.
     * @param pos {@link #pos}.
     * @param player {@link #player}.
     * @param hand {@link #hand}.
     * @param canHarvest {@link #canHarvest}.
     */
    public RightClickHarvestCheck(Level level, BlockState target, BlockPos pos, Player player, InteractionHand hand, boolean canHarvest) {
      super(level, target, pos, player, hand);
      this.canHarvest = canHarvest;
    }
    
    /**
     * Returns this {@link #canHarvest}.
     * 
     * @return this {@link #canHarvest}.
     */
    public boolean canHarvest() {
      return this.canHarvest;
    }

    /**
     * Sets this {@link #canHarvest}.
     * 
     * @param canHarvest
     */
    public void setCanHarvest(boolean canHarvest) {
      this.canHarvest = canHarvest;
    }
  }

  /**
   * Event fired before right-click harvesting.
   */
  public static class BeforeHarvest extends HarvestWithEaseServerEvent {
    /**
     * @param level {@link #level}.
     * @param target {@link #target}.
     * @param pos {@link #pos}.
     * @param face {@link #face}.
     * @param hitResult {@link #hitResult}.
     * @param player {@link #player}.
     * @param hand {@link #hand}.
     */
    public BeforeHarvest(ServerLevel level, BlockState target, BlockPos pos, Direction face, BlockHitResult hitResult, ServerPlayer player, InteractionHand hand) {
      super(level, target, pos, face, hitResult, player, hand);
    }
  }

  /**
   * Event fired when calculating the drops resulting from right-click harvesting.
   */
  @Cancelable
  public static class HarvestDrops extends HarvestWithEaseServerEvent {
    /**
     * List of drops.
     */
    public final List<ItemStack> drops;

    /**
     * Reference to the default drops.
     */
    private final List<ItemStack> defaultDrops;

    /**
     * @param level {@link #level}.
     * @param target {@link #target}.
     * @param pos {@link #pos}.
     * @param face {@link #face}.
     * @param hitResult {@link #hitResult}.
     * @param player {@link ServerPlayer player} right-click harvesting.
     * @param hand {@link #hand}.
     */
    public HarvestDrops(ServerLevel level, BlockState target, BlockPos pos, Direction face, BlockHitResult hitResult, ServerPlayer player, InteractionHand hand) {
      super(level, target, pos, face, hitResult, player, hand);
      defaultDrops = initDrops();
      drops = new ArrayList<>(defaultDrops.stream().map(item -> item.copy()).toList());
    }

    /**
     * Returns whether the list of drops changed from its default value.
     * 
     * @return whether the list of drops changed from its default value.
     */
    public boolean haveDropsChanged() {
      if (defaultDrops.size() == drops.size()) {
        for (int c = 0; c < defaultDrops.size(); c++) {
          if (!ItemStack.matches(defaultDrops.get(c), drops.get(c))) {
            return true;
          }
        }
        return false;
      }
      return true;
    }

    /**
     * Initializes the {@link #drops} list.
     * 
     * @return the list of drops a player would get by breaking the crop, with one seed removed.
     */
    private List<ItemStack> initDrops() {
      List<ItemStack> drops = Block.getDrops(target, level, pos, target.hasBlockEntity() ? level.getBlockEntity(pos) : null, getEntity(), getEntity().getItemInHand(hand));
      boolean seedRemoved = false;
      for (ItemStack stack : drops) {
        if (!seedRemoved && stack.sameItem(target.getBlock().getCloneItemStack(target, hitResult, level, pos, getEntity()))) {
          stack.shrink(1);
          seedRemoved = true;
        }
      }
      return drops;
    }
  }

  /**
   * Event fired after right-click harvesting.
   */
  public static class AfterHarvest extends HarvestWithEaseServerEvent {
    /**
     * @param level {@link #level}.
     * @param target {@link #target}.
     * @param pos {@link #pos}.
     * @param face {@link #face}.
     * @param hitResult {@link #hitResult}.
     * @param player {@link #player}.
     * @param hand {@link #hand}.
     */
    public AfterHarvest(ServerLevel level, BlockState target, BlockPos pos, Direction face, BlockHitResult hitResult, ServerPlayer player, InteractionHand hand) {
      super(level, target, pos, face, hitResult, player, hand);
    }
  }
}
