package edu.project1.text;

public interface Text {
    String greetingsMsg();

    String languageChangedSuccessfully();

    String incorrectLanguageMsg();

    String incorrectLetterInputMsg();

    String chooseLangMsg();

    String iterGuessMsg();

    String guessWrongMsg();

    String guessCorrectMsg();

    String iterStepMsg(String guessedWord, int mistake, int maxMistakes);

    default String winMsg() {
        return """

            ██████████████████████████████████████████████████████████
            █░░░░░░██████████░░░░░░█░░░░░░░░░░█░░░░░░██████████░░░░░░█
            █░░▄▀░░██████████░░▄▀░░█░░▄▀▄▀▄▀░░█░░▄▀░░░░░░░░░░██░░▄▀░░█
            █░░▄▀░░██████████░░▄▀░░█░░░░▄▀░░░░█░░▄▀▄▀▄▀▄▀▄▀░░██░░▄▀░░█
            █░░▄▀░░██████████░░▄▀░░███░░▄▀░░███░░▄▀░░░░░░▄▀░░██░░▄▀░░█
            █░░▄▀░░██░░░░░░██░░▄▀░░███░░▄▀░░███░░▄▀░░██░░▄▀░░██░░▄▀░░█
            █░░▄▀░░██░░▄▀░░██░░▄▀░░███░░▄▀░░███░░▄▀░░██░░▄▀░░██░░▄▀░░█
            █░░▄▀░░██░░▄▀░░██░░▄▀░░███░░▄▀░░███░░▄▀░░██░░▄▀░░██░░▄▀░░█
            █░░▄▀░░░░░░▄▀░░░░░░▄▀░░███░░▄▀░░███░░▄▀░░██░░▄▀░░░░░░▄▀░░█
            █░░▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀▄▀░░█░░░░▄▀░░░░█░░▄▀░░██░░▄▀▄▀▄▀▄▀▄▀░░█
            █░░▄▀░░░░░░▄▀░░░░░░▄▀░░█░░▄▀▄▀▄▀░░█░░▄▀░░██░░░░░░░░░░▄▀░░█
            █░░░░░░██░░░░░░██░░░░░░█░░░░░░░░░░█░░░░░░██████████░░░░░░█
            ██████████████████████████████████████████████████████████
            """;
    }

    default String looseMsg() {
        return """

            █████████████████████████████
            █▄─▄███─▄▄─█─▄▄─█─▄▄▄▄█▄─▄▄─█
            ██─██▀█─██─█─██─█▄▄▄▄─██─▄█▀█
            ▀▄▄▄▄▄▀▄▄▄▄▀▄▄▄▄▀▄▄▄▄▄▀▄▄▄▄▄▀
            """;
    }

    String againMsg();

    String incorrectAgainMsg();

    String exitMsg();

    String abnormalExitMsg();
}
