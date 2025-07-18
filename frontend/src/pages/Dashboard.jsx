import React, { useEffect, useState } from "react";
import axios from "axios";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  Tooltip,
  ResponsiveContainer,
  CartesianGrid,
} from "recharts";
import {
  Users,
  BookOpen,
  FileText,
  ArrowRight,
} from "lucide-react";
import { Link, useNavigate } from "react-router-dom"; // ✅ import useNavigate

const Dashboard = () => {
  const [summary, setSummary] = useState({
    students: 0,
    classes: 0,
    enrollments: 0,
    payments: 0,
  });
  const [registrationData, setRegistrationData] = useState([]);
  const navigate = useNavigate(); // ✅ for redirecting on logout

  const handleLogout = () => {
    // Clear token or session if stored
    localStorage.removeItem("token");
    sessionStorage.clear();
    navigate("/"); // Redirect to login page
  };

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/dashboard/summary")
      .then((res) => setSummary(res.data))
      .catch((err) => console.error("Failed to fetch dashboard summary", err));

    axios
      .get("http://localhost:8080/api/dashboard/registrations")
      .then((res) => {
        const data = Object.entries(res.data).map(([month, count]) => ({
          month,
          count,
        }));
        setRegistrationData(data);
      })
      .catch((err) =>
        console.error("Failed to fetch registration stats", err)
      );
  }, []);

  return (
    <div className="w-full min-h-screen bg-gray-100 p-6">
      {/* Header with Logout */}
      <div className="flex justify-between items-center mb-8">
        <h2 className="text-3xl font-bold text-gray-800">Dashboard Overview</h2>
        <button
          onClick={handleLogout}
          className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600 transition"
        >
          Logout
        </button>
      </div>

      {/* Summary Cards */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-10">
        <Link to="/students" className="bg-white p-6 rounded-xl shadow hover:shadow-lg flex items-center gap-4 transition">
          <Users className="text-blue-500" size={40} />
          <div>
            <p className="text-gray-600">Students</p>
            <p className="text-xl font-bold">{summary.students}</p>
          </div>
          <ArrowRight className="ml-auto text-blue-400" />
        </Link>

        <Link to="/classes" className="bg-white p-6 rounded-xl shadow hover:shadow-lg flex items-center gap-4 transition">
          <BookOpen className="text-green-500" size={40} />
          <div>
            <p className="text-gray-600">Classes</p>
            <p className="text-xl font-bold">{summary.classes}</p>
          </div>
          <ArrowRight className="ml-auto text-green-400" />
        </Link>

        <Link to="/enrollments" className="bg-white p-6 rounded-xl shadow hover:shadow-lg flex items-center gap-4 transition">
          <FileText className="text-yellow-500" size={40} />
          <div>
            <p className="text-gray-600">Enrollments</p>
            <p className="text-xl font-bold">{summary.enrollments}</p>
          </div>
          <ArrowRight className="ml-auto text-yellow-400" />
        </Link>

        <Link to="/payments" className="bg-white p-6 rounded-xl shadow hover:shadow-lg flex items-center gap-4 transition">
          <span className="text-purple-500 text-4xl">Rs</span>
          <div>
            <p className="text-gray-600">Payments</p>
            <p className="text-xl font-bold">{summary.payments}</p>
          </div>
          <ArrowRight className="ml-auto text-purple-400" />
        </Link>
      </div>

      {/* Student Registration Chart */}
      <div className="bg-white p-6 rounded-xl shadow">
        <h3 className="text-2xl font-semibold mb-4 text-gray-800">
          Monthly Student Registrations
        </h3>
        {registrationData.length === 0 ? (
          <p className="text-gray-500">No data available.</p>
        ) : (
          <ResponsiveContainer width="100%" height={300}>
            <LineChart data={registrationData}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="month" />
              <YAxis allowDecimals={false} />
              <Tooltip />
              <Line type="monotone" dataKey="count" stroke="#3b82f6" strokeWidth={3} />
            </LineChart>
          </ResponsiveContainer>
        )}
      </div>
    </div>
  );
};

export default Dashboard;
