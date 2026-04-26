package Project.Franquicias.infrastructure.adapter.out.persistence.repository;

import Project.Franquicias.models.Branch;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface BranchMongoRepository extends ReactiveMongoRepository<Branch, String> {

    Flux<Branch> findByFranchiseId(String franchiseId);
}
