package snakepackage;


import java.awt.*;

import javax.sound.midi.Soundbank;
import javax.swing.JFrame;

import enums.GridSize;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLOutput;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.plaf.TableHeaderUI;

/**
 * @author jd-
 *
 */
public class SnakeApp  {

    private static SnakeApp app;
    public static final int MAX_THREADS = 8;
    Snake[] snakes = new Snake[MAX_THREADS];
    ArrayList<Snake> snakeD;
    private static final Cell[] spawn = {
        new Cell(1, (GridSize.GRID_HEIGHT / 2) / 2),
        new Cell(GridSize.GRID_WIDTH - 2,
        3 * (GridSize.GRID_HEIGHT / 2) / 2),
        new Cell(3 * (GridSize.GRID_WIDTH / 2) / 2, 1),
        new Cell((GridSize.GRID_WIDTH / 2) / 2, GridSize.GRID_HEIGHT - 2),
        new Cell(1, 3 * (GridSize.GRID_HEIGHT / 2) / 2),
        new Cell(GridSize.GRID_WIDTH - 2, (GridSize.GRID_HEIGHT / 2) / 2),
        new Cell((GridSize.GRID_WIDTH / 2) / 2, 1),
        new Cell(3 * (GridSize.GRID_WIDTH / 2) / 2,
        GridSize.GRID_HEIGHT - 2)};
    private JFrame frame;
    private static Board board;
    private Verificar verificador;
    int nr_selected = 0;
    Thread[] thread = new Thread[MAX_THREADS];
    private  static JButton start, resume, stop;

    public SnakeApp() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame = new JFrame("The Snake Race");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setSize(618, 640);
        frame.setSize(GridSize.GRID_WIDTH * GridSize.WIDTH_BOX + 17,
                GridSize.GRID_HEIGHT * GridSize.HEIGH_BOX + 40);
        frame.setLocation(dimension.width / 2 - frame.getWidth() / 2,
                dimension.height / 2 - frame.getHeight() / 2);
        board = new Board();
        
        verificador = new Verificar();
        frame.add(board,BorderLayout.CENTER);
        
        JPanel actionsBPabel=new JPanel();
        actionsBPabel.setLayout(new FlowLayout());

        start = new JButton("Start");
        stop = new JButton("Pause");
        resume = new JButton("Resume");
        actionsBPabel.add(start);
        actionsBPabel.add(resume);
        actionsBPabel.add(stop);
        frame.add(actionsBPabel,BorderLayout.SOUTH);

        snakeD = new ArrayList<Snake>();
        prepareAcciones();

    }

    private void prepareAcciones() {

        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (Thread i : thread){
                    i.start();
                }


                start.setEnabled(false);
                stop.setEnabled(true);
                resume.setEnabled(false);
            }
        });
        stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int max = -100;
                int peor=0;
                for (Snake i : snakes){
                    if (i.getBody().size() > max){max = i.getBody().size();}
                }
                for (Thread i: thread){
                    i.suspend();
                }
                peor = verificador.getprimeraMuerta();
                if (peor == -1) {
                    System.out.println("No hay muertas");
                }
                else{
                    System.out.println("La primera en morir fue: "+ peor);
                }
                //System.out.println(snakeD.get(0).getIdt());
                System.out.println("la longitud maxima hasta el momento es: "+max);

                stop.setEnabled(false);
                resume.setEnabled(true);

            }
        });
        resume.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (Thread i: thread){
                    i.resume();
                }
                resume.setEnabled(false);
                stop.setEnabled(true);
            }
        });

    }


    public static void main(String[] args) {
        app = new SnakeApp();
        app.init();
    }

    private void init() {
        
        
        
        for (int i = 0; i != MAX_THREADS; i++) {
            
            snakes[i] = new Snake(i + 1, spawn[i], i + 1, verificador);
            snakes[i].addObserver(board);
            thread[i] = new Thread(snakes[i]);

        }

        frame.setVisible(true);


        while (true) {
            int x = 0;
            for (int i = 0; i != MAX_THREADS; i++) {
                if (snakes[i].isSnakeEnd() == true) {
                    x++;
                }
            }
            if (x == MAX_THREADS) {
                break;
            }
        }


        System.out.println("Thread (snake) status:");
        for (int i = 0; i != MAX_THREADS; i++) {
            System.out.println("["+i+"] :"+thread[i].getState());
        }


    }

    public static SnakeApp getApp() {
        return app;
    }

}
