package ecomerce.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "IHANOI REST API", description = "IHANOI REST API", version = "v1.0", contact = @Contact(name = "IHANOI"), license = @License(name = "Apache License Version 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0")))
@SecurityScheme(name = "Token Bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "Bearer")
public class SwaggerConfig {
}
