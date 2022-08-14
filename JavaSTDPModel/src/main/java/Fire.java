import java.io.Serializable;

class Pulse implements Serializable {
    public int id;
    public long time;

    Pulse(int id, long time){
        this.id = id;
        this.time = time;
    }
}