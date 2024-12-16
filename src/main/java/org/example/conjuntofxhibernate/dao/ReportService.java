package org.example.conjuntofxhibernate.dao;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.example.conjuntofxhibernate.models.Copia;
import org.example.conjuntofxhibernate.models.Usuario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import static org.example.conjuntofxhibernate.models.Sesion.copiaSeleccionada;
import static org.example.conjuntofxhibernate.models.Sesion.usuarioIniciado;

public class ReportService {

    private static Connection con = null;

    static{
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BD","root","my-secret-pw");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void mostrarInformePeliculas(){
        try {
            JasperPrint jp = JasperFillManager.fillReport("InformePeliculas.jasper",null, con);
            JasperExportManager.exportReportToPdfFile(jp, "InformePeliculas.pdf");
            JasperViewer.viewReport(jp, false);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }
    public static void generarInformeInfoPeliculas() {
        try {
            JasperPrint jp = JasperFillManager.fillReport("InformePeliculas.jasper",null, con);
            JasperExportManager.exportReportToPdfFile(jp, "InformePeliculas.pdf");
        } catch (JRException e) {
            throw new RuntimeException("Error al generar el informe: " + e.getMessage(), e);
        }
    }

    /*---------------------------------------------------------------------------------------------------------*/


    public static void generarInformeCopiasMal() {
        try {
            JasperPrint jp = JasperFillManager.fillReport("InformeMalEstado.jasper",null, con);
            JasperExportManager.exportReportToPdfFile(jp, "InformeMalEstado.pdf");
        } catch (JRException e) {
            throw new RuntimeException("Error al generar el informe: " + e.getMessage(), e);
        }
    }
    public static void mostrarInformeCopiasMal(){
        try {
            JasperPrint jp = JasperFillManager.fillReport("InformeMalEstado.jasper",null, con);
            JasperExportManager.exportReportToPdfFile(jp, "InformeMalEstado.pdf");
            JasperViewer.viewReport(jp, false);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    /*-------------------------------------------------------------------------------------------------------------*/

    public static void generarInformeMasCopias() {
        try {
            JasperPrint jp = JasperFillManager.fillReport("InformeMasCopias.jasper",null, con);
            JasperExportManager.exportReportToPdfFile(jp, "InformeMasCopias.pdf");
        } catch (JRException e) {
            throw new RuntimeException("Error al generar el informe: " + e.getMessage(), e);
        }
    }

    public static void mostrarInformeMasCopias(){
        try {
            JasperPrint jp = JasperFillManager.fillReport("InformeMasCopias.jasper",null, con);
            JasperExportManager.exportReportToPdfFile(jp, "InformeMasCopias.pdf");
            JasperViewer.viewReport(jp, false);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    /*-----------------------------------------------------------------------------*/
    public static void generarInformeDetalles(){
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("copiaSeleccionada", copiaSeleccionada.getId());
        try {
            JasperPrint jp = JasperFillManager.fillReport("InformeDetalles.jasper",parametros, con);
            JasperExportManager.exportReportToPdfFile(jp, "InformeDetalles.pdf");
            JasperViewer.viewReport(jp, false);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }
    public static void mostrarInformeDetalles(){
        HashMap<String, Object> parametros = new HashMap<>();
        parametros.put("copiaSeleccionada", copiaSeleccionada.getId());
        try {
            JasperPrint jp = JasperFillManager.fillReport("InformeDetalles.jasper",null, con);
            JasperExportManager.exportReportToPdfFile(jp, "InformeDetalles.pdf");
            JasperViewer.viewReport(jp, false);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }





}
