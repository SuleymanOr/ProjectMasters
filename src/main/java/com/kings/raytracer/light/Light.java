package com.kings.raytracer.light;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.kings.raytracer.geometry.Sphere;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Sphere.class, name = "LightDirected" ),
})
public interface Light {

    double[] getAmountOfLight(double[] point);

    double[] getVectorToLight(double[] pointOfIntersection) throws Exception;

    double[] getPosition();

    void setPosition(double[] position);

    double[] getColor();

    void setColor(double[] color);
}
