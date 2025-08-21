Got it! Here's a **clean, updated README** for your Journal app that accounts for the `/journal` context path and includes full API documentation:

---

# 📝 Journal Application Backend

## 📌 Overview

The Journal Application Backend is a **Spring Boot RESTful API** for managing journal entries. It supports **CRUD operations** and JWT-based authentication, providing a secure way to store and manage personal notes.

**Deployed URL:** [https://journal-app-production-3f39.up.railway.app/journal](https://journal-app-production-3f39.up.railway.app)
health check api= https://journal-app-production-3f39.up.railway.app/journal/ok
                                                                  here=/journal is context path 
                                                                  and /ok is end point
                                                                  put /journal in every call and then endpoint



---

## 🛠️ Technologies Used

* Java 17+
* Spring Boot 3.x
* Spring Security 6 (JWT-based authentication)
* Spring Data JPA
* H2 / MySQL (configurable)
* Lombok
* Maven

---

## 📂 Project Structure

```
src/
├── main/
│   ├── java/com/innovator/journal/
│   │   ├── controller/
│   │   │   └── JournalEntryController.java
│   │   ├── entity/
│   │   │   └── JournalEntry.java
│   │   ├── repository/
│   │   │   └── JournalEntryRepository.java
│   │   ├── service/
│   │   │   └── JournalEntryService.java
│   │   └── JournalAppApplication.java
│   └── resources/
│       └── application.properties
└── pom.xml
```

---

## 🔐 Authentication

* Uses **JWT tokens**.
* Include `Authorization: Bearer <token>` in headers for protected endpoints.
* Public endpoints can be accessed without authentication (if configured in Spring Security).

---

## 📡 API Endpoints

> **Note:** The context path `/journal` is prefixed to all endpoints.

### 1. **Create Journal Entry**

* **Method:** POST
* **Endpoint:** `/journal`
* **Description:** Creates a new journal entry.
* **Request Body:**

```json
{
  "title": "My First Entry",
  "content": "Today was a great day!"
}
```

* **Response:**

```json
{
  "id": 1,
  "title": "My First Entry",
  "content": "Today was a great day!"
}
```

---

### 2. **Get All Journal Entries**

* **Method:** GET
* **Endpoint:** `/journal`
* **Description:** Retrieves all journal entries.
* **Response:**

```json
[
  {
    "id": 1,
    "title": "My First Entry",
    "content": "Today was a great day!"
  },
  {
    "id": 2,
    "title": "Another Day",
    "content": "Learned something new."
  }
]
```

---

### 3. **Get Journal Entry by ID**

* **Method:** GET
* **Endpoint:** `/journal/{id}`
* **Description:** Retrieves a specific journal entry by ID.
* **Response:**

```json
{
  "id": 1,
  "title": "My First Entry",
  "content": "Today was a great day!"
}
```

---

### 4. **Update Journal Entry**

* **Method:** PUT
* **Endpoint:** `/journal/{id}`
* **Description:** Updates an existing journal entry by ID.
* **Request Body:**

```json
{
  "title": "Updated Title",
  "content": "Updated content."
}
```

* **Response:**

```json
{
  "id": 1,
  "title": "Updated Title",
  "content": "Updated content."
}
```

---

### 5. **Delete Journal Entry**

* **Method:** DELETE
* **Endpoint:** `/journal/{id}`
* **Description:** Deletes a specific journal entry by ID.
* **Response:** `204 No Content`

---

---

## ✅ Common Issues

* **403 Forbidden:** Ensure JWT token is included for protected endpoints.
* **404 Not Found:** Verify the endpoint URL includes the context path `/journal`.
* **500 Errors:** Check server logs for stack traces; may indicate database or code issues.

---

## 📄 License

MIT License

---
