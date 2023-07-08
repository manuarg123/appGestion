import React, { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import MedicalCenterList from "./MedicalCenterList";
import { Container, Button } from "react-bootstrap";
import MedicalCenterForm from "./MedicalCenterForm";

function MedicalCenterIndex() {
  const [showForm, setShowForm] = useState(false);

  const handleAddMedicalCenter = () => {
    setShowForm(true);
  };

  const handleCloseForm = () => {
    setShowForm(false);
  };
  return (
    <Container className="main-color">
      <div>
        <Button
          className="btn-index"
          variant="success"
          size="sm"
          onClick={handleAddMedicalCenter}
        >
          Agregar Centro Médico
        </Button>
        <MedicalCenterForm show={showForm} handleClose={handleCloseForm} />
        <MedicalCenterList />
      </div>
    </Container>
  );
}
export default MedicalCenterIndex;
