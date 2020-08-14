package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyThread extends Thread{
    private String ip;
    private int inicio, fin, servers, checkedListsCount;
    private int BLACK_LIST_ALARM_COUNT;
    private HostBlacklistsDataSourceFacade skds;
    private LinkedList<Integer> blackListOcurrences;
    private HostBlackListsValidator valida;


    public MyThread(String ip, int inicio, int fin, HostBlacklistsDataSourceFacade skds, HostBlackListsValidator instances) {
        this.ip = ip;
        this.inicio = inicio;
        this.fin = fin;
        this.servers = 0;
        BLACK_LIST_ALARM_COUNT = 5;
        this.skds = skds;
        blackListOcurrences=new LinkedList<>();
        System.out.println("hiloo..........");
        //System.out.println(inicio+","+fin);
        this.valida = instances;

    }

    public void run(){
        validator();
        //this.stop();
        //setServers();
    }

    private void validator(){
        checkedListsCount=0;
        for (int i=inicio;i<fin && servers<BLACK_LIST_ALARM_COUNT;i++){

            checkedListsCount++;

            if (skds.isInBlackListServer(i, ip)){

                blackListOcurrences.add(i);

                //setServers();
                servers++;
            }
            //if (valida.getServers()>=BLACK_LIST_ALARM_COUNT){
            //    skds.reportAsNotTrustworthy(ip);
            //}

        }

        //skds.reportAsTrustworthy(ip);
        //LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{valida.getServers(), skds.getRegisteredServersCount()});


    }
    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());

    public int getServers() {
        return servers;
    }
    public LinkedList<Integer> getblackListOcurrences(){
        return blackListOcurrences;
    }
    private void setServers(){
        valida.setServers(1);
    }

}
