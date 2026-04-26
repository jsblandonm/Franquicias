package Project.Franquicias.infrastructure.adapter.in.web;

import Project.Franquicias.models.Branch;
import Project.Franquicias.port.in.BranchUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchUseCase branchUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Branch> addBranch(@RequestBody @Valid Branch branch) {
        return branchUseCase.addBranch(branch);
    }

    @PatchMapping("/{id}/name")
    public Mono<Branch> updateName(@PathVariable String id,
                                   @RequestParam String newName) {
        return branchUseCase.updateBranchName(id, newName);
    }
}
