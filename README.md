# PastebinLite



\# Pastebin Lite



A lightweight pastebin service to share code and text snippets with optional expiration and view limits.



\## 🚀 Features



\- \*\*Create Pastes\*\*: Share code and text snippets with unique URLs

\- \*\*TTL (Time-to-Live)\*\*: Set expiration time for pastes

\- \*\*View Limits\*\*: Limit how many times a paste can be viewed

\- \*\*Modern UI\*\*: Dark theme with glassmorphism effects

\- \*\*Responsive\*\*: Works on desktop and mobile



\## 🛠️ Tech Stack



\- \*\*Frontend\*\*: Angular 17+

\- \*\*Backend\*\*: Spring Boot 3.2.1 (Java 17)

\- \*\*Database\*\*: PostgreSQL



\## 📁 Project Structure



```

Pastebin-lite/

├── backend/          # Spring Boot API

│   ├── src/main/java/com/pastebin/

│   │   ├── controller/   # REST endpoints

│   │   ├── service/      # Business logic

│   │   ├── entity/       # JPA entities

│   │   ├── repository/   # Data access

│   │   ├── dto/          # Request/Response DTOs

│   │   └── config/       # Configuration

│   └── pom.xml

│

└── frontend/         # Angular App

&nbsp;   └── src/app/

&nbsp;       ├── components/   # UI components

&nbsp;       ├── services/     # API services

&nbsp;       └── models/       # TypeScript interfaces

```



\## 🏃‍♂️ Running Locally



\### Prerequisites

\- Java 17+

\- Node.js 18+

\- PostgreSQL 14+

\- Angular CLI (`npm install -g @angular/cli`)



\### 1. Database Setup

```sql

CREATE DATABASE pastebin;

```



\### 2. Backend

```bash

cd backend



\# Set environment variables

export DATABASE\_URL=jdbc:postgresql://localhost:5432/pastebin

export DATABASE\_USERNAME=postgres

export DATABASE\_PASSWORD=your\_password



\# Run

./mvnw spring-boot:run

```



Backend runs at: http://localhost:8080



\### 3. Frontend

```bash

cd frontend

npm install

ng serve

```



Frontend runs at: http://localhost:4201



\## 📡 API Endpoints



| Method | Endpoint | Description |

|--------|----------|-------------|

| POST | `/api/pastes` | Create new paste |

| GET | `/api/pastes/{id}` | Get paste by ID |

| GET | `/api/pastes/{id}/raw` | Get raw content |

| DELETE | `/api/pastes/{id}` | Delete paste |

| GET | `/health` | Health check |



\### Create Paste Request

```json

{

&nbsp; "content": "Your code or text here",

&nbsp; "ttlSeconds": 3600,

&nbsp; "maxViews": 10

}

```



\## 🚀 Deployment



\### Backend (Railway)

1\. Create a new project on \[Railway](https://railway.app)

2\. Add PostgreSQL database

3\. Deploy from GitHub, set environment variables:

&nbsp;  - `DATABASE\_URL`

&nbsp;  - `DATABASE\_USERNAME`

&nbsp;  - `DATABASE\_PASSWORD`

&nbsp;  - `CORS\_ORIGINS` (your Vercel frontend URL)



\### Frontend (Vercel)

1\. Update `environments/environment.prod.ts` with your Railway backend URL

2\. Deploy to \[Vercel](https://vercel.com):

```bash

cd frontend

npm run build

\# Deploy dist/frontend/browser to Vercel

```



\## 📄 License



MIT



