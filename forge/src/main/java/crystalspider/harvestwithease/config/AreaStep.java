package crystalspider.harvestwithease.config;

/**
 * Harvest area size increase step.
 */
public enum AreaStep {
  NONE(0),
  SMALL(2),
  MEDIUM(4),
  LARGE(6);

  /**
   * Increase step.
   */
  public final int step;

  /**
   * @param step {@link #step}.
   */
  AreaStep(int step) {
    this.step = step;
  }

  @Override
  public String toString() {
    return name().toLowerCase();
  }
}
