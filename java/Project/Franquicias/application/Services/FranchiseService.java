package Project.Franquicias.application.Services;

import Project.Franquicias.models.Franchise;
import Project.Franquicias.port.in.FranchiseUseCase;
import Project.Franquicias.port.out.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FranchiseService implements FranchiseUseCase {

    private final FranchiseRepository franchiseRepository;

    @Override
    public Mono<Franchise> addFranchise(Franchise franchise) {
        return franchiseRepository.save(franchise);
    }

    @Override
    public Mono<Franchise> updateFranchise(String id, String newName) {
        return franchiseRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new RuntimeException("Franchise not found with id:" + id)))
                        .flatMap(existingFranchise -> franchiseRepository.update(id, newName));
    }
}
