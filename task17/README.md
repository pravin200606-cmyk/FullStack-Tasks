# Task 17: Inter-Service Communication using REST
## Spring Boot Microservices | Eclipse + MySQL Workbench

---

## 📁 Project Structure

```
microservices-task17/
├── student-service/       ← Microservice 1 (Port 8081) — provides student data
├── course-service/        ← Microservice 2 (Port 8082) — consumes student API
└── mysql-setup.sql        ← Run this in MySQL Workbench first
```

---

## ✅ Prerequisites

| Tool | Version |
|------|---------|
| Java JDK | 17+ |
| Eclipse IDE | 2023+ (with Spring Tools or standard Java IDE) |
| Maven | 3.6+ (bundled with Eclipse) |
| MySQL Server | 8.0+ |
| MySQL Workbench | Any recent version |
| Postman / Browser | For testing |

---

## 🗄️ STEP 1: MySQL Workbench Setup

1. Open **MySQL Workbench**
2. Connect to your local MySQL server (usually `localhost:3306`)
3. Open a new SQL tab and run the script: **`mysql-setup.sql`**
4. This creates `studentdb` database and inserts sample data

---

## ⚙️ STEP 2: Update Password in application.properties

Open:
```
student-service/src/main/resources/application.properties
```

Change:
```properties
spring.datasource.password=your_mysql_password
```
→ Replace `your_mysql_password` with your actual MySQL root password.

---

## 🛠️ STEP 3: Import Projects into Eclipse

### Import student-service:
1. `File → Import → Maven → Existing Maven Projects`
2. Browse to: `microservices-task17/student-service/`
3. Click **Finish**
4. Wait for Maven to download dependencies (first time takes a few minutes)

### Import course-service:
1. Repeat same steps
2. Browse to: `microservices-task17/course-service/`
3. Click **Finish**

---

## ▶️ STEP 4: Run the Applications

### Run Student Service FIRST:
1. Open `StudentServiceApplication.java`
2. Right-click → **Run As → Java Application**
3. Wait for: `Started StudentServiceApplication on port 8081`

### Then Run Course Service:
1. Open `CourseServiceApplication.java`
2. Right-click → **Run As → Java Application**
3. Wait for: `Started CourseServiceApplication on port 8082`

---

## 🧪 STEP 5: Test the APIs

### Test Student Service directly:
```
GET http://localhost:8081/students
```
Expected:
```json
[
  {"id":1,"name":"John","department":"AI"},
  {"id":2,"name":"Alice","department":"DS"}
]
```

### Test via Course Service (inter-service communication):
```
GET http://localhost:8082/courses/students
```
→ Same result, but fetched BY Course Service FROM Student Service

### Test by ID:
```
GET http://localhost:8082/courses/students/1
```

### Test Fallback (Circuit Breaker):
1. **Stop** the Student Service
2. Call: `GET http://localhost:8082/courses/students`
3. Expected: `"Fallback: Student Service is currently DOWN!..."`

---

## 🔌 API Endpoints Summary

| Service | Method | URL | Description |
|---------|--------|-----|-------------|
| Student Service | GET | `localhost:8081/students` | Get all students |
| Student Service | GET | `localhost:8081/students/{id}` | Get student by ID |
| Student Service | POST | `localhost:8081/students` | Add a student |
| Student Service | DELETE | `localhost:8081/students/{id}` | Delete a student |
| Course Service | GET | `localhost:8082/courses/students` | Fetch all students (via REST) |
| Course Service | GET | `localhost:8082/courses/students/{id}` | Fetch student by ID (via REST) |

---

## 🔁 Architecture Flow

```
Client (Browser/Postman)
       ↓
  Course Service (8082)
       ↓  [RestTemplate + Circuit Breaker]
  Student Service (8081)
       ↓  [Spring Data JPA]
  MySQL Database (3306)
```

---

## ⚡ Circuit Breaker Behavior (Resilience4j)

| Circuit State | Condition | What Happens |
|--------------|-----------|--------------|
| CLOSED | Normal operation | Calls pass through to Student Service |
| OPEN | 50%+ failures in last 10 calls | Returns fallback immediately, no call made |
| HALF-OPEN | After 10 seconds | Allows 3 test calls to check if service recovered |

---

## 🐛 Common Errors & Fixes

| Error | Fix |
|-------|-----|
| `Access denied for user 'root'` | Update password in `application.properties` |
| `Unknown database 'studentdb'` | Run `mysql-setup.sql` in MySQL Workbench first |
| `Port 8081 already in use` | Kill the process or change port in properties |
| `Bean not found: RestTemplate` | Check `AppConfig.java` exists in `config` package |
| `CircuitBreaker not activating` | Ensure `spring-boot-starter-aop` dependency is present |
| Maven download errors | Right-click project → Maven → Update Project |

---

## 📦 Adding a Student via POST (Postman/curl)

**POST** `http://localhost:8081/students`
```json
{
  "name": "Rahul",
  "department": "ECE"
}
```

---

*Task 17 — Spring Boot Microservices with REST Inter-Service Communication*
