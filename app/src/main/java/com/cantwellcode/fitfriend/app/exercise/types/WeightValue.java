package com.cantwellcode.fitfriend.app.exercise.types;

/**
 * Created by Daniel on 6/27/2014.
 */
public class WeightValue implements Value {

    private float data;
    private String unit;
    private String label;

    public WeightValue() {}

    public WeightValue(float data) {
        this.data = data;
    }

    @Override
    public void setData(String data) {
        this.data = Float.valueOf(data);
    }

    @Override
    public String getDataDisplayString() {
            return Float.toString(data);
    }

    @Override
    public String getUnitDisplayString() {
        return "lbs";
    }

    @Override
    public String getLabelDisplayString() {
        // Sets with "Weight" values do not have a label
        return null;
    }
}