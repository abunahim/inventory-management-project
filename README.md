# 📦 Inventory Management App

![CI Pipeline](https://github.com/abunahim/inventory-management-project/actions/workflows/ci.yml/badge.svg)
![CD Pipeline](https://github.com/abunahim/inventory-management-project/actions/workflows/cd.yml/badge.svg)

A RESTful inventory management system built with **Java Spring Boot**, developed phase-by-phase while learning DevOps practices.

---

## 🛠️ Tech Stack
| Layer | Technology |
|---|---|
| Backend | Java 17, Spring Boot 3.5.x |
| Database | MySQL |
| Build Tool | Maven |
| Containerization | Docker, Docker Compose |
| CI/CD | GitHub Actions |
| Version Control | Git + GitHub |

---

## 🚀 How to Run Locally

### Prerequisites
- Java 17+
- Maven
- MySQL
- Docker Desktop

### Run with Docker
```bash
git clone https://github.com/abunahim/inventory-management-project.git
cd inventory-management-project
docker-compose up --build
```
App runs at: `http://localhost:8080`

### Run without Docker
```bash
cd inventory-management-project/backend/inventory-management
./mvnw spring-boot:run
```

---

## 📍 Project Phases

| Phase | Description | Status |
|---|---|---|
| 1 | Git setup + Spring Boot skeleton | ✅ Done |
| 2 | REST API + CRUD + Validation | ✅ Done |
| 3 | DTO Layer | ✅ Done |
| 4 | Testing (JUnit) | ✅ Done |
| 5 | Docker | ✅ Done |
| 6 | CI/CD with GitHub Actions | ✅ Done |
| 7 | Kubernetes | 🔜 Next |
| 8 | Cloud Deployment | ⏳ Pending |
| 9 | Monitoring (Prometheus + Grafana) | ⏳ Pending |

---

## 🔁 CI/CD Pipeline

| Trigger | Pipeline | What it does |
|---|---|---|
| Push to any branch | CI | Runs all 18 tests |
| Merge to `main` | CD | Builds & pushes Docker image to Docker Hub |

---

## 🧪 API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/products` | Get all products |
| POST | `/products` | Create a product |
| GET | `/products/{id}` | Get product by ID |
| PUT | `/products/{id}` | Update product |
| DELETE | `/products/{id}` | Delete product |

---

## 📖 Dev Log
- **Phase 1** — Project initialized with Spring Boot. Git + GitHub configured.
- **Phase 2** — Built Product CRUD REST API, input validation, exception handling and fixed transitive CVEs.
- **Phase 3** — Added DTO layer, separating API contracts from database entities.
- **Phase 4** — Added JUnit unit tests, repository tests and MockMvc integration tests. 18/18 passing.
- **Phase 5** — Containerized app and MySQL with Docker and docker-compose.
- **Phase 6** — CI/CD pipelines with GitHub Actions. Auto-tests on every push, auto-builds Docker image on merge to main. Dependabot enabled for vulnerability scanning. Branch protection enabled on main.