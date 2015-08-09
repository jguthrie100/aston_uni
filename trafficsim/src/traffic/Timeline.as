package traffic {
	internal class Timeline {
		private var timeArr:Array;
		
		public function Timeline() {
			timeArr = new Array();			
		}
		
		public function setTimeSlot(msg:ClaimMessage) {
			for(var i:int = msg.getArrivalTime(); i <= msg.getExitTime(); i++) {
				timeArr[i] = msg.getVin();
			}
		}
		
		public function getTimeSlot(i:int) {
			if(timeArr.length >= i) {
				return timeArr[i];
			}
			return null;
		}
	}
}