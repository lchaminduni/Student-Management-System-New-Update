import React, { useEffect, useState } from 'react';
import axios from 'axios';
import jsPDF from 'jspdf';
import Chart from 'chart.js/auto';


function PaymentManagement() {
  const [students, setStudents] = useState([]);
  const [studentId, setStudentId] = useState('');
  const [selectedStudent, setSelectedStudent] = useState(null);
  const [enrollments, setEnrollments] = useState([]);
  const [allStatuses, setAllStatuses] = useState([]);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchStudents();
  }, []);
  
  useEffect(() => {
    if (students.length > 0) {
      fetchAllStatuses();
    }
  }, [students]);
  

  const fetchStudents = async () => {
    try {
      const res = await axios.get('http://localhost:8080/api/students');
      setStudents(res.data);
    } catch (e) {
      setError('Failed to fetch students.');
    }
  };

  const fetchAllStatuses = async () => {
    try {
        const promises = students.map(async (student) => {
          try {
            const res = await axios.get(`http://localhost:8080/api/payments/status/${student.studentId}`);
            return { studentId: student.studentId, name: student.name, statuses: res.data };
          } catch {
            return { studentId: student.studentId, name: student.name, statuses: [] };
          }
        });
    
        const results = await Promise.all(promises);
        setAllStatuses(results);
      } catch (e) {
        console.error('Error fetching payment statuses:', e);
      }
  };

  const handleSearch = async () => {
    setError('');
    setEnrollments([]);
    setSelectedStudent(null);
    const student = students.find(s => s.studentId.toString() === studentId.trim());

    if (!student) {
      setError('Student not found.');
      return;
    }

    setSelectedStudent(student);
    setLoading(true);

    try {
      const res = await axios.get(`http://localhost:8080/api/payments/status/${studentId}`);
      const enriched = res.data.map(e => ({ ...e, paidDate: new Date().toISOString().split('T')[0] }));
      setEnrollments(enriched);
    } catch (e) {
      setError('Failed to fetch enrollment/payment status.');
    } finally {
      setLoading(false);
    }
  };

  const markAsPaid = async (classId, classFee) => {
    try {
      const today = new Date().toISOString().split('T')[0];
      const month = today.slice(0, 7);

      await axios.post('http://localhost:8080/api/payments/add', {
        studentId,
        classId,
        month,
        totalPayment: classFee,
        paymentDate: today,
      });

      alert('Payment recorded successfully.');
      handleSearch();
    } catch (err) {
      alert(err.response?.data || 'Payment failed.');
    }
  };

  const generatePDF = () => {
    const doc = new jsPDF();
    doc.setFontSize(18);
    doc.text('Payment Receipt', 70, 20);

    doc.setFontSize(12);
    doc.text(`Student: ${selectedStudent.name} (ID: ${selectedStudent.studentId})`, 20, 35);
    doc.text('Paid Classes:', 20, 45);

    let y = 55;
    enrollments.forEach((e, i) => {
      if (e.paid) {
        doc.text(`${i + 1}. Class ID: ${e.classId} | Fee: ${e.classFee} | Enroll Date: ${e.enrollDate} | Paid Date: ${e.paidDate}`, 20, y);
        y += 10;
      }
    });

    doc.save(`Payment_Receipt_${selectedStudent.studentId}.pdf`);
  };

  useEffect(() => {
    if (allStatuses.length > 0) {
      setTimeout(() => {
        drawChart();
      }, 100); // allow canvas to be rendered before drawing
    }
  }, [allStatuses]);
  

  const drawChart = () => {
    const months = {};

    allStatuses.forEach(s => {
      s.statuses.forEach(stat => {
        const month = new Date(stat.enrollDate).toISOString().slice(0, 7);
        if (!months[month]) months[month] = { paid: 0, unpaid: 0 };
        stat.paid ? months[month].paid++ : months[month].unpaid++;
      });
    });

    const canvas = document.getElementById('paymentChart');
    if (!canvas) return; // 🔴 Chart.js fails silently if canvas not found

    const ctx = canvas.getContext('2d');

    new Chart(ctx, {
      type: 'bar',
      data: {
        labels: Object.keys(months),
        datasets: [
          {
            label: 'Paid',
            backgroundColor: 'green',
            data: Object.values(months).map(m => m.paid),
          },
          {
            label: 'Unpaid',
            backgroundColor: 'red',
            data: Object.values(months).map(m => m.unpaid),
          },
        ],
      },
      options: {
        responsive: true,
        plugins: {
          title: {
            display: true,
            text: 'Monthly Payment Status',
          },
        },
      },
    });
  };

  return (
    <div className="p-8 max-w-6xl mx-auto bg-gray-50 min-h-screen">
      <h1 className="text-3xl font-bold mb-6 text-indigo-700">Payment Management</h1>

      <div className="mb-6 flex items-center space-x-4">
        <input
          type="text"
          value={studentId}
          onChange={(e) => setStudentId(e.target.value)}
          placeholder="Enter Student ID"
          className="border p-2 rounded w-64"
        />
        <button onClick={handleSearch} className="bg-indigo-600 text-white px-4 py-2 rounded hover:bg-indigo-700">
          Search
        </button>
      </div>

      {error && <p className="text-red-600 mb-4">{error}</p>}
      {loading && <p>Loading student data...</p>}

      {selectedStudent && (
        <div className="bg-white p-6 rounded shadow">
          <h2 className="text-xl font-semibold mb-3">
            Student: {selectedStudent.name} (ID: {selectedStudent.studentId})
          </h2>

          {enrollments.length === 0 ? (
            <p>No enrollments found.</p>
          ) : (
            <>
              <table className="w-full border border-collapse text-sm mb-6">
                <thead className="bg-indigo-100">
                  <tr>
                    <th className="border p-2">Class ID</th>
                    <th className="border p-2">Enroll Date</th>
                    <th className="border p-2">Fee</th>
                    <th className="border p-2">Status</th>
                    <th className="border p-2">Action</th>
                  </tr>
                </thead>
                <tbody>
                  {enrollments.map((e, i) => (
                    <tr key={i} className="hover:bg-gray-50">
                      <td className="border p-2">{e.classId}</td>
                      <td className="border p-2">{e.enrollDate}</td>
                      <td className="border p-2">{e.classFee}</td>
                      <td className="border p-2 font-semibold">
                        {e.paid ? (
                          <span className="text-green-600">Paid</span>
                        ) : (
                          <span className="text-red-600">Unpaid</span>
                        )}
                      </td>
                      <td className="border p-2">
                        {!e.paid && (
                          <button
                            className="bg-green-500 text-white px-3 py-1 rounded hover:bg-green-600"
                            onClick={() => markAsPaid(e.classId, e.classFee)}
                          >
                            Mark Paid
                          </button>
                        )}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>

              <button
                onClick={generatePDF}
                className="bg-purple-600 text-white px-4 py-2 rounded hover:bg-purple-700"
              >
                Get Payment Slip (PDF)
              </button>
            </>
          )}
        </div>
      )}

      {/* Side-by-side Chart and Table */}
<div className="mt-12 flex flex-col md:flex-row gap-6">
  {/* Chart Section */}
  <div className="md:w-1/2 bg-white p-4 rounded shadow">
    <h2 className="text-xl font-semibold mb-4">Monthly Payment Chart</h2>
    <canvas id="paymentChart" height="300"></canvas>
  </div>

  {/* All Student Status Table */}
  <div className="md:w-1/2 bg-white p-4 rounded shadow overflow-auto max-h-[500px]">
    <h2 className="text-xl font-semibold mb-4">All Students Payment Status</h2>
    <table className="w-full border border-collapse text-sm">
      <thead className="bg-gray-200 sticky top-0 z-10">
        <tr>
          <th className="border p-2">Student ID</th>
          <th className="border p-2">Name</th>
          <th className="border p-2">Class ID</th>
          <th className="border p-2">Enroll Date</th>
          <th className="border p-2">Fee</th>
          <th className="border p-2">Status</th>
        </tr>
      </thead>
      <tbody>
        {allStatuses.flatMap((s, i) =>
          s.statuses.map((e, j) => (
            <tr key={`${i}-${j}`}>
              <td className="border p-2">{s.studentId}</td>
              <td className="border p-2">{s.name}</td>
              <td className="border p-2">{e.classId}</td>
              <td className="border p-2">{e.enrollDate}</td>
              <td className="border p-2">{e.classFee}</td>
              <td className="border p-2">{e.paid ? 'Paid' : 'Unpaid'}</td>
            </tr>
          ))
        )}
      </tbody>
    </table>
  </div>
</div>

    </div>
  );
}

export default PaymentManagement;
