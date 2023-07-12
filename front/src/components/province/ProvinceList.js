import React, { useEffect, useState } from "react";
import axios from "axios";
import ProvinceForm from "./ProvinceForm";
import "bootstrap/dist/css/bootstrap.min.css";
import { Table, Button } from "react-bootstrap";

function ProvinceList() {
  const [data, setData] = useState([]);
  const [showForm, setShowForm] = useState(false);
  const [selectedProvinceId, setSelectedProvinceId] = useState(null);
  const [provinceList, setProvinceList] = useState([]); //Para actualizar la lista sin recargar la página

  useEffect(() => {
    fetchProvinceList();
  }, []);

  const handleEditProvince = (provinceId) => {
    setSelectedProvinceId(provinceId);
    setShowForm(true);
  };

  const handleCloseForm = () => {
    setShowForm(false);
  };

  //Lo paso a una función para actualizarr la lista sin recargar al agregar o editar provincia
  const fetchProvinceList = async () => {
    try {
      const token = localStorage.getItem("token");

      if (!token) {
        return;
      }
      
      const response = await axios.get("http://localhost:8080/api/provinces", {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.token}`,
        },
      });
      setProvinceList(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div>
      <Table responsive>
        <thead>
          <tr>
            <th className="text-center">ID</th>
            <th className="text-center">Nombre</th>
            <th className="text-center">Acciones</th>
          </tr>
        </thead>
        <tbody>
          {provinceList.map((province) => (
            <tr key={province.id}>
              <td className="text-center">{province.id}</td>
              <td className="text-center">{province.name}</td>
              <td>
                <Button
                  className="btn-index"
                  variant="primary"
                  size="sm"
                  onClick={() => handleEditProvince(province.id)}
                >
                  Editar Provincia
                </Button>
                <ProvinceForm
                  show={showForm}
                  handleClose={handleCloseForm}
                  provinceId={selectedProvinceId}
                  fetchProvinceList={fetchProvinceList}
                />
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
  );
}

export default ProvinceList;
