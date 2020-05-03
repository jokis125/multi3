import java.text.DecimalFormat;
import java.util.ArrayList;

public class Main
{
    public static double result = 0;

    public static void main(String args[])
    {
        int threadCount = 1;
        double a = -300000000;
        double b = 300000000;

        var resultList = new ArrayList<Double>();
        int threadRunAmount = 20;
        //--------------
        for(var i = 0; i <= 16 ; i++)
        {
            threadCount = i;
            double times = 0;
            for(var j = 0; j < threadRunAmount ; j++)
            {
                var aq = new AdaptiveQuadrature(threadCount);
                double startTime = System.nanoTime();
                Main.result = aq.adaptive(a,b);
                double endTime = System.nanoTime();
                double timeElapsed = (endTime - startTime) / 1_000_000_000;
                times+= timeElapsed;
            }
            var timeElapsedAverage = times/threadRunAmount;
            DecimalFormat numberFormat = new DecimalFormat("#.0000000");

            if(threadCount != 0)
            {
                System.out.print("Thread count: " + threadCount);
                //System.out.println("Integrated range: [" + a + "; " + b + "]");
                System.out.println(" | Time taken on average: " + numberFormat.format(timeElapsedAverage)
                        + " seconds");
                //System.out.println("| Area: " + Main.result);
            }
        }





    }
}

