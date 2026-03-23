# 📦 Inventory Management App

A RESTful inventory management system built with **Java Spring Boot**, developed phase-by-phase while learning DevOps practices.

---

## 🛠️ Tech Stack
| Layer | Technology |
|---|---|
| Backend | Java 17, Spring Boot 3.5.x |
| Database | MySQL |
| Build Tool | Maven |
| Version Control | Git + GitHub |

---

## 🚀 How to Run Locally

### Prerequisites
- Java 17+
- Maven
- MySQL

### Steps
```bash
git clone https://github.com/abunahim/inventory-management-project.git
cd inventory-management-project/backend/inventory-management
./mvnw spring-boot:run
```
App runs at: `http://localhost:8080`

---

## 📍 Project Phases

| Phase | Description | Status |
|---|---|---|
| 1 | Git setup + Spring Boot skeleton | ✅ Done |
| 2 | REST API + CRUD + Validation | ✅ Done |
| 3 | DTO Layer | ✅ Done |
| 4 | Testing | ⏳ Pending |
| 5 | Docker | ⏳ Pending |
| 6 | CI/CD with GitHub Actions | ⏳ Pending |
| 7 | Cloud Deployment | ⏳ Pending |
| 8 | Monitoring | ⏳ Pending |

---

## 📖 Dev Log
- **Phase 1** — Project initialized with Spring Boot. Git + GitHub configured.
- **Phase 2** — Built Product CRUD REST API, input validation, exception handling and fixed transitive CVEs.
- **Phase 3** — Added DTO layer, separating API contracts from database entities.