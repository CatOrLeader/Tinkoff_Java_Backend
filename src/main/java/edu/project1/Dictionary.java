package edu.project1;

import edu.project1.text.EngText;
import edu.project1.text.Text;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.jetbrains.annotations.NotNull;

public final class Dictionary {
    private final ArrayList<String> dictionary;
    private final Random random;
    private int lastChosenWord;

    public Dictionary(@NotNull ArrayList<String> dictionary) {
        this.dictionary = dictionary;
        random = new Random();
    }

    public static Dictionary defaultDictionary(Text text) {
        ArrayList<String> dict;
        if (text instanceof EngText) {
            dict = new ArrayList<>(
                List.of(new String[] {"tinkoff", "java", "backend"})
            );
        } else {
            dict = new ArrayList<>(
                List.of(new String[] {"тинькофф", "джава", "бэкенд"})
            );
        }

        return new Dictionary(dict);
    }

    @NotNull
    public String randomWord() {
        lastChosenWord = random.nextInt(0, dictionary.size());
        return dictionary.get(lastChosenWord);
    }

    @NotNull public String getLastChosenWord() {
        return dictionary.get(lastChosenWord);
    }
}
