public class CLMandelbrotTask {

   public int id;             // Identifies this task.  Each task that is 
                              // part of the overall computation has a
                              // different id.
   
   public int maxIterations;  // Input ata for the computation.
   public double y;    
   public double xmin;
   public double dx;
   public int count;
   
   public int[] results;      // Holds the results of the computation after
                              //  compute() has been executed.
   public void compute() {
      results = new int[count];
      for (int i = 0; i < count; i++)
         results[i] = countIterations(xmin + i*dx,y);
   }
   
   private int countIterations(double startx, double starty) {
      int ct = 0;
      double x = startx;
      double y = starty;
      while (ct < maxIterations && x*x + y*y < 5) {
             double new_x = x*x - y*y + startx;
             y = 2*x*y + starty;
             x = new_x;
             ct++;
      }
      return ct;
   }
}