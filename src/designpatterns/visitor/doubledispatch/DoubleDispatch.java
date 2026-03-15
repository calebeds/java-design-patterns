package designpatterns.visitor.doubledispatch;

interface ExpressionVisitor {
    void visit(DoubleExpression doubleExpression);
    void visit(AdditionExpression additionExpression);
}

abstract class Expression {
    public abstract void accept(ExpressionVisitor visitor);
}

class DoubleExpression extends Expression {
    private double value;

    public DoubleExpression(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
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

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }
}

class ExpressionPrinter implements ExpressionVisitor {
    private StringBuilder stringBuilder = new StringBuilder();


    @Override
    public void visit(DoubleExpression doubleExpression) {
        stringBuilder.append(doubleExpression.getValue());
    }

    @Override
    public void visit(AdditionExpression additionExpression) {
        stringBuilder.append("(");
        additionExpression.getLeft().accept(this);
        stringBuilder.append("+");
        additionExpression.getRight().accept(this);
        stringBuilder.append(")");
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }
}

class ExpressionCalculator implements ExpressionVisitor {
    private double result;

    @Override
    public void visit(DoubleExpression doubleExpression) {
        result = doubleExpression.getValue();
    }

    @Override
    public void visit(AdditionExpression additionExpression) {
        additionExpression.getLeft().accept(this);
        double a = result;
        additionExpression.getRight().accept(this);
        double b = result;
        result = a + b;
    }

    public double getResult() {
        return result;
    }
}

public class DoubleDispatch {
    public static void main(String[] args) {
        //1+(2+3)
        AdditionExpression expression = new AdditionExpression(
                new DoubleExpression(1),
                new AdditionExpression(
                        new DoubleExpression(2), new DoubleExpression(3)
                )
        );
        ExpressionPrinter expressionPrinter = new ExpressionPrinter();
        expressionPrinter.visit(expression);
        System.out.println(expressionPrinter);

        ExpressionCalculator expressionCalculator = new ExpressionCalculator();
        expressionCalculator.visit(expression);

        System.out.println(expressionPrinter + " = " + expressionCalculator.getResult());

    }
}
