/*
Comp317 Assignment 2:
Jacob Van Walraven :1299808
Bryny Patchett :1245256
*/

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.DataOutputStream;

public class BitPacker {
  public static void main(String[] args) {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    BitManipulation out = new BitManipulation(new DataOutputStream(System.out));
    int count = 257;
    int numbits = 9;

    try {
      String input = in.readLine();
      while(input != null) {
        Integer phrase = Integer.parseInt(input);                      // convert the line of input to a int
        if(count == (int)Math.pow(2, numbits)) { numbits += 1; }       // if the count has reached the max value for the numbits, increase numbits
        out.put(phrase, numbits);                                      // give the phrase to BitManipulation to deal with it
        if(phrase == 256) { numbits = 9; count = 257; }                // if the phrase number was 256 we can reset the number of encoding bits to 9
        count++;                                                       // increase the count
        input = in.readLine();                                         // read the next line
      }
      out.flush();                                                     // tell BitManipulation class to flush any remaining bits
    } catch(Exception e) { System.err.println(e); }
  }
}
