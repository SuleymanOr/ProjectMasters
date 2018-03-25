package com.kings.raytracer.service;

import com.kings.raytracer.auxiliary.Camera;
import com.kings.raytracer.utility.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ImageRender {

    @Autowired
    RayTracer rayTracer;

    public byte[] renderImage(Scene scene, Camera camera){

        ByteArrayOutputStream image = new ByteArrayOutputStream();

        try {
            ImageIO.write(rayTracer.renderImage(scene, camera), "PNG", image);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return image.toByteArray();

    }
}

