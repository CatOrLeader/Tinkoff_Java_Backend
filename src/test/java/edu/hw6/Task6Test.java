package edu.hw6;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw6.Task6.POSSIBLE_SERVICE;
import static org.assertj.core.api.Assertions.assertThat;

public class Task6Test {
    @Test
    @DisplayName("Check for output of all busy ports")
    void allBusyPortsOutput() {
        int[] ports = Task6.scanPorts();
        List<Integer> busy = new ArrayList<>();
        for (int i = 0; i < ports.length; i++) {
            if (ports[i] != 0) {
                busy.add(i);
            }
        }

        String actualOutput = Task6.prettyOutput(ports);

        for (Integer integer : busy) {
            if (POSSIBLE_SERVICE.get(integer) == null) {
                continue;
            }

            assertThat(actualOutput).contains(String.valueOf(integer));
        }
    }
}
