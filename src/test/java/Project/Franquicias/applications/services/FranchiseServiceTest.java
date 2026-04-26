package Project.Franquicias.applications.services;

import Project.Franquicias.application.Services.FranchiseService;
import Project.Franquicias.models.Franchise;
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
class FranchiseServiceTest {

    @Mock
    private FranchiseRepository franchiseRepository;

    @InjectMocks
    private FranchiseService franchiseService;

//    add franchies
    @Test
    void addFranchise_shouldReturnFranchise() {
        //GIVEN
        Franchise franchise = Franchise.builder()
                .name("MacDolad's")
                .build();

        Franchise savedFranchise = Franchise.builder()
                .id("12bbaa")
                .name("MacDolad's")
                .build();
        when(franchiseRepository.save(franchise)).thenReturn(Mono.just(savedFranchise));

        //WHEN
        Mono<Franchise> result = franchiseService.addFranchise(franchise);

        //THEN
        StepVerifier.create(result)
                .expectNextMatches(f -> f.getId().equals("12bbaa") && f.getName().equals("MacDolad's"))
                .verifyComplete();
    }


//    Update Franchises
    @Test
    void updateFranchise_shouldReturnUpdatedFranchise_whenFranchiseExists(){

        //GIVEN
        String id = "12bbaa";
        String newName = "Burger King";
        Franchise existing = Franchise.builder()
                .id(id)
                .name("MacDolad's")
                .build();

        Franchise updated = Franchise.builder()
                .id(id)
                .name(newName)
                .build();

        when(franchiseRepository.findById(id)).thenReturn(Mono.just(existing));
        when(franchiseRepository.update(id, newName)).thenReturn(Mono.just(updated));

        // WHEN
        Mono<Franchise> result = franchiseService.updateFranchise(id, newName);

        // THEN
        StepVerifier.create(result)
                .expectNextMatches(f -> f.getName().equals("Burger King"))
                .verifyComplete();

        verify(franchiseRepository, times(1)).findById(id);
        verify(franchiseRepository, times(1)).update(id, newName);

    }

    @Test
    void updateFranchise_shouldReturnError_whenFranchiseNotFound() {
        // GIVEN
        String id = "noexiste";
        when(franchiseRepository.findById(id)).thenReturn(Mono.empty());

        // WHEN
        Mono<Franchise> result = franchiseService.updateFranchise(id, "Nuevo nombre");

        // THEN
        StepVerifier.create(result)
                .expectErrorMatches(error ->
                        error instanceof RuntimeException &&
                                error.getMessage().contains("Franchise not found with id")
                )
                .verify();

        verify(franchiseRepository, never()).update(any(), any());
    }
}

