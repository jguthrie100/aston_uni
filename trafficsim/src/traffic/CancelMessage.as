package traffic {
	internal class CancelMessage {
		
		private var vin:int;
		private var msgId:int;
				
		public function CancelMessage(vin:int, msgId:int) {
			this.vin = vin;
			this.msgId = msgId;
		}
		
		public function getVin():int {
			return this.vin;
		}
		
		public function getMsgId():int {
			return this.msgId;
		}
	}
}