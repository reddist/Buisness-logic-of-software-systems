package io.reddist.repositories;

import io.reddist.entities.Vacancy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacancyRepo extends CrudRepository<Vacancy, Long> {
    Vacancy getByVacancyId (long id);
}
