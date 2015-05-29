package edu.nyu.pqs.stopwatch.impl;

import java.util.List;
import java.util.LinkedList;
import java.util.Collections;
import java.util.ListIterator;

import edu.nyu.pqs.stopwatch.api.IStopwatch;

/**
 * This is an unconditionally thread-safe object that can be used to obtain time 
 * span for multiple laps. Different threads can share a single stopwatch object and 
 * safely call any of the stopwatch methods.
 * 
 * This is a package-private class and can only be created in the StopwatchFactory. 
 * 
 * @author Chris Pac
 * @version 1.0
 * @since 2015-03-10
 *
 */
class SimpleStopwatch implements IStopwatch {
  private final String id;
  private boolean isWatchRunning = false;
  
  /*
   * contains (possibly not increasing) points/instances in time in nanoseconds; 
   * not time spans. 
   */
  private List<Long> lapInstanceTimes = new LinkedList<Long>();
  
  /*
   * contains weakly increasing points in time in nanoseconds
   */
  private List<Long> startTimes = new LinkedList<Long>();
  
  /*
   * synchronization lock protects isWatchRunning, lapInstanceTimes, and startTimes
   */
  private final Object watchLock = new Object();
  
  /**
   * SimpleStopwatch constructor can only be invoked within the same package.
   * 
   * @param id an identifier for this Stopwatch object
   * @throws IllegalArgumentException if <code>id</code> is empty or null
   */
  SimpleStopwatch(String id) {
    // we are guaranteeing that getId will never return empty or null so we better make sure...
    if (id == null || id.isEmpty()) {
      throw new IllegalArgumentException("Invalid id- empty or null");
    }
    
    this.id =id;
  }
  
  /**
   * Returns the identifier of this stopwatch object
   * @return the id of this SimpleStopwatch.  Will never be empty or null.
   * @see edu.nyu.pqs.stopwatch.api.IStopwatch#getId()
   */
  @Override
  public String getId() {    
    return id;
  }

  /**
   * Starts the stopwatch.
   * @throws IllegalStateException if called when the stopwatch is already running
   * @see edu.nyu.pqs.stopwatch.api.IStopwatch#start()
   */
  @Override
  public void start() {
    /* always get the time first!
     * getting the time after locking could cause us to have an
     * incorrect time if we had to wait for the lock to be released 
     */
    long start = System.nanoTime();
    
    synchronized(watchLock) {
      if (isWatchRunning) {
        throw new IllegalStateException("Stopwatch " + id + " is already running");
      }
      
      isWatchRunning = true;
      startTimes.add(start);
    }
  }

  /**
   * Stores the time elapsed since the last time lap() was called
   * or since start() was called if this is the first lap.
   * 
   * @throws IllegalStateException if called when the Stopwatch isn't running
   * @see edu.nyu.pqs.stopwatch.api.IStopwatch#lap()
   */
  @Override
  public void lap() {
    // again always get the time first!
    long lapTime = System.nanoTime();
    
    synchronized(watchLock) {
      if (!isWatchRunning) {
        throw new IllegalStateException("Stopwatch " + id + " is not running");
      }
      
      lapInstanceTimes.add(lapTime);      
    }
  }

  /**
   * Stops the Stopwatch (and records one final lap). This does not clear recorded laps and 
   * the stopwatch can be resumed by calling start.
   * 
   * @throws IllegalStateException if called when the stopwatch isn't running
   * @see edu.nyu.pqs.stopwatch.api.IStopwatch#stop()
   */
  @Override
  public void stop() {
    // and again get the time first!
    long lapTime = System.nanoTime();
    
    synchronized(watchLock) {
      if (!isWatchRunning) {
        throw new IllegalStateException("Stopwatch " + id + " is not running");
      }
      
      lapInstanceTimes.add(lapTime);
      isWatchRunning =  false;
    }
  }

