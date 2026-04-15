package ihanoi.ihanoi_backend.mapper;

import ihanoi.ihanoi_backend.dto.user.UserDto;
import ihanoi.ihanoi_backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = StaffDepartmentMapper.class)
public interface UserMapper extends BaseMapper<User, UserDto> {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
}