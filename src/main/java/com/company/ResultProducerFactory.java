package com.company;

import java.util.function.BiFunction;

/**
 * Factory for ResultProducer
 */
final class ResultProducerFactory {
    private static final BiFunction<Character, Character, Character> AND_RESULT_PRODUCER = (input1, input2) -> (char)(input1 & input2);
    private static final BiFunction<Character, Character, Character> OR_RESULT_PRODUCER = (input1, input2) -> (char)(input1 | input2);
    private static final BiFunction<Character, Character, Character> L_SHIFT_RESULT_PRODUCER = (input1, input2) -> (char)(input1 << input2);
    private static final BiFunction<Character, Character, Character> R_SHIFT_RESULT_PRODUCER = (input1, input2) -> (char)(input1 >> input2);
    private static final BiFunction<Character, Character, Character> NOT_RESULT_PRODUCER = (input1, input2) -> (char)(~input1);
    private static final BiFunction<Character, Character, Character> SAME_RESULT_PRODUCER = (input1, input2) -> input1;

    static BiFunction<Character, Character, Character> get2OperandsProducer(final String type, IllegalArgumentException exception)
    {
        switch(type)
        {
            case "OR":
                return OR_RESULT_PRODUCER;
            case "AND":
                return AND_RESULT_PRODUCER;
            case "LSHIFT":
                return L_SHIFT_RESULT_PRODUCER;
            case "RSHIFT":
                return R_SHIFT_RESULT_PRODUCER;
            default:
                throw exception;
        }
    }

    static BiFunction<Character, Character, Character> getNotResultProducer()
    {
        return NOT_RESULT_PRODUCER;
    }

    static BiFunction<Character, Character, Character> getSameResultProducer()
    {
        return SAME_RESULT_PRODUCER;
    }
}
