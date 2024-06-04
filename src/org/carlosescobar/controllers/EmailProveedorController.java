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
import org.carlosescobar.bean.EmailProveedor;
import org.carlosescobar.bean.Proveedores;
import org.carlosescobar.db.Conexion;
import org.carlosescobar.system.Main;

/**
 *
 * @author Usuario
 */
public class EmailProveedorController implements Initializable {

    private Main escenarioPrincipal;
    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<EmailProveedor> ListaEmail;
    private ObservableList<Proveedores> listaProveedor;
    @FXML
    private Button btnRegresar;
    @FXML
    private TableView tblEmailP;

    @FXML
    private TableColumn colCodigoEmailP;

    @FXML
    private TableColumn colEmailP;

    @FXML
    private TableColumn colDescripcion;

    @FXML
    private TableColumn colCodigoP;

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
    private TextField txtEmail;

    @FXML
    private TextField txtDescripcionE;
    @FXML
    private ComboBox cmbCodigoP;

    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        cmbCodigoP.setItems(getEmpleados());
    }
    
    public void cargarDatos() {
        tblEmailP.setItems(getEmail());
        colCodigoEmailP.setCellValueFactory(new PropertyValueFactory<EmailProveedor, Integer>("codigoEmailProveedor"));
        colEmailP.setCellValueFactory(new PropertyValueFactory<EmailProveedor, String>("emailProveedor"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<EmailProveedor, String>("descripcion"));
        colCodigoP.setCellValueFactory(new PropertyValueFactory<EmailProveedor, Integer>("codigoProveedor"));
    }
    
     public ObservableList<EmailProveedor> getEmail() {
        ArrayList<EmailProveedor> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarEmailProveedor()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new EmailProveedor(resultado.getInt("codigoEmailProveedor"),
                        resultado.getString("emailProveedor"),
                        resultado.getString("descripcion"),
                        resultado.getInt("codigoProveedor")
                        ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ListaEmail = FXCollections.observableList(lista);
    }
     
     public ObservableList<Proveedores> getEmpleados() {
        ArrayList<Proveedores> Lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{ call sp_listarProveedores()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                Lista.add(new Proveedores(resultado.getInt("codigoProveedor"),
                        resultado.getString("nitProveedor"),
                        resultado.getString("nombresProveedor"),
                        resultado.getString("apellidosProveedor"),
                        resultado.getString("razonSocial"),
                        resultado.getString("contactoPrincipal"),
                        resultado.getString("paginaWeb"),
                        resultado.getString("direccionProveedor")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProveedor = FXCollections.observableList(Lista);
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
     
      public void guardar() {
        EmailProveedor registro = new EmailProveedor();
        registro.setCodigoEmailProveedor(Integer.parseInt(txtCodigoE.getText()));
        registro.setEmailProveedor(txtEmail.getText());
        registro.setDescripcion((txtDescripcionE.getText()));
        registro.setCodigoProveedor(((Proveedores) cmbCodigoP.getSelectionModel().getSelectedItem()).getCodigoProveedor());

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarEmailProveedor(?,?,?,?)}");
            procedimiento.setInt(1, registro.getCodigoEmailProveedor());
            procedimiento.setString(2, registro.getEmailProveedor());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setInt(4, registro.getCodigoProveedor());


            procedimiento.execute();

            ListaEmail.add(registro);

        } catch (Exception e) {
            e.printStackTrace();
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
                if (tblEmailP.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confrima la eliminacion del registro", "Eliminar Producto",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarEmailProveedor(?)}");
                            procedimiento.setInt(1, ((EmailProveedor) tblEmailP.getSelectionModel().getSelectedItem()).getCodigoEmailProveedor());
                            procedimiento.execute();
                            ListaEmail.remove(tblEmailP.getSelectionModel().getSelectedItem());
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
                if (tblEmailP.getSelectionModel().getSelectedItem() != null) {
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarEmailProveedor(?,?,?,?)}");
            
            EmailProveedor registro = (EmailProveedor )tblEmailP.getSelectionModel().getSelectedItem();
        registro.setCodigoEmailProveedor(Integer.parseInt(txtCodigoE.getText()));
        registro.setEmailProveedor(txtEmail.getText());
        registro.setDescripcion((txtDescripcionE.getText()));
        registro.setCodigoProveedor(((Proveedores) cmbCodigoP.getSelectionModel().getSelectedItem()).getCodigoProveedor());


            procedimiento.setInt(1, registro.getCodigoEmailProveedor());
            procedimiento.setString(2, registro.getEmailProveedor());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setInt(4, registro.getCodigoProveedor());
           
            procedimiento.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
     public void limpiarControles(){
        txtCodigoE.clear();
        txtEmail.clear();
        txtDescripcionE.clear();
        cmbCodigoP.getSelectionModel().getSelectedItem();
        
    }
    public void activarControles(){
        txtCodigoE.setEditable(true);
        txtEmail.setEditable(true);
        txtDescripcionE.setEditable(true);
        cmbCodigoP.setDisable(false);
        
    }
    
    public void desactivarControles(){
        txtCodigoE.setEditable(false);
        txtEmail.setEditable(false);
        txtDescripcionE.setEditable(false);
        cmbCodigoP.setDisable(true);
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

