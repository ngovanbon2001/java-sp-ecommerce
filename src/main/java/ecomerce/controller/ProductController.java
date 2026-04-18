package ecomerce.controller;

import ecomerce.common.BasePagingResponse;
import ecomerce.dto.product.ProductDetail;
import ecomerce.request.product.FilterRequest;
import ecomerce.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Sản phẩm", description = "Quản lý sản phẩm")
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/index")
    public BasePagingResponse<ProductDetail> index(@Valid @RequestBody FilterRequest filterRequest, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        log.debug("products.index");
        return new BasePagingResponse<>(this.productService.index(filterRequest, pageable));
    }
}
