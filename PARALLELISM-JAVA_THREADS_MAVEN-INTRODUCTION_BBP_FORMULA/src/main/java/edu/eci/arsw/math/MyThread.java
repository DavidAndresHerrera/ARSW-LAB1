package edu.eci.arsw.math;

public class MyThread extends Thread{
    int start;
    int count;
    byte[] r;

    public MyThread(int start, int count) {
        this.start=start;
        this.count=count;
    }

    public void run() {
        this.r=PiDigits.getDigits(start,count);
    }

    public byte[] getResultado() {
        return this.r;
    }
}
