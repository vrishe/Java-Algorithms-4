package expressions;

import java.util.List;

public interface Expression<T> {

	ExpressionType getExpressionType();
	
	List<Expression<T>> getChildren();
	
	
	Expression<T> reduce();
}
