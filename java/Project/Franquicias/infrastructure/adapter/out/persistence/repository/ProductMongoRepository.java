package Project.Franquicias.infrastructure.adapter.out.persistence.repository;

import Project.Franquicias.models.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductMongoRepository extends ReactiveMongoRepository<Product, String> {
    Flux<Product> findByBranchId(String branchId);
}
