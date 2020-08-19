# Part I - Introduction to threads in JAVA
1. In agreement with the lectures, complete the classes CountThread, so that they define the life cycle of a thread that prints the numbers between A and B on the screen.

![](/img/implementacionHilo.png)

2. Complete the main method of the CountMainThreads class so that: 
- Create 3 threads of type CountThread, assigning the first interval [0..99], the second [99..199], and the third [200..299]. 

![](/img/implementacionMain.png)

- Start the three threads with start(). Run and check the output on the screen. 

![](/img/start().png)

- Change the beginning with start() to run(). How does the output change? Why?

![](/img/mainRun().png)

![](/img/run().png)

La diferencia radica principalmente en que cuando se usa el metodo star() se realiza paralelismo y cuando se usa run() un hilo se essjecuta despues del otro.

# Part II - Black List Search Exercise 