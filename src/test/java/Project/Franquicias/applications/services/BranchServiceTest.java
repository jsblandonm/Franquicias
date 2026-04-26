package Project.Franquicias.applications.services;

import Project.Franquicias.application.Services.BranchServices;
import Project.Franquicias.models.Branch;
import Project.Franquicias.models.Franchise;
import Project.Franquicias.port.out.BranchRepository;
import Project.Franquicias.port.out.FranchiseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BranchServiceTest {

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private FranchiseRepository franchiseRepository;

    @InjectMocks
    private BranchServices branchService;

    // ── addBranch ─────────────────────────────────────────────

    @Test
    void addBranch_shouldReturnSavedBranch_whenFranchiseExists() {
        // GIVEN
        Branch branch = Branch.builder()
                .name("Sucursal Centro")
                .franchiseId("franchise123")
                .build();

        Franchise franchise = Franchise.builder()
                .id("franchise123")
                .name("McDonald's")
                .build();

        Branch savedBranch = Branch.builder()
                .id("branch123")
                .name("Sucursal Centro")
                .franchiseId("franchise123")
                .build();

        when(franchiseRepository.findById("franchise123")).thenReturn(Mono.just(franchise));
        when(branchRepository.save(branch)).thenReturn(Mono.just(savedBranch));

        // WHEN
        Mono<Branch> result = branchService.addBranch(branch);

        // THEN
        StepVerifier.create(result)
                .expectNextMatches(b ->
                        b.getId().equals("branch123") &&
                                b.getName().equals("Sucursal Centro") &&
                                b.getFranchiseId().equals("franchise123")
                )
                .verifyComplete();

        verify(franchiseRepository, times(1)).findById("franchise123");
        verify(branchRepository, times(1)).save(branch);
    }

    @Test
    void addBranch_shouldReturnError_whenFranchiseNotFound() {
        // GIVEN
        Branch branch = Branch.builder()
                .name("Sucursal Centro")
                .franchiseId("noexiste")
                .build();

        when(franchiseRepository.findById("noexiste")).thenReturn(Mono.empty());

        // WHEN
        Mono<Branch> result = branchService.addBranch(branch);

        // THEN
        StepVerifier.create(result)
                .expectErrorMatches(error ->
                        error instanceof RuntimeException &&
                                error.getMessage().contains("Franchise not found with id")
                )
                .verify();

        verify(branchRepository, never()).save(any());
    }

    // ── updateBranchName ──────────────────────────────────────

    @Test
    void updateBranchName_shouldReturnUpdatedBranch_whenBranchExists() {
        // GIVEN
        String id = "branch123";
        String newName = "Sucursal Norte";

        Branch existing = Branch.builder()
                .id(id)
                .name("Sucursal Centro")
                .franchiseId("franchise123")
                .build();

        Branch updated = Branch.builder()
                .id(id)
                .name(newName)
                .franchiseId("franchise123")
                .build();

        when(branchRepository.findById(id)).thenReturn(Mono.just(existing));
        when(branchRepository.update(id, newName)).thenReturn(Mono.just(updated));

        // WHEN
        Mono<Branch> result = branchService.updateBranchName(id, newName);

        // THEN
        StepVerifier.create(result)
                .expectNextMatches(b -> b.getName().equals("Sucursal Norte"))
                .verifyComplete();

        verify(branchRepository, times(1)).findById(id);
        verify(branchRepository, times(1)).update(id, newName);
    }

    @Test
    void updateBranchName_shouldReturnError_whenBranchNotFound() {
        // GIVEN
        String id = "noexiste";
        when(branchRepository.findById(id)).thenReturn(Mono.empty());

        // WHEN
        Mono<Branch> result = branchService.updateBranchName(id, "Nuevo nombre");

        // THEN
        StepVerifier.create(result)
                .expectErrorMatches(error ->
                        error instanceof RuntimeException &&
                                error.getMessage().contains("Branch not found with id")
                )
                .verify();

        verify(branchRepository, never()).update(any(), any());
    }
}