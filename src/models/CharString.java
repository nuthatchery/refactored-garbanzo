package models;

import java.util.ArrayList;
import java.util.List;

import comp.IIdentity;
import comp.IUnchangeableModel;
import comp.Identity;
import datastructures.Triple;
import datastructures.Tuple;
import relationalmodel.IModel;
import relationalmodel.RelationalModel;

public class CharString implements IUnchangeableModel {
		public static final IIdentity CHARSTRING_MODEL_ID = new Identity("charstring");
		private static final RelationalModel charstringModel = new RelationalModel().beginTransaction();
		public static final IIdentity EMPTY = new Identity("");
		public static final IIdentity STRING_REP_OF = charstringModel.addNode("StringRepOf");
		static{
			charstringModel.addEdge(STRING_REP_OF, MetaMeta.IS, MetaMeta.IS_REPRESENTATION_OF);
		}

		public static IModel getInstance() {
			return charstringModel;
		}

		public static IIdentity getIdentity() {
			return CHARSTRING_MODEL_ID;
		}

		@SuppressWarnings("unchecked")
		public <T> T getData(IIdentity node, Class<T> type) {
			if (type == String.class) {
				return (T) node.getName();
			}else {
				throw new IllegalArgumentException("No data of type " + type);
			}
		}

		@Override
		public IIdentity getId() {
			return CHARSTRING_MODEL_ID;
		}

		@Override
		public List<IIdentity> getNodes() {
			//TODO
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean hasNode(IIdentity node) {
			//TODO
			throw new UnsupportedOperationException();
		}

		@Override
		public List<Triple> getEdges() {
			//TODO
			throw new UnsupportedOperationException();
		}

		@Override
		public List<Tuple> getEdges(IIdentity from) {
			return new ArrayList<Tuple>();
		}

		@Override
		public int getNumChildren(IIdentity node) {
			return 0;
		}

		public static IIdentity GET(String string) {
			return charstringModel.addNode(string);
		}

	}
