package ecomerce.configuration.security;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RequestScope {
    private int userId;

    private String username;

    private String fullname;

    private String permission;

    private String imageProfile;

    private String profileType;

    private String primaryEmail;

    private String secondaryEmail;

    private String gender;

    private String birthdate;

    private String primaryPhone;

    private int stationId;

    private List<Integer> stationIds;

    private List<Integer> provinceIds;

    private List<Integer> regionIds;

    private int groupId;

    private int positionId;

    private List<String> admGroupIds;

    private String usrc = "anonymous";

    private String sub;
}
