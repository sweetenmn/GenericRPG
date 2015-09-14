package game;

/**
 * Created by josephbenton on 9/13/15.
 */
public enum Direction {
    UP {
        @Override
        public Position move(Position current) {
            return new Position(current.getX(), current.getY() - 1);
        }
    },
    DOWN {
        @Override
        public Position move(Position current) {
            return new Position(current.getX(), current.getY() + 1);
        }
    },
    LEFT {
        @Override
        public Position move(Position current) {
            return new Position(current.getX() - 1, current.getY());
        }
    },
    RIGHT {
        @Override
        public Position move(Position current) {
            return new Position(current.getX() + 1, current.getY());
        }
    };

    public abstract Position move(Position current);
}
