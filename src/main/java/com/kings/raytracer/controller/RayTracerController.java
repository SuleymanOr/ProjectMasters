package com.kings.raytracer.controller;

import com.kings.raytracer.entity.RayTracingImage;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class RayTracerController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/rayTracer")
    public RayTracingImage initialise(@RequestParam(value="name", defaultValue="World") String name) {
        return new RayTracingImage(counter.incrementAndGet(), String.format(template, name));
    }

    @RequestMapping(value = "/image", method = RequestMethod.GET, produces = "image/png")
    public @ResponseBody byte[] getFile(){
        ByteArrayOutputStream image = new ByteArrayOutputStream();
        BufferedImage buffer = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);

        for(int i = 0; i < 480; i++){
            for(int j = 0; j < 640; j++){
                buffer.setRGB(j,i,1000);
            }
        }

        try {
            ImageIO.write(buffer, "PNG", image);
        }catch (IOException ioe){
            System.out.println(ioe.getMessage());
        }
        return image.toByteArray();
    }
}

