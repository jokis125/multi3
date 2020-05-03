public class MyThread extends Thread
{
    AdaptiveQuadrature adaptiveQuadrature;
    double a, b;
    public double Result;
    public MyThread(AdaptiveQuadrature aq, double a, double b)
    {
        adaptiveQuadrature = aq;
        this.a = a;
        this.b = b;
    }

    @Override
    public void run()
    {
        Result = adaptiveQuadrature.adaptive(a,b);
    }
}
