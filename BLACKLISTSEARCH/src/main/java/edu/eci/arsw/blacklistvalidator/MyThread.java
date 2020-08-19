package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import jdk.nashorn.internal.ir.BlockStatement;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyThread extends  Thread{

    private String ip;
    private int inicio, fin, servers, checkedListsCount, BLACK_LIST_ALARM_COUNT;
    private HostBlackListsValidator valida;
    private List<Integer> blackListOcurrences;
    private HostBlacklistsDataSourceFacade skds;

    public MyThread(String ip, int inicio, int fin, HostBlackListsValidator instances){
        this.ip = ip;
        this.inicio = inicio;
        this.fin = fin;
        this.valida = instances;

        BLACK_LIST_ALARM_COUNT = 5;
        skds=HostBlacklistsDataSourceFacade.getInstance();
    }

    public void run(){

        validar();
        //System.out.println("trhed"+inicio+" "+fin);


    }

    private void validar(){
        blackListOcurrences = valida.checkHost(ip,inicio,fin);
    }

}
