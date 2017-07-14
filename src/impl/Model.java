package impl;

import comp.IIdentity;
import io.usethesource.capsule.api.TernaryRelation;
import io.usethesource.capsule.api.Triple;
import io.usethesource.vallang.ISet;
import io.usethesource.vallang.ISetRelation;
import io.usethesource.vallang.ISourceLocation;
import io.usethesource.vallang.IValueFactory;
import io.usethesource.vallang.impl.fast.ValueFactory;
import io.usethesource.vallang.type.Type;
import io.usethesource.vallang.type.TypeFactory;

public class Model {
	public static final IValueFactory VF = ValueFactory.getInstance();
	public static final TypeFactory TF = TypeFactory.getInstance();
	public static final Type tupleType = TF.tupleType(TF.sourceLocationType(), TF.sourceLocationType(), TF.sourceLocationType());
	private ISet relations;
	private TernaryRelation<IIdentity, IIdentity, IIdentity, Triple<IIdentity, IIdentity, IIdentity>> rel;
	public Model() {
		relations = VF.set(tupleType);
	}

	public ISourceLocation lookup() 
	{
		return null;
	}
}
