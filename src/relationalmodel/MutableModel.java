package relationalmodel;

public class MutableModel extends RelationalModel {
	
	public MutableModel(){
		super(true);
	}

	/**
	 * Creates a new MutableModel object that is a mutable copy of the argument model
	 * @param relationalModel
	 */
	public MutableModel(RelationalModel m) {
		super(m, true);
	}

	RelationalModel getImmutable(){
		return new RelationalModel(this, false);
	}
	
	@Override
	public IModel commitTransaction() {
		return new RelationalModel(this, false);
	}
}
