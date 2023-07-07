import { useState } from 'react';
import { Button } from 'react-bootstrap';
import axios from 'axios';
import './Login.css';

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
        localStorage.setItem('token', token);
        window.location.href = "/";
      } else {
        console.error('Error:', response.status);
      }
    } catch (error) {
      console.error('Error:', error);
    }
  };

  const iniciarSesion = (e) => {
    e.preventDefault();
    handleSubmit(e);
  };

  const crearUsuario = (e) => {
    console.log("hola")
  };

  return (
    <div className="login text-center">
      <h1 className="login-title">Iniciar Sesión</h1>
      <form onSubmit={handleSubmit}>
        <div className='form-group margin-top'>
          <input
            className='input-inline'
            type="text"
            name="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
        </div>
        <div className='form-group margin-top'>
          <input
            type="password"
            name="password"
            class="input-inline"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
        <div className='form-group margin-top'>            
          <Button type="submit" variant="primary" size="sm" onClick={iniciarSesion}>
            Login
          </Button>
          <Button className="btn-margin" variant="success" size="sm" onClick={crearUsuario}>
            Crear Usuario
          </Button>
        </div>
      </form>
    </div>
  );
}