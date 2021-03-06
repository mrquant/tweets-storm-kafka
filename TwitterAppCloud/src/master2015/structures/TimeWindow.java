package master2015.structures;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TimeWindow implements Comparable<TimeWindow>, Serializable {

	private static final long serialVersionUID = 7640542374945891392L;

	private static int size;
	private static int advance;
	private String language;
	private Long timestamp; //Time in seconds
	
	public TimeWindow(String language, Long timestamp) {
		this.language = language;
		this.timestamp = timestamp;
	}
	
	public static void configTimeWindow(int size, int advance){
		TimeWindow.size = size;
		TimeWindow.advance = advance;
	}
	
	public static int getSize(){
		return size;
	}
	
	public static int getAdvance(){
		return advance;
	}
	
	public static TimeWindow getTimeWindow(String language, Long timestamp){
		Long currentTs = timestamp / 1000L;
		Long window = (long) TimeWindow.size;
		while(!(currentTs < window)){
			window += TimeWindow.advance;
		}
		return new TimeWindow(language,window);
	}
	
	public static List<TimeWindow> getAllTimeWindow(String language, Long timestamp){
		List<TimeWindow> tws = new ArrayList<TimeWindow>();
		Long currentTs = timestamp / 1000L;
		Long window = getFirstTimeWindowTime(currentTs);
		Long prevWindow = window - TimeWindow.size;
		int nAdvances = (int) Math.floor((currentTs - prevWindow) / TimeWindow.advance);
		TimeWindow tw;
		for(int i = 0; i<=nAdvances; i++){
			tw = new TimeWindow(language,i*TimeWindow.advance+window);
			tws.add(tw);
		}
		return tws;
	}
	
	public static Long getFirstTimeWindowTime(Long timestamp) {
		long windowNumber;
		
		if(timestamp < TimeWindow.size) {
			return (long) TimeWindow.size; //First window
		} else {
			System.out.println("Antes de el modulo con " + TimeWindow.advance);
			Long lowerMultiple = timestamp - (timestamp % TimeWindow.advance);
			int dif = TimeWindow.size - TimeWindow.advance;
			
			//Get closer to the real window
			windowNumber = lowerMultiple / TimeWindow.advance;
			
			//Bugfix: move some windows before and apply brute force
			windowNumber -= Math.ceil(dif / TimeWindow.advance);
			
			//Little correction of the window
			long window =  ((windowNumber - 1L) * ((long)TimeWindow.advance)) + ((long)TimeWindow.size);
			while(!(timestamp < window)){
				window += TimeWindow.advance;
			}
			return window;
		}
		
	}
	
	public String getLanguage() {
		return language;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	@Override
	public int compareTo(TimeWindow o) {
		if(!(this.timestamp.equals(o.getTimestamp()))) {
			return this.timestamp.compareTo(o.getTimestamp());
		} else {
			return this.language.compareTo(o.getLanguage());
		}
		
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj == null)
			return false;
		
	    if(!(obj instanceof TimeWindow))
	    	return false;
		
		TimeWindow o = (TimeWindow) obj;
		
		return this.language.equals(o.getLanguage()) && this.timestamp.equals(o.getTimestamp());
	}
	
	@Override
	public String toString() {
		return "TimeWindow{"+this.language+","+this.timestamp+"}";
	}
	
	@Override
	public int hashCode() {
		return this.language.hashCode() * this.timestamp.hashCode();
	}
	
	
}
