import org.junit.*;

import static org.junit.Assert.*;

public class Chapter1_BasicProgrammingModel extends ChapterTestBase {
		
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
	
	
	/**
	 * Convert integer to string
	 */
	@Test
	public void exercise1_1_9() {
		Utils.announceTestMethod();
		
		final int value = 451;
		
		StringBuilder sb = new StringBuilder();
		for (int i = value; i > 0; i /= 2) {
			sb.insert(0, i % 2);
		}
		String expected = Integer.toBinaryString(value);
		
		StdOut.printf("Binary representation of %d is: %s (ref. %s)\n", 
				value, sb.toString(), expected);
		assertEquals(expected, sb.toString());
	}
	
	/**
	 * What will be printed out?
	 * 
	 * Result: 0, 1, 2, 3, 4, 4, 3, 2, 1, 0
	 */
	@Test
	public void exercise1_1_12() {
		Utils.announceTestMethod();
		
		int array[] = new int[10];
		int expected[] = new int[array.length];
		
		int ihalf = array.length / 2 + array.length % 2;
		for (int i = 0, imax = array.length - 1; i < array.length; ++i) {
			array[i] = imax - i;
			
			if (i < ihalf) {
				expected[i] = i;
				expected[imax - i] = i;
			}
		}
		StringBuilder sb = new StringBuilder();
			
		for (int i = 0; i < array.length; ++i) {
			array[i] = array[array[i]];
			
			sb.append(array[i]).append(',');
		}
		sb.delete(sb.length() - 1, sb.length());
		
		StdOut.printf("Output: %s\n", sb);
		assertArrayEquals(expected, array);
	}
	
	/**
	 * Find a number A, such as: 0 < A <= Log2(N), where A -> max.
	 */
	@Test
	public void exercise1_1_14() {
		Utils.announceTestMethod();
		
		final int N = 1763;
		
		int result = 0, value = N;
		while ((value /= 2) > 0) {
			++result;
		}
		int expected = (int)(Math.log(N) / Math.log(2));
		
		StdOut.printf("Result is: %d (expected %d)\n", result, expected);
		assertEquals(expected, result);
	}
	
	/**
	 * Build a histogram for an array.
	 * 
	 * @see <a href="https://www.random.org/">Random.org</a>
	 */
	@Test
	public void exercise1_1_15() {
		Utils.announceTestMethod();
		
		final int array[] = new int[] { 
			42, 82, 10, 49, 47, 63, 25,  2, 52, 84,
			58, 94,100, 99, 75, 68,  9, 33, 59, 96,
			51, 17, 44, 42, 88, 86, 92, 54, 37, 15,
			55, 31, 67, 72, 71, 96, 65, 78, 39, 28,
			46, 21, 34, 18, 11, 16, 14, 60, 65, 32,
			76,  7, 81, 12, 55, 46, 95, 79, 22, 85,
			29, 27, 39, 70, 90, 23, 98, 24, 13, 36,
			80, 40, 38,  1, 62, 26, 53,  4,  6, 73,
			30,  3, 41, 88, 88,  5, 20, 69, 50, 43,
			56, 78, 19,  5, 74, 83, 45,  8, 77, 66
		};
		int min = Integer.MAX_VALUE, histogram[];
		{
			int max = Integer.MIN_VALUE, histogramSize;
			
			for (int value : array) {
				if (value < min) {
					min = value;
				} 
				if (value > max) {
					max = value;
				}
			}			
			histogramSize = max - min + 1;
			assertTrue(histogramSize <= array.length);
			
			histogram = new int[histogramSize];
		}
		StringBuilder sb = new StringBuilder(String.format("MIN, %d -> ", min));
		
		for (int value : array) {
			++histogram[value - min];
		}
		int count = 0;
		
		for (int value : histogram) {
			count += value;
			
			sb.append(value).append(',');
		}
		sb.delete(sb.length() - 1, sb.length());
		sb.append(String.format(" -> %d, MAX", min + histogram.length - 1));
		
		StdOut.println(sb.toString());
		StdOut.printf("Result is: %d (expected %d)\n", count, array.length);
		assertEquals(array.length, count);
	}
	
	/**
	 * What will be printed?
	 * 
	 * Result: "311361142246"
	 */
	@Test
	public void exercise1_1_16() {
		Utils.announceTestMethod();
		
		String result = exR1(6);
		StdOut.printf("Result is: %s\n", result);
		assertEquals("311361142246", result);
	}

	private static String exR1(int n) {
		if (n > 0) {
			return exR1(n - 3) + n + exR1(n - 2) + n;
		}
		return "";
	}
	
	/**
	 * What will be printed?
	 * 
	 * Result: 50 and 33, mystery(a, b) counts a*b;
	 * 	If '+' changed with '*' and 'return 0' is changed with 'return 1', then is counts a^b.
	 */
	@Test
	public void exercise1_1_18() {
		Utils.announceTestMethod();
		
		final int result1 = mystery(2, 25);
		final int result2 = mystery(3, 11);
		
		StdOut.printf("Results are: %d, %d\n", result1, result2);
		assertTrue(result1 == 50 && result2 == 33);
	}
	
	private static int mystery(int a, int b) {
		if (b != 0) {
			int result = mystery(a + a, b / 2);
			
			return b % 2 == 0 ? result : result + a;
		}
		return 0;
	}
	
	/**
	 * Fibonacci calculations.
	 */
	@Test(timeout = 60 * 60 * 1000)
	@ConditionalIgnoreRule.ConditionalIgnore(from = TestConditions.class, select = "RUN_SHORT_TESTS_ONLY")
	public void exercise1_1_19() {
		Utils.announceTestMethod();
		
		final int N = 2;
		
		F(N); // JIT warm-up.
		double time = Utils.measureExecutionTimeFor(new Action() {

			@Override
			public void invoke() {
				F(N); // A 'single' call.
			};
		});
		int maxNumber = 2 * (int)(Math.log((60 * 60 * 1000) / time) / Math.log(2)); // Very inexact, not sure about 2 multiplier. But still, Fibonacci has 2^n complexity.
		
		StdOut.printf("Maximum number to calculate F for less than an hour is: %d\n", maxNumber);
		
		final OutValue<Integer> recursiveDepth = new OutValue<>(0);
		final long unreachableResult = F(maxNumber, recursiveDepth);
		
		StdOut.printf("Cheers! Fibonacci number for index %d is %d (recursive depth: %d)", 
				maxNumber, unreachableResult, recursiveDepth.value);
	}
	
	static class OutValue<T> {
		
		public OutValue(T value) {
			this.value = value;
		}
		
		T value;
	};
	
	private static long F(int value, OutValue<Integer> outValue) {
		outValue.value++;
		
		if (value > 1) {
			return F(value - 2, outValue) + F(value - 1, outValue);
		}	
		return value;
	}
	
	private static long F(int value) {
		if (value > 1) {
			return F(value - 2) + F(value - 1);
		}		
		return value;
	}
}
