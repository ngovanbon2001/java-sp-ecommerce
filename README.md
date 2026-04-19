# iHanoi Backend

Backend cho hệ thống iHanoi - Ứng dụng E-commerce Spring Boot

## 📋 Yêu cầu

- Docker (phiên bản mới nhất)
- Docker Compose
- Git

## 🚀 Bắt đầu nhanh (Khuyến nghị)

### 1. Clone dự án
```bash
git clone https://github.com/ngovanbon2001/java-sp-ecommerce.git
cd java-sp-ecommerce
```

### 2. Khởi động với Docker
```bash
docker compose up --build
```

Lệnh này sẽ:
- Build ứng dụng Spring Boot thành Docker image
- Khởi động MySQL database container
- Khởi động Spring Boot app container
- Tạo schema database tự động

**Thời gian build lần đầu:** 2-3 phút (tùy vào internet)

### 3. Kiểm tra ứng dụng
```bash
# Check status containers
docker compose ps

# View logs
docker compose logs -f
```

## 📱 Truy cập ứng dụng

| Dịch vụ | URL | Thông tin |
|---------|-----|----------|
| **API** | http://localhost:8081 | Spring Boot server |
| **Swagger UI** | http://localhost:8081/swagger-ui.html | API Documentation |
| **Health Check** | http://localhost:8081/actuator/health | App status |
| **MySQL** | localhost:3306 | Username: `root`, Password: `root` |

## 🔧 Cách sử dụng hàng ngày

### Khởi động dự án
```bash
cd /home/truonguy/java-sp-ecommerce
docker compose up -d
```

### Dừng dự án
```bash
docker compose down
```

### Dừng và xóa toàn bộ dữ liệu (MySQL)
```bash
docker compose down -v
```

### Xem logs của app
```bash
# Real-time logs
docker compose logs -f ihanoi_be

# Xem 50 dòng cuối cùng
docker compose logs ihanoi_be --tail=50
```

### Xem logs của MySQL
```bash
docker compose logs -f mysql
```

### Rebuild image (sau khi thay đổi code)
```bash
docker compose up --build -d
```

### Xem trạng thái containers
```bash
docker compose ps
```

## 🐛 Troubleshooting

### Port đã được sử dụng
Nếu port 8081 hoặc 3306 đã bị chiếm:

```bash
# Thay đổi port trong docker-compose.yml
# Từ: ports: - "8081:8081"
# Thành: ports: - "8080:8081"  (hoặc cổng khác)
```

### Build failed hoặc lỗi cache
```bash
# Rebuild từ đầu, bỏ qua cache
docker compose build --no-cache
docker compose up -d
```

### MySQL không kết nối được
```bash
# Xem logs MySQL
docker compose logs mysql

# Kiểm tra network
docker network inspect java-sp-ecommerce_default

# Reset MySQL
docker compose down -v
docker compose up -d
```

### Container crash ngay sau khi start
```bash
# Xem chi tiết lỗi
docker compose logs ihanoi_be

# Rebuild
docker compose build ihanoi_be --no-cache
docker compose up ihanoi_be
```

## 📦 Cấu trúc dự án

```
java-sp-ecommerce/
├── Dockerfile                    # Build image Spring Boot
├── docker-compose.yml            # Định nghĩa services (App + MySQL)
├── docker-compose.local.yml      # MySQL standalone config
├── pom.xml                       # Maven dependencies
├── src/
│   └── main/
│       ├── java/ecomerce/       # Application code
│       │   ├── controller/      # REST Controllers
│       │   ├── service/         # Business logic
│       │   ├── entity/          # Database models
│       │   ├── repository/      # Data access
│       │   ├── security/        # JWT & OAuth2
│       │   └── configuration/   # Spring config
│       └── resources/
│           └── application.properties
├── README.md                     # File này
└── DOCKER_SETUP.md              # Chi tiết Docker setup

```

## 🔑 Cấu hình Environment Variables

Ứng dụng hỗ trợ các biến môi trường sau (Docker tự động cấu hình):

```bash
# MySQL Configuration
MYSQL_HOST=mysql                 # Service name in Docker network
MYSQL_PORT=3306
MYSQL_DBNAME=ecommerce
MYSQL_USERNAME=root
MYSQL_PASSWORD=root

# Spring Boot Server
SERVER_PORT=8081

# OAuth2 / VneID (tùy chọn - để trống nếu không sử dụng)
VNEID_API_KEY=
VNEID_CLIENT_ID=
VNEID_CLIENT_SECRET=
VNEID_TARGET_CODE=
```

## 🛠️ Phát triển (Development)

### Chạy từ IDE
1. Đảm bảo MySQL đang chạy: `docker compose up -d mysql`
2. Mở project trong IDE (IntelliJ IDEA, VS Code, Eclipse)
3. Run `IhanoiBackendApplication.java`

### Build thủ công (không dùng Docker)
```bash
# Compile
mvn clean compile

# Package
mvn clean package -Dmaven.test.skip=true

# Chạy JAR
java -jar target/ihanoi-backend-0.0.1-SNAPSHOT.jar
```

## 📊 Công nghệ sử dụng

- **Java 17** - Programming language
- **Spring Boot 3.5.3** - Framework
- **Spring Data JPA** - ORM
- **Hibernate** - Persistence provider
- **MySQL 8** - Database
- **Spring Security** - Authentication
- **JWT** - Token-based auth
- **OAuth2** - Social login
- **Swagger/OpenAPI** - API documentation
- **Maven** - Build tool
- **Docker** - Containerization

## 📝 API Documentation

Sau khi ứng dụng khởi động, truy cập **Swagger UI** để xem đầy đủ danh sách API:

```
http://localhost:8081/swagger-ui.html
```

Hoặc JSON format:
```
http://localhost:8081/v3/api-docs
```

## 🔐 Bảo mật

- Ứng dụng sử dụng JWT tokens cho authentication
- MySQL chạy với credentials mặc định (chỉ dùng development)
- Để production: cấu hình password mạnh & environment variables an toàn

## 📞 Hỗ trợ

Nếu gặp sự cố:
1. Xem file [DOCKER_SETUP.md](DOCKER_SETUP.md) để chi tiết Docker setup
2. Check logs: `docker compose logs -f`
3. Tham khảo phần **Troubleshooting** phía trên

## 📄 License

MIT License - Xem LICENSE file để chi tiết