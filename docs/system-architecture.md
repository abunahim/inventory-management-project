# System Architecture

This project is a backend inventory management system.

## Components

1. Client
2. Backend API (Spring Boot)
3. Database (MySQL)

## Architecture Flow

Client -> REST API -> Database

## Layers

- Controller Layer
- Service Layer
- Repository Layer
- Database Layer

## Architecture Diagram

User
  |
  | HTTP Request
  ▼
Spring Boot API
  |
  | JDBC Connection
  ▼
MySQL Database