package ru.vw.practice.lesson2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SecondLesson {
  public static final String ENGINEER_TEXT = "Инженер";
  public static final String WORD_SPLITTER = "\\s+";
  private static final List<Integer> TEST_INT_LIST = List.of(5, 2, 10, 9, 4, 3, 10, 1, 13);
  private static final List<Employee> TEST_EMLOYEE_LIST = List.of(
          Employee.builder().name("Вася").age(19).job("Professor").build(),
          Employee.builder().name("Федя").age(64).job(ENGINEER_TEXT).build(),
          Employee.builder().name("Коля").age(32).job(ENGINEER_TEXT).build(),
          Employee.builder().name("Арсений").age(32).job(ENGINEER_TEXT).build(),
          Employee.builder().name("Дима").age(16).job("Student").build(),
          Employee.builder().name("Саша").age(19).job(ENGINEER_TEXT).build(),
          Employee.builder().name("Миша").age(83).job("Ямщик").build(),
          Employee.builder().name("Семен").age(55).job(ENGINEER_TEXT).build()
  );

  private static final List<String> TEST_WORDS_LIST = List.of(
          "Hello",
          "Amigo",
          "Table",
          "Connected",
          "List"
  );

  private static final String TEST_WORDS_STRING = "Table Hello Table Connected List Table Connected";

  private static final String[] TEST_WORDS_ARRAY = new String[]
          {
                  "Hello goodbye find lesson remove",
                  "Amigo list lesson future list",
                  "Table rule cat mock integer",
                  "Test fire government green again",
                  "List dual comparator dual join where"
          };

  //Реализуйте удаление из листа всех дубликатов
  public static <T> List<T> removeDoubles(List<T> list) {
    return list.stream().distinct().toList();
  }

  //Найдите в списке целых чисел 3-е наибольшее число (пример: 5 2 10 9 4 3 10 1 13 => 10)
  public static Optional<Integer> getThirdElementReverse(List<Integer> list) {
    return list.stream().sorted(Comparator.reverseOrder()).skip(2).findFirst();
  }

  //Найдите в списке целых чисел 3-е наибольшее «уникальное» число (пример: 5 2 10 9 4 3 10 1 13 => 9, в отличие от прошлой задачи здесь разные 10 считает за одно число)
  public static Optional<Integer> getThirdElementDistinctReverse(List<Integer> list) {
    return list.stream().sorted(Comparator.reverseOrder()).distinct().skip(2).findFirst();
  }

  //Имеется список объектов типа Сотрудник (имя, возраст, должность), необходимо получить список имен 3 самых старших сотрудников с должностью «Инженер», в порядке убывания возраста
  public static List<String> getThreeEngineerReverse(List<Employee> list) {
    return list.stream().filter(a -> Objects.equals(a.getJob(), ENGINEER_TEXT)).sorted((a, b) -> b.getAge() - a.getAge()).limit(3).map(Employee::getName).toList();
  }

  //Имеется список объектов типа Сотрудник (имя, возраст, должность), посчитайте средний возраст сотрудников с должностью «Инженер»
  public static OptionalDouble getAverageEngineer(List<Employee> list) {
    return list.stream().filter(a -> Objects.equals(a.getJob(), ENGINEER_TEXT)).mapToInt(Employee::getAge).average();
  }

  //Просто эксперимент
  public record IntPair(int age, int count) {
  }
  //Просто эксперимент
  public static Optional<Double> getAverageEngineerV2(List<Employee> list) {
    return list.stream().filter(a -> Objects.equals(a.getJob(), ENGINEER_TEXT)).map(a -> new IntPair(a.getAge(), 1))
            .reduce((a, b) -> new IntPair(a.age + b.age, a.count + b.count)).map(a -> (double) a.age / a.count);
  }


  //Найдите в списке слов самое длинное
  public static Optional<String> getLongestString(List<String> list) {
    return list.stream().max(Comparator.comparingInt(String::length));
  }

  //Имеется строка с набором слов в нижнем регистре, разделенных пробелом. Постройте хеш-мапы, в которой будут хранится пары: слово - сколько раз оно встречается во входной строке
  public static Map<String, Long> getWordsCount(String words) {
    return Arrays.stream(words.split(WORD_SPLITTER)).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
  }

  //Отпечатайте в консоль строки из списка в порядке увеличения длины слова, если слова имеют одинаковую длины, то должен быть сохранен алфавитный порядок
  public static void printWords(List<String> list) {
    list.stream().sorted(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder())).forEachOrdered(System.out::println);
  }

  //Имеется массив строк, в каждой из которых лежит набор из 5 строк, [читаю как из пяти СЛОВ]
  //разделенных пробелом, найдите среди всех слов самое длинное, если таких слов несколько, получите любое из них
  public static Optional<String> getLongestWord(String[] list) {
    return Arrays.stream(list).flatMap(a -> Arrays.stream(a.split(WORD_SPLITTER))).max(Comparator.comparingInt(String::length));
  }


  public static void runAllTest() {
    System.out.printf("1. removeDoubles: %s\n", removeDoubles(TEST_INT_LIST));

    System.out.printf("2. getThirdElementReverse: %s\n", getThirdElementReverse(TEST_INT_LIST));

    System.out.printf("3. getThirdElementDistinctReverse: %s\n", getThirdElementDistinctReverse(TEST_INT_LIST));

    System.out.printf("4. getThreeEngineerReverse: %s\n", getThreeEngineerReverse(TEST_EMLOYEE_LIST));

    System.out.printf("5. getAverageEngineer: %s\n", getAverageEngineer(TEST_EMLOYEE_LIST));

    System.out.printf("6. getLongestString: %s\n", getLongestString(TEST_WORDS_LIST));

    System.out.printf("7. getWordsCount: %s\n", getWordsCount(TEST_WORDS_STRING));

    System.out.println("8. printWords: ");
    printWords(TEST_WORDS_LIST);

    System.out.printf("9. removeDoubles: %s\n", getLongestWord(TEST_WORDS_ARRAY));
  }

}
