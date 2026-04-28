# TASK 16 - API Gateway with Load Balancing
## Spring Boot + Spring Cloud Gateway

---

## PROJECT STRUCTURE

```
task16/
├── student-service/          ← Microservice (runs on 8081 & 8082)
│   ├── pom.xml
│   └── src/main/java/com/example/studentservice/
│       ├── StudentServiceApplication.java
│       └── StudentController.java
│   └── src/main/resources/
│       └── application.properties
│
└── api-gateway/              ← API Gateway (runs on 8080)
    ├── pom.xml
    └── src/main/java/com/example/apigateway/
    │   └── ApiGatewayApplication.java
    └── src/main/resources/
        └── application.yml
```

---

## HOW TO RUN (Step-by-Step)

### Prerequisites
- Java 17+
- Maven installed

---

### STEP 1: Run Student Service — Instance 1 (Port 8081)

```bash
cd student-service
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
```

Verify: http://localhost:8081/students
Expected: `Response from Student Service - Instance running on port: 8081`

---

### STEP 2: Run Student Service — Instance 2 (Port 8082)

Open a NEW terminal:

```bash
cd student-service
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8082
```

Verify: http://localhost:8082/students
Expected: `Response from Student Service - Instance running on port: 8082`

---

### STEP 3: Run API Gateway (Port 8080)

Open another NEW terminal:

```bash
cd api-gateway
mvn spring-boot:run
```

Gateway starts on: http://localhost:8080

---

### STEP 4: Test Load Balancing

Open browser or Postman → go to:
```
http://localhost:8080/students
```

**Refresh multiple times.** You will see alternating responses:

```
Response from Student Service - Instance running on port: 8081
Response from Student Service - Instance running on port: 8082
Response from Student Service - Instance running on port: 8081
...
```

✅ This confirms Routing is working  
✅ This confirms Round-Robin Load Balancing is working  

---

## IntelliJ IDEA — Run Multiple Instances

1. Open `student-service` project
2. Run first instance normally (port = 8081 from application.properties)
3. Go to: **Run → Edit Configurations**
4. Click **+** → Spring Boot → Select `StudentServiceApplication`
5. In **Program Arguments**, add: `--server.port=8082`
6. Click **Apply** → **Run**
7. Now open `api-gateway` project → Run `ApiGatewayApplication`

---

## HOW IT WORKS (Flow)

```
Client (Browser/Postman)
        ↓
http://localhost:8080/students
        ↓
  API Gateway (8080)
  - Matches route: /students/**
  - URI: lb://STUDENT-SERVICE
        ↓
  Load Balancer (Round-Robin)
  ┌─────────────────────────┐
  │  Instance 1: 8081       │
  │  Instance 2: 8082       │
  └─────────────────────────┘
        ↓
  Response returned to Client
```

---

## EXPECTED OUTPUT

| Request # | Response                                                      |
|-----------|---------------------------------------------------------------|
| 1st       | Response from Student Service - Instance running on port: 8081 |
| 2nd       | Response from Student Service - Instance running on port: 8082 |
| 3rd       | Response from Student Service - Instance running on port: 8081 |
| 4th       | Response from Student Service - Instance running on port: 8082 |
