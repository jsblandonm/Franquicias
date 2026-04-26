package Project.Franquicias.applications.services;

import Project.Franquicias.application.Services.ProductServices;
import Project.Franquicias.models.Branch;
import Project.Franquicias.models.DTO.ProductByBranchDTO;
import Project.Franquicias.models.Product;
import Project.Franquicias.port.out.BranchRepository;
import Project.Franquicias.port.out.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private ProductServices productService;

    // ── addProduct ────────────────────────────────────────────

    @Test
    void addProduct_shouldReturnSavedProduct_whenBranchExists() {
        // GIVEN
        Product product = Product.builder()
                .name("Big Mac")
                .stock(100)
                .branchId("branch123")
                .build();

        Branch branch = Branch.builder()
                .id("branch123")
                .name("Sucursal Centro")
                .franchiseId("franchise123")
                .build();

        Product savedProduct = Product.builder()
                .id("product123")
                .name("Big Mac")
                .stock(100)
                .branchId("branch123")
                .build();

        when(branchRepository.findById("branch123")).thenReturn(Mono.just(branch));
        when(productRepository.save(product)).thenReturn(Mono.just(savedProduct));

        // WHEN
        Mono<Product> result = productService.addProduct(product);

        // THEN
        StepVerifier.create(result)
                .expectNextMatches(p ->
                        p.getId().equals("product123") &&
                                p.getName().equals("Big Mac") &&
                                p.getStock().equals(100)
                )
                .verifyComplete();

        verify(branchRepository, times(1)).findById("branch123");
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void addProduct_shouldReturnError_whenBranchNotFound() {
        // GIVEN
        Product product = Product.builder()
                .name("Big Mac")
                .stock(100)
                .branchId("noexiste")
                .build();

        when(branchRepository.findById("noexiste")).thenReturn(Mono.empty());

        // WHEN
        Mono<Product> result = productService.addProduct(product);

        // THEN
        StepVerifier.create(result)
                .expectErrorMatches(error ->
                        error instanceof RuntimeException &&
                                error.getMessage().contains("Branch not found with id")
                )
                .verify();

        verify(productRepository, never()).save(any());
    }

    // ── deleteProduct ─────────────────────────────────────────

    @Test
    void deleteProduct_shouldComplete_whenProductExists() {
        // GIVEN
        String id = "product123";
        Product existing = Product.builder()
                .id(id)
                .name("Big Mac")
                .stock(100)
                .branchId("branch123")
                .build();

        when(productRepository.findById(id)).thenReturn(Mono.just(existing));
        when(productRepository.deleteById(id)).thenReturn(Mono.empty());

        // WHEN
        Mono<Void> result = productService.deleteProduct(id);

        // THEN
        StepVerifier.create(result)
                .verifyComplete();

        verify(productRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteProduct_shouldReturnError_whenProductNotFound() {
        // GIVEN
        String id = "noexiste";
        when(productRepository.findById(id)).thenReturn(Mono.empty());

        // WHEN
        Mono<Void> result = productService.deleteProduct(id);

        // THEN
        StepVerifier.create(result)
                .expectErrorMatches(error ->
                        error instanceof RuntimeException &&
                                error.getMessage().contains("Product not found with id")
                )
                .verify();

        verify(productRepository, never()).deleteById(any());
    }

    // ── updateStock ───────────────────────────────────────────

    @Test
    void updateStock_shouldReturnUpdatedProduct_whenProductExists() {
        // GIVEN
        String id = "product123";
        Integer newStock = 200;

        Product existing = Product.builder()
                .id(id)
                .name("Big Mac")
                .stock(100)
                .branchId("branch123")
                .build();

        Product updated = Product.builder()
                .id(id)
                .name("Big Mac")
                .stock(newStock)
                .branchId("branch123")
                .build();

        when(productRepository.findById(id)).thenReturn(Mono.just(existing));
        when(productRepository.update(any())).thenReturn(Mono.just(updated));

        // WHEN
        Mono<Product> result = productService.updateStock(id, newStock);

        // THEN
        StepVerifier.create(result)
                .expectNextMatches(p -> p.getStock().equals(200))
                .verifyComplete();

        verify(productRepository, times(1)).update(any());
    }

    // ── getTopStockProductByBranch ────────────────────────────

    @Test
    void getTopStockProductByBranch_shouldReturnProductWithHighestStock() {
        // GIVEN
        String franchiseId = "franchise123";

        Branch branch = Branch.builder()
                .id("branch123")
                .name("Sucursal Centro")
                .franchiseId(franchiseId)
                .build();

        Product product1 = Product.builder()
                .id("p1").name("Big Mac").stock(50).branchId("branch123").build();

        Product product2 = Product.builder()
                .id("p2").name("McFlurry").stock(200).branchId("branch123").build();

        Product product3 = Product.builder()
                .id("p3").name("McPollo").stock(100).branchId("branch123").build();

        when(branchRepository.findByFranchiseId(franchiseId))
                .thenReturn(Flux.just(branch));

        when(productRepository.findByBranchId("branch123"))
                .thenReturn(Flux.just(product1, product2, product3));

        // WHEN
        Flux<ProductByBranchDTO> result = productService.getTopStockProductByBranch(franchiseId);

        // THEN - debe retornar McFlurry porque tiene el mayor stock (200)
        StepVerifier.create(result)
                .expectNextMatches(dto ->
                        dto.getProductName().equals("McFlurry") &&
                                dto.getStock().equals(200) &&
                                dto.getBranchName().equals("Sucursal Centro")
                )
                .verifyComplete();
    }
}