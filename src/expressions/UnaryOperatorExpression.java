package expressions;

import java.lang.reflect.Array;

public abstract class UnaryOperatorExpression<T> extends GenericOperatorExpression<T> {

	public void setParameter(Expression<T> param) {
		children[0] = param;
	}
	
	
	protected UnaryOperatorExpression(Class<T> valueType) {
		super(1, valueType);
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public Expression<T> reduce() {
		Expression<T> child = children[0];
		
		while (child.getExpressionType() != ExpressionType.Operand) {
			child = child.reduce();
		}
		T result[] = (T[])Array.newInstance(valueType, children.length);
		{
			result[0] = ((OperandExpression<T>)child).getValue();
		}
		return new GenericOperandExpression<T>(apply(result));
	}
}
