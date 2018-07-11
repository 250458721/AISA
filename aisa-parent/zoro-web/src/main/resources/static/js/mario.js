/**
 * Created by yu on 2015/6/24.
 */
//游戏控制类
function Control(){
    this.code = { 37: 'left', 39: 'right', 38: 'jump', 40: 'down'}
    this.status = { 'left': false, 'right': false, 'jump': false, 'down': false };
    document.addEventListener("keydown",function(e){
        var status = this.code[e.keyCode];
        if(!status) return;
        this.status[status] = true;
    }.bind(this))
    document.addEventListener("keyup",function(e){
        var status = this.code[e.keyCode];
        if(!status) return;
        this.status[status] = false;
    }.bind(this))
	document.addEventListener("touchstart",function(e){
		for(var i=0;i<e.touches.length ;i++){
			var touch = e.touches[i]; //获取第一个触点
	        var x = Number(touch.clientX); //页面触点X坐标
	        var y = Number(touch.clientY); //页面触点Y坐标
			console.log(x+" "+y)
			if(x>150&&x<200&&y>250&&y<340){
				this.status['left'] = true;
				this.status['right'] = false;
			}
			else if(x>205&&x<255&&y>250&&y<340){
				this.status['right'] = true;
				this.status['left'] = false;
			}
	        else if(x>460&&x<515&&y>250&&y<340){
				this.status['jump'] = true;
			}
		}
        //记录触点初始位置
		//console.log(x+" "+y)
	}.bind(this))
	document.addEventListener("touchend",function(e){
		console.log(e)
		var touch = e.changedTouches[0];
		var x =touch.pageX; //页面触点X坐标
		var y = touch.pageY;
		if(x>460&&x<515&&y>250&&y<340){
			this.status['jump'] = false;
		}
		else{
			console.log(x+" "+y)

				this.status['left'] = false;

				this.status['right'] = false;

				this.status['jump'] = false;
		}
			
		
	}.bind(this))

}
//游戏地图类
function Map(img,size){
    this.background = resources.get("background.jpg")
	this.scenes = [];
	for(var i=1;i<=27;i++){
		   this.scenes.push(resources.get("scene"+i+".jpg"))
	}
    this.x=0;
}
Map.prototype.update = function(control,player,canvas,interTime){
	if(player.isMeet==0||player.isEnd!=-1){
		if(player.position.x-this.x>=canvas.width/2-70)
			this.x += player.speed.x*interTime/300;
		return
	}
	
	if(control.status.right||player.isMeet==0||player.isEnd!=-1){
        if(player.position.x-this.x>=canvas.width/2)
            this.x += player.speed.x*interTime/300;
    }else if(control.status.left&&player.position.x>=canvas.width/2){
		 if(player.position.x-this.x<=canvas.width/2)
            this.x -= player.speed.x*interTime/300;
    }
}

//模型类
function Model(imgs,position){
    this.imgs = deepClone(imgs);
    this.position = position;
    this.act = "default";
}
Model.prototype.alls = [];//画面中需要被检测渲染的所有模型，包括生物，场景
Model.prototype.map;//所在的地图
//生物类，继承模型类
function Livings(imgs,position){
    Model.call(this,imgs,position)
    this.crush = {left:false,right:false,top:false,bottom:false};
    this.speed = {x:-30,y:0};
    this.act="default";
    this.isAlive = true;
}
inheritPrototype(Livings,Model)
Livings.prototype.spirit = function(act){
    this.imgs[act].x += this.imgs[act].spiritW;
    if(this.imgs[act].x == this.imgs[act].img.width) this.imgs[act].x=0;

}
Livings.prototype.move = function(x,y){
    if(this.isAlive) this.collide(Livings.prototype.onCrush);
    if(x>0&&!this.crush.right)
        this.position.x += x;
    else if(x<0&&!this.crush.left)
        this.position.x += x;
    if(y>0&&!this.crush.bottom||!this.isAlive)
        this.position.y +=y;
    else if(y<0&&!this.crush.top)
        this.position.y +=y;
    //console.log(this.speed)
}
Livings.prototype.autoMove = function(interTime){
    //if(this.crush.left) this.speed.x = -this.speed.x;
    this.move(this.speed.x*interTime/1000,0);
    this.spirit("default");
    this.gravity(400, interTime / 1000)
    //console.log(this.imgs["default"])
}
Livings.prototype.die =function(){
    if(this.isAlive) {
        this.isAlive = false;
        this.act= "die";
    }
}

