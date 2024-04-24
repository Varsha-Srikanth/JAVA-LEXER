import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaLexer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your Java code:");
        String inputCode = scanner.nextLine();

        List<Token> tokens = tokenize(inputCode);

        for (Token token : tokens) {
            System.out.println(token);
        }
        scanner.close();
    }

    public static List<Token> tokenize(String inputCode) {
        List<Token> tokens = new ArrayList<>();

        String regex = "\\s*([a-zA-Z]+|[0-9]+|[+\\-*/=;()'\"])\\s*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inputCode);

        while (matcher.find()) {
            String tokenValue = matcher.group(1);
            TokenType tokenType = getTokenType(tokenValue);
            Token token = new Token(tokenType, tokenValue);
            tokens.add(token);
        }

        identifyDataTypes(tokens);

        return tokens;
    }

    public static TokenType getTokenType(String tokenValue) {
        if (tokenValue.matches("[a-zA-Z]+")) {
            return TokenType.IDENTIFIER;
        } else if (tokenValue.matches("[0-9]+")) {
            return TokenType.NUMBER;
        } else {
            switch (tokenValue) {
                case "/":
                    return TokenType.OPERATORS;
                case "*":
                    return TokenType.OPERATORS;
                case "+":
                    return TokenType.OPERATORS;
                case "-":
                    return TokenType.OPERATORS;
                case "=":
                    return TokenType.OPERATORS;
                case ";":
                    return TokenType.PUNCTUATORS;
                case "(":
                    return TokenType.OPERATORS;
                case ")":
                    return TokenType.OPERATORS;
                case "'":
                    return TokenType.PUNCTUATORS;
                case "\"":
                    return TokenType.PUNCTUATORS;
                default:
                    return TokenType.UNKNOWN;
            }
        }
    }

    private static void identifyDataTypes(List<Token> tokens) {
        for (int i = 0; i < tokens.size(); i++) {
            Token currentToken = tokens.get(i);
            if (currentToken.getType() == TokenType.IDENTIFIER) {
                DataType dataType = DataType.getDataType(currentToken.getValue());
                if (dataType != null) {
                    currentToken.setType(TokenType.DATA_TYPE);
                }
            }
        }
    }
}

enum TokenType {
    IDENTIFIER,
    NUMBER,
    OPERATORS,
    PUNCTUATORS,
    UNKNOWN,
    DATA_TYPE,
}

enum DataType {
    INT,
    FLOAT,
    DOUBLE,
    STRING,
    CHAR;

    public static DataType getDataType(String value) {
        for (DataType dataType : DataType.values()) {
            if (dataType.name().equalsIgnoreCase(value)) {
                return dataType;
            }
        }
        return null;
    }
}

class Token {
    private TokenType type;
    private String value;

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return "Token{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
