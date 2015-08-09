package traffic {
	internal class ClaimMessage {
		
		private var arrivalRoad:Road;
		private var arrivalTime:int;
		private var exitTime:int;
		private var exitRoad:Road;
		private var vin:int;
		private var msgId:int;
		private var isStopped:Boolean;
		
		public function ClaimMessage(arrivalRoad:Road, arrivalTime:int, exitTime:int, exitRoad:Road, vin:int, msgId:int, isStopped:Boolean) {
			this.arrivalRoad = arrivalRoad;
			this.arrivalTime = arrivalTime;
			this.exitTime = exitTime;
			this.exitRoad = exitRoad;
			this.vin = vin;
			this.msgId = msgId;
			this.isStopped = isStopped;
		}
		
		public function getArrivalRoad():Road {
			return this.arrivalRoad;
		}
		
		public function getArrivalTime():int {
			return this.arrivalTime;
		}
		
		public function getExitTime():int {
			return this.exitTime;
		}
		
		public function getExitRoad():Road {
			return this.exitRoad;
		}
		
		public function getVin():int {
			return this.vin;
		}
		
		public function getMsgId():int {
			return this.msgId;
		}
		
		public function getIsStopped():Boolean {
			return this.isStopped;
		}
	}
}