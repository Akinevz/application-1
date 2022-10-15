package com.akinevz.demo1.model;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * A quick and dirty parser that converts the [Inshur lat/long: 50.824955973889, -0.13878781625840952] representation
 * to a POJO
 */
public class GeoCoordinateParser {
    private final List<Token> tokens;
    private final StringBuilder stringBuilder;
    private ParserState state;

    private int index;

    public GeoCoordinateParser(String source) {
        if (source.isBlank()) throw new IllegalStateException("Geo info must be in [name:lat,lon] format");
        // we want to separate on word boundary, colon, and comma
        // https://regex101.com/r/5DlQ4o/1
        // the pattern below represents breaks before/after the following [:,] and opening and closing brackets
        Scanner scanner = new Scanner(source).useDelimiter("(?<=\\[)|(?=])|(?<=:)|(?=:)|(?<=,)|(?=,)");

        this.state = ParserState.INITIALIZED;
        // tokenise the entire scanner
        this.tokens = scanner.tokens().map(this::tokenize).toList();
        // used for building Text tokens that get chunked
        this.stringBuilder = new StringBuilder();
        // a little old school
        this.index = 0;
    }

    /**
     * Utility method to convert each character to their representative token
     * @param s token string
     * @return token object
     */
    private Token tokenize(String s) {
        // I wanted to play around with the new features. this project requires java 18
        // nb: they are nice
        var trimmed = s.trim();
        return switch (trimmed) {
            case "[" -> new OpeningBrace();
            case "]" -> new ClosingBrace();
            case ":" -> new Colon();
            case "," -> new Comma();
            case String otherwise -> new Text(otherwise);
        };
    }

    private enum ParserState {
        INITIALIZED, OPEN_BRACE, NAME_READ, LAT_READ, SEP_READ, LON_READ, CLOSING_BRACE
    }

    public Optional<String> descriptor() {
        if (state != ParserState.INITIALIZED) return Optional.empty();
        do {
            // eager
            var token = tokens.get(index++);
            switch (token) {
                case OpeningBrace ignored && state == ParserState.INITIALIZED -> state = ParserState.OPEN_BRACE;
                case Text text -> stringBuilder.append(text.content);
                case Colon ignored -> state = ParserState.NAME_READ;
                case default -> throw new IllegalStateException("Unexpected value in label position: " + token);
            }
        } while (state != ParserState.NAME_READ);
        var label = stringBuilder.toString();
        stringBuilder.setLength(0);
        return Optional.of(label);
    }

    public Optional<String> lat() {
        if (state != ParserState.NAME_READ && state != ParserState.OPEN_BRACE) return Optional.empty();

        var token = tokens.get(index++);
        if (token instanceof Text n) {
            state = ParserState.LAT_READ;
            return Optional.of(n.content);
        }
        throw new IllegalStateException("Unexpected value in lat position: " + token);
    }

    public Optional<String> lon() {
        if (state != ParserState.LAT_READ) return Optional.empty();
        var token = tokens.get(index++);
        if (token instanceof Comma) {
            state = ParserState.SEP_READ;
        }
        if (state != ParserState.SEP_READ) return Optional.empty();
        var last = tokens.get(index++);
        if (last instanceof Text n) {
            state = ParserState.LON_READ;
            return Optional.of(n.content);
        }
        throw new IllegalStateException("Unexpected value in lon position: " + last);
    }

    interface Token {
    }

    interface Brace extends Token {
    }

    static class OpeningBrace implements Brace {
    }

    static class ClosingBrace implements Brace {
    }

    static class Text implements Token {
        private final String content;

        public Text(String content) {
            this.content = content;
        }
    }

    static class Colon implements Token {
    }

    static class Comma implements Token {

    }

}
