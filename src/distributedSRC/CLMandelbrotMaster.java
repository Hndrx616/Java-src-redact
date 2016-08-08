import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.image.BufferedImage;


public class CLMandelbrotMaster {

   private static final int DEFAULT_PORT = 13572;
   private static final String CLOSE_CONNECTION_COMMAND = "close";
   private static final String SHUT_DOWN_COMMAND = "shutdown";
   private static final String TASK_COMMAND = "task";
   private static final String RESULT_COMMAND = "result";
   private static ArrayList<CLMandelbrotTask> tasks;
   private static int tasksCompleted;
   private static int rows, columns;
   private static int maxIterations;
   private static int[][] mandelbrotData;  
   public static void main(String[] args) {
      
      long startTime = System.currentTimeMillis();
      
      createJob();  // Create the list of tasks that need to be computed.
      
      if (args.length == 0) { // Run non-distributed computation.
         
         System.out.println("Running on this computer only...");
         for (int i = 0; i < tasks.size(); i++) {
            CLMandelbrotTask task = tasks.get(i);
            task.compute();
            finishTask(task);
         }
         
      }
      else {  // Run a distributed computation.
      
         WorkerConnection[] workers = new WorkerConnection[args.length];
         
         for (int i = 0; i < args.length; i++) {
               // Create the worker threads that communicate with the
               // CLMandelbrotWorker programs.  The threads start automatically
               // as soon as they are created.
            String host = args[i];
            int port = DEFAULT_PORT;
            int pos = host.indexOf(':');
            if (pos >= 0) {
                  // The host string contains a ":", which should be
                  // followed by the port number.
               String portString = host.substring(pos+1);
               host = host.substring(0,pos);  // Remove port from host string.
               try {
                  port = Integer.parseInt(portString);
               }
               catch (NumberFormatException e) {
               }
            }
            workers[i] = new WorkerConnection(i+1, host, port);
         }
         
         for (int i = 0; i < args.length; i++) {
                // Wait for all the threads to terminate.
            while (workers[i].isAlive()) {
               try {
                  workers[i].join();
               }
               catch (InterruptedException e) {
               }
            }
         }
   
         if (tasksCompleted != rows) {
               // Not all of the tasks were completed.
            System.out.println("Something went wrong.  Only " + tasksCompleted);
            System.out.println("out of " + rows + " tasks were completed");
            System.exit(1);
         }
         
      }
      
      long elapsedTime = System.currentTimeMillis() - startTime;
      System.out.println("Finished in " + (elapsedTime/1000.0) + " seconds ");
      
      // saveImage();  // Uncomment this line if you would like to save the
                       // image that was computed by this program to a file.
   
   } // end main()

   private static void createJob() {
      double xmin = -0.9548900066789311; // Region of xy-plane shown in the image.
      double xmax = -0.9548895970332226;
      double ymin = 0.2525416221154478;
      double ymax = 0.25254192934972913;
      maxIterations = 10000;
      rows = 768;
      columns = 1024;
      mandelbrotData = new int[rows][columns];
      double dx = (xmax - xmin)/(columns+1);
      double dy = (ymax - ymin)/(rows+1);
      tasks = new ArrayList<CLMandelbrotTask>();
      for (int j = 0; j < rows; j++) {  // Add tasks to the task list.
         CLMandelbrotTask task;
         task = new CLMandelbrotTask();
         task.id = j;
         task.maxIterations = maxIterations;
         task.y = ymax-j*dy;
         task.xmin = xmin;
         task.dx = dx;
         task.count = columns;
         tasks.add(task);
      }
   }
   
   synchronized private static CLMandelbrotTask getNextTask() {
      if (tasks.size() == 0)
         return null;
      else 
         return tasks.remove(0);
   }

   synchronized private static void reassignTask(CLMandelbrotTask task) {
      tasks.add(task);
   }
  
   synchronized private static void finishTask(CLMandelbrotTask task) {
      int row = task.id;
      System.arraycopy(task.results,0,mandelbrotData[row],0,columns);
      tasksCompleted++;
   }

   private static String writeTask(CLMandelbrotTask task) {
      StringBuffer buffer = new StringBuffer();
      buffer.append(TASK_COMMAND);
      buffer.append(' ');
      buffer.append(task.id);
      buffer.append(' ');
      buffer.append(task.maxIterations);
      buffer.append(' ');
      buffer.append(task.y);
      buffer.append(' ');
      buffer.append(task.xmin);
      buffer.append(' ');
      buffer.append(task.dx);
      buffer.append(' ');
      buffer.append(task.count);
      buffer.append(' ');
      return buffer.toString();
   }

   private static void readResults(String data, CLMandelbrotTask task) throws Exception {
      Scanner scanner = new Scanner(data);
      scanner.next();  // read "results" at beginning of line
      int id = scanner.nextInt();
      if (id != task.id)
         throw new IOException("Wrong task ID in results returned by worker");
      int count = scanner.nextInt();
      if (count != task.count)
         throw new IOException("Wrong data count in results returned by worker");
      task.results = new int[count];
      for (int i = 0; i < count; i++)
         task.results[i] = scanner.nextInt();
   }

