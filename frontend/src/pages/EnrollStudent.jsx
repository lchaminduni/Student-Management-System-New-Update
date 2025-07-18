import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function EnrollStudent() {
  const [students, setStudents] = useState([]);
  const [classes, setClasses] = useState([]);
  const [enrollments, setEnrollments] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');
  const [formData, setFormData] = useState({
    studentId: '',
    classId: '',
    enrollDate: ''
  });
  const navigate = useNavigate();

  useEffect(() => {
    axios.get('http://localhost:8080/api/students').then(res => setStudents(res.data));
    axios.get('http://localhost:8080/api/class/all').then(res => setClasses(res.data));
    axios.get('http://localhost:8080/api/enrollments/all').then(res => setEnrollments(res.data));
  }, []);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    await axios.post('http://localhost:8080/api/enrollments/add', formData);
    alert('Enrolled successfully');
    window.location.reload();
  };

  const handleDelete = async (id) => {
    await axios.delete(`http://localhost:8080/api/enrollments/delete/${id}`);
    alert('Enrollment deleted');
    window.location.reload();
  };

  // Get student name by studentId
  const getStudentName = (id) => {
    const student = students.find(st => String(st.studentId) === String(id));
    return student ? student.name : 'Unknown';
  };

  // Filtered enrollments by student ID or name
  const filteredEnrollments = enrollments.filter(e => {
    const studentName = getStudentName(e.studentId).toLowerCase();
    const studentId = String(e.studentId);
    return (
      studentId.includes(searchQuery.toLowerCase()) ||
      studentName.includes(searchQuery.toLowerCase())
    );
  });

  return (
    <div className="min-h-screen bg-gray-50 p-10">
      <h1 className="text-3xl font-bold mb-6 text-indigo-700">Student Enrollment</h1>
      
      <form onSubmit={handleSubmit} className="bg-white p-6 rounded shadow mb-10 grid grid-cols-1 md:grid-cols-3 gap-4">
        <div>
          <label className="block text-sm font-medium">Student</label>
          <select name="studentId" onChange={handleChange} required className="w-full border rounded p-2">
            <option value="">Select Student</option>
            {students.map(st => (
              <option key={st.studentId} value={st.studentId}>{st.name}</option>
            ))}
          </select>
        </div>
        <div>
          <label className="block text-sm font-medium">Class</label>
          <select name="classId" onChange={handleChange} required className="w-full border rounded p-2">
            <option value="">Select Class</option>
            {classes.map(cls => (
              <option key={cls.classId} value={cls.classId}>{cls.subject} - Grade {cls.grade}</option>
            ))}
          </select>
        </div>
        <div>
          <label className="block text-sm font-medium">Enroll Date</label>
          <input type="date" name="enrollDate" onChange={handleChange} required className="w-full border rounded p-2" />
        </div>
        <div className="col-span-full">
          <button type="submit" className="bg-indigo-600 text-white px-4 py-2 rounded hover:bg-indigo-700">Enroll Student</button>
        </div>
      </form>

      {/* 🔍 Search input */}
      <div className="mb-4">
        <input
          type="text"
          placeholder="Search by Student ID or Name"
          className="w-full md:w-1/3 p-2 border border-gray-300 rounded"
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
        />
      </div>

      <h2 className="text-xl font-semibold text-gray-700 mb-2">All Enrollments</h2>
      <table className="w-full text-sm border border-collapse border-gray-300">
        <thead className="bg-indigo-100">
          <tr>
            <th className="border p-2">Enrollment ID</th>
            <th className="border p-2">Student ID</th>
            <th className="border p-2">Student Name</th>
            <th className="border p-2">Class ID</th>
            <th className="border p-2">Enroll Date</th>
            <th className="border p-2">Actions</th>
          </tr>
        </thead>
        <tbody>
          {filteredEnrollments.length > 0 ? (
            filteredEnrollments.map(e => (
              <tr key={e.enrollmentId} className="hover:bg-gray-50">
                <td className="border p-2">{e.enrollmentId}</td>
                <td className="border p-2">{e.studentId}</td>
                <td className="border p-2">{getStudentName(e.studentId)}</td>
                <td className="border p-2">{e.classId}</td>
                <td className="border p-2">{e.enrollDate}</td>
                <td className="border p-2">
                  <button onClick={() => handleDelete(e.enrollmentId)} className="text-white bg-red-500 px-3 py-1 rounded hover:bg-red-600">Delete</button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="6" className="text-center py-4 text-gray-500">No enrollments found</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
}

export default EnrollStudent;
