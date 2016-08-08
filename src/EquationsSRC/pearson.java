import java.util.Vector;
public class Pearson {

  //Calculate the Pearson correlation coefficient of two lists, X and Y.
  //return measure of correlation between the two lists

  public static double GetCorrelation(Vector<Double> xVect, Vector<Double> yVect) {
    double meanX = 0.0, meanY = 0.0;
    for(int i = 0; i < xVect.size(); i++)
    {
        meanX += xVect.elementAt(i);
        meanY += yVect.elementAt(i);
    }

    meanX /= xVect.size();
    meanY /= yVect.size();

    double sumXY = 0.0, sumX2 = 0.0, sumY2 = 0.0;
    for(int i = 0; i < xVect.size(); i++)
    {
      sumXY += ((xVect.elementAt(i) - meanX) * (yVect.elementAt(i) - meanY));
      sumX2 += Math.pow(xVect.elementAt(i) - meanX, 2.0);
      sumY2 += Math.pow(yVect.elementAt(i) - meanY, 2.0);
    }

    return (sumXY / (Math.sqrt(sumX2) * Math.sqrt(sumY2)));
  }//end: GetCorrelation(X,Y)
}



//Real time example model

public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
 String line = value.toString();
 String[] tokens = line.split(DELIM); 
 double[] arr = toDouble(tokens); 
  
 for(int i=0; i < arr.length; i++) {
  for(int j=i+1; j < arr.length; j++) {
   VariableIndexPairsWritable k2 = new VariableIndexPairsWritable(i, j);
   VariableValuePairsWritable v2 = new VariableValuePairsWritable(arr[i], arr[j]);
   context.write(k2, v2);
   } 
  }
}
public double[] toDouble(String[] tokens) {
 double[] arr = new double[tokens.length];
 for(int i=0; i < tokens.length; i++) {
  arr[i] = Double.parseDouble(tokens[i]);
 }
 return arr;
}


//real time example Caml
## Hollander & Wolfe (1973), p. 187f.
## Assessment of tuna quality.  We compare the Hunter L measure of
##  lightness to the averages of consumer panel scores (recoded as
##  integer values from 1 to 6 and averaged over 80 such values) in
##  9 lots of canned tuna.

x <- c(44.4, 45.9, 41.9, 53.3, 44.7, 44.1, 50.7, 45.2, 60.1)
y <- c( 2.6,  3.1,  2.5,  5.0,  3.6,  4.0,  5.2,  2.8,  3.8)

##  The alternative hypothesis of interest is that the
##  Hunter L value is positively associated with the panel score.

cor.test(x, y, method = "kendall", alternative = "greater")
## => p=0.05972

cor.test(x, y, method = "kendall", alternative = "greater",
         exact = FALSE) # using large sample approximation
## => p=0.04765

## Compare this to
cor.test(x, y, method = "spearm", alternative = "g")
cor.test(x, y,                    alternative = "g")

## Formula interface.
require(graphics)
pairs(USJudgeRatings)
cor.test(~ CONT + INTG, data = USJudgeRatings)