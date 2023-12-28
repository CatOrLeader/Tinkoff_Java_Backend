package edu.hw4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static edu.hw4.Animal.Sex.F;
import static edu.hw4.Animal.Sex.M;
import static edu.hw4.Animal.Type.BIRD;
import static edu.hw4.Animal.Type.CAT;
import static edu.hw4.Animal.Type.DOG;
import static edu.hw4.Animal.Type.FISH;
import static edu.hw4.Animal.Type.SPIDER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class TasksTest {
    private static List<Animal> animals;

    @NotNull private static Set<Map.Entry<String, Set<ValidationError>>> preapreExpectedEntrySet() {
        ValidationError incorrectAge = new IncorrectAgeError();
        ValidationError incorrectWeight = new IncorrectWeightError();
        ValidationError incorrectHeight = new IncorrectHeightError();
        ValidationError incorrectBitePossibility = new IncorrectBitePossibilityError();
        return Map.of(
            "Jack", Set.of(incorrectAge),
            "Meowka", Set.of(incorrectHeight),
            "Tarantul", Set.of(incorrectWeight),
            "Jo", Set.of(incorrectBitePossibility),
            "Crow", Set.of(new IncorrectAgeError(), new IncorrectHeightError(), new IncorrectWeightError())
        ).entrySet();
    }

    // -------------------------------------------TASK 1---------------------------------------------------

    @BeforeEach void fillList() {
        animals = List.of(
            new Animal("Jack", DOG, M, 7, 110, 15, true),
            new Animal("Meowka", CAT, F, 4, 55, 4, true),
            new Animal("Tarantul", SPIDER, M, 1, 5, 1, false),
            new Animal("Jo", FISH, M, 2, 10, 1, false),
            new Animal("Crow", BIRD, F, 3, 35, 2, false)
        );
    }

    @Test
    @Tag("task1")
    @DisplayName("Get animals sorted by their height, ascending")
    void getAnimalsSortedByHeight_asc() {
        List<Animal> actualList = Tasks.sortByHeightAscending(animals);
        List<Animal> expectedList = List.of(
            animals.get(2), animals.get(3), animals.get(4), animals.get(1), animals.get(0)
        );

        assertThat(actualList).containsExactlyElementsOf(expectedList);
    }

    // -------------------------------------------TASK 2---------------------------------------------------

    @Test
    @Tag("task1")
    @DisplayName("Get animals sorted by their height, ascending; Empty List provided")
    void getAnimalsSortedByHeight_asc_emptyListProvided() {
        List<Animal> actualList = Tasks.sortByHeightAscending(new ArrayList<>());
        List<Animal> expectedList = List.of();

        assertThat(actualList).containsExactlyElementsOf(expectedList);
    }

    @Test
    @Tag("task2")
    @DisplayName("Get animals sorted by their weight, descending")
    void getAnimalSortedByWeight_desc() {
        List<Animal> actualList = Tasks.sortByWeightDescending(animals);
        List<Animal> expectedList = List.of(
            animals.get(0), animals.get(1), animals.get(4), animals.get(2), animals.get(3)
        );

        assertThat(actualList).containsExactlyElementsOf(expectedList);
    }

    @Test
    @Tag("task2")
    @DisplayName("Get animals sorted by their weight, descending; Empty List provided")
    void getAnimalSortedByWeight_desc_emptyListProvided() {
        List<Animal> actualList = Tasks.sortByWeightDescending(new ArrayList<>());
        List<Animal> expectedList = new ArrayList<>();

        assertThat(actualList).containsExactlyElementsOf(expectedList);
    }

    // -------------------------------------------TASK 3---------------------------------------------------
    @Test
    @Tag("task3")
    @DisplayName("Get frequency dictionary of animal types")
    void getAnimalTypeFrequencyDictionary() {
        List<Animal> animalsUpd = new ArrayList<>(animals);
        animalsUpd.add(new Animal("Bob", DOG, M, 12, 132, 20, false));

        Map<Animal.Type, Integer> map = Tasks.getAnimalsTypeFreqDict(animalsUpd);
        Set<Map.Entry<Animal.Type, Integer>> actualSet = map.entrySet();
        Set<Map.Entry<Animal.Type, Integer>> expectedSet = Map.of(
            DOG, 2,
            CAT, 1,
            FISH, 1,
            SPIDER, 1,
            BIRD, 1
        ).entrySet();

        assertThat(actualSet).containsExactlyInAnyOrderElementsOf(expectedSet);
    }

    @Test
    @Tag("task3")
    @DisplayName("Get frequency dictionary of animal types; Empty List provided")
    void getAnimalTypeFrequencyDictionary_emptyListProvided() {
        List<Animal> animalsUpd = new ArrayList<>();

        Map<Animal.Type, Integer> map = Tasks.getAnimalsTypeFreqDict(animalsUpd);
        Set<Map.Entry<Animal.Type, Integer>> actualSet = map.entrySet();
        Set<Map.Entry<Animal.Type, Integer>> expectedSet = new HashSet<>();

        assertThat(actualSet).containsExactlyInAnyOrderElementsOf(expectedSet);
    }

    // -------------------------------------------TASK 4---------------------------------------------------
    @Test
    @Tag("task4")
    @DisplayName("Get longest named animal")
    void getAnimalWithLongestName() {
        Animal actualAnimal = Tasks.getLongestName(animals);
        Animal expectedAnimal = animals.get(2);

        assertThat(actualAnimal).isEqualTo(expectedAnimal);
    }

    @Test
    @Tag("task4")
    @DisplayName("Get longest named animal; Empty List provided")
    void getAnimalWithLongestName_emptyListProvided() {
        Animal actualAnimal = Tasks.getLongestName(new ArrayList<>());

        assertThat(actualAnimal).isNull();
    }

    // -------------------------------------------TASK 5---------------------------------------------------
    @Test
    @Tag("task5")
    @DisplayName("Get largest by frequency animals' Sex")
    void getMostTimesAppearedSex() {
        Animal.Sex actualType = Tasks.getMostAppearedSex(animals);

        assertThat(actualType).isEqualTo(M);
    }

    @Test
    @Tag("task5")
    @DisplayName("Get largest by frequency animals' Sex, another list")
    void getMostTimesAppearedSex_anotherList() {
        List<Animal> animalsUpd = new ArrayList<>(animals);
        animalsUpd.remove(2);
        animalsUpd.remove(2);
        animalsUpd.add(new Animal("Bob", DOG, F, 12, 132, 20, false));

        Animal.Sex actualType = Tasks.getMostAppearedSex(animalsUpd);

        assertThat(actualType).isEqualTo(F);
    }

    @Test
    @Tag("task5")
    @DisplayName("Get largest by frequency animals' Sex, another list; Empty List provided")
    void getMostTimesAppearedSex_emptyListProvided() {
        List<Animal> animalsUpd = new ArrayList<>();

        Assertions.assertThrows(NoSuchElementException.class, () -> Tasks.getMostAppearedSex(animalsUpd));
    }

    // -------------------------------------------TASK 6---------------------------------------------------
    @Test
    @Tag("task6")
    @DisplayName("Get heaviest animal for each type")
    void getHeaviestAnimalEachType() {
        Map<Animal.Type, Animal> map = Tasks.getHeaviestAnimalEachType(animals);
        Set<Map.Entry<Animal.Type, Animal>> actualSet = map.entrySet();
        Set<Map.Entry<Animal.Type, Animal>> expectedSet = Map.of(
            DOG, animals.get(0),
            CAT, animals.get(1),
            FISH, animals.get(3),
            SPIDER, animals.get(2),
            BIRD, animals.get(4)
        ).entrySet();

        assertThat(actualSet).containsExactlyInAnyOrderElementsOf(expectedSet);
    }

    @Test
    @Tag("task6")
    @DisplayName("Get heaviest animal for each type; Empty List provided")
    void getHeaviestAnimalEachType_emptyListProvided() {
        List<Animal> animalsUpd = new ArrayList<>();

        Map<Animal.Type, Animal> map = Tasks.getHeaviestAnimalEachType(animalsUpd);
        Set<Map.Entry<Animal.Type, Animal>> actualSet = map.entrySet();
        Set<Map.Entry<Animal.Type, Animal>> expectedSet = new HashSet<>();

        assertThat(actualSet).containsExactlyInAnyOrderElementsOf(expectedSet);
    }

    // -------------------------------------------TASK 7---------------------------------------------------
    @Test
    @Tag("task7")
    @DisplayName("Get oldest animal")
    void getOldestAnimal() {
        Animal actualAnimal = Tasks.getOldestAnimal(animals, 1);
        Animal expectedAnimal = animals.get(0);

        assertThat(actualAnimal).isEqualTo(expectedAnimal);
    }

    @Test
    @Tag("task7")
    @DisplayName("Get third oldest animal")
    void getThirdOldestAnimal() {
        Animal actualAnimal = Tasks.getOldestAnimal(animals, 3);
        Animal expectedAnimal = animals.get(4);

        assertThat(actualAnimal).isEqualTo(expectedAnimal);
    }

    @Test
    @Tag("task7")
    @DisplayName("Get oldest animal; Empty List Provided")
    void getOldestAnimal_emptyListProvided() {
        List<Animal> animalsUpd = new ArrayList<>();

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> Tasks.getOldestAnimal(animalsUpd, 0));
    }

    @Test
    @Tag("task7")
    @DisplayName("Get oldest animal; Incorrect k provided")
    void getOldestAnimal_incorrectProvidedK() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> Tasks.getOldestAnimal(animals, 10));
    }

    // -------------------------------------------TASK 8---------------------------------------------------
    @Test
    @Tag("task8")
    @DisplayName("Get heaviest animal with height lower than k")
    void getOldestAnimal_lowerThanK() {
        int k = 500;

        Optional<Animal> actualAnimal = Tasks.getHeaviestAnimalLowerThanK(animals, k);
        Optional<Animal> expectedAnimal = Optional.of(animals.get(0));

        assertThat(actualAnimal).isEqualTo(expectedAnimal);
    }

    @Test
    @Tag("task8")
    @DisplayName("Get heaviest animal with height lower than k; k = 0")
    void getOldestAnimal_lowerThanK_kIsZero() {
        int k = 0;

        Optional<Animal> actualAnimal = Tasks.getHeaviestAnimalLowerThanK(animals, k);

        assertThat(actualAnimal.isPresent()).isFalse();
    }

    @Test
    @Tag("task8")
    @DisplayName("Get heaviest animal with height lower than k; Empty List provided")
    void getOldestAnimal_lowerThanK_emptyListProvided() {
        List<Animal> animalsUpd = new ArrayList<>();
        int k = 500;

        Optional<Animal> actualAnimal = Tasks.getHeaviestAnimalLowerThanK(animalsUpd, k);

        assertThat(actualAnimal.isPresent()).isFalse();
    }

    // -------------------------------------------TASK 9---------------------------------------------------
    @Test
    @Tag("task9")
    @DisplayName("Get number of paws")
    void getNumberOfPaws() {
        int actualValue = Tasks.getCountOfPaws(animals);
        int expectedValue = 18;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @Tag("task9")
    @DisplayName("Get number of paws; Empty List provided")
    void getNumberOfPaws_emptyListProvided() {
        List<Animal> animalsUpd = new ArrayList<>();

        int actualValue = Tasks.getCountOfPaws(animalsUpd);
        int expectedValue = 0;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    // -------------------------------------------TASK 10---------------------------------------------------
    @Test
    @Tag("task10")
    @DisplayName("Get animals for which number of paws != age")
    void getUnequalAgeAndPawsAnimals() {
        List<Animal> actualAnimals = Tasks.getMismatchAgePawsAnimals(animals);
        List<Animal> expectedAnimals = List.of(
            animals.get(0), animals.get(2), animals.get(3), animals.get(4)
        );

        assertThat(actualAnimals).containsExactlyInAnyOrderElementsOf(expectedAnimals);
    }

    @Test
    @Tag("task10")
    @DisplayName("Get animals for which number of paws != age; Empty List provided")
    void getUnequalAgeAndPawsAnimals_emptyListProvided() {
        List<Animal> animalsUpd = new ArrayList<>();

        List<Animal> actualAnimals = Tasks.getMismatchAgePawsAnimals(animalsUpd);
        List<Animal> expectedAnimals = List.of();

        assertThat(actualAnimals).containsExactlyInAnyOrderElementsOf(expectedAnimals);
    }

    // -------------------------------------------TASK 11---------------------------------------------------
    @Test
    @Tag("task11")
    @DisplayName("Get animals which can bite and higher than 100")
    void getAnimalsCanBiteAndHigherThan100() {
        List<Animal> actualAnimals = Tasks.getAnimalsCanBiteAndHigherThan100(animals);
        List<Animal> expectedAnimals = List.of(
            animals.get(0)
        );

        assertThat(actualAnimals).containsExactlyInAnyOrderElementsOf(expectedAnimals);
    }

    @Test
    @Tag("task11")
    @DisplayName("Get animals which can bite and higher than 100; Empty List provided")
    void getAnimalsCanBiteAndHigherThan100_emptyListProvided() {
        List<Animal> animalsUpd = new ArrayList<>();

        List<Animal> actualAnimals = Tasks.getAnimalsCanBiteAndHigherThan100(animalsUpd);
        List<Animal> expectedAnimals = List.of();

        assertThat(actualAnimals).containsExactlyInAnyOrderElementsOf(expectedAnimals);
    }

    // -------------------------------------------TASK 12---------------------------------------------------
    @Test
    @Tag("task12")
    @DisplayName("Get animals which weight is larger than height")
    void getAnimalsWeightLargerThanHeight() {
        List<Animal> animalsUpd = new ArrayList<>(animals);
        animalsUpd.add(new Animal("Lol", BIRD, M, 5, 30, 40, false));

        int actualValue = Tasks.getAnimalsWeightLargerThanHeight(animalsUpd);
        int expectedValue = 1;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @Tag("task12")
    @DisplayName("Get animals which weight is larger than height; Empty List provided")
    void getAnimalsWeightLargerThanHeight_emptyListProvided() {
        List<Animal> animalsUpd = new ArrayList<>();

        int actualValue = Tasks.getAnimalsWeightLargerThanHeight(animalsUpd);
        int expectedValue = 0;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    // -------------------------------------------TASK 13---------------------------------------------------
    @Test
    @Tag("task13")
    @DisplayName("Get animals which names contain 2 or more words, zero animals")
    void getAnimalsWithNameContains2OrMoreWords_zeroAnimals() {
        List<Animal> actualAnimals = Tasks.getAnimalsNamesContainTwoOrMoreWords(animals);
        List<Animal> expectedAnimals = new ArrayList<>();

        assertThat(actualAnimals).isEqualTo(expectedAnimals);
    }

    @Test
    @Tag("task13")
    @DisplayName("Get animals which names contain 2 or more words")
    void getAnimalsWithNameContains2OrMoreWords() {
        List<Animal> animalsUpd = new ArrayList<>(animals);
        animalsUpd.add(new Animal("Lol Lol", BIRD, M, 5, 30, 40, false));

        List<Animal> actualAnimals = Tasks.getAnimalsNamesContainTwoOrMoreWords(animalsUpd);
        List<Animal> expectedAnimals = List.of(
            animalsUpd.get(5)
        );

        assertThat(actualAnimals).isEqualTo(expectedAnimals);
    }

    @Test
    @Tag("task13")
    @DisplayName("Get animals which names contain 2 or more words; Empty List provided")
    void getAnimalsWithNameContains2OrMoreWords_emptyListProvided() {
        List<Animal> animalsUpd = new ArrayList<>();

        List<Animal> actualAnimals = Tasks.getAnimalsNamesContainTwoOrMoreWords(animalsUpd);
        List<Animal> expectedAnimals = List.of();

        assertThat(actualAnimals).isEqualTo(expectedAnimals);
    }

    // -------------------------------------------TASK 14---------------------------------------------------
    @Test
    @Tag("task14")
    @DisplayName("Is list contains dog higher than k, true")
    void isContainsDogHigherThanK_true() {
        int k = 50;

        boolean actualValue = Tasks.isContainsDogHigherThanK(animals, k);

        assertThat(actualValue).isTrue();
    }

    @Test
    @Tag("task14")
    @DisplayName("Is list contains dog higher than k, false")
    void isContainsDogHigherThanK_false() {
        int k = 5000;

        boolean actualValue = Tasks.isContainsDogHigherThanK(animals, k);

        assertThat(actualValue).isFalse();
    }

    @Test
    @Tag("task14")
    @DisplayName("Is list contains dog higher than k; Empty List provided")
    void isContainsDogHigherThanK_emptyListProvided() {
        int k = 0;
        List<Animal> animalsUpd = new ArrayList<>();

        boolean actualValue = Tasks.isContainsDogHigherThanK(animalsUpd, k);

        assertThat(actualValue).isFalse();
    }

    // -------------------------------------------TASK 15---------------------------------------------------
    @Test
    @Tag("task15")
    @DisplayName("Sum of weights of animals between k and l inclusive")
    void sumOfWeightOfAnimalsBetweenKAndL() {
        int k = 0, l = 5;

        int actualValue = Tasks.sumOfWeightsOfAnimalsBetweenKAndLYears(animals, k, l);
        int expectedValue = 8;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @Tag("task15")
    @DisplayName("Sum of weights of animals between k and l inclusive, no range")
    void sumOfWeightOfAnimalsBetweenKAndL_noRange() {
        int k = 0, l = 0;

        int actualValue = Tasks.sumOfWeightsOfAnimalsBetweenKAndLYears(animals, k, l);
        int expectedValue = 0;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @Test
    @Tag("task15")
    @DisplayName("Sum of weights of animals between k and l inclusive; Empty List provided")
    void sumOfWeightOfAnimalsBetweenKAndL_emptyListProvided() {
        List<Animal> animalsUpd = new ArrayList<>();
        int k = 0, l = 0;

        int actualValue = Tasks.sumOfWeightsOfAnimalsBetweenKAndLYears(animalsUpd, k, l);
        int expectedValue = 0;

        assertThat(actualValue).isEqualTo(expectedValue);
    }

    // -------------------------------------------TASK 16---------------------------------------------------
    @Test
    @Tag("task16")
    @DisplayName("Sort sequentially by type, sex, and age")
    void sortSeqByTypeSexAge() {
        List<Animal> animalsUpd = new ArrayList<>(animals);
        animalsUpd.add(new Animal("Acs", CAT, M, 5, 35, 3, false));

        List<Animal> actualAnimals = Tasks.sortSeqByTypeSexAge(animalsUpd);
        List<Animal> expectedAnimals = List.of(
            animalsUpd.get(5), animalsUpd.get(1), animalsUpd.get(0), animalsUpd.get(4),
            animalsUpd.get(3), animalsUpd.get(2)
        );

        assertThat(actualAnimals).containsExactlyElementsOf(expectedAnimals);
    }

    @Test
    @Tag("task16")
    @DisplayName("Sort sequentially by type, sex, and age; Empty List provided")
    void sortSeqByTypeSexAge_emptyListProvided() {
        List<Animal> animalsUpd = new ArrayList<>();

        List<Animal> actualAnimals = Tasks.sortSeqByTypeSexAge(animalsUpd);
        List<Animal> expectedAnimals = List.of();

        assertThat(actualAnimals).containsExactlyElementsOf(expectedAnimals);
    }

    // -------------------------------------------TASK 17---------------------------------------------------
    @Test
    @Tag("task17")
    @DisplayName("Are spiders bite more often than dogs, true")
    void areSpidersBiteMoreOftenThanDogs_false() {
        List<Animal> animalsUpd = new ArrayList<>(animals);
        animalsUpd.add(new Animal("Acs", CAT, M, 5, 35, 3, false));

        boolean actualValue = Tasks.areSpidersBiteMoreOftenThanDog(animalsUpd);

        assertThat(actualValue).isFalse();
    }

    @Test
    @Tag("task17")
    @DisplayName("Are spiders bite more often than dogs, false")
    void areSpidersBiteMoreOftenThanDogs_true() {
        List<Animal> animalsUpd = new ArrayList<>(animals);
        animalsUpd.add(new Animal("Acs", SPIDER, M, 5, 35, 3, true));
        animalsUpd.add(new Animal("Asc", SPIDER, M, 5, 35, 3, true));

        boolean actualValue = Tasks.areSpidersBiteMoreOftenThanDog(animalsUpd);

        assertThat(actualValue).isTrue();
    }

    @Test
    @Tag("task17")
    @DisplayName("Are spiders bite more often than dogs; Empty List provided")
    void areSpidersBiteMoreOftenThanDogs_emptyListProvided() {
        List<Animal> animalsUpd = new ArrayList<>();

        boolean actualValue = Tasks.areSpidersBiteMoreOftenThanDog(animalsUpd);

        assertThat(actualValue).isFalse();
    }

    // -------------------------------------------TASK 18---------------------------------------------------
    @Test
    @Tag("task18")
    @DisplayName("Get heaviest fish")
    void getHeaviestFish() {
        List<Animal> animalsUpd1 = new ArrayList<>(animals);
        animalsUpd1.add(new Animal("asc", FISH, F, 3, 15, 2, false));
        List<Animal> animalsUpd2 = new ArrayList<>(animalsUpd1);
        Animal heaviestFish = new Animal("Lek", FISH, F, 3, 23, 5, false);
        animalsUpd2.add(heaviestFish);
        List<List<Animal>> animalsUpdAll = List.of(animalsUpd1, animalsUpd2);

        Animal actualAnimal = Tasks.getHeaviestFish(animalsUpdAll);

        assertThat(actualAnimal).isEqualTo(heaviestFish);
    }

    @Test
    @Tag("task18")
    @DisplayName("Get heaviest fish; empty list provided")
    void getHeaviestFish_emptyListProvided() {
        List<List<Animal>> animalsUpdAll = List.of();

        Assertions.assertThrows(NoSuchElementException.class, () -> Tasks.getHeaviestFish(animalsUpdAll));
    }

    // -------------------------------------------TASK 19---------------------------------------------------
    @Test
    @Tag("task19")
    @DisplayName("Get map for the <animal name, errors>, no errors")
    void getErrorsWithAnimalNames_noErrors() {
        Set<Map.Entry<String, Set<ValidationError>>> actualSet = Tasks.getErrors(animals).entrySet();
        Set<Map.Entry<String, Set<ValidationError>>> expectedSet = new HashSet<>();

        assertThat(actualSet).containsExactlyInAnyOrderElementsOf(expectedSet);
    }

    @Test
    @Tag("task19")
    @DisplayName("Get map for the <animal name, errors>, all errors check")
    void getErrorsWithAnimalNames_allErrorsCheck() {
        List<Animal> animalsUpd = List.of(
            new Animal("Jack", DOG, M, 35, 110, 15, true),
            new Animal("Meowka", CAT, F, 4, -20, 4, true),
            new Animal("Tarantul", SPIDER, M, 1, 5, -10, false),
            new Animal("Jo", FISH, M, 2, 10, 1, true),
            new Animal("Crow", BIRD, F, 40, -15, -2, false)
        );

        List<Map.Entry<String, Set<ValidationError>>> actualSet = Tasks.getErrors(animalsUpd).entrySet().stream()
            .sorted(Map.Entry.comparingByKey()).toList();
        List<Map.Entry<String, Set<ValidationError>>> expectedSet = preapreExpectedEntrySet().stream()
            .sorted(Map.Entry.comparingByKey()).toList();

        if (actualSet.size() != expectedSet.size()) {
            fail("mismatch size of errors");
        }
        for (int i = 0; i < actualSet.size(); i++) {
            Map.Entry<String, Set<ValidationError>> actualEntry = actualSet.get(i);
            Map.Entry<String, Set<ValidationError>> expectedEntry = expectedSet.get(i);
            assertThat(actualEntry.getKey()).isEqualTo(expectedEntry.getKey());
            if (actualEntry.getValue().size() != expectedEntry.getValue().size()) {
                fail("mismatch size of errors");
            }
        }
    }

    // -------------------------------------------TASK 20---------------------------------------------------
    @Test
    @Tag("task20")
    @DisplayName("Get map for the <animal name, string of errors>; empty set")
    void getErrorsWithAnimalNamesBeautiful_emptySetProvided() {
        List<Animal> animalsUpd = List.of();

        Set<Map.Entry<String, String>> actualSet = Tasks.getErrorsBeautiful(animalsUpd).entrySet();
        Set<Map.Entry<String, String>> expectedSet = new HashSet<>();

        assertThat(actualSet).containsExactlyInAnyOrderElementsOf(expectedSet);
    }
}
