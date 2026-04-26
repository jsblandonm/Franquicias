package Project.Franquicias.infrastructure.adapter.out.persistence;

import Project.Franquicias.infrastructure.adapter.out.persistence.repository.ProductMongoRepository;
import Project.Franquicias.models.Product;
import Project.Franquicias.port.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductMongoRepository mongoRepository;

    @Override
    public Mono<Product> save(Product product) {
        return mongoRepository.save(product);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return mongoRepository.deleteById(id);
    }

    @Override
    public Mono<Product> findById(String id) {
        return mongoRepository.findById(id);
    }

    @Override
    public Mono<Product> update(Product product) {
        return mongoRepository.save(product);
    }

    @Override
    public Flux<Product> findByBranchId(String branchId) {
        return mongoRepository.findByBranchId(branchId);
    }
}
