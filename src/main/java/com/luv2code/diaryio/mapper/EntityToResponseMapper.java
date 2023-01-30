package com.luv2code.diaryio.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * @param <E> entity class
 * @param <D> response dto class
 * */

@Component
public class EntityToResponseMapper<E, D> {

    private final ModelMapper modelMapper;

    @Autowired
    public EntityToResponseMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public D map(final E from, final Class<D> to) {
        return modelMapper.map(from, to);
    }

    public List<D> mapToList(final Collection<E> from, final Class<D> to) {
        return from.stream()
                .map(element -> this.map(element, to))
                .toList();
    }
}
