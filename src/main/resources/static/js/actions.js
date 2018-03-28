var id_increment  = 1;
var scale = 100.0;

function refreshList(shapeList,lightList){
    $("#shape-list").html("");
    shapeList.forEach(function(e){
        var element = "<div class='shape-item' key="+ e.id.toString() +"><span class='shape-item-name'>"+ e.name +"</span><span class='shape-item-edit'>/</span><span class='shape-item-delete'>x</span></div>"
        $("#shape-list").append(element);
    });
    $("#light-list").html("");
    lightList.forEach(function(e){
        var element = "<div class='light-item' key="+ e.id.toString() +"><span class='light-item-name'>"+ e.name +"</span><span class='light-item-edit'>/</span><span class='light-item-delete'>x</span></div>"
        $("#light-list").append(element);
    });
};

//convert degree to radians
function toRad (angle) {
    return angle * (Math.PI / 180);
}

//calculate a point x,y,z, after rotating a,b,c and translating p,q,m
function pointConvert(x,y,z,a,b,c,p,q,m){
    //rotate a units around x
    yp = y*Math.cos(a) - z*Math.sin(a);
    zp = y*Math.sin(a) + z*Math.cos(a);
    y = yp;
    z = zp;
    //rotate b units around y
    zp = z*Math.cos(b) - x*Math.sin(b);
    xp = z*Math.sin(b) + x*Math.cos(b);
    z = zp;
    x = xp;
    //rotate c units around z
    xp = x*Math.cos(c) - y*Math.sin(c);
    yp = x*Math.sin(c) + y*Math.cos(c);
    x = xp;
    y = yp;
    //translate
    x = x + p;
    y = y + q;
    z = z + m;
    return [x/scale,y/scale,z/scale];
};

// calculate rotation around axises , assuming that the shape is symmetric in respect to Y axis (like cylinder)
function dirToAngles(direction,type) {
    angels = [];
    if(type === "Disc" ) {
        angels[0] = Math.atan(1.0*direction[1]/direction[2]);
        if(direction[1] === 0) angels[0] = 0;
        angels[1] = Math.atan(1.0*direction[0]/direction[2]);
        if(direction[0] === 0) angels[1] = 0;
        angels[2] = 0;
    }else{
        angels[0] = Math.atan(1.0*direction[2]/direction[1]);
        if(direction[2] === 0) angels[0] = 0;
        angels[1] = 0;
        angels[2] = Math.atan(1.0 * direction[0] / direction[1]);
        if(direction[0] === 0) angels[2] = 0;
    }

    return angels;
}

function Light(id,name,type,color,x,y,z){
    this.id = id;
    this.name = name;
    this.type = type;
    this.color = color;
    this.x = x;
    this.y = y;
    this.z = z;

    this.toString = function () {
        return "id :" + this.id + " name: " + this.name;
    };
    this.toJsonForRaytracer= function () {
        color =  new THREE.Color(this.color);
        return {"type" : "Light", "direction" : [x,y,z],"color" : color.toArray()};
    };
}

function Shape(id,name,type,color,x,y,z,direction,surface,reflect){
    this.id = id;
    this.name = name;
    this.type = type;
    this.color = color;
    this.x = x;
    this.y = y;
    this.z = z;
    this.direction = direction;
    this.surfaceType = surface;
    this.reflectance = reflect;
    this.ambient = [0.5,0.5,0.5];
    this.shininess = 20;
    this.emission = [0,0,0];
    this.checkersDiffuse1 = [1,1,1];
    this.checkersDiffuse2 = [0.1,0.1,0.1];
    this.specular = [0.7,0.7,0.7];
    this.toString = function () {
        return "id :" + this.id + " name: " + this.name;
    };
    this.shapeToJsonForRaytracer= function () {
        color =  new THREE.Color(this.color);
        return {"type" : this.type, "center" : [x/scale,y/scale,z/scale], "direction" : direction, "diffuse" : color.toArray(),"surfaceType" : this.surfaceType, "reflectance" : this.reflectance,  "ambient" : this. ambient, "shininess" : this.shininess, "emission" : this.emission, "checkersDiffuse1" : this.checkersDiffuse1, "checkersDiffuse2" : this.checkersDiffuse2, "specular" : this.specular};
    };
}



