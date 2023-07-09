import React, { useState, useEffect } from "react";
import { Modal, Button, Form } from "react-bootstrap";
import axios from "axios";

function ProvinceForm({ show, handleClose, provinceId = null }) {
  const [data, setData] = useState("");
  const [name, setName] = useState("");
  const [shouldPostData, setShouldPostData] = useState(false);
  
  useEffect(() => {
    const postData = async () => {
      try {
        const token = localStorage.getItem("token");

        if (!token) {
          return;
        }

        const response = await axios.post(
          "http://localhost:8080/api/provinces/new",
          data,
          {
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${localStorage.token}`,
            },
          }
        );
        console.log(response);
      } catch (error) {
        console.error(error);
      }
    };

    if (shouldPostData) {
      postData();
      setShouldPostData(false);
    }
  }, [data, shouldPostData]);

  const handleSubmit = () => {
    const postData = {
      name: name,
    };

    setData(postData);
    setShouldPostData(true);
  };

  return (
    <Modal
      size="lg"
      aria-labelledby="contained-modal-title-vcenter"
      centered
      show={show}
      onHide={handleClose}
    >
      <Modal.Header closeButton>
        <Modal.Title>Agregar Provincia</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
            <Form.Label>Nombre:</Form.Label>
            <Form.Control
              type="text"
              placeholder="Ingresar Nombre.."
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
          </Form.Group>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={handleClose}>
          Cerrar
        </Button>
        <Button variant="primary" onClick={handleSubmit}>
          Guardar
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

export default ProvinceForm;
