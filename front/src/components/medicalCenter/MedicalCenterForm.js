import React, { useState, useEffect } from "react";
import { Modal, Button, Form } from "react-bootstrap";
import axios from "axios";

/**
 *
 * @param {show} param1 recibe la condicion para que este abierto o cerrado el form
 * @param {handleClose} param2 recibe el evento para cerrar el form
 * @param {edit} param3 Si es para editar o agregar uno nuevo
 * @returns
 */
function MedicalCenterForm({ show, handleClose, edit = false }) {
  const [data, setData] = useState("");
  const [name, setName] = useState("");

  //Use efect se dispara cuando cambie la variable data, que se hará en la función que maneje el submit
  useEffect(() => {
    const postData = async () => {
      try {
        const response = await axios.post(
          "http://localhost:8080/api/medicalCenters",
          data
        );
        console.log(response.data);
        // Realizar acciones adicionales en función de la respuesta del servidor
      } catch (error) {
        console.error(error);
        // Manejar el error en caso de fallo en la solicitud POST
      }
    };

    postData();
  }, [data]);

  const handleSubmit = () => {
    // Lógica para recopilar y preparar los datos antes del envío
    const postData = {};
    setData(postData);
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
        <Modal.Title>Agregar Centro Médico</Modal.Title>
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

export default MedicalCenterForm;