   private static class WorkerConnection extends Thread {
      
      int id;        // Identifies this thread in output statements.
      String host;   // The host to which this thread will connect.
      int port;      // The port number to which this thread will connect.
      
      WorkerConnection(int id, String host, int port) {
         this.id = id;
         this.host = host;
         this.port = port;
         start();
      }
      public void run() {
         
         int tasksCompleted = 0; // How many tasks has this thread handled.
         Socket socket;  // The socket for the connection.
         
         try {
            socket = new Socket(host,port);  // open the connection.
         }
         catch (Exception e) {
            System.out.println("Thread " + id + " could not open connection to " +
                  host + ":" + port);
            System.out.println("   Error: " + e);
            return;
         }
                  
         CLMandelbrotTask currentTask = null;
         CLMandelbrotTask nextTask = null;

         try {
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(
                                new InputStreamReader(socket.getInputStream()) );
            currentTask = getNextTask();
            if (currentTask != null) {
                  // Send first task to the worker program.
               String taskString = writeTask(currentTask);
               out.println(taskString);
               out.flush();
            }
            while (currentTask != null) {
               String resultString = in.readLine(); // Get results for currentTask.
               if (resultString == null)
                  throw new IOException("Connection closed unexpectedly.");
               if (! resultString.startsWith(RESULT_COMMAND))
                  throw new IOException("Illegal string received from worker.");
               nextTask = getNextTask();  // Get next task and send it to worker.
               if (nextTask != null) {
                     // Send nextTask to worker before processing results for 
                     // currentTask, so that the worker can work on nextTask
                     // while the currentTask results are processed.
                  String taskString = writeTask(nextTask);
                  out.println(taskString);
                  out.flush();
               }
               readResults(resultString, currentTask); 
               finishTask(currentTask);  // Process results from currentTask.
               tasksCompleted++;
               currentTask = nextTask;   // We are finished with old currentTask.
               nextTask = null;
            }
            out.println(CLOSE_CONNECTION_COMMAND);  // Send close command to worker.
            out.flush();
         }
         catch (Exception e) {
            System.out.println("Thread " + id + " terminated because of an error");
            System.out.println("   Error: " + e);
            e.printStackTrace();
               // Put uncompleted task, if any, back into the task list.
            if (currentTask != null)
               reassignTask(currentTask);
            if (nextTask != null)
               reassignTask(nextTask);
         }
         finally {
            System.out.println("Thread " + id + " ending after completing " + 
                  tasksCompleted + " tasks");
            try {
               socket.close();
            }
            catch (Exception e) {
            }
         }
         
      } //end run()
      
   } // end nested class WorkerConnection

   private static void saveImage() {
      Scanner in = new Scanner(System.in);
      System.out.println();
      while (true) {
         System.out.println("Computation complete.  Do you want to save the image?");
         String line = in.nextLine().trim().toLowerCase();
         if (line.equals("no") || line.equals("n"))
            break;
         else if (line.equals("yes") || line.equals("y")) {
            JFileChooser fileDialog = new JFileChooser();
            fileDialog.setSelectedFile(new File("CLMandelbrot_image.png")); 
            fileDialog.setDialogTitle("Select File to be Saved");
            int option = fileDialog.showSaveDialog(null);
            if (option != JFileChooser.APPROVE_OPTION)
               return;  // User canceled or clicked the dialog's close box.
            File selectedFile = fileDialog.getSelectedFile();
            if (selectedFile.exists()) {  // Ask the user whether to replace the file.
               int response = JOptionPane.showConfirmDialog( null,
                     "The file \"" + selectedFile.getName()
                     + "\" already exists.\nDo you want to replace it?", 
                     "Confirm Save",
                     JOptionPane.YES_NO_OPTION, 
                     JOptionPane.WARNING_MESSAGE );
               if (response != JOptionPane.YES_OPTION)
                  return;  // User does not want to replace the file.
            }
            try {
               int[] palette = new int[250];
               for (int i = 0; i < 250; i++) {
                  Color c = new Color(i,i,i);
                  palette[i] = c.getRGB();
               }
               BufferedImage OSI = new BufferedImage(columns,rows,BufferedImage.TYPE_INT_RGB);
               int[] rgb = new int[columns];
               for (int row = 0; row < rows; row++) {
                  for (int col = 0; col < columns; col++) {
                     if (mandelbrotData[row][col] == maxIterations)
                        rgb[col] = 0;
                     else
                        rgb[col] = palette[ (int)((mandelbrotData[row][col] * 250.0)/maxIterations) ];
                  }
                  OSI.setRGB(0,row,columns,1,rgb,0,1024);
               }
               boolean hasPNG = ImageIO.write(OSI,"PNG",selectedFile);
               if ( ! hasPNG )
                  throw new Exception("PNG format not available.");
            }
            catch (Exception e) {
               System.out.println("Sorry, but an error occured while trying to save the image.");
               e.printStackTrace();
            }
            break;
         }
         else
            System.out.println("Answer yes or no.");
      }
   }
} // end CLMandelbrotMaster