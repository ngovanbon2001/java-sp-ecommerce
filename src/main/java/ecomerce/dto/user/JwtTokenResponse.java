package ecomerce.dto.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtTokenResponse {

    private String accessToken;

    private String refreshToken;

    private String tokenType;
}
