package expressions;

public class GenericOperandExpression<T> extends OperandExpression<T> {
	
	private final T value;
	
	public T getValue() {
		return value;
	}

	public GenericOperandExpression(T value) {
		this.value = value;
	}
}
