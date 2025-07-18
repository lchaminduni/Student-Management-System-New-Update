// src/pages/Login.jsx
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function Login() {
  const [formData, setFormData] = useState({ username: '', password: '' });
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      const response = await axios.post('http://localhost:8080/api/admin/login', formData);
      alert('Login successful!');
      navigate('/dashboard');
      // Save token and redirect if needed
    } catch (error) {
      alert('Login failed. Please check credentials.');
    }
  };

  return (
    <div className="bg-white p-6 rounded shadow-md w-80">
      <h2 className="text-xl font-bold mb-4 text-center">Admin Login</h2>
      <input
        type="text"
        placeholder="Username"
        value={formData.username}
        onChange={(e) => setFormData({ ...formData, username: e.target.value })}
        className="w-full mb-3 p-2 border rounded"
      />
      <input
        type="password"
        placeholder="Password"
        value={formData.password}
        onChange={(e) => setFormData({ ...formData, password: e.target.value })}
        className="w-full mb-3 p-2 border rounded"
      />
      <button
        onClick={handleLogin}
        className="w-full bg-blue-500 text-white p-2 rounded mb-3"
      >
        Login
      </button>
      <button
        onClick={() => navigate('/register')}
        className="w-full bg-green-500 text-white p-2 rounded"
      >
        Signup
      </button>
    </div>
  );
}

export default Login;
