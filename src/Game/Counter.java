package Game;

/**
 * Counter class. supports: increase, decrease, getValue;
 */
public class Counter {
    private int count;

    /**
     * Initialize a counter.
     *
     * @param start starting value of counter
     */
    public Counter(int start) {
        count = start;
    }

    /**
     * Initialize a counter with starting value of 0.
     */
    public Counter() {
        this(0);
    }

    // add number to current count.
    void increase(int number) {
        count += number;
    }

    // subtract number from current count.
    void decrease(int number) {
        count -= number;
    }

    // get current count.
    int getValue() {
        return count;
    }
}