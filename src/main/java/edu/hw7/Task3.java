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
        private final HashMap<Integer, Person> byId;
        private final HashMap<String, List<Person>> byName;
        private final HashMap<String, List<Person>> byAddress;
        private final HashMap<String, List<Person>> byPhoneNumber;

        public SynchronizedPersonDatabase(
        ) {
            this.byId = new HashMap<>();
            this.byName = new HashMap<>();
            this.byAddress = new HashMap<>();
            this.byPhoneNumber = new HashMap<>();
        }

        @Override
        public synchronized void add(@NotNull Person person) {
            byId.put(person.id(), person);

            if (person.name() != null) {
                if (!byName.containsKey(person.name())) {
                    byName.put(person.name(), new ArrayList<>());
                }
                byName.get(person.name()).add(person);
            }

            if (person.address() != null) {
                if (!byAddress.containsKey(person.address())) {
                    byAddress.put(person.address(), new ArrayList<>());
                }
                byAddress.get(person.address()).add(person);
            }

            if (person.phoneNumber() != null) {
                if (!byPhoneNumber.containsKey(person.phoneNumber())) {
                    byPhoneNumber.put(person.phoneNumber(), new ArrayList<>());
                }
                byPhoneNumber.get(person.phoneNumber()).add(person);
            }
        }

        @Override
        public synchronized void delete(int id) {
            Person person = byId.get(id);
            byId.remove(id);

            if (person.name() != null) {
                byName.get(person.name()).remove(person);
                if (byName.get(person.name()).isEmpty()) {
                    byName.remove(person.name());
                }
            }

            if (person.address() != null) {
                byAddress.get(person.address()).remove(person);
                if (byAddress.get(person.address()).isEmpty()) {
                    byAddress.remove(person.address());
                }
            }

            if (person.phoneNumber() != null) {
                byPhoneNumber.get(person.phoneNumber()).remove(person);
                if (byPhoneNumber.get(person.phoneNumber()).isEmpty()) {
                    byPhoneNumber.remove(person.phoneNumber());
                }
            }
        }

        @Override
        public @Nullable List<Person> findByName(@NotNull String name) {
            List<Person> persons = byName.get(name);
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
            List<Person> persons = byAddress.get(address);
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
            List<Person> persons = byPhoneNumber.get(phone);
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
