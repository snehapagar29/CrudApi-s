package productCategory.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import productCategory.entity.EntityCategory;
import productCategory.repositry.CategoryRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

@Service
public class ServiceCategory {

    @Autowired
    private CategoryRepository categoryRepository;

   
    public EntityCategory createCategory(EntityCategory category) 
    { 	
            return categoryRepository.save(category); 
    }
    
    
    public boolean isCategoryExists(String name) 
    {
        return categoryRepository.existsByName(name);
    }

    
    public boolean isCategoryExists(Long id) 
    {
        return categoryRepository.existsById(id);
    }

 
    public Page<EntityCategory> getAllCategories(Pageable pageable) 
    {
        
    	return categoryRepository.findAll(pageable);
    }
    
    
    public Optional<EntityCategory> getCategoryById(Long id) 
    {
        return categoryRepository.findById(id);
    }

    
    public EntityCategory updateCategory(Long id, EntityCategory categoryDetails) 
    {
        Optional<EntityCategory> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) 
        {
            EntityCategory category = optionalCategory.get();
            category.setName(categoryDetails.getName());
            return categoryRepository.save(category);
        } 
        
        else
        {
            throw new RuntimeException("Category not found with id: " + id);
        }
    }

   
    public void deleteCategory(Long id) 
    {
        Optional<EntityCategory> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) 
        {
            categoryRepository.deleteById(id);
        } 
        else
        {
            throw new RuntimeException("Category not found with id: " + id);
        }
		
    }
    
    

    
}