function Sphere (id,name,type,color,x,y,z,radius,direction,surface,reflect){
    Shape.call(this,id,name,type,color,x,y,z,direction,surface,reflect);
    this.radius = radius;
    this.toJsonForRaytracer = function () {
        shape = this.shapeToJsonForRaytracer();
        return Object.assign(shape,{"radius" : radius/scale});
    };
}

function Cube (id,name,type,color,x,y,z,w,h,d,height,direction,surface,reflect){
    Shape.call(this,id,name,type,color,x,y,z,height,direction,surface,reflect);
    this.w = w;
    this.d = d;
    this.h = h;
    this.toJsonForRaytracer = function () {
        shape = this.shapeToJsonForRaytracer();
        return Object.assign(shape,{"p0" : pointConvert(-w/2,-h/2,-d/2,toRad(shape.direction[0]),toRad(shape.direction[1]),toRad(shape.direction[2]),x,y,z),
                                    "p1" : pointConvert(w/2,-h/2,-d/2,toRad(shape.direction[0]),toRad(shape.direction[1]),toRad(shape.direction[2]),x,y,z),
                                    "p2" : pointConvert(-w/2,-h/2,d/2,toRad(shape.direction[0]),toRad(shape.direction[1]),toRad(shape.direction[2]),x,y,z),
                                    "p3" : pointConvert(-w/2,h/2,-d/2,toRad(shape.direction[0]),toRad(shape.direction[1]),toRad(shape.direction[2]),x,y,z)});
    };
    
}

function Cylinder (id,name,type,color,x,y,z,radius,height,direction,surface,reflect){
    Shape.call(this,id,name,type,color,x,y,z,direction,surface,reflect);
    this.radius = radius;
    this.height = height;
    this.toJsonForRaytracer = function () {
        shape = this.shapeToJsonForRaytracer();
        var normal = pointConvert(0,100,0,toRad(direction[0]),toRad(direction[1]),toRad(direction[2]),0,0,0);
        return Object.assign(shape,{"radius" : radius/scale, "length" : height/scale, "direction" : normal});
    };
}

function Cone (id,name,type,color,x,y,z,radius,height,direction,surface,reflect){
    Shape.call(this,id,name,type,color,x,y,z,direction,surface,reflect);
    this.radius = radius;
    this.height = height;
    this.toJsonForRaytracer = function () {
        shape = this.shapeToJsonForRaytracer();
        aa= (radius*1.0);
        bb = (height*1.0);
        cc = (radius*1.0)/(height*1.0);
        dd = Math.atan((radius*1.0)/(height*1.0));
        return Object.assign(shape,{"start" : shape.center, "height" : height/scale, "angel" : Math.atan((radius*1.0)/(height*1.0))});
    };
}


function Torus (id, name, type, color, x, y, z, radius, tube_radius, direction,surface,reflect){
    Shape.call(this,id,name,type,color,x,y,z, direction,surface,reflect);
    this.radius = radius;
    this.tube_radius = tube_radius;
    this.toJsonForRaytracer = function () {
        shape = this.shapeToJsonForRaytracer();
        var normal = pointConvert(0,0,100,toRad(direction[0]),toRad(direction[1]),toRad(direction[2]),0,0,0);
        return Object.assign(shape, {"centralRadius" : this.radius / scale, "tubeRadius" : this.tube_radius / scale, "normal" : normal});
    }
}

function Plane (id,name,type,color,x,y,z,w,h,direction,surface,reflect){
    Shape.call(this,id,name,type,color,x,y,z,direction,surface,reflect);
    this.width = w;
    this.height = h;
    this.toJsonForRaytracer = function () {
        shape = this.shapeToJsonForRaytracer();
        return Object.assign(shape,{"point0" : pointConvert(-w/2,-h/2,0,toRad(shape.direction[0]),toRad(shape.direction[1]),toRad(shape.direction[2]),x,y,z),"point1" : pointConvert(w/2,z-h/2,0,toRad(shape.direction[0]),toRad(shape.direction[1]),toRad(shape.direction[2]),x,y,z),"point2" : pointConvert(-w/2,h/2,0,toRad(shape.direction[0]),toRad(shape.direction[1]),toRad(shape.direction[2]),x,y,z)});
    };
}

