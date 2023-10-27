class ThreadSum implements Runnable {
    private final int startIndex;
    private final int finishIndex;
    private final ArrClass arrClass;
    private long partialSum;

    public ThreadSum(int startIndex, int finishIndex, ArrClass arrClass) {
        this.startIndex = startIndex;
        this.finishIndex = finishIndex;
        this.arrClass = arrClass;
    }

    @Override
    public void run() {
        partialSum = arrClass.partSum(startIndex, finishIndex);
        arrClass.collectSum(partialSum);
    }
}
