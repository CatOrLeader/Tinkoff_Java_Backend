package edu.hw3;

import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Collections;
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
}
