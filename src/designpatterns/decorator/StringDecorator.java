package designpatterns.decorator;

import java.io.UnsupportedEncodingException;
import java.lang.invoke.MethodHandles;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class MagicString {
    private String string;

    public MagicString(String string) {
        this.string = string;
    }

    public long getNumberOfVowels() {
        return string.chars()
                .mapToObj(c -> (char)c)
                .filter(character -> "aeiou".contains(character.toString()))
                .count();

    }

    /// /////////////////////////////////////////string
    public int length() {
        return string.length();
    }

    public int indexOf(String str, int beginIndex, int endIndex) {
        return string.indexOf(str, beginIndex, endIndex);
    }

    public String replace(CharSequence target, CharSequence replacement) {
        return string.replace(target, replacement);
    }

    public IntStream codePoints() {
        return string.codePoints();
    }

    @Deprecated(since = "1.1")
    public void getBytes(int srcBegin, int srcEnd, byte[] dst, int dstBegin) {
        string.getBytes(srcBegin, srcEnd, dst, dstBegin);
    }

    public int indexOf(int ch) {
        return string.indexOf(ch);
    }

    public int compareToIgnoreCase(String str) {
        return string.compareToIgnoreCase(str);
    }

    public boolean equalsIgnoreCase(String anotherString) {
        return string.equalsIgnoreCase(anotherString);
    }

    public boolean endsWith(String suffix) {
        return string.endsWith(suffix);
    }

    public String substring(int beginIndex) {
        return string.substring(beginIndex);
    }

    public String stripLeading() {
        return string.stripLeading();
    }

    public Optional<String> describeConstable() {
        return string.describeConstable();
    }

    public boolean isEmpty() {
        return string.isEmpty();
    }

    public int codePointAt(int index) {
        return string.codePointAt(index);
    }

    public String[] splitWithDelimiters(String regex, int limit) {
        return string.splitWithDelimiters(regex, limit);
    }

    public int indexOf(int ch, int beginIndex, int endIndex) {
        return string.indexOf(ch, beginIndex, endIndex);
    }

    public byte[] getBytes(String charsetName) throws UnsupportedEncodingException {
        return string.getBytes(charsetName);
    }

    public String indent(int n) {
        return string.indent(n);
    }

    public byte[] getBytes() {
        return string.getBytes();
    }

    public String intern() {
        return string.intern();
    }

    public CharSequence subSequence(int beginIndex, int endIndex) {
        return string.subSequence(beginIndex, endIndex);
    }

    public boolean isBlank() {
        return string.isBlank();
    }

    public int codePointCount(int beginIndex, int endIndex) {
        return string.codePointCount(beginIndex, endIndex);
    }

    public String formatted(Object... args) {
        return string.formatted(args);
    }

    public int lastIndexOf(int ch, int fromIndex) {
        return string.lastIndexOf(ch, fromIndex);
    }

    public String toLowerCase() {
        return string.toLowerCase();
    }

    public int lastIndexOf(String str) {
        return string.lastIndexOf(str);
    }

    public boolean regionMatches(boolean ignoreCase, int toffset, String other, int ooffset, int len) {
        return string.regionMatches(ignoreCase, toffset, other, ooffset, len);
    }

    public String replace(char oldChar, char newChar) {
        return string.replace(oldChar, newChar);
    }

    public boolean contentEquals(StringBuffer sb) {
        return string.contentEquals(sb);
    }

    public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
        string.getChars(srcBegin, srcEnd, dst, dstBegin);
    }

    public boolean startsWith(String prefix) {
        return string.startsWith(prefix);
    }

    public <R> R transform(Function<? super String, ? extends R> f) {
        return string.transform(f);
    }

    public int indexOf(String str, int fromIndex) {
        return string.indexOf(str, fromIndex);
    }

    public boolean contains(CharSequence s) {
        return string.contains(s);
    }

    public String toUpperCase() {
        return string.toUpperCase();
    }

    public boolean contentEquals(CharSequence cs) {
        return string.contentEquals(cs);
    }

    public int compareTo(String anotherString) {
        return string.compareTo(anotherString);
    }

    public String substring(int beginIndex, int endIndex) {
        return string.substring(beginIndex, endIndex);
    }

    public IntStream chars() {
        return string.chars();
    }

    public String stripTrailing() {
        return string.stripTrailing();
    }

    public char charAt(int index) {
        return string.charAt(index);
    }

    public String replaceAll(String regex, String replacement) {
        return string.replaceAll(regex, replacement);
    }

    public String strip() {
        return string.strip();
    }

    public String resolveConstantDesc(MethodHandles.Lookup lookup) {
        return string.resolveConstantDesc(lookup);
    }

    public String concat(String str) {
        return string.concat(str);
    }

    public String[] split(String regex, int limit) {
        return string.split(regex, limit);
    }

    public Stream<String> lines() {
        return string.lines();
    }

    public char[] toCharArray() {
        return string.toCharArray();
    }

    public int codePointBefore(int index) {
        return string.codePointBefore(index);
    }

    public boolean regionMatches(int toffset, String other, int ooffset, int len) {
        return string.regionMatches(toffset, other, ooffset, len);
    }

    public int indexOf(int ch, int fromIndex) {
        return string.indexOf(ch, fromIndex);
    }

    public String repeat(int count) {
        return string.repeat(count);
    }

    public byte[] getBytes(Charset charset) {
        return string.getBytes(charset);
    }

    public int lastIndexOf(int ch) {
        return string.lastIndexOf(ch);
    }

    public boolean matches(String regex) {
        return string.matches(regex);
    }

    public int lastIndexOf(String str, int fromIndex) {
        return string.lastIndexOf(str, fromIndex);
    }

    public String stripIndent() {
        return string.stripIndent();
    }

    public String toLowerCase(Locale locale) {
        return string.toLowerCase(locale);
    }

    public int offsetByCodePoints(int index, int codePointOffset) {
        return string.offsetByCodePoints(index, codePointOffset);
    }

    public int indexOf(String str) {
        return string.indexOf(str);
    }

    public String trim() {
        return string.trim();
    }

    public String replaceFirst(String regex, String replacement) {
        return string.replaceFirst(regex, replacement);
    }

    public String[] split(String regex) {
        return string.split(regex);
    }

    public boolean startsWith(String prefix, int toffset) {
        return string.startsWith(prefix, toffset);
    }

    public String translateEscapes() {
        return string.translateEscapes();
    }

    public String toUpperCase(Locale locale) {
        return string.toUpperCase(locale);
    }

    @Override
    public String toString() {
        return string;
    }
}

public class StringDecorator {
    public static void main(String[] args) {
        MagicString string = new MagicString("hello");
        System.out.println(string + " has " + string.getNumberOfVowels() + " vowels");
    }
}
