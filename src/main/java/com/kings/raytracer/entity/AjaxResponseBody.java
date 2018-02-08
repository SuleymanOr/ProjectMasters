package com.kings.raytracer.entity;

import java.util.List;

public class AjaxResponseBody {

    String msg;
    RayTracingImage result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RayTracingImage getResult() {
        return result;
    }

    public void setResult(RayTracingImage result) {
        this.result = result;
    }
}
