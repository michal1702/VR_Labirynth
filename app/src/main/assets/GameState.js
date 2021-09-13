class GameState {
	constructor(positionX, positionY, positionZ, points, hearts, map, offsetX, offsetZ) {
		this.offsetX = offsetX;
		this.offsetZ = offsetZ;
		this.positionX = positionX - offsetX;
		this.positionY = positionY;
		this.positionZ = positionZ - offsetZ;
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
}