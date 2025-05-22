package Game;

/**
 * Interface for HitNotifiers, notify someone that something is being hit.
 */
public interface HitNotifier {
    /**
     * Add hl to the list of listeners to hit event.
     * @param hl hit listener to add
     */
    void addHitListener(HitListener hl);

    /**
     * Remove hl from the list of listeners.
     * @param hl hit listener to remove
     */
    void removeHitListener(HitListener hl);
}
