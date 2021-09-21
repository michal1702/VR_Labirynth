 var gameState;

// if(!android.checkLoad()){
	var ourMaze = new Maze(5, 5, 0.5, 0.09, 0.01);
    	ourMaze.init();
    	ourMaze.addEntranceExit();
    	ourMaze.draw();
    	var multiArray = ourMaze.generateMap();
    	var flatArray = multiArray.flat();
    	gameState = new GameState(0.8 - (2 * ourMaze.ncols-1), 1.6, 0.8 - (2 * ourMaze.nrows-1), 0, 3, flatArray, ourMaze.ncols, ourMaze.nrows)
/* }else { 
	var posX = android.getPositionX();
	var posY = android.getPositionY();
	var posZ = android.getPositionZ();
	var points = android.getPoints();
	var hearts = android.getHearts();
	var rawMap = android.getMap();
	var mapEls = rawMap.split('|');
	var map = new Array();
	for(var i=0; i<mapEls.length;i++){
	    map.push(mapEls[i]);
	}
	console.log(map);
	var columns = android.getColumns();
	var rows = android.getRows();
	gameState = new GameState(posX, posY, posZ, points, hearts, map, columns, rows);
} 

	/*let ourMaze = new Maze(5, 5, 0.5, 0.09, 0.01);
	ourMaze.init();
	ourMaze.addEntranceExit();
	ourMaze.draw();
	var multiArray = ourMaze.generateMap();
	console.log(multiArray);
	var flatArray = multiArray.flat();
	var gameState = new GameState(0.8, 1.6, 0.8, 0, 5, flatArray, 2 * (gameState.columns - 1), 2 * (gameState.columns - 1))*/
	


