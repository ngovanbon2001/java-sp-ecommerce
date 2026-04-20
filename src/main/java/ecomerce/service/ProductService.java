package ecomerce.service;

import ecomerce.dto.department.CreationDepartmentResult;
import ecomerce.dto.department.DepartmentDto;
import ecomerce.dto.product.ProductDetail;
import ecomerce.dto.product.ProductSpecification;
import ecomerce.entity.Department;
import ecomerce.entity.Product;
import ecomerce.exception.BizException;
import ecomerce.mapper.ProductMapper;
import ecomerce.repository.ProductRepository;
import ecomerce.request.product.CreateRequest;
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

    public ProductDetail store(CreateRequest createRequest) throws BizException {

        Product checkProduct = this.productRepository.findByName(createRequest.getName());
        if (checkProduct != null) {
            throw new BizException("Không thể tồn tai 2 sp cùng tên");
        }

//        Product productInsert = Product.builder()
//                .categoryId(createRequest.getCategoryId())
//                .brandId(createRequest.getBrandId())
//                .name(createRequest.getName())
//                .imageUrl(createRequest.getImageUrl())
//                .price(createRequest.getPrice())
//                .oldPrice(createRequest.getOldPrice())
//                .description(createRequest.getDescription())
//                .color(createRequest.getColor())
//                .specifications(createRequest.getSpecifications())
//                .tags(createRequest.getTags())
//                .isBestSell(createRequest.getIsBestSell())
//                .isNew(createRequest.getIsNew())
//                .sortOrder(createRequest.getSortOrder())
//                .active(createRequest.getActive())
//                .build();

        Product productInsert = ProductMapper.INSTANCE.toEntity(createRequest);

        this.productRepository.save(productInsert);

        return ProductMapper.INSTANCE.entityToDto(productInsert);
    }

    public ProductDetail update(Long productId, CreateRequest createRequest) throws BizException {
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new BizException("Product không tồn tại"));
        ;

        ProductMapper.INSTANCE.updateEntityFromDto(createRequest, product);

        this.productRepository.save(product);

        return ProductMapper.INSTANCE.entityToDto(product);
    }

    public void delete(Long productId) throws BizException {
        this.productRepository.findById(productId).orElseThrow(() -> new BizException("Không tìm thấy sản phẩm với id: " + productId));
        this.productRepository.deleteById(productId);
    }

    public ProductDetail detail(Long productId) throws BizException {
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new BizException("Không tìm thấy sản phẩm với id: " + productId));
        return ProductMapper.INSTANCE.entityToDto(product);
    }
}
