import React, { useState, useEffect } from "react";
import { Modal, Button, Form } from "react-bootstrap";
import axios from "axios";

function ProvinceForm({
  show,
  handleClose,
  provinceId = null,
  fetchProvinceList,
}) {
  const [provinceData, setProvinceData] = useState(null);
  const [name, setName] = useState(provinceData ? provinceData.name : "");
  const [shouldPostData, setShouldPostData] = useState(false);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const token = localStorage.getItem("token");

        if (!token) {
          return;
        }

        if (provinceId) {
          const response = await axios.get(
            `http://localhost:8080/api/provinces/show/${provinceId}`,
            {
              headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${localStorage.token}`,
              },
            }
          );
          setProvinceData(response.data.data);
          setName(response.data.data.name);
        }
      } catch (error) {
        console.error(error);
      }
    };

    fetchData();
  }, [provinceId]);

  const handleSubmit = async () => {
    try {
      const token = localStorage.getItem("token");

      if (!token) {
        return;
      }

      if (provinceId) {
        const response = await axios.put(
          `http://localhost:8080/api/provinces/edit/${provinceId}`,
          {
            name: name,
          },
          {
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${localStorage.token}`,
            },
          }
        );
      } else {
        // Agregar nueva provincia
        const response = await axios.post(
          "http://localhost:8080/api/provinces/new",
          {
            name: name,
          },
          {
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${localStorage.token}`,
            },
          }
        );
      }
      fetchProvinceList(); //Actualiza lista
    } catch (error) {
      console.error(error);
    }
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
        <Modal.Title>
          {provinceId ? "Editar Provincia" : "Agregar Provincia"}
        </Modal.Title>
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
          {provinceId ? "Guardar Cambios" : "Agregar"}
        </Button>
      </Modal.Footer>
    </Modal>
  );
}

export default ProvinceForm;
