package de.ma.queue

import com.maju.annotations.IConverter
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper

@Mapper(
    componentModel = "cdi",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
interface QueueMapper : IConverter<Queue, QueueDTO>

