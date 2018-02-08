package com.kings.raytracer.controller;


//import java.util.stream.Collectors;

import com.kings.raytracer.entity.AjaxResponseBody;
import com.kings.raytracer.entity.RayTracingImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


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
            @Valid @RequestBody RayTracingImage image, Errors errors) {

        AjaxResponseBody result = new AjaxResponseBody();

        //If error, just return a 400 bad request, along with the error message
        if (errors.hasErrors()) {

            result.setMsg("error!!!");

            return ResponseEntity.badRequest().body(result);

        }

        if (image.getSize().isEmpty()) {
            result.setMsg("We have received nothing!");
        } else {
            result.setMsg("Success");
        }
        result.setResult(image);

        return ResponseEntity.ok(result);

    }
}