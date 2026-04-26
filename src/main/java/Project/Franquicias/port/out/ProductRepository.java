package Project.Franquicias.port.out;

import Project.Franquicias.models.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ProductRepository {
    Mono<Product> save(Product product);

    Mono<Void> deleteById(String id);
    Mono<Product> findById(String id);
    Mono<Product> update(Product product);
    Flux<Product> findByBranchId(String branchId);
}