  /**
   * Resets the stopwatch.  If the stopwatch is running, this method stops the
   * watch and resets it.  This also clears all recorded laps.
   * @see edu.nyu.pqs.stopwatch.api.IStopwatch#reset()
   */
  @Override
  public void reset() {
    synchronized(watchLock) {
      lapInstanceTimes.clear();
      startTimes.clear();
      isWatchRunning =  false;
    }
  }

  /**
   * This function computes the time span in milliseconds between the start time and end time.
   * This function expects the start time to be less than or equal to end time and that
   * both be in nanoseconds.
   * 
   * Since the return type is Long there will be loss of precision.
   * 
   * @param startNano start of time span in nanoseconds
   * @param endNano  end of time span in nanoseconds
   * @return the time span in milliseconds
   */
  private Long getTimeSpanInMilliseconds(Long startNano, Long endNano) {    
    return (endNano - startNano) / 1000000;
  }
  
  /**
   * Returns a list of lap times (in milliseconds).  This method can be called at
   * any time and will not throw an exception.
   * @return a list of recorded lap times or an empty list if no times are recorded.
   * @see edu.nyu.pqs.stopwatch.api.IStopwatch#getLapTimes()
   */
  @Override
  public List<Long> getLapTimes() {
    List<Long> startTimesCopy;
    List<Long> lapTimesCopy;
    
    synchronized(watchLock) {
      startTimesCopy = new LinkedList<Long>(startTimes);
      lapTimesCopy = new LinkedList<Long>(lapInstanceTimes);
    }
    
    /* Now we need to sort the lap instance times and compute the lap times.
     * Lap time is a time span but what we have is a list of unordered  
     * time instances in nanoseconds.
     * 
     * We sort the lap times list because there was a chance that a thread could have been 
     * suspended between getting the nanoTime and grabbing the lock. While suspended
     * another thread could have added a greater time instance into the list.
     */
    Collections.sort(lapTimesCopy);
    
    ListIterator<Long> itrStart = startTimesCopy.listIterator();    
    if (itrStart.hasNext()) {
      // get the very first start time
      Long start = itrStart.next();
      Long end;
      
      for (int i = 0; i < lapTimesCopy.size(); i++) {
        end = lapTimesCopy.get(i);        
        
        /* check if the next start time is in the interval between two lap instance times
         * and if it is use it.
         */
        if (itrStart.hasNext()) {
          Long tmpStart = startTimesCopy.get(itrStart.nextIndex());
          if (tmpStart > start && tmpStart <= end) {
            start = tmpStart;
            itrStart.next();
          }
        }
        
        lapTimesCopy.set(i, getTimeSpanInMilliseconds(start, end));
        start = end;
      }
    }    
    
    return lapTimesCopy;
  }

  /**
   * Indicates whether some object is equal to this SimpleStopwatch object.
   * This method test if the id's of the two stopwatches are equal.
   * 
   * @param o the reference object with which to compare 
   * @return true if this object is the same as the argument; false otherwise 
   */
  @Override 
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }      
    if (!(o instanceof IStopwatch)) {
      return false;
    }
    IStopwatch watch = (IStopwatch) o;
    return this.getId().equals(watch.getId());          
  }

  /**
   * Computes and returns the hash code value for this stopwatch object.
   * The hash code value is computed based on the stopwatch id.
   * 
   * @return a hash code value for this stopwatch object
   */
  @Override 
  public int hashCode() {
    return 31 + this.id.hashCode();
  }  
  
  /**
   * This method returns the string representation of this stopwatch object.
   * The exact representation are unspecified and subject to change,
   * but the following may be regarded as typical:
   * 
   * "[Stopwatch: id, Running: Yes/No, Number of laps: #]"
   * 
   * @return a string representation of this stopwatch object
   */
  @Override 
  public String toString() {
    synchronized(watchLock) {
      return String.format("[Stopwatch: %s, Running: %s, Number of laps %s]", 
          id, isWatchRunning ? "Yes" : "No", lapInstanceTimes.size());  
    }
  }  
}
