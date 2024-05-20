/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carlosescobar.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.carlosescobar.system.Main;


/**
 *
 * @author Usuario
 */
public class MenuProgramadorController implements Initializable{
    private Main  escenarioPrincipal;
    
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
}
