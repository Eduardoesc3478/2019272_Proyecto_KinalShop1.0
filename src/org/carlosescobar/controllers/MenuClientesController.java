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
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.carlosescobar.bean.Clientes;
import org.carlosescobar.db.Conexion;
import org.carlosescobar.report.GenerarReportes;
import org.carlosescobar.system.Main;





/**
 * FXML Controller class
 *
 * @author informatica
 */
public class MenuClientesController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Clientes> ListaClientes;

    @FXML
    private Button btnRegresar;
    @FXML
    private TableView tblClientes;
    @FXML
    private TextField txtCodigoC;
    @FXML
    private TextField txtNombreC;
    @FXML
    private TextField txtApellidoC;
    @FXML
    private TextField txtNitC;
    @FXML
    private TextField txtDireccionC;
    @FXML
    private TextField txtTelefonoC;
    @FXML
    private TextField txtCorreoC;
    @FXML
    private TableColumn colCodigoC;
    @FXML
    private TableColumn colNombreC;
    @FXML
    private TableColumn colApellidoC;
    @FXML
    private TableColumn colNitC;
    @FXML
    private TableColumn colDireccionC;
    @FXML
    private TableColumn colTelefonoC;
    @FXML
    private TableColumn colCorreoC;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnReportes;
    @FXML
    private ImageView imgAgregar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView imgEditar;
    @FXML
    private ImageView imgReportes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }

    public void cargarDatos() {
        tblClientes.setItems(getClientes());
        colCodigoC.setCellValueFactory(new PropertyValueFactory<Clientes, Integer>("clienteID"));
        colNombreC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nombreClientes"));
        colApellidoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("apellidoClientes"));
        colNitC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("nitClientes"));
        colDireccionC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("direccionClientes"));
        colTelefonoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("telefonoClientes"));
        colCorreoC.setCellValueFactory(new PropertyValueFactory<Clientes, String>("correoClientes"));
    }

    public void seleccionarElemento() {
        txtCodigoC.setText(String.valueOf(((Clientes) tblClientes.getSelectionModel().getSelectedItems()).getClienteID()));
        txtNombreC.setText((((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getNombreClientes()));
        txtApellidoC.setText((((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getNombreClientes()));
        txtNitC.setText((((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getNombreClientes()));
        txtDireccionC.setText((((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getNombreClientes()));
        txtTelefonoC.setText((((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getNombreClientes()));
        txtCorreoC.setText((((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getNombreClientes()));
        
    }

    public ObservableList<Clientes> getClientes() {
        ArrayList<Clientes> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarClientes()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Clientes(resultado.getInt("clienteID"),
                        resultado.getString("nombreClientes"),
                        resultado.getString("apellidoClientes"),
                        resultado.getString("nitClientes"),
                        resultado.getString("direccionClientes"),
                        resultado.getString("telefonoClientes"),
                        resultado.getString("correoClientes")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ListaClientes = FXCollections.observableList(lista);
    }

    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();

                btnAgregar.setText("Guargar");
                btnReportes.setText("Cancelar");
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
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);
                imgAgregar.setImage(new Image("/org/carlosescobar/images/Agregar.png"));
                imgEliminar.setImage(new Image("/org/carlosescobar/images/Eliminar.png"));
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

                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReportes.setDisable(false);
                imgAgregar.setImage(new Image("/org/carlosescobar/images/Guardar.png"));
                imgEliminar.setImage(new Image("/org/carlosescobar/images/Cancelar.png"));
                break;

            default:
                if (tblClientes.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confrima la eliminacion del registro", "Eliminar Cliente",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarCliente(?)}");
                            procedimiento.setInt(1, ((Clientes) tblClientes.getSelectionModel().getSelectedItem()).getClienteID());
                            procedimiento.execute();
                            ListaClientes.remove(tblClientes.getSelectionModel().getSelectedItem());
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

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblClientes.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/carlosescobar/images/Actualizar.png"));
                    imgReportes.setImage(new Image("/org/carlosescobar/images/Cancelar.png"));
                    activarControles();
                    txtCodigoC.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un cliente para editar");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Actualizar");
                btnReportes.setText("Cancelar");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/carlosescobar/images/editar2.png"));
                imgReportes.setImage(new Image("/org/carlosescobar/images/Reporte.png"));
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;

        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarClientes(?, ?, ?, ?, ?, ?, ?)}");
            Clientes registro = (Clientes) tblClientes.getSelectionModel().getSelectedItem();
            registro.setNitClientes(txtNitC.getText());
            registro.setNombreClientes(txtNombreC.getText());
            registro.setApellidoClientes(txtApellidoC.getText());
            registro.setTelefonoClientes(txtTelefonoC.getText());
            registro.setDireccionClientes(txtDireccionC.getText());
            registro.setCorreoClientes(txtCorreoC.getText());
            procedimiento.setInt(1, registro.getClienteID());
            procedimiento.setString(2, registro.getNombreClientes());
            procedimiento.setString(3, registro.getApellidoClientes());
            procedimiento.setString(4, registro.getNitClientes());
            procedimiento.setString(5, registro.getDireccionClientes());
            procedimiento.setString(6, registro.getTelefonoClientes());
            procedimiento.setString(7, registro.getCorreoClientes());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void reportes() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                imprimirReporte();
                break;
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

    
    public void imprimirReporte(){
        Map parametros = new HashMap();
        parametros.put("clienteID",null);
        GenerarReportes.mostrarReportes("ReporteClientes.jasper","Reporte de clientes", parametros);
    }
    public void guardar() {
        Clientes registro = new Clientes();
        registro.setClienteID(Integer.parseInt(txtCodigoC.getText()));
        registro.setNombreClientes(txtNombreC.getText());
        registro.setApellidoClientes(txtApellidoC.getText());
        registro.setNitClientes(txtNitC.getText());
        registro.setTelefonoClientes(txtTelefonoC.getText());
        registro.setDireccionClientes(txtDireccionC.getText());
        registro.setCorreoClientes(txtCorreoC.getText());

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call  sp_agregarClientes(?,?,?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getClienteID());
            procedimiento.setString(2, registro.getNombreClientes());
            procedimiento.setString(3, registro.getApellidoClientes());
            procedimiento.setString(4, registro.getNitClientes());
            procedimiento.setString(5, registro.getDireccionClientes());
            procedimiento.setString(6, registro.getTelefonoClientes());
            procedimiento.setString(7, registro.getCorreoClientes());
            procedimiento.execute();
            ListaClientes.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void desactivarControles() {
        txtCodigoC.setEditable(false);
        txtNombreC.setEditable(false);
        txtApellidoC.setEditable(false);
        txtNitC.setEditable(false);
        txtTelefonoC.setEditable(false);
        txtDireccionC.setEditable(false);
        txtCorreoC.setEditable(false);
    }

    public void activarControles() {
        txtCodigoC.setEditable(true);
        txtNombreC.setEditable(true);
        txtApellidoC.setEditable(true);
        txtNitC.setEditable(true);
        txtTelefonoC.setEditable(true);
        txtDireccionC.setEditable(true);
        txtCorreoC.setEditable(true);
    }

    public void limpiarControles() {
        txtCodigoC.clear();
        txtNombreC.clear();
        txtApellidoC.clear();
        txtNitC.clear();
        txtTelefonoC.clear();
        txtDireccionC.clear();
        txtCorreoC.clear();
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }

    }

}
