/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carlosescobar.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.carlosescobar.system.Main;

/**
 *
 * @author informatica
 */
public class MenuPrincipalController implements Initializable {

    private Main escenarioPrincipal;
    @FXML
    private MenuItem btnClientes;

    @FXML
    private MenuItem btnProgramador;
    @FXML
    private MenuItem btnTipoDeProducto;
    @FXML
    private MenuItem btnCompras;
    @FXML
    private MenuItem btnCargoEmpleado;
    @FXML
    private MenuItem btnProveedores;

    @FXML
    private MenuItem btnProductos;
    @FXML
    private MenuItem btnDetalleCompra;
    @FXML
    private MenuItem btnDetalleFactura;
    @FXML
    private MenuItem btnEmailProveedor;
    @FXML
    private MenuItem btnTelefonoProveedor;
    @FXML
    private MenuItem btnEmpleados;
    @FXML
    private MenuItem btnFacturas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnClientes) {
            escenarioPrincipal.menuClientesView();
        } else if (event.getSource() == btnProgramador) {
            escenarioPrincipal.menuProgramador();
        } else if (event.getSource() == btnTipoDeProducto) {
            escenarioPrincipal.menuTipoProducto();
        } else if (event.getSource() == btnCompras) {
            escenarioPrincipal.menuCompras();
        } else if (event.getSource() == btnCargoEmpleado) {
            escenarioPrincipal.menuCargoEmpleado();
        } else if (event.getSource() == btnProveedores) {
            escenarioPrincipal.menuProveedores();
        } else if (event.getSource() == btnProductos) {
            escenarioPrincipal.menuProducto();
        } else if (event.getSource() == btnDetalleCompra) {
            escenarioPrincipal.menuDetalleCompra();
        } else if (event.getSource() == btnDetalleFactura) {
            escenarioPrincipal.menuDetalleFactura();
        } else if (event.getSource() == btnEmailProveedor) {
            escenarioPrincipal.menuEmailProveedor();
        } else if (event.getSource() == btnTelefonoProveedor) {
            escenarioPrincipal.menuTelefonoProveedor();
        } else if (event.getSource() == btnEmpleados) {
            escenarioPrincipal.menuEmpleados();
        } else if (event.getSource() == btnFacturas) {
            escenarioPrincipal.menuFactura();
        }
    }
}
