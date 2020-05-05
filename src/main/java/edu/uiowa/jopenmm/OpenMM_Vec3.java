package edu.uiowa.jopenmm;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;

/**
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a
 * href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource
 * projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a
 * href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 *
 * @author Michael J. Schnieders
 */
public class OpenMM_Vec3 extends Structure {
  public double x;
  public double y;
  public double z;

  /** Constructor for OpenMM_Vec3. */
  public OpenMM_Vec3() {
    super();
  }

  /**
   * getFieldOrder.
   *
   * @return a {@link java.util.List} object.
   */
  protected List<?> getFieldOrder() {
    return Arrays.asList("x", "y", "z");
  }

  /**
   * Constructor for OpenMM_Vec3.
   *
   * @param x a double.
   * @param y a double.
   * @param z a double.
   */
  public OpenMM_Vec3(double x, double y, double z) {
    super();
    this.x = x;
    this.y = y;
    this.z = z;
  }

  /**
   * Constructor for OpenMM_Vec3.
   *
   * @param peer a {@link com.sun.jna.Pointer} object.
   */
  public OpenMM_Vec3(Pointer peer) {
    super(peer);
  }

  public static class ByReference extends OpenMM_Vec3 implements Structure.ByReference {};

  public static class ByValue extends OpenMM_Vec3 implements Structure.ByValue {};
}
