import java.net.*;
import java.io.*;
import java.util.Date;


public class PreMain {
    public static void main(String args[]){
        int totalThreads = 9;
        int pattern[] = {1,0,1,0,1,0,1,0,1};
        for(int i = 0; i < totalThreads; i++){
            PreNeuron neuron = new PreNeuron(i, pattern[i]);
            neuron.start();
        }
    }
}

class PreNeuron extends Thread
{

    public int id;
    public int active;

    Socket socket = null;
    ObjectOutputStream oos =  null;

    public PreNeuron(int id, int active) {
        this.id = id;
        this.active = active;
    }

    @Override
    public void run()
    {
        System.out.println("Neuron #"+id+" active!");
        int noiseLevel = 1;
        int noisy = 0;
        int isActive = active;
        try
        {
            socket = new Socket("127.0.0.1",5000);
            oos = new ObjectOutputStream(socket.getOutputStream());
            while(true)
            {
                isActive = active;
                if(getRandomNumber(0,9) < noiseLevel) {
                    isActive = 1-active;
//                    System.out.println(id + " is noisy");
                }
                try
                {
//                    isActive = active;
                    if(isActive == 1){
                        String data = String.valueOf(id);
                        Date date = new Date();
                        Pulse pulse = new Pulse(id, date.getTime());
                        oos.writeObject(pulse);
                    }
                    int delay = getRandomNumber(10,30);
                    Thread.sleep(delay);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}