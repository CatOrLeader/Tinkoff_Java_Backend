package edu.hw4;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Tasks {
    private Tasks() {
    }

    @NotNull public static List<Animal> sortByHeightAscending(@NotNull List<Animal> list) {
        return list.stream().sorted(Comparator.comparingInt(Animal::height)).toList();
    }

    @NotNull public static List<Animal> sortByWeightDescending(@NotNull List<Animal> list) {
        return list.stream().sorted(Comparator.comparingInt(Animal::weight).reversed()).toList();
    }

    @NotNull public static Map<Animal.Type, Integer> getAnimalsTypeFreqDict(@NotNull List<Animal> list) {
        return list.stream()
            .collect(Collectors.groupingBy(
                Animal::type,
                Collectors.summingInt(type -> 1)
            ));
    }

    @Nullable public static Animal getLongestName(@NotNull List<Animal> list) {
        return list.stream().max(Comparator.comparingInt(o -> o.name().length())).orElse(null);
    }

    @NotNull public static Animal.Sex getMostAppearedSex(@NotNull List<Animal> list) {
        return list.stream()
            .collect(Collectors.groupingBy(
                Animal::sex,
                Collectors.summingInt(sex -> 1)
            ))
            .entrySet()
            .stream()
            .max(Comparator.comparingInt(Map.Entry::getValue))
            .orElseThrow().getKey();
    }

    @NotNull public static Map<Animal.Type, Animal> getHeaviestAnimalEachType(@NotNull List<Animal> list) {
        return list.stream()
            .collect(Collectors.groupingBy(Animal::type)).entrySet().stream()
            .collect(
                Collectors.toMap(
                    Map.Entry::getKey,
                    entry -> entry.getValue().stream().max(Comparator.comparingInt(Animal::weight)).orElseThrow()
                )
            );
    }

    @NotNull public static Animal getOldestAnimal(@NotNull List<Animal> list, int k) {
        return list.stream()
            .sorted(Comparator.comparingInt(Animal::age))
            .toList()
            .reversed()
            .get(k - 1);
    }

    @NotNull public static Optional<Animal> getHeaviestAnimalLowerThanK(@NotNull List<Animal> list, int k) {
        return list.stream()
            .filter(animal -> animal.height() < k)
            .max(Comparator.comparingInt(Animal::weight));
    }

    public static int getCountOfPaws(@NotNull List<Animal> list) {
        return list.stream()
            .mapToInt(Animal::paws)
            .sum();
    }

    @NotNull public static List<Animal> getMismatchAgePawsAnimals(@NotNull List<Animal> list) {
        return list.stream()
            .filter(animal -> animal.age() != animal.paws())
            .toList();
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    @NotNull public static List<Animal> getAnimalsCanBiteAndHigherThan100(@NotNull List<Animal> list) {
        return list.stream()
            .filter(animal -> animal.bites() && animal.height() > 100)
            .toList();
    }

    public static int getAnimalsWeightLargerThanHeight(@NotNull List<Animal> list) {
        return list.stream()
            .filter(animal -> animal.weight() > animal.height())
            .toList()
            .size();
    }

    @NotNull public static List<Animal> getAnimalsNamesContainTwoOrMoreWords(@NotNull List<Animal> list) {
        return list.stream()
            .filter(animal -> animal.name().split(" ").length >= 2)
            .toList();
    }

    public static boolean isContainsDogHigherThanK(@NotNull List<Animal> list, int k) {
        return !list.stream()
            .filter(animal -> animal.type() == Animal.Type.DOG && animal.height() > k)
            .toList()
            .isEmpty();
    }

    public static int sumOfWeightsOfAnimalsBetweenKAndLYears(@NotNull List<Animal> list, int k, int l) {
        return list.stream()
            .filter(animal -> k <= animal.age() && animal.age() <= l)
            .mapToInt(Animal::weight)
            .sum();
    }

    @NotNull public static List<Animal> sortSeqByTypeSexAge(@NotNull List<Animal> list) {
        return list.stream()
            .sorted(Comparator
                .comparing(Animal::type)
                .thenComparing(Animal::sex)
                .thenComparing(Animal::name))
            .toList();
    }

    public static boolean areSpidersBiteMoreOftenThanDog(@NotNull List<Animal> list) {
        return list.stream()
                   .filter(animal -> animal.type() == Animal.Type.DOG || animal.type() == Animal.Type.SPIDER)
                   .mapToInt(animal -> {
                       if (animal.bites()) {
                           if (animal.type() == Animal.Type.DOG) {
                               return 1;
                           } else {
                               return -1;
                           }
                       }
                       return 0;
                   })
                   .sum() < 0;
    }

    @NotNull public static Animal getHeaviestFish(@NotNull List<List<Animal>> lists) {
        return lists.stream()
            .map(list -> list.stream()
                .filter(animal -> animal.type() == Animal.Type.FISH)
                .max(Comparator.comparingInt(Animal::weight))
                .orElseThrow()
            )
            .max(Comparator.comparingInt(Animal::weight))
            .orElseThrow();
    }

    @NotNull public static Map<String, Set<ValidationError>> getErrors(@NotNull List<Animal> list) {
        return list.stream()
            .filter(ValidationError::containsErrors)
            .collect(
                Collectors.toMap(
                    Animal::name,
                    Tasks::getSetOfErrors
                )
            );
    }

    @NotNull public static Map<String, String> getErrorsBeautiful(@NotNull List<Animal> list) {
        return list.stream()
            .filter(ValidationError::containsErrors)
            .collect(
                Collectors.toMap(
                    Animal::name,
                    animal -> ValidationError.merge(getSetOfErrors(animal), animal)
                )
            );
    }

    private static @NotNull Set<ValidationError> getSetOfErrors(@NotNull Animal animal) {
        Set<ValidationError> set = new HashSet<>();
        if (IncorrectBitePossibilityError.isAppears(animal)) {
            set.add(new IncorrectBitePossibilityError());
        }
        if (IncorrectAgeError.isAppears(animal)) {
            set.add(new IncorrectAgeError());
        }
        if (IncorrectHeightError.isAppears(animal)) {
            set.add(new IncorrectHeightError());
        }
        if (IncorrectWeightError.isAppears(animal)) {
            set.add(new IncorrectWeightError());
        }
        return set;
    }
}
