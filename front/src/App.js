import React, { useEffect, useState } from "react";
import axios from "axios";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";
import { Login } from "./components/login/Login.js";
import "bootstrap/dist/css/bootstrap.min.css";
import { Logout } from "./components/login/Logout.js";
import MedicalCenterList from "./components/medicalCenter/MedicalCenterList.js";
import Home from "./components/layout/home/Home.js";
import { Container } from "react-bootstrap";
import "./style.css";

function App() {
  const [data, setData] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const token = localStorage.getItem("token");

        if (!token) {
          return;
        }

        const response = await axios.get(
          "http://localhost:8080/api/medicalCenters",
          {
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${localStorage.token}`,
            },
          }
        );

        setData(response.data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchData();
  }, []);

  const hasToken = localStorage.getItem("token");

  return (
    <Container className="body-color">
      <div>
        <Router>
          <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/logout" element={<Logout />} />
            {hasToken ? (
              <Route path="*" element={<Home></Home>} />
            ) : (
              <Route path="*" element={<Navigate to="/login" />} />
            )}
          </Routes>
        </Router>
      </div>
    </Container>
  );
}

export default App;
