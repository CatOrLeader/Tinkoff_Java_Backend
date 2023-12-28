package edu.hw7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Task3and5 {
    private Task3and5() {
    }

    public static final class SynchronizedPersonDatabaseBasedOnReadWriteLock implements Task3.PersonDatabase {
        private final Lock writeLock;
        private final Lock readLock;

        private final HashMap<Integer, Task3.Person> byId;
        private final HashMap<String, List<Task3.Person>> byName;
        private final HashMap<String, List<Task3.Person>> byAddress;
        private final HashMap<String, List<Task3.Person>> byPhoneNumber;

        public SynchronizedPersonDatabaseBasedOnReadWriteLock() {
            this.byId = new HashMap<>();
            this.byName = new HashMap<>();
            this.byAddress = new HashMap<>();
            this.byPhoneNumber = new HashMap<>();
            ReadWriteLock lock = new ReentrantReadWriteLock();
            writeLock = lock.writeLock();
            readLock = lock.readLock();
        }

        @Override
        public void add(@NotNull Task3.Person person) {
            writeLock.lock();
            try {
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
            } finally {
                writeLock.unlock();
            }
        }

        @Override
        public void delete(int id) {
            writeLock.lock();

            try {
                Task3.Person person = byId.get(id);
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
            } finally {
                writeLock.unlock();
            }
        }

        @Override
        public @Nullable List<Task3.Person> findByName(@NotNull String name) {
            readLock.lock();
            List<Task3.Person> foundPersons = new ArrayList<>();

            try {
                List<Task3.Person> persons = byName.get(name);
                if (persons == null) {
                    return null;
                }

                for (Task3.Person person : persons) {
                    if (person.address() == null || person.phoneNumber() == null) {
                        continue;
                    }

                    foundPersons.add(person);
                }
            } finally {
                readLock.unlock();
            }

            return foundPersons;
        }

        @Override
        public @Nullable List<Task3.Person> findByAddress(@NotNull String address) {
            readLock.lock();
            List<Task3.Person> foundPersons = new ArrayList<>();

            try {
                List<Task3.Person> persons = byAddress.get(address);
                if (persons == null) {
                    return null;
                }

                for (Task3.Person person : persons) {
                    if (person.name() == null || person.phoneNumber() == null) {
                        continue;
                    }

                    foundPersons.add(person);
                }

            } finally {
                readLock.unlock();
            }

            return foundPersons;
        }

        @Override
        public @Nullable List<Task3.Person> findByPhone(@NotNull String phone) {
            readLock.lock();
            List<Task3.Person> foundPersons = new ArrayList<>();

            try {
                List<Task3.Person> persons = byPhoneNumber.get(phone);
                if (persons == null) {
                    return null;
                }

                for (Task3.Person person : persons) {
                    if (person.address() == null || person.name() == null) {
                        continue;
                    }

                    foundPersons.add(person);
                }

            } finally {
                readLock.unlock();
            }

            return foundPersons;
        }
    }
}
