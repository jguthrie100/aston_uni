package traffic {
	internal class TimeCount {
		
		private var index = 0;
		
		public function TimeCount() {
		}
		
		public function incTime():int {
			index++;
			return index;
		}
		
		public function getTime():int {
			return index;
		}
	}
}