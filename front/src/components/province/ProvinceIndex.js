import React, { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import ProvinceForm from "./ProvinceForm";
import ProvinceList from "./ProvinceList";
import { Container, Button } from "react-bootstrap";

function ProvinceIndex() {
  const [showForm, setShowForm] = useState(false);

  const handleAddProvince = () => {
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
          onClick={handleAddProvince}
        >
          Agregar Provincia
        </Button>
        <ProvinceForm show={showForm} handleClose={handleCloseForm} />
        <ProvinceList />
      </div>
    </Container>
  );
}
export default ProvinceIndex;
