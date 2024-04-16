package hska.iwi.products;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ProductRepository extends CrudRepository<Product, Integer> {
    List<Product> findByDetailsContainsAndPriceBetween(String details, Double lower, Double upper);

    //@Query("SELECT p FROM Product p WHERE p.details like %:details% and (:lower is null or p.price >= :lower) and (:upper is null or p.price <= :upper)")
    //List<Product> findByDetailsAndPrice(@Param("details") String details, @Param("lower") Double lower, @Param("upper") Double upper);
}