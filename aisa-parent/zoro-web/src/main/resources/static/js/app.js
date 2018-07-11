/**
 * Created by yu on 2015/6/24.
 */
var b = document.body;
var c = document.getElementsByTagName('canvas')[0];
var a = c.getContext('2d');
var M = Math,
    n = M.pow,
    i, 
	E = 0,
	EE=0.2,
    F = "rgba(233,61,109,",
    d = M.cos,
    z = M.sin,
    L = 1,
    u = 30,
    W = window,
    w = c.width = 400,//W.innerWidth,
    h = c.height = 360,//W.innerHeight,
    r = function () {
        return M.random() * 2 - 1
    },
    y = "px Arial",
    v = "",
    q = "♥",
    X = w / 2,
    Y = h / 2,
    T = 4,
    p = function () {
        var e = this;
        e.g = function () {
				e.x = X;
				e.y = Y;
				e.k = 0;
				e.l = 0;
				e.t = M.random() * 19000;
				e.c = e.t
			};
		e.d = function () {
				a.fillStyle = F + (e.c / e.t) + ")";
				a.fillText(q, e.x, e.y);
				e.c -= 50;
				e.x += e.k;
				e.y += e.l;
				e.k = e.k + r();
				e.l = e.l + r();
				if (e.c < 0 || e.x > w || e.x < 0 || e.y > h || e.y < 0) {
					e.g()
				}
			};
		e.g()
    },
A,
B;
a.textAlign = "center";
a.strokeStyle = "#000";
a.lineWidth = 2;
for (i = 0; i < 350; i++) {
        M[i] = new p()
    }	
		
 
var DEFLENGTH = 40;//单位长度，以mario的高度为标准
var CWIDTH = 400;//CANVAS宽度
var CHEIGHT = 400;//CANVAS高度
var TIMECOUNT = 0;
var resourcesArray=["background.jpg","heart.png","ring.png","togetherJL.png","togetherJR.png","togetherL.png","togetherR.png",
"princess.jpg","iceland.gif","land1.gif","land2.gif","pipe.png","brick1.gif",
"singleJR.png","singleJL.png","singleR.png","singleL.png","monster.png","monsterD.gif"];
for(var i=1;i<=27;i++){
	resourcesArray.push(["scene"+i+".jpg"]);
}
resources.load(resourcesArray)


