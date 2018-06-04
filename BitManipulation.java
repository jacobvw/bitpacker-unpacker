/*
Comp317 Assignment 2:
Jacob Van Walraven :1299808
Bryny Patchett :1245256
*/

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;

public class BitManipulation {
  private DataInputStream in_;
  private DataOutputStream out_;
  
  private int buffer_;
  private int bufferSize_;
  private boolean inputEmpty_;
  
  // constructors
  public BitManipulation(DataInputStream in) {
    in_ = in;
    out_ = null;
    inputEmpty_ = false;
    buffer_ = 0;
    bufferSize_ = 0;
  }
  public BitManipulation(DataOutputStream out) {
    in_ = null;
    out_ = out;
    buffer_ = 0;
    bufferSize_ = 0;
  }
  public BitManipulation(DataInputStream in, DataOutputStream out) {
    in_ = in;
    out_ = out;
    inputEmpty_ = false;
    buffer_ = 0;
    bufferSize_ = 0;
  }
  
  // writes the integer represented by the number of bits supplied to the output stream. If the overall length is not a multiple of 8
  // the flush method must be called
  public void put(int n, int numbits) {
    if(out_ != null) {                                          // only perform put operations if a output stream was set when class was instantiated
      if(bufferSize_ != 0) { buffer_ <<= numbits; }             // shift the data the numbits to be entered if its not empty
      n &= (int)(Math.pow(2, numbits) - 1);                     // & with the number of bits to get rid of any overflows
      buffer_ |= n;                                             // | n with the buffer
      bufferSize_ += numbits;                                   // increment the number of bits in buffer
    
      try { 
        while(bufferSize_ >= 8) {                               // while the buffer contains more than a byte (8 bits)
          int t = buffer_ >>> (bufferSize_ - 8);                // right shift to get rid of bytes we dont want
          bufferSize_ -= 8;                                     // reduce the buffer size by the number of bits requested
          buffer_ &= (int)(Math.pow(2, bufferSize_) - 1);       // clear up the buffer removing the bits that are being returned
          out_.write(t);                                        // write out the byte
          out_.flush();					        // flush it to ensure nothing is being keep in the OS's buffer
        }
      } catch(Exception e) {System.err.println(e); }
    } else { System.err.println("No output stream specified during class instantiation"); }
  }
  
  public void flush() {
    if(out_ != null) {                                          // only perform put operations if a output stream was set when class was instantiated
      if(bufferSize_ != 0 && bufferSize_ < 8) {		        // buffer only needs to be flushed if the remaining bits is less than a byte
        try {
          buffer_ <<= (8 - bufferSize_);			// move the least significant to the most significant positions of the buffer
          out_.write(buffer_);                                  // output the buffer
          out_.flush();					        // flush it to ensure nothing is being keep in the OS's buffer
      
          buffer_ &= (int)(Math.pow(2, bufferSize_) - 1);       // clear up the buffer removing the bits that are being returned
          bufferSize_ -= bufferSize_;			        // clean up the size incase the buffer starts being used again
        } catch(Exception e) { System.err.println(e); }
      }
    } else { System.err.println("No output stream specified during class instantiation"); }
  }
  
  // returns n number of bits from the input stream. If less than n avaliable returns null
  public Integer get(int n) {
    if(in_ != null) {                                           // only perform get operations if a input stream was set when class was instantiated
      try {                                                     // if the inputstream is not empty
        if(!inputEmpty_) {                                      // while the buffer size can fit in more bytes
          while(bufferSize_ <= 24) {
            int aa = in_.readByte();                            // read in the next byte
            if(bufferSize_ != 0) { buffer_<<=8; }               // shift the data 1 byte to the left if its not empty
            aa &= 255;                                          // & it will 255 to get rid of any overflow bits
            buffer_ |= aa;                                      // | it with the buffer
            bufferSize_ += 8;                                   // increment the size of the buffer
          }
        }
                                                                // return number of bits requested
      } catch(EOFException e) { inputEmpty_ = true; }           // EOFException occurs when the input stream is empty. So flag it is empty
      catch(Exception e) { System.err.println(e); }
    
      if(bufferSize_ < n) { return null; }                      // if requested n is greater than what is avaliable, return avaliable
      if(bufferSize_ == 0) { return null; }                     // if the buffer is empty return null
      
      int t = buffer_ >>> (bufferSize_ - n);                    // right shift to get rid of bytes we dont want
      bufferSize_ -= n;                                         // reduce the buffer size by the number of bits requested
      buffer_ &= (int)(Math.pow(2, bufferSize_) - 1);           // clear up the buffer removing the bits that are being returned

      return t; 
    } else { System.err.println("No input stream specified during class instantiation"); return null; }
  }
  
}

