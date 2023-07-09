import AppNavbar from "../navbar/AppNavbar.js";
import MedicalCenterIndex from "../../medicalCenter/MedicalCenterIndex";
import ProvinceIndex from "../../province/ProvinceIndex.js";
import React, { useEffect, useState } from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";
import axios from "axios";
import { Container } from "react-bootstrap";

const Home = () => {
  const hasToken = localStorage.getItem("token");
  const [isMainPage, setIsMainPage] = useState(true); //Para no mostrar contenido propio de Home al acceder a alguno de los index

  useEffect(() => {
    if (window.location.pathname !== "/") {
      setIsMainPage(false);
    }
  }, []);

  return (
    <div>
      <AppNavbar />
      <Container>
        {isMainPage && <h1> Bienvenido a la página principal</h1>}
      </Container>
      <Routes>
        <Route path="/medicalCenters" element={<MedicalCenterIndex />} />
        <Route path="/provinces" element={<ProvinceIndex />} />
      </Routes>
    </div>
  );
};

export default Home;
