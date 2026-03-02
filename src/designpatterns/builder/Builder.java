package designpatterns.builder;

import java.util.ArrayList;
import java.util.Collections;

class HtmlElement {
    private String name, text;
    private ArrayList<HtmlElement> elements = new ArrayList<>();

    private final int indentSize = 2;
    private final String newLine = System.lineSeparator();

    public HtmlElement() {
    }

    public HtmlElement(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public ArrayList<HtmlElement> getElements() {
        return elements;
    }

    private String toStringImpl(int indent) {
        StringBuilder stringBuilder = new StringBuilder();
        String i = String.join("", Collections.nCopies(indent * indentSize, " "));
        stringBuilder.append(String.format("%s<%s>%s", i, name, newLine));
        if(text != null && !text.isEmpty()) {
            stringBuilder.append(String.join("", Collections.nCopies(indentSize * (indent + 1), " ")))
                    .append(text)
                    .append(newLine);
        }
        for(HtmlElement e: elements) {
            stringBuilder.append(e.toStringImpl(indent + 1));
        }

        stringBuilder.append(String.format("%s</%s>%s", i, name, newLine));
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return toStringImpl(0);
    }


}

class HtmlBuilder {
    private String rootName;
    private HtmlElement root = new HtmlElement();

    public HtmlBuilder(String rootName) {
        this.rootName = rootName;
        root.setName(rootName);
    }

    public HtmlBuilder addChild(String childName, String childText) {
        HtmlElement element = new HtmlElement(childName, childText);
        root.getElements().add(element);
        return this;
    }

    public void clear() {
        root = new HtmlElement();
        root.setName(rootName);
    }

    @Override
    public String toString() {
        return root.toString();
    }
}

public class Builder {

    public static void main(String[] args) {
//        final String hello = "hello";
//        System.out.println("<p>" + hello + "</p>");
//
//        final String[] words = {"hello", "world"};
//        final StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("<ul>\n");
//        for(String word: words) {
//            stringBuilder.append(String.format("  <li>%s</li>\n", word));
//        }
//        stringBuilder.append("</ul>");
//
//        System.out.println(stringBuilder);
        HtmlBuilder builder = new HtmlBuilder("ul");
        builder
                .addChild("li", "hello")
                .addChild("li", "world");
        System.out.println(builder);
    }
}
