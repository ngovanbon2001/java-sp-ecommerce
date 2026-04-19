# Docker Setup Guide - iHanoi Backend

## Yêu cầu
- Docker (latest version)
- Docker Compose

## Cấu hình đã được điều chỉnh

1. **Dockerfile**: Sử dụng `eclipse-temurin:17-jdk` (thay vì openjdk:21-jdk không khả dụng)
2. **application.properties**: Hỗ trợ environment variables để cấu hình MySQL, Server Port
3. **docker-compose.yml**: Gộp cả MySQL + Spring Boot app trong một file duy nhất

## Khởi động ứng dụng

### Cách 1: Chạy toàn bộ stack (khuyến nghị)
```bash
cd /home/truonguy/java-sp-ecommerce
docker compose up --build
```

Lệnh này sẽ:
- Build Spring Boot app thành Docker image
- Khởi động MySQL container  
- Khởi động Spring Boot app container
- Cả hai service sẽ liên kết qua mạng Docker

### Cách 2: Chạy riêng từng service

**Khởi động MySQL:**
```bash
docker compose -f docker-compose.local.yml up
```

**Khởi động Spring Boot App:**
```bash
docker compose up ihanoi_be --build
```

## Kiểm tra trạng thái

```bash
# Xem danh sách containers đang chạy
docker compose ps

# Xem logs của app
docker compose logs ihanoi_be

# Xem logs của MySQL
docker compose logs mysql

# Follow logs (real-time)
docker compose logs -f
```

## 📧 Test OTP (Forgot Password & Reset Password)

### Yêu cầu
- MailHog service chạy (tự động khi chạy `docker compose up`)
- Bất kỳ email nào để test OTP

### Các bước test:

1. **Mở MailHog UI** để xem email gửi đi:
   ```
   http://localhost:8025
   ```

2. **Gọi API Forgot Password** (Postman/curl):
   ```bash
   POST http://localhost:8081/api/auth/forgot-password
   Content-Type: application/json
   
   {
     "email": "user@example.com"
   }
   ```
   
   Response: `{"success": true, "message": "OTP sent to email"}`

3. **Kiểm tra OTP trong MailHog**:
   - Vào http://localhost:8025
   - Tìm email từ: `noreply@ihanoi-ecommerce.local`
   - Copy OTP (6 chữ số) từ email

4. **Gọi API Reset Password**:
   ```bash
   POST http://localhost:8081/api/auth/reset-password
   Content-Type: application/json
   
   {
     "email": "user@example.com",
     "otp": "123456",
     "newPassword": "Abcdef1@"
   }
   ```
   
   Response: `{"success": true, "message": "Password updated"}`

### Yêu cầu mật khẩu mới:
- 8-128 ký tự
- Bắt đầu bằng chữ hoa (A-Z)
- Có ít nhất 1 chữ thường (a-z), 1 số (0-9), 1 ký tự đặc biệt (@$!%*?&)
- Ví dụ hợp lệ: `Abcdef1@`, `MyPass123!`

## Truy cập ứng dụng

- **Spring Boot App**: http://localhost:8081
- **Swagger UI**: http://localhost:8081/swagger-ui.html
- **MySQL**: localhost:3306 (root/root)
- **MailHog**: http://localhost:8025 (xem email test)

## Environment Variables

Cấu hình các env var sau trước khi chạy (nếu cần thay đổi):

```bash
export MYSQL_HOST=mysql          # MySQL service name in Docker network
export MYSQL_PORT=3306
export MYSQL_DBNAME=ecommerce
export MYSQL_USERNAME=root
export MYSQL_PASSWORD=root
export SERVER_PORT=8081
export REDIS_HOST=localhost
export REDIS_PORT=6379
```

## Dừng ứng dụng

```bash
# Dừng và xóa containers
docker compose down

# Dừng và xóa cả volumes (MySQL data)
docker compose down -v
```

## Troubleshooting

### Port already in use
Nếu port 8081 hoặc 3306 đã được sử dụng:
```bash
# Thay đổi port trong docker-compose.yml
# Ví dụ, thay "8081:8081" thành "8080:8081"
```

### Build failed
```bash
# Rebuild từ đầu, bỏ qua cache
docker compose build --no-cache

# Xóa images cũ
docker image prune -a
```

### MySQL connection issue
```bash
# Kiểm tra MySQL logs
docker compose logs mysql

# Kiểm tra network
docker network inspect java-sp-ecommerce_default
```

## Notes

- Lần build đầu tiên sẽ mất thời gian (tải dependencies Maven)
- Sau lần build đầu, các lần build tiếp theo sẽ nhanh hơn do caching
- MySQL data được lưu trong `./mysql-data/` folder
