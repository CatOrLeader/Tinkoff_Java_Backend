package edu.project1;

import edu.project1.text.Language;
import edu.project1.text.RusText;
import edu.project1.text.Text;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.jetbrains.annotations.NotNull;

public class UserInputParser {
    private static final String EXIT_SYMBOL = "^D";
    private final Scanner scanner;

    public UserInputParser(@NotNull InputStream inputStream) {
        scanner = new Scanner(inputStream);
    }

    @NotNull public Text parseLang() throws ExitMessageProvided {
        String maybeLang = scanner.nextLine().trim();
        if (isExit(maybeLang)) {
            throw new ExitMessageProvided();
        }
        Text newText = Language.parseLang(maybeLang).getLanguageClass();
        if (newText == null) {
            throw new InputMismatchException("incorrect value of the language provided");
        }
        return newText;
    }

    @NotNull public String parseLetter(@NotNull Text text) throws ExitMessageProvided {
        String maybeLetter = scanner.nextLine().trim().toLowerCase();
        if (isExit(maybeLetter)) {
            throw new ExitMessageProvided();
        }
        String regex = (text instanceof RusText ? "[а-я]" : "[a-z]");
        if (!maybeLetter.matches(regex)) {
            throw new InputMismatchException("incorrect letter provided");
        }
        return maybeLetter;
    }

    public boolean parseAgainChoice() throws ExitMessageProvided {
        String maybeChoice = scanner.nextLine().trim().toUpperCase();
        if (isExit(maybeChoice)) {
            throw new ExitMessageProvided();
        }
        if (!maybeChoice.matches("[YN]")) {
            throw new InputMismatchException("incorrect choice provided");
        }
        return maybeChoice.equals("Y");
    }

    public boolean isExit(String str) {
        return str.equalsIgnoreCase(EXIT_SYMBOL);
    }
}
