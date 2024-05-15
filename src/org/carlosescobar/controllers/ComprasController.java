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
import org.carlosescobar.bean.Compras;
import org.carlosescobar.db.Conexion;
import org.carlosescobar.system.Main;

/**
 *
 * @author Usuario
 */
public class ComprasController implements Initializable {
    private Main escenarioPrincipal;
    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Compras> ListaCompras;
    
    @FXML
    
    private Button btnRegresar;
    @FXML
    private TableView tblCompras;

    @FXML
    private TableColumn colNumeroDocumentoC;

    @FXML
    private TableColumn colFechaDocumentoC;

    @FXML
    private TableColumn colDescripcionC;

    @FXML
    private TableColumn colTotalC;

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
    private TextField txtNumeroDocumentoC;

    @FXML
    private TextField txtFechaDocumento;

    @FXML
    private TextField txtDescripcionC;

    @FXML
    private TextField txtTotalC;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public void cargarDatos() {
        tblCompras.setItems(getCompras());
        colNumeroDocumentoC.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("numeroDocumento"));
        colFechaDocumentoC.setCellValueFactory(new PropertyValueFactory<Compras, String>("fechaDocumento"));
        colDescripcionC.setCellValueFactory(new PropertyValueFactory<Compras, String>("descripcion"));
        colTotalC.setCellValueFactory(new PropertyValueFactory<Compras, Float>("totalDocumento"));

    }

    public void seleccionarElemento() {
        txtNumeroDocumentoC.setText(String.valueOf(((Compras) tblCompras.getSelectionModel().getSelectedItems()).getNumeroDocumento()));
        txtFechaDocumento.setText((((Compras) tblCompras.getSelectionModel().getSelectedItems()).getFechaDocumento()));
        txtDescripcionC.setText((((Compras) tblCompras.getSelectionModel().getSelectedItems()).getDescripcion()));
        txtTotalC.setText((((Compras) tblCompras.getSelectionModel().getSelectedItems()).getTotalDocumento()));
        
    }

    public ObservableList<Compras> getCompras() {
        ArrayList<Compras> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarCompras()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Compras(resultado.getInt("numeroDocumento"),
                        resultado.getString("fechaDocumento"),
                        resultado.getString("descripcion"),
                        resultado.getString("totalDocumento")
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
                if (tblCompras.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confrima la eliminacion del registro", "Eliminar Cliente",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarCompras(?)}");
                            procedimiento.setInt(1, ((Compras) tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento());
                            procedimiento.execute();
                            ListaCompras.remove(tblCompras.getSelectionModel().getSelectedItem());
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
                if (tblCompras.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReportes.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/carlosescobar/images/Actualizar.png"));
                    imgReportes.setImage(new Image("/org/carlosescobar/images/Cancelar.png"));
                    activarControles();
                    txtNumeroDocumentoC.setEditable(false);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarCompras(?, ?, ?, ?)}");
            Compras registro = (Compras) tblCompras.getSelectionModel().getSelectedItem();
            registro.setNumeroDocumento(Integer.parseInt(txtNumeroDocumentoC.getText()));
            registro.setFechaDocumento(txtFechaDocumento.getText());
            registro.setDescripcion(txtDescripcionC.getText());
            registro.setTotalDocumento((txtTotalC.getText()));
            
            procedimiento.setInt(1, registro.getNumeroDocumento());
            procedimiento.setString(2, registro.getFechaDocumento());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setString(4, registro.getTotalDocumento());
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void guardar() {
        Compras registro = new Compras();
        registro.setNumeroDocumento(Integer.parseInt(txtNumeroDocumentoC.getText()));
        registro.setFechaDocumento(txtFechaDocumento.getText());
        registro.setDescripcion(txtDescripcionC.getText());
        registro.setTotalDocumento((txtTotalC.getText()));
        

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call  sp_agregarCompras(?,?,?,?)}");
            procedimiento.setInt(1, registro.getNumeroDocumento());
            procedimiento.setString(2, registro.getFechaDocumento());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setString(4, registro.getTotalDocumento());
            
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
                btnEditar.setText("Actualizar");
                btnReportes.setText("Cancelar");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/carlosescobar/images/Actualizar.png"));
                imgReportes.setImage(new Image("/org/carlosescobar/images/Cancelar.png"));
                activarControles();
                txtNumeroDocumentoC.setEditable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
        }
    }

    public void desactivarControles() {
        txtNumeroDocumentoC.setEditable(false);
        txtFechaDocumento.setEditable(false);
        txtDescripcionC.setEditable(false);
        txtTotalC.setEditable(false);
    }

    public void activarControles() {
        txtNumeroDocumentoC.setEditable(true);
        txtFechaDocumento.setEditable(true);
        txtDescripcionC.setEditable(true);
        txtTotalC.setEditable(true);

    }

    public void limpiarControles() {
        txtNumeroDocumentoC.clear();
        txtFechaDocumento.clear();
        txtDescripcionC.clear();
        txtTotalC.clear();
        
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
