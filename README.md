# RestAssured – API Automation Practice

This repository contains my hands-on practice and learning work for building API automation using **RestAssured**, **Java**, and **TestNG**.  
The goal of this project is to strengthen API testing concepts, build reusable automation components, and understand real-world patterns like serialization, authentication, and specification builders.

---

## 🚀 Tech Stack

- **Java 8+**
- **RestAssured**
- **TestNG**
- **Maven**
- **POJO Serialization/Deserialization**
- **OAuth 2.0 (client_credentials & password grant)**
- **JSONPath**
- **Spec Builders (Request + Response)**

---

## 📚 What I Implemented

### ✅ **1. RestAssured Fundamentals**
- `given() – when() – then()` usage  
- GET, POST, PUT, DELETE methods  
- Content-Type & Accept headers  
- Logging, response extraction  
- Static imports for readability  

---

### ✅ **2. Parameters**
Implemented and practiced different parameter types:

| Parameter Type | Example |
|----------------|---------|
| **Query Params** | `.queryParam("key", "value")` |
| **Path Params**  | `.pathParam("id", 101)` |
| **Form Params**  | `.formParam("username", "admin")` |
| **Header Params** | `.header("Authorization", "Bearer token")` |

---

### ✅ **3. Payload Handling**
- Created **separate Java classes** for request bodies  
- Stored payloads in dedicated classes instead of writing raw JSON  
- Practiced building payloads dynamically  

---

### ✅ **4. Serialization & Deserialization**
Used POJO classes to:
- Convert Java objects → JSON (serialization)
- Convert JSON → Java objects (deserialization)

Includes:
- Nested JSON handling  
- Lists inside JSON  
- Mapping response into strongly typed POJOs  

---

### ✅ **5. SpecBuilder (Reusable Specs)**

#### **Request Specification**
Created using `RequestSpecBuilder`:
- Base URI & Base Path
- Common headers
- Content-Type
- Query parameters
- Reusable across all tests

#### **Response Specification**
Created using `ResponseSpecBuilder`:
- Status code validations
- Content-Type assertions
- Logging
- Common response expectations

> This keeps tests clean and avoids repetition.

---

### ✅ **6. OAuth 2.0 Authentication**

Implemented and validated:
- **client_credentials** flow  
- **password** flow  

Using:
- Token extraction with `response.path()`
- Using extracted token in `Authorization: Bearer <token>`
- Reusing tokens inside RequestSpecBuilder for authenticated tests  

---

### ✅ **7. JSON Parsing**
Practiced extracting:
- Single values
- Arrays
- Nested JSON fields

Using:
```java
JsonPath js = new JsonPath(response.asString());
