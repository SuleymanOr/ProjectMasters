package com.kings.raytracer.controller;


//import java.util.stream.Collectors;

import RayTraceAlgo.Point3D;
import RayTraceAlgo.Ray;
import RayTraceAlgo.Sphere;
import RayTraceAlgo.Vector3D;
import com.kings.raytracer.entity.AjaxResponseBody;
import com.kings.raytracer.entity.RayTracingImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


@RestController
public class RayTracerController {
    /*private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();*/

//    @RequestMapping("/rayTracer")
//    public RayTracingImage initialise(@RequestParam(value="name", defaultValue="World") String name) {
//        return new RayTracingImage(counter.incrementAndGet(),
//                String.format(template, name));
//    }


    @PostMapping("/api/rayTracerJson")
    public ResponseEntity<?> getSearchResultViaAjax(
            @Valid @RequestBody RayTracingImage data, Errors errors) {

        AjaxResponseBody result = new AjaxResponseBody();

        ByteArrayOutputStream image = new ByteArrayOutputStream();
        BufferedImage buffer = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);

        java.awt.Color color = new java.awt.Color(1.0F, 0.0F, 0.0F);
        Sphere sphere = new Sphere(new Point3D(0.0,0.0,0.0), data.getRadius(), color);

        for(int y = 0; y < 480; y++){
            for(int x = 0; x < 640; x++){
                Ray ray = new Ray(new Point3D(x-640/2 + 0.5, y-480/2 + 0.5, 100), new Vector3D(0.0,0.0,-1.0));
                // point x value is an orthographic projection, z is 100 units away from the origin
                if(sphere.hit(ray) != 0.0){
                    buffer.setRGB(x,y, sphere.color.getRGB());
                }
                else
                    buffer.setRGB(x,y, 0);
            }
        }

        try {
            ImageIO.write(buffer, "PNG", image);
        }catch (IOException ioe){
            System.out.println(ioe.getMessage());
        }


        //return image.toByteArray(); // image



        //If error, just return a 400 bad request, along with the error message
        if (errors.hasErrors()) {

            result.setMsg("error!!");

            return ResponseEntity.badRequest().body(result);

        }

//        if (image.getSize().isEmpty()) {
//            result.setMsg("We have received nothing!");
//        } else {
//            result.setMsg("Success");
//        }
        result.setResult(data);

        return ResponseEntity.ok(image.toByteArray());

    }

    java.awt.Color color = new java.awt.Color(1.0F, 0.0F, 0.0F);
    Sphere sphere = new Sphere(new Point3D(0.0,0.0,0.0), 70.0, color);

    @RequestMapping(value = "/image", method = RequestMethod.GET, produces = "image/png")
    public @ResponseBody byte[] getFile(){
        ByteArrayOutputStream image = new ByteArrayOutputStream();
        BufferedImage buffer = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);

        for(int y = 0; y < 480; y++){
            for(int x = 0; x < 640; x++){
                //buffer.setRGB(j,i,1000);
                Ray ray = new Ray(new Point3D(x-640/2 + 0.5, y-480/2 + 0.5, 100), new Vector3D(0.0,0.0,-1.0));
                // point x value is an orthographic projection, z is 100 units away from the origin
                if(sphere.hit(ray) != 0.0){
                    buffer.setRGB(x,y, sphere.color.getRGB());
                }
                else
                    buffer.setRGB(x,y, 0);
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