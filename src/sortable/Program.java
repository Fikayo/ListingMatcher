package sortable;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

public class Program {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		 String listingsFile = "res/listings.txt";
		 String productsFile = "res/products.txt";
		 String resultsFile = "out/results.txt";
		 String diffFile = "out/diffs.txt";
		 		 
		 Stopwatch watch = Stopwatch.createUnstarted();
		 ListingMatcher matcher = new ListingMatcher(listingsFile, productsFile);
		 watch.start();
		 matcher.run();
		 matcher.printResults(resultsFile);
		 watch.stop();
		 matcher.getDiffs(diffFile);
		 
		 System.out.printf("\nTime take: %d s", watch.elapsed(TimeUnit.MILLISECONDS));
	} 
}
