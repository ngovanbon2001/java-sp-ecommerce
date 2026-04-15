package ihanoi.ihanoi_backend.entity;

import ihanoi.ihanoi_backend.common.Const;
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
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, length = 128)
    private String username;

    @Column(name = "password", nullable = false, length = 256)
    private String password;

    @Column(name = "address", nullable = false, length = Integer.MAX_VALUE)
    private String address;

    @Column(name = "ward_code", nullable = false)
    private String wardCode;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "fullname", nullable = false)
    private String fullname;

    @ColumnDefault("now()")
    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @ColumnDefault("1")
    @Column(name = "status", nullable = false)
    private Short status;

    @ColumnDefault("1")
    @Column(name = "verified", nullable = false)
    private Short verified;

    @ColumnDefault("standard")
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Const.User.Role role;

    @Size(max = 255)
    @Column(name = "email")
    private String email;

    @Size(max = 255)
    @Column(name = "citizen_number")
    private String citizenNumber;

    @Type(JsonBinaryType.class)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "avatar", columnDefinition = "jsonb")
    private String avatar;

    @Size(max = 10)
    @Column(name = "gender", length = 10)
    private String gender;


    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "read_all_warnings_at")
    private ZonedDateTime readAllWarningsAt;

    @OneToMany(mappedBy = "user")
    private List<String> staffDepartments = new ArrayList<>();

}