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
public class TipoProducto {

    private int codigoTipoProducto;
    private String descripcion;

    public TipoProducto() {
    }

    public TipoProducto(int codigoTipoDeProducto, String descripcion) {
        this.codigoTipoProducto = codigoTipoDeProducto;
        this.descripcion = descripcion;
    }

    public int getCodigoTipoDeProducto() {
        return codigoTipoProducto;
    }

    public void setCodigoTipoDeProducto(int codigoTipoDeProducto) {
        this.codigoTipoProducto = codigoTipoDeProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return getCodigoTipoDeProducto() + "  ";
    }

    
}


