package Project.Franquicias.infrastructure.adapter.in.web;

import Project.Franquicias.models.Franchise;
import Project.Franquicias.port.in.FranchiseUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/franchises")
@RequiredArgsConstructor
public class FranchiseController {

    private final FranchiseUseCase franchiseUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Franchise> addFranchise(@RequestBody @Valid Franchise franchise) {
        return franchiseUseCase.addFranchise(franchise);
    }

    @PatchMapping("/{id}/name")
    public Mono<Franchise> updateName(@PathVariable String id, @RequestParam String newName) {
        return franchiseUseCase.updateFranchise(id, newName);
    }

}
