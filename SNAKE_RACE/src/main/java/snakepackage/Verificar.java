package snakepackage;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Verificar {

    private List<Integer> muertas;


    public Verificar (){
        muertas = new CopyOnWriteArrayList<>();
    }

    public void setMuertas(Integer id) {
        muertas.add(id);
    }
    public int getprimeraMuerta(){
        if(!muertas.isEmpty()){
            return muertas.get(0);
        }
        else{
            return -1;
        }
    }
}