Livings.prototype.gravity = function(g,interTime){//添加重力
    if(this.position.y<CHEIGHT+100&&!this.crush.bottom||!this.isAlive){
        this.move(0,this.speed.y*interTime + g*interTime*interTime/2);
        this.speed.y += g*interTime;
    }
}
Livings.prototype.collide = function(callback){//碰撞检测
    var tImg = this.imgs[this.act],
        tCenter = {x:this.position.x + tImg.renderW/2,y:this.position.y + tImg.renderH/2},
        that = this;
    var tCrushW = tImg.crushW||tImg.renderW,
        tCrushH = tImg.crushH||tImg.renderH;
    this.crush = {left:false,right:false,top:false,bottom:false};
    this.alls.forEach(function(model,index){
        if(model!==that) {
            var mImg = model.imgs[model.act],
                mCrushW = mImg.crushW || mImg.renderW,
                mCrushH = mImg.crushH || mImg.renderH;
            var mCenter = {x: model.position.x + mImg.renderW / 2, y: model.position.y + mImg.renderH / 2};
            if (Math.abs(tCenter.x - mCenter.x) < (tCrushW / 2 + mCrushW / 2) && Math.abs(tCenter.y - mCenter.y) < (tCrushH / 2 + mCrushH / 2)+2) {
                //if(model.constructor==Player) console.log(tCenter.y - mCenter.y > 0, tCenter.y - mCenter.y <= tCrushH / 2 + mCrushH / 2  , Math.abs(tCenter.x - mCenter.x) < (tCrushW / 2 + mCrushW / 2)-12)
                if (tCenter.x - mCenter.x > 0 && tCenter.x - mCenter.x < tCrushW / 2 + mCrushW / 2 && Math.abs(tCenter.y - mCenter.y) < (tCrushH / 2 + mCrushH / 2)) {
                    that.crush.left = true;
                }
                if (tCenter.x - mCenter.x < 0 && mCenter.x - tCenter.x < tCrushW / 2 + mCrushW / 2 && Math.abs(tCenter.y - mCenter.y) < (tCrushH / 2 + mCrushH / 2)) {
                    that.crush.right = true;
                }
                if (tCenter.y - mCenter.y > 0 && tCenter.y - mCenter.y <= tCrushH / 2 + mCrushH / 2  && Math.abs(tCenter.x - mCenter.x) < (tCrushW / 2 + mCrushW / 2)-12) {
                    that.crush.top = true;
                    that.speed.y = 0;
                    that.position.y +=1;
                }
                if (tCenter.y - mCenter.y < 0 && mCenter.y - tCenter.y < tCrushH / 2 + mCrushH / 2+2 && Math.abs(tCenter.x - mCenter.x) < (tCrushW / 2 + mCrushW / 2)-12) {
                    that.crush.bottom = true;
                    that.position.y = model.position.y-tImg.renderH;//修正碰撞之后物体的位置
                }

                that.onCrush&&that.onCrush(model);

            }
        }

    })
}
//玩家类，继承生物类
function Player(imgs,position,callbacks){
    Livings.call(this,imgs,position);
    this.speed = {x:80,y:0};
    this.lifes = 3;
    this.act="moveR";
    this.onCrush = callbacks.onCrush;
	this.isMeet = -1;
	this.isEnd = -1;
}
inheritPrototype(Player,Livings);
Player.prototype.die =function(){
    if(this.isAlive) {
        this.isAlive = false;
        this.speed = {x: 0, y: -400};
    }
}
Player.prototype.meet =function(){
    //if(!this.isMeet) {
        this.isMeet = true;
		this.speed = {x: 80, y: 0};
    //}
}
Player.prototype.change =function(status){
	if(status ==1 ){
		this.imgs={
        moveR:{img:resources.get("togetherR.png"),x:0,spiritW:32,renderW:DEFLENGTH,renderH:DEFLENGTH,crushW:0.8*DEFLENGTH},
        moveL:{img:resources.get("togetherL.png"),x:0,spiritW:32,renderW:DEFLENGTH,renderH:DEFLENGTH,crushW:0.8*DEFLENGTH},
        jumpR:{img:resources.get("togetherJR.png"),x:0,renderW:DEFLENGTH,renderH:DEFLENGTH},
        jumpL:{img:resources.get("togetherJL.png"),x:0,renderW:DEFLENGTH,renderH:DEFLENGTH}
		}
	}else{
		this.imgs={
        moveR:{img:resources.get("singleR.png"),x:0,spiritW:32,renderW:DEFLENGTH,renderH:DEFLENGTH,crushW:0.8*DEFLENGTH},
        moveL:{img:resources.get("singleL.png"),x:0,spiritW:32,renderW:DEFLENGTH,renderH:DEFLENGTH,crushW:0.8*DEFLENGTH},
        jumpR:{img:resources.get("singleJR.png"),x:0,renderW:DEFLENGTH,renderH:DEFLENGTH},
        jumpL:{img:resources.get("singleJL.png"),x:0,renderW:DEFLENGTH,renderH:DEFLENGTH}
		}
	}
	//console.log(this.position)
}
Player.prototype.update = function(control,canvas,interTime,loop){//更新状态
    var moveX=0,moveY= 0,direction = this.act.slice(-1),ctx = canvas.getContext("2d");
    // 到达伊藤园，进入待见面状态
	if(this.position.x>800&&this.position.x<930&&this.isMeet==-1){
		this.isMeet=0
		return;
	}
	// 见面成功
	if(this.position.x>=930&&this.isMeet==0){
		this.isMeet=1
	}
	
	if(this.isMeet==0){
        moveX = this.speed.x * interTime / 1000;
        direction = "R";
        this.spirit("move"+direction)
		this.move(moveX, moveY)
		this.gravity(400, interTime / 500)
	}

	// 见面中游戏暂停，展示动画
	if(this.isMeet==0||this.isMeet==1){
		return;
	}
	// 牵手成功
	else if(this.isMeet==2){
		this.change(1);
		this.isMeet=3;
	}
	// 进入结束动画	
	if(this.position.x>10400&&this.position.x<10600&&this.isEnd==-1){
		this.isEnd=0
	}
	// 见面成功
	if(this.position.x>=10600&&this.isEnd==0){
		this.isEnd=1;
		this.change(0);
	}
	if(this.isEnd==0){
        moveX = this.speed.x * interTime / 1000;
        direction = "R";
        this.spirit("move"+direction)
		this.move(moveX, moveY)
		this.gravity(400, interTime / 500)
	}
	if(this.isEnd==2){
		this.isEnd=3;
	}
	// 见面中游戏暂停，展示动画
	if(this.isEnd!=-1){
		return;
	}
	//控制左右行走，跳跃
    if(control.status.left&&this.position.x>0){
        moveX = -this.speed.x * interTime / 300;
        direction = "L";
        this.spirit("move"+direction)
    }
    if(control.status.right){
        if (this.position.x-this.map.x < canvas.width / 2) {
            moveX = this.speed.x * interTime / 300;
            direction = "R";
        }
        this.spirit("move"+direction)
    }
    if(control.status.jump){
        if(this.crush.bottom) {
            this.speed.y = -256;
            moveY = this.speed.y * interTime / 500;
        }
    }
    if(this.crush.bottom){
        this.act = "move"+direction;
    }
    else{
        this.act = "jump"+direction;
    }

    this.move(moveX, moveY)
    this.gravity(400, interTime / 500)
    if(this.position.y>CHEIGHT){
        this.die();
    }
    if(!this.isAlive){
        loop.info.gameover=true;
    }
    //console.log(this.position)
}
//camera类，用于渲染游戏画面
function Camera(canvas){
    this.ctx = canvas.getContext("2d");
}
// 场景绘制
Camera.prototype.drawBackground = function(map){
    var canvas = this.ctx.canvas;
	//console.log(Math.floor(map.x/canvas.width))
    if(Math.floor(map.x/canvas.width)>=0&&Math.floor(map.x/canvas.width)<map.scenes.length){
		this.ctx.drawImage(map.scenes[Math.floor(map.x/canvas.width)],0,0,709,600,-(map.x%canvas.width),0,canvas.width,canvas.height)
	}else{
	   this.ctx.drawImage(map.background,0,0,709,600,-(map.x%canvas.width),0,canvas.width,canvas.height)
	}
	if(Math.floor(map.x/canvas.width)>=0&&Math.floor(map.x/canvas.width)<map.scenes.length-1){
		this.ctx.drawImage(map.scenes[Math.floor(map.x/canvas.width)+1],0,0,709,600,canvas.width-(map.x%canvas.width),0,canvas.width,canvas.height)
	}else{
		this.ctx.drawImage(map.background,0,0,709,600,canvas.width-(map.x%canvas.width),0,canvas.width,canvas.height)
	}
	//console.log(map.x)

}
Camera.prototype.drawLivings = function(livings,map){
    var ctx = this.ctx;
    livings.forEach(function(item,index){
        var actImg = item.imgs[item.act];
        var renderW = actImg.renderW||actImg.img.width,
            renderH = actImg.renderH||actImg.img.height,
            spiritW = actImg.spiritW||actImg.img.width;
        //console.log(actImg.img,actImg.x,0,spiritW,actImg.img.height,item.position.x,item.position.y,renderW,renderH)
        ctx.drawImage(actImg.img,actImg.x,0,spiritW,actImg.img.height,item.position.x-map.x,item.position.y,renderW,renderH)
    })
}
Camera.prototype.drawModels = function(models,map){
    var ctx = this.ctx;
    models.forEach(function(item,index){
        var actImg = item.imgs[item.act];
        var renderW = actImg.renderW||actImg.img.width,
            renderH = actImg.renderH||actImg.img.height;
        ctx.drawImage(actImg.img,item.position.x-map.x,item.position.y,renderW,renderH)
    })
}
Camera.prototype.drawGameInfos = function(infos){
    var ctx = this.ctx;
    if(infos.gameover){
        ctx.font = "30px Arial";
        ctx.fillStyle="#f00";
        ctx.fillText('Game Over',130,180);
    }
}
Camera.prototype.drawPrincess = function(player,princessData,map){
    var ctx = this.ctx;
	if(player.isEnd>=1){
		var actImg = princessData.imgs["default"];
			var renderW = actImg.renderW||actImg.img.width,
				renderH = actImg.renderH||actImg.img.height;
			ctx.drawImage(actImg.img,10700-map.x,princessData.position.y,renderW,renderH)
	}
	else{
		// 没有离开场景时，一直绘制公主
		if(player.isMeet !=3 ){
			var actImg = princessData.imgs["default"];
			var renderW = actImg.renderW||actImg.img.width,
				renderH = actImg.renderH||actImg.img.height;
			ctx.drawImage(actImg.img,princessData.position.x-map.x,princessData.position.y,renderW,renderH)
		} 
	}
}
Camera.prototype.drawLove = function(player,heartData,map){
    var ctx = this.ctx;
	if(player.isMeet == 1){
		var actImg = heartData.imgs["default"];
        var renderW = actImg.renderW||actImg.img.width,
            renderH = actImg.renderH||actImg.img.height;
		ctx.drawImage(actImg.img,heartData.position.x-map.x,heartData.position.y-TIMECOUNT,renderW,renderH)
		TIMECOUNT++;
		if(TIMECOUNT == 30){
			TIMECOUNT =0;
			player.isMeet = 2;
		}
	} 
}
Camera.prototype.drawDimond = function(player,dimondData,map){
    var ctx = this.ctx;
	if(player.isEnd >= 1){
		var actImg = dimondData.imgs["default"];
        var renderW = actImg.renderW||actImg.img.width,
            renderH = actImg.renderH||actImg.img.height;
		ctx.drawImage(actImg.img,dimondData.position.x-map.x,dimondData.position.y-TIMECOUNT,renderW,renderH)
		if(TIMECOUNT < 25){
			TIMECOUNT++;
		}
		else{
			player.isEnd = 2;
		}
	} 
}
Camera.prototype.drawController = function(){
    var ctx = this.ctx;
	//圆1  
	ctx.beginPath();  
	ctx.arc(40,315,25,0,Math.PI*2,false);  
	ctx.fillStyle="rgba(77,80,192,0.5)";//半透明的红色  
	ctx.fill();  
	ctx.strokeStyle="rgba(77,80,192,1)";//红色  
	ctx.stroke();  
	
	ctx.beginPath();  
	ctx.arc(100,315,25,0,Math.PI*2,false);  
	ctx.fillStyle="rgba(77,80,192,0.5)";//半透明的红色  
	ctx.fill();  
	ctx.strokeStyle="rgba(77,80,192,1)";//红色  
	ctx.stroke();  
	
	ctx.beginPath();  
	ctx.arc(355,315,25,0,Math.PI*2,false);  
	ctx.fillStyle="rgba(77,80,192,0.5)";//半透明的红色  
	ctx.fill();  
	ctx.strokeStyle="rgba(77,80,192,1)";//红色  
	ctx.stroke();  
}
Camera.prototype.drawEnd = function(){
	a.font = u + y;
    X = (w / 3 * n(z(T), 3) + w / 2);
    Y = 0.8 * (-h / 40 * (13 * d(T) - 5 * d(2 * T) - 2 * d(3 * T) - d(4 * T)) + h / 2.3);
    T += (z(T) + 3) / 99;
    for (i = 0; i < 350; i++) {
        with(M[i]) {
            A = (x / w - 0.5)*1 ,
            B = -(y / h - 0.5);
            if (L && (A * A + 2 * n((B - 0.5 * n(M.abs(A), 0.5)), 2)) > 0.11) {
                k = l = 0
            }
            d()
        }
    }
	a.font = 30 + y;

	if (E > 0.98) {
		EE = - 0.02;
	}
    if (E < 0.6) {
        EE = 0.02;
    }
	E += EE;
	a.fillStyle = F + E + ")";
	a.fillText("merry me!", w / 2, h *0.35)
    a.fillStyle = "#E93D6D";
    a.fillText(v, X, Y + u);
    a.strokeText(v, X, Y + u)
	
}
//游戏循环类，用于控制游戏循环
function GameLoop(callback,fps){
    var fps = fps||60;
    this.callback = callback;
    this.lastTime = 0;
    this.interval = 1000/fps;
    this.info = {};
}

GameLoop.prototype.frame = function(time){
    var interTime = time - this.lastTime;
    if(interTime>this.interval){
        this.callback(interTime)
        this.lastTime = time;
    }
    requestAnimationFrame(this.frame.bind(this))
}

//获取参与渲染计算的模型
function getRenderModels(models,map){
    var arr = [];
    models.forEach(function(item,index){
       if(item.position.x-map.x >= -DEFLENGTH && item.position.x-map.x <= CWIDTH){
           arr.push(item)
       }

    })
    return arr;
}

//组合寄生式继承类的原型
function inheritPrototype(subType,superType){
    var prototype = Object.create(superType.prototype);
    prototype.constructor = subType;
    subType.prototype = prototype;
}
//对象深复制
function deepClone(obj){
    var clone = obj.constructor==Object?{}:[];
    for(var i in obj){
        if(obj[i].constructor == Object||obj[i].constructor == Array){
            clone[i] = deepClone(obj[i]);
        }else{
            clone[i] = obj[i]
        }
    }
    return clone;
}