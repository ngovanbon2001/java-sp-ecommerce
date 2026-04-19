package ecomerce.entity;

import ecomerce.common.Const;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Data
@Table(name = "users")
@DynamicInsert
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String phone;

    private String permission;

    private String active;

    private String email;

    @Column(name = "email_verified_at")
    private LocalDateTime emailVerifiedAt;

    private String password;

    @Column(name = "remember_token")
    private String rememberToken;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(columnDefinition = "longtext")
    private String address;

    private LocalDate birthday;

    @Column(name = "citizen_number")
    private String citizenNumber;

    private LocalDateTime created;

    private String fullname;

    private String gender;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "read_all_warnings_at")
    private LocalDateTime readAllWarningsAt;

    private Integer status;

    private Integer verified;

    @Column(name = "ward_code")
    private String wardCode;
}