# Student Management System

This is a **Student Management System** designed to help educational institutions manage students, classes, enrollments, payments, and performance efficiently.  
It is built with **React.js** for the frontend and **Spring Boot (Java)** for the backend, with **MySQL** as the database.

## 🚀 Technologies Used

### Frontend:
- React.js
- Tailwind CSS
- Axios
- React Router DOM
- Recharts (for charts and graphs)

### Backend:
- Spring Boot (Java)
- Spring Data JPA
- Spring Security (JWT Authentication)
- MySQL
- Hibernate

## 🔐 Features

- Admin login with JWT authentication
- Dashboard with summary cards and chart for student registrations
- Student registration and profile management
- Class creation and management
- Enroll students in classes
- Track student performance (marks)
- Manage student payments
- Responsive user interface

## 📁 Project Structure

### Backend (Spring Boot)
StudentManagementSystem-Backend/
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ └── com.example.sms/
│ │ │ ├── controller/
│ │ │ ├── service/
│ │ │ ├── model/
│ │ │ ├── repository/
│ │ │ └── config/
│ │ └── resources/
│ │ └── application.properties

shell
Copy
Edit

### Frontend (React)
student-management-frontend/
├── src/
│ ├── components/
│ ├── pages/
│ ├── services/
│ ├── App.jsx
│ └── main.jsx

markdown
Copy
Edit

## ⚙️ Setup Instructions

### Backend
1. Clone the backend repo
2. Create a MySQL database (e.g., `student_management`)
3. Update your DB credentials in `application.properties`
4. Run the Spring Boot application

```bash
./mvnw spring-boot:run
Frontend
Clone the frontend repo

Navigate to the project directory

Install dependencies

bash
Copy
Edit
npm install
Start the React development server

bash
Copy
Edit
npm run dev
Note: Make sure the backend is running on localhost:8080 or update the frontend API URLs accordingly.

📊 Dashboard Overview
Summary of total students, classes, enrollments, and payments

Chart showing monthly registration trends

✍️ Author
Lihini Chamiduni Wickrama Senevirathna

🏫 Bachelor of Information Technology – University of Moratuwa

💻 Passionate about software development and UI/UX
