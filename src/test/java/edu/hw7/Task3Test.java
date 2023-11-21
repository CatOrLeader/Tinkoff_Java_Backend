package edu.hw7;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.hw7.Task3.Person;
import static edu.hw7.Task3.PersonDatabase;
import static edu.hw7.Task3.SynchronizedPersonDatabase;
import static org.assertj.core.api.Assertions.assertThat;

public class Task3Test {
    private static PersonDatabase DB;
    private static final List<Person> PERSONS = new ArrayList<>();

    @BeforeAll
    static void initialize() {
        DB = new SynchronizedPersonDatabase();
        Person person1 = new Person(1, "Olof", "Bohemian st.", "123");
        Person person2 = new Person(2, "Olof", "Lev st.", "1234");
        Person person3 = new Person(3, "Bjorn", "Bohemian st.", "123");

        PERSONS.addAll(List.of(person1, person2, person3));
    }

    @BeforeEach
    void fillDB() {
        PERSONS.forEach(person -> DB.add(person));
    }

    @AfterEach
    void clear() {
        PERSONS.forEach(person -> DB.delete(person.id()));
    }

    @Test
    @DisplayName("Single thread searching by name")
    void givenSingleThread_WhenFindByName() {
        List<Person> actualValue = DB.findByName("Olof");
        List<Person> expectedValue = List.of(
            new Person(1, "Olof", "Bohemian st.", "123"),
            new Person(2, "Olof", "Lev st.", "1234")
        );

        assertThat(actualValue).containsExactlyInAnyOrderElementsOf(expectedValue);
    }

    @Test
    @DisplayName("Single thread searching by address")
    void givenSingleThread_WhenFindByAddress() {
        List<Person> actualValue = DB.findByAddress("Bohemian st.");
        List<Person> expectedValue = List.of(
            new Person(1, "Olof", "Bohemian st.", "123"),
            new Person(3, "Bjorn", "Bohemian st.", "123")
        );

        assertThat(actualValue).containsExactlyInAnyOrderElementsOf(expectedValue);
    }

    @Test
    @DisplayName("Single thread searching by phone number")
    void givenSingleThread_WhenFindByPhoneNumber() {
        List<Person> actualValue = DB.findByPhone("123");
        List<Person> expectedValue = List.of(
            new Person(1, "Olof", "Bohemian st.", "123"),
            new Person(3, "Bjorn", "Bohemian st.", "123")
        );

        assertThat(actualValue).containsExactlyInAnyOrderElementsOf(expectedValue);
    }

    @Test
    @DisplayName("Single thread searching by address, no name")
    void givenSingleThread_WhenFindByName_NoGivenName() {
        String incorrectName = "non existence";

        assertThat(DB.findByName(incorrectName)).isNull();
    }

    @Test
    @DisplayName("Single thread searching by address, no address")
    void givenSingleThread_WhenFindByName_NoGivenAddress() {
        String incorrectName = "non existence";

        assertThat(DB.findByAddress(incorrectName)).isNull();
    }

    @Test
    @DisplayName("Single thread searching by address, no phone number")
    void givenSingleThread_WhenFindByName_NoGivenPhoneNumber() {
        String incorrectName = "non existence";

        assertThat(DB.findByPhone(incorrectName)).isNull();
    }

    @Test
    @DisplayName("Multithreading adding and searching by name")
    void multiThread_WhenAddingAndSearchingByName() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(100);
        clear();

        PERSONS.forEach(person -> service.submit(() -> DB.add(person)));
        service.awaitTermination(100, TimeUnit.MILLISECONDS);
        service.close();

        List<Person> actualValue = DB.findByName("Olof");
        List<Person> expectedValue = List.of(
            new Person(1, "Olof", "Bohemian st.", "123"),
            new Person(2, "Olof", "Lev st.", "1234")
        );

