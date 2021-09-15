 if(!android.checkLoad()){
	var ourMaze = new Maze(5, 5, 0.5, 0.09, 0.01);
    	ourMaze.init();
    	ourMaze.addEntranceExit();
    	ourMaze.draw();
    	var multiArray = ourMaze.generateMap();
    	console.log(multiArray);
    	var flatArray = multiArray.flat();
    	var gameState = new GameState(0.8, 1.6, 0.8, 0, 5, flatArray, 2 * (ourMaze.ncols - 1), 2 * (ourMaze.ncols - 1))
}else {
	var posX = android.getPositionX();
	var posY = android.getPositionY();
	var posZ = android.getPositionZ();
	var points = android.getPoints();
	var hearts = android.getHearts();
	var map = android.getMap();
	var offsetX = android.getOffsetX();
	var offsetZ = android.getOffsetZ();
	var gameState = new GameState(posX, posY, posZ, points, hearts, map, offsetX, offsetZ);
}

	/*let ourMaze = new Maze(5, 5, 0.5, 0.09, 0.01);
	ourMaze.init();
	ourMaze.addEntranceExit();
	ourMaze.draw();
	var multiArray = ourMaze.generateMap();
	console.log(multiArray);
	var flatArray = multiArray.flat();
	var gameState = new GameState(0.8, 1.6, 0.8, 0, 5, flatArray, 2 * (ourMaze.ncols - 1), 2 * (ourMaze.ncols - 1))*/
	
	console.log(gameState);

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
		}
	},
	// TODO: add textures
	init: function() {
		var scene = this.el.sceneEl.object3D;
		const sideGeometry = new THREE.BoxGeometry(4, 4, 0.1);
		const wallMaterial = new THREE.MeshBasicMaterial({
			color: 0x843c24
		});
		const floorMaterial = new THREE.MeshBasicMaterial({
			color: 0xf2c5a6
		});
		const floorGeometry = new THREE.BoxGeometry(4 * ourMaze.nrows, 4 * ourMaze.ncols, 0.1);
		var floor = document.createElement("a-plane");
		floor.setAttribute("height", 4 * ourMaze.nrows);
		floor.setAttribute("width", 4 * ourMaze.ncols);
		floor.setAttribute("color", "#f2c5a6");
		floor.setAttribute("position", {
			x: 0,
			y: 0,
			z: 0
		});
		floor.setAttribute("rotation", {
			x: -90,
			y: 0,
			z: 0
		});
		this.el.appendChild(floor);
		var player = document.createElement("a-camera");
		player.setAttribute("geometry", "primitive: box; width: 0.25; height: 2; depth: 0.25");
		player.setAttribute("id", "player");
		player.setAttribute("aabb-collider", "objects: .wall, .coin, .trap; debug: false");
		player.setAttribute("camera");
		player.setAttribute("position", {
			x: 0.8 - gameState.offsetX,
			y: 1.6,
			z: 0.8 - gameState.offsetZ
		});
		this.data.posX = 0.8 - gameState.offsetX;
		this.data.posY = 1.6;
		this.data.posZ = 0.8 - gameState.offsetZ;
		this.el.appendChild(player);
		for(let r = 0; r < ourMaze.nrows; r++) {
			for(let c = 0; c < ourMaze.ncols; c++) {
				let tmpVal = gameState.map[r*5+c];
				console.log(r*5+c);
				if(tmpVal >= 64) {
					tmpVal -= 64;
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
				}
				if(tmpVal >= 32) {
					tmpVal -= 32;
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
				}
				if(tmpVal >= 16) {
					tmpVal -= 16;
					var coin = document.createElement("a-cylinder");
					coin.setAttribute("id", "coin"+"_"+r+"_"+c)
					coin.setAttribute("class", "coin");
					coin.setAttribute("height", "0.1");
					coin.setAttribute("radius", "0.5");
					coin.setAttribute("color", "#FFFF00");
					coin.setAttribute("rotation", "90 0 0");
					coin.setAttribute("animation", "property: rotation; to: 90 360 0 dur: 2000; easing: linear; loop: true;");
					coin.setAttribute("data-aabb-collider-dynamic");
					coin.setAttribute("position", {
						x: 0 + c * 4 - gameState.offsetX,
						y: 0.6,
						z: 0 + r * 4 - gameState.offsetZ
					});
					this.el.appendChild(coin);
				}
				if(tmpVal >= 8) {
					tmpVal -= 8;
					var lWall = document.createElement("a-box");
					lWall.setAttribute("class", "wall");
					lWall.setAttribute("color", "#843c24");
					lWall.setAttribute("width", "4");
					lWall.setAttribute("height", "4");
					lWall.setAttribute("depth", "0.1");
					lWall.setAttribute("rotation", "0 -90 0");
					lWall.setAttribute("position", {
						x: -2 + c * 4 - gameState.offsetX,
						y: 2,
						z: 0 + r * 4 - gameState.offsetZ
					});
					this.el.appendChild(lWall);
				}
				if(tmpVal >= 4) {
					tmpVal -= 4;
					if(r === ourMaze.nrows - 1) {
						var bWall = document.createElement("a-box");
						bWall.setAttribute("class", "wall");
						bWall.setAttribute("color", "#843c24");
						bWall.setAttribute("width", "4");
						bWall.setAttribute("height", "4");
						bWall.setAttribute("depth", "0.1");
						bWall.setAttribute("rotation", "0 0 0");
						bWall.setAttribute("position", {
							x: 0 + c * 4 - gameState.offsetX,
							y: 2,
							z: 2 + r * 4 - gameState.offsetZ
						});
						this.el.appendChild(bWall);
					}
				}
				if(tmpVal >= 2) {
					tmpVal -= 2;
					if(c === ourMaze.ncols - 1) {
						var rWall = document.createElement("a-box");
						rWall.setAttribute("class", "wall");
						rWall.setAttribute("color", "#843c24");
						rWall.setAttribute("width", "4");
						rWall.setAttribute("height", "4");
						rWall.setAttribute("depth", "0.1");
						rWall.setAttribute("rotation", "0 90 0");
						rWall.setAttribute("position", {
							x: 2 + c * 4 - gameState.offsetX,
							y: 2,
							z: 0 + r * 4 - gameState.offsetZ
						});
						this.el.appendChild(rWall);
					}
				}
				if(tmpVal >= 1) {
					tmpVal -= 1;
					var tWall = document.createElement("a-box");
					tWall.setAttribute("class", "wall");
					tWall.setAttribute("color", "#843c24");
					tWall.setAttribute("width", "4");
					tWall.setAttribute("height", "4");
					tWall.setAttribute("depth", "0.1");
					tWall.setAttribute("rotation", "0 0 0");
					tWall.setAttribute("position", {
						x: 0 + c * 4 - gameState.offsetX,
						y: 2,
						z: -2 + r * 4 - gameState.offsetZ
					});
					this.el.appendChild(tWall);
				}
			}
		}
	},
	tick: function() {
		var player = document.querySelector("#player");
		var intersectedList = player.components['aabb-collider']['intersectedEls'];
		
		if(intersectedList.length === 0) {
			this.el.setAttribute("posX", player.getAttribute("position").x);
			this.el.setAttribute("posY", player.getAttribute("position").y);
			this.el.setAttribute("posZ", player.getAttribute("position").z);
			this.data.posX = player.getAttribute("position").x;
			this.data.posY = player.getAttribute("position").y;
			this.data.posZ = player.getAttribute("position").z;
		} else {
			for(var i = 0; i < intersectedList.length; i++) {
				var intersectedType = intersectedList[i].getAttribute("class");
				if(intersectedType == "wall") {
					player.setAttribute("position", {
						x: this.data.posX,
						y: this.data.posY,
						z: this.data.posZ
					});
				} else if(intersectedType == "coin") {
					var el = intersectedList[i];
					var id = el.getAttribute("id");
					const idParts = id.split('_');
					var row = idParts[1];
					var column = idParts[2];
					gameState.updateCoins(row, column);
					console.log(row);
					console.log(column)
					console.log(gameState.map);
					console.log(el);
					el.parentNode.removeChild(el);
					console.log("success");
					intersectedList.splice(i);
					gameState.updatePoints();
					console.log(gameState.points);
				} else {
					gameState.updateHearts();
					console.log(gameState.hearts);
				}
			}
		};
		gameState.updatePosition(player.getAttribute("position").x, player.getAttribute("position").y, player.getAttribute("position").z);
	}
})

function saveGameState() {
	return gameState;
}