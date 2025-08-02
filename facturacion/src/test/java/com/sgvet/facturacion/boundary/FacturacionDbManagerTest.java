package com.sgvet.facturacion.boundary;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;

public class FacturacionDbManagerTest {

    @Test
    public void testSingletonInstance() {
        FacturacionDbManager instance1 = FacturacionDbManager.getInstance();
        FacturacionDbManager instance2 = FacturacionDbManager.getInstance();
        Assert.assertNotNull(instance1);
        Assert.assertSame(instance1, instance2);
    }

    @Test
    public void testConnectionNotNull() {
        Connection conn = FacturacionDbManager.getConnection();
        Assert.assertNotNull(conn);
    }
}