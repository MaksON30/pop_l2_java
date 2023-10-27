import java.util.Random;

class ArrClass {
    private final int dim;
    private final int threadNum;
    public final int[] arr;
    private long sum = 0;
    private int threadCount = 0;
    private final Object lock = new Object();
    private int minIndex;
    private int min;


    public ArrClass(int dim, int threadNum) {
        this.dim = dim;
        arr = new int[dim];
        this.threadNum = threadNum;
        InitArr();
    }

    private void InitArr() {
        Random random = new Random();
        int randomIndex = random.nextInt(dim);
        int negativeNumber = -random.nextInt(dim);
        this.minIndex = randomIndex;
        this.min = negativeNumber;

        for (int i = 0; i < dim; i++) {
            if (i == randomIndex) {
                arr[i] = negativeNumber;
            } else {
                arr[i] = i;
            }
        }
    }
    public double getMin() {
        return min;
    }

    public int getMinIndex() {
        return minIndex;
    }


    public long partSum(int startIndex, int finishIndex) {
        long sum = 0;
        for (int i = startIndex; i < finishIndex; i++) {
            sum += arr[i];
            // Знаходження мінімального елемента та його індексу
            if (arr[i] < min) {
                min = arr[i];
                minIndex = i;
            }
        }
        return sum;
    }

    public long threadSum() {
        Thread[] threads = new Thread[threadNum];
        for (int i = 0; i < threadNum; i++) {
            int start = i * dim / threadNum;
            int end = (i + 1) * dim / threadNum;
            System.out.println("Thread " + (i + 1) + " processing range from " + start + " to " + end);
            threads[i] = new Thread(new ThreadSum(start, end, this));
            threads[i].start();
        }
        synchronized (lock) {
            while (threadCount < threadNum) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return sum;
    }

    public void collectSum(long sum) {
        synchronized (lock) {
            this.sum += sum;
            threadCount++;
            if (threadCount == threadNum) {
                lock.notify();
            }
        }
    }

}
