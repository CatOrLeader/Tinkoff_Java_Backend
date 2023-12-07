package edu.hw6;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Task2 {
    private Task2() {
    }

    private static final int OFFSET = 2;
    private static final String EXTENSION_SEP = "\\.";

    public static void cloneFile(Path path) throws IOException {
        if (!path.toFile().isFile()) {
            throw new IOException("It is not the file provided");
        }

        File current = path.toFile();
        File[] siblings =
            new File(getFolder(current))
                .listFiles((dir, name) -> name.startsWith(getFilenameWithoutExtension(current)));

        if (siblings == null) {
            throw new IOException("Incorrect folder/file provided");
        }

        if (siblings.length == 1) {
            createFIle(Path.of(createFilename(current, 1)));
            return;
        }

        List<Integer> copyIndexes = new ArrayList<>();
        for (File sibling : siblings) {
            String siblingName = sibling.getName();
            String[] siblingNameSeparated = siblingName.split(EXTENSION_SEP);
            String siblingNameBeforeExtension = siblingNameSeparated[siblingNameSeparated.length - OFFSET];

            if (!isCopy(siblingName)) {
                continue;
            }

            if (isFirstCopy(siblingName)) {
                copyIndexes.add(1);
                continue;
            }

            String copyIndexStr = siblingNameBeforeExtension.substring(
                siblingNameBeforeExtension.lastIndexOf("(") + 1,
                siblingNameBeforeExtension.lastIndexOf(")")
            );
            int copyIndex = Integer.parseInt(copyIndexStr);
            copyIndexes.add(copyIndex);
        }

        int currentCopyIndex = getMissedCopyNumber(copyIndexes);

        Path destination = Path.of(createFilename(current, currentCopyIndex));
        createFIle(destination);
        copyContent(path, destination);
    }

    private static int getMissedCopyNumber(List<Integer> arr) {
        Collections.sort(arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i) - arr.get(i - 1) != 1) {
                return arr.get(i - 1) + 1;
            }
        }
        return arr.getLast() + 1;
    }

    private static void createFIle(Path file) throws IOException {
        Files.createFile(
            file
        );
    }

    private static void copyContent(Path src, Path dest) throws IOException {
        try (
            BufferedReader srcReader = new BufferedReader(new FileReader(src.toString()));
            BufferedWriter destWriter = new BufferedWriter(new FileWriter(dest.toString()))
        ) {
            for (String str : srcReader.lines().toList()) {
                destWriter.write(str);
            }
        } catch (FileNotFoundException srcEx) {
            throw new IOException("Incorrect source file provided");
        } catch (IOException ex) {
            throw new IOException("Something went wrong while writing in the destination file");
        }
    }

    private static String createFilename(File current, int copyIndex) {
        return getFolder(current)
               + getFilenameWithoutExtension(current)
               + getCopyTemplate(copyIndex)
               + getExtension(current);
    }

    private static String getCopyTemplate(int copyIndex) {
        return " - copy" + (copyIndex == 1 ? "" : " (" + copyIndex + ")");
    }

    private static String getFolder(File file) {
        String absPath = file.getAbsolutePath();
        String filename = file.getName();
        return absPath.substring(0, absPath.length() - filename.length());
    }

    private static String getFilenameWithoutExtension(File file) {
        String filename = file.getName();
        if (!filename.contains(".")) {
            return filename;
        }
        return filename.substring(0, filename.lastIndexOf("."));
    }

    private static String getExtension(File file) {
        String filename = file.getName();
        if (!filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }

    private static boolean isCopy(String filename) {
        return filename.matches(".*copy( \\([0-9]+\\))?\\..*");
    }

    private static boolean isFirstCopy(String filename) {
        return isCopy(filename) && !filename.contains("(");
    }
}
