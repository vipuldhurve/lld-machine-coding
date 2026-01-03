package snakesAndLadders.models;

import lombok.Getter;

@Getter
public class Jumper {
    private Integer start;
    private Integer end;
    private final JumperType type;

    public Jumper(Integer start, Integer end, JumperType type) {
        validate(start, end, type);
        this.start = start;
        this.end = end;
        this.type = type;
    }

    public Jumper(Integer start, Integer end) {
        this.start = start;
        this.end = end;
        this.type = (end > start) ? JumperType.LADDER : JumperType.SNAKE;
    }

    private void validate(Integer start, Integer end, JumperType type) {
        if (start == null || end == null || type == null) {
            throw new IllegalArgumentException("Start, end, and type cannot be null.");
        }

        if (start.equals(end)) {
            throw new IllegalArgumentException("Start and end positions cannot be the same.");
        }

        switch (type) {
            case SNAKE:
                if (end > start) {
                    throw new IllegalArgumentException(
                            String.format("Invalid Snake: End (%d) must be lower than Start (%d)", end, start));
                }
                break;
            case LADDER:
                if (end < start) {
                    throw new IllegalArgumentException(
                            String.format("Invalid Ladder: End (%d) must be higher than Start (%d)", end, start));
                }
                break;
        }
    }
}
