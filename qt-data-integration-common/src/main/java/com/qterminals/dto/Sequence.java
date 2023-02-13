package com.qterminals.dto;

import com.qterminals.constant.SequenceType;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.StringJoiner;

@Getter
@Setter
public class Sequence {
    private Long genericKey;
    private String sequenceFormat;
    private Long sequenceCounter;
    private Long maximumDigits;
    private SequenceType sequenceType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sequence)) return false;
        Sequence sequence = (Sequence) o;
        return getGenericKey().equals(sequence.getGenericKey()) && getSequenceFormat().equals(sequence.getSequenceFormat()) && getSequenceCounter().equals(sequence.getSequenceCounter()) && getMaximumDigits().equals(sequence.getMaximumDigits()) && getSequenceType() == sequence.getSequenceType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGenericKey(), getSequenceFormat(), getSequenceCounter(), getMaximumDigits(), getSequenceType());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Sequence.class.getSimpleName() + "[", "]")
                .add("genericKey=" + genericKey)
                .add("sequence_format='" + sequenceFormat + "'")
                .add("sequence_counter=" + sequenceCounter)
                .add("maximumDigits=" + maximumDigits)
                .add("sequenceType=" + sequenceType)
                .toString();
    }
}
