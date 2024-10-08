package com.alten.backend.mappers;

import com.alten.backend.dto.ProductDTO;
import com.alten.backend.models.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
	
	//ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
	
	ProductDTO asProductDTO(Product product);
	List<ProductDTO> asProductDTOList(List<Product> products);

	Product asProduct(ProductDTO product);

}
