package traffic {
	internal class Vehicle {
		private var v:int = 0;    // Current velocity of the car
		private var pos:int = 0;    // Position of the car in the road array
		private var vin:int;
		private var road:Road;
		private var nextRoad:Road;
		private var arrivalRoad:Road;
		private var laneNum:int;
		private var isOnJunc:Boolean;
		private var juncPos:int = 0;
		private var maxSpeed:int;    // Set max Speed to a random amount between 4 and 7
		private var vehicleLength:int;    // The length of the vehicle (how many array cells it takes up)
		private var msgHandler:MessageHandler;
		private var timer:TimeCount;
		private var waitTill:int = 0;    // Used to determine how long the vehicle must stop to look at the junction
		private var timeline:Timeline;
		
		public function Vehicle(vin:int, maxSpeed:int, vehicleLength:int, timer:TimeCount) {
			this.maxSpeed = maxSpeed;
			this.vin = vin;
			this.vehicleLength = vehicleLength;
			this.msgHandler = new MessageHandler(vin);
			this.timer = timer;
			this.timeline = new Timeline();
		}
		
		public function getRoad():Road {
			return this.road;
		}
		
		public function setRoad(road:Road):void {
			this.road = road;
		}
		
		public function getArrivalRoad():Road {
			return this.arrivalRoad;
		}
		
		public function setArrivalRoad(road:Road):void {
			this.arrivalRoad = road;
		}
		
		public function onJunc():Boolean {
			return this.isOnJunc;
		}
		
		public function setOnJunc(onJunc:Boolean):void {
			this.isOnJunc = onJunc;
		}
		
		public function getLaneNum():int {
			return this.laneNum;
		}
		
		public function setLaneNum(num:int):void {
			this.laneNum = num;
		}
		
		public function nextLaneNum():int {
			if(laneNum) {
				return 0;
			} else {
				return 1;
			}
		}
		
		public function getNextRoad():Road {
			return this.nextRoad;
		}
		
		public function setNextRoad(r:Road):void {
			this.nextRoad = r;
		}
		
		public function getV():int {
			return this.v;
		}
		
		public function setV(n:int):void {
			this.v = n;
		}
		
		public function incV():void {
			this.v++;
		}
		
		public function getPos():int {
			return this.pos;
		}
		
		public function setPos(n:int):void {
			this.pos = n;
		}
		
		public function getJuncPos():int {
			return this.juncPos;
		}
		
		public function setJuncPos(pos:int) {
			this.juncPos = pos;
		}
		
		public function getMaxSpeed():int {
			return this.maxSpeed;
		}
		
		public function getVehicleLength():int {
			return this.vehicleLength;
		}
		
		public function drive():Boolean {
			var v:int = this.getV();
			var newPos:int;
			var road:Road = this.getRoad();
			var lane:Lane = road.getLane(laneNum);
			if(v >= this.getMaxSpeed()) {
				v = this.getMaxSpeed();
			} else {
				v++;
			}
			var gapAhead:int = getGapAhead(road.getLane(laneNum), v, (this.getPos() + 1));
			this.setV(gapAhead);
			
			// Work out the next position of the car
			var nextPos = this.getPos() + this.getV();
			
			// If the car's next position is on the same road, enter if() body
			if(nextPos < lane.getLength()) {

				lane.setCellGroup(this, this.getPos(), 1);    //delete car from existing position
				lane.setCellGroup(this, nextPos);    //add car to new position
				 
				 // If car is clear of the junction and is still set as being on the junction,
				 //  remove its association with the junction
				if(nextPos - this.getVehicleLength() >= -1 && this.onJunc()) {
					lane.getStartJunc().removeVehicle(this);
					this.setOnJunc(false);
					lane.getStartJunc().incNumCarsCrossed();
					//trace("Num Vehicles: "+lane.getStartJunc().getNumVehicles());
				} else if(this.onJunc()) {
					// Set car's position in the junction
					var juncPos:int = (nextPos+lane.getStartJunc().getJunctionSize());
					//lane.getEndJunc().setVehiclePos(juncPos);
					this.setJuncPos(juncPos);
					lane.getStartJunc().updateLane();
				}

				newPos = nextPos;
			} else {
				if(getLaneNum() == 1) {
					return false;
				}
				
				// When giving way, if waitTill is unset, set it to 8 units of time in the future
				if(lane.getEndJunc().getJuncType() == "giveway" && !waitTill) {
					waitTill = timer.getTime() + 8;
				}
				
				// Enter if() body if the car has waited 8 units of time at the junction
				//  (where relevant; standard waitTill == 0)
				if(timer.getTime() >= waitTill) {
					// Enter here if the next car position is past the end of the road

					// If the junction has no vehicle on it, or the current vehicle is already on it,
					//   or the car infront came from the same road (when using traffic lights), enter if() body
					if((lane.getEndJunc().getJuncType() == "giveway" && !lane.getEndJunc().getNumVehicles()) || this.onJunc()
								|| (lane.getEndJunc().getJuncType() == "trafficlights" 
									//&& lane.getEndJunc().getVehicle(0).getArrivalRoad() == this.getRoad() 
									&& this.getRoad() == lane.getEndJunc().getRoad(lane.getEndJunc().getGreenRoad()))
								|| (lane.getEndJunc().getJuncType() == "p2p"
									&& timeline.getTimeSlot(timer.getTime()) == this.vin)) {

						// Set car to be in the junction
						lane.getEndJunc().addVehicle(this);
						this.setOnJunc(true);
						
						// Set car's position in the junction
						var juncPos:int = (nextPos-lane.getLength());
						//lane.getEndJunc().setVehiclePos(juncPos);
						this.setJuncPos(juncPos);
						lane.getEndJunc().updateLane();
						
						//trace((nextPos-getLength()-1));
						//trace("Junc Pos: " + this.getEndJunc().getVehiclePos());
						//trace("Next Pos: " + nextPos);
						
						// Update position of car on the road
						lane.setCellGroup(this, this.getPos(), 1);    //delete car from existing position
						lane.setCellGroup(this, nextPos);    //add car to new position
						
						// Transfer vehicle from one road to the next if it is fully in the junction
						if(this.getJuncPos() >= 20) {
							//Remove from current road
							lane.removeVehicle(this);
							
							// Add to next road
							this.getNextRoad().getLane(this.nextLaneNum()).addVehicle(this, 1);
							this.setRoad(this.getNextRoad());
							this.setPos(1);
							this.setLaneNum(this.nextLaneNum());
							newPos = 1;
						} else {
							newPos = nextPos;
						}
						
					} else if(lane.getEndJunc().getNumVehicles()) {  // If junction is busy, wait at the end of the road
						lane.setCellGroup(this, this.getPos(), 1);    //delete car from existing position
						lane.setCellGroup(this, lane.getLength()-1);    //add car to new position
						newPos = lane.getLength()-1;
					}
				} else { // Car waits at the end of the road for 8 units of time
					lane.setCellGroup(this, this.getPos(), 1);    //delete car from existing position
					lane.setCellGroup(this, lane.getLength()-1);    //add car to new position
					newPos = lane.getLength()-1;
				}
			}
			
			
			
			//var newPos:int = road.getLane(laneNum).moveVehicle(this, this.getV());
			if(newPos) {
				this.setPos(newPos);
			}
			if(this.getPos() > road.getLane(getLaneNum()).getLength() - 40 && road.getLane(getLaneNum()).getEndJunc() && !this.onJunc()) {
				createMessage();
			}
			return true;
		}
		
		public function createMessage() {			
			
				// Work out time till the vehicle reaches the end of the road
				// distance / speed = timeleft
				var distance = road.getLane(getLaneNum()).getLength() - this.getPos();
				var arrivalTime = (distance / this.getV()) + timer.getTime();
				var exitTime = arrivalTime + 6;
				
				var i = arrivalTime;
				
				/*
				while(i<=exitTime) {
					trace("test1");
					if(timeline.getTimeSlot(i) != null) {
						arrivalTime = i+1;
						exitTime = arrivalTime + 6;
					}
					i++;
					trace("test2");
				}
				*/
				
					
				msgHandler.sendClaim(road.getLane(getLaneNum()).getEndJunc(), getRoad(), arrivalTime, exitTime, getNextRoad(), false);
				/*
				trace(vin+":SEND CLAIM");
				trace(vin+":Cur Time: " + timer.getTime());
				trace(vin+":Arr Time: " + arrivalTime);
				trace(vin+":Cur Pos: " + this.getPos());
				*/
			
		}
			
		
		// Finds out the number of free cells infront of the vehicle
		//  until the next vehicle or maxGapCheck is reached
		public function getGapAhead(lane:Lane, maxGapCheck:int, nextCheck:int = 0):int {
			var gapAhead:int = 0;  // A tally to record num of free spaces in front
			var nextSpace:int = nextCheck;  // Gets position of the array cell infront of the vehicle
			var curLane:Lane = lane;
			var gapLeft:int = maxGapCheck;   // Counts down how many spaces are left to check
			var newLaneNum;
			
			for (var i:int = nextSpace; i <= nextSpace + maxGapCheck - 1; i++)
			{
				if(i >= curLane.getLength()) {
					// Check if the next lane is the junction
					if(!this.onJunc() && lane.getEndJunc() != null) {
						// Work out available gap on the junction
						curLane = lane.getEndJunc().getLane();
						gapAhead += getGapAhead(curLane, gapLeft);
						return gapAhead;
					} else {
						// Check out the junction
						if(laneNum) {
							newLaneNum = 0;
						} else {
							newLaneNum = 1;
						}
						curLane = getNextRoad().getLane(newLaneNum);
						//trace("Gap Left: " + gapLeft);
						//trace("Gap Ahead1: " + gapAhead);
						gapAhead += getGapAhead(curLane, gapLeft);
						//trace("Gap Ahead2: " + gapAhead);
						return gapAhead;
					}
				}
						
				if(!curLane.getCell(i))
				{
					gapAhead++;
				} else
				{
					break;
				}
				gapLeft--;
			}
			
			return gapAhead;
		}
		
		
		public function receiveClaim(msg:ClaimMessage) {
			timeline.setTimeSlot(msg);
		}
		
		public function receiveCancel(msg:CancelMessage) {
			
		}
		
			
				
	}
}