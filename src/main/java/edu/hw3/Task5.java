package edu.hw3;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Task5 {
    private Task5() {
    }

    @NotNull
    public static List<String> parseContacts(String[] contacts, String type) {
        SortingType sortingType = SortingType.parseType(type);
        if (sortingType == null) {
            throw new InputMismatchException("incorrect sorting type provided");
        }
        if (contacts == null || contacts.length == 0) {
            return new ArrayList<>();
        }

        List<Initials> initials = new ArrayList<>();
        for (String str : contacts) {
            String[] strSplit = str.split(" ");
            if (strSplit.length > 2) {
                throw new InputMismatchException("incorrect initials for one of the contacts");
            }

            if (strSplit.length == 1) {
                initials.add(new Initials(strSplit[0], null));
            } else {
                initials.add(new Initials(strSplit[0], strSplit[1]));
            }
        }

        initials.sort(Initials::compareTo);
        if (sortingType == SortingType.DESC) {
            initials = initials.reversed();
        }

        return initials.stream().map(Objects::toString).toList();
    }

    enum SortingType {
        ASC("ASC"), DESC("DESC");
        private final String value;

        SortingType(String value) {
            this.value = value;
        }

        @Nullable
        public static SortingType parseType(@NotNull String type) {
            if (type.equals(ASC.value)) {
                return ASC;
            } else if (type.equals(DESC.value)) {
                return DESC;
            }

            return null;
        }
    }

    record Initials(String name, String surname) implements Comparable<Initials> {
        @Override
        public int compareTo(@NotNull Task5.Initials o) {
            String surnameSelf = (surname != null ? surname : name);
            String surnameOther = (o.surname != null ? o.surname : o.name);

            return surnameSelf.compareTo(surnameOther);
        }

        @Override
        public String toString() {
            if (surname == null) {
                return name;
            }
            return name + " " + surname;
        }
    }
}
