package Project.Franquicias.infrastructure.adapter.out.persistence;


import Project.Franquicias.infrastructure.adapter.out.persistence.repository.FranchiseMongoRepository;
import Project.Franquicias.models.Franchise;
import Project.Franquicias.port.out.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FranchiseRepositoryImpl implements FranchiseRepository {

    private final FranchiseMongoRepository mongoRepository;

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        return mongoRepository.save(franchise);
    }

    @Override
    public Mono<Franchise> findById(String id) {
        return mongoRepository.findById(id);
    }

    @Override
    public Mono<Franchise> update(String id, String newName) {
        return mongoRepository.findById(id)
                .flatMap(existing -> {
                    existing.setName(newName);
                    return mongoRepository.save(existing);
                });
    }
}
