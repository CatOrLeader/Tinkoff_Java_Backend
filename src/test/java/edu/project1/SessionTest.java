package edu.project1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SessionTest {
    @Test
    @DisplayName("Increment mistakes counts")
    void incrementMistakes() {
        Session session = new Session(new Config());

        session.incrementMistake();
        int actualValue = session.getDoneMistakes();
        int expectedValue = 1;

        org.assertj.core.api.Assertions.assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Winning state")
    void winningState() {
        Session session = new Session(new Config());

        session.win();
        State actualState = session.getState();
        State expectedState = State.WIN;

        org.assertj.core.api.Assertions.assertThat(actualState).isEqualTo(expectedState);
    }

    @Test
    @DisplayName("Loosing state")
    void loosingState() {
        Session session = new Session(new Config());

        session.loose();
        State actualState = session.getState();
        State expectedState = State.LOOSE;

        org.assertj.core.api.Assertions.assertThat(actualState).isEqualTo(expectedState);
    }
}
