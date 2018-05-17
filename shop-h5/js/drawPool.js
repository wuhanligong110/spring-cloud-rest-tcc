var drawPoolData = 0.0;   // 数据量
var canvas = document.getElementById("drawPool");
var ctx  = canvas.getContext("2d");
var M = Math;
var Sin = M.sin;
var Cos = M.cos;
var Sqrt = M.sqrt;
var Pow = M.pow;
var PI = M.PI;
var Round = M.round;
var oW = canvas.width = 110;
var oH = canvas.height = 110;
  // 线宽
var lineWidth = 2;
  // 大半径
var r = (oW / 2);
var cR = r - 10*lineWidth;
  ctx.beginPath();
  ctx.lineWidth = lineWidth;
  // 水波动画初始参数
var axisLength = 2*r - 16*lineWidth;  // Sin 图形长度
var unit = axisLength / 9; // 波浪宽
var range = .4 // 浪幅
var nowrange = range;  
var xoffset = 8*lineWidth; // x 轴偏移量
var sp = 0; // 周期偏移量
var nowdata = 0;
var waveupsp = 0.006; // 水波上涨速度
  // 圆动画初始参数
var arcStack = [];  // 圆栈
var bR = r-8*lineWidth;
var soffset = -(PI/2); // 圆动画起始位置
var circleLock = true; // 起始动画锁
  // 获取圆动画轨迹点集
  for(var i = soffset; i< soffset + 2*PI; i+=1/(8*PI)) {
    arcStack.push([
      r + bR * Cos(i),
      r + bR * Sin(i)
    ])
  }
  // 圆起始点
var cStartPoint = arcStack.shift();  
  ctx.strokeStyle = "#1c86d1";
  ctx.moveTo(cStartPoint[0],cStartPoint[1]);
  // 开始渲染
  render();  
  function drawSine () {
    ctx.beginPath();
    ctx.save();
    var Stack = []; // 记录起始点和终点坐标
    for (var i = xoffset; i<=xoffset + axisLength; i+=20/axisLength) {
      var x = sp + (xoffset + i) / unit;
      var y = Sin(x) * nowrange;
      var dx = i;
      var dy = 2*cR*(1-nowdata) + (r - cR) - (unit * y);
      ctx.lineTo(dx, dy);
      Stack.push([dx,dy])
    }
    // 获取初始点和结束点
    var startP = Stack[0]
    var endP = Stack[Stack.length - 1]
    ctx.lineTo(xoffset + axisLength,oW);
    ctx.lineTo(xoffset,oW);
    ctx.lineTo(startP[0], startP[1])
    ctx.fillStyle = "#f6b71e";
    ctx.fill();
    ctx.restore();
  }

  function drawText () {
    ctx.globalCompositeOperation = 'source-over';
    var size = 0.3*cR;
    ctx.font = 'bold ' + size + 'px Microsoft Yahei';
    txt = (drawPoolData.toFixed(4)*100).toFixed(2) + '%';
    var fonty = r + size/2;
    var fontx = r - size * 0.8;
    ctx.fillStyle = "white";
    ctx.textAlign = 'center';
   // ctx.fillText(txt, r+5, r+8)
  }
  //最外面淡黄色圈
  function drawCircle(){
    ctx.beginPath();
    ctx.lineWidth = 2;
    ctx.strokeStyle = 'gold';
    ctx.arc(r, r, cR+7, 0, 2 * Math.PI);
    ctx.stroke();
    ctx.restore();
  }
  //灰色圆圈
  function grayCircle(){
    ctx.beginPath();
    ctx.lineWidth = 1;
    ctx.strokeStyle = '#eaeaea';
    ctx.arc(r, r, cR+3, 0, 2 * Math.PI);
    ctx.stroke();
    ctx.restore();
    ctx.beginPath();
  }
  //橘黄色进度圈
  function orangeCircle(){
    ctx.beginPath();
    ctx.strokeStyle = 'yellow';
    //使用这个使圆环两端是圆弧形状
    ctx.lineCap = 'round';
    ctx.arc(r, r, cR+3,0 * (Math.PI / 180.0) - (Math.PI / 2),(nowdata * 360) * (Math.PI / 180.0) - (Math.PI / 2));
    ctx.stroke();
    ctx.save()
  }
  //裁剪中间水圈
  function clipCircle(){
    ctx.beginPath();
    ctx.arc(r, r, cR, 0, 2 * Math.PI,false);
    ctx.clip();
  }
  //渲染canvas
  function render () {
    ctx.clearRect(0,0,oW,oH);
    //最外面淡黄色圈
    drawCircle();
    //灰色圆圈  
    grayCircle();
    //橘黄色进度圈
    orangeCircle();
    //裁剪中间水圈  
    clipCircle();
    if (drawPoolData >= 0.85) {
      if (nowrange > range/4) {
        var t = range * 0.01;
        nowrange -= t;   
      }
    } else if (drawPoolData <= 0.1) {
      if (nowrange < range*1.5) {
        var t = range * 0.01;
        nowrange += t;   
      }
    } else {
      if (nowrange <= range) {
        var t = range * 0.01;
        nowrange += t;   
      }      
      if (nowrange >= range) {
        var t = range * 0.01;
        nowrange -= t;
      }
    }
    if((drawPoolData - nowdata) > 0) {
      nowdata += waveupsp;      
    }
    if((drawPoolData - nowdata) < 0){
      nowdata -= waveupsp
    }
    sp += 0.07;
    // 开始水波动画
    drawSine();
    // 写字
    drawText();  
    requestAnimationFrame(render)
  }
  

//抽奖池滚动
var ball = document.getElementById('drawPoolDiv'); //球
//速度变量
var speedX = 2;
var speedY = 3;
var poolTime = 100; //以毫秒为单位
//偏移的总量
var poolX = 0;
var poolY = 0;

var pooltimer = null;

function ballMove() {
    poolX += speedX;
    poolY += speedY;
    var ky = document.body.clientHeight - ball.offsetHeight;
    var kx = document.body.clientWidth  - ball.offsetWidth;
    if (poolY >= ky || poolY <= 0) {
        speedY = -speedY;
    }
	//console.log("poolX="+poolX+"	kx="+kx);
    if (poolX >= kx || poolX <= 0) {
        speedX = -speedX
    }

    ball.style.transform = 'translate(' + poolX + 'px,' + poolY + 'px)'
}
//球自动移动 定时器
function autoMove() {
    pooltimer = setInterval('ballMove()', poolTime);
}

function clear() {
    clearInterval(pooltimer);
}
ball.onmouseout = autoMove;
ball.onmouseover = clear;
autoMove();