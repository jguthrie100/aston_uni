package traffic {
	internal class Lane {
		
		private var lArray;   // array representing location on the Lane
		private var vArray:Array = new Array();   // list of all Vehicles on the lane
		private var startJunc:Junction;
		private var endJunc:Junction;
		
		public function Lane(length:int) {
			lArray = new Array(length);
			
			startJunc = null;
			endJunc = null;
		}
		
		public function toString():String {
			return lArray.toString();
		}
		
		/*
		   Returns Junction object of the junction at the start of the road
		*/
		public function getStartJunc():Junction {
			return startJunc;
		}
		
		/*
		   Returns Junction object of the junction at the end of the road
		*/
		public function getEndJunc():Junction {
			return endJunc;
		}
		
		/*
		   Sets the junction at the start of the road to the passed Junction object
		*/
		public function setStartJunc(startJunc:Junction):void {
			this.startJunc = startJunc;
		}
		
		/*
		   Sets the junction at the end of the road to the passed Junction object.
		*/
		public function setEndJunc(endJunc:Junction):void {
			this.endJunc = endJunc;
		}
		
		/*
		   Adds a vehicle to the road in the specified position.
		   Returns false if the relevant part of the road is busy.
		*/
		public function addVehicle(vehicle:Vehicle, pos:int):Boolean {
			// Check to make sure that there is space for the vehicle to fit in
			for(var i:int = 0; i < pos; i++) {
				if(lArray[i]) {
					return false;
				}
			}
			//trace("pos = " + pos);
			setCellGroup(vehicle, pos);
			vArray.push(vehicle);
			return true;
		}
		
		/*
		   Removes a vehicle from the road.
		*/
		public function removeVehicle(vehicle:Vehicle):void {
			var pos:int = vehicle.getPos();
			setCellGroup(vehicle, pos, 1);
			
			// Loop through list of vehicles and remove the relevant one.
			for(var i:int = 0; i < vArray.length; i++) {
				if(vArray[i] == vehicle) {
					vArray.splice(i, 1);
				}
			}
		}
		
		/*
		   Move the vehicle into a new position in the lane.
		   Returns the new position of the car
		   Returns null if the space is busy with another car.
		*/
		public function moveVehicle(vehicle:Vehicle, distance:int):int {
			var pos:int = vehicle.getPos();
			var nextLaneNum:int;
			var nextRoad:Road;
			var nextPos = (pos+distance);
			
			if(nextPos < getLength()) {
				/*
				// Loop through the spaces infront of the car. Return null if a space contains another car
				for(var i:int = (pos+1); i <= nextPos; i++) {
					if(lArray[i]) {
						return null;
					}
				}  // --- Loop Completes = space to move into; continue...
				
				
				setCellGroup(vehicle, pos, 1);    //delete car from existing position
				setCellGroup(vehicle, nextPos);    //add car to new position
				
				
				// If vehicle is clear of the junction, remove the association with it
				if(nextPos - vehicle.getVehicleLength() >= 0 && vehicle.onJunc()) {
					this.getStartJunc().setVehicle(null);
					vehicle.setOnJunc(false);
				}
				
				return nextPos;
				*/
			} else {
				if(!this.getEndJunc().getNumVehicles() || vehicle.onJunc()) {
					/*
					
					this.getEndJunc().setVehiclePos((nextPos-getLength()-1));
					//trace((nextPos-getLength()-1));
					//trace("Junc Pos: " + this.getEndJunc().getVehiclePos());
					//trace("Next Pos: " + nextPos);
					this.getEndJunc().setVehicle(vehicle);
					vehicle.setOnJunc(true);
					vehicle.setJuncPos((nextPos-getLength()-1));
					
					setCellGroup(vehicle, pos, 1);    //delete car from existing position
					setCellGroup(vehicle, nextPos);    //add car to new position
					
					// Transfer vehicle from one road to the next if it is fully in the junction
					if(this.getEndJunc().getVehiclePos() >= vehicle.getVehicleLength()-1) {
						trace("CHANGE ROAD");
						//Remove from current road
						this.removeVehicle(vehicle);
						// Add to next road
						vehicle.getNextRoad().getLane(vehicle.nextLaneNum()).addVehicle(vehicle, 1);
						vehicle.setRoad(vehicle.getNextRoad());
						vehicle.setPos(1);
						vehicle.setLaneNum(vehicle.nextLaneNum());
						return 1;
					}
					return nextPos;
					
					*/
				} 
				
				// Wait at the entrance to the junction if it is occupied by another car
				else if(this.getEndJunc().getNumVehicles()) {
					setCellGroup(vehicle, pos, 1);    //delete car from existing position
					setCellGroup(vehicle, getLength()-1);    //add car to new position
					return getLength()-1;
				}
					
				
				/*
				nextLaneNum = vehicle.nextLaneNum();
				nextRoad = this.getEndJunc().getRandomRoad(vehicle.getRoad());
				
				if(nextRoad.getLane(nextLaneNum).isLaneBusy(0,vehicle.getVehicleLength()-1)) {
					trace("Cell value: " + nextRoad.getLane(nextLaneNum).getCell(0));
					
					// If the nextroad is busy, only drive as far as possible on the current road.
					return (moveVehicle(vehicle, getLength() - pos));
				}
				
				removeVehicle(vehicle);
				vehicle.setRoad(nextRoad);
				vehicle.setLaneNum(nextLaneNum);
				vehicle.getRoad().getLane(vehicle.getLaneNum()).addVehicle(vehicle, vehicle.getVehicleLength()-1);
				return vehicle.getVehicleLength()-1;
				*/
			}
			return null;
		}
		
		/*
		   Method that sets a whole group of cells to contain the vehicle
		   If 'remove' == 1, nullify the value of the cell instead.
		*/
		public function setCellGroup(vehicle:Vehicle, pos:int, remove:int = 0):void {
			var vehicleLength = vehicle.getVehicleLength();
			
			// Set the cells that the vehicle takes up in the array (determined by vehicle length)
			for(var i:int = pos; i > pos - vehicleLength; i--) {
				if(i < getLength() && i >= 0) {
					if(remove) {
						setCell(i, null);
					} else {
						//trace("set cell: " + i);
						setCell(i, vehicle);
					}
				}
			}
		}
		
		/*
		   Method to return the vehicle at a given location in the array
		*/
		public function getCell(cellNum:int):Vehicle {
			return lArray[cellNum];
		}
		
		/*
		   Method to say if there is a vehicle on a certain section of the road
		*/
		public function isLaneBusy(fromPos:int, toPos:int):Boolean {
			if(toPos > getLength()) {
				toPos = getLength();
			}
			for(var i:int = fromPos; i <= toPos; i++) {
				if(getCell(i)) {
					return true;
				}
			}
			return false;
		}
		
		/*
		   Sets a cell to equal a vehicle object (or null)
		*/
		public function setCell(cell:int, vehicle:Vehicle) {
			lArray[cell] = vehicle;
		}
		
		/*
		   Method to get length of the lane.
		*/
		public function getLength():int {
			return lArray.length;
		}
		
		/*
		   Method to check if a given vehicle is on the lane
		   Returns true/false if vehicle is/isn't found
		*/
		public function containsVehicle(vehicle:Vehicle):Boolean {
			for(var i:int = 0; i < vArray.length; i++) {
				if(vArray[i] == vehicle) {
					return true;
				}
			}
			return false;
		}
		
		/*
		   Method to return the array of vehicles on the lane.
		*/
		public function getVehicles() {			
			if(vArray.length) {				
				return vArray;
			} else {
				return null;
			}
		}
	}
}