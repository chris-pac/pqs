package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import edu.nyu.pqs.stopwatch.api.IStopwatch;

/**
 * The StopwatchFactory is a thread-safe factory class for IStopwatch objects.
 * It maintains references to all created IStopwatch objects and provides a
 * convenient method for getting a list of those objects.
 *
 */
public class StopwatchFactory {

  private static final ConcurrentMap<String, IStopwatch> watchMap = 
      new ConcurrentHashMap<String, IStopwatch>();
  
  /**
   * Creates and returns a new IStopwatch object
   * @param id The identifier of the new object
   * @return The new IStopwatch object
   * @throws IllegalArgumentException if <code>id</code> is empty, null, or already
   *     taken.
   */
  public static IStopwatch getStopwatch(String id) {
    if (id == null || id.isEmpty()) {
      throw new IllegalArgumentException("Invalid id- empty or null");
    }
    
    // optimization check, no need to create new stopwatch if id is taken
    if (watchMap.containsKey(id)) {
      throw new IllegalArgumentException("Stopwatch with id " + id + " already exists");
    }
    
    IStopwatch stopwatch = new SimpleStopwatch(id);    
    if (watchMap.putIfAbsent(id, stopwatch) != null) {
      throw new IllegalArgumentException("Stopwatch with id " + id + " already exists");
    }
    
    return stopwatch;
  }

  /**
   * Returns a list of all created stopwatches
   * @return a List of all created IStopwatch objects.  Returns an empty
   * list if no IStopwatches have been created.
   */
  public static List<IStopwatch> getStopwatches() {    
    return new ArrayList<IStopwatch>(watchMap.values());
  }
}
