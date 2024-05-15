/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carlosescobar.bean;

/**
 *
 * @author informatica
 */
public class Clientes {
    private int clienteID;
    private String nitClientes;
    private String nombreClientes;
    private String apellidoClientes;    
    private String telefonoClientes;
    private String direccionClientes;
    private String correoClientes;

    public Clientes() {
    }

    public Clientes(int clienteID, String nombreClientes, String apellidoClientes, String nitClientes, String telefonoClientes, String direccionClientes, String correoClientes) {
        this.clienteID = clienteID;
        this.nombreClientes = nombreClientes;
        this.apellidoClientes = apellidoClientes;
        this.nitClientes = nitClientes;
        this.telefonoClientes = telefonoClientes;
        this.direccionClientes = direccionClientes;
        this.correoClientes = correoClientes;
    }

    public int getClienteID() {
        return clienteID;
    }

    public void setClienteID(int clienteID) {
        this.clienteID = clienteID;
    }

    public String getNombreClientes() {
        return nombreClientes;
    }

    public void setNombreClientes(String nombreClientes) {
        this.nombreClientes = nombreClientes;
    }

    public String getApellidoClientes() {
        return apellidoClientes;
    }

    public void setApellidoClientes(String apellidoClientes) {
        this.apellidoClientes = apellidoClientes;
    }

    public String getNitClientes() {
        return nitClientes;
    }

    public void setNitClientes(String nitClientes) {
        this.nitClientes = nitClientes;
    }

    public String getTelefonoClientes() {
        return telefonoClientes;
    }

    public void setTelefonoClientes(String telefonoClientes) {
        this.telefonoClientes = telefonoClientes;
    }

    public String getDireccionClientes() {
        return direccionClientes;
    }

    public void setDireccionClientes(String direccionClientes) {
        this.direccionClientes = direccionClientes;
    }

    public String getCorreoClientes() {
        return correoClientes;
    }

    public void setCorreoClientes(String correoClientes) {
        this.correoClientes = correoClientes;
    }
}
