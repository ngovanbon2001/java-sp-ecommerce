package ihanoi.ihanoi_backend.dto.department;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SearchDepartmentCriteria {
    private String departmentName;
    private String wardCode;
}
