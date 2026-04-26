package Project.Franquicias.models.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductByBranchDTO {

    private String branchId;
    private String branchName;
    private String productId;
    private String productName;
    private Integer stock;

    public ProductByBranchDTO() {
    }

    public ProductByBranchDTO(String branchId, String branchName, String productId, String productName, Integer stock) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.productId = productId;
        this.productName = productName;
        this.stock = stock;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
