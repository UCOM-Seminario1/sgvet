package com.sgvet.rrhh.control;

import com.sgvet.rrhh.boundary.RRHHRepository;

public class RRHHControllerTestable extends RRHHController {

    public RRHHControllerTestable(RRHHRepository rrhhRepository) {
        try {
            java.lang.reflect.Field field = RRHHController.class.getDeclaredField("rrhhRepository");
            field.setAccessible(true);
            field.set(this, rrhhRepository);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