function Disc (id,name,type,color,x,y,z,radius,direction,surface,reflect){
    Shape.call(this,id,name,type,color,x,y,z,direction,surface,reflect);
    this.radius = radius;
    this.toJsonForRaytracer = function () {
        shape = this.shapeToJsonForRaytracer();
        var normal = pointConvert(0,0,100,direction[0],direction[1],direction[2],0,0,0);
        return Object.assign(shape,{"radius" : radius/scale, "normal" : normal});
    };
}

function Triangle (id,name,type,color,x,y,z,a,b,c,p,q,m,direction,surface,reflect){
    Shape.call(this,id,name,type,color,x,y,z,direction,surface,reflect);
    this.p0 = [x/scale,y/scale,z/scale];
    this.p1 = [a/scale,b/scale,c/scale];
    this.p2 = [p/scale,q/scale,m/scale];
    this.toJsonForRaytracer = function () {
        shape = this.shapeToJsonForRaytracer();
        return Object.assign(shape,{"point0" : this.p0, "point1" : this.p1, "point2" : this.p2});
    };
}

function LocalScene(scene,camera,ambient,background){
    this.shapes = [];
    this.lights = [];
    this.live_scene = scene;
    this.ambient = ambient;
    this.background = background;
    this.addShape = function (type) {
        type = type.toLowerCase();
        var shape = {};
        shape.name = $("#new-"+type+"-name").val();
        shape.color = parseInt($("#new-"+type+"-color").val(),16);
        shape.x = parseInt($("#new-"+type+"-x").val(),10);
        shape.y = parseInt($("#new-"+type+"-y").val(),10);
        shape.z = parseInt($("#new-"+type+"-z").val(),10);
        shape.surface = $("#new-"+type+"-surface").val();
        shape.reflect = parseInt($("#new-"+type+"-reflect").val(),10)/scale;
        if(!(type === "sphere")){
            shape.direction = [];
            shape.direction[0] = parseInt($("#new-"+type+"-direction-x").val(),10);
            shape.direction[1] = parseInt($("#new-"+type+"-direction-y").val(),10);
            shape.direction[2] = parseInt($("#new-"+type+"-direction-z").val(),10);
        }
        return shape;
    }
    this.addSphere = function () {
        // Adding rhe shape to local shape list
        var id = id_increment;
        id_increment +=1;
        var type = "Sphere";
        var shape =  this.addShape(type);
        var radius =  parseInt($("#new-sphere-radius").val(),10);
        this.shapes[id] = new Sphere(id,shape.name,type,shape.color,shape.x,shape.y,shape.z,radius,shape.direction,shape.surface,shape.reflect);
        // Adding the shape to three.js scene
        var geometry = new THREE.SphereBufferGeometry( radius, 20, 20 );
        var material = new THREE.MeshLambertMaterial( { color: shape.color , wireframe: true} );
        var sphere = new THREE.Mesh( geometry, material );
        sphere.name = id;
        sphere.position.set(shape.x,shape.y,shape.z);
        scene.add( sphere );
    };
    this.addCube = function () {
        var id = id_increment;
        id_increment +=1;
        var type = "Cube";
        var shape =  this.addShape(type);
        var w = parseInt($("#new-cube-w").val(),10);
        var d = parseInt($("#new-cube-d").val(),10);
        var h = parseInt($("#new-cube-h").val(),10);
        this.shapes[id] = new Cube(id,shape.name,type,shape.color,shape.x,shape.y,shape.z,w,h,d,shape.direction,shape.surface,shape.reflect);
        var material = new THREE.MeshLambertMaterial( { color: shape.color , wireframe: true} );
        var geometry = new THREE.BoxGeometry( w, h , d, 5, 5 ,5);
        var box = new THREE.Mesh( geometry, material );
        box.name = id;
        box.position.set(shape.x,shape.y,shape.z);
        scene.add( box );
    };

    this.addCylinder = function () {
        var id = id_increment;
        id_increment +=1;
        var type = "Cylinder";
        var shape =  this.addShape(type);
        var radius = parseInt($("#new-cylinder-radius").val(),10);
        var height = parseInt($("#new-cylinder-height").val(),10);
        this.shapes[id] = new Cylinder(id,shape.name,type,shape.color,shape.x,shape.y,shape.z,radius,height,shape.direction,shape.surface,shape.reflect);
        var material = new THREE.MeshLambertMaterial( { color: shape.color , wireframe: true} );
        var geometry = new THREE.CylinderBufferGeometry( radius,radius, height, 20, 10 );
        var cylinder = new THREE.Mesh( geometry, material );
        cylinder.rotateOnAxis(new THREE.Vector3(1,0,0), toRad(shape.direction[0]));
        cylinder.rotateOnAxis(new THREE.Vector3(0,1,0), toRad(shape.direction[1]));
        cylinder.rotateOnAxis(new THREE.Vector3(0,0,1), toRad(shape.direction[2]));
        cylinder.name = id;
        cylinder.position.set(shape.x,shape.y,shape.z);
        scene.add( cylinder );
    };

    this.addCone = function () {
        var id = id_increment;
        id_increment +=1;
        var type = "Cone";
        var shape =  this.addShape(type);
        var radius = parseInt($("#new-cone-radius").val(),10);
        var height = parseInt($("#new-cone-height").val(),10);
        this.shapes[id] = new Cone(id,shape.name,type,shape.color,shape.x,shape.y,shape.z,radius,height,shape.direction,shape.surface,shape.reflect);
        var geometry = new THREE.ConeBufferGeometry( 5, 20, 32 );
        var material = new THREE.MeshBasicMaterial( { color: shape.color , wireframe: true} );
        var cone = new THREE.Mesh( geometry, material );
        scene.add( cone );
        cone.name = id;
        cone.position.set(shape.x,shape.y,shape.z);
        scene.add( cone );
    };

    this.addTorus = function () {
        var id = id_increment;
        id_increment +=1;
        var type = "Torus";
        var shape =  this.addShape(type);
        var radius = parseInt($("#new-torus-radius").val(),10);
        var tube_radius = parseInt($("#new-torus-tube-radius").val(),10);
        this.shapes[id] = new Torus(id,shape.name,type,shape.color,shape.x,shape.y,shape.z,radius,tube_radius, shape.direction,shape.surface,shape.reflect);
        var material = new THREE.MeshLambertMaterial( { color: shape.color , wireframe: true} );
        var geometry = new THREE.TorusBufferGeometry( radius,tube_radius, 10, 20 );
        var torus = new THREE.Mesh( geometry, material );
        torus.rotateOnAxis(new THREE.Vector3(1,0,0), toRad(shape.direction[0]));
        torus.rotateOnAxis(new THREE.Vector3(0,1,0), toRad(shape.direction[1]));
        torus.name = id;
        torus.position.set(shape.x,shape.y,shape.z);
        scene.add( torus );
    };

    this.addPlane = function () {
        var id = id_increment;
        id_increment +=1;
        var type = "Plane";
        var shape =  this.addShape(type);
        var w = parseInt($("#new-plane-w").val(),10);
        var h = parseInt($("#new-plane-h").val(),10);
        this.shapes[id] = new Plane(id,shape.name,type,shape.color,shape.x,shape.y,shape.z,w,h,shape.direction,shape.surface,shape.reflect);
        var material = new THREE.MeshLambertMaterial( { color: shape.color , wireframe: true} );
        var geometry = new THREE.PlaneBufferGeometry( w,h,10,10 );
        var plane = new THREE.Mesh( geometry, material );
        // plane.rotation.x = toRad(90);
        plane.rotateOnAxis(new THREE.Vector3(1,0,0), toRad(shape.direction[0]));
        plane.rotateOnAxis(new THREE.Vector3(0,1,0), toRad(shape.direction[1]));
        plane.rotateOnAxis(new THREE.Vector3(0,0,1), toRad(shape.direction[2]));
        plane.name = id;
        plane.position.set(shape.x,shape.y,shape.z);
        scene.add( plane );
    };

    this.addDisc = function () {
        var id = id_increment;
        id_increment +=1;
        var type = "Disc";
        var shape =  this.addShape(type);
        var radius = parseInt($("#new-disc-radius").val(),10);
        this.shapes[id] = new Disc(id,shape.name,type,shape.color,shape.x,shape.y,shape.z,radius,shape.direction,shape.surface,shape.reflect);
        var material = new THREE.MeshLambertMaterial( { color: shape.color , wireframe: true} );
        var geometry = new THREE.CircleBufferGeometry( radius, 20 );
        var disc = new THREE.Mesh( geometry, material );
        disc.rotateOnAxis(new THREE.Vector3(1,0,0), toRad(shape.direction[0]));
        disc.rotateOnAxis(new THREE.Vector3(0,1,0), toRad(shape.direction[1]));
        disc.name = id;
        disc.position.set(shape.x,shape.y,shape.z);
        scene.add( disc );
    };

    this.addTriangle = function () {
        var id = id_increment;
        id_increment +=1;
        var type = "Triangle";
        var shape =  this.addShape(type);
        var a = parseInt($("#new-triangle-a").val(),10);
        var b = parseInt($("#new-triangle-b").val(),10);
        var c = parseInt($("#new-triangle-c").val(),10);
        var p = parseInt($("#new-triangle-p").val(),10);
        var q = parseInt($("#new-triangle-q").val(),10);
        var m = parseInt($("#new-triangle-m").val(),10);
        this.shapes[id] = new Triangle(id,shape.name,type,shape.color,shape.x,shape.y,shape.z,a,b,c,p,q,m,shape.direction,shape.surface,shape.reflect);

        var geom = new THREE.Geometry();
        var v1 = new THREE.Vector3(shape.x,shape.y,shape.z);
        var v2 = new THREE.Vector3(a,b,c);
        var v3 = new THREE.Vector3(p,q,m);
        var triangle = new THREE.Triangle( v1, v2, v3 );
        var normal = triangle.normal();

        // An example of getting the area from the Triangle class
        console.log( 'Area of triangle is: '+ triangle.area() );

        geom.vertices.push(triangle.a);
        geom.vertices.push(triangle.b);
        geom.vertices.push(triangle.c);
        geom.faces.push( new THREE.Face3( 0, 1, 2, normal ) );
        var material = new THREE.MeshLambertMaterial( { color: shape.color , wireframe: true} );
        var tri= new THREE.Mesh( geom, material );
        tri.name = id;
        scene.add( tri );
    };

    this.addLight = function () {
        var id = id_increment;
        id_increment +=1;
        var name = $("#new-light-name").val();
        var type = "Light";
        var color = parseInt($("#new-light-color").val(),16);
        var x = parseInt($("#new-light-x").val(),10);
        var y = parseInt($("#new-light-y").val(),10);
        var z = parseInt($("#new-light-z").val(),10);
        this.lights[id] = new Light(id,name,type,color,x,y,z);
        var light = new THREE.DirectionalLight( color,2 );
        light.position.set( x, y, z );
        scene.add( light );
        light.name = id;
        light.position.set(-x,-y,-z);
        scene.add( light );
    };


    this.removeShape = function (id) {
        var tmp = this.live_scene.getObjectByName(id);
        this.live_scene.remove(tmp);
        delete this.shapes[id];
    };

    this.removeLight = function (id) {
        var tmp = this.live_scene.getObjectByName(id);
        this.live_scene.remove(tmp);
        delete this.lights[id];
    };

    this.toJsonForRaytracer = function(){
      var data = {};
      var backScene = {};
      data.backgroundColor = this.background.toArray();
      data.ambientLight = this.ambient.color.toArray();
      // backScene.superSampleValue = 1;
      // backScene.screenWidth = 1280;
      // backScene.screenHeight = 800;
      data.superSampleValue = 1;
      data.screenWidth = parseInt($("#output-width").val(),10);
      data.screenHeight = parseInt($("#output-height").val(),10);;
      data.figures = this.shapes.map(function(item){return item.toJsonForRaytracer()});
      data.figures = data.figures.filter(function(n){ return n != undefined });
      data.lights = this.lights.map(function(item){return item.toJsonForRaytracer()});
      data.lights = data.lights.filter(function(n){ return n != undefined });
      data.scene = backScene;
      data.camera = {"eye": [camera.position.x/scale,camera.position.y/scale,camera.position.z/scale],"lookAt":[0,0,0],"upDirection":[0,1,0],"screenDist":1,"screenWidth":2};
      return data;
    };
};

