package Project.Franquicias.application.Services;

import Project.Franquicias.models.Branch;
import Project.Franquicias.port.in.BranchUseCase;
import Project.Franquicias.port.out.BranchRepository;
import Project.Franquicias.port.out.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BranchServices implements BranchUseCase {

    private final BranchRepository branchRepository;
    private final FranchiseRepository franchiseRepository;

    @Override
    public Mono<Branch> addBranch(Branch branch) {
        return franchiseRepository.findById(branch.getFranchiseId())
                .switchIfEmpty(Mono.error(
                        new RuntimeException("Franchise not found with id:" + branch.getFranchiseId())
                        ))
                .flatMap(existingFranchise -> branchRepository.save(branch));

    }

    @Override
    public Mono<Branch> updateBranchName(String id, String newName) {
        return branchRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new RuntimeException("Branch not found with id: " + id)
                ))
                .flatMap(existing -> branchRepository.update(id, newName));
    }
}
