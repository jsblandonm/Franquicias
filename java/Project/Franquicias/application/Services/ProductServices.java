package Project.Franquicias.application.Services;

import Project.Franquicias.models.DTO.ProductByBranchDTO;
import Project.Franquicias.models.Product;
import Project.Franquicias.port.in.ProductUseCase;
import Project.Franquicias.port.out.BranchRepository;
import Project.Franquicias.port.out.FranchiseRepository;
import Project.Franquicias.port.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductServices implements ProductUseCase {

    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;


    @Override
    public Mono<Product> addProduct(Product product) {
        return branchRepository.findById(product.getBranchId())
                .switchIfEmpty(Mono.error(
                        new RuntimeException("branch not found with id:" + product.getBranchId())
                ))
                .flatMap(existingBranch -> productRepository.save(product));
    }

    @Override
    public Mono<Void> deleteProduct(String id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("product not found with id: " + id)))
                .flatMap(existingProduct -> productRepository.deleteById(id));
    }

    @Override
    public Mono<Product> updateStock(String id, Integer newStock) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("product not found with id: " + id)))
                .flatMap(existingProduct -> {
                    existingProduct.setStock(newStock);
                    return productRepository.update(existingProduct);
                });
    }

    @Override
    public Mono<Product> updateProductName(String id, String newName) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("product not found with id: " + id)))
                .flatMap(existingProduct -> {
                    existingProduct.setName(newName);
                    return productRepository.update(existingProduct);
                });
    }

    @Override
    public Flux<ProductByBranchDTO> getTopStockProductByBranch(String franchiseId) {
        return branchRepository.findByFranchiseId(franchiseId)
                .flatMap(branch ->
                        productRepository.findByBranchId(branch.getId())
                                .sort((p1, p2) -> p2.getStock() - p1.getStock())
                                .next()
                                .map(product -> new ProductByBranchDTO(
                                        branch.getId(),
                                        branch.getName(),
                                        product.getId(),
                                        product.getName(),
                                        product.getStock()
                                ))
                        );
    }

}

