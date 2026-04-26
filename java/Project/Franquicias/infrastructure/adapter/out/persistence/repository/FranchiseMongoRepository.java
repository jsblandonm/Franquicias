package Project.Franquicias.infrastructure.adapter.out.persistence.repository;

import Project.Franquicias.models.Franchise;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FranchiseMongoRepository extends ReactiveMongoRepository<Franchise, String> {
}
