package edu.project1.text;

import java.util.InputMismatchException;
import org.jetbrains.annotations.Nullable;

public enum Language {
    RUS {
        @Override
        public Text getLanguageClass() {
            return new RusText();
        }
    },
    ENG {
        @Override
        public Text getLanguageClass() {
            return new EngText();
        }
    };

    public static Language parseLang(String str) throws InputMismatchException {
        if (str.equals("rus")) {
            return RUS;
        }

        if (str.equals("eng")) {
            return ENG;
        }

        throw new InputMismatchException();
    }

    @Nullable
    public Text getLanguageClass() {
        return null;
    }
}
