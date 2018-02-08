$(document).ready(function () {
    var scene = new THREE.Scene();
    var camera = new THREE.PerspectiveCamera( 75, 2, 0.1, 1000 );

    var renderer = new THREE.WebGLRenderer();
    renderer.setSize( $("#canvas-container").width()-15, ($("#canvas-container").width()-15)/2 );
    document.getElementById('canvas-container').appendChild( renderer.domElement );

    var geometry = new THREE.BoxGeometry( 20, 20, 20, 5, 5 ,5);

    var material = new THREE.MeshLambertMaterial( { color: 0x22a5ce , wireframe: true} );
    var cube = new THREE.Mesh( geometry, material );
    scene.add( cube );

    camera.position.z = 100;

    var light = new THREE.PointLight( 0xFFFFFF );
    light.position.set( 10, 10, 25 );
    scene.add( light );

    var light2 = new THREE.PointLight( 0xFFFFFF );
    light2.position.set( -10, -10, 25 );
    scene.add( light2 );



    var render = function () {
        requestAnimationFrame( render );

        cube.rotation.x += 0.005;
        cube.rotation.y += 0.008;
        camera.updateProjectionMatrix();

        renderer.render(scene, camera);
    };

    render();

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
            error: function (e) {

                var json = "<h4>Ajax Response</h4><pre>"
                    + e.responseText + "</pre>";
                $('#feedback').html(json);

                alert("err" + e);

                $("#result").setAttribute("src", "data:image/png;base64," + e)
                $("#myModal").modal();
                $("#myModal").show();

            }
        });
    }

});
/**
 * Created by nima on 02/02/2018.
 */
