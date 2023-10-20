package edu.project1.text;

public class EngText implements Text {
    @Override
    public String greetingsMsg() {
        return """

            ██╗░░██╗░█████╗░███╗░░██╗░██████╗░███╗░░░███╗░█████╗░███╗░░██╗      ░██████╗░░█████╗░███╗░░░███╗███████╗
            ██║░░██║██╔══██╗████╗░██║██╔════╝░████╗░████║██╔══██╗████╗░██║      ██╔════╝░██╔══██╗████╗░████║██╔════╝
            ███████║███████║██╔██╗██║██║░░██╗░██╔████╔██║███████║██╔██╗██║      ██║░░██╗░███████║██╔████╔██║█████╗░░
            ██╔══██║██╔══██║██║╚████║██║░░╚██╗██║╚██╔╝██║██╔══██║██║╚████║      ██║░░╚██╗██╔══██║██║╚██╔╝██║██╔══╝░░
            ██║░░██║██║░░██║██║░╚███║╚██████╔╝██║░╚═╝░██║██║░░██║██║░╚███║      ╚██████╔╝██║░░██║██║░╚═╝░██║███████╗
            ╚═╝░░╚═╝╚═╝░░╚═╝╚═╝░░╚══╝░╚═════╝░╚═╝░░░░░╚═╝╚═╝░░╚═╝╚═╝░░╚══╝      ░╚═════╝░╚═╝░░╚═╝╚═╝░░░░░╚═╝╚══════╝
            \n
            Hello! This is a "Hangman Game" on the console, written in Java. Thank you for paying attention to this.
            Hoping you'll enjoy
            """;
    }

    @Override
    public String languageChangedSuccessfully() {
        return "Language is changed successfully. Words will be riddled in the English";
    }

    @Override
    public String incorrectLanguageMsg() {
        return "Your input is incorrect! Try again and choose the appropriate language for you";
    }

    @Override
    public String incorrectLetterInputMsg() {
        return "Your input is incorrect! Try again and write letter again";
    }

    @Override
    public String chooseLangMsg() {
        return "Input the language that is more handy to you (rus/eng)";
    }

    @Override
    public String iterGuessMsg() {
        return "Guess a letter";
    }

    @Override
    public String guessWrongMsg() {
        return "Letter is incorrect, try again";
    }

    @Override
    public String guessCorrectMsg() {
        return "You hit the bull's-eye! Correct";
    }

    @Override
    public String iterStepMsg(String guessedWord, int mistake, int maxMistakes) {
        return "The word: " + guessedWord + "\nMistakes: " + mistake + " out of " + maxMistakes;
    }

    @Override
    public String againMsg() {
        return "Would you like to try again? (Y/N)";
    }

    @Override
    public String incorrectAgainMsg() {
        return "Your input is incorrect!";
    }

    @Override
    public String exitMsg() {
        return "Bye! I will wait for you ( ͡° ͜ʖ ͡°)";
    }

    @Override
    public String abnormalExitMsg() {
        return "Something went wrong! Or maybe you just push the Ctrl+D buttons\n" + exitMsg();
    }
}
