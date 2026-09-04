package ngz.meteofrance.wcs.utils;

import java.util.ArrayList;
import java.util.List;

/** Generic utility for computing the cartesian product of N lists. */
public final class CartesianProductUtils {

    private CartesianProductUtils() {} // utility class – no instances

    /**
     * Input : [ [1,2], [A,B], [X,Y] ] Output: [ [1,A,X], [1,A,Y], [1,B,X], [1,B,Y], [2,A,X], ... ]
     */
    public static <T> List<List<T>> cartesianProduct(List<List<T>> lists) {
        List<List<T>> result = new ArrayList<>();
        result.add(new ArrayList<>()); // seed with one empty combination

        for (List<T> currentList : lists) {
            List<List<T>> newResult = new ArrayList<>();
            for (List<T> existingCombination : result) {
                for (T value : currentList) {
                    List<T> newCombination = new ArrayList<>(existingCombination);
                    newCombination.add(value);
                    newResult.add(newCombination);
                }
            }
            result = newResult;
        }
        return result;
    }
}
