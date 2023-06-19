import logo from './logo.svg';
import './App.css';
import React, { useEffect, useState } from 'react';
import axios from 'axios';

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
       {data.map(item => (
         <p key={item.id}>{item.name}</p>
       ))}
     </div>
   );
}

export default App;