AFRAME.registerComponent("mymaze", {
	schema: {
		posX: {
			default: gameState.positionX
		},
		posY: {
			default: gameState.positionY
		},
		posZ: {
			default: gameState.positionZ
		},
		initX: {
			default: gameState.positionX
		},
		initY: {
			default: gameState.positionY
		},
		initZ: {
			default: gameState.positionZ
		}, 
		interval: {
			default: 0
		}
	},
	// TODO: add textures
	init: function() {
		var scene = this.el.sceneEl.object3D;
	
		var floor = document.createElement("a-plane");
		floor.setAttribute("height", 4 * gameState.rows);
		floor.setAttribute("width", 4 * gameState.columns);
		floor.setAttribute("color", "#f2c5a6");
		floor.object3D.position.set(0, 0, 0);
		
		floor.setAttribute("rotation", {
			x: -90,
			y: 0,
			z: 0
		});
		this.el.appendChild(floor);
		
		//var player = document.createElement("a-camera");
		//player.setAttribute("geometry", "primitive: box; width: 0.25; height: 2; depth: 0.25");
		//player.setAttribute("id", "player");
		//player.setAttribute("aabb-collider", "objects: .wall, .coin, .trap; debug: false");
		//player.setAttribute("gamepad-controls", "controller: 0; lookEnabled: true; movementEnabled: true; invertAxisY: true");
		/*player.setAttribute("position", {
			x: gameState.positionX,
			y: 1.6,
			z: gameState.positionZ
		});*/
		this.data.posX = 0.8 - (2 * (gameState.columns-1));
		this.data.posY = 1.6;
		this.data.posZ = 0.8 - (2 * (gameState.rows-1));
	
		//this.el.appendChild(player);
		for(let r = 0; r < gameState.rows; r++) {
			for(let c = 0; c < gameState.columns; c++) {
				var hasTrap = false;
				var hasOtherTrap = false;
				var index = r*gameState.columns+c;
				let tmpVal = gameState.map[index];
				if(tmpVal >= 1024) {
					tmpVal -= 1024;
					
					var bWall = this.el.sceneEl.components.pool__entrance.requestEntity();
						bWall.setAttribute("class", "exit");
					//	bWall.setAttribute("color", "#ececec");
						bWall.object3D.position.set(0 + c * 4 - (2 * (gameState.columns-1)),
							2,
							2 + r * 4 - (2 * (gameState.rows-1)));
				}
				if(tmpVal >= 512) {
					tmpVal -= 512;
					
					var rWall = this.el.sceneEl.components.pool__exitr.requestEntity();
						rWall.setAttribute("class", "exit");
					//	rWall.setAttribute("color", "#ececec");
						rWall.object3D.position.set(2 + c * 4 - (2 * (gameState.columns-1)),
							2,
							0 + r * 4 - (2 * (gameState.rows-1)));
				}
				if(tmpVal >= 256) {
					tmpVal -= 256;
					var lWall = this.el.sceneEl.components.pool__exitl.requestEntity();
					lWall.setAttribute("class", "exit");
					lWall.setAttribute("color", "#ececec");
					
					lWall.object3D.position.set(-2 + c * 4 - (2 * (gameState.columns-1)), 2, 0 + r * 4 - (2 * (gameState.rows-1)));
				}
				if(tmpVal >= 128) {
					tmpVal -= 128;
					var tWall = this.el.sceneEl.components.pool__entrance.requestEntity();
					tWall.setAttribute("class", "wall");
				//	tWall.setAttribute('color', '#ECECEC');
				
					tWall.object3D.position.set(0 + c * 4 - (2 * (gameState.columns-1)),
						2,
						-2 + r * 4 - (2 * (gameState.rows-1)));
				}
				if(tmpVal >= 64) {
					tmpVal -= 64;
				//	hasTrap = true;
				//	console.log("trap");
					/*	for (var row = 0; row <= 9; row += 1) {
							for (var col = 0; col <= 9; col += 1) {
								var stake = document.createElement("a-cone");
								stake.setAttribute("geometry", "radiusBottom: 0.125");
								stake.setAttribute("height", "2");
								stake.setAttribute("width", "1");
								stake.setAttribute("animation", "property: object3D.position.y; to: -0.4; dur: 2000; easing: linear; loop: true; dir: alternate");
								stake.setAttribute("position", {
									x: 1.8 + c * 4 - col * 0.4 - offsetX,
									y: 0,
									z: 1.8 + r * 4 - row * 0.4 - offsetZ
								});
								this.el.appendChild(stake);
							}
						} */
						
						
						const wallX = -2 + c * 4 - (2 * (gameState.columns-1));
		
					
					
						var lTrap = document.createElement("a-box");
						lTrap.setAttribute("class", "trap");
						lTrap.setAttribute("height", "4");
						lTrap.setAttribute("width", "2");
						lTrap.setAttribute("depth", "0.1");
						lTrap.setAttribute("rotation", "0 0 0");
						lTrap.setAttribute("animation", "property: width; to: 0; dur: 2000; easing: linear; loop: true; dir: alternate");
						lTrap.setAttribute('animation__1', `property: object3D.position.x; to: ${wallX}; dur: 2000; easing: linear; loop: true; dir: alternate`);
						lTrap.object3D.position.set(-1 + c * 4 - (2 * (gameState.columns-1)), 2, 0 + r * 4 - (2 * (gameState.rows-1)));
						
						const wallXD = 2 + c * 4 - (2 * (gameState.columns-1));
						
						var rTrap = document.createElement("a-box");
						rTrap.setAttribute("class", "trap");
						rTrap.setAttribute("height", "4");
						rTrap.setAttribute("width", "2");
						rTrap.setAttribute("depth", "0.1");
						rTrap.setAttribute("rotation", "0 0 0");
						rTrap.setAttribute("animation", "property: width; to: 0; dur: 2000; easing: linear; loop: true; dir: alternate");
						rTrap.setAttribute('animation__1', `property: object3D.position.x; to: ${wallXD}; dur: 2000; easing: linear; loop: true; dir: alternate`);
						rTrap.object3D.position.set(1 + c * 4 - (2 * (gameState.columns-1)), 2, 0 + r * 4 - (2 * (gameState.rows-1)));
						
						this.el.appendChild(lTrap);
						this.el.appendChild(rTrap);
					
				}
				if(tmpVal >= 32) {
					tmpVal -= 32;
				//	hasOtherTrap = true;
				//	console.log("other-trap");
					/*	var trunk = document.createElement("a-cylinder");
						trunk.setAttribute("height", "4");
						trunk.setAttribute("radius", "0.3");
						trunk.setAttribute("color", "#FFC65D");
						trunk.setAttribute("rotation", "0 0 0");
						trunk.setAttribute("event-set__hitstart", "material.opacity: 0.5; material.color: orange");
						trunk.setAttribute("event-set__hitend", "material.opacity: 1; material.color: red");
						trunk.setAttribute("animation", "property: rotation; to: 0 360 0 dur: 2000; easing: linear; loop: true;");
						trunk.setAttribute("data-aabb-collider-dynamic");
						trunk.setAttribute("position", {
							x: 0 + c * 4 - offsetX,
							y: 2,
							z: 0 + r * 4 - offsetZ
						});
						for(var i = 0; i < 4; i++){
							var branch = document.createElement("a-cylinder");
							branch.setAttribute("height", "3.8");
							branch.setAttribute("radius", "0.1");
							branch.setAttribute("color", "#FFC65D");
							branch.setAttribute("rotation", "90 0 0");
							branch.setAttribute("data-aabb-collider-dynamic");
							branch.setAttribute("position", {
								x: 0,
								y: -1.75 + i,
								z: 0
							});
							trunk.appendChild(branch);
						}
						this.el.appendChild(trunk); */
						
						
					
						const wallZ = 2 + r * 4 - (2 * (gameState.rows-1));
							const wallZD = -2 + r * 4 - (2 * (gameState.rows-1));
					
					
						var bTrap = document.createElement("a-box");
						bTrap.setAttribute("class", "trap");
						bTrap.setAttribute("height", "4");
						bTrap.setAttribute("width", "2");
						bTrap.setAttribute("depth", "0.1");
						bTrap.setAttribute("rotation", "0 -90 0");
						bTrap.setAttribute("animation", "property: width; to: 0; dur: 2000; easing: linear; loop: true; dir: alternate");
						bTrap.setAttribute('animation__1', `property: object3D.position.z; to: ${wallZ}; dur: 2000; easing: linear; loop: true; dir: alternate`);
						bTrap.setAttribute('aabb-collider-dynamic','');
						bTrap.object3D.position.set(0 + c * 4 - (2 * (gameState.columns-1)),
							2,
							1 + r * 4 - (2 * (gameState.rows-1)));
						
					
						
						var tTrap = document.createElement("a-box");
						tTrap.setAttribute("class", "trap");
						tTrap.setAttribute("height", "4");
						tTrap.setAttribute("width", "2");
						tTrap.setAttribute("depth", "0.1");
						tTrap.setAttribute("rotation", "0 90 0");
						tTrap.setAttribute("animation", "property: width; to: 0; dur: 2000; easing: linear; loop: true; dir: alternate");
						tTrap.setAttribute('animation__1', `property: object3D.position.z; to: ${wallZD}; dur: 2000; easing: linear; loop: true; dir: alternate`);
						tTrap.object3D.position.set(0 + c * 4 - (2 * (gameState.columns-1)),
						2,
						-1 + r * 4 - (2 * (gameState.rows-1)));
						
						this.el.appendChild(tTrap);
						this.el.appendChild(bTrap);
						console.log(bTrap);
				}
				if(tmpVal >= 16) {
					tmpVal -= 16;
					var coin = this.el.sceneEl.components.pool__coins.requestEntity();
					coin.setAttribute("id", "coin"+"_"+index)
					coin.setAttribute("class", "coin");
					coin.setAttribute("animation", "property: rotation; to: 90 360 0 dur: 2000; easing: linear; loop: true;");
					coin.play();
					coin.object3D.position.set(0 + c * 4 - (2 * (gameState.columns-1)),
						0.6,
						0 + r * 4 - (2 * (gameState.rows-1)));
				}
				if(tmpVal >= 8) {
					tmpVal -= 8;
					var lWall = this.el.sceneEl.components.pool__lwalls.requestEntity();
					lWall.setAttribute("class", "wall");
						lWall.setAttribute("color", "#843c24");
					lWall.object3D.position.set(-2 + c * 4 - (2 * (gameState.columns-1)), 2, 0 + r * 4 - (2 * (gameState.rows-1)));
					
				
				}
				if(tmpVal >= 4) {
					tmpVal -= 4;
					if(r === gameState.rows - 1) {
						var bWall = this.el.sceneEl.components.pool__hwalls.requestEntity();
						bWall.setAttribute("class", "wall");
						bWall.setAttribute("color", "#843c24");
						bWall.object3D.position.set(0 + c * 4 - (2 * (gameState.columns-1)),
							2,
							2 + r * 4 - (2 * (gameState.rows-1)));
					}
				}
				if(tmpVal >= 2) {
					tmpVal -= 2;
					if(c === gameState.columns - 1) {
						var rWall = this.el.sceneEl.components.pool__rwalls.requestEntity();
						rWall.setAttribute("class", "wall");
						rWall.setAttribute("color", "#843c24");
						rWall.object3D.position.set(2 + c * 4 - (2 * (gameState.columns-1)),
							2,
							0 + r * 4 - (2 * (gameState.rows-1)));
					}
				}
				if(tmpVal >= 1) {
					tmpVal -= 1;
					var tWall = this.el.sceneEl.components.pool__hwalls.requestEntity();
					tWall.setAttribute("class", "wall");
					tWall.setAttribute("color", "#843c24");
					tWall.object3D.position.set(0 + c * 4 - (2 * (gameState.columns-1)),
						2,
						-2 + r * 4 - (2 * (gameState.rows-1)));
				}
			}
		}
	},
	tick: function() {
		var player = document.querySelector("#player");
		var intersectedList = player.components['aabb-collider']['intersectedEls'];
	//	console.log(intersectedList);
		if(intersectedList.length === 0) {
			
			this.lastPosition = {
					x: player.object3D.position.x,
					y: player.object3D.position.y,
					z: player.object3D.position.z
				}
				
		} else {
			
			for(var i = 0; i < intersectedList.length; i++) {
				var intersectedType = intersectedList[i].getAttribute("class");
				if(intersectedType == "wall") {
				//	console.log("hit");
					player.object3D.position.set(this.lastPosition.x, this.lastPosition.y, this.lastPosition.z);
				} else if(intersectedType == "coin") {
					var el = intersectedList[i];
					var id = el.getAttribute("id");
					const idParts = id.split('_');
					var index = idParts[1];
					gameState.updateCoins(index);
					this.el.sceneEl.components.pool__coins.returnEntity(el);
				//	el.parentNode.removeChild(el);
					intersectedList.splice(i);
					gameState.updatePoints();
					var coins = document.querySelector("#coinVal");
					coins.setAttribute("value", gameState.points);
				//	coins.value = gameState.points;
				//	gameState.updateHearts();
				} else if(intersectedType == "exit"){
					gameState.points += gameState.heart * 20;
					this.pause()
					android.gameOver(gameState.points);
				} 
				else {
				//	console.log("heart");
				if(this.data.interval == 0){
					player.object3D.position.set(this.data.initX, this.data.initY, this.data.initZ);
					gameState.updateHearts();
					this.data.interval+=1000;
					var hearts = document.querySelector("#heartVal");
					hearts.setAttribute("value", gameState.hearts);
				}
				}
			}
		};
		
		if(gameState.hearts <= 0){
		//	this.pause()
		//	android.gameOver(gameState.points);
		}
		
		if(this.data.interval > 0){
			this.data.interval--;
		}
		
		gameState.updatePosition(player.getAttribute("position").x, player.getAttribute("position").y, player.getAttribute("position").z);
	},
	
	//pause: function() {
	//	android.gameOver(gameState.points);
	//}
})

function saveGameState() {
	return gameState;
}
