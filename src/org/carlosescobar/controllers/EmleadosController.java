/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carlosescobar.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.carlosescobar.bean.CargoEmpleado;
import org.carlosescobar.bean.Empleados;
import org.carlosescobar.db.Conexion;
import org.carlosescobar.system.Main;

/**
 *
 * @author Usuario
 */
public class EmleadosController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Empleados> ListaEmpleados;
    private ObservableList<CargoEmpleado> listaCargo;
    @FXML
    private Button btnRegresar;
    @FXML
    private TableView tblEmpleado;

    @FXML
    private TableColumn colCodigoE;

    @FXML
    private TableColumn colNombreE;

    @FXML
    private TableColumn colApellidoE;

    @FXML
    private TableColumn colSueldoE;

    @FXML
    private TableColumn colDireccionE;

    @FXML
    private TableColumn colTurnoE;

    @FXML
    private TableColumn colCodigoCargoE;

    @FXML
    private Button btnAgregar;

    @FXML
    private ImageView imgAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private ImageView imgEliminar;

    @FXML
    private Button btnEditar;

    @FXML
    private ImageView imgEditar;

    @FXML
    private Button btnReportes;

    @FXML
    private ImageView imgReportes;

    @FXML
    private TextField txtCodigoE;

    @FXML
    private TextField txtNombreE;

    @FXML
    private TextField txtApellidoE;

    @FXML
    private TextField txtSueldoE;

    @FXML
    private TextField txtDireccionE;

    @FXML
    private TextField txtTurnoE;

    @FXML
    private ComboBox cmbCodigoCargoE;

    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        cmbCodigoCargoE.setItems(getCargo());
    }

    public void cargarDatos() {
        tblEmpleado.setItems(getClientes());
        colCodigoE.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("codigoEmpleado"));
        colNombreE.setCellValueFactory(new PropertyValueFactory<Empleados, String>("nombresEmpleados"));
        colApellidoE.setCellValueFactory(new PropertyValueFactory<Empleados, String>("apellidosEmpleados"));
        colSueldoE.setCellValueFactory(new PropertyValueFactory<Empleados, Double>("sueldo"));
        colDireccionE.setCellValueFactory(new PropertyValueFactory<Empleados, String>("direccion"));
        colTurnoE.setCellValueFactory(new PropertyValueFactory<Empleados, String>("turno"));
        colCodigoCargoE.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("codigoCargoEmpleado"));

    }

    public ObservableList<Empleados> getClientes() {
        ArrayList<Empleados> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarEmpleados()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Empleados(resultado.getInt("codigoEmpleado"),
                        resultado.getString("nombresEmpleados"),
                        resultado.getString("apellidosEmpleados"),
                        resultado.getDouble("sueldo"),
                        resultado.getString("direccion"),
                        resultado.getString("turno"),
                        resultado.getInt("codigoCargoEmpleado")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ListaEmpleados = FXCollections.observableList(lista);
    }

    public ObservableList<CargoEmpleado> getCargo() {
        ArrayList<CargoEmpleado> Lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{ call sp_listarCargos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                Lista.add(new CargoEmpleado(resultado.getInt("codigoCargoEmpleado"),
                        resultado.getString("nombreCargo"),
                        resultado.getString("descripcionCargo")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaCargo = FXCollections.observableList(Lista);
    }

    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();

                btnEditar.setDisable(true);
                btnEliminar.setDisable(true);
                imgAgregar.setImage(new Image("/org/carlosescobar/images/Guardar.png"));
                imgReportes.setImage(new Image("/org/carlosescobar/images/Cancelar.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;

            case ACTUALIZAR:
                guardar();
                desactivarControles();
                cargarDatos();
                limpiarControles();

                btnEditar.setDisable(false);
                btnReportes.setDisable(false);
                imgAgregar.setImage(new Image("/org/carlosescobar/images/Agregar.png"));
                imgReportes.setImage(new Image("/org/carlosescobar/images/Eliminar.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                tipoDeOperaciones = operaciones.NINGUNO;

                break;
        }
    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();

                btnEditar.setDisable(false);
                btnReportes.setDisable(false);
                imgAgregar.setImage(new Image("/org/carlosescobar/images/Guardar.png"));
                imgEliminar.setImage(new Image("/org/carlosescobar/images/Cancelar.png"));
                break;

            default:
                if (tblEmpleado.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confrima la eliminacion del registro", "Eliminar Producto",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarTelefonoProveedor(?)}");
                            procedimiento.setInt(1, ((Empleados) tblEmpleado.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
                            procedimiento.execute();
                            ListaEmpleados.remove(tblEmpleado.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showConfirmDialog(null, "Debe de seleccionar un cliente para eliminar");

                }

        }
    }

    public void reportes() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();

                btnAgregar.setDisable(false);
                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);
                imgAgregar.setImage(new Image("/org/carlosescobar/images/Agregar.png"));
                imgEditar.setImage(new Image("/org/carlosescobar/images/editar2.png"));
                imgReportes.setImage(new Image("/org/carlosescobar/images/Reporte.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void guardar() {
        Empleados registro = new Empleados();
        registro.setCodigoEmpleado(Integer.parseInt(txtCodigoE.getText()));
        registro.setNombresEmpleados(txtNombreE.getText());
        registro.setApellidosEmpleados((txtApellidoE.getText()));
        registro.setSueldo(Double.parseDouble(txtSueldoE.getText()));
        registro.setDireccion((txtDireccionE.getText()));
        registro.setTurno((txtTurnoE.getText()));

        registro.setCodigoCargoEmpleado(((CargoEmpleado) cmbCodigoCargoE.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarEmpleado(?,?,?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getCodigoEmpleado());
            procedimiento.setString(2, registro.getNombresEmpleados());
            procedimiento.setString(3, registro.getApellidosEmpleados());
            procedimiento.setDouble(4, registro.getSueldo());
            procedimiento.setString(5, registro.getDireccion());
            procedimiento.setString(6, registro.getTurno());
            procedimiento.setInt(7, registro.getCodigoCargoEmpleado());

            procedimiento.execute();

            ListaEmpleados.add(registro);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblEmpleado.getSelectionModel().getSelectedItem() != null) {
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/carlosescobar/images/Actualizar.png"));
                    imgReportes.setImage(new Image("/org/carlosescobar/images/Cancelar.png"));
                    activarControles();

                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione el objeto");

                }
                break;

            case ACTUALIZAR:
                actualizar();
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/carlosescobar/images/editar2.png"));
                imgReportes.setImage(new Image("/org/carlosescobar/images/Reporte.png"));
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarEmpleado(?,?,?,?,?,?,?)}");
            Empleados registro = (Empleados)tblEmpleado.getSelectionModel().getSelectedItem();
            registro.setCodigoEmpleado(Integer.parseInt(txtCodigoE.getText()));
            registro.setNombresEmpleados(txtNombreE.getText());
            registro.setApellidosEmpleados((txtApellidoE.getText()));
            registro.setSueldo(Double.parseDouble(txtSueldoE.getText()));
            registro.setDireccion((txtDireccionE.getText()));
            registro.setTurno((txtTurnoE.getText()));

            registro.setCodigoCargoEmpleado(((CargoEmpleado) cmbCodigoCargoE.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());
            procedimiento.setInt(1, registro.getCodigoEmpleado());
            procedimiento.setString(2, registro.getNombresEmpleados());
            procedimiento.setString(3, registro.getApellidosEmpleados());
            procedimiento.setDouble(4, registro.getSueldo());
            procedimiento.setString(5, registro.getDireccion());
            procedimiento.setString(6, registro.getTurno());
            procedimiento.setInt(7, registro.getCodigoCargoEmpleado());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void desactivarControles() {
        txtCodigoE.setEditable(false);
        txtNombreE.setEditable(false);
        txtApellidoE.setEditable(false);
        txtSueldoE.setEditable(false);
        txtDireccionE.setEditable(false);
        txtTurnoE.setEditable(false);
        cmbCodigoCargoE.setDisable(true);

    }

    public void activarControles() {
        txtCodigoE.setEditable(true);
        txtNombreE.setEditable(true);
        txtApellidoE.setEditable(true);
        txtSueldoE.setEditable(true);
        txtDireccionE.setEditable(true);
        txtTurnoE.setEditable(true);
        cmbCodigoCargoE.setDisable(false);

    }

    public void limpiarControles() {
        txtCodigoE.clear();
        txtNombreE.clear();
        txtApellidoE.clear();
        txtSueldoE.clear();
        txtDireccionE.clear();
        txtTurnoE.clear();

        cmbCodigoCargoE.getSelectionModel().getSelectedItem();

    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
