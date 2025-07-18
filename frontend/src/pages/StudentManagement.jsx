import React, { useState, useEffect } from "react";
import axios from "axios";

const emptyForm = {
  studentId: "",
  name: "",
  address: "",
  birthDate: "",
  fmName: "",
  contactNumber: "",
  gender: "",
  registeredDate: "",
};

export default function StudentManagement() {
  const [students, setStudents] = useState([]);
  const [form, setForm] = useState(emptyForm);
  const [isUpdate, setIsUpdate] = useState(false);
  const [search, setSearch] = useState("");
  const [filteredStudents, setFilteredStudents] = useState([]);

  const apiBase = "http://localhost:8080/api/students";

  useEffect(() => {
    fetchStudents();
  }, []);

  useEffect(() => {
    if (search.trim() === "") {
      setFilteredStudents(students);
    } else {
      const filtered = students.filter((s) =>
        s.name.toLowerCase().includes(search.toLowerCase()) ||
        s.studentId.toString().includes(search)
      );
      setFilteredStudents(filtered);
    }
  }, [search, students]);

  const fetchStudents = async () => {
    try {
      const res = await axios.get(apiBase);
      setStudents(res.data);
      setFilteredStudents(res.data);
    } catch (error) {
      alert("Failed to fetch students");
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Validate required fields
    if (!form.name || !form.birthDate || !form.gender) {
      alert("Please fill in all required fields.");
      return;
    }

    try {
      if (isUpdate) {
        await axios.put(`${apiBase}/update`, form);
        alert("Student updated successfully");
      } else {
        await axios.post(`${apiBase}/register`, form);
        alert("Student registered successfully");
      }
      fetchStudents();
      clearForm();
    } catch (error) {
      alert("Operation failed");
    }
  };

  const handleEdit = (student) => {
    setForm(student);
    setIsUpdate(true);
  };

  const handleDelete = async (studentId) => {
    if (window.confirm("Are you sure to delete this student?")) {
      try {
        await axios.delete(`${apiBase}/${studentId}`);
        alert("Student deleted successfully");
        fetchStudents();
      } catch (error) {
        alert("Delete failed");
      }
    }
  };

  const clearForm = () => {
    setForm(emptyForm);
    setIsUpdate(false);
  };

  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <h1 className="text-3xl font-bold mb-6 text-center text-indigo-700">
        Student Management
      </h1>
      <div className="max-w-7xl mx-auto grid grid-cols-1 md:grid-cols-3 gap-8">
        {/* Form Section */}
        <div className="bg-white p-6 rounded shadow-md ">
          <h2 className="text-xl font-semibold mb-4">
            {isUpdate ? "Update Student" : "Register New Student"}
          </h2>
          <form onSubmit={handleSubmit} className="space-y-4">
            {/* Name */}
            <div>
              <label className="block mb-1 font-medium">Name *</label>
              <input
                type="text"
                value={form.name}
                onChange={(e) => setForm({ ...form, name: e.target.value })}
                className="w-full p-2 border rounded focus:outline-indigo-500"
                required
              />
            </div>
            {/* Address */}
            <div>
              <label className="block mb-1 font-medium">Address</label>
              <input
                type="text"
                value={form.address}
                onChange={(e) => setForm({ ...form, address: e.target.value })}
                className="w-full p-2 border rounded focus:outline-indigo-500"
              />
            </div>
            {/* Birth Date */}
            <div>
              <label className="block mb-1 font-medium">Birth Date *</label>
              <input
                type="date"
                value={form.birthDate}
                onChange={(e) => setForm({ ...form, birthDate: e.target.value })}
                className="w-full p-2 border rounded focus:outline-indigo-500"
                required
              />
            </div>
            {/* Parent/Guardian Name */}
            <div>
              <label className="block mb-1 font-medium">Parent/Guardian Name</label>
              <input
                type="text"
                value={form.fmName}
                onChange={(e) => setForm({ ...form, fmName: e.target.value })}
                className="w-full p-2 border rounded focus:outline-indigo-500"
              />
            </div>
            {/* Contact Number */}
            <div>
              <label className="block mb-1 font-medium">Contact Number</label>
              <input
                type="tel"
                value={form.contactNumber}
                onChange={(e) =>
                  setForm({ ...form, contactNumber: e.target.value })
                }
                className="w-full p-2 border rounded focus:outline-indigo-500"
                placeholder="e.g. +94771234567"
              />
            </div>
            {/* Gender */}
            <div>
              <label className="block mb-1 font-medium">Gender *</label>
              <select
                value={form.gender}
                onChange={(e) => setForm({ ...form, gender: e.target.value })}
                className="w-full p-2 border rounded focus:outline-indigo-500"
                required
              >
                <option value="">Select Gender</option>
                <option value="Male">Male</option>
                <option value="Female">Female</option>
                <option value="Other">Other</option>
              </select>
            </div>

            {/* Registered Date */}
            <div>
              <label className="block mb-1 font-medium">Registered Date *</label>
              <input
                type="date"
                value={form.registeredDate}
                onChange={(e) => setForm({ ...form, registeredDate: e.target.value })}
                className="w-full p-2 border rounded focus:outline-indigo-500"
                required
              />
            </div>


            <div className="flex gap-4">
              <button
                type="submit"
                className="bg-indigo-600 text-white px-4 py-2 rounded hover:bg-indigo-700 transition"
              >
                {isUpdate ? "Update Student" : "Register Student"}
              </button>
              <button
                type="button"
                onClick={clearForm}
                className="bg-gray-400 text-white px-4 py-2 rounded hover:bg-gray-500 transition"
              >
                Clear
              </button>
            </div>
          </form>
        </div>

        {/* List & Search Section */}
        <div className="md:col-span-2 bg-white p-6 rounded shadow-md overflow-auto max-h-[75vh] w-[1000px]">

          <h2 className="text-xl font-semibold mb-4">Students List</h2>

          {/* Search */}
          <div className="mb-4">
            <input
              type="text"
              placeholder="Search by Name or ID"
              value={search}
              onChange={(e) => setSearch(e.target.value)}
              className="w-full p-2 border rounded focus:outline-indigo-500"
            />
          </div>

          {/* Table */}
<table className="w-full table-auto border-collapse border border-gray-300 text-sm">
  <thead className="bg-indigo-100">
    <tr>
      <th className="border border-gray-300 p-2">Student ID</th>
      <th className="border border-gray-300 p-2">Name</th>
      <th className="border border-gray-300 p-2 min-w-[150px]">Birth Date</th>
      <th className="border border-gray-300 p-2">Gender</th>
      <th className="border border-gray-300 p-2">Contact Number</th>
      <th className="border border-gray-300 p-2">Parent/Guardian Name</th>
      <th className="border border-gray-300 p-2">Registered Date</th>
      <th className="border border-gray-300 p-2 min-w-[180px]">Action</th>
    </tr>
  </thead>
  <tbody>
    {filteredStudents.length > 0 ? (
      filteredStudents.map((student) => (
        <tr key={student.studentId} className="hover:bg-indigo-50">
          <td className="border border-gray-300 p-2">{student.studentId}</td>
          <td className="border border-gray-300 p-2">{student.name}</td>
          <td className="border border-gray-300 p-2">{student.birthDate}</td>
          <td className="border border-gray-300 p-2">{student.gender}</td>
          <td className="border border-gray-300 p-2">{student.contactNumber}</td>
          <td className="border border-gray-300 p-2">{student.fmName}</td>
          <td className="border border-gray-300 p-2">{student.registeredDate}</td>
          <td className="border border-gray-300 p-2 space-x-2">
            <button
              onClick={() => handleEdit(student)}
              className="bg-yellow-400 px-2 py-1 rounded hover:bg-yellow-500"
            >
              Edit
            </button>
            <button
              onClick={() => handleDelete(student.studentId)}
              className="bg-red-500 px-2 py-1 rounded text-white hover:bg-red-600"
            >
              Delete
            </button>
          </td>
        </tr>
      ))
    ) : (
      <tr>
        <td
          colSpan="7"
          className="text-center p-4 text-gray-500 italic"
        >
          No students found.
        </td>
      </tr>
    )}
  </tbody>
</table>

        </div>
      </div>
    </div>
  );
}
