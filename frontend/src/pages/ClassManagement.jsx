import React, { useState, useEffect } from "react";
import axios from "axios";

const API_URL = "http://localhost:8080/api/class";

const ClassManagement = () => {
  const [classes, setClasses] = useState([]);
  const [formData, setFormData] = useState({
    classId: "",
    grade: "",
    subject: "",
    teacherName: "",
    classFee: "",
    date: "",
    time: "",
  });

  const [editing, setEditing] = useState(false);

  // Load all classes
  useEffect(() => {
    fetchClasses();
  }, []);

  const fetchClasses = async () => {
    try {
      const res = await axios.get(`${API_URL}/all`);
      setClasses(res.data);
    } catch (err) {
      console.error("Error fetching classes:", err);
    }
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (editing) {
        await axios.put(`${API_URL}/update`, formData);
      } else {
        await axios.post(`${API_URL}/add`, formData);
      }
      setFormData({
        classId: "",
        grade: "",
        subject: "",
        teacherName: "",
        classFee: "",
        date: "",
        time: "",
      });
      setEditing(false);
      fetchClasses();
    } catch (err) {
      console.error("Error saving class:", err);
    }
  };

  const handleEdit = (classItem) => {
    setFormData(classItem);
    setEditing(true);
  };

  const handleDelete = async (id) => {
    if (window.confirm("Are you sure to delete this class?")) {
      await axios.delete(`${API_URL}/${id}`);
      fetchClasses();
    }
  };

  return (
    <div className="p-6 max-w-6xl mx-auto bg-white rounded-xl shadow-md space-y-6">
      <h2 className="text-3xl font-bold text-center text-indigo-600">Class Management</h2>

      {/* Form */}
      <form onSubmit={handleSubmit} className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <input
          type="text"
          name="grade"
          value={formData.grade}
          onChange={handleChange}
          placeholder="Grade"
          className="border p-2 rounded"
          required
        />
        <input
          type="text"
          name="subject"
          value={formData.subject}
          onChange={handleChange}
          placeholder="Subject"
          className="border p-2 rounded"
          required
        />
        <input
          type="text"
          name="teacherName"
          value={formData.teacherName}
          onChange={handleChange}
          placeholder="Teacher Name"
          className="border p-2 rounded"
          required
        />
        <input
          type="number"
          name="classFee"
          value={formData.classFee}
          onChange={handleChange}
          placeholder="Class Fee"
          className="border p-2 rounded"
          required
        />
        <input
          type="date"
          name="date"
          value={formData.date}
          onChange={handleChange}
          className="border p-2 rounded"
          required
        />
        <input
          type="time"
          name="time"
          value={formData.time}
          onChange={handleChange}
          className="border p-2 rounded"
          required
        />

        <button
          type="submit"
          className="col-span-1 md:col-span-2 bg-indigo-600 hover:bg-indigo-700 text-white py-2 px-4 rounded"
        >
          {editing ? "Update Class" : "Add Class"}
        </button>
      </form>

      {/* Class Table */}
      <div className="overflow-x-auto mt-6">
        <table className="min-w-full text-sm text-gray-700">
          <thead className="bg-indigo-100">
            <tr>
              <th className="py-2 px-4">Grade</th>
              <th className="py-2 px-4">Subject</th>
              <th className="py-2 px-4">Teacher</th>
              <th className="py-2 px-4">Fee</th>
              <th className="py-2 px-4">Date</th>
              <th className="py-2 px-4">Time</th>
              <th className="py-2 px-4">Actions</th>
            </tr>
          </thead>
          <tbody>
            {classes.map((item) => (
              <tr key={item.classId} className="border-b hover:bg-gray-50">
                <td className="py-2 px-4">{item.grade}</td>
                <td className="py-2 px-4">{item.subject}</td>
                <td className="py-2 px-4">{item.teacherName}</td>
                <td className="py-2 px-4">Rs. {item.classFee}</td>
                <td className="py-2 px-4">{item.date}</td>
                <td className="py-2 px-4">{item.time}</td>
                <td className="py-2 px-4 space-x-2">
                  <button
                    className="bg-yellow-400 text-white px-3 py-1 rounded hover:bg-yellow-500"
                    onClick={() => handleEdit(item)}
                  >
                    Edit
                  </button>
                  <button
                    className="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600"
                    onClick={() => handleDelete(item.classId)}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
            {classes.length === 0 && (
              <tr>
                <td colSpan="7" className="text-center py-4 text-gray-500">
                  No classes found.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ClassManagement;
