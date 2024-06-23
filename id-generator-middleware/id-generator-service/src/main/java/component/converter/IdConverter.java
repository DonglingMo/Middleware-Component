package component.converter;

import component.service.entity.IdEntity;


public interface IdConverter {
    long convert(IdEntity id);

    IdEntity convert(long id);
}
