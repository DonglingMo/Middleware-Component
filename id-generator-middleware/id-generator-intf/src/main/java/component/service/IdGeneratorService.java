package component.service;

import component.service.entity.IdEntity;

public interface IdGeneratorService {
    long getId();

    IdEntity inverseId(long id);

}
