package com.github.yuzhian.zero.boot.common.util;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * Snowflake JAVA 实现
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 0000000000 00
 * 1位符号位 - 41位时间戳 - 5位数据中心id - 5机器标识id - 12位毫秒内计数
 */
public class Sequence {
    private static final SnowFlake snowFlake = new SnowFlake();

    public static Long getLong() {
        return snowFlake.nextId();
    }

    public static String getString() {
        return String.valueOf(snowFlake.nextId());
    }

    static class SnowFlake {

        // 时间起始标记点 2020-01-01 00:00:00
        private static final long TWEPOCH = 1577808000000L;
        // 数据中心
        private final long DATA_CENTER_ID;
        // 机器标识
        private final long MACHINE_ID;

        // 上一次时间戳
        private static long lastStamp = -1L;
        // 序列号
        private static long sequence = 0L;

        // 序列号占用的位数
        private static final long SEQUENCE_BIT = 12;
        // 机器标识占用的位数
        private static final long MACHINE_BIT = 5;
        // 数据中心占用的位数
        private static final long DATA_CENTER_BIT = 5;

        // 机器标志偏移量
        private static final long MACHINE_SHIFT = SEQUENCE_BIT;
        // 数据中心偏移量
        private static final long DATA_CENTER_SHIFT = SEQUENCE_BIT + MACHINE_BIT;
        // 时间戳偏移量
        private static final long TIMESTAMP_SHIFT = SEQUENCE_BIT + MACHINE_BIT + DATA_CENTER_BIT;

        // 序列号最大支持数量(4095)
        private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);
        // 机器最大支持数量(31)
        private static final long MAX_MACHINE = ~(-1L << MACHINE_BIT);
        // 数据中心最大支持数量(31)
        private static final long MAX_DATA_CENTER = ~(-1L << DATA_CENTER_BIT);

        SnowFlake() {
            this.DATA_CENTER_ID = getDataCenterId();
            this.MACHINE_ID = getMachineId(DATA_CENTER_ID);
        }

        /**
         * @return 下一个ID
         */
        synchronized long nextId() {
            long currStamp = getTimestamp();
            if (currStamp < lastStamp) {
                throw new RuntimeException(String.format("时钟向后移动, 拒绝生成ID, 倒退 %d 毫秒", lastStamp - currStamp));
            }
            if (currStamp == lastStamp) {
                //相同毫秒内, 序列号自增
                sequence = (sequence + 1) & MAX_SEQUENCE;
                //同一毫秒的序列数已经达到最大
                if (sequence == 0L) {
                    currStamp = tilNextMillis();
                }
            } else {
                //不同毫秒内，序列号置为0
                sequence = 0L;
            }
            lastStamp = currStamp;
            return (currStamp - TWEPOCH) << TIMESTAMP_SHIFT // 时间戳部分
                    | DATA_CENTER_ID << DATA_CENTER_SHIFT   // 数据中心部分
                    | MACHINE_ID << MACHINE_SHIFT           // 机器标识部分
                    | sequence;                             // 序列号部分
        }

        /**
         * @return 下一时间的时间戳
         */
        private static long tilNextMillis() {
            long timestamp = getTimestamp();
            while (timestamp <= lastStamp) {
                timestamp = getTimestamp();
            }
            return timestamp;
        }

        /**
         * @return 当前时间戳
         */
        private static long getTimestamp() {
            return System.currentTimeMillis();
        }

        /**
         * @param DATA_CENTER_ID 数据中心id
         * @return 机器标识 (mac地址 和 jvmPid)
         */
        private static long getMachineId(long DATA_CENTER_ID) {
            StringBuilder macAndPid = new StringBuilder();
            macAndPid.append(DATA_CENTER_ID);
            String name = ManagementFactory.getRuntimeMXBean().getName();
            if (!name.isEmpty()) {
                macAndPid.append(name.split("@")[0]);
            }
            return (macAndPid.toString().hashCode() & 0xffff) % (MAX_MACHINE + 1);
        }

        private static long getDataCenterId() {
            try {
                InetAddress ip = InetAddress.getLocalHost();
                NetworkInterface network = NetworkInterface.getByInetAddress(ip);
                if (network != null) {
                    byte[] mac = network.getHardwareAddress();
                    return (((0x000000FF & mac[mac.length - 1]) | (0x0000FF00 & ((mac[mac.length - 2]) << 8))) >> 6)
                            % (MAX_DATA_CENTER + 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 1L;
        }
    }

}
