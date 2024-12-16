package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Vacancy;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class MemoryVacancyRepository implements VacancyRepository {

    private AtomicInteger nextId = new AtomicInteger(0);

    private final Map<Integer, Vacancy> vacancies = new ConcurrentHashMap<>();

    private MemoryVacancyRepository() {
        save(new Vacancy(1, "Intern Java Developer",
                "Понимание принципов ООП, понимание алгоритмов и структур данных, знание Java Core",
                LocalDateTime.of(2001, 1, 1, 0, 0), true, 1, 1));
        save(new Vacancy(2, "Junior Java Developer",
                "Обязательное знание основ Java EE (Servlets, jsp)",
                LocalDateTime.of(2001, 1, 1, 0, 0), true, 2, 1));
        save(new Vacancy(3, "Junior+ Java Developer",
                "Представление о реляционных базах данных (Oracle, MySQL, PostgreSQL и др.)",
                LocalDateTime.of(2001, 1, 1, 0, 0), true, 3, 1));
        save(new Vacancy(4, "Middle Java Developer",
                "Опыт написания unit-тестов, знания систем контроля версий (git или svn)",
                LocalDateTime.of(2001, 1, 1, 0, 0), true, 1, 1));
        save(new Vacancy(5, "Middle+ Java Developer",
                "Знание Spring Framework, а также ORM (Hibernate Framework)",
                LocalDateTime.of(2001, 1, 1, 0, 0), true, 2, 1));
        save(new Vacancy(6, "Senior Java Developer",
                "Высшая академия beckend веб-разработки \"Job4j\"",
                LocalDateTime.of(2001, 1, 1, 0, 0), true, 3, 1));
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        vacancy.setId(nextId.incrementAndGet());
        vacancies.put(vacancy.getId(), vacancy);
        return vacancy;
    }

    @Override
    public boolean deleteById(int id) {
        return vacancies.remove(id) != null;
    }

    @Override
    public boolean update(Vacancy vacancy) {
        return vacancies.computeIfPresent(vacancy.getId(), (id, oldVacancy) -> {
            return new Vacancy(
                    oldVacancy.getId(),
                    vacancy.getTitle(),
                    vacancy.getDescription(),
                    vacancy.getCreationDate(),
                    vacancy.getVisible(),
                    vacancy.getCityId(),
                    vacancy.getFileId()
            );
        }) != null;
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        return Optional.ofNullable(vacancies.get(id));
    }

    @Override
    public Collection<Vacancy> findAll() {
        return vacancies.values();
    }
}
