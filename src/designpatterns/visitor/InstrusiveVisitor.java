package designpatterns.visitor;

abstract class Expression {
    abstract void print(StringBuilder stringBuilder);
}

class DoubleExpression extends Expression {
    private double value;

    public DoubleExpression(double value) {
        this.value = value;
    }

    @Override
    void print(StringBuilder stringBuilder) {
        stringBuilder.append(value);
    }
}

class AdditionExpression extends Expression {
    private Expression left, right;

    public AdditionExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    void print(StringBuilder stringBuilder) {
        stringBuilder.append("(");
        left.print(stringBuilder);
        stringBuilder.append("+");
        right.print(stringBuilder);
        stringBuilder.append(")");
    }
}

public class InstrusiveVisitor {
    public static void main(String[] args) {
        //1+(2+3)
        AdditionExpression expression = new AdditionExpression(
                new DoubleExpression(1),
                new AdditionExpression(
                        new DoubleExpression(2), new DoubleExpression(3)
                )
        );

        StringBuilder stringBuilder = new StringBuilder();
        expression.print(stringBuilder);
        System.out.println(stringBuilder);
    }
}
