package master2015.structures;

import java.util.ArrayList;
import java.util.List;

public class TimeWindow implements Comparable<TimeWindow> {

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
		Long window = (long) TimeWindow.size;
		while(!(currentTs < window)){
			window += TimeWindow.advance;
		}
		Long prevWindow = window - TimeWindow.size;
		int nAdvances = (int) Math.floor((currentTs - prevWindow) / TimeWindow.advance);
		TimeWindow tw;
		for(int i = 0; i<=nAdvances; i++){
			tw = new TimeWindow(language,i*TimeWindow.advance+window);
			tws.add(tw);
		}
		return tws;
	}
	
	public String getLanguage() {
		return language;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	@Override
	public int compareTo(TimeWindow o) {
		return this.timestamp.compareTo(o.getTimestamp());
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(!obj.getClass().equals(this.getClass())) {
			return false;
		}
		
		TimeWindow o = (TimeWindow) obj;
		
		return this.language.equals(o.getLanguage()) && this.timestamp.equals(o.getTimestamp());
	}
}
