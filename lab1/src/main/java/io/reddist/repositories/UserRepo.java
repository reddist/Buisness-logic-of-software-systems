package io.reddist.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import io.reddist.entities.User;

@Repository public interface UserRepo extends CrudRepository<User, Long> {

    User getByEmail(String email);

    User getByUserId(long id);

    boolean existsByEmail(String email);
}
