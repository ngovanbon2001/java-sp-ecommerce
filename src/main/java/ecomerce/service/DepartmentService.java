package ecomerce.service;

import ecomerce.dto.department.*;
import ecomerce.repository.DepartmentRepository;
import ecomerce.repository.UserRepository;
import ecomerce.entity.Department;
import ecomerce.exception.BizException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;

    public DepartmentService(
            DepartmentRepository departmentRepository,
            UserRepository userRepository
    ) {
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
    }

    public CreationDepartmentResult createTopDepartment(DepartmentDto departmentDto) throws BizException {
        // if (!this.authorizationService.isRoot()) {
        //     throw new AccessDeniedException("Từ chối truy cập");
        // }

        // Department checkDepartment = departmentRepository.findByName(departmentDto.getName());
        // if (checkDepartment != null) {
        //     throw new BizException("Phòng ban cùng tên đã tồn tại trong hệ thống");
        // }

        // Department department = DepartmentMapper.INSTANCE.dtoToEntity(departmentDto);
        // department.setOwner(this.authorizationService.getCurrentUser());
        // this.departmentRepository.save(department);

        // Department insertedDepartment = this.departmentRepository.findByName(department.getName());
        // insertedDepartment.setPath("/" + insertedDepartment.getId() + "/");
        // this.departmentRepository.save(insertedDepartment);

        // return CreationDepartmentMapper.INSTANCE.entityToDto(insertedDepartment);
        return null;
    }

    public CreationDepartmentResult createSubDepartment(@Valid DepartmentDto departmentDto, Long parentDepartmentId) throws BizException {
        // if (!this.authorizationService.isRoot() && !this.authorizationService.isAdmin(parentDepartmentId)) {
        //     throw new AccessDeniedException("Từ chối truy cập.");
        // }

        // Department checkDepartment = departmentRepository.findByName(departmentDto.getName());
        // if (checkDepartment != null) {
        //     throw new BizException("Phòng ban cùng tên đã tồn tại trong hệ thống.");
        // }

        // Department parentDepartment = this.departmentRepository.findById(parentDepartmentId).orElseThrow(
        //         () -> new BizException("Không tìm thấy phòng ban với id: " + parentDepartmentId)
        // );

        // Department department = DepartmentMapper.INSTANCE.dtoToEntity(departmentDto);
        // department.setOwner(this.authorizationService.getCurrentUser());
        // this.departmentRepository.save(department);

        // Department insertedDepartment = this.departmentRepository.findByName(department.getName());
        // insertedDepartment.setPath(parentDepartment.getPath() + insertedDepartment.getId() + "/");
        // this.departmentRepository.save(insertedDepartment);

        // return CreationDepartmentMapper.INSTANCE.entityToDto(insertedDepartment);
        return null;
    }

    public DepartmentNode viewSubDepartments(Long departmentId) throws BizException {
        // if (!this.authorizationService.isRoot() && !this.authorizationService.isAdmin(departmentId)) {
        //     throw new AccessDeniedException("Từ chối truy cập.");
        // }

        // Department department = this.departmentRepository.findById(departmentId).orElseThrow(
        //         () -> new BizException("Không tìm thấy phòng ban với id: " + departmentId)
        // );

        // List<Department> subDepartmentList = this.departmentRepository.findByPathStartingWithOrderByPathAsc(department.getPath());

        // HashMap<Long, DepartmentNode> subDepartmentMap = new HashMap<>();
        // for (Department subDepartment : subDepartmentList) {
        //     subDepartmentMap.put(subDepartment.getId(), DepartmentNode.builder()
        //             .id(subDepartment.getId())
        //             .name(subDepartment.getName())
        //             .description(subDepartment.getDescription())
        //             .subDepartmentList(new ArrayList<>())
        //             .build()
        //     );
        // }

        // for (Department subDepartment : subDepartmentList) {
        //     String[] paths = subDepartment.getPath().split("/");
        //     if (paths.length <= 2) continue; // root node

        //     Long parentDepartmentId = Long.parseLong(paths[paths.length - 2]);
        //     DepartmentNode parentNode = subDepartmentMap.get(parentDepartmentId);

        //     if (parentNode == null) continue; // current node

        //     parentNode.getSubDepartmentList().add(subDepartmentMap.get(subDepartment.getId()));
        // }

        // // return root node:
        // return subDepartmentMap.get(subDepartmentList.getFirst().getId());
        return null;
    }

    public DepartmentDetail getDepartmentDetail(Long departmentId) throws BizException {
        // if (!this.authorizationService.isRoot() && !this.authorizationService.isAdmin(departmentId) && !this.authorizationService.isStaff(departmentId)) {
        //     throw new AccessDeniedException("Từ chối truy cập.");
        // }
        // Department department = this.departmentRepository.findById(departmentId).orElseThrow(() -> new BizException("Không tìm thấy phòng ban với id: " + departmentId));
        // List<Long> staffIds = department.getStaffDepartments().stream().map(StaffDepartment::getUser).map(User::getId).toList();
        // Map<Long, StaffDepartment> staffDepartmentRole = department.getStaffDepartments().stream()
        //         .collect(Collectors.toMap(
        //                 sd -> sd.getUser().getId(),
        //                 Function.identity()
        //         ));
        // List<User> users = userRepository.findAllById(staffIds);
        // List<DepartmentDetail.User> departmentDetailUsers = new ArrayList<>();
        // for (User user : users) {
        //     if (staffDepartmentRole.containsKey(user.getId())) {
        //         departmentDetailUsers.add(DepartmentDetail.User.builder()
        //                 .id(user.getId())
        //                 .fullname(user.getFullname())
        //                 .phoneNumber(user.getPhoneNumber())
        //                 .role(staffDepartmentRole.get(user.getId()).getRole().name())
        //                 .build());
        //     }
        // }
        // return DepartmentDetail.builder()
        //         .id(department.getId())
        //         .name(department.getName())
        //         .description(department.getDescription())
        //         .wards(WardMapper.INSTANCE.entityToDto(department.getWards().stream().toList()))
        //         .users(departmentDetailUsers)
        //         .build();
        return null;
    }

    public Page<DepartmentDto> searchDepartments(
            SearchDepartmentCriteria searchDepartmentCriteria,
            Pageable pageable
    ) {

//         DepartmentSpecification departmentSpecification = new DepartmentSpecification(searchDepartmentCriteria);

        // return this.departmentRepository.findAll(departmentSpecification, pageable).map(
        //         DepartmentMapper.INSTANCE::entityToDto
        // );

        return null;
    }

    public DepartmentDto updateDepartment(Long departmentId, DepartmentDto updatingDepartmentDto) throws BizException {
        // if (!this.authorizationService.isRoot() && !this.authorizationService.isAdmin(departmentId)) {
        //     throw new AccessDeniedException("Từ chối truy cập.");
        // }

        // Department department = this.departmentRepository.findById(departmentId).orElseThrow(
        //         () -> new BizException("Không tìm thấy phòng ban với id: " + departmentId)
        // );

        // department.setName(updatingDepartmentDto.getName());
        // department.setDescription(updatingDepartmentDto.getDescription());

        // this.departmentRepository.save(department);

        // return DepartmentMapper.INSTANCE.entityToDto(department);

        return null;

    }

    @Transactional
    public void removeDepartment(Long departmentId) throws BizException {
        // if (!this.authorizationService.isRoot() && !this.authorizationService.isAdmin(departmentId)) {
        //     throw new AccessDeniedException("Từ chối truy cập.");
        // }

        // Department department = this.departmentRepository.findById(departmentId).orElseThrow(
        //         () -> new BizException("Không tìm thấy phòng ban với id: " + departmentId)
        // );

        // List<Department> subDepartmentList = this.departmentRepository.findByPathStartingWithOrderByPathAsc(department.getPath());
        // if (subDepartmentList.size() > 1) {
        //     throw new BizException("Tồn tại phòng ban cấp thấp hơn. Không thể xóa phòng ban này.");
        // }

        // this.departmentRepository.deleteById(departmentId);
    }

    public Department findDepartmentById(Long departmentId) {
        return this.departmentRepository.findById(departmentId).orElse(null);
    }
}
