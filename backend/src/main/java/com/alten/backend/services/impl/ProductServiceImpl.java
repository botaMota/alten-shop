package com.alten.backend.services.impl;

import com.alten.backend.dto.CollectionsInner;
import com.alten.backend.dto.ProductDTO;
import com.alten.backend.exceptions.FunctionalException;
import com.alten.backend.mappers.ProductMapper;
import com.alten.backend.models.Product;
import com.alten.backend.repositories.ProductRepository;
import com.alten.backend.services.ProductService;
import com.alten.backend.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
    private ProductRepository productRepository;
	
	@Autowired
	private ProductMapper mapper;

	@Override
	public Map<String, Object> getProducts(Pageable pageable) {
		Map<String,Object> map = new HashMap<>();
		Page<Product> page = productRepository.findAll(pageable);
		
		
		List<CollectionsInner> plansDto = List.copyOf(mapper.asProductDTOList(page.getContent()));
		
		map.put("data", plansDto);
		Double nbResult = (double) page.getTotalElements();
        map.put("nbResult", nbResult);
        map.put("getTotalElements", page.getTotalElements());
        map.put("getTotalPages", page.getTotalPages());
		
		return map;
	}

	@Override
	public ProductDTO getProductById(Integer id) {
		return mapper.asProductDTO(productRepository.findById(id).get());
	}

	@Override
	public ProductDTO saveProduct(ProductDTO productDTO) {

		Optional<Product> existingProductOptional = productRepository.findById(productDTO.getId());
		if(!existingProductOptional.isPresent()){
			Product product = productRepository.save(mapper.asProduct(productDTO));
			return mapper.asProductDTO(product);
		}
		throw new FunctionalException("Product Already exist");
	}

	@Transactional
	@Override
	public ProductDTO updateProduct(Integer id, ProductDTO partialData)  {
		Optional<Product> existingProductOptional = productRepository.findById(id);
		if (!existingProductOptional.isPresent()) {
			throw new NotFoundException("Product not found with the id : "+id);
		}
		Product existingProduct = existingProductOptional.get();
        try {
            Utils.updateNonNullProperties(partialData,existingProduct);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return mapper.asProductDTO(productRepository.save(existingProduct));
	}

	@Override
	public void deleteProduct(Integer id) {
		Optional<Product> product = productRepository.findById(id);
		if (!product.isPresent()) {
			throw new NotFoundException("Product not found with the id : "+id);
		}
		productRepository.delete(product.get());
	}


}
