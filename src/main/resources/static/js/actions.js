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
});
/**
 * Created by nima on 02/02/2018.
 */
