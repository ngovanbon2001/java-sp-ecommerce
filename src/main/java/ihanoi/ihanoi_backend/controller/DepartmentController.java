package ihanoi.ihanoi_backend.controller;

import ihanoi.ihanoi_backend.common.BasePagingResponse;
import ihanoi.ihanoi_backend.common.BaseResponse;
import ihanoi.ihanoi_backend.dto.department.*;
import ihanoi.ihanoi_backend.exception.BizException;
import ihanoi.ihanoi_backend.service.DepartmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/departments")
@Tag(name = "Nhóm quản trị", description = "nhóm quản trị")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public BaseResponse<CreationDepartmentResult> create(@Valid @RequestBody DepartmentDto departmentDto) throws BizException {

        return BaseResponse.success(this.departmentService.createTopDepartment(departmentDto));
    }

    @GetMapping("/search")
    public BasePagingResponse<DepartmentDto> search(
            @RequestParam(name = "department_name", required = false) String departmentName,
            @RequestParam(name = "ward_code", required = false) String wardCode,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        SearchDepartmentCriteria searchDepartmentCriteria = new SearchDepartmentCriteria();
        searchDepartmentCriteria.setDepartmentName(departmentName);
        searchDepartmentCriteria.setWardCode(wardCode);

        return new BasePagingResponse<>(this.departmentService.searchDepartments(searchDepartmentCriteria, pageable));
    }

    @PostMapping("/{departmentId}/sub-departments")
    public BaseResponse<CreationDepartmentResult> createSubDepartment(@Valid @RequestBody DepartmentDto departmentDto, @PathVariable Long departmentId) throws BizException {
        return BaseResponse.success(this.departmentService.createSubDepartment(departmentDto, departmentId));
    }

    @GetMapping("/{departmentId}/sub-departments")
    public BaseResponse<DepartmentNode> viewSubDepartments(@PathVariable Long departmentId) throws BizException {
        return BaseResponse.success(this.departmentService.viewSubDepartments(departmentId));
    }

    @GetMapping("/{departmentId}")
    public BaseResponse<DepartmentDetail> getDepartmentDetail(@PathVariable Long departmentId) throws BizException {
        return BaseResponse.success(departmentService.getDepartmentDetail(departmentId));
    }

    @PutMapping("/{departmentId}")
    public BaseResponse<DepartmentDto> updateDepartment(@PathVariable Long departmentId, @Valid @RequestBody DepartmentDto updatingDepartmentDto) throws BizException {
        return BaseResponse.success(this.departmentService.updateDepartment(departmentId, updatingDepartmentDto));
    }

    @DeleteMapping("/{departmentId}")
    public BaseResponse<String> deleteDepartment(@PathVariable Long departmentId) throws BizException {

        this.departmentService.removeDepartment(departmentId);
        return BaseResponse.success("OK");
    }
}
