/**
 * Created by liuligang on 2019/1/2.
 */

//建议值[16,9]
var table = [];
var maxCol = 16,maxRow=9;

var camera, scene, renderer;
var controls;
var radius = 100, theta = 0;

var objects = [];
var targets = { table: [], sphere: [], helix: [], grid: [] };



function init() {

    camera = new THREE.PerspectiveCamera( 40, window.innerWidth / window.innerHeight, 1, 10000 );
    camera.position.z = 3000;

    scene = new THREE.Scene();


    table.forEach(function(val,index){
        var element = document.createElement( 'div' );
        element.className = 'element';
        element.style.backgroundColor = 'rgba(0,127,127,' + ( Math.random() * 0.5 + 0.25 ) + ')';

        var a = document.createElement('a');
        a.href =  val.resource;
        a.setAttribute('data-lightbox','roadtrip');
        a.setAttribute('title',a.href.substr(a.href.lastIndexOf('/')+1));

        var image=new Image();
        image.src=val.src;
        //a.appendChild(image)

        element.appendChild( image );
//        element.setAttribute('style',"background-image: url(" + val.src + ")");
        a.appendChild(element)

        //var object = new THREE.CSS3DObject( element );
        var object = new THREE.CSS3DObject( a );
        object.position.x = Math.random() * 4000 - 2000;
        object.position.y = Math.random() * 4000 - 2000;
        object.position.z = Math.random() * 4000 - 2000;
        scene.add( object );

        objects.push( object );

        //table
        var object = new THREE.Object3D();
        object.position.x = (val.col * 181 ) - 1330;
        object.position.y = - (val.row * 120 ) + 990;
        targets.table.push( object );
    });

    // sphere

    var vector = new THREE.Vector3();

    for ( var i = 0, l = objects.length; i < l; i ++ ) {

        var phi = Math.acos( -1 + ( 2 * i ) / l );
        var theta = Math.sqrt( l * Math.PI ) * phi;

        var object = new THREE.Object3D();

        object.position.x = 800 * Math.cos( theta ) * Math.sin( phi );
        object.position.y = 800 * Math.sin( theta ) * Math.sin( phi );
        object.position.z = 800 * Math.cos( phi );

        vector.copy( object.position ).multiplyScalar( 2 );

        object.lookAt( vector );

        targets.sphere.push( object );

    }

    // helix

    var vector = new THREE.Vector3();

    for ( var i = 0, l = objects.length; i < l; i ++ ) {

        //var phi = i * 0.175 + Math.PI;
        var phi = i * 0.205 + Math.PI;

        var object = new THREE.Object3D();

        object.position.x = 900 * Math.sin( phi );
        object.position.y = - ( i * 8 ) + 450;
        object.position.z = 900 * Math.cos( phi );

        vector.x = object.position.x * 2;
        vector.y = object.position.y;
        vector.z = object.position.z * 2;

        object.lookAt( vector );

        targets.helix.push( object );

    }

    // grid

    for ( var i = 0; i < objects.length; i ++ ) {

        var object = new THREE.Object3D();

        object.position.x = ( ( i % 5 ) * 400 ) - 800;
        object.position.y = ( - ( Math.floor( i / 5 ) % 5 ) * 400 ) + 800;
        object.position.z = ( Math.floor( i / 25 ) ) * 1000 - 2000;

        targets.grid.push( object );

    }

    //

    renderer = new THREE.CSS3DRenderer();
    renderer.setSize( window.innerWidth, window.innerHeight );
    renderer.domElement.style.position = 'absolute';
    document.getElementById( 'container' ).appendChild( renderer.domElement );

    //

    controls = new THREE.TrackballControls( camera, renderer.domElement );
    controls.rotateSpeed = 0.5;
    controls.minDistance = 500;
    controls.maxDistance = 6000;
    controls.addEventListener( 'change', render );

    var button = document.getElementById( 'table' );
    button.addEventListener( 'click', function ( event ) {

        transform( targets.table, 2000 );

    }, false );

    var button = document.getElementById( 'sphere' );
    button.addEventListener( 'click', function ( event ) {

        transform( targets.sphere, 2000 );

    }, false );

    var button = document.getElementById( 'helix' );
    button.addEventListener( 'click', function ( event ) {

        transform( targets.helix, 2000 );

    }, false );

    var button = document.getElementById( 'grid' );
    button.addEventListener( 'click', function ( event ) {

        transform( targets.grid, 2000 );

    }, false );

    //transform( targets.helix, 5000 );
    transform( targets.table, 2000 );
    //

    window.addEventListener( 'resize', onWindowResize, false );

}

