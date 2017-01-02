package de.randomerror.fh.database.entities;

/**
 * Created by Henri on 02.01.17.
 */
public class DistrictInfo {
    private int numProvider;
    private int numDeliveries;
    private double averageOrderValue;

    public DistrictInfo(int numProvider, int numDeliveries, double averageOrderValue) {
        this.numProvider = numProvider;
        this.numDeliveries = numDeliveries;
        this.averageOrderValue = averageOrderValue;
    }

    public int getNumProvider() {
        return numProvider;
    }

    public int getNumDeliveries() {
        return numDeliveries;
    }

    public double getAverageOrderValue() {
        return averageOrderValue;
    }
}
