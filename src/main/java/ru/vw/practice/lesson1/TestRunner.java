package ru.vw.practice.lesson1;

import ru.vw.practice.lesson1.annotation.AfterSuite;
import ru.vw.practice.lesson1.annotation.BeforeSuite;
import ru.vw.practice.lesson1.annotation.CsvSource;
import ru.vw.practice.lesson1.annotation.Test;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/** Класс осуществляющий проверки аннотаций и запуск методов. */
public class TestRunner {
  public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  public static void runTests(Class<?> c) {
    Map<Class<?>, List<Method>> annotationInfo = Arrays.stream(c.getDeclaredMethods())
            .flatMap(a -> Arrays.stream(a.getDeclaredAnnotations()).map(b -> Map.entry(b.annotationType(), a)))
            .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())));

    annotationValidate(annotationInfo);

    if (annotationInfo.containsKey(BeforeSuite.class)) {
      Method beforeSuite = annotationInfo.get(BeforeSuite.class).get(0);
      invokeMethod(beforeSuite, beforeSuite);
    }

    if (annotationInfo.containsKey(Test.class)) {
      try {
        Object obj = c.getConstructor().newInstance();
        annotationInfo.get(Test.class).stream()
                .sorted(Comparator.comparingInt(a -> a.getAnnotation(Test.class).priority()))
                .forEach(test -> invokeMethod(test, obj));
      } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }

    if (annotationInfo.containsKey(AfterSuite.class)) {
      Method afterSuite = annotationInfo.get(AfterSuite.class).get(0);
      invokeMethod(afterSuite, afterSuite);
    }
  }

  private static void runIfPresent(Map<Class<?>, List<Method>> map, Class<?> item, BiConsumer<Class<?>, List<Method>> action) {
    if (map.containsKey(item)) {
      action.accept(item, map.get(item));
    }
  }

  private static void annotationValidate(Map<Class<?>, List<Method>> annotationInfo) {
    runIfPresent(annotationInfo, BeforeSuite.class, (a, b) -> {
      if (b.size() > 1) throw new RuntimeException("BeforeSuite used twice in this Class");
      if (b.stream().anyMatch(c -> !Modifier.isStatic(c.getModifiers())))
        throw new RuntimeException("BeforeSuite on nonstatic method");
    });
    runIfPresent(annotationInfo, AfterSuite.class, (a, b) -> {
      if (b.size() > 1) throw new RuntimeException("AfterSuite used twice in this Class");
      if (b.stream().anyMatch(c -> !Modifier.isStatic(c.getModifiers())))
        throw new RuntimeException("AfterSuite on nonstatic method");
    });
    runIfPresent(annotationInfo, Test.class, (a, b) -> {
      if (b.stream().anyMatch(c -> {
        int priority = c.getAnnotation(Test.class).priority();
        return priority < Test.MIN_PRIORITY || priority > Test.MAX_PRIORITY;
      })) throw new RuntimeException("Test annotation priority is out of bounds");
    });
  }

  private static void invokeMethod(Method method, Object obj) {
    try {
      if (!Modifier.isPublic(method.getModifiers())) {
        method.setAccessible(true);
      }
      Class<?>[] paramTypes = method.getParameterTypes();
      CsvSource source = method.getAnnotation(CsvSource.class);
      if (Objects.nonNull(source) && paramTypes.length > 0) {
        String[] strParams = source.value().split("\\s*,", paramTypes.length + 1);
        Object[] params = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
          if (paramTypes[i] == int.class) {
            params[i] = Integer.parseInt(strParams[i]);
          } else if (paramTypes[i] == Date.class) {
            params[i] = dateFormat.parse(strParams[i], new ParsePosition(0));
          } else if (paramTypes[i] == boolean.class) {
            params[i] = Boolean.parseBoolean(strParams[i]);
          } else if (paramTypes[i] == String.class) {
            params[i] = strParams[i];
          } else {
            params[i] = convert(paramTypes[i], strParams[i]);
          }
        }

        method.invoke(obj, params);

      } else {
        method.invoke(obj);
      }
    } catch (InvocationTargetException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  private static Object convert(Class<?> targetType, String text) {
    PropertyEditor editor = PropertyEditorManager.findEditor(targetType);
    editor.setAsText(text);
    return editor.getValue();
  }

}
