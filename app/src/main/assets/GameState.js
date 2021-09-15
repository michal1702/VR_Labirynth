class GameState {
	constructor(positionX, positionY, positionZ, points, hearts, map, offsetX, offsetZ) {
		this.offsetX = offsetX;
		this.offsetZ = offsetZ;
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
	updateCoins(row, column){
		this.map[row][column] =  this.map[row][column] - 16;
	}
}