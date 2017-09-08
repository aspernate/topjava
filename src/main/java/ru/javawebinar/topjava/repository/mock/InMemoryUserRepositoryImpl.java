package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        List<User> users = Arrays.asList(
            new User(null, "Вася", "vasya@mail.ru", "vasya", Role.ROLE_USER),
            new User(null, "Петя", "petya@mail.ru", "petya", Role.ROLE_USER),
            new User(null, "Ваня", "vanya@mail.ru", "vanya", Role.ROLE_ADMIN)

        );
        users.forEach(this::save);
    }

    @Override
    public boolean delete(int id) {
        repository.remove(id);
        log.info("delete {}", id);
        return true;
    }

    @Override
    public User save(User user) {
        if(user.isNew())
            user.setId(counter.incrementAndGet());
        repository.put(user.getId(), user);
        log.info("save {}", user);
        return user;
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>(repository.values());
        users.sort((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
        log.info("getAll");
        return users;
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return getAll().stream().filter(user -> user.getEmail().compareToIgnoreCase(email) == 0).findFirst().get();
    }
}
