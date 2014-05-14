package com.cantwellcode.athletejournal;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 4/27/2014.
 */

@ParseClassName("ExercisePost")
public class ExercisePost extends ParseObject {

    private String type;
    private String intensity;
    private String dateOf;
    private String timeOf;
    private String duration;
    private String location;
    private String distance;

    private List<User> attending;


}
