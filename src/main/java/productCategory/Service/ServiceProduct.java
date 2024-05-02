package productCategory.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import productCategory.entity.EntityProduct;
import productCategory.repositry.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceProduct {

    @Autowired
    private ProductRepository productRepository;
   
 
 
    public EntityProduct createProduct(EntityProduct product) 
    {
    	System.out.println(product);
        return productRepository.save(product);
    }

    
    public boolean isProductExists(Long id) {
        return productRepository.existsById(id);
    }

    
    public boolean isProductExists(String name) {
        return productRepository.existsByName(name);
    }
    
    
    
    private boolean isValidProduct(EntityProduct product) 
    {
        return product != null && product.getName() != null && !product.getName().isEmpty() &&
                product.getDescription() != null && !product.getDescription().isEmpty() &&
                product.getPrice() >= 0;
    }

   
 
    @Transactional
    public EntityProduct getProductById(Long productId) 
    {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    
    @Transactional
    public List<EntityProduct> getAllProducts() 
    {
        return productRepository.findAll();
    }
    
    
    @Transactional
    public EntityProduct updateProduct(Long productId, EntityProduct product) 
    {
        Optional<EntityProduct> existingProductOptional = productRepository.findById(productId);
        if (existingProductOptional.isPresent())
        {
            EntityProduct existingProduct = existingProductOptional.get();
            
            
            if (isValidProduct(product)) 
            {
                product.setId(productId);
                return productRepository.save(product);
            } 
            else 
            {
                throw new IllegalArgumentException("Invalid product data");
            }
        } 
        
	        else 
	        {
	            throw new IllegalArgumentException("Product not found");
	        }
    }
    	
    
	    	@Transactional
	    public void deleteProduct(Long productId) 
	    {
	        productRepository.deleteById(productId);
	    }
	
	    
		}
