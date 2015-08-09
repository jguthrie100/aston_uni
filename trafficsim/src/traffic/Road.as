package traffic {
	internal class Road {
		private var roadArray;
		private var juncA:Junction;
		private var juncB:Junction;
		private var junctionRoad = false;
		
		public function Road(numLanes:int, roadLength:int, junctionRoad:Boolean = false) {
			roadArray = new Array(numLanes);
			for(var i:int = 0; i < numLanes; i++) {
				roadArray[i] = new Lane(roadLength);
			}
			juncA = null;
			juncB = null;
			if(junctionRoad) {
				this.junctionRoad = true;
			}			
		}
		
		public function isJunctionRoad():Boolean {
			return junctionRoad;
		}
		
		/*
		   Method to return a Lane object in the road
		   Defaults to checking lane 0
		*/
		public function getLane(laneNum:int = 0):Lane {
			return roadArray[laneNum];
		}
		
		/*
		   Method to return number of lanes in the road
		*/
		public function numLanes():int {
			return roadArray.length;
		}
		
		/*
		   Returns Junction object of the junction at the start of the road
		*/
		public function getJuncA():Junction {
			return juncA;
		}
		
		/*
		   Returns Junction object of the junction at the end of the road
		*/
		public function getJuncB():Junction {
			return juncB;
		}
		
		/*
		   Sets the junction at the start of the road to the passed Junction object
		*/
		public function setJuncA(juncA:Junction):void {
			this.juncA = juncA;
		}
		
		/*
		   Sets the junction at the end of the road to the passed Junction object.
		*/
		public function setJuncB(juncB:Junction):void {
			this.juncB = juncB;
		}
		
		/*
		   Method that returns an array of all the vehicles on all the lanes in this road
		*/
		public function getVehicles():Array {
			var vehicleArray = new Array();
			for(var i:int = 0; i < numLanes(); i++) {
				if(getLane(i).getVehicles()) {
					vehicleArray = vehicleArray.concat(getLane(i).getVehicles());
				}
			}
			if(vehicleArray.length) {				
				return vehicleArray;
			} else {
				return null;
			}
		}
		
		public function moveVehicle(vehicle:Vehicle, laneNum:int, distance:int):int {
			var curPos:int = vehicle.getPos();
			var newPos:int = this.getLane(laneNum).moveVehicle(vehicle, distance);
			return newPos;
		}
	}
}