public enum Action {
  /**
   * Represents the possible movements in the 3D matrix.
   * x is the change in layer (up/down),
   * y is the change in row (south/north),
   * z is the change in column (east/west).
   */
  E(0, 0, 1),
  W(0, 0, -1),
  S(0, 1, 0),
  N(0, -1, 0),
  U(-1, 0, 0),
  D(1, 0, 0);

  private final int x;
  private final int y;
  private final int z;

  Action(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public int getX() {
    return x;
  }
  public int getY() {
    return y;
  }
  public int getZ() {
    return z;
  }
}
