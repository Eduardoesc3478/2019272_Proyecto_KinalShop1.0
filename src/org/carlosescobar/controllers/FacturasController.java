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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.carlosescobar.bean.Clientes;
import org.carlosescobar.bean.Empleados;
import org.carlosescobar.bean.Factura;
import org.carlosescobar.db.Conexion;
import org.carlosescobar.report.GenerarReportes;
import org.carlosescobar.system.Main;

/**
 *
 * @author Usuario
 */
public class FacturasController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Factura> ListaFacturas;
    private ObservableList<Empleados> ListaEmpleados;
    private ObservableList<Clientes> ListaClientes;
    @FXML
    private Button btnRegresar;
    @FXML
    private TableView tblFactura;

    @FXML
    private TableColumn colNumeroF;

    @FXML
    private TableColumn colEstadoF;

    @FXML
    private TableColumn colTotalF;

    @FXML
    private TableColumn colFechaF;

    @FXML
    private TableColumn colClienteID;

    @FXML
    private TableColumn colCodigoE;

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
    private TextField txtNumeroF;

    @FXML
    private TextField txtEstado;

    @FXML
    private TextField txtTotalF;

    @FXML
    private TextField txtFechaF;

    @FXML
    private ComboBox cmbClienteID;

    @FXML
    private ComboBox cmbCodigoE;

    

    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        cmbClienteID.setItems(getClientes());
        cmbCodigoE.setItems(getEmpleados());
    }

    public void cargarDatos() {
        tblFactura.setItems(getFacturas());
        colNumeroF.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("numeroFactura"));
        colEstadoF.setCellValueFactory(new PropertyValueFactory<Factura, String>("estado"));
        colTotalF.setCellValueFactory(new PropertyValueFactory<Factura, Double>("totalFactura"));
        colFechaF.setCellValueFactory(new PropertyValueFactory<Factura, String>("fechaFactura"));
        colClienteID.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("clienteID"));
        colCodigoE.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("codigoEmpleado"));
    }
    
    

    public ObservableList<Factura> getFacturas() {
        ArrayList<Factura> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarFacturas()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Factura(resultado.getInt("numeroFactura"),
                        resultado.getString("estado"),
                        resultado.getDouble("totalFactura"),
                        resultado.getString("fechaFactura"),
                        resultado.getInt("clienteID"),
                        resultado.getInt("codigoEmpleado")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ListaFacturas = FXCollections.observableList(lista);
    }
    public ObservableList<Empleados> getEmpleados() {
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
public ObservableList<Clientes> getClientes() {
        ArrayList<Clientes> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarClientes()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Clientes(resultado.getInt("clienteID"),
                        resultado.getString("nitClientes"),
                        resultado.getString("nombreClientes"),
                        resultado.getString("apellidoClientes"),
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
                btnEliminar.setDisable(false);
                imgAgregar.setImage(new Image("/org/carlosescobar/images/Agregar.png"));
                imgReportes.setImage(new Image("/org/carlosescobar/images/Reporte.png"));
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
                if (tblFactura.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confrima la eliminacion del registro", "Eliminar Producto",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarFactura(?)}");
                            procedimiento.setInt(1, ((Factura) tblFactura.getSelectionModel().getSelectedItem()).getNumeroFactura());
                            procedimiento.execute();
                            ListaFacturas.remove(tblFactura.getSelectionModel().getSelectedItem());
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
    
    public void guardar() {
        Factura registro = new Factura();
        registro.setNumeroFactura(Integer.parseInt(txtNumeroF.getText()));
        registro.setEstado(txtEstado.getText());
        registro.setTotalFactura(Double.parseDouble(txtTotalF.getText()));
        registro.setFechaFactura((txtFechaF.getText()));
        

        registro.setClienteID(((Clientes) cmbClienteID.getSelectionModel().getSelectedItem()).getClienteID());
        registro.setCodigoEmpleado(((Empleados)cmbCodigoE.getSelectionModel().getSelectedItem()).getCodigoEmpleado());

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarFactura(?,?,?,?,?,?)}");
            procedimiento.setInt(1, registro.getNumeroFactura());
            procedimiento.setString(2, registro.getEstado());
            procedimiento.setDouble(3, registro.getTotalFactura());
            procedimiento.setString(4, registro.getFechaFactura());
            procedimiento.setInt(5, registro.getClienteID());
            procedimiento.setInt(6, registro.getCodigoEmpleado());

            procedimiento.execute();

            ListaFacturas.add(registro);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblFactura.getSelectionModel().getSelectedItem() != null) {
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarFactura(?,?,?,?,?,?)}");
            Factura registro = (Factura)tblFactura.getSelectionModel().getSelectedItem();
            registro.setNumeroFactura(Integer.parseInt(txtNumeroF.getText()));
        registro.setEstado(txtEstado.getText());
        registro.setTotalFactura(Double.parseDouble(txtTotalF.getText()));
        registro.setFechaFactura((txtFechaF.getText()));
        

        registro.setClienteID(((Clientes) cmbClienteID.getSelectionModel().getSelectedItem()).getClienteID());
        registro.setCodigoEmpleado(((Empleados)cmbCodigoE.getSelectionModel().getSelectedItem()).getCodigoEmpleado());

            
             procedimiento.setInt(1, registro.getNumeroFactura());
            procedimiento.setString(2, registro.getEstado());
            procedimiento.setDouble(3, registro.getTotalFactura());
            procedimiento.setString(4, registro.getFechaFactura());
            procedimiento.setInt(5, registro.getClienteID());
            procedimiento.setInt(6, registro.getCodigoEmpleado());
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
        int numFac = Integer.valueOf(((Factura)tblFactura.getSelectionModel().getSelectedItem()).getNumeroFactura());
        parametros.put(numFac,numFac);
        GenerarReportes.mostrarReportes("ReporteFactura.jasper","Reporte de clientes", parametros);
    }
    public void desactivarControles() {
        txtNumeroF.setEditable(false);
        txtEstado.setEditable(false);
        txtTotalF.setEditable(false);
        txtFechaF.setEditable(false);

        cmbClienteID.setDisable(true);
        cmbCodigoE.setDisable(true);

    }

    public void activarControles() {
        txtNumeroF.setEditable(true);
        txtEstado.setEditable(true);
        txtTotalF.setEditable(true);
        txtFechaF.setEditable(true);

        cmbClienteID.setDisable(false);
        cmbCodigoE.setDisable(false);

    }

    public void limpiarControles() {
        txtNumeroF.clear();
        txtEstado.clear();
        txtTotalF.clear();
        txtFechaF.clear();

        cmbClienteID.getSelectionModel().getSelectedItem();
        cmbCodigoE.getSelectionModel().getSelectedItem();
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
