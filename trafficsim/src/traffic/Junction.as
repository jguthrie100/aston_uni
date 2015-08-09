package traffic {
	internal class Junction {
		
		private var jArray;
		private var junctionSize;
		private var greenRoad:int = 0;  // Road that has green light to drive
		private var vehicle:Array = new Array();
		private var juncLane:Lane;
		private var juncType:String = "trafficlights";
		private var numCarsCrossed:int = 0;
		
		public function Junction(numExits:int, size:int) {
			jArray = new Array(numExits);
			this.junctionSize = size;
			this.juncLane = new Lane(size);
		}
		
		public function changeJuncType():void {
			if(juncType == "giveway") {
				juncType = "trafficlights";
			} else if(juncType == "trafficlights") {
				juncType = "giveway";
//				juncType = "p2p";
//			} else if(juncType == "p2p") {
//				juncType = "giveway";
			}
		}
		
		public function getJuncType():String {
			return juncType;
		}
		
		public function getNumCarsCrossed():int {
			return numCarsCrossed;
		}
		
		public function incNumCarsCrossed():int {
			numCarsCrossed++;
			return numCarsCrossed;
		}
		
		public function updateLane():void {
			for(var i:int = 0; i < juncLane.getLength(); i++) {
				juncLane.setCell(i, null);
			}
			
			for(i = 0; i < vehicle.length; i++) {
				juncLane.setCellGroup(vehicle[i], vehicle[i].getJuncPos());
			}
			//if(vehicle.length) {trace("Junc Pos: "+vehicle[0].getJuncPos());}
			//trace("JuncLane: "+juncLane.toString());
		}
		
		public function nextGreenRoad():int {
			greenRoad++;
			if(greenRoad > getNumExits()-1) {
				greenRoad = 0;
			}
			return greenRoad;
		}
		
		public function getGreenRoad():int {
			return greenRoad;
		}
		
		public function setGreenRoad(i:int) {
			this.greenRoad = i;
		}
		
		public function getLane():Lane {
			return juncLane;
		}
		
		public function getJunctionSize():int {
			return this.junctionSize;
		}
		
		public function setJunctionSize(size:int):void {
			this.junctionSize = size;
		}
		
		public function getVehicle(i:int):Vehicle {
			return vehicle[i];
		}
		
		public function addVehicle(veh:Vehicle):void {
			if(getVehicleArrPos(veh) == -1) {
				this.vehicle.unshift(veh);
			}
			updateLane();
		}
		
		public function removeVehicle(veh:Vehicle):void {
			vehicle.splice(getVehicleArrPos(veh), 1);
			updateLane();
		}
		
		public function getVehicleArrPos(veh:Vehicle):int {
			for(var i:int = 0; i < vehicle.length; i++) {
				if(vehicle[i] == veh) {
					return i;
				}
			}
			return -1;
		}
		
		public function getNumVehicles():int {
			return this.vehicle.length;
		}
		/*
		public function getVehiclePos():int {
			return this.vehiclePos;
		}
		
		public function setVehiclePos(pos:int):void {
			this.vehiclePos = pos;
		}
		*/
		public function getNumExits():int {
			return jArray.length;
		}
		
		// Returns a random exit road other than the one that is passed into the method
		public function getRandomRoad(curRoad:Road):Road {
			var newRoad;
			while(!newRoad) {
				var num = Math.floor(Math.random()*getNumExits());
				//trace(num);
				newRoad = getRoad(num);
				if(newRoad == curRoad) {
					newRoad = null;
					//trace("random road was same road");
				}
			}
			
			return newRoad;
		}
		
		/*
		   Method to add a road to a junction.
		   arrayPos determines the position of the road with respect to other roads joining the junction
		   
		   eg            |
		                 |
		                 |
		                 2
		       =======1     3=======
			             0
						 |
						 |
						 |
		*/
		public function addRoad(road:Road, exitNum:int):Boolean {
			if(exitNum < jArray.length) {
				jArray[exitNum] = road;
				return true;
			}
			return false;
		}
		
		/*
		   Method that returns the Road object of the specified junction exit
		*/
		public function getRoad(exitNum:int):Road {
			if(exitNum < jArray.length) {
				return jArray[exitNum];
			} else {
				return null;
			}
		}				
		
		/*
		   Method that returns an array of all the vehicles on all the roads connecting to this junction
		*/
		public function getVehicles():Array {
			var vArray = new Array();
			for(var i:int = 0; i < jArray.length; i++) {
				if(getRoad(i).getVehicles()) {
					vArray = vArray.concat(getRoad(i).getVehicles());
				}
			}
			if(vArray.length) {
				return vArray;
			} else {
				return null;
			}
		}
	}
}