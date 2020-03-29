package cn.eclubcc.common.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicLong;

/**
 * java edition of Twitter <b>Snowflake</b>, a network service for generating unique ID numbers at
 * high scale with some simple guarantees.
 *
 * <p>https://github.com/twitter/snowflake
 */
@Slf4j
public class Snowflake {

  private final long unusedBits = 1L;
  /**
   * 'time stamp' here is defined as the number of millisecond that have elapsed since the {@link
   * #epoch} given by users on {@link Snowflake} instance initialization
   */
  private final long timestampBits = 41L;

  private final long datacenterIdBits = 5L;
  private final long workerIdBits = 5L;
  private final long sequenceBits = 12L;

  /*
   * max values of timeStamp, workerId, datacenterId and sequence
   */
  // 2^5-1
  private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
  // 2^5-1
  private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
  // 2^12-1
  private final long maxSequence = -1L ^ (-1L << sequenceBits);

  /** left shift bits of timeStamp, workerId and datacenterId */
  private final long timestampShift = sequenceBits + datacenterIdBits + workerIdBits;

  private final long datacenterIdShift = sequenceBits + workerIdBits;
  private final long workerIdShift = sequenceBits;

  /*
   * object status variables
   */

  /**
   * reference material of 'time stamp' is '2016-01-01'. its value can't be modified after
   * initialization.
   */
  private final long epoch = 1451606400000L;

  /**
   * data center number the process running on, its value can't be modified after initialization.
   *
   * <p>max: 2^5-1 range: [0,31]
   */
  private final long datacenterId;

  /**
   * machine or process number, its value can't be modified after initialization.
   *
   * <p>max: 2^5-1 range: [0,31]
   */
  private final long workerId;

  /**
   * the unique and incrementing sequence number scoped in only one period/unit (here is ONE
   * millisecond). its value will be increased by 1 in the same specified period and then reset to 0
   * for next period.
   *
   * <p>max: 2^12-1 range: [0,4095]
   */
  private long sequence = 0L;

  /** the time stamp last snowflake ID generated */
  private long lastTimestamp = -1L;

  private static Snowflake snowFlake = null;

  static {
    snowFlake = new Snowflake();
  }

  public static synchronized long nextId() {
    return snowFlake.getNextId();
  }

  /**
   * generate an unique and incrementing id
   *
   * @return id
   */
  public synchronized long getNextId() {
    long currTimestamp = timestampGen();

    if (currTimestamp < lastTimestamp) {
      throw new IllegalStateException(
          String.format(
              "Clock moved backwards. Refusing to generate id for %d milliseconds",
              lastTimestamp - currTimestamp));
    }

    if (currTimestamp == lastTimestamp) {
      sequence = (sequence + 1) & maxSequence;
      // overflow: greater than max sequence
      if (sequence == 0) {
        currTimestamp = waitNextMillis(currTimestamp);
      }

    } else { // reset to 0 for next period/millisecond
      sequence = 0L;
    }

    // track and memo the time stamp last snowflake ID generated
    lastTimestamp = currTimestamp;

    return ((currTimestamp - epoch) << timestampShift)
        | //
        (datacenterId << datacenterIdShift)
        | //
        (workerId << workerIdShift)
        | // new line for nice looking
        sequence;
  }

  private Snowflake() {
    // 获取机器编码
    long tempWorkerId = this.getMachineNum();
    // 获取进程编码
    RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
    long tempDatacenterId = Long.valueOf(runtimeMXBean.getName().split("@")[0]);
    // 避免编码超出最大值
    this.workerId = tempWorkerId & maxWorkerId;
    this.datacenterId = tempDatacenterId & maxDatacenterId;
  }

  /** track the amount of calling {@link #waitNextMillis(long)} method */
  private final AtomicLong waitCount = new AtomicLong(0);

  /** @return the amount of calling {@link #waitNextMillis(long)} method */
  public long getWaitCount() {
    return waitCount.get();
  }

  /**
   * running loop blocking until next millisecond
   *
   * @param currTimestamp current time stamp
   * @return current time stamp in millisecond
   */
  protected long waitNextMillis(long currTimestamp) {
    waitCount.incrementAndGet();
    while (currTimestamp <= lastTimestamp) {
      currTimestamp = timestampGen();
    }
    return currTimestamp;
  }

  /**
   * get current time stamp
   *
   * @return current time stamp in millisecond
   */
  protected long timestampGen() {
    return System.currentTimeMillis();
  }

  public long getEpoch() {
    return this.epoch;
  }

  /**
   * extract time stamp, datacenterId, workerId and sequence number information from the given id
   *
   * @param id a snowflake id generated by this object
   * @return an array containing time stamp, datacenterId, workerId and sequence number
   */
  public long[] parseId(long id) {
    long[] arr = new long[5];
    arr[4] = ((id & diode(unusedBits, timestampBits)) >> timestampShift);
    arr[0] = arr[4] + epoch;
    arr[1] = (id & diode(unusedBits + timestampBits, datacenterIdBits)) >> datacenterIdShift;
    arr[2] =
        (id & diode(unusedBits + timestampBits + datacenterIdBits, workerIdBits)) >> workerIdShift;
    arr[3] =
        (id & diode(unusedBits + timestampBits + datacenterIdBits + workerIdBits, sequenceBits));
    return arr;
  }

  /**
   * extract and display time stamp, datacenterId, workerId and sequence number information from the
   * given id in humanization format
   *
   * @param id snowflake id in Long format
   * @return snowflake id in String format
   */
  public String formatId(long id) {
    long[] arr = parseId(id);
    String tmf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(arr[0]));
    return String.format("%s, #%d, @(%d,%d)", tmf, arr[3], arr[1], arr[2]);
  }

  /**
   * a diode is a long value whose left and right margin are ZERO, while middle bits are ONE in
   * binary string layout. it looks like a diode in shape.
   *
   * @param offset left margin position
   * @param length offset+length is right margin position
   * @return a long value
   */
  private long diode(long offset, long length) {
    int lb = (int) (64 - offset);
    int rb = (int) (64 - (offset + length));
    return (-1L << lb) ^ (-1L << rb);
  }

  /**
   * getMachineNum by NetworkInterfaces
   *
   * @return MachineNum
   */
  private long getMachineNum() {
    long machinePiece;
    StringBuilder sb = new StringBuilder();
    Enumeration<NetworkInterface> e = null;
    try {
      e = NetworkInterface.getNetworkInterfaces();
    } catch (SocketException e1) {
      e1.printStackTrace();
    }
    while (e.hasMoreElements()) {
      NetworkInterface ni = e.nextElement();
      sb.append(ni.toString());
    }
    machinePiece = sb.toString().hashCode();
    return machinePiece;
  }
}
