package designpatterns.strategy.staticliststrategy;

import java.util.List;
import java.util.function.Supplier;

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

class TextProcessor<LS extends ListStrategy> {
    private StringBuilder stringBuilder = new StringBuilder();
    private LS listStrategy;

    public TextProcessor(Supplier<? extends LS> ctor) {
        listStrategy = ctor.get();
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

public class StaticStrategy {
    public static void main(String[] args) {
        TextProcessor<MarkdownStrategy> textProcessor = new TextProcessor<>(MarkdownStrategy::new);
        textProcessor.appendList(List.of("alpha", "beta", "gamma"));
        System.out.println(textProcessor);

        TextProcessor<HtmlStrategy> textProcessor2 = new TextProcessor<>(HtmlStrategy::new);
        textProcessor2.appendList(List.of("alpha", "beta", "gamma"));
        System.out.println(textProcessor2);

    }
}
