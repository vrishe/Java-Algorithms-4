package expressions;

import org.junit.Test;
import static org.junit.Assert.*;

public class ExpressionTest {

	private static final class AdditionOperatorExpression extends BinaryOperatorExpression<Integer> {

		protected AdditionOperatorExpression() {
			super(Integer.class);
		}
		
		@Override
		public Integer apply(Integer[] params) {
			return params[0] + params[1];
		}
	}
	
	private static final class SubtractionOperatorExpression extends BinaryOperatorExpression<Integer> {

		protected SubtractionOperatorExpression() {
			super(Integer.class);
		}

		@Override
		public Integer apply(Integer[] params) {
			return params[0] - params[1];
		}
	}
	
	private static final class MultiplicationOperatorExpression extends BinaryOperatorExpression<Integer> {

		protected MultiplicationOperatorExpression() {
			super(Integer.class);
		}
		
		@Override
		public Integer apply(Integer[] params) {
			return params[0] * params[1];
		}
	}
	
	@SuppressWarnings("unused")
	private static final class DivisionOperatorExpression extends BinaryOperatorExpression<Integer> {

		protected DivisionOperatorExpression() {
			super(Integer.class);
		}
		
		@Override
		public Integer apply(Integer[] params) {
			return params[0] / params[1];
		}
	}
	
	private static final class ResultOperatorExpression extends UnaryOperatorExpression<Integer> {

		protected ResultOperatorExpression() {
			super(Integer.class);
		}
		
		@Override
		public Integer apply(Integer[] params) {
			return params[0];
		}
	}
	
	
	@Test
	public void generalTest() {
		final int result = 5 + (15 - 3 * 4) - 6;
		
		// Variant A
		ResultOperatorExpression variantA = new ResultOperatorExpression();
		{
			MultiplicationOperatorExpression multiplication = new MultiplicationOperatorExpression();
			{
				multiplication.setParameters(
					new GenericOperandExpression<Integer>(3), 
					new GenericOperandExpression<Integer>(4)
				);
			}
			SubtractionOperatorExpression subtraction0 = new SubtractionOperatorExpression();
			{
				subtraction0.setParameters(new GenericOperandExpression<Integer>(15), multiplication);
			}
			SubtractionOperatorExpression subtraction1 = new SubtractionOperatorExpression();
			{
				subtraction1.setParameters(subtraction0, new GenericOperandExpression<Integer>(6));
			}
			AdditionOperatorExpression addition = new AdditionOperatorExpression();
			{
				addition.setParameters(new GenericOperandExpression<Integer>(5), subtraction1);
			}
			variantA.setParameter(addition);
		}
		Expression<Integer> resultA = variantA.reduce();
		
		assertNotNull(resultA);
		assertEquals(resultA.getExpressionType(), ExpressionType.Operand);
		
		// Variant B
		ResultOperatorExpression variantB = new ResultOperatorExpression();
		{
			MultiplicationOperatorExpression multiplication = new MultiplicationOperatorExpression();
			{
				multiplication.setParameters(
					new GenericOperandExpression<Integer>(3), 
					new GenericOperandExpression<Integer>(4)
				);
			}
			SubtractionOperatorExpression subtraction0 = new SubtractionOperatorExpression();
			{
				subtraction0.setParameters(new GenericOperandExpression<Integer>(15), multiplication);
			}
			AdditionOperatorExpression addition = new AdditionOperatorExpression();
			{
				addition.setParameters(subtraction0, new GenericOperandExpression<Integer>(5));
			}
			SubtractionOperatorExpression subtraction1 = new SubtractionOperatorExpression();
			{
				subtraction1.setParameters(addition, new GenericOperandExpression<Integer>(6));
			}
			variantB.setParameter(subtraction1);
		}
		Expression<Integer> resultB = variantB.reduce();
		
		assertNotNull(resultB);
		assertEquals(resultB.getExpressionType(), ExpressionType.Operand);
		
		assertTrue(result == ((OperandExpression<Integer>)resultA).getValue() 
				&& result == ((OperandExpression<Integer>)resultB).getValue());
	}
}
