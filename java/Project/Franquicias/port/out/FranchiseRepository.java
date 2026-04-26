package Project.Franquicias.port.out;

import Project.Franquicias.models.Franchise;
import reactor.core.publisher.Mono;

public interface FranchiseRepository {
    Mono<Franchise> save(Franchise franchise);
    Mono<Franchise> findById(String id);
    Mono<Franchise> update(String id, String newName);
}
