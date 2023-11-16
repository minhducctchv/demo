package com.example.demo.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.stream.Collectors;

public class ModelMapperUtil {
    public static ModelMapper modelMapper;

    static {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public static <S, D> D map(S source, Class<D> destinationClass) {
        return modelMapper.map(source, destinationClass);
    }

    public static <S, D> Collection<D> mapAll(Collection<S> sources, Class<D> destinationClass) {
        return sources.stream().map(
                s -> modelMapper.map(s, destinationClass)).collect(Collectors.toList()
        );
    }

    public static <S, D> Page<D> mapPage(Page<S> sources, Class<D> destinationClass) {
        return sources.map(s -> modelMapper.map(s, destinationClass));
    }
}
