class Maze {
	constructor(nrows, ncols, coinDensity, axeDensity, spikeDensity) {
		this.nrows = nrows;
		this.ncols = ncols;
		this.grid = [];
		this.stack = [];
		this.tmp = [];
		this.coinDensity = coinDensity;
		this.axeDensity = axeDensity;
		this.spikeDensity = spikeDensity;
		this.trapDensity = axeDensity + spikeDensity;
	}
	init() {
		for(let r = 0; r < this.nrows; r++) {
			let row = []
			for(let c = 0; c < this.ncols; c++) {
				let cell = new Cell(r, c, this.grid, this.coinDensity, this.axeDensity, this.trapDensity);
				row.push(cell);
			}
			this.grid.push(row);
		}
		this.tmp = this.grid[0][0];
	}
	draw() {
		this.tmp.reached = true;
		let next = this.tmp.checkNeighbours();
		if(next) {
			next.reached = true;
			this.stack.push(this.tmp);
			this.tmp.removeWall(this.tmp, next);
			this.tmp = next;
		} else if(this.stack.length > 0) {
			let cell = this.stack.pop();
			this.tmp = cell;
		}
		if(this.stack.length == 0) {
			return;
		}
		this.draw();
	}
	addEntranceExit() {
		this.grid[0][0].walls.tWall = false;
		this.grid[0][0].special = specials.ENTRANCE;
		let posEx = 0;
		let indexEx = 0;
		do {
			posEx = Math.floor(Math.random() * 4);
			if(posEx % 2 == 0) {
				indexEx = Math.floor(Math.random() * this.ncols);
			} else {
				indexEx = Math.floor(Math.random() * this.nrows);
			}
		} while (posEx == 0 && indexEx == 0);
		if(posEx == 0) {
			this.grid[0][indexEx].walls.tWall = false;
			this.grid[0][indexEx].special = specials.EXIT;
		} else if(posEx == 1) {
			this.grid[indexEx][this.ncols - 1].walls.rWall = false;
			this.grid[indexEx][this.ncols - 1].special = specials.EXIT_RIGHT;
		} else if(posEx == 2) {
			this.grid[this.nrows - 1][indexEx].walls.bWall = false;
			this.grid[this.nrows - 1][indexEx].special = specials.EXIT_BOTTOM;
		} else if(posEx == 3) {
			this.grid[indexEx][0].walls.lWall = false;
			this.grid[indexEx][0].special = specials.EXIT_LEFT;
		}
	}
	generateMap() {
		let map = [];
		for(let r = 0; r < this.nrows; r++) {
			let mapRow = [];
			for(let c = 0; c < this.ncols; c++) {
				let grid = this.grid;
				let val = 0;
				if(grid[r][c].walls.tWall === true) val += 1;
				if(grid[r][c].walls.rWall === true) val += 2;
				if(grid[r][c].walls.bWall === true) val += 4;
				if(grid[r][c].walls.lWall === true) val += 8;
				if(grid[r][c].special === 1) {
					val += 16;
				} else {
					if(grid[r][c].special < 4){
						if(grid[r][c].walls.rWall && grid[r][c].walls.lWall){
							val += 64;
						} 	else if(grid[r][c].walls.tWall && grid[r][c].walls.bWall){
							val+= 32;
						}
					}
				}
				if(grid[r][c].special === 4) val += 128;
				if(grid[r][c].special === 5) val += 256;
				if(grid[r][c].special === 6) val += 512;
				if(grid[r][c].special === 7) val += 1024;
				
				
				
				mapRow.push(val);
			}
			map.push(mapRow);
		}
		return map;
	}
}