// src/pages/Register.jsx
// src/pages/Register.jsx
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function Register() {
  const [formData, setFormData] = useState({
    name: '',
    username: '',
    password: '',
    address: '',
    contactNumber: '',
    nic: '',
    birthDate: '',
  });

  const navigate = useNavigate();

  const handleRegister = async () => {
    try {
      await axios.post('http://localhost:8080/api/admin/register', formData);
      alert('Registration successful!');
      navigate('/login'); // ✅ Redirect back to login
    } catch (error) {
      alert('Registration failed.');
    }
  };

  return (
    <div className="bg-white p-6 rounded shadow-md w-[450px]">
      <h2 className="text-xl font-bold mb-4 text-center">Admin Signup</h2>
      {Object.entries(formData).map(([key, value]) => (
        <input
          key={key}
          type={key === 'birthDate' ? 'date' : 'text'}
          placeholder={key.charAt(0).toUpperCase() + key.slice(1)}
          value={value}
          onChange={(e) => setFormData({ ...formData, [key]: e.target.value })}
          className="w-full mb-3 p-2 border rounded"
        />
      ))}
      <button
        onClick={handleRegister}
        className="w-full bg-blue-500 text-white p-2 rounded"
      >
        Register
      </button>
    </div>
  );
}

export default Register;