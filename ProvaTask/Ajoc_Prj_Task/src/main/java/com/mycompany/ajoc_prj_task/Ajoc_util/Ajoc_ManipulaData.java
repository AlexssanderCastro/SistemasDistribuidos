/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ajoc_prj_task.Ajoc_util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Alexssander
 */
public class Ajoc_ManipulaData {

    
    private static Ajoc_ManipulaData instancia;

    
    private Ajoc_ManipulaData() {}

    
    public static Ajoc_ManipulaData getInstancia() {
        if (instancia == null) {
            instancia = new Ajoc_ManipulaData();
        }
        return instancia;
    }

    
    public Date string2Date(String data) {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return Date.valueOf(LocalDate.parse(data, formato));
    }

    
    public String date2String(String data) {
        try {
            java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(data);
            data = new SimpleDateFormat("dd/MM/yyyy").format(date);
        } catch (ParseException e) {
            System.out.println(e);
        }
        return data;
    }
}
