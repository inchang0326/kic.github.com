
public enum HandType {
	GAWI{
		@Override
		public String toString(){ return "가위";}
		@Override
		public HandType winValueOf(){
			return BAWI;
		}
		@Override
		public HandType failValueOf(){
			return BO;
		}
		@Override
		public HandType next(){
			return BAWI;
		}
	}, 
	BAWI{
		@Override
		public String toString(){ return "바위";}
		@Override
		public HandType winValueOf(){
			return BO;	
		}
		@Override
		public HandType failValueOf(){
			return GAWI;
		}
		@Override
		public HandType next(){
			return BO;
		}
	}, 
	BO{
		@Override
		public String toString(){ return "보";}
		@Override
		public HandType winValueOf(){
			return GAWI;
		}
		@Override
		public HandType failValueOf(){
			return BAWI;
		}
		@Override
		public HandType next(){
			return GAWI;
		}
	};
	
	public abstract HandType winValueOf();
	public abstract HandType failValueOf();
	public abstract HandType next();
	
	public static HandType valueOf(int n){
		return HandType.values()[n];
	}
}
