import java.net.*;
import java.io.*;
import java.util.Date;

public class PostMain {

//    public static double weights[] = {0,0,0,0,0,0,0,0,0};
    public static double weights[] = {5000,5000,5000,0,0,0,5000,5000,5000};
    public static long time[] = {-1,-1,-1,-1,-1,-1,-1,-1,-1};
    public static long time_mem[] = {-1,-1,-1,-1,-1,-1,-1,-1,-1};
    public static int count[] = {0,0,0,0,0,0,0,0,0};
    public static long postFireTime = -1;
    public static double threshold = 5;
    public static double level = 0;

    public static void main(String args[]) throws IOException{
        ServerSocket server = new ServerSocket(5000);
        while(true){
            Socket socket = null;
            try{
                socket = server.accept();
                ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                Thread t = new Thread(new POSTIntegrator(socket, ois));
                t.start();
            } catch (Exception e){
                socket.close();
                e.printStackTrace();
            }
        }
    }

    public void integrateAndFire(Pulse pulse){

        int id = pulse.id;
        time[id] = pulse.time;
        System.out.println("id "+id);
        System.out.println("Wt "+weights[id]);
        System.out.println("Mult "+(Math.log10(weights[id])));
        double updateLevel = 1;
        if(weights[id] == 0) {
            updateLevel = 1;
        } else if(weights[id] < 0){
            if (weights[id] < -10) {
                updateLevel = 1/Math.log10(-weights[id]);
            } else {
                updateLevel = 1;
            }

        }
        else {
            if(weights[id] > 1){
                updateLevel = 1+Math.log10(weights[id]);
            } else {
                updateLevel = 1;
            }
        }
        System.out.println("Adding " + updateLevel);

        level+= updateLevel;

        if(level >= threshold){
            postFire();
            level = 0;
        }

        if(postFireTime != -1) {
            long delay = pulse.time - postFireTime;
//            System.out.println(id + " w/ delay " + delay);
            updateWeights(id, delay);
        }
        printWeights();
    }


    public void postFire(){
        Date date = new Date();
        postFireTime = date.getTime();

        //Depressing Inactive Neurons
        for(int i = 0; i < time.length; i++){
            if(time[i] == time_mem[i]){
                System.out.println(i+" did not fire this cycle");
                if(weights[i] > -5000){
                    weights[i]-= 2;
                }
            }
            time_mem[i] = time[i];
        }
        for(int i = 0; i < time.length; i++){
            if(time[i] != -1) {
                long delay = time[i] - postFireTime;
//                System.out.println("POST " + i + " w/ delay " + delay);
                updateWeights(i, delay);
            }
        }
        System.out.println("Fired!");
    }

    public void updateWeights(int id, long delay){
        double dw = weightFunction(delay);
        System.out.println(id+" has delay "+delay+"updating weight by "+(-dw));
        if(weights[id] > 5000){
            return;
        }
        if(weights[id] < -5000){
            return;
        }
        if(delay == 0){
            // LTP
            weights[id] += 1;
        } else if (delay < 0){
            // LTP
            weights[id] -= dw;
        } else if (delay > 0){
            // LTD
            weights[id] -= dw;
        }
    }

    public double weightFunction(long delay){
        double dw = 35.0/delay;
        return dw;
    }

    public void printWeights(){
        System.out.println("------------------------------");
        for(int i = 0; i < weights.length; i++){
            System.out.printf("%5f",weights[i]);
            System.out.print("|");
            if((i+1)%3==0){
                System.out.println();
                System.out.println("------");
            }
        }
    }

    public void counter(int id){
        count[id]++;
        if(id == 3){
            System.out.println("UPDATED 3!!!!!");
        }
        printCounter();
    }

    public void printTimes(){
        System.out.println("------------------------------");
        for(int i = 0; i < time.length; i++){
            System.out.printf("%15d",time[i]);
            System.out.print("|");
            if((i+1)%3==0){
                System.out.println();
                System.out.println("------");
            }
        }
    }

    public void printCounter(){
        System.out.println("------------------------------");
        for(int i = 0; i < count.length; i++){
            System.out.printf("%5d",count[i]);
            System.out.print("|");
            if((i+1)%3==0){
                System.out.println();
                System.out.println("------");
            }
        }
    }
}

class POSTIntegrator extends PostMain implements Runnable {

    public Socket socket;
    public ObjectInputStream ois;

    public POSTIntegrator (Socket s, ObjectInputStream ois)
    {
        this.socket = s;
        this.ois = ois;
    }

    @Override
    public void run(){
        while(true){
            try {
                Pulse pulse = (Pulse) ois.readObject();
//                System.out.println(pulse.id+" "+pulse.time);
                integrateAndFire(pulse);
            } catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}