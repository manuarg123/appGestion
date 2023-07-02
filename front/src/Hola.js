import React, { useEffect, useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

function Hola({ data }) {
    return (
      <div>
        <h1>HOLA</h1>
        {data.map((item) => (
          <p key={item.id}>{item.name}</p>
        ))}
      </div>
    );
  }
export default Hola;