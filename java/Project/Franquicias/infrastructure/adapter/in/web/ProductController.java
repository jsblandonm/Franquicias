package Project.Franquicias.infrastructure.adapter.in.web;

import Project.Franquicias.models.DTO.ProductByBranchDTO;
import Project.Franquicias.models.Product;
import Project.Franquicias.port.in.ProductUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductUseCase productUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> addProduct(@RequestBody @Valid Product product) {
        return productUseCase.addProduct(product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProduct(@PathVariable String id) {
        return productUseCase.deleteProduct(id);
    }

    @PatchMapping("/{id}/stock")
    public Mono<Product> updateStock(@PathVariable String id,
                                     @RequestParam Integer newStock) {
        return productUseCase.updateStock(id, newStock);
    }

    @PatchMapping("/{id}/name")
    public Mono<Product> updateName(@PathVariable String id,
                                    @RequestParam String newName) {
        return productUseCase.updateProductName(id, newName);
    }

    @GetMapping("/top-stock/franchise/{franchiseId}")
    public Flux<ProductByBranchDTO> getTopStockByBranch(@PathVariable String franchiseId) {
        return productUseCase.getTopStockProductByBranch(franchiseId);
    }
}
