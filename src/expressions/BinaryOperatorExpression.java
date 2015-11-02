package expressions;

public abstract class BinaryOperatorExpression<T> extends GenericOperatorExpression<T> {

	public void setParameters(Expression<T> a, Expression<T> b) {
		children[0] = a;
		children[1] = b;
	}
	
	
	protected BinaryOperatorExpression(Class<T> valueType) {
		super(2, valueType);
	}
}
