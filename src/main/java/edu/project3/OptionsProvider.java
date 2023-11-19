package edu.project3;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public final class OptionsProvider {
    private OptionsProvider() {
    }

    public static final Option PATH_OPTION = new Option("p", "path", true, "path to the log file(s)");
    public static final Option FROM_OPTION = new Option("f",  "from", true, "log from specific date");
    public static final Option TO_OPTION = new Option("t", "to", true, "log until the specific date");
    public static final Option FORMAT_OPTION = new Option("ft", "format", true, "format of output");

    public static Options options() {
        return new Options()
            .addOption(PATH_OPTION)
            .addOption(FROM_OPTION)
            .addOption(TO_OPTION)
            .addOption(FORMAT_OPTION);
    }
}
