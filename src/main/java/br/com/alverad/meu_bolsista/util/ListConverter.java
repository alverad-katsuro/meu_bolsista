package br.com.alverad.meu_bolsista.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.Converter;
import org.modelmapper.internal.util.MappingContextHelper;
import org.modelmapper.spi.MappingContext;

public class ListConverter implements Converter<List<Object>, List<Object>> {

	@Override
	public List<Object> convert(MappingContext<List<Object>, List<Object>> context) {
		if (context.getSource() == null) {
			if (context.getDestination() != null) {
				context.getDestination().clear();
			}
			return context.getDestination();
		}

		List<Object> destination = ObjectUtils.defaultIfNull(context.getDestination(), new ArrayList<>());

		Class<?> elementType = MappingContextHelper.resolveDestinationGenericType(context);

		destination.clear();
		context.getSource().forEach(sourceElement -> {
			MappingContext<?, ?> elementContext = context.create(sourceElement, elementType);
			destination.add(context.getMappingEngine().map(elementContext));
		});

		return destination;
	}
}