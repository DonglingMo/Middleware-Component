package component.entity;

import component.service.entity.IdEntity;

public class IdEntityMetaBuilder {
    private static final IdEntityMeta MAX_PEAK = new IdEntityMeta((byte) 10, (byte) 20, (byte) 30, (byte) 2, (byte) 1, (byte) 1);

    private static final IdEntityMeta MIN_GRANULARITY = new IdEntityMeta((byte) 10, (byte) 10, (byte) 40, (byte) 2, (byte) 1, (byte) 1);

    public static IdEntityMeta getIdEntityMeta(IdType type) {
        if (IdType.MAX_PEAK.equals(type)) {
            return MAX_PEAK;
        } else if (IdType.MIN_GRANULARITY.equals(type)) {
            return MIN_GRANULARITY;
        }
        return null;
    }
}
