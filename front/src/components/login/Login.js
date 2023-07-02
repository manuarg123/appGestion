import { useState } from 'react';
import { Button } from 'react-bootstrap';
import axios from 'axios';

export function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    const data = {
      username: username,
      password: password
    };

    try {
      const response = await axios.post('http://localhost:8080/login', data, {
        headers: {
          'Content-Type': 'application/json'
        }
      });

      if (response.status === 200) {
        const token = response.data.data.accessToken;
        // Guardar el token en el Local Storage
        localStorage.setItem('token', token);
        // Redireccionar o realizar otras acciones según necesidad
      } else {
        console.error('Error:', response.status);
      }
    } catch (error) {
      console.error('Error:', error);
    }
  };

  const manejadorBoton = (e) => {
    e.preventDefault();
    handleSubmit(e);
  };

  return (
    <div className="login">
      <h1 className="login-title">Log in to Twitter</h1>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <input
          type="password"
          name="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <Button type="submit" variant="primary" onClick={manejadorBoton}>
          Login
        </Button>
      </form>
    </div>
  );
}