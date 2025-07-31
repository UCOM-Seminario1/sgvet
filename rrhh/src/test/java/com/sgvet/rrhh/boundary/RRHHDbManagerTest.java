package com.sgvet.rrhh.boundary;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;

public class RRHHDbManagerTest {

    @Test
    public void testSingletonInstance() {
        RRHHDbManager instance1 = RRHHDbManager.getInstance();
        RRHHDbManager instance2 = RRHHDbManager.getInstance();
        assertNotNull(instance1);
        assertEquals(instance1, instance2);
    }

    @Test
    public void testConnectionNotNull() {
        Connection conn = RRHHDbManager.getConnection();
        assertNotNull(conn);
    }

    @Test
    public void testConnectionClosed() {
        Connection conn = RRHHDbManager.getConnection();
        try {
            conn.close();
            assertTrue(conn.isClosed());
        } catch (Exception e) {
            System.out.println("Connection should be closed: " + e.getMessage());
        }
    }

   
}