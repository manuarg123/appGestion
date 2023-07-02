import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Login } from './components/login/Login.js';
import 'bootstrap/dist/css/bootstrap.min.css';
import Hola from './Hola.js';
function App() {
  const [data, setData] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const token = localStorage.getItem('token');
        const response = await axios.get('http://localhost:8080/api/medicalCenters', {
          headers: {
            'Content-Type' : 'application/json',
            Authorization : `Bearer ${localStorage.token}`
          }
        });

        setData(response.data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchData();
  }, []);

  return (
    <Router>
      <div>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/" element={<Hola data={data} />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;