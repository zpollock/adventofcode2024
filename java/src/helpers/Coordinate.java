package src.helpers;

public class Coordinate {
    public int x;
    public int y;
    
    public Coordinate() {

    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT
    }

    public static final int[][] DIRECTIONS = {
            { 1, 0 },
            { 0, 1 },
            { -1, 0 },
            { 0, -1 },
            { -1, -1 },
            { 1, -1 },
            { -1, 1 },
            { 1, 1 }
    };

    public static final int[][] DIRECTIONS_WO_DIAGONALS = {
            { 1, 0 },
            { 0, 1 },
            { -1, 0 },
            { 0, -1 },
    };

    public static Direction turn90Left(Direction dir) {
        switch (dir) {
            case UP:
                return Direction.LEFT;
            case LEFT:
                return Direction.DOWN;
            case DOWN:
                return Direction.RIGHT;
            case RIGHT:
                return Direction.UP;
            default:
                throw new IllegalArgumentException("Unsupported direction");
        }
    }

    public static Direction turn90Right(Direction dir) {
        switch (dir) {
            case UP:
                return Direction.RIGHT;
            case RIGHT:
                return Direction.DOWN;
            case DOWN:
                return Direction.LEFT;
            case LEFT:
                return Direction.UP;
            default:
                throw new IllegalArgumentException("Unsupported direction");
        }
    }
}
