
import AppNavbar from '../navbar/AppNavbar.js';
import MedicalCenterIndex from '../../medicalCenter/MedicalCenterIndex'; 
import ProvinceIndex from '../../province/ProvinceIndex.js';
import React, { useEffect, useState } from 'react';
import { BrowserRouter as Router, Switch,Routes, Route, Navigate } from 'react-router-dom';
import axios from 'axios';

const Home = () => {
   const hasToken = localStorage.getItem('token');
  return (
    <Router>
      <div>
        <AppNavbar />
        <Switch>
          <Route path="/medicalCenters" component={MedicalCenterIndex} />
          <Route path="/provinceCenters" component={ProvinceIndex} />
        </Switch>
      </div>
    </Router>
  );
};

export default Home;