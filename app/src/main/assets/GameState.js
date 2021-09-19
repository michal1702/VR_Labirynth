class GameState {
	constructor(positionX, positionY, positionZ, points, hearts, map, columns, rows) {
		this.columns = columns;
		this.rows = rows;
		this.positionX = positionX;
		this.positionY = positionY;
		this.positionZ = positionZ;
		this.points = points;
		this.hearts = hearts;
		this.map = map;
	}
	updatePosition(posX, posY, posZ) {
		this.positionX = posX;
		this.positionY = posY;
		this.positionZ = posZ;
	}
	updatePoints() {
		this.points++;
	}
	updateHearts() {
		this.hearts--;
	}
	updateCoins(index){
		this.map[index] =  this.map[index] - 16;
	}
}