        assertThat(actualValue).containsExactlyInAnyOrderElementsOf(expectedValue);
    }

    @Test
    @DisplayName("Multithreading adding and searching by address")
    void multiThread_WhenAddingAndSearchingByAddress() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(100);
        clear();

        PERSONS.forEach(person -> service.submit(() -> DB.add(person)));
        service.awaitTermination(100, TimeUnit.MILLISECONDS);
        service.close();

        List<Person> actualValue = DB.findByAddress("Bohemian st.");
        List<Person> expectedValue = List.of(
            new Person(1, "Olof", "Bohemian st.", "123"),
            new Person(3, "Bjorn", "Bohemian st.", "123")
        );

        assertThat(actualValue).containsExactlyInAnyOrderElementsOf(expectedValue);
    }

    @Test
    @DisplayName("Multithreading adding and searching by phone number")
    void multiThread_WhenAddingAndSearchingByPhoneNumber() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(100);
        clear();

        PERSONS.forEach(person -> service.submit(() -> DB.add(person)));
        service.awaitTermination(100, TimeUnit.MILLISECONDS);
        service.close();

        List<Person> actualValue = DB.findByPhone("123");
        List<Person> expectedValue = List.of(
            new Person(1, "Olof", "Bohemian st.", "123"),
            new Person(3, "Bjorn", "Bohemian st.", "123")
        );

        assertThat(actualValue).containsExactlyInAnyOrderElementsOf(expectedValue);
    }

    @Test
    @DisplayName("Deleting the whole list of certain name")
    void singleThread_DeleteListByName_UsingID() {
        int id = 3;
        DB.delete(id);
        String name = "Bjorn";

        assertThat(DB.findByName(name)).isNull();
        DB.add(PERSONS.get(2));
    }

    @Test
    @DisplayName("Deleting the whole list of certain address")
    void singleThread_DeleteListByAddress_UsingID() {
        int id = 2;
        DB.delete(id);
        String street = "Lev st.";

        assertThat(DB.findByAddress(street)).isNull();
        DB.add(PERSONS.get(1));
    }

    @Test
    @DisplayName("Deleting the whole list of certain phone number")
    void singleThread_DeleteListByPhoneNumber_UsingID() {
        int id = 2;
        DB.delete(id);
        String phone = "1234";

        assertThat(DB.findByPhone(phone)).isNull();

        DB.add(PERSONS.get(1));
    }

    @Test
    @DisplayName("Single thread searching by name and not found by other field")
    void givenSingleThread_FindByNameWithoutOtherField() {
        String name = "Olof";
        DB.add(new Person(4, name, null, null));

        List<Person> actualValue = DB.findByName(name);
        List<Person> expectedValue = List.of(
            new Person(1, "Olof", "Bohemian st.", "123"),
            new Person(2, "Olof", "Lev st.", "1234")
        );

        DB.delete(4);

        assertThat(actualValue).containsExactlyInAnyOrderElementsOf(expectedValue);
    }

    @Test
    @DisplayName("Single thread searching by address and not found by other field")
    void givenSingleThread_FindByAddressWithoutOtherField() {
        String address = "Lev st.";
        DB.add(new Person(4, null, address, null));

        List<Person> actualValue = DB.findByAddress(address);
        List<Person> expectedValue = List.of(
            new Person(2, "Olof", "Lev st.", "1234")
        );

        DB.delete(4);

        assertThat(actualValue).containsExactlyInAnyOrderElementsOf(expectedValue);
    }

    @Test
    @DisplayName("Single thread searching by phone number and not found by other field")
    void givenSingleThread_FindByPhoneNumberWithoutOtherField() {
        String number = "1234";
        DB.add(new Person(4, null, null, number));

        List<Person> actualValue = DB.findByPhone(number);
        List<Person> expectedValue = List.of(
            new Person(2, "Olof", "Lev st.", "1234")
        );

        DB.delete(4);

        assertThat(actualValue).containsExactlyInAnyOrderElementsOf(expectedValue);
    }
}
