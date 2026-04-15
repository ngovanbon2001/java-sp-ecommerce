package ihanoi.ihanoi_backend.repository;

import ihanoi.ihanoi_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findByPhoneNumber(String phoneNumber);

    User findByUsername(String username);

    User findByCitizenNumber(String citizenNumber);
}