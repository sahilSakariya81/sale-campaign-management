│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── repository/
│   │   │   ├── model/
│   │   │   └── config/
│   │   └── resources/
│   │       ├── application.properties
│
├── pom.xml
└── README.md
```
---
## ⚙️ Setup & Installation
### 1️⃣ Clone Repository
```bash id="a9x2mn"
git clone https://github.com/sahilSakariya81/sale-campaign-management.git

```
---
### 2️⃣ Configure Database
Update `application.properties`:
```properties id="k2p8zy"
spring.datasource.url=jdbc:mysql://localhost:3306/campaigndb
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```
---
### 3️⃣ Run Application
```bash id="n4v6rt"
./mvnw spring-boot:run
```
---
## 🔌 API Endpoints
### 📢 Campaign APIs
| Method | Endpoint            | Description       |
| ------ | ------------------- | ----------------- |
| GET    | /api/campaigns      | Get all campaigns |
| POST   | /api/campaigns      | Create campaign   |
| PUT    | /api/campaigns/{id} | Update campaign   |
| DELETE | /api/campaigns/{id} | Delete campaign   |
---
### 🛍️ Product APIs
| Method | Endpoint           | Description      |
| ------ | ------------------ | ---------------- |
| GET    | /api/products      | Get all products |
| POST   | /api/products      | Add product      |
| PUT    | /api/products/{id} | Update product   |
| DELETE | /api/products/{id} | Delete product   |
---
## 🔄 Application Flow
```id="x7q2hs"
1. Admin creates campaign
2. Admin sets discount & duration
3. Products are linked to campaign
4. Discount applied automatically
5. Campaign ends → prices revert
```
---
## 🧪 Testing APIs
Use:
* Postman
* Thunder Client (VS Code)
---
## 📌 Future Improvements
* Scheduled campaign activation (cron jobs)
* Multiple discount types (flat / percentage)
* Admin dashboard UI
* Analytics & reporting
---
## 👨‍💻 Author
Sahil Sakariya
---
## ⭐ Support
If you like this project, give it a ⭐ on GitHub!
