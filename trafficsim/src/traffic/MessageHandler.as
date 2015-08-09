package traffic {
	internal class MessageHandler {
		
		private var vin:int;
		private var msgId:int = 0;
		
		public function MessageHandler(vin:int) {
			this.vin = vin;
		}
		
		
		
		// Returns true if message is sent
		public function sendClaim(junc:Junction, arrivalRoad:Road, arrivalTime:int, exitTime:int, exitRoad:Road, isStopped:Boolean):Boolean {
			var claimMsg = new ClaimMessage(arrivalRoad, arrivalTime, exitTime, exitRoad, vin, msgId, isStopped);
			var vArray:Array = new Array();
			vArray = vArray.concat(junc.getVehicles());
			
			for(var key:String in vArray) {
				vArray[key].receiveClaim(claimMsg);
			}
			return true;
		}
		
		// Returns true if message is sent
		public function sendCancel(junc:Junction):Boolean {
			var cancelMsg = new CancelMessage(vin, msgId);
			var vArray:Array = junc.getVehicles();
			
			for(var key:String in vArray) {
				vArray[key].receiveCancel(cancelMsg);
			}
			return true;
		}
		
		
	}
}