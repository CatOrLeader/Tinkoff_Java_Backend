package edu.project1.text;

public class RusText implements Text {

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
            Привет! Это игра "Виселица", написанная на Java и реализованная в консоли. Спасибо, что решили
            поиграть. Удачи!
            """;
    }

    @Override
    public String languageChangedSuccessfully() {
        return "Язык успешно сменён. Слова будут загаданы на Русском языке";
    }

    @Override
    public String incorrectLanguageMsg() {
        return "Введенный язык неверен. Попробуйте повторно";
    }

    @Override
    public String incorrectLetterInputMsg() {
        return "Введённая буква неверна. Попробуйте повторно";
    }

    @Override
    public String chooseLangMsg() {
        return "Выберите удобный для вас язык (rus/eng)";
    }

    @Override
    public String iterGuessMsg() {
        return "Введите букву";
    }

    @Override
    public String guessWrongMsg() {
        return "Такой буквы в слове нет. Попробуйте еще раз";
    }

    @Override
    public String guessCorrectMsg() {
        return "В яблочко! Открываю...";
    }

    @Override
    public String iterStepMsg(String guessedWord, int mistake, int maxMistakes) {
        return "Слово: " + guessedWord + "\n Ошибки: " + mistake + " из " + maxMistakes;
    }

    @Override
    public String againMsg() {
        return "Хотите сыграть еще раз, (Y/N)";
    }

    @Override
    public String incorrectAgainMsg() {
        return "Введенное сообщение неерно. Попробуйте повторно";
    }

    @Override
    public String exitMsg() {
        return "Благодарю за игру! Буду ждать вашего возвращения";
    }

    @Override
    public String abnormalExitMsg() {
        return "Видимо, что-то пошло не так... Или вы нажали на Ctrl+D. "
               + exitMsg();
    }
}
