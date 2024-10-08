package com.alten.backend.services;

import com.alten.backend.dto.ProductDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;


public interface ProductService {
	
	Map<String, Object> getProducts(Pageable pageable);
	ProductDTO getProductById(Integer id);

	ProductDTO saveProduct(ProductDTO dto) ;


	@Transactional
	ProductDTO updateProduct(Integer id, ProductDTO partialData);

	void deleteProduct(Integer id);
}
