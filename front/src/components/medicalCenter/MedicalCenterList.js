import React, { useEffect, useState } from "react";
import axios from "axios";

import "bootstrap/dist/css/bootstrap.min.css";
import Table from "react-bootstrap/Table";

function MedicalCenterList() {
  const [data, setData] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const token = localStorage.getItem("token");

        if (!token) {
          return;
        }

        const response = await axios.get(
          "http://localhost:8080/api/medicalCenters",
          {
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${localStorage.token}`,
            },
          }
        );

        setData(response.data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchData();
  }, []);

  return (
    <div>
      <Table responsive>
        <thead>
          <tr>
            <th className="text-center">Centro Médico</th>
            <th className="text-center">Dirección</th>
            <th className="text-center">Localidad</th>
            <th className="text-center">Email</th>
            <th className="text-center">Teléfono</th>
            <th className="text-center">Acciones</th>
          </tr>
        </thead>
        <tbody>
          {data.map((medicalCenter, index) => (
            <tr key={medicalCenter.id}>
              <td className="text-center">{medicalCenter.fullName}</td>
              <td className="text-center">
                {medicalCenter.addresses.length > 0
                  ? medicalCenter.addresses.map((address, index) => {
                      if (index === 0) {
                        return address.street + " " + address.number;
                      }
                    })
                  : "-"}
              </td>
              <td className="text-center">
                {medicalCenter.addresses.length > 0
                  ? medicalCenter.addresses.map((address, index) => {
                      if (index === 0) {
                        return address.location.name;
                      }
                    })
                  : "-"}
              </td>
              <td className="text-center">
                {medicalCenter.emails.length > 0
                  ? medicalCenter.emails.map((email, index) => {
                      if (index === 0) {
                        return email.value;
                      }
                    })
                  : "-"}
              </td>
              <td className="text-center">
                {medicalCenter.phones.length > 0
                  ? medicalCenter.phones.map((phone, index) => {
                      if (index === 0) {
                        return phone.number;
                      }
                    })
                  : "-"}
              </td>
              <td></td>
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
  );
}
export default MedicalCenterList;
