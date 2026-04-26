package Project.Franquicias.port.in;

import Project.Franquicias.models.Branch;
import reactor.core.publisher.Mono;

public interface BranchUseCase {

    Mono<Branch> addBranch(Branch branch);
    Mono<Branch> updateBranchName(String id, String newName);

}
