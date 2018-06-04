/*
Comp317 Assignment 2:
Jacob Van Walraven :1299808
Bryny Patchett :1245256
*/

import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.io.DataInputStream;

class BitUnpacker{
  public static void main (String args[]){

    BitManipulation in = new BitManipulation(new DataInputStream(System.in));
    int count = 257;
    int numbits = 9;

    try {

      Integer input = in.get(numbits);
      while(input != null) {
        System.out.println(input);                                        // print out to standard output
        if(input == 256) { numbits = 9; count = 257; }                    // if the phrase number was 256 we can reset the number of encoding bits to 9
        count++;                                                          // increase the count
        if(count == (int)Math.pow(2, numbits)) { numbits += 1; }          // if the count has reached the max value for the numbits, increase numbits
        input = in.get(numbits);                                          // get the next number of input bits required
      }
      System.out.flush();                                                 // flush out anything left in java's buffers

    } catch(Exception e) { System.err.println(e + " count: " + count); }
  }
}
