package sortable;

import java.util.concurrent.TimeUnit;
import com.google.common.base.Stopwatch;

public class Program {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		 String listingsFile = "res/listings.txt";
		 String productsFile = "res/products.txt";
		 String resultsFile = "out/results.txt";
		 String diffFile = "out/diffs.txt";
		 		 
		 ListingMatcher matcher = new ListingMatcher(listingsFile, productsFile);

		 Stopwatch watch = Stopwatch.createUnstarted();
		 watch.start();
		 matcher.run();
		 matcher.printResults(resultsFile);
		 watch.stop();
		 
		 matcher.getDiffs(diffFile);
		 
		 System.out.printf("\nTime taken: %d ms", watch.elapsed(TimeUnit.MILLISECONDS));
	} 
}
