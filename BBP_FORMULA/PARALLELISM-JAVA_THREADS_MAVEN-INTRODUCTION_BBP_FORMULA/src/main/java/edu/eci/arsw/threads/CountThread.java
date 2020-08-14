/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.threads;

import edu.eci.arsw.math.PiDigits;

import java.util.ArrayList;

/**
 *
 * @author hcadavid
 */
public class CountThread extends Thread {
    private int inicio,fin;
    private byte[] datos;
    public CountThread(int inicio, int fin){
        this.inicio=inicio;
        this.fin=fin;

    }

    public void run(){
        this.datos= PiDigits.getDigits(inicio,fin);
    }
}
