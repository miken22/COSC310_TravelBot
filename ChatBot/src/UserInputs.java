import java.util.*;

/**
 * This class will store user inputs during the conversation for
 * use later throughout the conversation
 * 
 * @author Mike Nowicki
 *
 */
public class UserInputs<K,V> {
	
	public HashMap<K,V> savedInputs;
	
	public UserInputs(){
		savedInputs = new HashMap<>();
	}
	public V getValue(K k){
		return savedInputs.get(k);
	}
	public void addInput(K k, V v){
		savedInputs.put(k, v);
	}
	public void putAll(Map<? extends K, ? extends V> m){
		savedInputs.putAll(m);
	}
	public Set<Map.Entry<K,V>> getEntrySet() {

		return savedInputs.entrySet();
	}
}
