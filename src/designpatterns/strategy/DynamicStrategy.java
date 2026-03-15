package designpatterns.strategy;

import java.util.List;

enum OutputFormat {
    MARKDOWN, HTML
}

interface ListStrategy {
    default void start(StringBuilder stringBuilder) {}
    void addListItem(StringBuilder stringBuilder, String item);
    default void end(StringBuilder stringBuilder) {}
}

class MarkdownStrategy implements ListStrategy {

    @Override
    public void addListItem(StringBuilder stringBuilder, String item) {
        stringBuilder.append(" * ").append(item)
                .append(System.lineSeparator());
    }
}

class HtmlStrategy implements ListStrategy {

    @Override
    public void start(StringBuilder stringBuilder) {
        stringBuilder.append("<ul>")
                .append(System.lineSeparator());
    }

    @Override
    public void addListItem(StringBuilder stringBuilder, String item) {
        stringBuilder.append("  <li>")
                .append(item)
                .append("</li>")
                .append(System.lineSeparator());
    }

    @Override
    public void end(StringBuilder stringBuilder) {
        stringBuilder.append("<ul>")
                .append(System.lineSeparator());
    }
}

class TextProcessor {
    private StringBuilder stringBuilder = new StringBuilder();
    private ListStrategy listStrategy;

    public TextProcessor(OutputFormat format) {
        setOutputFormat(format);
    }

    public void setOutputFormat(OutputFormat format) {
        switch (format) {
            case MARKDOWN -> listStrategy = new MarkdownStrategy();
            case HTML -> listStrategy = new HtmlStrategy();
        }
    }

    public void appendList(List<String> items) {
        listStrategy.start(stringBuilder);
        for(String item: items) {
            listStrategy.addListItem(stringBuilder, item);
        }
        listStrategy.end(stringBuilder);
    }

    void clear() {
        stringBuilder.setLength(0);
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }
}

public class DynamicStrategy {
    public static void main(String[] args) {
        TextProcessor textProcessor = new TextProcessor(OutputFormat.MARKDOWN);
        textProcessor.appendList(List.of("liberte", "egalite", "fraternite"));
        System.out.println(textProcessor);

        textProcessor.clear();
        textProcessor.setOutputFormat(OutputFormat.HTML);
        textProcessor.appendList(List.of("inheritance", "encapsulation", "polymorphism", "abstraction"));
        System.out.println(textProcessor);
    }
}
