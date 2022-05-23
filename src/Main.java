import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Collection<Person> persons = getPeopleList();
        System.out.println("Count of underage: " + getUnderageCount(persons));
        System.out.println("Count of recruits: " + getRecruitsList(persons).size());
        System.out.println("Count of workable: " + getWorkableList(persons).size());
    }

    public static Collection<Person> getPeopleList() {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        return persons;
    }

    public static List<String> getRecruitsList(Collection<Person> persons) {
        return persons.parallelStream()
                .filter(person -> person.getSex() == Sex.MAN)
                .filter(person -> person.getAge() >= 18 && person.getAge() <= 27)
                .map(person -> person.getFamily())
                .collect(Collectors.toList());
    }

    public static Long getUnderageCount(Collection<Person> persons) {
        return persons.parallelStream()
                .filter(person -> person.getAge() < 18)
                .count();
    }

    public static List<Person> getWorkableList(Collection<Person> persons) {
        return persons.parallelStream()
                .filter(person -> person.getEducation() == Education.HIGHER)
                .filter(person -> person.getAge() >= 18 && person.getAge() <= 65)
                .filter(person -> (person.getSex() == Sex.WOMAN && person.getAge() <= 60) || person.getSex() == Sex.MAN)
                .sorted(Comparator.comparing(person -> person.getFamily()))
                .collect(Collectors.toList());
    }
}
