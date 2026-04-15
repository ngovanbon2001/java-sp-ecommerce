package ihanoi.ihanoi_backend.repository;

import ihanoi.ihanoi_backend.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long>, JpaSpecificationExecutor<Department> {
    Department findByName(String name);

    List<Department> findByPathStartingWithOrderByPathAsc(String path);
}
