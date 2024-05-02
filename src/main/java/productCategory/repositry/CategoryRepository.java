package productCategory.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import productCategory.entity.EntityCategory;

@Repository
public interface CategoryRepository extends JpaRepository<EntityCategory, Long> 
{
	boolean existsByName(String categoryName);	
}
