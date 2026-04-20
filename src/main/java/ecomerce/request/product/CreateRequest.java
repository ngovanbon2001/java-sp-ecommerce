package ecomerce.request.product;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateRequest {
    @NotNull(message = "category_id không được null")
    private Integer categoryId;

    @NotNull(message = "brand_id không được null")
    private Integer brandId;

    @NotBlank(message = "name không được để trống")
    @Size(max = 255, message = "name tối đa 255 ký tự")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "name không được chứa ký tự đặc biệt")
    private String name;

    @Size(max = 255, message = "image_url tối đa 255 ký tự")
    private String imageUrl;

    @DecimalMin(value = "0.0", inclusive = true, message = "price phải >= 0")
    private Double price;

    @DecimalMin(value = "0.0", inclusive = true, message = "old_price phải >= 0")
    private Double oldPrice;

    private String description;

    @NotBlank(message = "color không được để trống")
    @Size(max = 255, message = "color tối đa 255 ký tự")
    private String color;

    @NotBlank(message = "specifications không được để trống")
    private String specifications;

    private String tags;

    @NotNull(message = "is_best_sell không được null")
    private Boolean isBestSell;

    @NotNull(message = "is_new không được null")
    private Boolean isNew;

    private Integer sortOrder;

    @NotNull(message = "active không được null")
    private Boolean active;
}