function transform( targets, duration ) {

    TWEEN.removeAll();

    for ( var i = 0; i < objects.length; i ++ ) {

        var object = objects[ i ];
        var target = targets[ i ];

        new TWEEN.Tween( object.position )
            .to( { x: target.position.x, y: target.position.y, z: target.position.z }, Math.random() * duration + duration )
            .easing( TWEEN.Easing.Exponential.InOut )
            .start();

        new TWEEN.Tween( object.rotation )
            .to( { x: target.rotation.x, y: target.rotation.y, z: target.rotation.z }, Math.random() * duration + duration )
            .easing( TWEEN.Easing.Exponential.InOut )
            .start();

    }

    new TWEEN.Tween( this )
        .to( {}, duration * 2 )
        .onUpdate( render )
        .start();
}

function onWindowResize() {

    camera.aspect = window.innerWidth / window.innerHeight;
    camera.updateProjectionMatrix();

    renderer.setSize( window.innerWidth, window.innerHeight );

}

function animate() {

    requestAnimationFrame( animate );

    TWEEN.update();
    controls.update();
    // render();
    //move( -1 );

}

function render() {
    /*
     theta += 0.1;
     */

/*
    if (!isMove) {
              camera.position.x += Math.PI * 1;
				camera.position.y += 5;
				camera.position.z += 0.1;
    }
    camera.lookAt( scene.position );
*/
    renderer.render( scene, camera );

}

/**
 ** 控制自动旋转
*/
/*
var isMove = false;

document.onmousedown = function(e) {
    isMove = true;
};

document.onmouseup = function() {
    isMove = false;
    animate();
}
*/

// 说明：用 JavaScript 实现网页图片等比例缩放
function resizeImg(image,distWidth,distHeight)
{
    srcWidth = image.width;
    srcHeight = image.height;
    var ratio = 1;
    if(srcWidth>0 && srcHeight>0)
    {
        if(srcWidth/srcHeight>= distWidth/distHeight)
        {
            if(srcWidth>distWidth)
            {
                ratio = distWidth/srcWidth;
            }
        }
        else
        {
            if(srcHeight>distHeight)
            {
                ratio = distHeight/srcHeight;
            }
        }
    }
    var width = srcWidth*ratio;
    var heigh = srcHeight*ratio;

    image.style.width = width.toString() + 'px';
    image.style.height = heigh.toString() + 'px';

    if(width < distWidth)
        image.style.paddingLeft = ((distWidth - width)/2).toString() + 'px';

    if(heigh < distHeight)
        image.style.paddingTop = ((distHeight - heigh)/2).toString() + 'px';
}

