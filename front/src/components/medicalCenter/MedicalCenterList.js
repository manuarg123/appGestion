import React, { useEffect, useState } from 'react';
import axios from 'axios';

import 'bootstrap/dist/css/bootstrap.min.css';
import { Logout } from '../login/Logout';
import Table from 'react-bootstrap/Table';

function MedicalCenterList() {
  const [data, setData] = useState([]);

    useEffect(() => {
      const fetchData = async () => {
        try {
          const token = localStorage.getItem('token');
  
          if (!token) {
            return;
          }
  
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
      <div>
      <Table responsive>
        <thead>
          <tr>
            <th>Número</th>
            <th>Centro Médico</th>
            {/* {Array.from({ length: 12 }).map((_, index) => (
              <th key={index}>Table heading</th>
            ))} */}
          </tr>
        </thead>
        <tbody>
          {data.map((item, index) => (
            <tr key={item.id}>
              <td>{index + 1}</td>
              <td>{item.name}</td>
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
    );
  }
export default MedicalCenterList;