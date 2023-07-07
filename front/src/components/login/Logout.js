import { useState } from 'react';
import { Button } from 'react-bootstrap';
import axios from 'axios';
import './Login.css';

export function Logout() {
  const handleSubmit = async (e) => {
    localStorage.token = "";
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <button type="submit" className="logout-button">
          <img src={`${process.env.PUBLIC_URL}/images/logout.png`} alt="Logout" />
        </button>
      </form>
    </div>
  );
}