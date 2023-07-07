import { useState } from 'react';
import { Button } from 'react-bootstrap';
import axios from 'axios';

export function Logout() {
  const handleSubmit = async (e) => {
    localStorage.token = "";
  };

  return (
    <div >
      <h1 className="login-title">Log in to Twitter</h1>
      <form onSubmit={handleSubmit}>
        <Button type="submit" variant="primary">
          Logout
        </Button>
      </form>
    </div>
  );
}