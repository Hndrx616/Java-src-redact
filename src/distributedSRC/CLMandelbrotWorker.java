import java.io.*;
import java.net.*;
import java.util.Scanner;

public class CLMandelbrotWorker {

   private static final int DEFAULT_PORT = 13572;
   private static final String CLOSE_CONNECTION_COMMAND = "close";
   private static final String SHUT_DOWN_COMMAND = "shutdown";
   private static final String TASK_COMMAND = "task";
   private static final String RESULT_COMMAND = "result";
   private static boolean shutdownCommandReceived;
   public static void main(String[] args) {

      int port = DEFAULT_PORT;

      if (args.length > 0) {
         try {
            port = Integer.parseInt(args[0]);
            if (port < 0 || port > 65535)
               throw new NumberFormatException();
         }
         catch (NumberFormatException e) {
            port = DEFAULT_PORT;
         }
      }

      System.out.println("Starting with listening port number " + port);

      while (shutdownCommandReceived == false) {

         ServerSocket listener = null;
         try {
            listener = new ServerSocket(port);
         }
         catch (Exception e) {
            System.out.println("ERROR: Can't create listening socket on port " + port);
            System.exit(1);
         }
         
         try {
            Socket connection = listener.accept();
            listener.close();
            System.out.println("Accepted connection from " + connection.getInetAddress());
            handleConnection(connection);
         }
         catch (Exception e) {
            System.out.println("ERROR: Server shut down with error:");
            System.out.println(e);
            System.exit(2);
         }
      }

      System.out.println("Shutting down normally.");

   } // end main()
   
   private static CLMandelbrotTask readTask(String taskData) throws IOException {
      try {
         Scanner scanner = new Scanner(taskData);
         CLMandelbrotTask task = new CLMandelbrotTask();
         scanner.next();  // skip the command at the start of the line.
         task.id = scanner.nextInt();
         task.maxIterations = scanner.nextInt();
         task.y = scanner.nextDouble();
         task.xmin = scanner.nextDouble();
         task.dx = scanner.nextDouble();
         task.count = scanner.nextInt();
         return task;
      }
      catch (Exception e) {
         throw new IOException("Illegal data found while reading task information.");
      }
   }
   
   private static String writeResults(CLMandelbrotTask task) {
      StringBuffer buffer = new StringBuffer();
      buffer.append(RESULT_COMMAND);
      buffer.append(' ');
      buffer.append(task.id);
      buffer.append(' ');
      buffer.append(task.count);
      for (int i = 0; i < task.count; i++) {
         buffer.append(' ');
         buffer.append(task.results[i]);
      }
      return buffer.toString();
   }

   private static void handleConnection(Socket connection) {
      try {
         BufferedReader in = new BufferedReader( new InputStreamReader(
                                             connection.getInputStream()) );
         PrintWriter out = new PrintWriter(connection.getOutputStream());
         while (true) {
            String line = in.readLine();  // Message from the master.
            if (line == null) {
                  // End-of-steram encountered -- should not happen.
               throw new Exception("Connection closed unexpectedly.");
            }
            if (line.startsWith(CLOSE_CONNECTION_COMMAND)) {
                  // Represents the normal termination of the connection.
               System.out.println("Received close command.");
               break;
            }
            else if (line.startsWith(SHUT_DOWN_COMMAND)) {
                  // Represents the normal termination of the connection
                  // and also tells this worker to shut down.
               System.out.println("Received shutdown command.");
               shutdownCommandReceived = true;
               break;
            }
            else if (line.startsWith(TASK_COMMAND)) {
                  // Represents a CLMandelbrotTask that this worker is
                  // supposed to perform.
               CLMandelbrotTask task = readTask(line);  // Decode the message.
               task.compute();  // Peform the task.
               out.println(writeResults(task));  //  Send back the results.
               out.flush();
            }
            else {
                  // No other messages are part of the protocol.
               throw new Exception("Illegal copmmand received.");
            }
         }
      }
      catch (Exception e) {
         System.out.println("Client connection closed with error " + e);
      }
      finally {
         try {
            connection.close();  // Make sure the socket is closed.
         }
         catch (Exception e) {
         }
      }
   }
}