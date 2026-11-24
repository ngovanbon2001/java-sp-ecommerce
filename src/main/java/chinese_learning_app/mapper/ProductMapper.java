package ecomerce.mapper;

import ecomerce.dto.product.ProductDetail;
import ecomerce.entity.Product;
import ecomerce.request.product.CreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper extends BaseMapper<Product, ProductDetail>{
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    void updateEntityFromDto(CreateRequest dto, @MappingTarget Product entity);

    Product toEntity(CreateRequest request);
}
