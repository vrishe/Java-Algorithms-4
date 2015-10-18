/*******************************************************************************
 * Copyright (c) 2013,2014 Rüdiger Herrmann, Sebastian Millies
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Rüdiger Herrmann - initial API and implementation
 * Sebastian Millies - increase type safety & simplify reflection by using enums
 *                     as conditions, simplify signatures by using TestRule, 
 *                     slight API change from IgnoreCondition#isSatisFied() to test()
 ******************************************************************************/
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Assume;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public final class ConditionalIgnoreRule implements TestRule {

	public interface IgnoreCondition {
		boolean test();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.METHOD })
	public @interface ConditionalIgnore {
		Class<? extends Enum<? extends IgnoreCondition>> from();
		String select();
	}

	@Override
	public Statement apply(Statement base, Description description) {
		Statement stmt = base;
		if (hasConditionalIgnoreAnnotation(description)) {
			IgnoreCondition condition = getIgnoreCondition(description);
			if (condition.test()) {
				stmt = new IgnoreStatement(condition);
			}
		}
		return stmt;
	}

	private static boolean hasConditionalIgnoreAnnotation(Description description) {
		return description.getAnnotation(ConditionalIgnore.class) != null;
	}

	private static IgnoreCondition getIgnoreCondition(Description description) {
		ConditionalIgnore annotation = description.getAnnotation(ConditionalIgnore.class);
		Class<? extends Enum<?>> clazz = annotation.from();
		try {
			Method meth = clazz.getMethod("valueOf", String.class);
			IgnoreCondition enumConst = (IgnoreCondition) meth.invoke(null,	annotation.select());
			return enumConst;
		} catch (IllegalAccessException | NoSuchMethodException	| SecurityException | InvocationTargetException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private static class IgnoreStatement extends Statement {
		private final IgnoreCondition condition;

		public IgnoreStatement(IgnoreCondition condition) {
			this.condition = condition;
		}

		@Override
		public void evaluate() {
			Assume.assumeTrue("Test ignored by " + condition, false);
		}
	}

}