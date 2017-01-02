package de.randomerror.fh.database;

/**
 * Created by Henri on 02.01.17.
 */
public class DistrictInfo {
    private int numProvider;
    private int numDeliveries;
    private int averageOrderValue;

    public DistrictInfo(int numProvider, int numDeliveries, int averageOrderValue) {
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

    public int getAverageOrderValue() {
        return averageOrderValue;
    }
}
