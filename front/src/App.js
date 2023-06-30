import logo from './logo.svg';
import './App.css';
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Login } from './Login.js';
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
 const [data, setData] = useState([]);

   useEffect(() => {
     axios.get('http://localhost:8080/api/medicalCenters')
       .then(response => {
         setData(response.data);
       })
       .catch(error => {
         console.error(error);
       });
   }, []);

    return (
      <div>
        {data.length === 0 && <Login />}
        {data.map(item => (
          <p key={item.id}>{item.name}</p>
        ))}
      </div>
    );
}

export default App;
