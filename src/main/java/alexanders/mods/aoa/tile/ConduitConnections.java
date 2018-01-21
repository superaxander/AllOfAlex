package alexanders.mods.aoa.tile;

public enum ConduitConnections {
    NONE("none", 0), TOP("top", 0), TOP_BOTTOM("top_bottom", 0), TOP_RIGHT("top_right", 0), TOP_LEFT("top_right", 270), TOP_RIGHT_BOTTOM("top_right_bottom", 0),
    TOP_RIGHT_LEFT("top_right_bottom", 270), TOP_BOTTOM_LEFT("top_right_bottom", 180), TOP_RIGHT_BOTTOM_LEFT("top_right_bottom_left", 0), RIGHT("top", 90),
    RIGHT_BOTTOM("top_right", 90),
    RIGHT_LEFT("top_bottom", 90), RIGHT_BOTTOM_LEFT("top_right_bottom", 90), BOTTOM("top", 180), BOTTOM_LEFT("top_right", 180), LEFT("top", 270);

    public final String suffix;
    public final int rotation;

    ConduitConnections(String suffix, int rotation) {
        this.suffix = suffix;
        this.rotation = rotation;
    }

    public ConduitConnections addConnection(int x, int y) {
        switch (x) {
            case 1:
                switch (y) {
                    case 0:
                        switch (this) {
                            case TOP:
                                return TOP_RIGHT;
                            case TOP_BOTTOM:
                                return TOP_RIGHT_BOTTOM;
                            case TOP_LEFT:
                                return TOP_RIGHT_LEFT;
                            case TOP_BOTTOM_LEFT:
                                return TOP_RIGHT_BOTTOM_LEFT;
                            case BOTTOM:
                                return RIGHT_BOTTOM;
                            case BOTTOM_LEFT:
                                return RIGHT_BOTTOM_LEFT;
                            case LEFT:
                                return RIGHT_LEFT;
                            case NONE:
                                return RIGHT;
                        }
                        break;
                }
                break;
            case 0:
                switch (y) {
                    case 1:
                        switch (this) {
                            case RIGHT:
                                return TOP_RIGHT;
                            case RIGHT_BOTTOM:
                                return TOP_RIGHT_BOTTOM;
                            case RIGHT_LEFT:
                                return TOP_RIGHT_LEFT;
                            case RIGHT_BOTTOM_LEFT:
                                return TOP_RIGHT_BOTTOM_LEFT;
                            case BOTTOM:
                                return TOP_BOTTOM;
                            case BOTTOM_LEFT:
                                return TOP_BOTTOM_LEFT;
                            case LEFT:
                                return TOP_LEFT;
                            case NONE:
                                return TOP;
                        }
                        break;
                    case -1:
                        switch (this) {
                            case TOP:
                                return TOP_BOTTOM;
                            case TOP_RIGHT:
                                return TOP_RIGHT_BOTTOM;
                            case TOP_LEFT:
                                return TOP_BOTTOM_LEFT;
                            case TOP_RIGHT_LEFT:
                                return TOP_RIGHT_BOTTOM_LEFT;
                            case RIGHT:
                                return RIGHT_BOTTOM;
                            case RIGHT_LEFT:
                                return RIGHT_BOTTOM_LEFT;
                            case LEFT:
                                return BOTTOM_LEFT;
                            case NONE:
                                return BOTTOM;
                        }
                        break;
                }
                break;
            case -1:
                switch (y) {
                    case 0:
                        switch (this) {
                            case TOP:
                                return TOP_LEFT;
                            case TOP_BOTTOM:
                                return TOP_BOTTOM_LEFT;
                            case TOP_RIGHT:
                                return TOP_RIGHT_LEFT;
                            case TOP_RIGHT_BOTTOM:
                                return TOP_RIGHT_BOTTOM_LEFT;
                            case RIGHT:
                                return RIGHT_LEFT;
                            case RIGHT_BOTTOM:
                                return RIGHT_BOTTOM_LEFT;
                            case BOTTOM:
                                return BOTTOM_LEFT;
                            case NONE:
                                return LEFT;
                        }
                        break;
                }
                break;
        }
        return this;
    }

    public ConduitConnections removeConnection(int x, int y) {
        switch (x) {
            case 1:
                switch (y) {
                    case 0:
                        switch (this) {
                            case TOP_RIGHT:
                                return TOP;
                            case TOP_RIGHT_BOTTOM:
                                return TOP_BOTTOM;
                            case TOP_RIGHT_LEFT:
                                return TOP_LEFT;
                            case TOP_RIGHT_BOTTOM_LEFT:
                                return TOP_BOTTOM_LEFT;
                            case RIGHT:
                                return NONE;
                            case RIGHT_BOTTOM:
                                return BOTTOM;
                            case RIGHT_LEFT:
                                return LEFT;
                            case RIGHT_BOTTOM_LEFT:
                                return BOTTOM_LEFT;
                        }
                        break;
                }
                break;
            case 0:
                switch (y) {
                    case 1:
                        switch (this) {
                            case TOP:
                                return NONE;
                            case TOP_LEFT:
                                return LEFT;
                            case TOP_BOTTOM:
                                return BOTTOM;
                            case TOP_RIGHT:
                                return RIGHT;
                            case TOP_RIGHT_BOTTOM:
                                return RIGHT_BOTTOM;
                            case TOP_RIGHT_LEFT:
                                return RIGHT_LEFT;
                            case TOP_BOTTOM_LEFT:
                                return BOTTOM_LEFT;
                            case TOP_RIGHT_BOTTOM_LEFT:
                                return RIGHT_BOTTOM_LEFT;

                        }
                        break;
                    case -1:
                        switch (this) {
                            case TOP_BOTTOM:
                                return TOP;
                            case TOP_RIGHT_BOTTOM:
                                return TOP_RIGHT;
                            case TOP_BOTTOM_LEFT:
                                return TOP_LEFT;
                            case TOP_RIGHT_BOTTOM_LEFT:
                                return TOP_RIGHT_LEFT;
                            case RIGHT_BOTTOM:
                                return RIGHT;
                            case RIGHT_BOTTOM_LEFT:
                                return RIGHT_LEFT;
                            case BOTTOM:
                                return NONE;
                            case BOTTOM_LEFT:
                                return LEFT;
                        }
                        break;
                }
                break;
            case -1:
                switch (y) {
                    case 0:
                        switch (this) {
                            case TOP_LEFT:
                                return TOP;
                            case TOP_RIGHT_LEFT:
                                return TOP_RIGHT;
                            case TOP_BOTTOM_LEFT:
                                return TOP_BOTTOM;
                            case TOP_RIGHT_BOTTOM_LEFT:
                                return TOP_RIGHT_BOTTOM;
                            case RIGHT_LEFT:
                                return RIGHT;
                            case RIGHT_BOTTOM_LEFT:
                                return RIGHT_BOTTOM;
                            case BOTTOM_LEFT:
                                return BOTTOM;
                            case LEFT:
                                return NONE;
                        }
                        break;
                }
                break;
        }
        return this;
    }
}
