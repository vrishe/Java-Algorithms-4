
public final class Utils {
	
	private static final int STACK_TRACE_DEPTH_BASE;

	static {
		// Finds out the index of "this code" in the returned stack trace - funny but it differs in JDK 1.5 and 1.6
		int i = 0;

		for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
			++i;

			if (ste.getClassName().equals(Utils.class.getName())) {
				break;
			}
		}
		STACK_TRACE_DEPTH_BASE = i;
	}


	public static String getCallerMethodName(int offsetUp, boolean justMethodName, boolean withLineNumber) {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

		if (stackTrace == null
				|| stackTrace.length <= STACK_TRACE_DEPTH_BASE)
			return "corrupted stack!";

		final int level = STACK_TRACE_DEPTH_BASE + offsetUp;
		if (0 <= level && level < stackTrace.length) {
			StackTraceElement callerElement = stackTrace[level];
			StringBuilder callerName = new StringBuilder();
			
			if (!justMethodName) {
				callerName.append(callerElement.getClassName()).append('#');
			}
			callerName.append(callerElement.getMethodName());

			if (withLineNumber) {
				callerName.append(String.format(
					" @ line %d", callerElement.getLineNumber()));
			}
			return callerName.toString() ;
		} else {
			return String.format("Out of stack bounds! (index: %d)", level);
		}
	}

	public static String getCallerMethodName(boolean withLineNumber) {
		return getCallerMethodName(1, false, withLineNumber);
	}

	public static String getCallerMethodName() {
		return getCallerMethodName(1, false, false);
	}
	
	public static void announceTestMethod() {
		StdOut.printf("\nExecuting... %s:\n", getCallerMethodName(1, true, false));
	}
	
	
	public static double measureExecutionTimeFor(Action action) {
		return measureExecutionTimeFor(action, getCallerMethodName(1, false, false));
	}
	
	public static double measureExecutionTimeFor(Action action, int times) {
		return measureExecutionTimeFor(action, getCallerMethodName(1, false, false), times);
	}
	
	public static double measureExecutionTimeFor(Action action, String tag) {
		return measureExecutionTimeFor(action, tag, 1);
	}
	
	public static double measureExecutionTimeFor(Action action, String tag, int times) {
		if (action == null) {
			throw new NullPointerException("action can't be 'null'.");
		}
		double result = 0;
		
		for (int t = 0; t < times; ++t) {
			long time = System.nanoTime();
			
			try {
				action.invoke();
			} catch (Throwable ignored) {
				break;
			} finally {
				result += (System.nanoTime() - time) / 1000000000d;
			}
		}
		StdOut.printf("Execution time for %s is: %.10fs\n", tag, result / times);
		
		return result;
	}
	
	
	private Utils() {
		/* Prevent instantiating */
	}
}
