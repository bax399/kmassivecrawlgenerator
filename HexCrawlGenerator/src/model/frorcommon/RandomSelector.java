//https://stackoverflow.com/questions/6409652/random-weighted-selection-in-java/30362366
package model.frorcommon;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;

public final class RandomSelector<T> {

  public static <T> RandomSelector<T> weighted(Set<T> elements, ToDoubleFunction<? super T> weighter)
      throws IllegalArgumentException {
    requireNonNull(elements, "elements must not be null");
    requireNonNull(weighter, "weighter must not be null");
    if (elements.isEmpty()) { throw new IllegalArgumentException("elements must not be empty"); }

    // Array is faster than anything. Use that.
    int size = elements.size();
    T[] elementArray = elements.toArray((T[]) new Object[size]);

    double totalWeight = 0d;
    double[] discreteProbabilities = new double[size];

    // Retrieve the probabilities
    for (int i = 0; i < size; i++) {
      double weight = weighter.applyAsDouble(elementArray[i]);
      if (weight < 0.0d) { throw new IllegalArgumentException("weighter may not return a negative number"); }
      discreteProbabilities[i] = weight;
      totalWeight += weight;
    }
    if (totalWeight == 0.0d) { throw new IllegalArgumentException("the total weight of elements must be greater than 0"); }

    // Normalize the probabilities
    for (int i = 0; i < size; i++) {
      discreteProbabilities[i] /= totalWeight;
    }
    return new RandomSelector<>(elementArray, new RandomWeightedSelection(discreteProbabilities));
  }

  private final T[] elements;
  private final ToIntFunction<Random> selection;

  private RandomSelector(T[] elements, ToIntFunction<Random> selection) {
    this.elements = elements;
    this.selection = selection;
  }

  public T next(Random random) {
    return elements[selection.applyAsInt(random)];
  }

  private class BaseIterator implements Iterator<T> {

	    private final Random random;

	    BaseIterator(final Random random) {
	      this.random = random;
	    }

	    @Override
	    public boolean hasNext() {
	      return true;
	    }

	    @Override
	    public T next() {
	      return RandomSelector.this.next(this.random);
	    }
	  }
  
  
  private static class RandomWeightedSelection implements ToIntFunction<Random> {
    // Alias method implementation O(1)
    // using Vose's algorithm to initialize O(n)

    private final double[] probabilities;
    private final int[] alias;

    RandomWeightedSelection(double[] probabilities) {
      int size = probabilities.length;

      double average = 1.0d / size;
      int[] small = new int[size];
      int smallSize = 0;
      int[] large = new int[size];
      int largeSize = 0;

      // Describe a column as either small (below average) or large (above average).
      for (int i = 0; i < size; i++) {
        if (probabilities[i] < average) {
          small[smallSize++] = i;
        } else {
          large[largeSize++] = i;
        }
      }

      final double[] pr = new double[size];
      final int[] al = new int[size];
      this.probabilities = pr;
      this.alias = al;      
      
      // For each column, saturate a small probability to average with a large probability.
      while (largeSize != 0 && smallSize != 0) {
        int less = small[--smallSize];
        int more = large[--largeSize];
        probabilities[less] = probabilities[less] * size;
        alias[less] = more;
        probabilities[more] += probabilities[less] - average;
        if (probabilities[more] < average) {
          small[smallSize++] = more;
        } else {
          large[largeSize++] = more;
        }
      }

      // Flush unused columns.
      while (smallSize != 0) {
        probabilities[small[--smallSize]] = 1.0d;
      }
      while (largeSize != 0) {
        probabilities[large[--largeSize]] = 1.0d;
      }
    }

    @Override public int applyAsInt(Random random) {
      // Call random once to decide which column will be used.
      int column = random.nextInt(probabilities.length);

      // Call random a second time to decide which will be used: the column or the alias.
      if (random.nextDouble() < probabilities[column]) {
        return column;
      } else {
        return alias[column];
      }
    }
  }
}