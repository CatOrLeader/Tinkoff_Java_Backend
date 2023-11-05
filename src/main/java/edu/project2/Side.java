package edu.project2;

public enum Side {
    NORTH {
        @Override
        public Position getMove() {
            return new Position(0, -1);
        }
    }, EAST {
        @Override
        public Position getMove() {
            return new Position(1, 0);
        }
    }, SOUTH {
        @Override
        public Position getMove() {
            return new Position(0, 1);
        }
    }, WEST {
        @Override
        public Position getMove() {
            return new Position(-1, 0);
        }
    };

    public Position getMove() {
        return new Position(0, 0);
    }

    public static Side parseSide(Position position) {
        if (position.equals(NORTH.getMove())) {
            return NORTH;
        }

        if (position.equals(EAST.getMove())) {
            return EAST;
        }

        if (position.equals(SOUTH.getMove())) {
            return SOUTH;
        }

        if (position.equals(WEST.getMove())) {
            return WEST;
        }

        throw new IllegalArgumentException("Illegal position provided to the parser of sides");
    }
}
