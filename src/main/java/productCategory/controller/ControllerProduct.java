package productCategory.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import productCategory.Service.ServiceProduct;
import productCategory.entity.EntityCategory;
import productCategory.entity.EntityProduct;

@RestController
@RequestMapping("/api/products")
public class ControllerProduct {

    @Autowired
    private ServiceProduct productService;
 
    
    @PostMapping("/createProduct")
    public ResponseEntity<String> createProduct(@RequestBody EntityProduct product) 
    {
        
        if (productService.isProductExists(product.getId()) || productService.isProductExists(product.getName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product with the provided id or name already exists");
        } else {
            EntityProduct createdProduct = productService.createProduct(product);
            
            String productDetails = getProductDetails(createdProduct);
            
            return ResponseEntity.status(HttpStatus.CREATED).body("Product is created successfully.\n" + productDetails);
        }
    }

  
    private String getProductDetails(EntityProduct product) {
        StringBuilder details = new StringBuilder();
        details.append("Product ID: ").append(product.getId()).append("\n");
        details.append("Product Name: ").append(product.getName()).append("\n");
        details.append("Product Description: ").append(product.getDescription()).append("\n");
        details.append("Product Price: ").append(product.getPrice()).append("\n");
        
        return details.toString();
    }
    
	
    @GetMapping("/getProductById/{id}")
    public ResponseEntity<EntityProduct> getProductById(@PathVariable("id") Long productId) {
        EntityProduct product = productService.getProductById(productId);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    
    @GetMapping("/getAllProducts")
    public ResponseEntity<List<EntityProduct>> getAllProducts() {
        List<EntityProduct> products = productService.getAllProducts();
        return products.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(products);
    }

    
    @PutMapping("updateProduct/{Id}")
    public ResponseEntity<EntityProduct> updateProduct(@PathVariable Long productId, @RequestBody EntityProduct product) {
        try 
        {
            EntityProduct updatedProduct = productService.updateProduct(productId, product);
            return ResponseEntity.ok(updatedProduct);
        } 
        catch (IllegalArgumentException ex) 
        {
            return ResponseEntity.notFound().build();
        }
    }

   
    @DeleteMapping("/deleteProduct/{Id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
