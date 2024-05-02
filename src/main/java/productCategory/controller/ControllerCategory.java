package productCategory.controller;

import org.springframework.web.bind.annotation.RestController;

import productCategory.Service.ServiceCategory;
import productCategory.entity.EntityCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class ControllerCategory 
{

    @Autowired
    private ServiceCategory categoryService;

    
    @PostMapping("/createCategory")
    public String createCategory(@RequestBody EntityCategory category) 
    {
       
        if (categoryService.isCategoryExists(category.getId()) || categoryService.isCategoryExists(category.getName())) 
        {
            return "Category with the provided id or name already exists";
        } 
        else 
        {
            
            EntityCategory createdCategory = categoryService.createCategory(category);
            
            String categoryDetails = getCategoryDetails(createdCategory);
            
            return "Category is created successfully.\n" + categoryDetails;
        }
    }
 

    
    private String getCategoryDetails(EntityCategory category) 
    {
        StringBuilder details = new StringBuilder();
        details.append("Category ID: ").append(category.getId()).append("\n");
        details.append("Category Name: ").append(category.getName()).append("\n");
        return details.toString();
    }

 
    @GetMapping ("/getAllCategories")
    public String getAllCategories(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) 
    {
        Pageable pageable = PageRequest.of(page, size);
        Page<EntityCategory> categoriesPage = categoryService.getAllCategories(pageable);
        
        StringBuilder response = new StringBuilder("All categories:\n");
        for (EntityCategory category : categoriesPage.getContent())
        {
            response.append(category.toString()).append("\n");
        }
        return response.toString();
    }


   
    @GetMapping("/getCategoryById/{id}")
    public String getCategoryById(@PathVariable("id") Long id) 
    {
        Optional<EntityCategory> category = categoryService.getCategoryById(id);
        if (category.isPresent()) 
        {
            return "Category found: " + category.get().toString();
        } 
        else 
        {
            return "Category with ID " + id + " not found";
        }
    }

   
    @PutMapping("/updateCategory/{id}")
    public EntityCategory updateCategory(@PathVariable("id") Long id,
                                     @RequestBody EntityCategory categoryDetails)
    {
     EntityCategory updatedCategory =  categoryService.updateCategory(id, categoryDetails);
        return updatedCategory;
    }
    

    
    @DeleteMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable("id") Long id) 
    {
    	categoryService.deleteCategory(id);
        return "category is deleted Successfully";
    }

}
