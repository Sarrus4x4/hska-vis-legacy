package hska.iwi.products;

import org.springframework.data.repository.CrudRepository;

import hska.iwi.products.Product;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ProductRepository extends CrudRepository<Product, Integer> {

}