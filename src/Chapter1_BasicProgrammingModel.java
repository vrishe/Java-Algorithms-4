import org.junit.*;
import static org.junit.Assert.*;

public class Chapter1_BasicProgrammingModel {
	
	@BeforeClass
	public static void introduce() {
		StdOut.println("1.1 Basic programming model");
	}
	
	/**
	 * What will be printed?
	 * 
	 * A 16th <a href="https://en.wikipedia.org/wiki/Fibonacci_number">Fibonacci</a> number which is 610;
	 */
	@Test
	public void exercise1_1_6() {
		Utils.announceTestMethod();
		
		int f = 0, g = 1;
		
		for (int i = 0; i <= 15; ++i) {
			StdOut.println(f);
			
			f = f + g;
			g = f - g;
		}
		assertEquals(610, g);
	}
	
	/**
	 * What will be printed?
	 * 
	 * a) An approximation of a square root of 9, which is ~3;
	 * b) A lower triangle area for a square with side N: N * (N - 1) / 2, which is 499500;
	 * c) A 2^n - 1, which is nearest to a given number (1000), which is 1023.
	 */
	@Test
	public void exercise1_1_7() {
		Utils.announceTestMethod();
		
		final double expectedA = 9.0;
		double t = expectedA;
		while(Math.abs(t - expectedA/t) > .001) {
			t = (t + expectedA/t) / 2;
		}
		StdOut.printf("a) Result is: %f\n", t);
		assertEquals(Math.sqrt(expectedA), t, .001);
		
		final int expectedB = 1000;
		int sum = 0;
		for (int i = 1; i < 1000; ++i) {
			for (int j = 0; j < i; ++j)
				++sum;
		}
		StdOut.printf("b) Result is: %d\n", sum);
		assertEquals(expectedB * (expectedB - 1) / 2, sum);
		
		final int expectedC = 1000;
		sum = 0;
		for (int i = 1; i < 1000; i *= 2) {
			for (int j = 0; j < i; ++j)
				++sum;
		}
		StdOut.printf("c) Result is: %d\n", sum);
		assertEquals((int)Math.pow(2, Math.round(Math.log(expectedC) / Math.log(2))) - 1, sum);
	}
	
	/**
	 * What will be printed?
	 * 
	 * a) Just a character a;
	 * b) An integer value of 'b' plus 'c': 98 + 99 = 197;
	 * c) The fourth character after 'a', which is: 97 + 4 = 101 -> 'e'.
	 */
	@Test
	public void exercise1_1_8() {
		Utils.announceTestMethod();
		
		System.out.println('b');				// b
		System.out.println('b' + 'c');			// 197, summation casts char to integer implicitly.
		System.out.println((char)('a' + 4));	// e
	}
}
