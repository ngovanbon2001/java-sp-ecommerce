package ecomerce.mapper;


import ecomerce.dto.department.DepartmentDto;
import ecomerce.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DepartmentMapper extends BaseMapper<Department, DepartmentDto> {
    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);
}