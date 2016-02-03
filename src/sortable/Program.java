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

		System.out.println("Starting program");
		
		ListingMatcher matcher = new ListingMatcher(listingsFile, productsFile);

		Stopwatch watch = Stopwatch.createStarted();
		matcher.run();
		
		long timeElapsed = watch.elapsed(TimeUnit.MILLISECONDS);
		
		matcher.printResults(resultsFile);		 
		matcher.getDiffs(diffFile);
		watch.stop();

		System.out.printf("\nProduct matching run time: %d ms", timeElapsed);
		System.out.printf("\nEntire Program run time: %d ms", watch.elapsed(TimeUnit.MILLISECONDS));
	} 
}
