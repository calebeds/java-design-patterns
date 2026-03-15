package designpatterns.visitor.reflectivevisitor;

abstract class Expression {
}

class DoubleExpression extends Expression {
    private double value;

    public DoubleExpression(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}

class AdditionExpression extends Expression {
    private Expression left, right;

    public AdditionExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }
}

class ExpressionPrinter {
    public static void print(Expression expression, StringBuilder stringBuilder) {
        if(expression.getClass() == DoubleExpression.class) {
            stringBuilder.append(((DoubleExpression) expression).getValue());
        } else if(expression.getClass() == AdditionExpression.class) {
            AdditionExpression additionExpression = (AdditionExpression)  expression;
            stringBuilder.append("(");
            print(additionExpression.getLeft(), stringBuilder);
            stringBuilder.append("+");
            print(additionExpression.getRight(), stringBuilder);
            stringBuilder.append(")");
        }
    }
}

public class Reflective {
    public static void main(String[] args) {
        //1+(2+3)
        AdditionExpression expression = new AdditionExpression(
                new DoubleExpression(1),
                new AdditionExpression(
                        new DoubleExpression(2), new DoubleExpression(3)
                )
        );
        StringBuilder stringBuilder = new StringBuilder();
        ExpressionPrinter.print(expression, stringBuilder);
        System.out.println(stringBuilder);

    }
}
