package productCategory.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import productCategory.entity.EntityProduct;

@Repository
public interface ProductRepository extends JpaRepository<EntityProduct, Long> 
{

	boolean existsByName(String name);
	 
}

