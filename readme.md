# Event Management Application

## Project Overview

This is a university project developed for **Tek-up**. The Event Management Application is built using **Spring MVC** and **Thymeleaf**, with a modular structure catering to two user groups:

1. **Back Office (Admin Portal):** For administrators to manage events, users, and system settings.
2. **Client Website:** For users to browse events, register, and purchase tickets.

The application uses **PostgreSQL** as its database and **pgAdmin** for database management, all containerized with **Docker Compose** for easy setup and deployment.

---

## Features

### Admin Portal (Back Office)

- Login for admins with role-based access.
- Manage events with CRUD operations (Create, Read, Update, Delete).
- View dashboard analytics for events and user activity.

### Client Website

- Browse and filter events.
- View detailed event information.
- Register as a user and log in.
- Purchase tickets and view purchase history.
- Manage user profile.

---

## Tech Stack

- **Framework**: Spring MVC
- **Template Engine**: Thymeleaf
- **Database**: PostgreSQL
- **Containerization**: Docker, Docker Compose
- **Database Management**: pgAdmin
- **Build Tool**: Maven
- **Java Version**: JDK 17+

---

## Getting Started

### Prerequisites

- Install **Docker** and **Docker Compose**.
- Install **JDK 17+** and **Maven**.

---

### Setup Instructions

#### 1. Clone the Repository

````bash
git clone https://github.com/your-repo/event-management-app.git
cd event-management-app
````

#### 2. Run docker compose
```bash
docker compose up --build --detach
````

