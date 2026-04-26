package Project.Franquicias.port.in;

import Project.Franquicias.models.DTO.ProductByBranchDTO;
import Project.Franquicias.models.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductUseCase {

    Mono<Product> addProduct(Product product);
    Mono<Void> deleteProduct(String id);
    Mono<Product> updateStock(String id, Integer newStock);
    Mono<Product> updateProductName(String id, String newName);
    Flux<ProductByBranchDTO> getTopStockProductByBranch(String franchiseId);

}
