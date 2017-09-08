package ru.javawebinar.topjava.repository.mock;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if(meal.getUserId() != userId)
            return null;
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return repository.remove(id).getUserId() == userId;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        if(meal.getUserId() != userId)
            return null;
        return repository.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.values().stream().sorted(Comparator.comparing(Meal::getDateTime)).filter(meal -> meal.getUserId() == userId).collect(Collectors.toList());
    }
}

