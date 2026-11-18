# Lost Found Backend - API Documentation

**Base URL:** `http://<YOUR_LOCAL_IP>:8080`  
Example: `http://192.168.1.15:8080`

---

## Authentication

All protected endpoints require a JWT token in the header:

```
Authorization: Bearer <JWT_TOKEN>
```

### 1. Login
- **POST** `/api/auth/login`
- **Description:** Authenticate user and receive JWT token
- **Request Body:**
  ```json
  {
    "username": "string",
    "password": "string"
  }
  ```
- **Response (200 OK):**
  ```json
  {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  }
  ```
- **Auth Required:** No

### 2. Register
- **POST** `/api/auth/register`
- **Description:** Create a new user account
- **Request Body:**
  ```json
  {
    "username": "string",
    "password": "string"
  }
  ```
- **Response (200 OK):** User object
- **Auth Required:** No

---

## Items (`/api/items`)

### 1. Create Item
- **POST** `/api/items`
- **Description:** Report a new lost or found item
- **Request Body:**
  ```json
  {
    "itemName": "string",
    "description": "string",
    "itemType": "string",          // e.g., "LOST" or "FOUND"
    "reportDate": "YYYY-MM-DD",
    "campusLocation": "string",
    "reporterName": "string",
    "reporterEmail": "string",
    "status": "OPEN"               // optional, default: "OPEN"
  }
  ```
- **Response:** Item object
- **Auth Required:** No

### 2. Get All Items
- **GET** `/api/items`
- **Description:** List all reported items
- **Response:** `List<Item>`
- **Auth Required:** No

### 3. Get Item by ID
- **GET** `/api/items/{id}`
- **Description:** Get details of a single item
- **Response:** Item object
- **Auth Required:** No

### 4. Update Item Status (Admin Only)
- **PATCH** `/api/items/{id}/status`
- **Description:** Update item status (e.g., OPEN → RETURNED)
- **Request Body:**
  ```json
  {
    "status": "RETURNED"
  }
  ```
- **Response:** Item object
- **Auth Required:** Yes (ADMIN role)

---

## Claims (`/api/claims`)

### 1. Submit Claim
- **POST** `/api/claims`
- **Description:** Submit a claim for an item
- **Request Body:**
  ```json
  {
    "claimantName": "string",
    "claimantEmail": "string",
    "studentId": "string",
    "claimCode": "string",         // unique tracking code
    "status": "PENDING",           // optional, default: "PENDING"
    "item": {
      "id": 1                      // reference to an existing Item
    }
  }
  ```
- **Response:** Claim object
- **Auth Required:** No

### 2. Get All Claims
- **GET** `/api/claims`
- **Description:** List all claims
- **Response:** `List<Claim>`
- **Auth Required:** Yes (Authenticated user)

### 3. Track Claim by Code
- **GET** `/api/claims/track/{code}`
- **Description:** Track claim status using claim code
- **Response:** Claim object
- **Auth Required:** No

### 4. Approve Claim (Admin Only)
- **PATCH** `/api/claims/{id}/approve`
- **Description:** Approve a pending claim
- **Response:** Claim object
- **Auth Required:** Yes (ADMIN role)

### 5. Reject Claim (Admin Only)
- **PATCH** `/api/claims/{id}/reject`
- **Description:** Reject a pending claim
- **Response:** Claim object
- **Auth Required:** Yes (ADMIN role)

---

## Data Models

### Item
| Field | Type | Required | Description |
|-------|------|----------|-------------|
| id | Long | No | Auto-generated |
| itemName | String | Yes | Name of the item |
| description | String | No | Description |
| itemType | String | Yes | LOST or FOUND |
| reportDate | LocalDate | Yes | Format: YYYY-MM-DD |
| campusLocation | String | Yes | Location on campus |
| reporterName | String | Yes | Name of reporter |
| reporterEmail | String | Yes | Email of reporter |
| status | String | No | Default: "OPEN" |
| createdAt | LocalDateTime | No | Auto-generated |

### Claim
| Field | Type | Required | Description |
|-------|------|----------|-------------|
| id | Long | No | Auto-generated |
| claimantName | String | Yes | Name of claimant |
| claimantEmail | String | Yes | Email of claimant |
| studentId | String | Yes | Student ID |
| claimCode | String | Yes | Unique tracking code |
| status | String | No | Default: "PENDING" |
| item | Item | Yes | Linked item object |
| createdAt | LocalDateTime | No | Auto-generated |
| reviewedAt | LocalDateTime | No | When reviewed |

---

## CORS Note

Currently CORS is configured for `http://localhost:5173` in `ItemController` and `ClaimController`.  
If your frontend runs on a different device or port, update the `@CrossOrigin` annotation:

```java
@CrossOrigin(origins = "*")  // Development only - restrict in production
```

Or add a global CORS configuration in `SecurityConfig`.

---

## Response Status Codes

| Code | Meaning |
|------|---------|
| 200 | OK / Success |
| 201 | Created |
| 400 | Bad Request (validation error) |
| 401 | Unauthorized (missing/invalid token) |
| 403 | Forbidden (insufficient role) |
| 404 | Not Found |
| 500 | Internal Server Error |