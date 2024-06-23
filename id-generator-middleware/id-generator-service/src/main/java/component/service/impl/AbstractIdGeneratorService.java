package component.service.impl;

import component.TimeUtils;
import component.converter.IdConverter;
import component.converter.IdConverterImpl;
import component.entity.IdEntityMeta;
import component.entity.IdEntityMetaBuilder;
import component.entity.IdType;
import component.provider.MachineIdProvider;
import component.service.IdGeneratorService;
import component.service.entity.IdEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public abstract class AbstractIdGeneratorService implements IdGeneratorService {

    protected long machineId = -1;
    protected long genMethod = 0;
    protected long type = 0;
    protected long version = 0;

    protected IdType idType;
    protected IdEntityMeta idEntityMeta;

    protected IdConverter idConverter;

    protected MachineIdProvider machineIdProvider;

    public AbstractIdGeneratorService() {
        idType = IdType.MAX_PEAK;
    }

    public AbstractIdGeneratorService(String type) {
        idType = IdType.parse(type);
    }

    public AbstractIdGeneratorService(IdType type) {
        idType = type;
    }

    public void init() {
        this.machineId = machineIdProvider.getMachineId();

        if (machineId < 0) {
            log.error("The machine ID is not configured properly so that Vesta Service refuses to start.");

            throw new IllegalStateException(
                    "The machine ID is not configured properly so that Vesta Service refuses to start.");

        }
        if(idEntityMeta == null){
            setIdMeta(IdEntityMetaBuilder.getIdEntityMeta(idType));
            setType(idType.value());
        } else {
            if(idEntityMeta.getTimeBits() == 30){
                setType(0);
            } else if(idEntityMeta.getTimeBits() == 40){
                setType(1);
            } else {
                throw new RuntimeException("Init Error. The time bits in IdMeta should be set to 30 or 40!");
            }
        }
        setIdConverter(new IdConverterImpl(this.idEntityMeta));
    }

    public long genId() {
        IdEntity id = new IdEntity();

        id.setMachine(machineId);
        id.setGenMethod(genMethod);
        id.setType(type);
        id.setVersion(version);

        populateId(id);

        long ret = idConverter.convert(id);

        // Use trace because it cause low performance
        if (log.isTraceEnabled())
            log.trace(String.format("Id: %s => %d", id, ret));

        return ret;
    }

    protected abstract void populateId(IdEntity id);

    public Date transTime(final long time) {
        if (idType == IdType.MAX_PEAK) {
            return new Date(time * 1000 + TimeUtils.EPOCH);
        } else if (idType == IdType.MIN_GRANULARITY) {
            return new Date(time + TimeUtils.EPOCH);
        }

        return null;
    }


    public IdEntity expId(long id) {
        return idConverter.convert(id);
    }

    public long makeId(long time, long seq) {
        return makeId(time, seq, machineId);
    }

    public long makeId(long time, long seq, long machine) {
        return makeId(genMethod, time, seq, machine);
    }

    public long makeId(long genMethod, long time, long seq, long machine) {
        return makeId(type, genMethod, time, seq, machine);
    }

    public long makeId(long type, long genMethod, long time,
                       long seq, long machine) {
        return makeId(version, type, genMethod, time, seq, machine);
    }

    public long makeId(long version, long type, long genMethod,
                       long time, long seq, long machine) {
        IdType idType = IdType.parse(type);

        IdEntity id = new IdEntity(machine, seq, time, genMethod, type, version);
        IdConverter idConverter = new IdConverterImpl(idType);

        return idConverter.convert(id);
    }


    public void setMachineId(long machineId) {
        this.machineId = machineId;
    }

    public void setGenMethod(long genMethod) {
        this.genMethod = genMethod;
    }

    public void setType(long type) {
        this.type = type;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public void setIdConverter(IdConverter idConverter) {
        this.idConverter = idConverter;
    }

    public void setIdMeta(IdEntityMeta idEntityMeta) {
        this.idEntityMeta = idEntityMeta;
    }

    public void setMachineIdProvider(MachineIdProvider machineIdProvider) {
        this.machineIdProvider = machineIdProvider;
    }
}
