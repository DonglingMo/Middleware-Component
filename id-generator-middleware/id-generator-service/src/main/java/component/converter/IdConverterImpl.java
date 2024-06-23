package component.converter;

import component.entity.IdEntityMeta;
import component.entity.IdEntityMetaBuilder;
import component.entity.IdType;
import component.service.entity.IdEntity;

public class IdConverterImpl implements IdConverter {
    private IdEntityMeta idEntityMeta;

    public IdConverterImpl() {
    }

    public IdConverterImpl(IdType idType) {
        this(IdEntityMetaBuilder.getIdEntityMeta(idType));
    }

    public IdConverterImpl(IdEntityMeta idEntityMeta) {
        this.idEntityMeta = idEntityMeta;
    }

    @Override
    public long convert(IdEntity id) {
        return doConvert(id, idEntityMeta);
    }

    protected long doConvert(IdEntity idEntity, IdEntityMeta idEntityMeta) {
        long ret = 0;

        ret |= idEntity.getMachine();

        ret |= idEntity.getSeq() << idEntityMeta.getSeqBitsStartPos();

        ret |= idEntity.getTime() << idEntityMeta.getTimeBitsStartPos();

        ret |= idEntity.getGenMethod() << idEntityMeta.getGenMethodBitsStartPos();

        ret |= idEntity.getType() << idEntityMeta.getTypeBitsStartPos();

        ret |= idEntity.getVersion() << idEntityMeta.getVersionBitsStartPos();

        return ret;
    }

    @Override
    public IdEntity convert(long id) {
        return doConvert(id, idEntityMeta);
    }

    protected IdEntity doConvert(long id, IdEntityMeta idEntityMeta) {
        IdEntity ret = new IdEntity();

        ret.setMachine(id & idEntityMeta.getMachineBitsMask());

        ret.setSeq((id >>> idEntityMeta.getSeqBitsStartPos()) & idEntityMeta.getSeqBitsMask());

        ret.setTime((id >>> idEntityMeta.getTimeBitsStartPos()) & idEntityMeta.getTimeBitsMask());

        ret.setGenMethod((id >>> idEntityMeta.getGenMethodBitsStartPos()) & idEntityMeta.getGenMethodBitsMask());

        ret.setType((id >>> idEntityMeta.getTypeBitsStartPos()) & idEntityMeta.getTypeBitsMask());

        ret.setVersion((id >>> idEntityMeta.getVersionBitsStartPos()) & idEntityMeta.getVersionBitsMask());

        return ret;
    }

    public IdEntityMeta getIdEntityMeta() {
        return idEntityMeta;
    }

    public void setIdMeta(IdEntityMeta idEntityMeta) {
        this.idEntityMeta = idEntityMeta;
    }
}
