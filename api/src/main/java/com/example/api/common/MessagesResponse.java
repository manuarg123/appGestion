package com.example.api.common;

import lombok.Getter;
import lombok.Setter;

@Getter
public class MessagesResponse {
    public static String addSuccess = "Registro agregado exitosamente";
    public static String addFailed = "Ocurrió un error al agregar el registro";
    public static String editSuccess = "Registro actualizado exitosamente";
    public static String editFailed = "Ocurrió un error al actualizar el registro";
    public static String deleteSuccess = "Se elimino exitosamente";
    public static String deleteFailed = "Ocurrió un error al eliminar el registro";
    public static String recordNameExists = "Ya existe un registro con ese nombre";
    public static String recordNotFound = "Nonexistent record";
    public static String logoutSuccess = "Logout Success";
    public static String logoutFailed = "Logout Failed";
    public static String loginSuccess = "Login Sucess";
    public static String loginFailed = "Login Failed";
    public static String nameNotNull = "Name cannot be null";
    public static String recordWasDeleted = "This record was deleted";
    public static String nameAlreadyExists = "Record with the same name already exists.";
    public static String notValidParameters = "The parameters received are not valid";
    public static String recordWasSoftDeleted = "The record is Soft deleted";
}
