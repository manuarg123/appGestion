import React from 'react';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import './AppNavbar.css';
import { Logout } from '../../login/Logout';

const AppNavbar = () => {
  return (
    <Navbar expand="lg" className="bg-body-tertiary main-color">
    <Container>
      <Navbar.Brand href="#home">App Gestion</Navbar.Brand>
      <Navbar.Toggle aria-controls="basic-navbar-nav" />
      <Navbar.Collapse id="basic-navbar-nav">
        <Nav className="me-auto">
          <Nav.Link href="#home">Home</Nav.Link>
          <Nav.Link href="#link">Link</Nav.Link>
          <NavDropdown title="Dropdown" id="basic-nav-dropdown">
            <NavDropdown.Item href="#action/3.1">Centros médicos</NavDropdown.Item>          
            <NavDropdown.Divider />
            <NavDropdown.Item href="#action/3.4">
              Home
            </NavDropdown.Item>
          </NavDropdown>
        </Nav>
      </Navbar.Collapse>
      <Logout>Logout</Logout>
    </Container>
  </Navbar>
  );
};

export default AppNavbar;