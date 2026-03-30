# 📦 Inventory Management App

![CI Pipeline](https://github.com/abunahim/inventory-management-project/actions/workflows/ci.yml/badge.svg)
![CD Pipeline](https://github.com/abunahim/inventory-management-project/actions/workflows/cd.yml/badge.svg)

A full-stack inventory management system built with **Java Spring Boot** and **React**, developed phase-by-phase while learning DevOps practices.

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 17, Spring Boot 3.5.x |
| Frontend | React 18, Vite, Axios |
| Database | PostgreSQL 16 |
| Caching | Redis 7.2 |
| Build Tool | Maven |
| Containerization | Docker, Docker Compose |
| Orchestration | Kubernetes (kind) |
| CI/CD | GitHub Actions |
| Version Control | Git + GitHub |

---

## 🏗️ Architecture
```
                    [ React Frontend ]
                    (localhost:5173)
                           ↓
                    [ Spring Boot API ]
                    (2 K8s replicas)
                         ↙   ↘
              [ Redis Cache ]  [ PostgreSQL 16 ]
              (cache reads)    (persistent storage)
```

---

## 🚀 How to Run Locally

### Prerequisites
- Java 17+
- Maven
- Docker Desktop
- Node.js 24+
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
Backend runs at: `http://localhost:8080`

### Option 2 — Run with Kubernetes
```bash
kubectl apply -f k8s/namespace.yml
kubectl apply -f k8s/configmap.yml
kubectl apply -f k8s/secret.yml
kubectl apply -f k8s/postgres-deployment.yml
kubectl apply -f k8s/postgres-service.yml
kubectl apply -f k8s/redis-deployment.yml
kubectl apply -f k8s/redis-service.yml
kubectl apply -f k8s/app-deployment.yml
kubectl apply -f k8s/app-service.yml

kubectl port-forward service/inventory-app-service 8081:8080 -n inventory
```
Backend runs at: `http://localhost:8081`

### Option 3 — Run Frontend
```bash
cd frontend
npm install
npm run dev
```
Frontend runs at: `http://localhost:5173`

---

## 🧪 API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/products` | Get all products |
| POST | `/products` | Create a product |
| GET | `/products/{id}` | Get product by ID (cached) |
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
| Push to any branch | CI | Runs all 19 tests |
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
| 9 | Redis (Caching) | ✅ Done |
| 10 | React Frontend | ✅ Done |
| 11 | JWT Security | 🔜 Next |
| 12 | AWS Deployment | ⏳ Pending |
| 13 | Prometheus + Grafana | ⏳ Pending |

---

## 📖 Dev Log
- **Phase 1** — Project initialized with Spring Boot. Git + GitHub configured.
- **Phase 2** — Built Product CRUD REST API, input validation, exception handling and fixed transitive CVEs.
- **Phase 3** — Added DTO layer, separating API contracts from database entities.
- **Phase 4** — Added JUnit unit tests, repository tests and MockMvc integration tests. 19/19 passing.
- **Phase 5** — Containerized app and PostgreSQL with Docker and docker-compose.
- **Phase 6** — CI/CD pipelines with GitHub Actions. Auto-tests on every push, auto-builds Docker image on merge to main. Dependabot enabled. Branch protection enabled on main.
- **Phase 7** — Added Kubernetes manifests. App runs with 2 replicas, self-healing and rolling deployments using kind.
- **Phase 8** — Migrated from MySQL to PostgreSQL. Added Spring profiles for Docker and Kubernetes environments.
- **Phase 9** — Added Redis caching using RedisTemplate. GET by ID served from cache after first request. Cache invalidated on update and delete.
- **Phase 10** — Added React frontend with full CRUD UI. Axios for API calls, CORS configured for local development.