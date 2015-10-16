import org.junit.*;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.junit.runner.RunWith;

@SuiteClasses({
	Chapter1_BasicProgrammingModel.class
})
@RunWith(Suite.class)
public class Chapter1 {

	@BeforeClass
	public static void introduce() {
		StdOut.println("Chapter 1:");
	}
}
