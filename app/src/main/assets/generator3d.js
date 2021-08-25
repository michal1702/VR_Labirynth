let tmp;
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
		let posEnt = Math.floor(Math.random() * 4);
		let cellEnt = 0;
		if (posEnt == 0) {
			cellEnt = Math.floor(Math.random() * this.ncols);
			this.grid[0][cellEnt].walls.tWall = false;
		} else if (posEnt == 1) {
			cellEnt = Math.floor(Math.random() * this.nrows);
			this.grid[cellEnt][this.ncols - 1].walls.rWall = false;
		} else if (posEnt == 2) {
			cellEnt = Math.floor(Math.random() * this.ncols);
			this.grid[this.nrows - 1][cellEnt].walls.bWall = false;
		} else if (posEnt == 3) {
			cellEnt = Math.floor(Math.random() * this.nrows);
			this.grid[cellEnt][0].walls.lWall = false;
		}
		let posEx = 0;
		let cellEx = 0;
		do {
			posEx = Math.floor(Math.random() * 4);
			if (posEx % 2 == 0) {
				cellEx = Math.floor(Math.random() * this.ncols);
			} else {
				cellEx = Math.floor(Math.random() * this.nrows);
			}
		} while (posEx == posEnt && cellEx == cellEnt);
		// TODO: Validate by checking if exit is reachable from entrance (maze-solving algorithm)
		if (posEx == 0) {
			this.grid[0][cellEx].walls.tWall = false;
		} else if (posEx == 1) {
			this.grid[cellEx][this.ncols - 1].walls.rWall = false;
		} else if (posEx == 2) {
			this.grid[this.nrows - 1][cellEx].walls.bWall = false;
		} else if (posEx == 3) {
			this.grid[cellEx][0].walls.lWall = false;
		}
	}
	generateMap() {
		let map = [];
		for (let r = 0; r < this.nrows; r++) {
			let mapRow = [];
			for (let c = 0; c < this.ncols; c++) {
				let grid = this.grid;
				let val = 0;
				if (grid[r][c].walls.tWall === true) {
					val += 1;
				}
				if (grid[r][c].walls.rWall === true) {
					val += 2;
				}
				if (grid[r][c].walls.bWall === true) {
					val += 4;
				}
				if (grid[r][c].walls.lWall === true) {
					val += 8;
				}
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
		// TODO: add trap + coins flags, set them randomly with a given probability
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
		if (up && !up.reached) {
			neighbours.push(up);
		}
		if (right && !right.reached) {
			neighbours.push(right);
		}
		if (bottom && !bottom.reached) {
			neighbours.push(bottom);
		}
		if (left && !left.reached) {
			neighbours.push(left);
		}
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
    // TODO: rewrite static element generation using three.js
    // TODO: add textures
	init: function() {
		for (let r = 0; r < newMaze.nrows; r++) {
			for (let c = 0; c < newMaze.ncols; c++) {
				let tmpVal = map[r][c];
				var box = document.createElement("a-box");
				box.setAttribute("height", "4");
				box.setAttribute("width", "4");
				box.setAttribute("depth", "0.1");
				box.setAttribute("rotation", "90 0 0");
				box.setAttribute("color", "green");
				box.setAttribute("position", {
					x: 0 + c * 4,
					y: 0,
					z: 0 + r * 4
				});
				this.el.appendChild(box);
				if (tmpVal >= 8) {
					tmpVal -= 8;
					var lWall = document.createElement("a-box");
					lWall.setAttribute("height", "4");
					lWall.setAttribute("width", "4");
					lWall.setAttribute("depth", "0.1");
					lWall.setAttribute("color", "blue");
					lWall.setAttribute("rotation", "0 90 0");
					lWall.setAttribute("position", {
						x: -2 + c * 4,
						y: 2,
						z: 0 + r * 4
					});
					this.el.appendChild(lWall);
				}
				if (tmpVal >= 4) {
					tmpVal -= 4;
					if (r === newMaze.nrows - 1) {
						var bWall = document.createElement("a-box");
						bWall.setAttribute("height", "4");
						bWall.setAttribute("width", "4");
						bWall.setAttribute("depth", "0.1");
						bWall.setAttribute("color", "red");
						bWall.setAttribute("rotation", "0 0 0");
						bWall.setAttribute("position", {
							x: 0 + c * 4,
							y: 2,
							z: 2 + r * 4
						});
						this.el.appendChild(bWall);
					}
				}
				if (tmpVal >= 2) {
					tmpVal -= 2;
					if (c === newMaze.ncols - 1) {
						var rWall = document.createElement("a-box");
						rWall.setAttribute("height", "4");
						rWall.setAttribute("width", "4");
						rWall.setAttribute("depth", "0.1");
						rWall.setAttribute("color", "yellow");
						rWall.setAttribute("rotation", "0 90 0");
						rWall.setAttribute("position", {
							x: 2 + c * 4,
							y: 2,
							z: 0 + r * 4
						});
						this.el.appendChild(rWall);
					}
				}
				if (tmpVal >= 1) {
					tmpVal -= 1;
					var tWall = document.createElement("a-box");
					tWall.setAttribute("height", "4");
					tWall.setAttribute("width", "4");
					tWall.setAttribute("depth", "0.1");
					tWall.setAttribute("color", "blue");
					tWall.setAttribute("rotation", "0 0 0");
					tWall.setAttribute("position", {
						x: 0 + c * 4,
						y: 2,
						z: -2 + r * 4
					});
					this.el.appendChild(tWall);
				}
			}
		}
	}
})