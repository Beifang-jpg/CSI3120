public class A4 {

    public static int count1 = 0;
    public static int count2 = 0;

    public static long singleOperation(long a) {
        
        return a + 1;
    }

    public static long multiOperation(long a, long b, long c, long d, long e, long f, long g, long h, long i, long j, 
                                     long k, long l, long m, long n, long o, long p, long q, long r, long s, long t) {
        return a + 1;
    }

    public static void main(String[] args) {
        final long iterations = 200000000000000000L;
        long startTime, endTime, durationSingle, durationMulti;

        // Timing for single parameter method
        startTime = System.nanoTime();
        for (long i = 0; i < iterations; i++) {
            singleOperation(i);
        }
        endTime = System.nanoTime();
        durationSingle = (endTime - startTime) / 1000000; 

        // Timing for 20 parameters method
        startTime = System.nanoTime();
        for (long i = 0; i < iterations; i++) {
            multiOperation(i, i, i, i, i, i, i, i, i, i, i, i, i, i, i, i, i, i, i, i);
        }
        endTime = System.nanoTime();
        durationMulti = (endTime - startTime) / 1000000; 

        // Output the results
        System.out.println("Time taken for single parameter method: " + durationSingle + " ms");
        System.out.println("Time taken for 20 parameters method: " + durationMulti + " ms");
    }
}