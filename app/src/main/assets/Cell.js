class Cell {
	constructor(rowIndex, colIndex, mazeGrid, coinDensity, axeDensity, trapDensity) {
		this.rowIndex = rowIndex;
		this.colIndex = colIndex;
		this.mazeGrid = mazeGrid;
		this.coinDensity = coinDensity;
		this.axeDensity = axeDensity;
		this.trapDensity = trapDensity;
		this.reached = false;
		this.walls = {
			tWall: true,
			rWall: true,
			bWall: true,
			lWall: true,
		}
		
		let rand = Math.random();
		if(rand > trapDensity) {
			if(rand < trapDensity + coinDensity) {
				this.special = specials.COIN;
			} else {
				this.special = specials.NONE;
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
		if(up && !up.reached) neighbours.push(up);
		if(right && !right.reached) neighbours.push(right);
		if(bottom && !bottom.reached) neighbours.push(bottom);
		if(left && !left.reached) neighbours.push(left);
		if(neighbours.length !== 0) {
			let temp = Math.floor(Math.random() * neighbours.length);
			return neighbours[temp];
		} else {
			return undefined;
		}
	}
	removeWall(c1, c2) {
		let x = (c1.colIndex - c2.colIndex);
		if(x == 1) {
			c1.walls.lWall = false;
			c2.walls.rWall = false;
		} else if(x == -1) {
			c1.walls.rWall = false;
			c2.walls.lWall = false;
		}
		let y = (c1.rowIndex - c2.rowIndex);
		if(y == 1) {
			c1.walls.tWall = false;
			c2.walls.bWall = false;
		} else if(y == -1) {
			c1.walls.bWall = false;
			c2.walls.tWall = false;
		}
	}
}