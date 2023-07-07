import React, { useEffect, useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Logout } from '../login/Logout';

function MedicalCenterList({ data }) {
    return (
      <div>
        {data.map((item) => (
          <p key={item.id}>{item.name}</p>
        ))}
        <Logout>Logout</Logout>
      </div>
    );
  }
export default MedicalCenterList;