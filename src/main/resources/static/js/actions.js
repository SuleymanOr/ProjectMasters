var id_increment  = 1;

function refreshList(list){
    $("#shape-list").html("");
    list.forEach(function(e){
        var element = "<div class='shape-item' key="+ e.id.toString() +"><span class='shape-item-name'>"+ e.name +"</span><span class='shape-item-edit'>/</span><span class='shape-item-delete'>x</span></div>"
        $("#shape-list").append(element);
    });
};

function Shape(id,name,type,color,x,y,z){
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
}

function Sphere (id,name,type,color,x,y,z,radius){
    Shape.call(this,id,name,type,color,x,y,z);
    this.radius = radius;
}

function Cube (id,name,type,color,x,y,z,w,l,h){
    Shape.call(this,id,name,type,color,x,y,z);
    this.w = w;
    this.l = l;
    this.h = h;
}

function Cylinder (id,name,type,color,x,y,z,radius,height){
    Shape.call(this,id,name,type,color,x,y,z);
    this.radius = radius;
    this.height = height;
}

function LocalScene(scene){
    this.elements = [];
    this.live_scene = scene;
    this.addSphere = function () {
        // Adding rhe shape to local shape list
        var id = id_increment;
        id_increment +=1;
        var name = $("#new-sphere-name").val();
        var type = "sphere";
        var radius =  parseInt($("#new-sphere-radius").val(),10);
        var color = parseInt($("#new-sphere-color").val(),16);
        var x = parseInt($("#new-sphere-x").val(),10);
        var y = parseInt($("#new-sphere-y").val(),10);
        var z = parseInt($("#new-sphere-z").val(),10);
        this.elements[id] = new Sphere(id,name,type,color,x,y,z,radius);
        // Adding the shape to three.js scene
        var geometry = new THREE.SphereBufferGeometry( radius, 20, 20 );
        var material = new THREE.MeshLambertMaterial( { color: color , wireframe: true} );
        var sphere = new THREE.Mesh( geometry, material );
        sphere.position.set(x,y,z);
        scene.add( sphere );
    };
    this.addCube = function () {
        var id = id_increment;
        id_increment +=1;
        var name = $("#new-cube-name").val();
        var type = "cube";
        var color = $("#new-cube-color").val();
        var x = parseInt($("#new-cube-x").val(),10);
        var y = parseInt($("#new-cube-y").val(),10);
        var z = parseInt($("#new-cube-z").val(),10);
        var w = parseInt($("#new-cube-w").val(),10);
        var l = parseInt($("#new-cube-l").val(),10);
        var h = parseInt($("#new-cube-h").val(),10);
        this.elements[id] = new Cube(id,name,type,color,x,y,z,w,l,h);
        var geometry = new THREE.BoxGeometry( 20, 20, 20, 5, 5 ,5);
    };
    this.addCylinder = function () {
        var id = id_increment;
        id_increment +=1;
        var name = $("#new-cylinder-name").val();
        var type = "cube";
        var color = $("#new-cylinder-color").val();
        var x = parseInt($("#new-cylinder-x").val(),10);
        var y = parseInt($("#new-cylinder-y").val(),10);
        var z = parseInt($("#new-cylinder-z").val(),10);
        var radius = parseInt($("#new-cylinder-radius").val(),10);
        var height = parseInt($("#new-cylinder-height").val(),10);
        this.elements[id] = new Cylinder(id,name,type,color,x,y,z,radius,height);
    };
    this.removeShape = function (id) {
        delete this.elements[id];
    };
};

$(document).ready(function () {
    var scene = new THREE.Scene();
    var camera = new THREE.PerspectiveCamera( 55, 2, 0.1, 1000 );

    var renderer = new THREE.WebGLRenderer();
    renderer.setSize( $("#canvas-container").width()-15, ($("#canvas-container").width()-15)/2 );
    document.getElementById('canvas-container').appendChild( renderer.domElement );

    camera.position.y=50;

    var light = new THREE.PointLight( 0xFFFFFF,2,0,2 );
    light.position.set( 0, 100, 0 );
    scene.add( light );

    var light2 = new THREE.PointLight( 0xFFFFFF,2,0,2 );
    light2.position.set( -10, -10, 25 );
     // scene.add( light2 );



    var render = function () {
        requestAnimationFrame( render );

        // cube.rotation.x += 0.005;
        // cube.rotation.y += 0.008;
        camera.updateProjectionMatrix();

        camera.lookAt( scene.position );

        // Move the camera in a circle with the pivot point in the centre of this circle...
        // ...so that the pivot point, and focus of the camera is on the centre of our scene.
        var timer = (new Date().getTime() % 30000)*3.14159265/15000;
        // var timer = (new Date().getTime() * 0.0005);
        camera.position.x = Math.floor(Math.cos( timer ) * 1000)/10.0;
        camera.position.z = Math.floor(Math.sin( timer ) * 1000)/10.0;

        renderer.render(scene, camera);
    };

    render();

    var local_scene = new LocalScene(scene);

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
            default:
                alert("Unknown Object!");
        }
        refreshList(local_scene.elements);
    });

    $("#shape-list").on("click",".shape-item-delete",(function () {
        local_scene.removeShape(parseInt($(this).parent().attr("key"),10));
        refreshList(setup.elements);
    }));

    $("#render-button").click(function (event) {
        alert("sent");
        //stop submit the form, we will post it manually.
        event.preventDefault();

        fire_ajax_submit();

    });

    function fire_ajax_submit() {

        var formData = {
            radius: 70
        }


        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/api/rayTracerJson",
            data: JSON.stringify(formData),
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