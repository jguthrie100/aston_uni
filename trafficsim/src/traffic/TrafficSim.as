package traffic {
	import flash.display.*;
	import flash.events.*;
	import flash.text.*;
	
	
	public class TrafficSim extends MovieClip {
		
		/*
		  Set up initial variables
		*/
		private var roadArray = new Array();    // A list of all the roads on the map
		private var vehicles = new Array();    // A list of all the cars on the road
		private var timer:TimeCount;
		private var index:int = 0;
		private var movement = 1;    // A boolean - 1: Animation will run    0: Animation is paused
		private var animSpeed = 10;   // Used to regulate how fast the animation plays. Higher num = slower playback
		private var nextVehicleID = 0;    // Used to set vehicle ids. Increments each time its used.
		
		private var road1:Road;
		private var road2:Road;
		private var road3:Road;
		private var road4:Road;
		private var junc1:Junction;
		
		public function TrafficSim() {
			/*
			  Add the event listeners to handle the button clicks and animation
			*/
			addEventListener(Event.ENTER_FRAME, iterate);
			startButton.addEventListener(MouseEvent.CLICK, pressStartButton);
			addCarRoad1Button.addEventListener(MouseEvent.CLICK, addCarRoad1);
			addCarRoad2Button.addEventListener(MouseEvent.CLICK, addCarRoad2);
			addCarRoad3Button.addEventListener(MouseEvent.CLICK, addCarRoad3);
			addCarRoad4Button.addEventListener(MouseEvent.CLICK, addCarRoad4);
			addCarRandomButton.addEventListener(MouseEvent.CLICK, addCarRandom);
			juncTypeButton.addEventListener(MouseEvent.CLICK, changeJuncType);
			
			road1 = new Road(2, 400);
			road2 = new Road(2, 400);
			road3 = new Road(2, 400);
			road4 = new Road(2, 400);
			
			junc1 = createJunc(4, 30);
			
			
			junc1.addRoad(road1, 0);
			junc1.addRoad(road2, 1);
			junc1.addRoad(road3, 2);
			junc1.addRoad(road4, 3);
			
			road1.getLane(0).setEndJunc(junc1);
			road1.getLane(1).setStartJunc(junc1);
			
			road2.getLane(0).setEndJunc(junc1);
			road2.getLane(1).setStartJunc(junc1);
			
			road3.getLane(0).setEndJunc(junc1);
			road3.getLane(1).setStartJunc(junc1);
			
			road4.getLane(0).setEndJunc(junc1);
			road4.getLane(1).setStartJunc(junc1);
			
			//var carr:Vehicle = new Vehicle(1, 1);
			
			
			this.roadArray.push(road1);    // Add a new road to the list of roads
			this.roadArray.push(road2);
			this.roadArray.push(road3);
			this.roadArray.push(road4);
			this.roadArray.push(junc1);
			
			timer = new TimeCount();
			
		}
		
		/*
		  When the stop/start button is pressed, this function is run
		  It switches the 'movement' variable between 0 and 1
		*/
		private function pressStartButton(e:MouseEvent) {
			switch(movement) {
				case 1:
					movement = 0;
					break;
				
				default:
					movement = 1;
			}
		}
		
		/*
		  This function gets rid of the road block when the magic button is pressed
		*/
		private function changeJuncType(e:MouseEvent) {
			junc1.changeJuncType();
		}
		
		private function createRoad(numLanes:int, roadLength:int) {
			var road = new Road(numLanes, roadLength);
			return road;
		}
		
		private function createJunc(numExits:int, size:int) {
			var junc = new Junction(numExits, size);
			return junc;
		}
		
		private function addCarRoad1(e:MouseEvent) {
			addCar(0);
		}
		
		private function addCarRoad2(e:MouseEvent) {
			addCar(1);
		}
		
		private function addCarRoad3(e:MouseEvent) {
			addCar(2);
		}
		
		private function addCarRoad4(e:MouseEvent) {
			addCar(3);
		}
		
		private function addCarRandom(e:MouseEvent) {
			var num = Math.floor(Math.random() * 4);
			addCar(num);
		}
		
		/*
		  This function is run when the 'Add Car' button is pressed
		  It creates a new object and adds it to the list of cars.
		  A car graphic is then placed on the stage ready to be animated
		*/
		private function addCar(roadNum:int) {
			// In final version this will be variable depending on the users selection of where the car should be placed
			var laneNum:int = 0;
			
			
			var maxCarSpeed:int = Math.round(7 - Math.random()*3);
			var carLength:int = 20;
			
			/*
			  This if() will set 'busy' to 1 if there is a car blocking a new car from being placed on the road
			*/
			var busy:int = 0;
			if(roadArray[roadNum].getLane(laneNum).isLaneBusy(0, carLength)) {
				busy = 1;
			}
			
			if(!busy) {    // Makes sure that there is space for the car to be placed on the road
				var car = new Vehicle(this.nextVehicleID, maxCarSpeed, carLength, timer);
				this.vehicles.push(car);    // Add the car to the list of vehicles
				this.roadArray[roadNum].getLane(laneNum).addVehicle(car, carLength);
				car.setRoad(this.roadArray[roadNum]);    // Set the car's record of what road it is on
				car.setArrivalRoad(car.getRoad());
				car.setLaneNum(laneNum);
				car.setPos(carLength);    // Set the car's own record of it's road positioning
				car.setV(car.getMaxSpeed());    // Car arrives on screen at max speed
				car.setNextRoad(junc1.getRandomRoad(car.getRoad()));
				
				/*
				   GUI manipulation
				*/
				var carObj:Car = new Car;
				carObj.name = "car"+this.nextVehicleID;
				this.nextVehicleID++;
				
				switch(roadNum) {
					case 0: carObj.x = 0; carObj.y = 410; carObj.rotation = 0; break;
					case 1: carObj.x = 840; carObj.y = 430; carObj.rotation = 180; break;
					case 2: carObj.x = 430; carObj.y = 0; carObj.rotation = 90; break;
					case 3: carObj.x = 410; carObj.y = 840; carObj.rotation = 270; break;
				}
				addChild(carObj);
			
			} else {
				//trace("Can only add one car per iteration");
			}
		}
		
		/*
		  This function controls the animation. It is called everytime the frame is refreshed
		*/
		private function iterate(event:Event) {
			if(movement) {
				if(!(index % animSpeed)) {    // higher num = slower, lower num = faster animation speed
					moveCars();
					timer.incTime();
					trace("Timer: "+timer.getTime()+", Cars: "+junc1.getNumCarsCrossed());
				}
				if(!(timer.getTime() % 100)) {
					junc1.nextGreenRoad();
				}
				if(!(timer.getTime() % 10)) {
					addCarRandom(null);
				}
				index++;
			}
			
			xText.text = "" + mouseX;
			yText.text = "" + mouseY;
			
			animSpeed = speedText.text;
			
			var rNum:Road;
			switch(Number(isBusyRoadText.text)) {
				case 1: rNum = road1; break;
				case 2: rNum = road2; break;
				case 3: rNum = road3; break;
				case 4: rNum = road4; break;
			}
			isBusyText.text = "" + rNum.getLane(Number(isBusyLaneText.text)).isLaneBusy(Number(isBusyFromText.text), Number(isBusyToText.text));
		}
		
		/*
		  This function works out how far each car should move in any given iteration
		  It also keeps the arrays and other information up to date and relevant
		*/
		private function moveCars() {
			var veh:Vehicle;
			for (var i:int=0; i < vehicles.length; i++) {    // Loop through all the cars on the road
			//	var pos = vehicles[i].getPos();    // This is the cell that the car is currently in (in the road array)
			//	var v = vehicles[i].getV();    // The velocity of the car. How many road 'units' will it drive?
			//	var gap = 0;
			
				veh = vehicles[i];
				if(veh) {
					var carObj:MovieClip = MovieClip(getChildByName("car"+i));    // Reference and move the car along the road
					carObj.posText.text = "" + veh.getPos();
					if(!veh.getRoad().getLane(veh.getLaneNum()).getEndJunc() && veh.getPos() >= veh.getRoad().getLane(veh.getLaneNum()).getLength() - 25) {
						veh.getRoad().getLane(veh.getLaneNum()).removeVehicle(veh);
						vehicles[i] = null;
						this.removeChild(carObj);
					} else {
						if(!veh.drive()) {
							veh = null;
							return;
						}
						var offsetX = 0;
						var offsetY = 0;
						var rot = 0;
						
						// Depending on which road the car is on, set the position and rotation
						switch(veh.getRoad()) {
							// Left side road
							case road1 :	if(veh.getLaneNum()) {
												offsetY = 430;
												offsetX = 400 - veh.getPos();
												rot = 180;
											} else {
												offsetY = 410;
												offsetX = veh.getPos();
												rot = 0;
											}
											break;
											
							// Right side road
							case road2 :	if(veh.getLaneNum()) {
												offsetY = 410;
												offsetX = 440 + veh.getPos();
												rot = 0;
											} else {
												offsetY = 430;
												offsetX = 840 - veh.getPos();
												rot = 180;
											}
											break;
											
							// Top road
							case road3 :	if(veh.getLaneNum()) {
												offsetX = 410;
												offsetY = 400 - veh.getPos();
												rot = 270;
											} else {
												offsetX = 430;
												offsetY = veh.getPos();
												rot = 90;
											}
											break;
											
							// Bottom road
							case road4 :	if(veh.getLaneNum()) {
												offsetX = 430;
												offsetY = 440 + veh.getPos();
												rot = 90;
											} else {
												offsetX = 410;
												offsetY = 840 - veh.getPos();
												rot = 270;
											}
											break;
											
						}
						
						carObj.x = offsetX;
						carObj.y = offsetY;
						carObj.rotation = rot;
						carObj.posText.rotation = 360 - rot;
						
						/*
						if(veh.getRoad() == road1) {
							offsetX = 0;
							carObj.x = (vehicles[i].getPos()*1+offsetX);
							carObj.y = offsetY;
						} else if(veh.getRoad() == road2) {
							offsetX = 400;
							carObj.x = (vehicles[i].getPos()*1+offsetX);
							carObj.y = offsetY;
						} else if(veh.getRoad() == road3) {
							offsetX = 400;
							carObj.rotation = 270;
							carObj.y = 510 - (vehicles[i].getPos()*1);
						} else if(veh.getRoad() == road4) {
							offsetX = 400;
							carObj.rotation = 90;
							carObj.y = 510 + (vehicles[i].getPos()*1);
						}
						*/
					}
				}
				//trace("veh: " + i + " - " + (vehicles[i].getPos() + offsetX));
			}
		}
				/*
				  Now we loop through the next few spaces infront of the car
				  We start at the space in front, and then make sure we check all the cells that the car's speed
				    will take it past
				*/
		/*
				for (var j=(pos+1); j<=(pos+1+v); j++) {
					if(!this.roadArray[j]) {    // Only continue if the next road space is free
		*/				/*
						  'gap' records the relevant space in front of the car.
						  It will only increment if we are not at top speed or acceleration potential
						*/
		/*
						if(gap < vehicles[i].getMaxSpeed() && gap <= v) {
							gap++;
						}
						vehicles[i].setV(gap);    // Set the velocity to the max gap we can use
					} else {
						vehicles[i].setV(gap);    // Set the velocity to the distance to the next car infront
						break;
					}
				}
				
				if(!Math.floor(Math.random()*2) && gap > 1) { // Lose a velocity unit 50% of the time providing we don't fully stop
					vehicles[i].setV(gap-1);
				}
			}
			
			for (i=0; i < vehicles.length; i++) {    // Loop through all the cars again
				v = vehicles[i].getV();    // We need updated values for speed and position
				pos = vehicles[i].getPos();
				
				if(v != 0) {    // If we are not stopped, then we set where the car just was, as free again
					this.roadArray[pos] = null;
					this.roadArray[pos-carLength] = null;
				}
				this.roadArray[pos+v] = 1;    // Mark the relevant piece of road as 'busy'
				this.roadArray[pos+v-carLength] = 1;
				this.vehicles[i].setPos(pos+v);    // Update the new position in the car object itself.
				
				var carObj:MovieClip = MovieClip(getChildByName("car"+i));    // Reference and move the car along the road
				carObj.x += (v*1);
			}
		}
		*/
	}
}