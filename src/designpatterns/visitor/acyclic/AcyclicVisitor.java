package designpatterns.visitor.acyclic;

interface Visitor {} // marker interface

interface ExpressionVisitor extends Visitor {
    void visit(Expression expression);
}
interface DoubleExpressionVisitor extends Visitor {
    void visit(DoubleExpression expression);
}

interface AdditionExpressionVisitor extends Visitor {
    void visit(AdditionExpression expression);
}



abstract class Expression {
    public void accept(Visitor visitor) {
        if(visitor instanceof ExpressionVisitor expressionVisitor) {
            expressionVisitor.visit(this);
        }
    }
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
    public void accept(Visitor visitor) {
        if(visitor instanceof DoubleExpressionVisitor expressionVisitor) {
            expressionVisitor.visit(this);
        }
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
    public void accept(Visitor visitor) {
        if(visitor instanceof AdditionExpressionVisitor expressionVisitor) {
            expressionVisitor.visit(this);
        }
    }
}

class ExpressionPrinter implements DoubleExpressionVisitor, AdditionExpressionVisitor {
    private final StringBuilder stringBuilder = new StringBuilder();

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

public class AcyclicVisitor {
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
    }
}
