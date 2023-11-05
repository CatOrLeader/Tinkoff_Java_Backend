package edu.hw3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw3.Task6.Stock;
import static edu.hw3.Task6.StockMarket;
import static org.assertj.core.api.Assertions.assertThat;

public class Task6Test {
    private StockMarket market;

    @BeforeEach void fillMarket() {
        market = new StockMarket();
        market.add(new Stock(125));
        market.add(new Stock(500));
        market.add(new Stock(1000));
    }

    @Test
    @DisplayName("Get most valuable stock w/out deletions")
    void getMostValuable_withoutDel() {
        Stock actualValue = market.mostValuableStock();
        Stock expectedValue = new Stock(1000);

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Get most valuable stock w/ one deletion")
    void getMostValuable_oneDel() {
        market.remove(market.mostValuableStock());

        Stock actualValue = market.mostValuableStock();
        Stock expectedValue = new Stock(500);

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Get most valuable stock w/ two deletions")
    void getMostValuable_twoDeletions() {
        market.remove(market.mostValuableStock());
        market.remove(market.mostValuableStock());

        Stock actualValue = market.mostValuableStock();
        Stock expectedValue = new Stock(125);

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Get null when queue is empty")
    void getNull() {
        market.remove(market.mostValuableStock());
        market.remove(market.mostValuableStock());
        market.remove(market.mostValuableStock());

        Stock actualValue = market.mostValuableStock();

        assertThat(actualValue).isNull();
    }

    @Test
    @DisplayName("Add most valuable stock and get it, w/out deletions")
    void addMostVal_getMostValuable_withoutDel() {
        market.add(new Stock(1500));

        Stock actualValue = market.mostValuableStock();
        Stock expectedValue = new Stock(1500);

        assertThat(actualValue).isEqualTo(expectedValue);
    }
}
