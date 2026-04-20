package ecomerce.controller;

import ecomerce.common.BasePagingResponse;
import ecomerce.common.BaseResponse;
import ecomerce.dto.department.DepartmentDetail;
import ecomerce.dto.department.DepartmentDto;
import ecomerce.dto.product.ProductDetail;
import ecomerce.exception.BizException;
import ecomerce.request.product.CreateRequest;
import ecomerce.request.product.FilterRequest;
import ecomerce.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Sản phẩm", description = "Quản lý sản phẩm")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/index")
    public BasePagingResponse<ProductDetail> index(@Valid @RequestBody FilterRequest filterRequest, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return new BasePagingResponse<>(this.productService.index(filterRequest, pageable));
    }

    @PostMapping("/store")
    public BaseResponse<ProductDetail> store(@Valid @RequestBody CreateRequest createRequest) throws BizException {
        return BaseResponse.success(this.productService.store(createRequest));
    }

    @PutMapping("/update/{productId}")
    public BaseResponse<ProductDetail> updateDepartment(@PathVariable Long productId, @Valid @RequestBody CreateRequest updateRequest) throws BizException {
        return BaseResponse.success(this.productService.update(productId, updateRequest));
    }

    @GetMapping("/{productId}")
    public BaseResponse<ProductDetail> detail(@PathVariable Long productId) throws BizException {
        return BaseResponse.success(productService.detail(productId));
    }

    @DeleteMapping("/{productId}")
    public BaseResponse<String> delete(@PathVariable Long productId) throws BizException {
        this.productService.delete(productId);
        return BaseResponse.success("OK");
    }
}
