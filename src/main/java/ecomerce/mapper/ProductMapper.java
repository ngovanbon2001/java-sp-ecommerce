package ecomerce.mapper;

import ecomerce.dto.product.ProductDetail;
import ecomerce.entity.Product;
import org.mapstruct.factory.Mappers;

public interface ProductMapper extends BaseMapper<Product, ProductDetail>{
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
}
