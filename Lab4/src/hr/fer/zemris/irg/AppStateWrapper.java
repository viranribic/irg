package hr.fer.zemris.irg;

/**
 * EAppState wrapper class providing some extended possibilities while manipulating the application states.
 * @author Viran
 *
 */
public class AppStateWrapper {

	private EAppState state=EAppState.POLYG_DEF;
	
	/**
	 * AppStateWrapper constructor.
	 * @param state Initial state.
	 */
	public AppStateWrapper(EAppState state) {
		this.state=state;
	}
	
	/**
	 * AppStateWrapper constructor.
	 */
	public AppStateWrapper() {
	}
	
	/**
	 * Change to the next state as defined by this function.
	 */
	public void nextState(){
		if (this.state.equals(EAppState.POLYG_DEF)){
			this.state=EAppState.POINT_DEF;
		}else if(this.state.equals(EAppState.POINT_DEF)){
			this.state=EAppState.POLYG_DEF;
		}else {
			throw new RuntimeException("Unsupported state.");
		}
	}
	
	/**
	 * Change to the previous state as defined by this function.
	 */
	public void prevState(){
		if (this.state.equals(EAppState.POLYG_DEF)){
			this.state=EAppState.POINT_DEF;
		}else if(this.state.equals(EAppState.POINT_DEF)){
			this.state=EAppState.POLYG_DEF;
		}else {
			throw new RuntimeException("Unsupported state.");
		}
	}
	
	/**
	 * Get the current application state.
	 * @return Application state.
	 */
	public EAppState getState(){
		return this.state;
	}
	
	/**
	 * Compare if this object is equal to another possible state.
	 * @param obj Another state.
	 * @return True if the objects are equal, false otherwise.
	 */
	public boolean equals(EAppState obj) {
		return state.equals(obj);
	}
	
	/**
	 * Compare if this object is equal to another possible state.
	 * @param obj Another state.
	 * @return True if the objects are equal, false otherwise.
	 */
	public boolean equals(AppStateWrapper obj) {
		return state.equals(obj.state);
	}
	
	
	
	@Override
	public String toString() {
		if (this.state.equals(EAppState.POLYG_DEF)){
			return "POLYG_DEF" ;
		}else if(this.state.equals(EAppState.POINT_DEF)){
			return "POINT_DEF" ;
		}else {
			return "Error" ;
		}
	}
}
