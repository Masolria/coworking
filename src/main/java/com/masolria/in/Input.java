package com.masolria.in;

import java.util.Scanner;

/**
 * The Input class manages console input.
 */
public class Input {
    /**
     * The Scanner field which is in use for console input.
     */
    Scanner scanner = new Scanner(System.in);

    /**
     * Obtain console input string and return it.
     *
     * @return the input string
     */
    public String input(){
       return scanner.nextLine();
    }
}
