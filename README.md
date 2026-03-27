# 📦 Inventory Management App

![CI Pipeline](https://github.com/abunahim/inventory-management-project/actions/workflows/ci.yml/badge.svg)
![CD Pipeline](https://github.com/abunahim/inventory-management-project/actions/workflows/cd.yml/badge.svg)

A RESTful inventory management system built with **Java Spring Boot**, developed phase-by-phase while learning DevOps practices.

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 17, Spring Boot 3.5.x |
| Database | PostgreSQL 16 |
| Caching | Redis (coming soon) |
| Build Tool | Maven |
| Containerization | Docker, Docker Compose |
| Orchestration | Kubernetes (kind) |
| CI/CD | GitHub Actions |
| Version Control | Git + GitHub |

---

## 🏗️ Architecture
```
                    Client (Postman / React)
                           ↓
                    [ Spring Boot API ]
                    (2 K8s replicas)
                           ↓
                    [ PostgreSQL 16 ]
                    (persistent storage)
```

---

## 🚀 How to Run Locally

### Prerequisites
- Java 17+
- Maven
- Docker Desktop
- kubectl

### Option 1 — Run with Docker Compose
```bash
git clone https://github.com/abunahim/inventory-management-project.git
cd inventory-management-project

# Create .env file with your credentials
# DB_NAME=inventory_db
# DB_USERNAME=postgres
# DB_PASSWORD=yourpassword

docker-compose up --build
```
App runs at: `http://localhost:8080`

### Option 2 — Run with Kubernetes
```bash
kubectl apply -f k8s/namespace.yml
kubectl apply -f k8s/configmap.yml
kubectl apply -f k8s/secret.yml
kubectl apply -f k8s/postgres-deployment.yml
kubectl apply -f k8s/postgres-service.yml
kubectl apply -f k8s/app-deployment.yml
kubectl apply -f k8s/app-service.yml

# Port forward to access the app
kubectl port-forward service/inventory-app-service 8081:8080 -n inventory
```
App runs at: `http://localhost:8081`

---

## 🧪 API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/products` | Get all products |
| POST | `/products` | Create a product |
| GET | `/products/{id}` | Get product by ID |
| PUT | `/products/{id}` | Update product |
| DELETE | `/products/{id}` | Delete product |

### Sample Request Body
```json
{
  "name": "Laptop",
  "price": 999.99,
  "quantity": 10
}
```

---

## 🔁 CI/CD Pipeline

| Trigger | Pipeline | What it does |
|---|---|---|
| Push to any branch | CI | Runs all 18 tests |
| Merge to `main` | CD | Builds and pushes Docker image to Docker Hub |

---

## 🌿 Git Branch Strategy

| Branch | Purpose |
|---|---|
| `main` | Production-ready code only |
| `dev` | Integration branch |
| `feature/*` | Individual features |

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
| 7 | Kubernetes | ✅ Done |
| 8 | PostgreSQL | ✅ Done |
| 9 | Redis (Caching) | 🔜 Next |
| 10 | React Frontend | ⏳ Pending |
| 11 | JWT Security | ⏳ Pending |
| 12 | AWS Deployment | ⏳ Pending |
| 13 | Prometheus + Grafana | ⏳ Pending |

---

## 📖 Dev Log
- **Phase 1** — Project initialized with Spring Boot. Git + GitHub configured.
- **Phase 2** — Built Product CRUD REST API, input validation, exception handling and fixed transitive CVEs.
- **Phase 3** — Added DTO layer, separating API contracts from database entities.
- **Phase 4** — Added JUnit unit tests, repository tests and MockMvc integration tests. 18/18 passing.
- **Phase 5** — Containerized app and PostgreSQL with Docker and docker-compose.
- **Phase 6** — CI/CD pipelines with GitHub Actions. Auto-tests on every push, auto-builds Docker image on merge to main. Dependabot enabled. Branch protection enabled on main.
- **Phase 7** — Added Kubernetes manifests. App runs with 2 replicas, self-healing and rolling deployments using kind.
- **Phase 8** — Migrated from MySQL to PostgreSQL. Added Spring profiles for Docker and Kubernetes environments.