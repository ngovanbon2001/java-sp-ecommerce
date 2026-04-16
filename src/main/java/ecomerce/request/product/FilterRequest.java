package ecomerce.request.product;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FilterRequest {
    @Size(max = 255, message = "name tối đa 255 ký tự")
    private String name;
}