resources.onReady(function(){
    var canvas = document.getElementById("stage");
    var map = new Map();
    Model.prototype.map = map;
    var camera = new Camera(canvas);
    var control = new Control();//实例化游戏控制类
	var princessData = 
	
	{
        imgs:{
            default:{img:resources.get("princess.jpg"),x:0,renderW:0.6*DEFLENGTH,renderH:DEFLENGTH}
        },
        position:{x:1030,y:280,width:1,height:1}
    }
	var heartData = 
	
	{
        imgs:{
            default:{img:resources.get("heart.png"),x:0,renderW:DEFLENGTH,renderH:DEFLENGTH}
        },
        position:{x:980,y:294,width:1,height:1}
    }
	var dimondData = 
	
	{
        imgs:{
            default:{img:resources.get("ring.png"),x:0,renderW:DEFLENGTH,renderH:DEFLENGTH}
        },
        position:{x:10650,y:294,width:1,height:1}
    }
    var modelsData = [
	
	// 开始及横店
	{
        imgs:{
            default:{img:resources.get("land1.gif"),x:0,renderW:DEFLENGTH,renderH:DEFLENGTH}
        },
        position:[{x:0,y:400,width:340,height:1},{x:0,y:360,width:12,height:1},{x:550,y:360,width:15,height:1},{x:1400,y:360,width:13,height:1},{x:2200,y:360,width:14,height:1}]
    },{
        imgs:{
            default:{img:resources.get("land2.gif"),x:0,renderW:DEFLENGTH,renderH:DEFLENGTH}
        },
        position:[{x:0,y:320,width:12,height:1},{x:550,y:320,width:15,height:1},{x:1400,y:320,width:14,height:1},{x:2200,y:320,width:14,height:1}]
    },{
        imgs:{
            default:{img:resources.get("land1.gif"),x:0,renderW:0.6*DEFLENGTH,renderH:0.6*DEFLENGTH}
        },
        position:[{x:1864,y:296,width:4,height:1},{x:1888,y:272,width:3,height:1},{x:1912,y:248,width:2,height:1},{x:1936,y:224,width:1,height:1}]
    },{
        imgs:{
            default:{img:resources.get("land2.gif"),x:0,renderW:0.6*DEFLENGTH,renderH:0.6*DEFLENGTH}
        },
        position:[{x:1840,y:296},{x:1864,y:272},{x:1888,y:248},{x:1912,y:224},{x:1936,y:200}]
    },{
        imgs:{
            default:{img:resources.get("pipe.png"),x:0,renderW:DEFLENGTH,renderH:1.5*DEFLENGTH}
        },
        position:[{x:330,y:400 -3.5*DEFLENGTH},{x:700,y:400-3.5*DEFLENGTH},{x:2400,y:400-3.5*DEFLENGTH}]
    },{
        imgs:{
            default:{img:resources.get("brick1.gif"),x:0,renderW:0.6*DEFLENGTH,renderH:0.6*DEFLENGTH}
        },
        position:[{x:200,y:180,width:5,height:1},{x:1200,y:220,width:2},{x:1650,y:220,width:4,height:1},{x:2000,y:100,width:7,height:1}]
    },
	// 上海
	{
        imgs:{
            default:{img:resources.get("land1.gif"),x:0,renderW:0.6*DEFLENGTH,renderH:0.6*DEFLENGTH}
        },
        position:[{x:2500,y:296,width:9,height:1},{x:2524,y:272,width:8,height:1},{x:2548,y:248,width:7,height:1},{x:2572,y:224,width:5,height:1},{x:2596,y:200,width:4,height:1},{x:2620,y:176,width:3,height:1},{x:2644,y:152,width:2,height:1},{x:2668,y:128,width:1,height:1}]
    },{
        imgs:{
            default:{img:resources.get("brick1.gif"),x:0,renderW:0.6*DEFLENGTH,renderH:0.6*DEFLENGTH}
        },
        position:[{x:2810,y:200,width:15,height:1},{x:3200,y:120,width:8},{x:3500,y:80,width:12,height:1},{x:4120,y:200,width:8},{x:4400,y:120,width:12}]
    },{
        imgs:{
            default:{img:resources.get("land1.gif"),x:0,renderW:DEFLENGTH,renderH:DEFLENGTH}
        },
        position:[{x:3930,y:360,width:12,height:1}]
    },{
        imgs:{
            default:{img:resources.get("land2.gif"),x:0,renderW:DEFLENGTH,renderH:DEFLENGTH}
        },
        position:[{x:3930,y:320,width:12,height:1}]
    },{
        imgs:{
            default:{img:resources.get("pipe.png"),x:0,renderW:DEFLENGTH,renderH:1.5*DEFLENGTH}
        },
        position:[{x:3930,y:400 -3.5*DEFLENGTH}]
    },
	// 重庆
	{
        imgs:{
            default:{img:resources.get("land1.gif"),x:0,renderW:DEFLENGTH,renderH:DEFLENGTH}
        },
        position:[{x:4700,y:360,width:16,height:1},{x:5850,y:360,width:16,height:1}]
    },{
        imgs:{
            default:{img:resources.get("land2.gif"),x:0,renderW:DEFLENGTH,renderH:DEFLENGTH}
        },
        position:[{x:4700,y:320,width:16,height:1},{x:4700,y:296,width:4,height:1},{x:4700,y:272,width:3,height:1},{x:4700,y:248,width:2,height:1},{x:4700,y:224,width:1,height:1},{x:6000,y:320,width:16,height:1},{x:6000,y:296,width:4,height:1},{x:6000,y:272,width:3,height:1},{x:6000,y:248,width:2,height:1},{x:6000,y:224,width:1,height:1}]
    },{
        imgs:{
            default:{img:resources.get("brick1.gif"),x:0,renderW:0.6*DEFLENGTH,renderH:0.6*DEFLENGTH}
        },
        position:[{x:5296,y:220,width:1,height:1},{x:5190,y:150,width:1,height:1},{x:5296,y:120,width:12,height:1},{x:5600,y:250,width:12,height:1}]
    },
	
	// 滑雪
	{
        imgs:{
            default:{img:resources.get("iceland.gif"),x:0,renderW:DEFLENGTH,renderH:DEFLENGTH}
        },
        position:[{x:6800,y:320,width:14,height:1},{x:7160,y:280,width:5,height:1},{x:7200,y:240,width:4,height:1},{x:7240,y:200,width:3,height:1},
		{x:7280,y:160,width:2,height:1},{x:7320,y:120,width:8,height:1},{x:7600,y:160,width:2,height:1},{x:7600,y:200,width:3,height:1},
		{x:7600,y:240,width:4,height:1},{x:7600,y:280,width:5,height:1},{x:7600,y:320,width:7,height:1}]
    },{
        imgs:{
            default:{img:resources.get("land1.gif"),x:0,renderW:DEFLENGTH,renderH:DEFLENGTH}
        },
        position:[{x:6800,y:360,width:14,height:1},{x:7880,y:360,width:1,height:1}]
    },
	// 西安
	{
        imgs:{
            default:{img:resources.get("land2.gif"),x:0,renderW:DEFLENGTH,renderH:DEFLENGTH}
        },
        position:[{x:8000,y:320,width:14,height:1},{x:9050,y:320,width:4,height:1}]
    },
	{
        imgs:{
            default:{img:resources.get("land1.gif"),x:0,renderW:DEFLENGTH,renderH:DEFLENGTH}
        },
        position:[{x:8000,y:360,width:14,height:1},{x:9050,y:360,width:4,height:1}]
    },{
        imgs:{
            default:{img:resources.get("brick1.gif"),x:0,renderW:0.6*DEFLENGTH,renderH:0.6*DEFLENGTH}
        },
        position:[{x:8400,y:220,width:4,height:1},{x:8600,y:145,width:17,height:1},{x:9250,y:145,width:15,height:1},{x:9700,y:200,width:13,height:1}]
    },{
        imgs:{
            default:{img:resources.get("pipe.png"),x:0,renderW:DEFLENGTH,renderH:2*DEFLENGTH}
        },
        position:[{x:9150,y:400 -4*DEFLENGTH}]
    },
	// 结局
	{
        imgs:{
            default:{img:resources.get("land2.gif"),x:0,renderW:DEFLENGTH,renderH:DEFLENGTH}
        },
        position:[{x:10060,y:320,width:24,height:1},{x:10100,y:280,width:1,height:1},{x:10140,y:240,width:1,height:1}
		,{x:10180,y:200,width:1,height:1},{x:10220,y:160,width:1,height:1},{x:10260,y:120,width:1,height:1}
		,{x:10300,y:80,width:1,height:1},{x:10340,y:40,width:1,height:1}]
    },
	{
        imgs:{
            default:{img:resources.get("land1.gif"),x:0,renderW:DEFLENGTH,renderH:DEFLENGTH}
        },
        position:[{x:10060,y:360,width:24,height:1},{x:10140,y:280,width:6,height:1},{x:10180,y:240,width:5,height:1}
		,{x:10220,y:200,width:4,height:1},{x:10260,y:160,width:3,height:1},{x:10300,y:120,width:2,height:1}
		,{x:10340,y:80,width:1,height:1}]
    }
	
	]
    var livingsData = [{
        imgs:{
            default:{img:resources.get("monster.png"),x:0,spiritW:60,renderW:0.8*DEFLENGTH,renderH:0.8*DEFLENGTH},
            die:{img:resources.get("monsterD.gif"),x:0,spiritW:60,renderW:0.8*DEFLENGTH,renderH:0.8*DEFLENGTH,crushH:0.1*DEFLENGTH}
        },
        position:[{x:200,y:290},{x:300,y:290},{x:650,y:290},{x:1310,y:160},{x:1550,y:110}]
    }]
    var models = [];
    var livings = [];
    //实例化场景模型
    modelsData.forEach(function(modelsD,index){
        modelsD.position.forEach(function(position,index){
            var ii=position.width|| 1,
                jj=position.height||1;
            for(i=0;i<ii;i++){
                for(j=0;j<jj;j++){
                    var model = new Model(modelsD.imgs,{x:position.x+i*modelsD.imgs.default.renderW , y:position.y+j*modelsD.imgs.default.renderH})
                    models.push(model)
                }
            }
        })
    })
    //实例化野怪
    livingsData.forEach(function(livingsD,index){
        livingsD.position.forEach(function(position,index){
            var living = new Livings(livingsD.imgs,{x:position.x,y:position.y})
            livings.push(living)
        })
    })
    //实例化马里奥
    var player = new Player({
        moveR:{img:resources.get("singleR.png"),x:0,spiritW:32,renderW:DEFLENGTH,renderH:DEFLENGTH,crushW:0.8*DEFLENGTH},
        moveL:{img:resources.get("singleL.png"),x:0,spiritW:32,renderW:DEFLENGTH,renderH:DEFLENGTH,crushW:0.8*DEFLENGTH},
        jumpR:{img:resources.get("singleJR.png"),x:0,renderW:DEFLENGTH,renderH:DEFLENGTH},
        jumpL:{img:resources.get("singleJL.png"),x:0,renderW:DEFLENGTH,renderH:DEFLENGTH}
		},
		{x:0,y:220},
		{onCrush:onCrush}
	)
    function onCrush(model){
        if(model.constructor==Livings&&model.crush.top){
			model.die();
            var index = livings.indexOf(model);
            livings.splice(index,1)
        }else if(model.constructor==Livings){
            //this.die();

        }
    }
    window.map=map;

    //livings.push(player);
    var loop = new GameLoop(function(interTime){
        var frameTime = 30;//每帧对应的时间
        var renderModels = getRenderModels(models,map),
            renderLivings = getRenderModels(livings,map);
        renderLivings.forEach(function(living,item){
            living.autoMove(frameTime);
        })
        renderLivings.push(player)
        Model.prototype.alls = Array.prototype.concat(renderLivings,renderModels);
        player.update(control,canvas,frameTime,loop);
        map.update(control,player,canvas,frameTime);

		camera.drawBackground(map);
		camera.drawModels(renderModels,map);
		camera.drawLivings(renderLivings,map);
		camera.drawPrincess(player,princessData,map);
		camera.drawGameInfos(loop.info);
		camera.drawController();
		camera.drawLove(player,heartData,map);
		camera.drawDimond(player,dimondData,map);
		if(player.isEnd > 1){
			camera.drawEnd();
		}
		//camera.drawScreen()
    },30)//30fps
    loop.frame()

})