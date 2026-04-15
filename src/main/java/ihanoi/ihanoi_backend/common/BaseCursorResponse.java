package ihanoi.ihanoi_backend.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BaseCursorResponse<T, C> {

    private boolean success;

    private List<T> data;

    private C nextCursor;

    public BaseCursorResponse(boolean success, List<T> data, C nextCursor) {
        super();
        this.success = success;
        this.data = data;
        this.nextCursor = nextCursor;
    }


    public static <T, C> BaseCursorResponse<T, C> success(List<T> data, C nextCursor) {
        return new BaseCursorResponse<>(Const.ResultCode.SUCCESS, data, nextCursor);
    }
}
