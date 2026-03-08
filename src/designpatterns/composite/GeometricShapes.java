package designpatterns.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class GraphicObject {
    protected String name = "Group";
    protected String color;
    private List<GraphicObject> children = new ArrayList<>();

    public GraphicObject() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<GraphicObject> getChildren() {
        return children;
    }

    private void print(StringBuilder stringBuilder, int depth) {
        stringBuilder.append(String.join("", Collections.nCopies(depth, "*")))
                .append(depth > 0 ? " " : "")
                .append((color == null || color.isEmpty()) ? "" : color + " ")
                .append(getName())
                .append(System.lineSeparator());

        for (GraphicObject child: children) {
            child.print(stringBuilder, depth + 1);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        print(stringBuilder, 0);
        return stringBuilder.toString();
    }

}

class Circle extends GraphicObject {
    public Circle(String color) {
        name = "Circle";
        this.color = color;
    }
}
class Square extends GraphicObject {
    public Square(String color) {
        name = "Square";
        this.color = color;
    }
}


public class GeometricShapes {
    public static void main(String[] args) {
        GraphicObject drawing = new GraphicObject();
        drawing.setName("My Drawing");
        drawing.getChildren().add(new Square("Red"));
        drawing.getChildren().add(new Circle("Yellow"));

        GraphicObject group = new GraphicObject();
        group.getChildren().add(new Circle("Blue"));
        group.getChildren().add(new Square("Blue"));

        drawing.getChildren().add(group);

        System.out.println(drawing);
    }
}
