package edu.hw7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Task3 {
    private Task3() {
    }

    public static final class SynchronizedPersonDatabase implements PersonDatabase {
        private static final HashMap<Integer, Person> BY_ID = new HashMap<>();
        private static final HashMap<String, List<Person>> BY_NAME = new HashMap<>();
        private static final HashMap<String, List<Person>> BY_ADDRESS = new HashMap<>();
        private static final HashMap<String, List<Person>> BY_PHONE_NUMBER = new HashMap<>();

        @Override
        public synchronized void add(@NotNull Person person) {
            BY_ID.put(person.id(), person);

            if (person.name() != null) {
                if (!BY_NAME.containsKey(person.name())) {
                    BY_NAME.put(person.name(), new ArrayList<>());
                }
                BY_NAME.get(person.name()).add(person);
            }

            if (person.address() != null) {
                if (!BY_ADDRESS.containsKey(person.address())) {
                    BY_ADDRESS.put(person.address(), new ArrayList<>());
                }
                BY_ADDRESS.get(person.address()).add(person);
            }

            if (person.phoneNumber() != null) {
                if (!BY_PHONE_NUMBER.containsKey(person.phoneNumber())) {
                    BY_PHONE_NUMBER.put(person.phoneNumber(), new ArrayList<>());
                }
                BY_PHONE_NUMBER.get(person.phoneNumber()).add(person);
            }
        }

        @Override
        public synchronized void delete(int id) {
            Person person = BY_ID.get(id);
            BY_ID.remove(id);

            if (person.name() != null) {
                BY_NAME.get(person.name()).remove(person);
                if (BY_NAME.get(person.name()).isEmpty()) {
                    BY_NAME.remove(person.name());
                }
            }

            if (person.address() != null) {
                BY_ADDRESS.get(person.address()).remove(person);
                if (BY_ADDRESS.get(person.address()).isEmpty()) {
                    BY_ADDRESS.remove(person.address());
                }
            }

            if (person.phoneNumber() != null) {
                BY_PHONE_NUMBER.get(person.phoneNumber()).remove(person);
                if (BY_PHONE_NUMBER.get(person.phoneNumber()).isEmpty()) {
                    BY_PHONE_NUMBER.remove(person.phoneNumber());
                }
            }
        }

        @Override
        public @Nullable List<Person> findByName(@NotNull String name) {
            List<Person> persons = BY_NAME.get(name);
            if (persons == null) {
                return null;
            }

            List<Person> foundPersons = new ArrayList<>();
            for (Person person : persons) {
                if (person.address() == null || person.phoneNumber() == null) {
                    continue;
                }

                foundPersons.add(person);
            }

            return foundPersons;
        }

        @Override
        public @Nullable List<Person> findByAddress(@NotNull String address) {
            List<Person> persons = BY_ADDRESS.get(address);
            if (persons == null) {
                return null;
            }

            List<Person> foundPersons = new ArrayList<>();
            for (Person person : persons) {
                if (person.name() == null || person.phoneNumber() == null) {
                    continue;
                }

                foundPersons.add(person);
            }

            return foundPersons;
        }

        @Override
        public @Nullable List<Person> findByPhone(@NotNull String phone) {
            List<Person> persons = BY_PHONE_NUMBER.get(phone);
            if (persons == null) {
                return null;
            }

            List<Person> foundPersons = new ArrayList<>();
            for (Person person : persons) {
                if (person.address() == null || person.name() == null) {
                    continue;
                }

                foundPersons.add(person);
            }

            return foundPersons;
        }
    }

    public record Person(int id, String name, String address, String phoneNumber) {
    }

    interface PersonDatabase {
        void add(@NotNull Person person);

        void delete(int id);

        @Nullable List<Person> findByName(@NotNull String name);

        @Nullable List<Person> findByAddress(@NotNull String address);

        @Nullable List<Person> findByPhone(@NotNull String phone);
    }
}
