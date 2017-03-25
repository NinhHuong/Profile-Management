package com.quocngay.profilemanagement.other;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by ninh huong on 3/24/2017.
 */

public class Constanst {
    public static int KEY_ROLL_TEACHER = 2;
    public static int KEY_ROLL_STUDENT = 1;
    public static int KEY_ROLL_ADMIN = 3;
    public static int KEY_GENDER_MALE = 1;
    public static int KEY_GENDER_FEMALE = 0;

    public static DateFormat KEY_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public static int getKeyRollTeacher() {
        return KEY_ROLL_TEACHER;
    }

    public static int getKeyRollStudent() {
        return KEY_ROLL_STUDENT;
    }
}
