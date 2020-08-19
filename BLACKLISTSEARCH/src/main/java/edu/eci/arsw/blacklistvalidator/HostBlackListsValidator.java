/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class HostBlackListsValidator {

    private static final int BLACK_LIST_ALARM_COUNT=5;
    private  String ipaddress;
    private int numHilos, ocurrencesCount,checkedListsCount;
    private HostBlacklistsDataSourceFacade skds;
    private ArrayList<MyThread> hilos = new ArrayList<MyThread>();
    private LinkedList<Integer> blackListOcurrences;



    public HostBlackListsValidator(String ipaddress, int numHilos) throws InterruptedException {
        this.ipaddress = ipaddress;
        this.numHilos = numHilos;
        ocurrencesCount=0;
        blackListOcurrences=new LinkedList<>();
        checkedListsCount=0;
        crearHilos();
    }


    private void crearHilos() throws InterruptedException {
        skds = HostBlacklistsDataSourceFacade.getInstance();
        int division = Math.round(skds.getRegisteredServersCount() / numHilos);
        for (int i = 0; i < numHilos; i ++) {
            MyThread hilo = new MyThread(ipaddress, (division*i), (division*(i+1)) - 1, getInstances());
            hilos.add(hilo);
        }
        for (MyThread i: hilos){
            i.start();
        }
        for (MyThread i: hilos){
            i.join();
        }
        verificar();

    }
    
    /**
     * Check the given host's IP address in all the available black lists,
     * and report it as NOT Trustworthy when such IP was reported in at least
     * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case.
     * The search is not exhaustive: When the number of occurrences is equal to
     * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as
     * NOT Trustworthy, and the list of the five blacklists returned.
     * @param ipaddress suspicious host's IP address.
     * @return  Blacklists numbers where the given host's IP address was found.
     */
    public List<Integer> checkHost(String ipaddress,int inicio,int fin ){
        HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
        for (int i=inicio;i<fin && ocurrencesCount<BLACK_LIST_ALARM_COUNT;i++){
            checkedListsCount++;
            if (skds.isInBlackListServer(i, ipaddress)){
                blackListOcurrences.add(i);
                ocurrencesCount++;
            }
        }
        return blackListOcurrences;
    }

    private void  verificar (){
        if (blackListOcurrences.size()>= BLACK_LIST_ALARM_COUNT){
            skds.reportAsNotTrustworthy(ipaddress);
        }
        else{
            skds.reportAsTrustworthy(ipaddress);
        }

        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{blackListOcurrences, skds.getRegisteredServersCount()});
    }
    
    
    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());

    public HostBlackListsValidator getInstances(){
        return this;
    }
    
}