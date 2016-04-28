
/**
 * Interface for distance.
 *
 * @param <T>
 */
public interface Distance<T> {
    /**
     * Distance variable that takes in two generics.
     * @param one first input
     * @param two second input 
     * @return distance between one and twos
     */
    double distance(T one, T two);
}
