package ecomerce.common;

import lombok.Getter;
import lombok.ToString;

import java.security.Key;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Const {
    public static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/swagger-ui/index.html/**",
            "/v3/api-docs/**", "/webjars/**",
            "/actuator/**",
            "/health/ping",
            "/ping",
            "/api/v1/user/login",
            "/api/v1/user/register",
            "/api/v1/user/sso-token",
            "/api/v1/address/provinces",
            "/api/v1/file/download/**",
            "/callback/**",
            "/api/v1/products/**"
    };

    public static class ResultCode {
        public static final boolean SUCCESS = true;
        public static final boolean ERROR = false;
    }

    public static class DateTime {
        public static final String TIME_ZONE = "Asia/Ho_Chi_Minh";
        public static final String SECOND_PRECISION_FORMAT = "yyyy/MM/dd-HH:mm:ss";
        public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
        public static final String DATETIME_FORMAT_PYTHON_WITH_MILISECOND = "yyyy-MM-dd HH:mm:ss.SSSSSS";
    }

    public static class Environment {
        public static final String DEV = "DEV";
        public static final String LAB = "LAB";
        public static final String PROD = "PROD";
    }

    @Getter
    public enum ConfigDataType {
        INT("int"), STRING("string"), JSON("json");

        private final String value;

        ConfigDataType(String value) {
            this.value = value;
        }
    }

    public static class Complaint {

        @Getter
        @ToString
        public enum Category {
            ANTT("An ninh trật tự"),
            TTXD("Trật tự xây dựng"),
            VMDT("Văn minh đô thị"),
            VSMT("Vệ sinh môi trường"),
            GT("Giao thông"),
            ATTP("An toàn thực phẩn"),
            CN("Cháy nổ"),
            PCTP("Phòng chống tội phạm"),
            BAO_LU("Bão, lũ"),
            DSXHK("Dân sinh xã hội khác");

            private final String description;

            Category(String description) {
                this.description = description;
            }
        }

        @Getter
        public enum Status {
            DRAFT("Bản nháp"),
            OPEN("Tạo mới"),
            REJECTED("Từ chối tiếp nhận"),
            WRONG_DESTINATION("Cơ quan báo sai"),
            WAITING("Phân phối tới cơ quan"),
            ACCEPTED("Cơ quan tiếp nhận xử lí"),
            DONE("Hoàn thành");

            private final String description;

            Status(String description) {
                this.description = description;
            }

        }

        @Getter
        public enum Level {
            URGENT("Khẩn cấp"),
            NORMAL("Bình Thường"),
            STANDARD("Cơ bản");

            private final String description;

            Level(String description) {
                this.description = description;
            }

        }

        public static Map<String, List<String>> COMBINED_STATUS = new HashMap<>();
        public static Map<String, List<String>> COMBINED_STATUS_MANAGE = new HashMap<>();


        static {
            COMBINED_STATUS.put("DRAFT", List.of(Status.DRAFT.name())); // Bản nháp
            COMBINED_STATUS.put("WAIT_ACCEPT", List.of(Status.OPEN.name())); // chờ tiếp nhận
            COMBINED_STATUS.put("ACCEPTED", List.of(Status.ACCEPTED.name(),
                    Status.WRONG_DESTINATION.name(), Status.WAITING.name(), Status.ACCEPTED.name())); // đã tiếp nhận
            COMBINED_STATUS.put("PROCESSING", List.of(Status.ACCEPTED.name())); // Đang xử lí
            COMBINED_STATUS.put("REJECTED", List.of(Status.REJECTED.name())); // từ chối tiếp nhận
            COMBINED_STATUS.put("DONE", List.of(Status.DONE.name())); // Đã xử lí

            COMBINED_STATUS_MANAGE.put("WAIT_PROCESS", List.of(Status.OPEN.name(), Status.WAITING.name(), Status.WRONG_DESTINATION.name()));
            COMBINED_STATUS_MANAGE.put("PROCESSING", List.of(Status.ACCEPTED.name()));
            COMBINED_STATUS_MANAGE.put("DONE", List.of(Status.REJECTED.name(), Status.DONE.name()));

        }

    }

    public static class Department {

        @Getter
        public enum Role {
            STAFF("Chuyên viên"),
            ADMIN("Lãnh đạo");


            private final String description;

            Role(String description) {
                this.description = description;
            }
        }
    }

    public static class User {

        @Getter
        public enum Role {
            ROOT("Quản trị viên"),
            STANDARD("Người dùng thường"),
            OFFICER("Cán bộ");

            private final String description;

            Role(String description) {
                this.description = description;
            }
        }

        public enum Gender {
            MALE,
            FEMALE
        }
    }

}
