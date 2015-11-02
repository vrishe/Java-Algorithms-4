package expressions;

public abstract class TernaryOperatorExpression<T> extends GenericOperatorExpression<T> {

	public void setParameters(Expression<T> a, Expression<T> b, Expression<T> c) {
		children[0] = a;
		children[1] = b;
		children[2] = c;
	}
	
	
	protected TernaryOperatorExpression(Class<T> valueType) {
		super(3, valueType);
	}
}
