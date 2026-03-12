package designpatterns.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

interface Element {
    int eval();
}

class Integer implements Element {

    private final int value;

    public Integer(int value) {
        this.value = value;
    }

    @Override
    public int eval() {
        return value;
    }
}

class BinaryOperation implements Element {
    public enum Type {
        ADDITION,
        SUBSTRACTION
    }

    private Type type;
    private Element left, right;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Element getLeft() {
        return left;
    }

    public void setLeft(Element left) {
        this.left = left;
    }

    public Element getRight() {
        return right;
    }

    public void setRight(Element right) {
        this.right = right;
    }

    @Override
    public int eval() {
        return switch (type) {
            case ADDITION -> left.eval() + right.eval();
            case SUBSTRACTION -> left.eval() - right.eval();
            default -> 0;
        };
    }
}

class Token {
    public enum Type {
        INTEGER,
        PLUS,
        MINUS,
        LPAREN,
        RPAREN
    }

    private Type type;
    private String text;

    public Token(Type type, String text) {
        this.type = type;
        this.text = text;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "`" + text + "`";
    }
}

public class Interpreter {
    static Element parse(List<Token> tokens) {
        BinaryOperation result = new BinaryOperation();
        boolean haveLHS = false;

        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);

            switch (token.getType()) {
                case INTEGER -> {
                    Integer integer = new Integer(java.lang.Integer.parseInt(token.getText()));
                    if(!haveLHS) {
                        result.setLeft(integer);
                        haveLHS = true;
                    } else {
                        result.setRight(integer);
                    }
                }
                case PLUS -> {
                    result.setType(BinaryOperation.Type.ADDITION);
                }
                case MINUS -> {
                    result.setType(BinaryOperation.Type.SUBSTRACTION);
                }
                case LPAREN -> {
                    int j = i + 1;
                    int parenCount = 1;

                    while (j < tokens.size() && parenCount > 0) {
                        if(tokens.get(j).getType() == Token.Type.LPAREN) {
                            parenCount++;
                        } else if(tokens.get(j).getType() == Token.Type.RPAREN) {
                            parenCount--;
                        }

                        if(parenCount > 0) {
                            j++;
                        }
                    }

                    if(j >= tokens.size()) {
                        throw new RuntimeException("Mismatched parentheses");
                    }

                    List<Token> subexpression = tokens.subList(i + 1, j);

                    Element element = parse(subexpression);

                    if(!haveLHS) {
                        result.setLeft(element);
                        haveLHS = true;
                    } else {
                        result.setRight(element);
                    }

                    i = j;
                }
            }
        }
        return result;
    }

    static List<Token> lex(String input) {
        ArrayList<Token> result = new ArrayList<>();

        for (int i = 0; i < input.length(); i++) {
            switch (input.charAt(i)) {
                case '+' -> result.add(new Token(Token.Type.PLUS, "+"));
                case '-' -> result.add(new Token(Token.Type.MINUS, "-"));
                case '(' -> result.add(new Token(Token.Type.LPAREN, "("));
                case ')' -> result.add(new Token(Token.Type.RPAREN, ")"));
                default -> {
                    StringBuilder stringBuilder = new StringBuilder("" + input.charAt(i));
                    for(int j = i + 1; j < input.length(); ++j) {
                        if(Character.isDigit(input.charAt(j))) {
                            stringBuilder.append(input.charAt(j));
                            ++i;
                        } else {
                            result.add(new Token(Token.Type.INTEGER, stringBuilder.toString()));
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String input = "(13+4)-(12+1)";
        List<Token> tokens = lex(input);
        System.out.println(tokens.stream()
                .map(token -> token.toString())
                .collect(Collectors.joining("\t")));

        Element parsed = parse(tokens);
        System.out.println(input + " = " + parsed.eval());
    }
}
