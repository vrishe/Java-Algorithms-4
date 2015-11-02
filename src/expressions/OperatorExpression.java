package expressions;

public abstract class OperatorExpression<T> implements Expression<T> {
	
	public abstract T apply(T[] params);
	
	public abstract int rank();
	
	@Override
	public ExpressionType getExpressionType() {
		return ExpressionType.Operator;
	}
}
