/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carlosescobar.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.carlosescobar.controllers.CargoController;
import org.carlosescobar.controllers.ComprasController;
import org.carlosescobar.controllers.DetalleCompraController;
import org.carlosescobar.controllers.DetalleFacturaController;
import org.carlosescobar.controllers.EmailProveedorController;
import org.carlosescobar.controllers.EmleadosController;
import org.carlosescobar.controllers.MenuClientesController;
import org.carlosescobar.controllers.MenuProgramadorController;
import org.carlosescobar.controllers.MenuPrincipalController;
import org.carlosescobar.controllers.MenuTipoDeProductoController;
import org.carlosescobar.controllers.ProductoController;
import org.carlosescobar.controllers.ProveedoresController;
import org.carlosescobar.controllers.TelefonoProveedorController;
import org.carlosescobar.controllers.FacturasController;

/**
 *
 * @author Usuario
 */
public class Main extends Application {

    private Stage escenarioPrincipal;
    private Scene escena;

    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("KINAL SHOP");
        escenarioPrincipal.getIcons().add(new Image("/org/carlosescobar/images/LogoParaSuperMercado.png"));
        menuPrincipalView();
        // Parent root = FXMLLoader.load(getClass().getResource("/org/carlosescobar/views/KinalShop.fxml"));
        //Scene escena =new Scene(root);
        //escenarioPrincipal.setScene(escena);
        escenarioPrincipal.show();
    }

    public Initializable CambiarEscena(String fxmlname, int width, int height) throws Exception {
        Initializable resultado;
        FXMLLoader loader = new FXMLLoader();
        InputStream file = Main.class.getResourceAsStream("/org/carlosescobar/views/" + fxmlname);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource("/org/carlosescobar/views/" + fxmlname));

        escena = new Scene((AnchorPane) loader.load(file), width, height);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();

        resultado = (Initializable) loader.getController();

        return resultado;
    }

    public void menuPrincipalView() {
        try {
            MenuPrincipalController menuPrincipal = (MenuPrincipalController) CambiarEscena("MenuPrincipalView.fxml", 601, 453);
            menuPrincipal.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());

        }

    }

    public void menuClientesView() {
        try {
            MenuClientesController menuClientesView = (MenuClientesController) CambiarEscena("MenuClientesView.fxml", 600, 334);
            menuClientesView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void menuProgramador() {
        try {
            MenuProgramadorController programadorController = (MenuProgramadorController) CambiarEscena("ProgramadorView.fxml", 600, 334);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void menuTipoProducto() {

        try {
            MenuTipoDeProductoController menuTipoDeProductoView = (MenuTipoDeProductoController) CambiarEscena("MenuTipoProductoView.fxml", 858, 485);
            menuTipoDeProductoView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void menuCompras() {
        try {
            ComprasController menuComprasView = (ComprasController) CambiarEscena("MenuComprasView.fxml", 667, 383);
            menuComprasView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void menuCargoEmpleado() {
        try {
            CargoController cargoView = (CargoController) CambiarEscena("MenuCargoEmpleadoView.fxml", 673, 385);
            cargoView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void menuProveedores() {
        try {
            ProveedoresController proveedoresView = (ProveedoresController) CambiarEscena("MenuProveedoresView.fxml", 986, 550);
            proveedoresView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    public void menuProducto() {
        try {
            ProductoController productosView = (ProductoController) CambiarEscena("MenuProductosView.fxml", 892, 518);
            productosView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void menuDetalleCompra() {
        try {
            DetalleCompraController detalleCompraView = (DetalleCompraController) CambiarEscena("MenuDetalleCompraView.fxml", 780, 440);
            detalleCompraView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void menuDetalleFactura() {
        try {
            DetalleFacturaController detalleFacturaView = (DetalleFacturaController) CambiarEscena("MenuDetalleFacturaView.fxml", 986, 550);
            detalleFacturaView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void menuEmailProveedor() {
        try {
            EmailProveedorController emailView = (EmailProveedorController) CambiarEscena("MenuEmailProveedorView.fxml", 745, 415);
            emailView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void menuTelefonoProveedor() {
        try {
            TelefonoProveedorController telefonoView = (TelefonoProveedorController) CambiarEscena("MenuTelefonoProveedorView.fxml", 767, 430);
            telefonoView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void menuEmpleados() {
        try {
            EmleadosController empleadoView = (EmleadosController) CambiarEscena("MenuEmpleadoView.fxml", 820, 464);
            empleadoView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    

    
    
    
    public void menuFactura() {
        try {
            FacturasController facturaView = (FacturasController) CambiarEscena("MenuFacturaView.fxml", 986, 550);
            facturaView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
