
enum TestConditions implements ConditionalIgnoreRule.IgnoreCondition {
	RUN_SHORT_TESTS_ONLY() {
		@Override
		public boolean test() {
			return !Boolean.parseBoolean(System.getenv("RUN_LONG_TESTS"));
		}
	},
    RUNNING_UNDER_WINDOWS() {
        @Override
        public boolean test() {
            return System.getProperty("os.name").startsWith("Windows");
        }
    }
}