public class RandomData {
    public static void main(String args[]){
        for(int i = 0; i < 50; i++){
            int[] arr = generateNoise();
            printArray(arr);
            System.out.println();
        }
    }

    public static int[] generateNoise(){
        int level = 2;
        int noise[] = {0,0,0,0,0,0,0,0,0};
        for(int i = 0; i < 9; i++){
            if(getRandomNumber(0,9) < level) {
                noise[i] = 1;
            }
        }
        return noise;
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static void printArray(int[] arr){
        for(int i = 0; i < arr.length; i++){
            System.out.print(arr[i]+" ");
        }
    }
}
