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
import javafx.scene.image.ImageView;
import org.carlosescobar.bean.DetalleCompra;
import org.carlosescobar.db.Conexion;
import org.carlosescobar.system.Main;

/**
 *
 * @author Usuario
 */
public class DetalleCompraController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<DetalleCompra> listaDetalleCompra;
    @FXML
    private Button btnAgregar;

    @FXML
    private ImageView imgAgregar;
    @FXML
    private Button btnEditar;
    @FXML
    private ImageView imgEditar;
    @FXML
    private Button btnReportes;
    @FXML
    private ImageView imgReportes;
    @FXML
    private Button btnEliminar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private TableView tblDetalleCompra;
    @FXML
    private TableColumn colCodigoDetalleC;
    @FXML
    private TableColumn colCostoU;
    @FXML
    private TableColumn colCantidadD;
    @FXML
    private TableColumn colCodigoP;
    @FXML
    private TableColumn colNumeroD;
    @FXML
    private TextField txtCodigoDetalleC;
    @FXML
    private TextField txtCosto;
    @FXML
    private TextField txtCantidad;
    @FXML
    private ComboBox cmbNumeroD;
    @FXML
    private ComboBox cmbCodigoP;
    @FXML
    private Button btnRegresar;

    public void initialize(URL url, ResourceBundle rb) {
       
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
