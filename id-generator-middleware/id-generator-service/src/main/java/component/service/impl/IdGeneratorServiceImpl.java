package component.service.impl;

import component.service.entity.IdEntity;

public class IdGeneratorServiceImpl extends AbstractIdGeneratorService {
    @Override
    protected void populateId(IdEntity id) {

    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public IdEntity inverseId(long id) {
        return null;
    }
}
