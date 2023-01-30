package com.luv2code.diaryio.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @param <E> entity class
 * @param <D> request dto class
 * */

@Component
public class RequestToEntityMapper<E, D> {

    private final ModelMapper modelMapper;

    @Autowired
    public RequestToEntityMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public E map(final D from, final Class<E> to) {
        return modelMapper.map(from, to);
    }

    public void map(final D from, final E to) {
        modelMapper.map(from, to);
    }
}
