// src/App.jsx
import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';
import StudentManagement from './pages/StudentManagement';
import ClassManagement from './pages/ClassManagement';
import EnrollStudent from './pages/EnrollStudent';
import PaymentManagement from './pages/PaymentManagement';

function App() {
  return (
    <Router>
      <div className="flex justify-center items-center min-h-screen bg-gray-100">
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
           <Route path="/dashboard" element={<Dashboard />} />
           <Route path="/students" element={<StudentManagement />} />
           <Route path="/classes" element={<ClassManagement />} />
           <Route path="/enrollments" element={<EnrollStudent />} />
           <Route path="/payments" element={<PaymentManagement />} />
          <Route path="*" element={<Navigate to="/login" />} />
          
        </Routes>
      </div>
      
    </Router>

  );
  

}

export default App;


