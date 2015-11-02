package expressions;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public abstract class GenericOperatorExpression<T> extends OperatorExpression<T> {

	protected final Expression<T> children[];
	protected final Class<T> valueType;
	
	@SuppressWarnings("unchecked")
	protected GenericOperatorExpression(int rank, Class<T> valueType) {
		this.children = new Expression[rank];
		this.valueType = valueType;
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public Expression<T> reduce() {
		T[] result = (T[]) Array.newInstance(valueType, children.length);
		
		for (int i = 0; i < children.length; ++i) {
			Expression<T> child = children[i];
			
			while (child.getExpressionType() != ExpressionType.Operand) {
				child = child.reduce();
			}
			result[i] = ((OperandExpression<T>)child).getValue();
		}
		return new GenericOperandExpression<T>(apply(result));
	}
	

	@Override
	public int rank() {
		return children.length;
	}

	@Override
	public List<Expression<T>> getChildren() {
		return Arrays.asList(children);
	}
}
