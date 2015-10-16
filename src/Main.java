import java.util.Arrays;

public class Main {
	
	public static void main(String[] args) {
		outputTest();
	}
	
	private static void outputTest() {
		double array[] = new double[50];
		for (int i = 0; i < array.length; ++i) {
			array[i] = StdRandom.uniform();
		}
		Arrays.sort(array);
		
		for (int i = 0; i < array.length; ++i) {
			double x = i / (double)array.length;
			double y = .5 * array[i];
			double w = .5 / array.length;
			double h = .5 * array[i];
			
			StdDraw.filledRectangle(x, y, w, h);
		}
	}
}
