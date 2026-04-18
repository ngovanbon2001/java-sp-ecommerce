package ecomerce.service;

import ecomerce.dto.product.ProductDetail;
import ecomerce.dto.product.ProductSpecification;
import ecomerce.entity.Product;
import ecomerce.mapper.ProductMapper;
import ecomerce.repository.ProductRepository;
import ecomerce.request.product.FilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<ProductDetail> index(FilterRequest filterRequest, Pageable pageable) {

        Specification<Product> productSpecification = ProductSpecification.filter(filterRequest);

        return this.productRepository.findAll(productSpecification, pageable).map(ProductMapper.INSTANCE::entityToDto);
    }
}
