public class AdaptiveQuadrature
{
    private final static double EPSILON = 1E-22;
    static int runningThreads;
    int threadCount;

    public AdaptiveQuadrature(int threadCount)
    {
        this.threadCount = threadCount;
    }

    static double fn(double x)
    {
        return Math.exp(- x * x / 2) / Math.sqrt(2 * Math.PI);
        //return  10 * Math.pow(x, 2) - 4 * x + 5;
    }


    public double adaptive(double a, double b)
    {
        double h = b - a;
        double c = (a + b) / 2.0;
        double d = (a + c) / 2.0;
        double e = (b + c) / 2.0;
        double Q1 = h / 6 * (fn(a) + 4 * fn(c) + fn(b));
        double Q2 = h / 12 * (fn(a) + 4 * fn(d) + 2 * fn(c) + 4 * fn(e) + fn(b));
        if (Math.abs(Q2 - Q1) <= EPSILON)
            return Q2 + (Q2 - Q1) / 15;
        else
        {
            MyThread t1 = new MyThread(this, a, c);
            MyThread t2 = new MyThread(this, c, b);
            if(runningThreads+2 <= threadCount)
            {
                incRunningThreads(2);
                t1.start();
                t2.start();
                try
                {
                    t1.join();
                    incRunningThreads(-1);
                    t2.join();
                    incRunningThreads(-1);
                }
                catch (Exception exc)
                {
                    exc.printStackTrace();
                }
                var res1 = t1.Result;
                var res2 = t2.Result;
                return res1 + res2;
            }

            else if(runningThreads+1 <= threadCount)
            {
                incRunningThreads(1);
                t1.start();
                try
                {
                    t1.join();
                    incRunningThreads(-1);
                }
                catch (Exception exc)
                {
                    exc.printStackTrace();
                }
                var res1 = t1.Result;
                var res2 = adaptive(c,b);
                return res1 + res2;
            }
            else if(threadCount == 1)
            {
                return adaptive(a, c) + adaptive(c, b);
            }
            else
            {
                return adaptive(a, c) + adaptive(c, b);
            }
        }
    }

    synchronized void incRunningThreads(int a)
    {
        runningThreads += a;
    }
}