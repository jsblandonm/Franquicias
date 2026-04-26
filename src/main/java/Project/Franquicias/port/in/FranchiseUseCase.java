package Project.Franquicias.port.in;

import Project.Franquicias.models.Franchise;
import reactor.core.publisher.Mono;

public interface FranchiseUseCase {
    Mono<Franchise> addFranchise(Franchise franchise);
    Mono<Franchise> updateFranchise(String id, String newName);
}
