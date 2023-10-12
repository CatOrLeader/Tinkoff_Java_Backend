package edu.project1;

import edu.project1.text.EngText;
import edu.project1.text.Text;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class Config {
    public static final int MAX_MISTAKES = 5;

    private Dictionary dictionary;
    private Text text;

    public Config() {
        dictionary = Dictionary.defaultDictionary();
        this.text = new EngText();
    }

    public Config(Dictionary dictionary) {
        this.dictionary = dictionary;
        this.text = new EngText();
    }

    public void setLang(Text text) {
        this.text = text;
    }
}
