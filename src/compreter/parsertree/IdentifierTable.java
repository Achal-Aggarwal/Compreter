package compreter.parsertree;

import java.util.HashMap;

public class IdentifierTable {
	HashMap<String, Identifier> hm = null;
	
	public IdentifierTable(){
		hm = new HashMap<String, Identifier>();
	}
	
	public void addIdentifier(Identifier i){
		hm.put(i.getNewName(), i);
		hm.put(i.getOldName(), i);
	}
	
	public Identifier getIdentifier(String name){
		return hm.get(name);
	}
}
