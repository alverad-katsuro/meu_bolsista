package br.com.alverad.meu_bolsista.util;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.Converter;
import org.modelmapper.internal.util.MappingContextHelper;
import org.modelmapper.spi.MappingContext;

public class SetConverter implements Converter<Set<Object>, Set<Object>> {

	@Override
	public Set<Object> convert(MappingContext<Set<Object>, Set<Object>> context) {
		if (context.getSource() == null) {
			if (context.getDestination() != null) {
				context.getDestination().clear();
			}
			return context.getDestination();
		}

		Set<Object> destination = ObjectUtils.defaultIfNull(context.getDestination(), new HashSet<>());

		Class<?> elementType = MappingContextHelper.resolveDestinationGenericType(context);

		destination.clear();
		context.getSource().forEach(sourceElement -> {
			MappingContext<?, ?> elementContext = context.create(sourceElement, elementType);
			destination.add(context.getMappingEngine().map(elementContext));
		});

		return destination;
	}
}