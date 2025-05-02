package ru.mentee.power.variables;

public class TypeCalculator {

  public static void main(String[] args) {
    byte b = 1;
    short s = 2;
    int i = 3;
    long l = 4;
    float f = 5.1234f;
    double d = 5.5;

    System.out.println("byte (" + b + ") + short (" + s + ") = " + (b + s) + " (int)");

    System.out.println("int (" + i + ") * byte (" + b + ") = " + (i * b) + " (int)");

    System.out.println("double (" + d + ") + int (" + i + ") = " + (d + i) + " (double)");

    System.out.println("int (" + i + ") / byte (" + b + ") = " + (i / b) + " (int)");

    System.out.println("double (" + d + ") / byte (" + b + ") = " + (d / b) + " (double)");

    System.out.println("float (" + f + ") + long (" + l + ") = " + (f + l) + " (float)");

    System.out.println("long (" + l + ") * double (" + d + ") = " + (l * d) + " (double)");

  }
}
