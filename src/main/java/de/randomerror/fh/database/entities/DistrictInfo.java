package de.randomerror.fh.database.entities;

/**
 * Class for representing metadata of a district
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
