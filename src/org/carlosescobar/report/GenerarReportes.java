/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carlosescobar.report;

import java.io.InputStream;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.carlosescobar.db.Conexion;

/**
 *
 * @author informatica
 */
public class GenerarReportes {
public  static void mostrarReportes(String nombreReporte, String titulo, Map parametros){
    InputStream reporte = GenerarReportes.class.getResourceAsStream(nombreReporte);
    
try{
    JasperReport ReporteClientes =(JasperReport) JRLoader.loadObject(reporte);
    JasperPrint reporteImpreso = JasperFillManager.fillReport(ReporteClientes, parametros, Conexion.getInstance().getConexion());
    JasperViewer visor = new JasperViewer(reporteImpreso, false);
    visor.setTitle(titulo);
    visor.setVisible(true);
}catch(Exception e){
    e.printStackTrace();
}    
}    
}
