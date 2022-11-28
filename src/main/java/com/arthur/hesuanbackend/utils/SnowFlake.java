package com.arthur.hesuanbackend.utils;

public class SnowFlake {
    /**
     * 简单的雪花算法
     *

        /* 0(符号占1位) + time(时间戳占41位--88年) + roomId(机房号占5位--32个） + machineId(机器号占5位--32个) + count(单位时间戳内生成的id个数占12位--4096个) */

        private long timestamp;

        private final long roomId;

        private final long machineId;

        private long count;

        public SnowFlake(long roomId, long machineId) {
            this.timestamp = System.currentTimeMillis();
            this.roomId = roomId;
            this.machineId = machineId;
        }

        public synchronized long nextId() {
            long nextTimestamp = System.currentTimeMillis();
            if (nextTimestamp < timestamp) {
                throw new RuntimeException("时间错误！");
            } else if (nextTimestamp == timestamp) {
                if (count >= 1 << 12) {
                    throw new RuntimeException("超出负载");
                }
                count++;
            } else {
                count = 0;
                timestamp = nextTimestamp;
            }
            return timestamp << 22 | roomId << 17 | machineId << 12 | count;
        }

        /* 测试 */
        public static void main(String[] args) {
            IdWorker idWorker = new IdWorker(1L, 1L);
            long id = idWorker.nextId();
            System.out.println(id);
        }
}
