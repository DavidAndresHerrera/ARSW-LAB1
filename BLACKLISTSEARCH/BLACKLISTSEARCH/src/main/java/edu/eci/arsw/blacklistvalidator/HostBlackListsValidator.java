/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */

public class HostBlackListsValidator {

    private static final int BLACK_LIST_ALARM_COUNT=5;
    private int n, servers;
    private String ipaddress;
    private ArrayList<MyThread> hilos = new ArrayList<MyThread>();
    private LinkedList<LinkedList<Integer>> nombreServers;
    private HostBlacklistsDataSourceFacade skds;
    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());


    public HostBlackListsValidator(String ipaddress, int n){
        servers = 0;
        this.ipaddress =  ipaddress;
        nombreServers = new LinkedList<LinkedList<Integer>>();
        checkHost(ipaddress, n);
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
    public void checkHost(String ipaddress, int n) {
        this.n = n;
        skds = HostBlacklistsDataSourceFacade.getInstance();
        int division = Math.round(skds.getRegisteredServersCount() / n);
        for (int i = 0; i < n; i ++) {
            MyThread hilo = new MyThread(ipaddress, (division*i), (division*(i+1)) - 1, skds, getInstances());
            hilos.add(hilo);
        }
        for (MyThread i: hilos){
                i.start();
        }
    }
    public HostBlackListsValidator getInstances(){
        return this;
    }
    public void setServers(int sumar) {
        servers += sumar;
    }
    /*
    public int getServers(){
        return servers;
    }
    */

    public void total (){
        int sumaTotal = 0;
        for (MyThread i: hilos){
            sumaTotal += i.getServers();
        }
        if (sumaTotal>=BLACK_LIST_ALARM_COUNT){
            skds.reportAsNotTrustworthy(ipaddress);
        }
        else{
            skds.reportAsTrustworthy(ipaddress);
        }
        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{sumaTotal, skds.getRegisteredServersCount()});
    }

}
