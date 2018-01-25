/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.inacap.mascoteria2018.utils;

/**
 *
 * @author alumnossur
 */
public class NumberUtils {

    public static boolean isNumber(String s_int) {
        try {
            Integer.parseInt(s_int);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
