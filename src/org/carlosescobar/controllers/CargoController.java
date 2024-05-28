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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.carlosescobar.bean.CargoEmpleado;
import org.carlosescobar.db.Conexion;
import org.carlosescobar.system.Main;

/**
 *
 * @author Usuario
 */
public class CargoController implements Initializable {
    
    private Main escenarioPrincipal;
    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<CargoEmpleado> ListaCompras;
    
    @FXML
    private Button btnRegresar;
    @FXML
    private TableView tblCargoE;

    @FXML
    private TableColumn colCodigoC;

    @FXML
    private TableColumn colNombreCargoE;

    @FXML
    private TableColumn colDescripcionC;

    @FXML
    private TextField txtCodigoCardoE;

    @FXML
    private TextField txtNombreC;

    @FXML
    private TextField txtDescripcionC;

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
    private Button btnReporte;

    @FXML
    private ImageView imgReporte;
    
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public void cargarDatos() {
        tblCargoE.setItems(getCargoEmpleado());
        colCodigoC.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, Integer>("codigoCargoEmpleado"));
        colNombreCargoE.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, String>("nombreCargo"));
        colDescripcionC.setCellValueFactory(new PropertyValueFactory<CargoEmpleado, String>("descripcionCargo"));
 

    }

    public void seleccionarElemento() {
        txtCodigoCardoE.setText(String.valueOf(((CargoEmpleado) tblCargoE.getSelectionModel().getSelectedItems()).getCodigoCargoEmpleado()));
        txtNombreC.setText((((CargoEmpleado) tblCargoE.getSelectionModel().getSelectedItems()).getNombreCargo()));
        txtDescripcionC.setText((((CargoEmpleado) tblCargoE.getSelectionModel().getSelectedItems()).getDescripcionCargo()));
       
        
    }

    public ObservableList<CargoEmpleado> getCargoEmpleado() {
        ArrayList<CargoEmpleado> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarCargos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new CargoEmpleado(resultado.getInt("codigoCargoEmpleado"),
                        resultado.getString("nombreCargo"),
                        resultado.getString("descripcionCargo")
                        ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ListaCompras = FXCollections.observableList(lista);
    }

    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();

                
                btnEditar.setDisable(true);
                btnEliminar.setDisable(true);
                imgAgregar.setImage(new Image("/org/carlosescobar/images/Guardar.png"));
                imgReporte.setImage(new Image("/org/carlosescobar/images/Cancelar.png"));
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
                imgReporte.setImage(new Image("/org/carlosescobar/images/Eliminar.png"));
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
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/carlosescobar/images/Guardar.png"));
                imgEliminar.setImage(new Image("/org/carlosescobar/images/Cancelar.png"));
                break;

            default:
                if (tblCargoE.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confrima la eliminacion del registro", "Eliminar Cliente",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarCargo(?)}");
                            procedimiento.setInt(1, ((CargoEmpleado) tblCargoE.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado());
                            procedimiento.execute();
                            ListaCompras.remove(tblCargoE.getSelectionModel().getSelectedItem());
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
                if (tblCargoE.getSelectionModel().getSelectedItem() != null) {
                    
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/carlosescobar/images/Actualizar.png"));
                    imgReporte.setImage(new Image("/org/carlosescobar/images/Cancelar.png"));
                    activarControles();
                    txtCodigoCardoE.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un cliente para editar");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Actualizar");

                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/carlosescobar/images/editar2.png"));
                imgReporte.setImage(new Image("/org/carlosescobar/images/Reporte.png"));
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;

        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarCargo(?, ?, ?)}");
            CargoEmpleado registro = (CargoEmpleado) tblCargoE.getSelectionModel().getSelectedItem();
            registro.setCodigoCargoEmpleado(Integer.parseInt(txtCodigoCardoE.getText()));
            registro.setNombreCargo(txtNombreC.getText());
            registro.setDescripcionCargo(txtDescripcionC.getText());

            
            procedimiento.setInt(1, registro.getCodigoCargoEmpleado());
            procedimiento.setString(2, registro.getNombreCargo());
            procedimiento.setString(3, registro.getDescripcionCargo());
           
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void guardar() {
        CargoEmpleado registro = new CargoEmpleado();
        registro.setCodigoCargoEmpleado(Integer.parseInt(txtCodigoCardoE.getText()));
        registro.setNombreCargo(txtNombreC.getText());
        registro.setDescripcionCargo(txtDescripcionC.getText());
        
        

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call  sp_agregarCargo(?,?,?)}");
            procedimiento.setInt(1, registro.getCodigoCargoEmpleado());
            procedimiento.setString(2, registro.getNombreCargo());
            procedimiento.setString(3, registro.getDescripcionCargo());
           
            
            procedimiento.execute();
            ListaCompras.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reporte() {

        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/carlosescobar/images/Actualizar.png"));
                imgReporte.setImage(new Image("/org/carlosescobar/images/Cancelar.png"));
                activarControles();
                txtCodigoCardoE.setEditable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
        }
    }

    public void desactivarControles() {
        txtCodigoCardoE.setEditable(false);
        txtNombreC.setEditable(false);
        txtDescripcionC.setEditable(false);
        
    }

    public void activarControles() {
        txtCodigoCardoE.setEditable(true);
        txtNombreC.setEditable(true);
        txtDescripcionC.setEditable(true);
        

    }

    public void limpiarControles() {
        txtCodigoCardoE.clear();
        txtNombreC.clear();
        txtDescripcionC.clear();
        
        
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