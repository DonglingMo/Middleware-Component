package component;

import component.entity.IdType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeUtils {
    public static final long EPOCH = 1704038400000L; //2024-01-01


    public static void validateTimestamp(long lastTimestamp, long timestamp) {
        if (timestamp < lastTimestamp) {
            if (log.isErrorEnabled())
                log.error(String
                        .format("Clock moved backwards.  Refusing to generate id for %d second/milisecond.",
                                lastTimestamp - timestamp));

            throw new IllegalStateException(
                    String.format(
                            "Clock moved backwards.  Refusing to generate id for %d second/milisecond.",
                            lastTimestamp - timestamp));
        }
    }

    public static long tillNextTimeUnit(final long lastTimestamp, final IdType idType) {
        if (log.isInfoEnabled())
            log.info(String
                    .format("Ids are used out during %d. Waiting till next second/milisencond.",
                            lastTimestamp));

        long timestamp = TimeUtils.genTime(idType);
        while (timestamp <= lastTimestamp) {
            timestamp = TimeUtils.genTime(idType);
        }

        if (log.isInfoEnabled())
            log.info(String.format("Next second/milisencond %d is up.",
                    timestamp));

        return timestamp;
    }

    public static long genTime(final IdType idType) {
        if (idType == IdType.MAX_PEAK)
            return (System.currentTimeMillis() - TimeUtils.EPOCH) / 1000;
        else if (idType == IdType.MIN_GRANULARITY)
            return (System.currentTimeMillis() - TimeUtils.EPOCH);

        return (System.currentTimeMillis() - TimeUtils.EPOCH) / 1000;
    }

}
