/**
 * This is the main interface that will be taken advantage of in each of the
 * classes that implement this interface
 *
 * @author Josh Greco
 */
public interface Player {
    /**
     * returns an integer when evaluate is called (changes per class that
     * implements it)
     */
    boolean evaluate();

    /**
     * returns an String when emit is called (changes per class that
     * implements it)
     */
    String emit();
}