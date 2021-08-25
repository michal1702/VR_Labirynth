let tmp;
let coinDensity = 0.5;
let axeDensity = 0.09;
let spikeDensity = 0.01;
let trapDensity = axeDensity + spikeDensity;
const specials = {
    NONE: 0,
    COIN: 1,
    AXES: 2,
    SPIKES: 3
};
class Maze {
	constructor(size, nrows, ncols) {
		this.size = size;
		this.nrows = nrows;
		this.ncols = ncols;
		this.grid = [];
		this.stack = [];
	}
	init() {
		for (let r = 0; r < this.nrows; r++) {
			let row = []
			for (let c = 0; c < this.ncols; c++) {
				let cell = new Cell(r, c, this.size, this.grid);
				row.push(cell);
			}
			this.grid.push(row);
		}
		tmp = this.grid[0][0];
	}
	draw() {
		tmp.reached = true;
		let next = tmp.checkNeighbours();
		if (next) {
			next.reached = true;
			this.stack.push(tmp);
			tmp.removeWall(tmp, next);
			tmp = next;
		} else if (this.stack.length > 0) {
			let cell = this.stack.pop();
			tmp = cell;
		}
		if (this.stack.length == 0) {
			return;
		}
		this.draw();
	}
	addEntranceExit() {
		this.grid[0][0].walls.tWall = false;
		this.grid[0][0].special = specials.NONE;
		let posEx = 0;
		let indexEx = 0;
		do {
			posEx = Math.floor(Math.random() * 4);
			if (posEx % 2 == 0) {
				indexEx = Math.floor(Math.random() * this.ncols);
			} else {
				indexEx = Math.floor(Math.random() * this.nrows);
			}
		} while (posEx == 0 && indexEx == 0);
		if (posEx == 0) {
			this.grid[0][indexEx].walls.tWall = false;
			this.grid[0][indexEx].special = specials.NONE;
		} else if (posEx == 1) {
			this.grid[indexEx][this.ncols - 1].walls.rWall = false;
			this.grid[indexEx][this.ncols - 1].special = specials.NONE;
		} else if (posEx == 2) {
			this.grid[this.nrows - 1][indexEx].walls.bWall = false;
			this.grid[this.nrows - 1][indexEx].special = specials.NONE;
		} else if (posEx == 3) {
			this.grid[indexEx][0].walls.lWall = false;
			this.grid[indexEx][0].special = specials.NONE;
		}
	}
	generateMap() {
		let map = [];
		for (let r = 0; r < this.nrows; r++) {
			let mapRow = [];
			for (let c = 0; c < this.ncols; c++) {
				let grid = this.grid;
				let val = 0;
				if (grid[r][c].walls.tWall === true) val += 1;
				if (grid[r][c].walls.rWall === true) val += 2;
				if (grid[r][c].walls.bWall === true) val += 4;
				if (grid[r][c].walls.lWall === true) val += 8;
				if (grid[r][c].special === 1) val += 16;
				if (grid[r][c].special === 2) val += 32;
				if (grid[r][c].special === 3) val += 64;
				mapRow.push(val);
			}
			map.push(mapRow);
		}
		return map;
	}
}
class Cell {
	constructor(rowIndex, colIndex, mazeSize, mazeGrid) {
		this.rowIndex = rowIndex;
		this.colIndex = colIndex;
		this.mazeSize = mazeSize;
		this.mazeGrid = mazeGrid;
		this.reached = false;
		this.walls = {
			tWall: true,
			rWall: true,
			bWall: true,
			lWall: true,
		}
		let rand = Math.random();
		if(rand > trapDensity){
		    if(rand < trapDensity + coinDensity){
		        this.special = specials.COIN;
		    }
		    else {
		        this.special = specials.NONE;
		    }
		} else {
		    if(rand < axeDensity){
		        this.special = specials.AXES;
		    } else {
		        this.special = specials.SPIKES;
		    }
		}
	}
	checkNeighbours() {
		let grid = this.mazeGrid;
		let row = this.rowIndex;
		let col = this.colIndex;
		let neighbours = [];
		let up = row !== 0 ? grid[row - 1][col] : undefined;
		let right = col !== grid.length - 1 ? grid[row][col + 1] : undefined;
		let bottom = row !== grid.length - 1 ? grid[row + 1][col] : undefined;
		let left = col !== 0 ? grid[row][col - 1] : undefined;
		if (up && !up.reached) neighbours.push(up);
		if (right && !right.reached) neighbours.push(right);
		if (bottom && !bottom.reached) neighbours.push(bottom);
		if (left && !left.reached) neighbours.push(left);
		if (neighbours.length !== 0) {
			let temp = Math.floor(Math.random() * neighbours.length);
			return neighbours[temp];
		} else {
			return undefined;
		}
	}
	removeWall(c1, c2) {
		let x = (c1.colIndex - c2.colIndex);
		if (x == 1) {
			c1.walls.lWall = false;
			c2.walls.rWall = false;
		} else if (x == -1) {
			c1.walls.rWall = false;
			c2.walls.lWall = false;
		}
		let y = (c1.rowIndex - c2.rowIndex);
		if (y == 1) {
			c1.walls.tWall = false;
			c2.walls.bWall = false;
		} else if (y == -1) {
			c1.walls.bWall = false;
			c2.walls.tWall = false;
		}
	}
}
let newMaze = new Maze(600, 20, 20);
newMaze.init();
newMaze.addEntranceExit();
newMaze.draw();
let map = newMaze.generateMap();
AFRAME.registerComponent("mymaze", {
    // TODO: add textures
	init: function() {
	var scene = this.el.sceneEl.object3D;
	const sideGeometry = new THREE.BoxGeometry(4, 4, 0.1);
	const wallMaterial = new THREE.MeshBasicMaterial( {color: 0x843c24});
	const floorMaterial = new THREE.MeshBasicMaterial( {color: 0xf2c5a6});
		for (let r = 0; r < newMaze.nrows; r++) {
			for (let c = 0; c < newMaze.ncols; c++) {
				let tmpVal = map[r][c];
				const floor = new THREE.Mesh(sideGeometry, floorMaterial);
				floor.rotation.x = Math.PI / 2;
				floor.position.x = 0 + c * 4;
				floor.position.y = 0;
				floor.position.z = 0 + r * 4;
				scene.add(floor);
				if (tmpVal >= 64) {
				    tmpVal -= 64;
					for (var row = 0; row <= 9; row += 1) {
						for (var col = 0; col <= 9; col += 1) {
							var stake = document.createElement("a-cone");
							stake.setAttribute("geometry", "radiusBottom: 0.125");
							stake.setAttribute("height", "2");
							stake.setAttribute("width", "1");
							stake.setAttribute("animation", "property: object3D.position.y; to: -0.4; dur: 2000; easing: linear; loop: true; dir: alternate");
							stake.setAttribute("position", {
								x: 1.8 + c * 4 - col * 0.4,
								y: 0,
								z: 1.8 + r * 4 - row * 0.4
							});
							this.el.appendChild(stake);
						}
					}

				}
				if (tmpVal >= 32) {
				    tmpVal -= 32;
					var trunk = document.createElement("a-cylinder");
					trunk.setAttribute("height", "4");
					trunk.setAttribute("radius", "0.3");
					trunk.setAttribute("color", "#FFC65D");
					trunk.setAttribute("rotation", "0 0 0");
					trunk.setAttribute("animation", "property: rotation; to: 0 360 0 dur: 2000; easing: linear; loop: true;");
					trunk.setAttribute("position", {
						x: 0 + c * 4,
						y: 2,
						z: 0 + r * 4
					});
					for(var i = 0; i < 4; i++){
						var branch = document.createElement("a-cylinder");
						branch.setAttribute("height", "3.8");
						branch.setAttribute("radius", "0.1");
						branch.setAttribute("color", "#FFC65D");
						branch.setAttribute("rotation", "90 0 0");
						branch.setAttribute("position", {
							x: 0,
							y: -1.75 + i,
							z: 0
						});
						trunk.appendChild(branch);
					}
					this.el.appendChild(trunk);


				}
				if (tmpVal >= 16) {
				    tmpVal -= 16;
					var coin = document.createElement("a-cylinder");
					coin.setAttribute("height", "0.1");
					coin.setAttribute("radius", "0.5");
					coin.setAttribute("color", "#FFFF00");
					coin.setAttribute("rotation", "90 0 0");
					coin.setAttribute("animation", "property: rotation; to: 90 360 0 dur: 2000; easing: linear; loop: true;");
					coin.setAttribute("position", {
						x: 0 + c * 4,
						y: 0.6,
						z: 0 + r * 4
					});
					this.el.appendChild(coin);
				}
				if (tmpVal >= 8) {
					tmpVal -= 8;
					const lWall = new THREE.Mesh(sideGeometry, wallMaterial);
					lWall.rotation.y = Math.PI / 2;
					lWall.position.x = -2 + c * 4;
					lWall.position.y = 2;
					lWall.position.z = 0 + r * 4;
					scene.add(lWall);
				}
				if (tmpVal >= 4) {
					tmpVal -= 4;
					if (r === newMaze.nrows - 1) {
						const bWall = new THREE.Mesh(sideGeometry, wallMaterial);
						bWall.position.x = 0 + c * 4;
						bWall.position.y = 2;
						bWall.position.z = 2 + r * 4;
						scene.add(bWall);
					}
				}
				if (tmpVal >= 2) {
					tmpVal -= 2;
					if (c === newMaze.ncols - 1) {
						const rWall = new THREE.Mesh(sideGeometry, wallMaterial);
						rWall.rotation.y = Math.PI / 2;
						rWall.position.x = 2 + c * 4;
						rWall.position.y = 2;
						rWall.position.z = 0 + r * 4;
						scene.add(rWall);
					}
				}
				if (tmpVal >= 1) {
					tmpVal -= 1;
					const tWall = new THREE.Mesh(sideGeometry, wallMaterial);
					tWall.position.x = 0 + c * 4;
					tWall.position.y = 2;
					tWall.position.z = -2 + r * 4;
					scene.add(tWall);
				}
			}
		}
	}
})