import React, { useEffect, useState} from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import MedicalCenterList from './MedicalCenterList';
import { Container, Button } from 'react-bootstrap';

function MedicalCenterIndex() {
   return (
    <Container className="main-color">
        <div>
            <Button className="btn-index" variant="success" size="sm">
                Agregar Centro Médico
            </Button>
            <MedicalCenterList/>
        </div>
    </Container>
   ); 
}
export default MedicalCenterIndex;