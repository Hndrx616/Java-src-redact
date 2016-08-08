import java.util.*;
class Main {
  public static void main (String[] args) throws Exception {
    System.out.println("");
    // you should use only one Scanner object
    Scanner scanner = new Scanner(System.in);
    int n = scanner.nextInt();
    for (int i=0; i<=n; i++) {
      double r;
      r = Math.exp(Math.log(2)*i);
      System.out.format("%.0f ", r);
    }
  }
}

