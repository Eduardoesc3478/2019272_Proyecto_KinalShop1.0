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
import org.carlosescobar.bean.TipoProducto;
import org.carlosescobar.db.Conexion;



import org.carlosescobar.system.Main;


public class MenuTipoDeProductoController implements  Initializable  {
   
    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private MenuTipoDeProductoController.operaciones tipoDeOperaciones = MenuTipoDeProductoController.operaciones.NINGUNO;
    private ObservableList<TipoProducto> ListaTipoProducto;

    @FXML
    private Button btnRegresar;

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
    private ImageView imgReporte;
    @FXML
    private TableView tblTipoProducto;
    @FXML
    private TableColumn colCodigoTipoP;
    @FXML
    private TableColumn colDescripcionP;
    @FXML
    private TextField txtCodigoP;
    @FXML
    private TextField txtDescripcionP;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }

    public void cargarDatos() {
        tblTipoProducto.setItems(getTipoProducto());
        colCodigoTipoP.setCellValueFactory(new PropertyValueFactory<TipoProducto, Integer>("codigoTipoProducto"));
        colDescripcionP.setCellValueFactory(new PropertyValueFactory<TipoProducto, String>("descripcion"));
    }

    public void seleccionarElemento() {
        txtCodigoP.setText(String.valueOf(((TipoProducto) tblTipoProducto.getSelectionModel().getSelectedItems()).getCodigoTipoDeProducto()));
        txtDescripcionP.setText((((TipoProducto) tblTipoProducto.getSelectionModel().getSelectedItem()).getDescripcion()));
    }
    
    public ObservableList<TipoProducto> getTipoProducto() {
        ArrayList<TipoProducto> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarTipoProducto()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new TipoProducto(resultado.getInt("codigoTipoProducto"),
                        resultado.getString("descripcion")));
                        
                        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ListaTipoProducto = FXCollections.observableList(lista);
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
                btnEliminar.setDisable(false);
                btnEditar.setDisable(false);
                btnReportes.setDisable(false);
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

               
                btnEditar.setDisable(false);
                btnReportes.setDisable(false);
                imgAgregar.setImage(new Image("/org/carlosescobar/images/Guardar.png"));
                imgEliminar.setImage(new Image("/org/carlosescobar/images/Cancelar.png"));
                break;

            default:
                if (tblTipoProducto.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confrima la eliminacion del registro", "Eliminar Tipo De Producto",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarTipoProducto(?)}");
                            procedimiento.setInt(1, ((TipoProducto) tblTipoProducto.getSelectionModel().getSelectedItem()).getCodigoTipoDeProducto());
                            procedimiento.execute();
                            ListaTipoProducto.remove(tblTipoProducto.getSelectionModel().getSelectedItem());
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
                if (tblTipoProducto.getSelectionModel().getSelectedItem() != null) {
                   
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/carlosescobar/images/Actualizar.png"));
                    imgReporte.setImage(new Image("/org/carlosescobar/images/Cancelar.png"));
                    activarControles();
                    txtCodigoP.setEditable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un cliente para editar");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarTipoProducto(?,?)}");
            TipoProducto registro = (TipoProducto) tblTipoProducto.getSelectionModel().getSelectedItem();
            registro.setDescripcion(txtDescripcionP.getText());
        
            procedimiento.setString(2, registro.getDescripcion());
            
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void guardar() {
        TipoProducto registro = new TipoProducto();
        registro.setCodigoTipoDeProducto(Integer.parseInt(txtCodigoP.getText()));
        registro.setDescripcion(txtDescripcionP.getText());
        

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call  sp_agregarTipoProducto(?,?)}");
            procedimiento.setInt(1, registro.getCodigoTipoDeProducto());
            procedimiento.setString(2, registro.getDescripcion());
            
            procedimiento.execute();
            ListaTipoProducto.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void desactivarControles() {
        txtCodigoP.setEditable(false);
        txtDescripcionP.setEditable(false);

    }

    public void activarControles() {
        txtCodigoP.setEditable(true);
        txtDescripcionP.setEditable(true);
        
    }

    public void limpiarControles() {
        txtCodigoP.clear();
        txtDescripcionP.clear();
        
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
