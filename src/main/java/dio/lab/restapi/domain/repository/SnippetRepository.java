package dio.lab.restapi.domain.repository;

import dio.lab.restapi.domain.model.Snippet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SnippetRepository extends JpaRepository<Snippet, Long> {
}
