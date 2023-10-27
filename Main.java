
public class Main {

    public static void main(String[] args) {
        int dim = 100000;
        int threadNum = 6;
        ArrClass arrClass = new ArrClass(dim, threadNum);

        System.out.println("Minimum element: " + arrClass.getMin() + " at index " + arrClass.getMinIndex());

        System.out.println(arrClass.threadSum());
    }
}