//获得指定文件夹图片名称列表，同时设置图片的位置
function getImgs (uid){
    var col = 0;
    var row = 0;
    // $.ajax({
    //     type: "GET",
    //     url: "/images",
    //     //data: "",
    //     dataType: "json",
    //     success: function(data){
    var data=[{"resource":"image/IMG_2810.JPG","src":"image/1/IMG_2810.JPG","type":"image"},{"resource":"image/IMG_2891.PNG","src":"image/1/IMG_2891.PNG","type":"image"},{"resource":"image/IMG_2892.JPG","src":"image/1/IMG_2892.JPG","type":"image"},{"resource":"image/IMG_2893.JPG","src":"image/1/IMG_2893.JPG","type":"image"},{"resource":"image/IMG_2894.JPG","src":"image/1/IMG_2894.JPG","type":"image"},{"resource":"image/IMG_2895.JPG","src":"image/1/IMG_2895.JPG","type":"image"},{"resource":"image/IMG_2896.JPG","src":"image/1/IMG_2896.JPG","type":"image"},{"resource":"image/IMG_2911.JPG","src":"image/1/IMG_2911.JPG","type":"image"},{"resource":"image/IMG_2912.JPG","src":"image/1/IMG_2912.JPG","type":"image"},{"resource":"image/IMG_2913.JPG","src":"image/1/IMG_2913.JPG","type":"image"},{"resource":"image/IMG_2914.JPG","src":"image/1/IMG_2914.JPG","type":"image"},{"resource":"image/IMG_3027.JPG","src":"image/1/IMG_3027.JPG","type":"image"},{"resource":"image/IMG_3028.JPG","src":"image/1/IMG_3028.JPG","type":"image"},{"resource":"image/IMG_3029.JPG","src":"image/1/IMG_3029.JPG","type":"image"},{"resource":"image/IMG_3034.JPG","src":"image/1/IMG_3034.JPG","type":"image"},{"resource":"image/IMG_3035.JPG","src":"image/1/IMG_3035.JPG","type":"image"},{"resource":"image/IMG_3036.JPG","src":"image/1/IMG_3036.JPG","type":"image"},{"resource":"image/IMG_3037.JPG","src":"image/1/IMG_3037.JPG","type":"image"},{"resource":"image/IMG_3038.JPG","src":"image/1/IMG_3038.JPG","type":"image"},{"resource":"image/IMG_3039.JPG","src":"image/1/IMG_3039.JPG","type":"image"},{"resource":"image/IMG_3040.JPG","src":"image/1/IMG_3040.JPG","type":"image"},{"resource":"image/IMG_3043.JPG","src":"image/1/IMG_3043.JPG","type":"image"},{"resource":"image/IMG_3044.JPG","src":"image/1/IMG_3044.JPG","type":"image"},{"resource":"image/IMG_3046.JPG","src":"image/1/IMG_3046.JPG","type":"image"},{"resource":"image/IMG_3048.JPG","src":"image/1/IMG_3048.JPG","type":"image"},{"resource":"image/IMG_3049.JPG","src":"image/1/IMG_3049.JPG","type":"image"},{"resource":"image/IMG_3050.JPG","src":"image/1/IMG_3050.JPG","type":"image"},{"resource":"image/IMG_3051.JPG","src":"image/1/IMG_3051.JPG","type":"image"},{"resource":"image/IMG_3052.JPG","src":"image/1/IMG_3052.JPG","type":"image"},{"resource":"image/IMG_3053.JPG","src":"image/1/IMG_3053.JPG","type":"image"},{"resource":"image/IMG_3054.JPG","src":"image/1/IMG_3054.JPG","type":"image"},{"resource":"image/IMG_3055.JPG","src":"image/1/IMG_3055.JPG","type":"image"},{"resource":"image/IMG_3056.JPG","src":"image/1/IMG_3056.JPG","type":"image"},{"resource":"image/IMG_3072.JPG","src":"image/1/IMG_3072.JPG","type":"image"},{"resource":"image/IMG_3073.JPG","src":"image/1/IMG_3073.JPG","type":"image"},{"resource":"image/IMG_3074.JPG","src":"image/1/IMG_3074.JPG","type":"image"},{"resource":"image/IMG_3075.JPG","src":"image/1/IMG_3075.JPG","type":"image"},{"resource":"image/IMG_3076.JPG","src":"image/1/IMG_3076.JPG","type":"image"},{"resource":"image/IMG_3078.JPG","src":"image/1/IMG_3078.JPG","type":"image"},{"resource":"image/IMG_3080.JPG","src":"image/1/IMG_3080.JPG","type":"image"},{"resource":"image/IMG_3081.JPG","src":"image/1/IMG_3081.JPG","type":"image"},{"resource":"image/IMG_3082.JPG","src":"image/1/IMG_3082.JPG","type":"image"},{"resource":"image/IMG_3085.JPG","src":"image/1/IMG_3085.JPG","type":"image"},{"resource":"image/IMG_3086.JPG","src":"image/1/IMG_3086.JPG","type":"image"},{"resource":"image/IMG_3087.JPG","src":"image/1/IMG_3087.JPG","type":"image"},{"resource":"image/IMG_3088.JPG","src":"image/1/IMG_3088.JPG","type":"image"},{"resource":"image/IMG_3089.JPG","src":"image/1/IMG_3089.JPG","type":"image"},{"resource":"image/IMG_3090.JPG","src":"image/1/IMG_3090.JPG","type":"image"},{"resource":"image/IMG_3091.JPG","src":"image/1/IMG_3091.JPG","type":"image"},{"resource":"image/IMG_3092.JPG","src":"image/1/IMG_3092.JPG","type":"image"},{"resource":"image/IMG_3093.JPG","src":"image/1/IMG_3093.JPG","type":"image"},{"resource":"image/IMG_3094.JPG","src":"image/1/IMG_3094.JPG","type":"image"},{"resource":"image/IMG_3095.JPG","src":"image/1/IMG_3095.JPG","type":"image"},{"resource":"image/IMG_3096.JPG","src":"image/1/IMG_3096.JPG","type":"image"},{"resource":"image/IMG_3097.JPG","src":"image/1/IMG_3097.JPG","type":"image"},{"resource":"image/IMG_3098.JPG","src":"image/1/IMG_3098.JPG","type":"image"},{"resource":"image/IMG_3099.JPG","src":"image/1/IMG_3099.JPG","type":"image"},{"resource":"image/IMG_3100.JPG","src":"image/1/IMG_3100.JPG","type":"image"},{"resource":"image/IMG_3101.JPG","src":"image/1/IMG_3101.JPG","type":"image"},{"resource":"image/IMG_3102.JPG","src":"image/1/IMG_3102.JPG","type":"image"},{"resource":"image/IMG_3103.JPG","src":"image/1/IMG_3103.JPG","type":"image"},{"resource":"image/IMG_3104.JPG","src":"image/1/IMG_3104.JPG","type":"image"},{"resource":"image/IMG_3105.JPG","src":"image/1/IMG_3105.JPG","type":"image"},{"resource":"image/IMG_3106.JPG","src":"image/1/IMG_3106.JPG","type":"image"},{"resource":"image/IMG_3107.JPG","src":"image/1/IMG_3107.JPG","type":"image"},{"resource":"image/IMG_3108.JPG","src":"image/1/IMG_3108.JPG","type":"image"},{"resource":"image/IMG_3109.JPG","src":"image/1/IMG_3109.JPG","type":"image"},{"resource":"image/IMG_3110.JPG","src":"image/1/IMG_3110.JPG","type":"image"},{"resource":"image/IMG_3111.JPG","src":"image/1/IMG_3111.JPG","type":"image"},{"resource":"image/IMG_3112.JPG","src":"image/1/IMG_3112.JPG","type":"image"},{"resource":"image/IMG_3114.JPG","src":"image/1/IMG_3114.JPG","type":"image"},{"resource":"image/IMG_3116.JPG","src":"image/1/IMG_3116.JPG","type":"image"},{"resource":"image/IMG_3117.JPG","src":"image/1/IMG_3117.JPG","type":"image"},{"resource":"image/IMG_3118.JPG","src":"image/1/IMG_3118.JPG","type":"image"},{"resource":"image/IMG_3120.JPG","src":"image/1/IMG_3120.JPG","type":"image"},{"resource":"image/IMG_3121.JPG","src":"image/1/IMG_3121.JPG","type":"image"},{"resource":"image/IMG_3122.JPG","src":"image/1/IMG_3122.JPG","type":"image"},{"resource":"image/IMG_3124.JPG","src":"image/1/IMG_3124.JPG","type":"image"},{"resource":"image/IMG_3128.JPG","src":"image/1/IMG_3128.JPG","type":"image"},{"resource":"image/IMG_3130.JPG","src":"image/1/IMG_3130.JPG","type":"image"},{"resource":"image/IMG_3132.JPG","src":"image/1/IMG_3132.JPG","type":"image"},{"resource":"image/IMG_3133.JPG","src":"image/1/IMG_3133.JPG","type":"image"},{"resource":"image/IMG_3134.JPG","src":"image/1/IMG_3134.JPG","type":"image"},{"resource":"image/IMG_3135.JPG","src":"image/1/IMG_3135.JPG","type":"image"},{"resource":"image/IMG_3136.JPG","src":"image/1/IMG_3136.JPG","type":"image"},{"resource":"image/IMG_3138.JPG","src":"image/1/IMG_3138.JPG","type":"image"}];
    $.each(data,function(index,value)
            {
                //console.log(index,value);
                data[index].col = col++;
                data[index].row = row;
                if(col > maxCol){
                    col = 1;
                    row ++;
                }
                if(row > maxRow){
                    return;
                }
            });
//            console.log(data);
            table = data;
            init();
            animate();
    //
    //     },
    //     error: function(XMLHttpRequest, textStatus, errorThrown){
    //         console.error(XMLHttpRequest, textStatus, errorThrown);
    //     }
    // });

}