# 📋 Copy-Paste this message for your Frontend Developer

নিচে ব্যাকএন্ডের সকল তথ্য দিচ্ছি। আপনার ফ্রন্টএন্ড ডেভেলপারকে এই মেসেজটি কপি করে পাঠিয়ে দিন।

---

## 🔗 Backend Base URL
```
http://192.168.0.189:8080
```
(আপনার ম্যাকের লোকাল আইপি)

আইপি জানার জন্য টার্মিনালে চালান:
```bash
ifconfig | grep "inet "
```

---

## 🔐 Authentication Header
প্রোটেক্টেড এন্ডপয়েন্টে রিকোয়েস্ট পাঠাতে হেডারে জেএটটি টোকেন যোগ করতে হবে:

```
Authorization: Bearer <JWT_TOKEN>
```

লগইন করলে টোকেন পাবেন:
- `POST /api/auth/login` → `{ "token": "..." }`

---

## 📌 API Endpoints Summary

| Module | Method | Endpoint | Auth | Description |
|--------|--------|----------|------|-------------|
| Auth | POST | `/api/auth/login` | ❌ | Login & get JWT token |
| Auth | POST | `/api/auth/register` | ❌ | Register new user |
| Items | POST | `/api/items` | ❌ | Create lost/found item |
| Items | GET | `/api/items` | ❌ | Get all items |
| Items | GET | `/api/items/{id}` | ❌ | Get item by ID |
| Items | PATCH | `/api/items/{id}/status` | ✅ ADMIN | Update item status |
| Claims | POST | `/api/claims` | ❌ | Submit claim |
| Claims | GET | `/api/claims` | ✅ User | Get all claims |
| Claims | GET | `/api/claims/track/{code}` | ❌ | Track claim by code |
| Claims | PATCH | `/api/claims/{id}/approve` | ✅ ADMIN | Approve claim |
| Claims | PATCH | `/api/claims/{id}/reject` | ✅ ADMIN | Reject claim |

---

## 📱 Item Request Body (POST /api/items)
```json
{
  "itemName": "string",
  "description": "string",
  "itemType": "LOST",        // or "FOUND"
  "reportDate": "2024-01-15",
  "campusLocation": "string",
  "reporterName": "string",
  "reporterEmail": "string",
  "status": "OPEN"
}
```

## 📝 Claim Request Body (POST /api/claims)
```json
{
  "claimantName": "string",
  "claimantEmail": "string",
  "studentId": "string",
  "claimCode": "string",
  "status": "PENDING",
  "item": {
    "id": 1
  }
}
```

---

## ⚠️ Important Notes
1.Backendটি চালু রাখতে হবে 맥ines-এ, এবং দুই ডিভাইস একই Wi-Fi-তে থাকতে হবে।
2. CORS সেটিংস অনুযায়ী ফ্রন্টএন্ড URL জোড়া দিতে হতে পারে বর্তমানে শুধু `http://localhost:5173` এনাবল করা আছে।
3. ডাটাবেস: PostgreSQL (`lost_found_db`), ব্যবহারকারী: `macbookpro`।

---

## 🚀 Backend Run Command (আপনার ম্যাকে)
```bash
cd /Users/alif/code/tanzim/lost-found-backend
./RUN_SERVER.sh
```

এই স্ক্রিপ্টটি আপনার আইপি দেখিয়ে দেবে এবং সার্ভার চালু করবে।

---