$(document).ready(function () {
    var scene = new THREE.Scene();
    var camera = new THREE.PerspectiveCamera( 55, 2, 0.1, 1000 );

    camera.position.y=50;
    var ambientInitial = 0x555555;
    var backgroundInitial = 0x000000;

    var renderer = new THREE.WebGLRenderer();
    renderer.setSize( $("#canvas-container").width(), ($("#canvas-container").width())/2 );
    document.getElementById('canvas-container').appendChild( renderer.domElement );
    scene.background =  new THREE.Color(backgroundInitial);

    var ambientLight = new THREE.AmbientLight( ambientInitial ,1);
    scene.add( ambientLight );

    var controls = new THREE.OrbitControls( camera, document.getElementById("canvas-container") );
    controls.update();

    //scene controls and axis helper

    $("#scene-background").change(function(){
        scene.background.set(parseInt($("#scene-background").val(),16));
    });
    $("#ambient-light").change(function(){
        ambientLight.color.set(parseInt($("#ambient-light").val(),16));
    });
    // helper renderer
    renderer2 = new THREE.WebGLRenderer();
    renderer2.setSize( $("#axis-helper").width(), ($("#axis-helper").width()) );
    document.getElementById('axis-helper').appendChild( renderer2.domElement );

    scene2 = new THREE.Scene();
    scene2.background = new THREE.Color(0x333333);

    // camera
    camera2 = new THREE.PerspectiveCamera( 50, 1, 1, 1000 );
    camera2.up = camera.up; // important!
    camera2.position.y=15;
    // axes
    axes2 = new THREE.AxisHelper( 5 );
    scene2.add( axes2 );

    //  render update function
    var render = function () {
        requestAnimationFrame( render );

        // cube.rotation.x += 0.005;
        // cube.rotation.y += 0.008;

        // // Move the camera in a circle with the pivot point in the centre of this circle...
        controls.update();
        camera2.position.copy( camera.position );
        camera2.position.sub( controls.target ); // added by @libe
        camera2.position.setLength( 15);
        camera2.lookAt( scene2.position );

        renderer2.render( scene2, camera2 );
        renderer.render(scene, camera);
    };

    render();

    var local_scene = new LocalScene(scene,camera,ambientLight,scene.background);

    $(".shape-add-button").click(function (event) {
        var id =  $(this).attr("id");
        switch(id){
            case "add-sphere":
                local_scene.addSphere();
                break;
            case "add-cube":
                local_scene.addCube();
                break;
            case "add-cylinder":
                local_scene.addCylinder();
                break;
            case "add-cone":
                local_scene.addCone();
                break;
            case "add-torus":
                local_scene.addTorus();
                break;
            case "add-plane":
                local_scene.addPlane();
                break;
            case "add-disc":
                local_scene.addDisc();
                break;
            case "add-triangle":
                   local_scene.addTriangle();
                break;
            case "add-light":
                local_scene.addLight();
                break;
            default:
                alert("Unknown Object!");
        }
        refreshList(local_scene.shapes,local_scene.lights);
        console.log(camera);
    });

    $("#shape-list").on("click",".shape-item-delete",(function () {
        local_scene.removeShape(parseInt($(this).parent().attr("key"),10));
        refreshList(local_scene.shapes,local_scene.lights);
    }));
    $("#light-list").on("click",".light-item-delete",(function () {
        local_scene.removeLight(parseInt($(this).parent().attr("key"),10));
        refreshList(local_scene.shapes,local_scene.lights);
    }));

    $("#render-button").click(function (event) {
        alert("sent");
        //stop submit the form, we will post it manually.
        event.preventDefault();
        console.log(local_scene.toJsonForRaytracer());
        console.log(local_scene);
        fire_ajax_submit();

    });

    function fire_ajax_submit() {


        var dummy_scene = {
            "figures":[{"type" : "Sphere", "center" : [0,0,0], "radius" : 0.5, "diffuse" : [0,1,0], "reflectance" : 0.5, "surfaceType" : "Normal"}],
            "backgroundColor" : [0,0,0],
            "ambientLight" : [1,1,1],
            "superSampleValue" : 1,
            "screenWidth" : 1280,
            "screenHeight" : 800
        };

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/api/rayTracerJson",
            data: JSON.stringify(local_scene.toJsonForRaytracer()),
            dataType: 'json',
            cache: false,
            timeout: 600000,
            success: function (data) {

                var json = "<h4>The following Json was send successfully</h4><pre>"
                    + JSON.stringify(data, null, 4) + "</pre>";
                $('#feedback').html(json);

                alert("ok");

                $("#result").src= "data:image/png;base64," + data;
                $("#myModal").modal();
                $("#myModal").show();


            },
            error: function (x,s,e) {

                $("#result").attr("src", "data:image/png;base64," + x.responseText);
                $("#resultModal").modal();
                $("#resultModal").show();

            }
        });
    }

    // --------------------------

    // --------------------------

});
/**
 * Created by nima on 02/02/2018.
 */
