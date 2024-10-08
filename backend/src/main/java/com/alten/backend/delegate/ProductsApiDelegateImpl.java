package com.alten.backend.delegate;

import com.alten.backend.api.ProductsApiDelegate;
import com.alten.backend.dto.PageableResponse;
import com.alten.backend.dto.ProductDTO;
import com.alten.backend.dto.Response;
import com.alten.backend.services.ProductService;
import com.alten.backend.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProductsApiDelegateImpl implements ProductsApiDelegate {
	
	@Autowired
	private ProductService productService;

	@Override
	public ResponseEntity<Response> getProductById(Integer id) {
		return ResponseEntity.ok(Utils.buildResponse(productService.getProductById(id), HttpStatus.OK.value()));
	}

	@Override
	public ResponseEntity<PageableResponse> getProducts(Integer page, Integer perPage) {
		// TODO Auto-generated method stub
		Pageable pageable = Pageable.ofSize(perPage).withPage(page);
		Map<String, Object> map = productService.getProducts(pageable);
		return Utils.buildPageableResponse(map,"/products", page, perPage);
	}

    @Override
    public ResponseEntity<Response> productsPost(ProductDTO productDTO) {
        return ResponseEntity.ok(Utils.buildResponse(productService.saveProduct(productDTO), HttpStatus.CREATED.value()));
    }

	/*@Override
	public ResponseEntity<Response> updateProduct(ProductDTO productDTO) {
		return ResponseEntity.ok(Utils.buildResponse(productService.updateProduct(productDTO.getId(),productDTO), HttpStatus.OK.value()));
	}*/

	@Override
	public ResponseEntity<Response> updateProduct(Integer id, ProductDTO productDTO) {
		return ResponseEntity.ok(Utils.buildResponse(productService.updateProduct(id,productDTO), HttpStatus.OK.value()));
	}

	@Override
	public ResponseEntity<Response> deleteProduct(Integer id) {
		productService.deleteProduct(id);
		return ResponseEntity.ok(Utils.buildResponse("Product removed", HttpStatus.OK.value()));
	}
}
