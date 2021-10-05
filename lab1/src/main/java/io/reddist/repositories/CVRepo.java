package io.reddist.repositories;

import io.reddist.entities.CV;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CVRepo extends CrudRepository<CV, Long> {
    CV getByCvId (long id);
}
