package com.kings.raytracer.controller;


//import java.util.stream.Collectors;

import RayTraceAlgo.*;
import com.kings.raytracer.entity.AjaxResponseBody;
import com.kings.raytracer.entity.RayTracingImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;


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

        Sphere sphere = new Sphere(new Point3D(0.0,0.0,0.0), data.getRadius(), new Colour(1.0F, 0.0F, 0.0F));

        for(int y = 0; y < 480; y++){

            for(int x = 0; x < 640; x++){
                //buffer.setRGB(j,i,1000);

                //creating new colour for every pixel in the image
                Colour colour = new Colour(0.0F, 0.0F, 0.0F);

                //creating and 8x8 grid
                for (int row =0; row < 8; row++)
                {
                    for (int col = 0; col < 8; col++)
                    {
                        //sampling each column(x) and each row(y) by taking the center of grid and dividing by 8
                        Ray ray = new Ray(new Point3D(x-640/2 + (col+0.5)/8, y-480/2 + (row+0.5)/8, 100), new Vector3D(0.0,0.0,-1.0));

                        if(sphere.hit(ray) != 0.0){
                            //if ray intersects sphere, adding that colour to the colour object created above
                            colour.add(sphere.colour);
                        }
                        else {}
                    }
                }
                //dividing by total number of samples (8x8) to get average colour
                colour.divide(64);
                //setting buffer to that colour
                buffer.setRGB(x,y, colour.toInteger());
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

        return ResponseEntity.ok(Base64.getEncoder().encodeToString(image.toByteArray()));

    }

    Sphere sphere = new Sphere(new Point3D(0.0,0.0,0.0), 70.0, new Colour(1.0F, 0.0F, 0.0F));

    @RequestMapping(value = "/image", method = RequestMethod.GET, produces = "image/png")
    public @ResponseBody byte[] getFile(){
        ByteArrayOutputStream image = new ByteArrayOutputStream();
        BufferedImage buffer = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);

        for(int y = 0; y < 480; y++){
            for(int x = 0; x < 640; x++){
                Ray ray = new Ray(new Point3D(x-640/2 + 0.5, y-480/2 + 0.5, 100), new Vector3D(0.0,0.0,-1.0));
                // point x value is an orthographic projection, z is 100 units away from the origin
                if(sphere.hit(ray) != 0.0){
                    buffer.setRGB(x,y, sphere.colour.toInteger());
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
