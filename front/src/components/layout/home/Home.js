
import AppNavbar from '../navbar/AppNavbar.js';
import MedicalCenterList from '../../medicalCenter/MedicalCenterList'; 
import React, { useEffect, useState } from 'react';
import axios from 'axios';

const Home = () => {
    
  return (
    <div>
      <AppNavbar />
      <div>
        {/* Contenido de la página principal */}
        <h1>Bienvenido a mi página principal</h1>
        <p>Este es el contenido de la página de inicio.</p>
        <MedicalCenterList />
      </div>
    </div>
  );
};

export default Home;