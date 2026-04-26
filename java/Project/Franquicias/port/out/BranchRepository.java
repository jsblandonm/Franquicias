package Project.Franquicias.port.out;

import Project.Franquicias.models.Branch;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BranchRepository {
    Mono<Branch> save(Branch branch);
    Mono<Branch> findById(String id);
    Mono<Branch> update(String id, String newName);
    Flux<Branch> findByFranchiseId(String franchiseId);
}
