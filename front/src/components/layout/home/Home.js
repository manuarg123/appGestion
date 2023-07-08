
import AppNavbar from '../navbar/AppNavbar.js';
import MedicalCenterIndex from '../../medicalCenter/MedicalCenterIndex'; 
import React, { useEffect, useState } from 'react';
import axios from 'axios';

const Home = () => {
    
  return (
    <div>
      <AppNavbar />
      <div>
        <MedicalCenterIndex />
      </div>
    </div>
  );
};

export default Home;