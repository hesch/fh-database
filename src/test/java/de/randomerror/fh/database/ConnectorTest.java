package de.randomerror.fh.database;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Henri on 02.01.17.
 */
public class ConnectorTest {

    private Connector connector;

    @Before
    public void before() {
        connector = new Connector();
        connector.connect();
    }

    @Test
    public void returnsTheRightNumberOfProviders() {
        int numProviders = connector.numProvidersInDistrict("39846");
        Assert.assertEquals(2, numProviders);

        numProviders = connector.numProvidersInDistrict("39000");
        Assert.assertEquals(0, numProviders);
    }

    @Test
    public void returnsTheRightAverageOrderSumOfDistrict() {
        double average = connector.averageOrderValueInDistrict("39850");
        Assert.assertEquals(39.66666666, average, 0.001);

        average = connector.numProvidersInDistrict("39000");
        Assert.assertEquals(0, average, 0.001);
    }
}
