package compreter.parsertree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IdentifierTable {
	HashMap<String, Identifier> hm = null;
	
	public IdentifierTable(){
		hm = new HashMap<String, Identifier>();
	}
	
	public void addIdentifier(Identifier i){
		hm.put(i.getNewName(), i);
		hm.put(i.getOldName(), i);
	}
	
	public boolean isExist(String i){
		return hm.containsKey(i);
	}
	
	public Identifier getIdentifier(String name){
		return hm.get(name);
	}
	
	public Object[] getAllIdentifier(){
		ArrayList<String> ids = new ArrayList<String>();
		for (Map.Entry<String, Identifier> entry : hm.entrySet()) {
		    Identifier id = entry.getValue();
		    
		    if(!id.getBlock().contains(":") && !ids.contains(id.getNewName(true))){
		    	ids.add(id.getNewName(true));
		    }
		}
		
		return ids.toArray();
	}
}
