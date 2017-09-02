package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        final Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>();
        mealList.forEach(meal -> caloriesSumByDate.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum));

        final List<UserMealWithExceed> mealsWithExceeded = new ArrayList<>();
        mealList.forEach(meal -> {
            if(meal.getDateTime().toLocalTime().compareTo(startTime) >= 0 && meal.getDateTime().toLocalTime().compareTo(endTime) <= 0)
            mealsWithExceeded.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), caloriesSumByDate.get(meal.getDateTime()) > caloriesPerDay));
        });
        return mealsWithExceeded;

        /*
        Map<LocalDate, Integer> caloriesSumByDate2 = mealList.stream()
               .collect(Collectors.toMap(
                       time -> time.getDateTime().toLocalDate(),
                       UserMeal::getCalories, (date1, date2) -> date1 + date2
               ));

        return mealList.stream()
                .filter(meal -> meal.getDateTime().toLocalTime().compareTo(startTime) >= 0 && meal.getDateTime().toLocalTime().compareTo(endTime) <= 0)
                .map(meal -> new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), caloriesSumByDate2.get(meal.getDateTime().toLocalDate()) > meal.getCalories()))
                .collect(Collectors.toList()); */
    }
}
