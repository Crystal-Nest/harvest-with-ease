package crystalspider.harvestwithease.config;

/**
 * Harvest area size.
 */
public enum AreaSize {
  SINGLE(1),
  MEDIUM(3),
  LARGE(5);

  /**
   * Numeric size (square side length).
   */
  public final int size;

  /**
   * @param size {@link #size}.
   */
  AreaSize(int size) {
    this.size = size;
  }

  @Override
  public String toString() {
    return name().toLowerCase();
  }
}
