package edu.hw3;

import java.util.ArrayList;
import java.util.InputMismatchException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task2Test {
    @Test
    @DisplayName("No nested parenthesis")
    void noNestedParenthesis() {
        String string = "()()()";

        ArrayList<String> actualClusters = Task2.clusterize(string);
        ArrayList<String> expectedCluster = new ArrayList<>();
        expectedCluster.add("()"); expectedCluster.add("()"); expectedCluster.add("()");

        assertThat(actualClusters).containsExactlyElementsOf(expectedCluster);
    }

    @Test
    @DisplayName("Only nested parenthesis")
    void onlyNestedParenthesis() {
        String string = "((()))";

        ArrayList<String> actualClusters = Task2.clusterize(string);
        ArrayList<String> expectedCluster = new ArrayList<>();
        expectedCluster.add("((()))");

        assertThat(actualClusters).containsExactlyElementsOf(expectedCluster);
    }

    @Test
    @DisplayName("Mixed string input")
    void mixedStringInput() {
        String string = "((()))(())()()(()())";

        ArrayList<String> actualClusters = Task2.clusterize(string);
        ArrayList<String> expectedCluster = new ArrayList<>();
        expectedCluster.add("((()))"); expectedCluster.add("(())");
        expectedCluster.add("()"); expectedCluster.add("()"); expectedCluster.add("(()())");

        assertThat(actualClusters).containsExactlyElementsOf(expectedCluster);
    }

    @Test
    @DisplayName("Two nested constructions")
    void twoNestedConstructions() {
        String string = "((())())(()(()()))";

        ArrayList<String> actualClusters = Task2.clusterize(string);
        ArrayList<String> expectedCluster = new ArrayList<>();
        expectedCluster.add("((())())"); expectedCluster.add("(()(()()))");

        assertThat(actualClusters).containsExactlyElementsOf(expectedCluster);
    }

    @Test
    @DisplayName("Empty string")
    void emptyString() {
        String string = "";

        ArrayList<String> actualClusters = Task2.clusterize(string);
        ArrayList<String> expectedCluster = new ArrayList<>();

        assertThat(actualClusters).containsExactlyElementsOf(expectedCluster);
    }

    @Test
    @DisplayName("No parenthesis string")
    void noParenthesisString() {
        String string = "bccsdf";

        ArrayList<String> actualClusters = Task2.clusterize(string);
        ArrayList<String> expectedCluster = new ArrayList<>();

        assertThat(actualClusters).containsExactlyElementsOf(expectedCluster);
    }

    @Test
    @DisplayName("With symbols")
    void withSymbols() {
        String string = "dsa(asd(asd(aabd)asd)dsa)asd";

        ArrayList<String> actualClusters = Task2.clusterize(string);
        ArrayList<String> expectedCluster = new ArrayList<>();
        expectedCluster.add("((()))");

        assertThat(actualClusters).containsExactlyElementsOf(expectedCluster);
    }

    @Test
    @DisplayName("No balance of parenthesis")
    void noBalanceOfParenthesis() {
        String string = "((()";

        Assertions.assertThrows(InputMismatchException.class, () -> Task2.clusterize(string));
    }
}
