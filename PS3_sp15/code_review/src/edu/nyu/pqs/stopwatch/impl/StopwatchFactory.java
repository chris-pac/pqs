package edu.nyu.pqs.stopwatch.impl;

import java.util.*;

import edu.nyu.pqs.stopwatch.api.IStopwatch;

/**
 * The StopwatchFactory is a thread-safe factory class for IStopwatch objects.
 * It maintains references to all created IStopwatch objects and provides a
 * convenient method for getting a list of those objects.
 * @author Zhenwei Gu
 * @version 1.0
 */
public class StopwatchFactory {
	
	// Create an ArrayList to hold the IStopwathes created by getStopwatch.
	static ArrayList<IStopwatch> myStopwatchList = new ArrayList<IStopwatch>();
	
	// Create a HashMap, for each item in this HashMap, the key is the watchID of IStopwatch,
	// and the value is the count of IStopwatch of this watchID.
	static HashMap<String,Integer> idCountMap = new HashMap<String,Integer>();
	
	/**
	 * Creates and returns a new IStopwatch object
	 * @param id The identifier of the new object
	 * @return The new IStopwatch object
	 * @throws IllegalArgumentException if <code>id</code> is empty, null, or already
   *     taken.
	 */
	public static IStopwatch getStopwatch(String id) {
			
		synchronized(StopwatchFactory.class) {
			
			// Throw IllegalArgumentException if id is empty, null, or already taken.
			if(id == null) {
				throw new IllegalArgumentException("ID is null!");
			}
			else if(id == "") {
				throw new IllegalArgumentException("ID is empty!");
			}
			else if(idCountMap.containsKey(id)) {
				throw new IllegalArgumentException("This ID is already taken!");
			}
			
			// Create a new Stopwatch with the given id, add this id to idCountMap.
			else {
				Stopwatch myStopwatch = new Stopwatch();
				myStopwatch.watchID = id;
				myStopwatchList.add(myStopwatch);
				idCountMap.put(id,1);
				return myStopwatch;
			}
		}
	}

	/**
	 * Returns a list of all created stopwatches
	 * @return a List of al creates IStopwatch objects.  Returns an empty
	 * list if no IStopwatches have been created.
	 */
	public static List<IStopwatch> getStopwatches() {
		synchronized(StopwatchFactory.class) {
			return myStopwatchList;
		}
	}
}


/**
 * The Stopwatch is a thread-safe class that inherits the IStopwatch interface.
 * It contains basic methods of a stopwatch for timing laps. Different threads can
 * share a single stopwatch object and safely call any of the stopwatch methods.
 * objects are created in the StopwatchFactory.  
 * @author Skywalker
 * @version 1.0
 */
class Stopwatch implements IStopwatch {
	
	// id of the Stopwatch
	public String watchID;
	
	// recorded start time of Stopwatch
	private long startTime;
	
	// recorded stop time of Stopwatch
	private long stopTime;
	
	// true if Stopwatch is running, false if not
	private boolean isRunning;
	
	// an ArrayList to record the laps time Stopwatch has recorded
	private ArrayList<Long> Laps;
	
	/**
	 * Constructor of Stopwatch class.
	 */
	public Stopwatch() {
		watchID = null;
		startTime = 0;
		stopTime = 0; 
		isRunning = false;
		Laps = new ArrayList<Long>();
	}
	
	/**
	 * Returns the Id of this stopwatch
	 * @return the Id of this stopwatch.  Will never be empty or null.
	 * @throws IllegalArgumentException if watchID is null or empty.
	 */
	public String getId() {
		synchronized(this) {
			
			// throw IllegalArgumentException if watchID is null or empty
			if(watchID == null){
				throw new IllegalArgumentException("ID is null!");
			}
			else if(watchID==""){
				throw new IllegalArgumentException("ID is empty!");
			}
			
			// return the watchID of the Stopwatch
			return watchID;
		}
	}
	
	/**
	 * Starts the stopwatch.
	 * @throws IllegalStateException if called when the stopwatch is already running
	 */
	public void start() {
		synchronized(this) {
			
			// throw IllegalStateException if Stopwatch is already running
			if(isRunning) {
				throw new IllegalStateException("The stopwatch is already running!");
			}
			
			// take the current time of the system in milli second as the start time of Stopwatch
			else {
				isRunning = true;
				startTime = System.currentTimeMillis();
			}
		}
	}
	
	/**
	 * Stores the time elapsed since the last time lap() was called
	 * or since start() was called if this is the first lap.
	 * (If lap() is called right after start() or lap(),
	 * the current lap time around 0 will still be recorded). 
	 * @throws IllegalStateException if called when the stopwatch isn't running
	 */
	public void lap() {
		synchronized(this) {
			
			// throw IllegalStateException if Stopwatch is not running
			if(!isRunning) {
				throw new IllegalStateException("Cannot lap a stopwatch that is not running!");
			}
			
			// take the current time of the system in milli second as the stop time, 
			// minus it by the start time to get the lap time, and add lap time to Laps
			else{
				stopTime = System.currentTimeMillis();
				Laps.add(stopTime - startTime);
				startTime = stopTime;
			}
		}
	}
	
	/**
	 * Stops the stopwatch (and records one final lap,
	 * If stop() is called right after start() or lap(), 
	 * the final lap time around 0 will still be recorded).
	 * @throws IllegalStateException if called when the stopwatch isn't running
	 */
	public void stop() {
		synchronized(this) {
			
			// throw IllegalStateException if Stopwatch is not running
			if(!isRunning) {
				throw new IllegalStateException("Cannot lap a stopwatch that is not running!");
			}
			
			// take the current time of the system in milli second as the stop time, 
			// minus it by the start time to get the lap time of the final lap, 
			// add this lap time to Laps EVEN when the last lap time is around 0
			else {
				stopTime = System.currentTimeMillis();
				Laps.add(stopTime - startTime);
				isRunning = false;
			}
		}
	}
	
	/**
	 * Resets the stopwatch.  If the stopwatch is running, this method stops the
	 * watch and resets it.  This also clears all recorded laps.
	 */
	public void reset() {
		synchronized(this) {
			
			// stop the Stopwatch if it's still running
			if(isRunning) {
				stop();
				isRunning = false;
			}
			
			// delete all the recorded lap times in Laps
			Laps.clear();
		}
	}
	
	/**
	 * Returns a list of lap times (in milliseconds).  This method can be called at
	 * any time and will not throw an exception.
	 * @return a list of recorded lap times or an empty list if no times are recorded.
	 */
	public List<Long> getLapTimes() {
		synchronized(this) {
			return Laps;
		}
	}
}
