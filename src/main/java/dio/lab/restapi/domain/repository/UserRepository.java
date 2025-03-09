package dio.lab.restapi.domain.repository;

import dio.lab.restapi.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
