package ihanoi.ihanoi_backend.mapper;


import ihanoi.ihanoi_backend.dto.department.DepartmentDto;
import ihanoi.ihanoi_backend.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DepartmentMapper extends BaseMapper<Department, DepartmentDto> {
    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);
}