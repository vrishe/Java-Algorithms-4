package expressions;

import java.util.List;

public abstract class OperandExpression<T> implements Expression<T> {
	
	public abstract T getValue();
	
	@Override
	public ExpressionType getExpressionType() {
		return ExpressionType.Operand;
	}

	@Override
	public List<Expression<T>> getChildren() {
		throw new UnsupportedOperationException("Operand has no children.");
	}

	@Override
	public Expression<T>  reduce() {
		return this;
	}
}
