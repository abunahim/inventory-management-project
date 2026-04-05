# 📦 Inventory Management App

![CI Pipeline](https://github.com/abunahim/inventory-management-project/actions/workflows/ci.yml/badge.svg)
![CD Pipeline](https://github.com/abunahim/inventory-management-project/actions/workflows/cd.yml/badge.svg)

A full-stack inventory management system built with **Java Spring Boot** and **React**, developed phase-by-phase while learning DevOps practices from scratch.

---

## 🌐 Live Demo

| Service | URL |
|---|---|
| **Frontend** | https://inventorymanagement-project.netlify.app |
| **Backend API** | https://inventory-backend-icaf.onrender.com |

> ⚠️ Free tier — backend may take ~30 seconds to wake up after inactivity.

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 17, Spring Boot 3.5.x |
| Frontend | React 18, Vite, Axios |
| Database | PostgreSQL 16 |
| Caching | Redis 7.2 |
| Security | Spring Security, JWT (jjwt 0.12.6) |
| Build Tool | Maven |
| Containerization | Docker, Docker Compose |
| Orchestration | Kubernetes (kind) |
| CI/CD | GitHub Actions, Docker Hub |
| Monitoring | Prometheus, Grafana, Spring Actuator |
| Cloud | Render (backend), Netlify (frontend) |

---

## 🏗️ Architecture
[ React Frontend - Netlify ]
↓
[ Spring Boot API - Render ]
↙   ↘
[ Redis Cache ] [ PostgreSQL - Render ]
↓
[ Prometheus → Grafana ] (local monitoring)

---

## 🚀 How to Run Locally

### Prerequisites
- Java 17+
- Maven
- Docker Desktop
- Node.js 24+
- kubectl

### Option 1 — Run with Docker Compose (includes monitoring)
```bash
git clone https://github.com/abunahim/inventory-management-project.git
cd inventory-management-project

# Create .env file
# DB_NAME=inventory_db
# DB_USERNAME=postgres
# DB_PASSWORD=yourpassword

docker-compose up --build
```

| Service | URL |
|---|---|
| Backend | http://localhost:8080 |
| Frontend | http://localhost:5173 |
| Prometheus | http://localhost:9090 |
| Grafana | http://localhost:3000 (admin/admin123) |

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

### Option 3 — Run Frontend
```bash
cd frontend
npm install
npm run dev
```

---

## 🔐 Authentication

All product endpoints require JWT authentication.

**Register:**
```bash
POST /auth/register
{ "username": "nahim", "password": "yourpassword" }
```

**Login:**
```bash
POST /auth/login
{ "username": "nahim", "password": "yourpassword" }
```

Use the returned token in the `Authorization` header:
Authorization: Bearer <token>

---

## 🧪 API Endpoints

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| POST | `/auth/register` | ❌ Public | Register user |
| POST | `/auth/login` | ❌ Public | Login and get token |
| GET | `/products` | ✅ Required | Get all products |
| POST | `/products` | ✅ Required | Create product |
| GET | `/products/{id}` | ✅ Required | Get product by ID |
| PUT | `/products/{id}` | ✅ Required | Update product |
| DELETE | `/products/{id}` | ✅ Required | Delete product |
| GET | `/actuator/health` | ❌ Public | Health check |
| GET | `/actuator/prometheus` | ❌ Public | Prometheus metrics |

### Sample Request Body
```json
{
  "name": "Laptop",
  "price": 999.99,
  "quantity": 10
}
```

---

## 📊 Monitoring

Prometheus scrapes metrics every 15 seconds from `/actuator/prometheus`.

Grafana dashboard includes:
- JVM Heap Memory usage
- HTTP Request Rate
- CPU Usage

Access locally at `http://localhost:3000` after running docker-compose.

---

## 🔁 CI/CD Pipeline

| Trigger | Pipeline | What it does |
|---|---|---|
| Push to any branch | CI | Runs all 19 tests |
| Merge to `main` | CD | Builds and pushes Docker image to Docker Hub |
| Merge to `main` | Render | Auto-deploys backend |
| Merge to `main` | Netlify | Auto-deploys frontend |

---

## 🌿 Git Branch Strategy

| Branch | Purpose |
|---|---|
| `main` | Production-ready code only |
| `dev` | Integration branch |
| `feature/*` | Individual features |

**Branch protection:** main requires PR + CI pass before merge.

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
| 9 | Redis Caching | ✅ Done |
| 10 | React Frontend | ✅ Done |
| 11 | JWT Security | ✅ Done |
| 12 | Cloud Deployment (Render + Netlify) | ✅ Done |
| 13 | Prometheus + Grafana Monitoring | ✅ Done |

---

## 📖 Dev Log

- **Phase 1** — Project initialized with Spring Boot. Git + GitHub configured with branch strategy.
- **Phase 2** — Built Product CRUD REST API with input validation, exception handling and CVE fixes.
- **Phase 3** — Added DTO layer separating API contracts from database entities.
- **Phase 4** — Added JUnit unit tests, repository tests and MockMvc integration tests. 19/19 passing.
- **Phase 5** — Containerized app and PostgreSQL with Docker and docker-compose.
- **Phase 6** — CI/CD pipelines with GitHub Actions. Auto-tests on every push, auto-builds Docker image on merge to main. Dependabot and branch protection enabled.
- **Phase 7** — Added Kubernetes manifests. App runs with 2 replicas with self-healing and rolling deployments using kind.
- **Phase 8** — Migrated from MySQL to PostgreSQL with Spring profiles for Docker and Kubernetes environments.
- **Phase 9** — Added Redis caching using RedisTemplate. GET by ID served from cache after first request.
- **Phase 10** — Added React frontend with full CRUD UI. Axios for API calls, CORS configured for local development.
- **Phase 11** — Added JWT authentication with Spring Security. Register/login endpoints public, all product endpoints protected.
- **Phase 12** — Full stack deployed. Backend on Render with PostgreSQL, frontend on Netlify. JWT auth working in production.
- **Phase 13** — Added Prometheus and Grafana monitoring. Metrics exposed via Spring Actuator, custom dashboard with heap memory, HTTP request rate and CPU usage.