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
    }

    @Test
    public void connectorCallsConnectSuccessfully() {
        connector.connect();
    }

    @Test
    public void stuff() {
        connector.connect();
        int numProviders = connector.numProvidersInDistrict("39846");
        Assert.assertEquals(2, numProviders);
    }
}
