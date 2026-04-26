package Project.Franquicias.infrastructure.adapter.out.persistence;

import Project.Franquicias.infrastructure.adapter.out.persistence.repository.BranchMongoRepository;
import Project.Franquicias.models.Branch;
import Project.Franquicias.port.out.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BranchRepositoryImpl implements BranchRepository {

    private final BranchMongoRepository mongoRepository;

    @Override
    public Mono<Branch> save(Branch branch) {
        return mongoRepository.save(branch);
    }

    @Override
    public Mono<Branch> findById(String id) {
        return mongoRepository.findById(id);
    }

    @Override
    public Mono<Branch> update(String id, String newName) {
        return mongoRepository.findById(id)
                .flatMap(existing -> {
                    existing.setName(newName);
                    return mongoRepository.save(existing);
                });
    }

    @Override
    public Flux<Branch> findByFranchiseId(String franchiseId) {
        return mongoRepository.findByFranchiseId(franchiseId);
    }
}