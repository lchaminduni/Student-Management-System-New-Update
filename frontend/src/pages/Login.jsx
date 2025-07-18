// src/pages/Login.jsx
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import logo from '../assets/logo.png'; // ✅ Adjust the path to your logo image

function Login() {
  const [formData, setFormData] = useState({ username: '', password: '' });
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      const response = await axios.post('http://localhost:8080/api/admin/login', formData);
      alert('Login successful!');
      navigate('/dashboard');
    } catch (error) {
      alert('Login failed. Please check credentials.');
    }
  };

  return (
    <div className="bg-white p-6 rounded shadow-md w-80 text-center">
      {/* ✅ Institute logo and name */}
      <img src={logo} alt="Sisu Piyasa Logo" className="mx-auto mb-4 h-20 w-20 object-contain" />
      {/*<h1 className="text-2xl font-bold italic text-purple-700 mb-2">Sisu Piyasa</h1>*/}


      <h2 className="text-xl font-bold mb-4">Admin Login</h2>

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
