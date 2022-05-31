package com.solveretur.issue;

import com.google.common.collect.Iterables;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
final class GroupedInputData implements Iterator<Map.Entry<ProcessingType, List<InputData>>> {
    private final NavigableMap<ProcessingType, List<InputData>> values;
    private ProcessingType currentGroup;

    Stream<Map.Entry<ProcessingType, List<InputData>>> stream() {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(this, Spliterator.ORDERED),
                false
        );
    }

    void feedback(final ProcessingType processingType, final InputData inputData) {
        if (currentGroup != null && processingType.ordinal() <= currentGroup.ordinal()) {
            throw new IllegalArgumentException(String.format("You tried to put ventilationGroup: %s when we already iterated to: %s%n", processingType, currentGroup));
        }
        values.putIfAbsent(processingType, new LinkedList<>());
        values.computeIfPresent(processingType, (key, value) -> {
            value.add(inputData);
            return value;
        });
    }

    static GroupedInputData from(final Stream<InputData> inputDataStream) {
        final NavigableMap<ProcessingType, List<InputData>> values = new TreeMap<>(Comparator.comparing(ProcessingType::ordinal));
        inputDataStream.forEach(it -> {
            final ProcessingType processingType = ProcessingType.findMatching(it);
            values.putIfAbsent(processingType, new LinkedList<>());
            values.computeIfPresent(processingType, (key, value) -> {
                value.add(it);
                return value;
            });
        });
        return new GroupedInputData(values);
    }


    @Override
    public boolean hasNext() {
        if (!values.isEmpty()) {
            if (currentGroup == null) {
                return true;
            } else {
                final var nextGroup = values.higherKey(currentGroup);
                return nextGroup != null;
            }
        }
        return false;    }

    @Override
    public Map.Entry<ProcessingType, List<InputData>> next() {
        if (!values.isEmpty()) {
            if (currentGroup == null) {
                currentGroup = values.firstKey();
            } else {
                final var nextGroup = values.higherKey(currentGroup);
                if (nextGroup != null) {
                    currentGroup = nextGroup;
                } else {
                    throw new NoSuchElementException(String.format("For keys: %s - next key is %s when current is %s%n", Iterables.toString(values.keySet()), nextGroup, currentGroup));
                }
            }
            return Map.entry(currentGroup, Objects.requireNonNull(values.get(currentGroup)));
        }
        throw new NoSuchElementException("Couldn't find next element - map is empty");
    }
}
