# Sample Spring REST API
- [Overview](#overview)
- [Setup](#setup)
- [Application](#application)
- [Roles](#roles)
- [Endpoints](#endpoints)

## Overview
The purpose of this REST API is to demonstrate a Spring boot application and its various features. This sample REST API provides security, user, and organization management.

This project contains the following:
- Configuration (Application, Security, Swagger)
- Controllers
- Spring actuator endpoints
- Exception handlers
- Services
- Repositories
- Entities
- Tests (Acceptance, Integration, Unit)

## Setup
1. Clone repository
2. Run application

**NOTE: Data is deleted after application shutdown since application uses H2 In-Memory Database.**

## Application
**Base URL**: http://localhost:8080/api/v1

**Swagger UI URL**: http://localhost:8080/api/v1/swagger-ui.html

## Roles
| Role |
|----------|
| ADMIN |
| USER |

## Endpoints
### Auth
| Endpoint | Description | Method |
|----------|-------------|:------:|
| /auth/login | Login | POST |
| /auth/token | Validate token | GET |
| /auth/token | Renew token | PUT |
| /auth/password | Change password | PUT |

### User
| Endpoint | Description | Method | Auth Required | Allowed Role(s) |
|----------|-------------|:------:|:-------------:|:---------------:|
| /user | Create user | POST | No | N/A |
| /user | Get user | GET | Yes | ADMIN, USER |
| /user/{id} | Get user by ID | GET | Yes | ADMIN, USER |
| /user/{id} | Update user | PUT | Yes | ADMIN, USER |
| /user/{id} | Delete user | DELETE | Yes | ADMIN, USER |

### Org
| Endpoint | Description | Method | Auth Required | Allowed Role(s) |
|----------|-------------|:------:|:-------------:|:---------------:|
| /org | Create organization | POST | Yes | ADMIN, USER |
| /org | Get organization | GET | Yes | ADMIN, USER |
| /org/{id} | Get organization by ID | GET | Yes | ADMIN, USER |
| /org/{id} | Update organization | PUT | Yes | ADMIN, USER |
| /org/{id} | Delete organization | DELETE | Yes | ADMIN, USER |

### Spring Actuator
| Endpoint | Description | Method | Auth Required | Allowed Role(s) |
|----------|-------------|:------:|:-------------:|:---------------:|
| /auditevents| Shows audit events information | GET   | Yes | ADMIN |
| /beans | Shows Spring beans in the application | GET   | Yes | ADMIN |
| /congifprops | Shows configuration properties | GET | Yes | ADMIN |
| /env | Shows environment variables | GET | Yes | ADMIN |
| /info | Shows application information | GET   | Yes | ADMIN |
| /health | Shows application health | GET   | Yes | ADMIN |
| /loggers | Shows configuration of loggers | GET   | Yes | ADMIN |
| /mappings | Shows request mapping paths | GET   | Yes | ADMIN |
| /metrics | Shows application metrics | GET   | Yes | ADMIN |
| /trace | Shows traces | GET   | Yes | ADMIN |

See [Spring Actuator Endpoints](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html) for more information

