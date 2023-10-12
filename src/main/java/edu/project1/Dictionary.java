package edu.project1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.jetbrains.annotations.NotNull;

public final class Dictionary {
    private final ArrayList<String> dictionary;
    private final Random random;

    public Dictionary(@NotNull ArrayList<String> dictionary) {
        this.dictionary = dictionary;
        random = new Random();
    }

    public static Dictionary defaultDictionary() {
        ArrayList<String> dict = new ArrayList<>(
            List.of(new String[] {"Tinkoff", "Java", "Backend"})
        );

        return new Dictionary(dict);
    }

    @NotNull
    public String randomWord() {
        return dictionary.get(random.nextInt(0, dictionary.size()));
    }
}
